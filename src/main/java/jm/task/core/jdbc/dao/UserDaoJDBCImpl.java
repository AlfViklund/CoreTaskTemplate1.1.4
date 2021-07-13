package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();
    private Statement statement;
    private List<User> allUsers = new ArrayList<>();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try{
            statement = this.connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users(" +
                    "id INTEGER not NULL AUTO_INCREMENT PRIMARY KEY," +
                    " name VARCHAR(50)," +
                    " lastName VARCHAR(50)," +
                    " age INT(3))");
        } catch (SQLException ex) {
        }
    }

    public void dropUsersTable() {
        try{
            statement = this.connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException ex) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            statement = this.connection.createStatement();
            boolean isAdded = statement.execute("INSERT INTO users(name,lastname,age) VALUES ('"+name+"', '"+lastName+"', '"+age+"')");
            if (isAdded) {
                System.out.println("User с именем – "+name+" добавлен в базу данных");
            }
        } catch (SQLException ex) {
        }
    }

    public void removeUserById(long id) {
        try {
            statement = this.connection.createStatement();
            int rows = statement.executeUpdate("DELETE FROM users WHERE Id = "+id+"");
            System.out.printf("Удалено %d строк", rows);
        } catch (SQLException ex) {
        }
    }

    public List<User> getAllUsers() {
        try {

            statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                allUsers.add(new User(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        (byte)resultSet.getInt(4)));
            }
        } catch (SQLException ex) {
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try {
            statement = this.connection.createStatement();
            int rows = statement.executeUpdate("DELETE FROM users;");
            System.out.printf("Очищено %d строк", rows);
        } catch (SQLException ex) {
        }
    }
}
