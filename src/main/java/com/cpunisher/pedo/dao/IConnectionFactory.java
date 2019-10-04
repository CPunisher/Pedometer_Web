package com.cpunisher.pedo.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionFactory {


    Connection createConnection() throws SQLException;
}
