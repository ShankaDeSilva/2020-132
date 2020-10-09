package com.example.routing;

import java.sql.Connection;
import java.sql.DriverManager;

public class clsConnectionPG {
    Connection connection = null;

    public Connection connectionDB() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nyc", "postgresql", "root");
        } catch (Exception er) {
            System.err.println(er.getMessage());
        }
        return connection;
    }

    protected void current_connection(Connection con)throws Exception{
        con.close();
    }
}
