package model;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
        import javax.swing.*;

public class DatabaseHandler {

    private static DatabaseHandler handler;

    private static final String DB_URL = "jdbc:derby:database";
    private static Connection connection = null;
    private static Statement statement = null;

    private DatabaseHandler() {
        connection = createConnection(DB_URL);

//        dropTable("ACCOUNT");
//        dropTable("MEMBER");

        setupUserTable(connection);
        setupAccountTable(connection);
    }

    public static DatabaseHandler getInstance() {
        if (handler == null)
            handler = new DatabaseHandler();
        return handler;
    }

    private Connection createConnection(String DB_URL) {
        try {
            System.out.println("Connecting to database " + DB_URL + ";create=true");
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            Connection connection = DriverManager.getConnection(DB_URL+";create=true");

            System.out.println("Connection successful!!!");
            return connection;

        } catch (Exception e) {
            // SQL State XJO15 and SQLCode 50000 mean an OK shutdown.
//            SQLException sqlException = (SQLException) e;
//            if (!(sqlException.getErrorCode() == 50000) && (sqlException.getSQLState().equals("XJ015")))
            {

                System.out.println(" ----------------- Error while connecting to database " + DB_URL + " ----------------- ");
                System.err.println(e);
            }
            return null;
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            statement = connection.createStatement();
            System.out.println(query);
            result = statement.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler " + ex.getLocalizedMessage());
            System.out.println(query);

            return null;
        }
        return result;
    }

    public boolean execAction(String query) {
        try {
            statement = connection.createStatement();
            System.out.println(query);
            statement.execute(query);
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error Occurred ", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler " + ex.getLocalizedMessage());
            System.out.println(query);
            return false;
        }
    }

    private void setupUserTable(Connection connection) {
        try {
            statement = connection.createStatement();

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "MEMBER", null);

            if (tables.next())
                System.out.println("Table MEMBER already exists. Ready to go!");
            else {
                statement.execute("CREATE TABLE MEMBER ("
                        + " id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
                        + "	name VARCHAR(100),\n"
                        + "	login VARCHAR(50),\n"
                        + "	password VARCHAR(50),\n"
                        + "	language VARCHAR(5),\n"
                        + " CONSTRAINT member_PK PRIMARY KEY (id))");
                System.out.println("Table MEMBER successfully created.");
                fillUserTable();
                System.out.println("Table MEMBER successfully filled. Ready to go!");
            }
        } catch (SQLException e) {
            System.out.println(" ----------------- Error in 'setupUserTable()' ----------------- ");
            System.err.println(e.getMessage());
            System.out.println(" --------------------------------------------- ----------------- ");
        }
    }

    private void setupAccountTable(Connection connection) {
        try {
            statement = connection.createStatement();

            DatabaseMetaData dmd = connection.getMetaData();
            ResultSet tables = dmd.getTables(null, null, "ACCOUNT", null);
            if (tables.next())
                System.out.println("Table ACCOUNT already exists. Ready to go!");
            else {
                statement.execute("CREATE TABLE ACCOUNT ("
                        + " userID INTEGER NOT NULL ,"
                        + " name VARCHAR(32),\n"
                        + " number VARCHAR(32),\n"
                        + " type VARCHAR(10),\n"
                        + " balance DECIMAL(19,2),\n"
                        + " percent INTEGER DEFAULT 1000,"
                        + " cardNumber VARCHAR(32),"
                        + " FOREIGN KEY (userID) REFERENCES MEMBER(id) )");
                fillAccountTable();
                System.out.println("Table ACCOUNT successfully created. Ready to go!");
            }
        } catch (SQLException e) {
            System.out.println(" ----------------- Error in 'setupAccountTable()' ----------------- ");
            System.err.println(e.getMessage());
            System.out.println(" ------------------------------------------------------------------ ");
        }
    }

    private void fillUserTable() {
        try {
            String login1 = "login1";
            String pass1 = "1234";
            String login2 = "login2";
            String pass2 = "0000";

            statement = connection.createStatement();
            statement.execute("INSERT INTO MEMBER (name, login, password, language) VALUES ( 'Adam Nowak', '" + DigestUtils.sha1Hex(login1) + "', '" + DigestUtils.sha1Hex(pass1) + "', 'pl_PL')");
            statement.execute("INSERT INTO MEMBER (name, login, password, language) VALUES ( 'John Winston', '" + DigestUtils.sha1Hex(login2) + "', '" + DigestUtils.sha1Hex(pass2) + "', 'en_UK')");

        } catch (SQLException e) {
            System.out.println(" ----------------- Error while setup Demo Database --------------- ");
            System.out.println(" ----------------- fillUserTable()-------------------------------- ");
            System.err.println(e.getMessage());
            System.out.println(" ----------------------------------------------------------------- ");
        }
    }

    private void fillAccountTable() {
        try {
            statement = connection.createStatement();
            statement.execute("INSERT INTO ACCOUNT VALUES (" +
                    "1, 'Moje konto', '49 1140 2204 0000 3802 0105 2638', '" + Account.Type.CURRENT + "', 1812.32, 1000, '4814 **** **** 4785')");

            statement.execute("INSERT INTO ACCOUNT VALUES (" +
                    "1, 'Oszczędnościowe', '62 1140 2004 0000 3402 0160 9768', '" + Account.Type.SAVINGS + "', 2500.00, 5000, '')");

            statement.execute("INSERT INTO ACCOUNT VALUES (" +
                    "2, 'Money to spend', '12 7894 0001 0000 1970 0001 1471', '" + Account.Type.SAVINGS + "', 15794.11, 10000, '6874 **** **** 1147')");

            statement.execute("INSERT INTO ACCOUNT VALUES (" +
                    "2, 'Piggy bank', '26 6516 5648 0084 0849 1684 6416', '" + Account.Type.SAVINGS + "', 50000.00, 100000, '')");

        } catch (SQLException e) {
            System.out.println(" ----------------- Error while setup Demo Database --------------- ");
            System.out.println(" ----------------- fillAccountTable() ---------------------------- ");
            System.err.println(e.getMessage());
            System.out.println(" ----------------------------------------------------------------- ");
        }
    }

    public boolean closeConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL + ";shutdown=true");
            connection.close();
            System.out.println("Connection closed");
            return true;
        } catch (SQLException sqlException) {
            // SQL State XJO15 and SQLCode 50000 mean an OK shutdown.
            if (!(sqlException.getErrorCode() == 50000) && (sqlException.getSQLState().equals("XJ015"))) {

                System.out.println("Error while connecting to database " + DB_URL);
                System.err.println(sqlException);

            }
        }
        return false;
    }

    private boolean dropTable(String table) {
        String query = "DROP TABLE " + table;
        boolean result = execAction(query);

        System.out.println(result ? table + " Deleted." : "Error occurred while deleting table: " + table);
        return result;
    }
}

