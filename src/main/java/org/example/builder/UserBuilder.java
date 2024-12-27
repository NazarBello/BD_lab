package org.example.builder;

import org.example.entities.Users;

import java.util.Scanner;

public class UserBuilder {

    public static Users buildUser() {
        Scanner scanner = new Scanner(System.in);
        Users user = new Users();
        System.out.println("Enter user name:");
        String userName = scanner.nextLine();
        user.setUsers_name(userName);
        return user;

    }
}
