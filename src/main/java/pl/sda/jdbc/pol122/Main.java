package pl.sda.jdbc.pol122;

import pl.sda.jdbc.pol122.statements.PreparedStatementExample;
import pl.sda.jdbc.pol122.statements.entites.PhoneDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
//        List<PhoneDAO> phoneDAOList = new ArrayList<>();
//        phoneDAOList.add(new PhoneDAO(6, "GOOD", "secret", "red", 330));
//        phoneDAOList.add(new PhoneDAO(7, "REQ", "hide", "yellow", 2000));
//        phoneDAOList.add(new PhoneDAO(8, "BQQ", "freshWay", "pink", 6500));
//        phoneDAOList.add(new PhoneDAO(9, "SEVE", "milkSoul", "black", 555));
//        phoneDAOList.add(new PhoneDAO(10, "SEVE", "betterCall", "gray", 894));
        int result = PreparedStatementExample.changePhonesPricesByIds(8, 18);
        System.out.println("Row count: " + result);

    }
}