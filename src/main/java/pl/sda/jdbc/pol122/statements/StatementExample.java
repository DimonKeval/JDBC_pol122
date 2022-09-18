package pl.sda.jdbc.pol122.statements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementExample {

    public static void launchStatement(){

        final String sqlQuery = "CREATE TABLE phones(id INT NOT NULL PRIMARY KEY, brand VARCHAR(30) NOT NULL, model VARCHAR(30) NOT NULL, color VARCHAR(30) NOT NULL, price INT NOT NULL)";
        Connection connection = null;
        Statement statement = null;
        try {
            //otwieranie połączenia z DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?serverTimezone=UTC",
                    "root", "Paramka32//");
            statement = connection.createStatement();

            statement.execute(sqlQuery);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se){
                se.printStackTrace();
            }
        }
    }
}
