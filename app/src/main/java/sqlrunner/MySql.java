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

    private Reader xmlReader;
    private Connection connection;

    public MySql(String path, String username, String password, String database) throws Exception {
        this.xmlReader = new Reader(path);
        try {
            this.connection = dbConnect(username, password, database);
        } catch (Exception e) {
            throw e;
        }
    }

    private <T> String toSqlString(Field field, T queryParam) throws Exception {
        // TODO: Extend to arrays

        String sqlString = String.valueOf(field.get(queryParam));
        String fieldType = field.getType().toString();

        if (fieldType.equals("class java.util.Date")) {
            try {
                Date fieldValue = (Date) field.get(queryParam);
                sqlString = MysqlDate.toMysqlDate(fieldValue);
            } catch (Exception e) {
                throw e;
            }
            ;
        }

        return sqlString;
    }

    private <T> String constructQuery(String querySyntax, T queryParam) throws Exception {
        Pattern pattern = Pattern.compile("\\$\\{[a-z_A-Z0-9]*\\}");
        Matcher matcher = pattern.matcher(querySyntax);

        HashMap<String, String> replacements = new HashMap<String, String>();

        while (matcher.find()) {
            String match = matcher.group();
            String attribute = match.substring(2, match.length() - 1);

            Class<?> queryParamClass = queryParam.getClass();

            try {
                Field field = queryParamClass.getDeclaredField(attribute);
                String fieldValue = toSqlString(field, queryParam);
                replacements.put(match, "'" + fieldValue + "'");

            } catch (Exception e) {
                throw e;
            }
        }

        String query = querySyntax;

        for (var entry : replacements.entrySet()) {
            query = query.replace(entry.getKey(), entry.getValue());
        }

        return query;
    }

    private Connection dbConnect(String username, String password, String database) throws Exception {
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

        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        String query = constructQuery(querySyntax.get("query"), queryParam);
        ResultSet rs = sendSelectQuery(query);

        R result = null;

        while (rs.next()) {

            result = resultType.cast(resultType.getDeclaredConstructor().newInstance());
            Field[] attributes = resultType.getFields();

            for (Field x : attributes) {
                int column = rs.findColumn(x.getName().toString());
                String value = rs.getString(column);
                x.set(result, value);
            }

            return result;
        }

        return result;
    }

    public <T, R> List<R> selectMany(String queryId, T queryParam, Class<R> resultItemType) throws Exception {
        HashMap<String, String> querySyntax;

        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        String query = constructQuery(querySyntax.get("query"), queryParam);
        ResultSet rs = sendSelectQuery(query);

        List<R> results = new ArrayList<R> ();

        R result = null;

        while (rs.next()) {

            result = resultItemType.cast(resultItemType.getDeclaredConstructor().newInstance());
            Field[] attributes = resultItemType.getFields();

            for (Field x : attributes) {
                int column = rs.findColumn(x.getName().toString());
                String value = rs.getString(column);
                x.set(result, value);
            }

            results.add(result);
        }

        return results;
    }

    public <T> int update(String queryId, T queryParam) throws Exception{
        HashMap<String, String> querySyntax;

        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        String query = constructQuery(querySyntax.get("query"), queryParam);
        return sendUpdateQuery(query);
    }

    public <T> int insert(String queryId, T queryParam) throws Exception{
        HashMap<String, String> querySyntax;

        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        String query = constructQuery(querySyntax.get("query"), queryParam);

        return sendUpdateQuery(query);
    }

    public <T> int delete(String queryId, T queryParam) throws Exception{
        HashMap<String, String> querySyntax;

        try {
            querySyntax = xmlReader.fetchQuerySyntax(queryId);
        } catch (Exception e) {
            throw e;
        }

        String query = constructQuery(querySyntax.get("query"), queryParam);
        System.out.println(query);
        return sendUpdateQuery(query);
    }
}
