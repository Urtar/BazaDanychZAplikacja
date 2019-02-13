package BazaDanychZAplikacja;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main1 {

    public static void main(String[] args) {
//        User user = new User();
//        user.setEmail("user3@email.pl");
//        user.setPassword("admin123");
//        user.setUsername("UserName4");
//        user.saveToDB();
        User user1 = User.findUserById(1);
//        user1.setUsername("UserName4");
//        user1.updateToDB();
//        System.out.println(Arrays.toString(User.loadAllUsers()));
//        user1.delete();
        System.out.println(Arrays.toString(User.loadAllUsers()));
//        Group_tab group_tab = new Group_tab();
//        group_tab.setUser_id(5);
//        group_tab.setGroup_name("Grupa1");
//        group_tab.saveToDB();
        System.out.println(Arrays.toString(Group_tab.loadAllGroups()));
        System.out.println(Arrays.toString(user1.loadAllByGroupId()));
    }
}
