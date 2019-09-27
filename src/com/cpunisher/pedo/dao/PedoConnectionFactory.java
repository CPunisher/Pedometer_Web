package com.cpunisher.pedo.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PedoConnectionFactory implements IConnectionFactory {

    private Properties properties;

    public PedoConnectionFactory() {
        properties = new Properties();

        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("properties/jdbc.properties"));
            Class.forName(properties.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("url"), properties);
    }
}
