package pl.lublin.wsei.java.cwiczenia;

import java.sql.*;
import java.util.Properties;

public class MyDB {
    private String host;
    private Number port;
    private String dbName;
    private String user;
    private String password;

    private Connection conn = null;
    private Statement statement = null;

    public MyDB(String host, Number port, String dbName) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void connect(){
        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", password);
        connectionProps.put("serverTimeZone", "Europe/Warsaw");

        String jdbcString = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        try {
            conn = DriverManager.getConnection(
                    jdbcString, connectionProps);
            statement = conn.createStatement();
        } catch (SQLException e){
            System.out.println("Błąd podłączenia do bazy " + dbName);
            System.out.println("Komunikat błędu: " + e.getMessage());

            conn = null;
        }
        System.out.println("Connected to database");
    }

    public Connection getConnection(){
        if(conn == null) connect();
        return conn;
    }
    
    public void closeConnection(){
        if(conn != null){
            try{
                conn.close();
            } catch (SQLException e) {
                System.out.println("Błąd przy zamykaniu połączenia bazdanowego: " + e.getMessage());
            }
        }
        conn = null;
    }

    public ResultSet selectData(String selectStatement){
        if((conn != null) && (statement != null)) {
            try {
                return statement.executeQuery(selectStatement);
            } catch (SQLException e) {
                System.out.println("Błąd realizacji zapytania " + selectStatement + ": " + e.getMessage());
            }
        }
        return null;
    }
}
