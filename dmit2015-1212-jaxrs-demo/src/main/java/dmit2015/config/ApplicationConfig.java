package dmit2015.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinitions({

//        @DataSourceDefinition(
//                name="java:app/datasources/h2databaseDS",
//                className="org.h2.jdbcx.JdbcDataSource",
//                url="jdbc:h2:file:~/databases/dmit2015-jaxrs-demodb",
////            url="jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
//                user="sa",
//                password="sa"),

        @DataSourceDefinition(
                name="java:app/datasources/hsqldatabaseDS",
                className="org.hsqldb.jdbc.JDBCDataSource",
		url="jdbc:hsqldb:file:~/jdk/databases/dmit2015-jaxrs-demos-hsqldb",
//                url="jdbc:hsqldb:mem:dmit2015hsqldb",
                user="user2015",
                password="Password2015"),

})

@ApplicationScoped
public class ApplicationConfig {

}