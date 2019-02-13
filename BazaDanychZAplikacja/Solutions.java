package BazaDanychZAplikacja;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Arrays;

public class Solutions {
    public int id;
    public int task_id;
    public Date resolved_on;
    public int user_id;

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public void setResolved_on(Date resolved_on) {
        this.resolved_on = resolved_on;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public int getTask_id() {
        return task_id;
    }

    public Date getResolved_on() {
        return resolved_on;
    }

    public int getUser_id() {
        return user_id;
    }
    public void saveToDB() {
        if (this.id == 0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String insertQuery = "insert into soultions(task_id, resolved_on, user_id) values(?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,this.task_id);
                preparedStatement.setDate(2, this.resolved_on);
                preparedStatement.setInt(3, this.user_id);
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

    public static Solutions [] loadAllSolutions() {
        Solutions [] allSolutions = new Solutions[0];
        try (Connection connection = DatabaseConnection.getConnection()) {
            String selectAllUsersQuery = "select * from solutions;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsersQuery);
            while(resultSet.next()) {
                Solutions solutions = new Solutions();
                solutions.id = resultSet.getInt("id");
                solutions.task_id = resultSet.getInt("task_id");
                solutions.resolved_on = resultSet.getDate("resolved_on");
                solutions.user_id = resultSet.getInt("user_id");
                allSolutions = Arrays.copyOf(allSolutions, allSolutions.length+1);
                allSolutions[allSolutions.length-1] = solutions;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allSolutions;
    }

    static public Solutions findGroupById(int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM solutions where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Solutions loadedSolutions = new Solutions();
                loadedSolutions.id = resultSet.getInt("id");
                loadedSolutions.task_id = resultSet.getInt("task_id");
                loadedSolutions.resolved_on = resultSet.getDate("resolved_on");
                loadedSolutions.user_id = resultSet.getInt("user_id");
                return loadedSolutions;}}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;}

    public void delete() {
        if (this.id != 0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM solutions WHERE id=?";
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
        return "Solutions{" +
                "id=" + id +
                ", task_id=" + task_id +
                ", resolved_on=" + resolved_on +
                ", user_id=" + user_id +
                '}';
    }

    public Solutions [] loadAllByUserId() {
        Solutions [] allUserSolutions = new Solutions[0];
        try (Connection connection = DatabaseConnection.getConnection()) {
            String selectAllUsersQuery = "select * from solutions where user_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllUsersQuery);
            preparedStatement.setInt(1, this.user_id);
            ResultSet resultSet = preparedStatement.executeQuery(selectAllUsersQuery);
            while(resultSet.next()) {
                Solutions solutionsOfUser = new Solutions();
                solutionsOfUser.id = resultSet.getInt("id");
                solutionsOfUser.task_id = resultSet.getInt("task_id");
                solutionsOfUser.resolved_on = resultSet.getDate("resolved_on");
                solutionsOfUser.user_id = resultSet.getInt("user_id");
                allUserSolutions = Arrays.copyOf(allUserSolutions, allUserSolutions.length+1);
                allUserSolutions[allUserSolutions.length-1] = solutionsOfUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUserSolutions;
    }

    public Solutions [] loadAllByExerciseId() {
        Solutions [] allSolutionsOfExecise = new Solutions[0];
        try (Connection connection = DatabaseConnection.getConnection()) {
            String selectAllUsersQuery = "select * from solutions where task_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllUsersQuery);
            preparedStatement.setInt(1, this.task_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Solutions solutionsOfExercise = new Solutions();
                solutionsOfExercise.id = resultSet.getInt("id");
                solutionsOfExercise.task_id = resultSet.getInt("task_id");
                solutionsOfExercise.resolved_on = resultSet.getDate("resolved_on");
                solutionsOfExercise.user_id = resultSet.getInt("user_id");
                allSolutionsOfExecise = Arrays.copyOf(allSolutionsOfExecise, allSolutionsOfExecise.length+1);
                allSolutionsOfExecise[allSolutionsOfExecise.length-1] = solutionsOfExercise;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allSolutionsOfExecise;
    }
}
