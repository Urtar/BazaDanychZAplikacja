package BazaDanychZAplikacja;

import java.sql.*;
import java.util.Arrays;

public class Exercises {
    public int id;
    public String name;

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void saveToDB() {
        if (this.id == 0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String insertQuery = "insert into exercises (name) values(?);";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, this.name);
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Exercises [] loadAllExercises() {
        Exercises [] allExercises = new Exercises[0];
        try (Connection connection = DatabaseConnection.getConnection()) {
            String selectAllUsersQuery = "select * from exercises;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsersQuery);
            while(resultSet.next()) {
                Exercises exercises = new Exercises();
                exercises.name = resultSet.getString("name");
                exercises.id = resultSet.getInt("id");
                allExercises = Arrays.copyOf(allExercises, allExercises.length+1);
                allExercises[allExercises.length-1] = exercises;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allExercises;
    }

    static public Exercises findExerciseById(int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM exercises where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Exercises loadedExercises = new Exercises();
                loadedExercises.id = resultSet.getInt("id");
                loadedExercises.name = resultSet.getString("name");
                return loadedExercises;}}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;}

    public void delete() {
        if (this.id != 0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM exercises WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, this.id);
                preparedStatement.executeUpdate();
                this.id = 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Exercises{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
