package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    @Override
    public void createUsersTable() {
        String command = "CREATE TABLE tableuser " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Таблица существует");
        }
    }

    public void dropUsersTable() {
        String command = "DROP TABLE tableuser";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.executeUpdate();
        } catch (SQLException ignored){

        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String command = "INSERT INTO tableuser(name,lastname,age) VALUES (?,?,?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем -" + name +" добавлен в базу данных");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String command = "DELETE FROM tableuser WHERE (id = ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> alluser = new ArrayList<>();
        String command ="select ID,NAME,LASTNAME,AGE FROM tableuser;";
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                alluser.add(user);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return alluser;
    }

    public void cleanUsersTable() {
        String command = "TRUNCATE tableuser;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)){
            preparedStatement.executeUpdate();
        } catch (SQLException ignored){

        }
    }
}
