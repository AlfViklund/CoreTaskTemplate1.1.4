package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS users(" +
                        "id BIGINT not NULL AUTO_INCREMENT PRIMARY KEY," +
                        " name VARCHAR(50)," +
                        " lastName VARCHAR(50)," +
                        " age TINYINT(3))");
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
            }
        } catch (SQLException ex) {
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS users");
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
            }
        } catch (SQLException ex) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection()) {
            String sql = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, age);
                preparedStatement.executeUpdate();
                Statement statement = connection.createStatement();
                connection.commit();
                System.out.println("User с именем – "+name+" добавлен в базу данных");
            } catch (SQLException ex) {
                connection.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection()) {
            String sql = "DELETE FROM users WHERE Id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                connection.commit();
                System.out.printf("Удален %d id", id);
            } catch (SQLException ex) {
                connection.rollback();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try(Connection connection = Util.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
                while (resultSet.next()) {
                    allUsers.add(new User(resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            (byte) resultSet.getInt(4)));
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
            }
        } catch(SQLException ex){
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.executeUpdate("truncate users");
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
            }
        } catch (SQLException ex) {
        }
    }
}

