package com.codegym.dao.user;

import com.codegym.dao.DBConnection;
import com.codegym.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAO implements IUserDAO {
    public static final String SQL_SELECT_ALL_USER = "SELECT * FROM users ";
    public static final String SQL_SELECT_USER_BY_ID = "SELECT * from users where  id = ?";
    public static final String SQL_INSERT_USER = "INSERT into users (username,passwork,phone,email,dateOfBirth,gender,address,status,role_id) value (?,?,?,?,?,?,?,?,?)";
    public static final String SQL_UPDATE_USER_BY_ID = "update users set username =?,password =?,phone =?,email =?,dateOfBirth =?,gender=?,address = ?,status =? , role_id =?";
    public static final String SQL_DELETE_USER_BY_ID = "delete  from users where  id = ?";
    public static final String SELECT_FROM_USERS_WHERE_EMAIL = "SELECT *from users where email =?";
    private Connection connection = DBConnection.getConnection();

    @Override
    public List<User> findAll() {
        List<User> users = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USER);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = getListUserFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByID(int search) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            preparedStatement.setInt(1, search);
            ResultSet resultSet = preparedStatement.executeQuery();
            user = getListUserFromResultSet(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private List<User> getListUserFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String phone = resultSet.getString("phone");
            String email = resultSet.getString("email");
            Date dateOfBirth = resultSet.getDate("dateOfBirth");
            Boolean gender = resultSet.getBoolean("gender");
            String address = resultSet.getString("address");
            boolean status = resultSet.getBoolean("status");
            int roleId = resultSet.getInt("role_id");
            User user = new User(id, username, password, phone, email, dateOfBirth, gender, address, status);
            user.setRole_id(roleId);
            users.add(user);
        }
        return users;
    }

    @Override
    public boolean create(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setDate(5, (java.sql.Date) user.getDateOfBirth());
            preparedStatement.setBoolean(6, user.isGender());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setBoolean(8, user.isStatus());
            preparedStatement.setInt(9, user.getRole_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateById(int id, User user) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setDate(5, (java.sql.Date) user.getDateOfBirth());
            preparedStatement.setBoolean(6, user.isGender());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setBoolean(8, user.isStatus());
            preparedStatement.setInt(9, user.getRole_id());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User GetUserByEmail(String email) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_USERS_WHERE_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            user=getListUserFromResultSet(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
