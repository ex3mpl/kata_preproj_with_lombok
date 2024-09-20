package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        //
        //        userService.saveUser("Prince", "Psycho", (byte) 1);
        //        userService.removeUserById(1);
        //        System.out.println();
        System.out.println(userService.getAllUsers());
        //        System.out.println();
        //        System.out.println(userService.getById(2));
        userService.dropUsersTable();
    }
}
