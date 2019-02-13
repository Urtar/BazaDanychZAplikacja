package BazaDanychZAplikacja;

import java.sql.*;
import java.util.Arrays;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;

//    gettery i setterey

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return this.id;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.setPassword(password);
    }

    public User() {
    }

    /**
     * operacje bazodanowe
     **/

    public void saveToDB() {
        if (this.id == 0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String insertQuery = "insert into user(email, password, username) values(?,?,?);";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, this.email);
                preparedStatement.setString(2, this.password);
                preparedStatement.setString(3, this.username);
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

    static public User findUserById(int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM user where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User loadedUser = new User();
                loadedUser.id = resultSet.getInt("id");
                loadedUser.username = resultSet.getString("username");
                loadedUser.password = resultSet.getString("password");
                loadedUser.email = resultSet.getString("email");
                return loadedUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateToDB() {
        if (this.id != 0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String insertQuery = "UPDATE user SET username=?, email=?, password=? where id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, this.username);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                preparedStatement.setInt(4, this.id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static User[] loadAllUsers() {
        User[] allUsers = new User[0];
        try (Connection connection = DatabaseConnection.getConnection()) {
            String selectAllUsersQuery = "select * from user;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllUsersQuery);
            while (resultSet.next()) {
                User user = new User();
                user.id = resultSet.getInt("id");
                user.email = resultSet.getString("email");
                user.username = resultSet.getString("username");
                allUsers = Arrays.copyOf(allUsers, allUsers.length + 1);
                allUsers[allUsers.length - 1] = user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void delete() {
        if (this.id != 0) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM user WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, this.id);
                preparedStatement.executeUpdate();
                this.id = 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getGroupNameOfUser () {
        try (Connection connection = DatabaseConnection.getConnection()) {
        String selectGroupIdOfUser = "select * from user join group_tab on user_id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectGroupIdOfUser);
        preparedStatement.setInt(1, this.id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            String group_name = resultSet.getString("group_name");
           return group_name;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return null;
    }

    public User[] loadAllByGroupId() {
        String group_name = getGroupNameOfUser();
        User [] allUserInGroup = new User[0];
        try (Connection connection = DatabaseConnection.getConnection()) {
            String selectAllUsersQuery = "select * from group_tab join user on user.id= group_tab.user_id where group_name = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllUsersQuery);
            preparedStatement.setString(1, group_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Users in group name " + group_name + " :");
            while (resultSet.next()) {
                User usersInGroup = new User();
                usersInGroup.id = resultSet.getInt("id");
                usersInGroup.username = resultSet.getString("username");
                usersInGroup.password = resultSet.getString("password");
                usersInGroup.email = resultSet.getString("email");
                allUserInGroup = Arrays.copyOf(allUserInGroup, allUserInGroup.length + 1);
                allUserInGroup[allUserInGroup.length - 1] = usersInGroup;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUserInGroup;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' + '\n' +
                '}';
    }
}
