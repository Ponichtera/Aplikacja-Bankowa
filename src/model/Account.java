package model;

import com.google.gson.Gson;
import org.omg.PortableInterceptor.ACTIVE;

import java.io.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static model.Account.Type.CURRENT;
import static model.Account.Type.SAVINGS;

public class Account {
    private String number, name, cardNumber;
    private Account.Type type;
    private BigDecimal balance;
    private int availablePercent;

    public Account(int id) {
    }

    public Account(String number, String name, Account.Type type, BigDecimal balance, int availablePercent, String cardNumber) {
        this.number = number;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.availablePercent = availablePercent;
        this.cardNumber=cardNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {

        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getAvailablePercent() {
        return availablePercent;
    }

    public void setAvailablePercent(int availablePercent) {
        this.availablePercent = availablePercent;
    }

    public static List<Account> getAccountForUser(int id) {
//        ResultSet rs = DatabaseHandler.getInstance().execQuery("SELECT * FROM ACCOUNT");
        ResultSet rs = DatabaseHandler.getInstance().execQuery("SELECT * FROM ACCOUNT WHERE userID=" + id);
        List<Account> list = new ArrayList<>();

        try {
            while (rs.next()) {
                System.out.println();
                Type type;

                switch(rs.getString("type")) {
                    case "CURRENT":
                        type = CURRENT;
                        break;
                    case "SAVINGS":
                        type = SAVINGS;
                        break;
                    default: type = CURRENT;
                }

                list.add(new Account(
                        rs.getString("number"),
                        rs.getString("name"),
                        type,
                        rs.getBigDecimal("balance"),
                        rs.getInt("percent"),
                        rs.getString("cardNumber")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    enum Type {
        CURRENT,
        SAVINGS;

        public String toString() {
            switch (this) {
                case CURRENT:
                    return "CURRENT";
                case SAVINGS:
                    return "SAVINGS";
            }
            return null;
        }
    }
}
