package model;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class User {
    private String name, login, password;
    private User.Lang lang;
    public Account[] accounts;

    public User(int id, Account ... accounts) {
        ResultSet rs = DatabaseHandler.getInstance().execQuery("SELECT * FROM MEMBER WHERE id=" + id);
        try {
            if (rs.next()) {
                name = rs.getString("name");
                login = rs.getString("login");
                password = rs.getString("password");

                switch(rs.getString("language")){
                    case "pl_PL" :
                        lang = Lang.pl_PL ;
                        break;
                    case "en_UK" :
                        lang = Lang.en_UK ;
                        break;
                    case "fr_FR" :
                        lang = Lang.fr_FR ;
                        break;
                    case "ru_RU" :
                        lang = Lang.ru_RU ;
                        break;
                    default: lang = Lang.en_UK;
                }
                this.accounts = accounts;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public User(String name, User.Lang lang, String login, String password, Account ... accounts) {
        this.name = name;
        this.lang = lang;
        this.accounts = accounts;
        setLogin(login);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public String getLang() {
        return lang.toString();
    }

    private static final String DATA_FILE = "data.txt";

    public void setPassword(String password) {
        String pass = DigestUtils.sha1Hex(password);
        this.password = pass;
    }

    public void setLogin(String login) {
        String log = DigestUtils.sha1Hex(login);
        this.login = log;
    }

//    public static void saveUser (User user) {
//
//        try (Writer writer = new FileWriter(DATA_FILE)) {
//            Gson gson = new Gson();
//            gson.toJson(user, writer);
//
//        } catch (IOException e) {
//            System.out.println("Exception in initConfig()\n");
//            e.printStackTrace();
//        }
//    }

//    public static User getUser() {
//        User user = new User();
//        try(Reader reader = new FileReader(DATA_FILE)) {
//            Gson gson = new Gson();
//            user = gson.fromJson(reader, User.class);
//
//        }catch (IOException e) {
//            System.out.println("File 'config.txt' not found, creating file.");
//            user.initConfig();
//        }
//        return user;
//    }

    private void initConfig() {


    }

    enum Lang {
        pl_PL,
        en_UK,
        fr_FR,
        ru_RU;

        public String toString(){
            switch(this){
                case pl_PL :
                    return "pl-PL";
                case en_UK :
                    return "en-UK";
                case fr_FR :
                    return "fr-FR";
                case ru_RU :
                    return "ru-RU";
            }
            return null;
        }
    }
}