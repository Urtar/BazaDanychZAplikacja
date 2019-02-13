package BazaDanychZAplikacja;

import java.sql.*;
import java.util.Arrays;

public class Group_tab {
    public int id;
    public String group_name;
    public int user_id;

    public int getId() {
        return id;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public int getUser_id() {
        return user_id;
    }
    public void saveToDB() {
        if (this.id == 0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String insertQuery = "insert into group_tab(group_name, user_id) values(?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, this.group_name);
                preparedStatement.setInt(2, this.user_id);
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

    public static Group_tab [] loadAllGroups() {
        Group_tab [] allGroups = new Group_tab[0];
        try (Connection connection = DatabaseConnection.getConnection()) {
            String selectAllUsersQuery = "select * from group_tab;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsersQuery);
            while(resultSet.next()) {
                Group_tab group_tab = new Group_tab();
                group_tab.id = resultSet.getInt("id");
                group_tab.group_name = resultSet.getString("group_name");
                group_tab.user_id = resultSet.getInt("user_id");
                allGroups = Arrays.copyOf(allGroups, allGroups.length+1);
                allGroups[allGroups.length-1] = group_tab;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allGroups;
    }

    static public Group_tab findGroupById(int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM group_tab where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Group_tab loadedGroup_tab = new Group_tab();
                loadedGroup_tab.id = resultSet.getInt("id");
                loadedGroup_tab.group_name = resultSet.getString("group_name");
                return loadedGroup_tab;}}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;}

    public void delete() {
        if (this.id != 0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM group_tab WHERE id=?";
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
        return "Group_tab{" +
                "id=" + id +
                ", group_name='" + group_name + '\'' +
                ", user_id=" + user_id + "\n" +
                '}';
    }
}
