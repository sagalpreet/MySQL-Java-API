package sqlrunner;

import java.lang.reflect.*;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.regex.*;
import java.util.Date;

import xml.Reader;
import date.MysqlDate;

public class MySql implements SqlRunner {
    /**
     * provides an API to perform dynamic queries over
     * a MySQL database
     */
    private Reader xmlReader; // for reading xml and getting query syntax from queryID
    private Connection connection; // to connect to the database

    public MySql(String path, String username, String password, String database) throws Exception {
        /**
         * constructor to set the path of xml file to read from
         * and establish database connection
         */
        this.xmlReader = new Reader(path);
        
        // catch exception if any while initializing database connection
        try {
            this.connection = dbConnect(username, password, database);
        } catch (Exception e) {
            throw e;
        }
    }

    private <T> String toSqlString(Field field, T queryParam) throws Exception {
        // converts the java type to sql type as a string

        String sqlString = String.valueOf(field.get(queryParam));
        String fieldType = field.getType().toString();

        // dates need to be handled separately
        if (fieldType.equals("class java.util.Date")) {
            try {
                Date fieldValue = (Date) field.get(queryParam);
                sqlString = MysqlDate.toMysqlDate(fieldValue);
            } catch (Exception e) {
                throw e;
            }
        }

        return sqlString;
    }

    private <T> String constructQuery(String querySyntax, T queryParam) throws Exception {
        /**
         * query is constructed from the querySyntax and
         * parameters are populated in place of tokens
         * to generate query on the fly
         */

        // regex pattern to extract tokens
        Pattern pattern = Pattern.compile("\\$\\{[a-z_A-Z0-9]*\\}");
        // match found in the querySyntax for tokens
        Matcher matcher = pattern.matcher(querySyntax);

        // hashmap to map tokens to values that are to be substituted in
        // their place
        HashMap<String, String> replacements = new HashMap<String, String>();

        while (matcher.find()) {
            // all the tokens are identified and populated into
            // hashmap with their values

            String match = matcher.group(); // returns matched token

            // curly brackets and dollar sign is removed to obtain
            // attribute name
            String attribute = match.substring(2, match.length() - 1);

            // reflection API is used to obtain the class of
            // an object
            Class<?> queryParamClass = queryParam.getClass();

            // attempt to replace all the fields is made else exception is raised
            try {
                Field field = queryParamClass.getDeclaredField(attribute);
                String fieldValue = toSqlString(field, queryParam);

                // value needs to be enclosed in single quotes for mysql
                // mysql convert strings to data types intelligently
                replacements.put(match, "'" + fieldValue + "'");

            } catch (Exception e) {
                throw e;
            }
        }

        String query = querySyntax;

        // all the replacements are made to obtain the final query
        for (var entry : replacements.entrySet()) {
            query = query.replace(entry.getKey(), entry.getValue());
        }

        return query;
    }

    private Connection dbConnect(String username, String password, String database) throws Exception {
        /**
         * connection to database is made
         */
        connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/" + database + "?user=" + username + "&" + "password=" + password);
        } catch (Exception e) {
            throw e;
        }
        return connection;
    }

    private ResultSet sendSelectQuery(String query) {
        /**
         * make a select query in database
         */
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int sendUpdateQuery(String query) {
        /**
         * update query in database is made
         * update here can actually be used
         * for any query which does not get
         * a set output
         * 
         * so, this works for delete and 
         * insert as well
         * 
         * returns the number of rows affected
         */
        try {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public <T, R> R selectOne(String queryId, T queryParam, Class<R> resultType) throws Exception {
        HashMap<String, String> querySyntax;

        // querySyntax from xml is fetched
        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        // final query is constructed
        String query = constructQuery(querySyntax.get("query"), queryParam);

        // query is made and results obtains
        ResultSet rs = sendSelectQuery(query);

        // null is returned if no row returned
        R result = null;

        // iterate over the resulting rows
        // return right after the first on
        // as the function is "selectOne"
        while (rs.next()) {
            // instantiate result of specified type
            result = resultType.cast(resultType.getDeclaredConstructor().newInstance());

            // we obtain all the fields of object and populate those
            Field[] attributes = resultType.getFields();

            for (Field x : attributes) {
                // get column number in database corresponding to the field
                int column = rs.findColumn(x.getName().toString());

                String value = rs.getString(column);

                // every value in object has to be a string type
                x.set(result, value);
            }

            return result;
        }

        return result;
    }

    public <T, R> List<R> selectMany(String queryId, T queryParam, Class<R> resultItemType) throws Exception {
        HashMap<String, String> querySyntax;

        // querySyntax from xml is fetched
        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        // query is constructed
        String query = constructQuery(querySyntax.get("query"), queryParam);

        // query is executed
        ResultSet rs = sendSelectQuery(query);

        // all the rows need to be returned
        // ArrayList is used for this purpose
        List<R> results = new ArrayList<R> ();

        R result = null;

        while (rs.next()) {

            result = resultItemType.cast(resultItemType.getDeclaredConstructor().newInstance());
            Field[] attributes = resultItemType.getFields();

            // iterate over each attribute in object
            for (Field x : attributes) {
                // get corresponding column in table
                int column = rs.findColumn(x.getName().toString());

                String value = rs.getString(column);

                // all values in object are assumed to be strings
                x.set(result, value);
            }

            results.add(result);
        }

        return results;
    }

    public <T> int update(String queryId, T queryParam) throws Exception{
        HashMap<String, String> querySyntax;

        // fetch query syntax from xml
        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        // construct query dynamically
        String query = constructQuery(querySyntax.get("query"), queryParam);

        // return the number of rows modified
        return sendUpdateQuery(query);
    }

    public <T> int insert(String queryId, T queryParam) throws Exception{
        HashMap<String, String> querySyntax;

        // fetch query syntax from xml
        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        // construct query dynamically
        String query = constructQuery(querySyntax.get("query"), queryParam);

        // return the number of rows modified
        return sendUpdateQuery(query);
    }

    public <T> int delete(String queryId, T queryParam) throws Exception{
        HashMap<String, String> querySyntax;

         // fetch query syntax from xml
        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        // construct query dynamically
        String query = constructQuery(querySyntax.get("query"), queryParam);

        // return the number of rows modified
        return sendUpdateQuery(query);
    }
}
