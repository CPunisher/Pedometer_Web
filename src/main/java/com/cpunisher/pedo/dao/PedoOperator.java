package com.cpunisher.pedo.dao;

import com.cpunisher.pedo.pojo.PedoData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PedoOperator implements IOperator<PedoData> {

    private PedoConnectionFactory factory;

    public PedoOperator(PedoConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public int insert(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = factory.createConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {

        } finally {
            DBUtils.release(null, statement, connection);
        }

        return 0;
    }

    @Override
    public int delete(String sql, Object... args) {
        return 0;
    }

    @Override
    public int update(String sql, Object... args) {
        return insert(sql, args);
    }

    @Override
    public List<PedoData> select(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<PedoData> list = new LinkedList<>();

        try {
            connection = factory.createConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PedoData pedoData = new PedoData();
                pedoData.setStuNum(resultSet.getLong(1));
                pedoData.setName(resultSet.getString(2));
                pedoData.setSteps(resultSet.getInt(3));
                list.add(pedoData);
            }

            return list;
        } catch (SQLException e) {

        } finally {
            DBUtils.release(resultSet, statement, connection);
        }

        return list;
    }
}
