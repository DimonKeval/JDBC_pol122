package pl.sda.jdbc.pol122.statements;

import pl.sda.jdbc.pol122.statements.entites.PhoneDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementExample {

    public static void launchPreparedStatement() {

        final String sqlQuery = "INSERT INTO users (id, name, lastname, phone_brand, age) VALUES (?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //otwieranie połączenia z DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?serverTimezone=UTC",
                    "root", "Paramka32//");
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Katy");
            preparedStatement.setString(3, "Presley");
            preparedStatement.setString(4, "ABC");
            preparedStatement.setInt(5, 22);
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void launchSelectOnTable() {
        final String sqlQuery = "SELECT * FROM users";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            //otwieranie połączenia z DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?serverTimezone=UTC",
                    "root", "Paramka32//");
            preparedStatement = connection.prepareStatement(sqlQuery);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                String name = result.getString("name");
                int age = result.getInt(5);
                System.out.println("Imię użytkownika: " + name + ". Wiek: " + age);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static int populateTabPhones(List<PhoneDAO> inputs) {
        final String sqlQuery = "INSERT INTO phones (id, brand, model, color, price) VALUES (?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            //otwieranie połączenia z DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?serverTimezone=UTC",
                    "root", "Paramka32//");
            preparedStatement = connection.prepareStatement(sqlQuery);
            for (PhoneDAO ph : inputs) {
                preparedStatement.setInt(1, ph.getId());
                preparedStatement.setString(2, ph.getBrand());
                preparedStatement.setString(3, ph.getModel());
                preparedStatement.setString(4, ph.getColor());
                preparedStatement.setInt(5, ph.getPrice());
                preparedStatement.executeUpdate();
                result++;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;
    }

    public static List<PhoneDAO> getPhoneDAOsByPriceBetween(int minPrice, int maxPrice) {
        final String sqlQuery = "SELECT * FROM phones WHERE price BETWEEN ? AND ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<PhoneDAO> phones = new ArrayList<>();
        try {
            //otwieranie połączenia z DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?serverTimezone=UTC",
                    "root", "Paramka32//");
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, minPrice);
            preparedStatement.setInt(2, maxPrice);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                phones.add(new PhoneDAO(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return phones;
    }

    public static int changePhonesPricesByIds(int idStart, int idEnd) throws SQLException {
        final String sqlQuery = "UPDATE phones SET price = 300 WHERE id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int resultCount = 0 ;
        try {
            //otwieranie połączenia z DB
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?serverTimezone=UTC",
                    "root", "Paramka32//");
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlQuery);
            for (int i = idStart; i <= idEnd; i++) {
                preparedStatement.setInt(1, i);
                int res = preparedStatement.executeUpdate();
                if (res == 0){
                    throw new SQLException("Row with id " + i + " doesn't exist in DB.");
                }
                resultCount++;
            }
            connection.commit();
        } catch (SQLException se) {
            connection.rollback();
            se.printStackTrace();
        } catch (Exception e) {
//            Cofamy transakcję
            connection.rollback();
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return resultCount;
    }
}
