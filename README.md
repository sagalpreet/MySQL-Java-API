Submitter name: Sagalpreet Singh
Roll No.: 2019csb1113
Course: CS305

=================================

# 1. What does this program do

This is an implementation of a library **MySql** which provides an API to execute MySQL queries. Any MySQL database can be accessed through this API. The database can be queried, updated etc. The API structure is as follows:

- *public MySql(String path, String username, String password, String database) throws Exception;*
- *private <T> String toSqlString(Field field, T queryParam) throws Exception;*
- *<T, R> List<R> selectMany(String queryId, T queryParam, Class<R> resultItemType) throws Exception;*
- *<T> int update(String queryId, T queryParam) throws Exception;*
- *<T> int insert(String queryId, T queryParam) throws Exception;*
- *<T> int delete(String queryId, T queryParam) throws Exception;*

The first of these is the constructor for the MySql class and rest all are standard methods as specified in the assignment.
Note that these methods represent the API, there are additional methods which are used as utilities in these methods, but they are private, or in other words they are present in library but not in API.

#### File Structure
.
├── app
│   ├── bin
│   │   ├── main
│   │   │   ├── cs305_2022
│   │   │   │   └── App.class
│   │   │   ├── date
│   │   │   │   ├── MysqlDate$1.class
│   │   │   │   └── MysqlDate.class
│   │   │   ├── queries.xml
│   │   │   ├── sqlrunner
│   │   │   │   ├── MySql.class
│   │   │   │   └── SqlRunner.class
│   │   │   └── xml
│   │   │       └── Reader.class
│   │   └── test
│   │       ├── cs305_2022
│   │       │   └── AppTest.class
│   │       ├── date
│   │       │   └── MysqlDateTest.class
│   │       ├── pojo
│   │       │   ├── DeleteActorsParam.class
│   │       │   ├── FindActorsParam.class
│   │       │   ├── FindActorsResult.class
│   │       │   ├── FindManyActorsParam.class
│   │       │   ├── FindManyActorsResult.class
│   │       │   ├── InsertActorsParam.class
│   │       │   └── UpdateActorsParam.class
│   │       ├── queries.xml
│   │       ├── sqlrunner
│   │       │   └── MySqlTest.class
│   │       └── xml
│   │           └── ReaderTest.class
│   ├── build
│   │   ├── classes
│   │   │   └── java
│   │   │       ├── main
│   │   │       │   ├── cs305_2022
│   │   │       │   │   └── App.class
│   │   │       │   ├── date
│   │   │       │   │   ├── MysqlDate$1.class
│   │   │       │   │   └── MysqlDate.class
│   │   │       │   ├── sqlrunner
│   │   │       │   │   ├── MySql.class
│   │   │       │   │   └── SqlRunner.class
│   │   │       │   └── xml
│   │   │       │       └── Reader.class
│   │   │       └── test
│   │   │           ├── cs305_2022
│   │   │           │   └── AppTest.class
│   │   │           ├── date
│   │   │           │   └── MysqlDateTest.class
│   │   │           ├── pojo
│   │   │           │   ├── DeleteActorsParam.class
│   │   │           │   ├── FindActorsParam.class
│   │   │           │   ├── FindActorsResult.class
│   │   │           │   ├── FindManyActorsParam.class
│   │   │           │   ├── FindManyActorsResult.class
│   │   │           │   ├── InsertActorsParam.class
│   │   │           │   └── UpdateActorsParam.class
│   │   │           ├── sqlrunner
│   │   │           │   └── MySqlTest.class
│   │   │           └── xml
│   │   │               └── ReaderTest.class
│   │   ├── distributions
│   │   │   ├── app.tar
│   │   │   └── app.zip
│   │   ├── generated
│   │   │   └── sources
│   │   │       ├── annotationProcessor
│   │   │       │   └── java
│   │   │       │       ├── main
│   │   │       │       └── test
│   │   │       └── headers
│   │   │           └── java
│   │   │               ├── main
│   │   │               └── test
│   │   ├── libs
│   │   │   └── app.jar
│   │   ├── reports
│   │   │   └── tests
│   │   │       └── test
│   │   │           ├── classes
│   │   │           │   ├── cs305_2022.AppTest.html
│   │   │           │   ├── date.MysqlDateTest.html
│   │   │           │   ├── sqlrunner.MySqlTest.html
│   │   │           │   └── xml.ReaderTest.html
│   │   │           ├── css
│   │   │           │   ├── base-style.css
│   │   │           │   └── style.css
│   │   │           ├── index.html
│   │   │           ├── js
│   │   │           │   └── report.js
│   │   │           └── packages
│   │   │               ├── cs305_2022.html
│   │   │               ├── date.html
│   │   │               ├── sqlrunner.html
│   │   │               └── xml.html
│   │   ├── resources
│   │   │   ├── main
│   │   │   │   └── queries.xml
│   │   │   └── test
│   │   │       └── queries.xml
│   │   ├── scripts
│   │   │   ├── app
│   │   │   └── app.bat
│   │   ├── test-results
│   │   │   └── test
│   │   │       ├── binary
│   │   │       │   ├── output.bin
│   │   │       │   ├── output.bin.idx
│   │   │       │   └── results.bin
│   │   │       ├── TEST-cs305_2022.AppTest.xml
│   │   │       ├── TEST-date.MysqlDateTest.xml
│   │   │       ├── TEST-sqlrunner.MySqlTest.xml
│   │   │       └── TEST-xml.ReaderTest.xml
│   │   └── tmp
│   │       ├── compileJava
│   │       │   └── previous-compilation-data.bin
│   │       ├── compileTestJava
│   │       │   └── previous-compilation-data.bin
│   │       ├── jar
│   │       │   └── MANIFEST.MF
│   │       └── test
│   ├── build.gradle.kts
│   └── src
│       ├── main
│       │   ├── java
│       │   │   ├── cs305_2022
│       │   │   │   └── App.java
│       │   │   ├── date
│       │   │   │   └── MysqlDate.java
│       │   │   ├── sqlrunner
│       │   │   │   ├── MySql.java
│       │   │   │   └── SqlRunner.java
│       │   │   └── xml
│       │   │       └── Reader.java
│       │   └── resources
│       │       └── queries.xml
│       └── test
│           ├── java
│           │   ├── cs305_2022
│           │   │   └── AppTest.java
│           │   ├── date
│           │   │   └── MysqlDateTest.java
│           │   ├── pojo
│           │   │   ├── DeleteActorsParam.java
│           │   │   ├── FindActorsParam.java
│           │   │   ├── FindActorsResult.java
│           │   │   ├── FindManyActorsParam.java
│           │   │   ├── FindManyActorsResult.java
│           │   │   ├── InsertActorsParam.java
│           │   │   └── UpdateActorsParam.java
│           │   ├── sqlrunner
│           │   │   └── MySqlTest.java
│           │   └── xml
│           │       └── ReaderTest.java
│           └── resources
│               └── queries.xml
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle.kts

76 directories, 90 files

# 2. A description of how this program works (i.e. its logic)

Gradle (Kotlin) has been used as the build system for this project. The corresponding settings can be accessed from settings.gradle.kts and build.gradle.kts files. The given problem can be broken down into following subproblems which can then be solved independently.

- Reading XML file
    - The XML file supplies the syntax of possible queries that can be executed for the database. These queries have a structure which needs to be followed in defining new query syntaxes. The requirements are as follows:
        - The dynamically filled variables in query should be given the exact same name as is the corresponding field label in the schema of that table.
        - ${var_name} is the syntax to have dynamic variables. Note that var_name should not contain anything other than alphanumerics and underscores.
    - A different package by the name xml is designed to provide interface of reading XML files. The class is named Reader.
    - javax.xml is used for handling xml files
    - java.io.File is used to read file
    - org.w3c.dom is used to interact with the Document Object Model of the xml file.
    - If an error is incurred while reading the contents from xml file, the corresponding Exception is thrown else a HashMap is returned which contains the tag, paramType and query. The query is fetched using *fetchQuerySyntax* method which takes queryID as input.
- Handling *Date* data structure.
    - Java dates are differently represented when compared to Mysql timestamps, so a package by the name *MysqlDate* is made, the sole purpose of which is to convert Java date to the string format of MySQL date.
- Core Functionality
    - Reflection API is used everywhere to create instance of class whose class is passed as an argument/generic to method. This API is also used to get the class of an object received from generics to conveniently exchange information between Java data types and MySQL data types.
    - MySQL database is intelligently able to type cast java strings to corresponding data types of MySQL which provides us with the flexibility to pass arguments as strings.
    - Regex is used to replace tokens in query syntax from xml with dynamically given variables.
    - JDBC has been used to interact with the database. This is a dependency for the project and listed under depencies in the build configuration file.
    - The logic for constructing queries is common, no matter what type the query is, this is achieved using regex.
    - For selectOne, we return the first row's content in the desired object even if multiple rows are received as a result of the query. In case, no row is returned, the null referenced object is returned.
    - The logic for update, delete and insert is exactly same as we use *executeUpdate* method for all of these. This method also gives the number of rows affected so there is literally nothing to be done manually, all thanks to the creators of Connection class.


# 3. How to compile and run this program

- The library can be used inside *cs305_2022/app/src/main/java/cs305_2022/App.java* by:
    - ```import sqlrunner.*``` 
        - as it will provide with the Mysql class
    - ```./gradlew run```
        - to compile and run the program written
- To build the project, use the command:
    - ```./gradlew build```
- To run unit tests:
    - ```gradle clean test```
    - *This may give a warning about deprecated class which can be ignored safely.*

# 4. Provide a snapshot of a sample run
```
┌💁  sagalpreet @ 💻  den in 📁  cs305_2022 on 🌿  assignment_1 •1 ⌀1 ✗
└❯ gradle clean test

> Task :app:compileTestJava
Note: /media/sagalpreet/Data/Sagal/Coursework/IIT-Ropar/Sem-6/CS305/cs305_2022/app/src/test/java/date/MysqlDateTest.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.

BUILD SUCCESSFUL in 3s
6 actionable tasks: 6 executed
```


