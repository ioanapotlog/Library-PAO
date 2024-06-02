package service.impl;

import model.User;
import service.UserService;

import java.sql.*;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class UserServiceImpl implements UserService {

    // CREATE
    @Override
    public long addUser(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;
        long userId = -1; // Default value if user insertion fails
        try {
            Scanner scanner = new Scanner(System.in);

            // Prompt the user for input
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            // Prepare the SQL INSERT statement
            String insertSql = "INSERT INTO USERS (LAST_NAME, FIRST_NAME, PHONE_NUMBER, EMAIL) VALUES (?, ?, ?, ?)";
            pstmt = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            // Set the values for the placeholders in the SQL statement
            pstmt.setString(1, lastName);
            pstmt.setString(2, firstName);
            pstmt.setString(3, phoneNumber);
            pstmt.setString(4, email);

            // Execute the insert statement
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Retrieve the generated keys
                generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    userId = generatedKeys.getLong(1); // Get the generated user ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the ResultSet and PreparedStatement
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userId;
    }

    // READ
    @Override
    public long findUserByEmail(Connection con, String email) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long userId = -1;
        try {
            String selectSql = "SELECT * FROM USERS WHERE EMAIL = ?";
            pstmt = con.prepareStatement(selectSql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                userId = rs.getLong("ID");
                String lastName = rs.getString("LAST_NAME");
                String firstName = rs.getString("FIRST_NAME");
                String phoneNumber = rs.getString("PHONE_NUMBER");
                System.out.println("User ID: " + userId + ", First Name: " + firstName + ", Last Name: " + lastName +
                        ", Phone Number: " + phoneNumber + ", Email: " + email);
            } else {
                System.out.println("No user found with the given email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userId;
    }

    @Override
    public void findUserById(Connection con, long userId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String selectSql = "SELECT * FROM USERS WHERE ID = ?";
            pstmt = con.prepareStatement(selectSql);
            pstmt.setLong(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String lastName = rs.getString("LAST_NAME");
                String firstName = rs.getString("FIRST_NAME");
                String phoneNumber = rs.getString("PHONE_NUMBER");
                String email = rs.getString("EMAIL");
                System.out.println("User ID: " + userId);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Phone Number: " + phoneNumber);
                System.out.println("Email: " + email);
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // UPDATE
    @Override
    public void updateUserbyEmail(Connection con, String email) {
        PreparedStatement pstmt = null;
        try {
            Scanner scanner = new Scanner(System.in);
            // Prompt the user for input (new data for the doctor)
            System.out.print("Enter new first name: ");
            String newFirstName = scanner.nextLine();
            System.out.print("Enter new last name: ");
            String newLastName = scanner.nextLine();
            System.out.print("Enter new phone number: ");
            String newPhoneNumber = scanner.nextLine();
            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine();

            String updateSql = "UPDATE USERS SET FIRST_NAME = ?, LAST_NAME = ?, PHONE_NUMBER = ?, EMAIL = ? WHERE EMAIL = ?";
            pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, newFirstName);
            pstmt.setString(2, newLastName);
            pstmt.setString(3, newPhoneNumber);
            pstmt.setString(4, newEmail);
            pstmt.setString(5, email);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("No user found with the given email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // DELETE
    @Override
    public void deleteUserByEmail(Connection con, String email) {
        PreparedStatement pstmt = null;
        try {
            String deleteSql = "DELETE FROM USERS WHERE EMAIL = ?";
            pstmt = con.prepareStatement(deleteSql);
            pstmt.setString(1, email);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("No user found with the given email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // SHOW ALL USERS
    @Override
    public void showUsers(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String showSQL = "SELECT * FROM USERS";
            pstmt = con.prepareStatement(showSQL);
            rs = pstmt.executeQuery();

            Set<User> userSet = new TreeSet<>();

            while (rs.next()) {
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                String phoneNumber = rs.getString("PHONE_NUMBER");
                String email = rs.getString("EMAIL");

                userSet.add(new User(firstName, lastName, phoneNumber, email));
            }

            // Print sorted users
            System.out.println("List of Users:");
            for (User user : userSet) {
                System.out.println(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean userExists(Connection con, String phoneNumber) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            String selectSql = "SELECT 1 FROM USERS WHERE PHONE_NUMBER = ?";
            pstmt = con.prepareStatement(selectSql);
            pstmt.setString(1, phoneNumber);
            rs = pstmt.executeQuery();
            exists = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exists;
    }

    @Override
    public long getUserIdByPhoneNumber(Connection con, String phoneNumber) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long userId = -1;  // Default value indicating user not found

        try {
            String query = "SELECT ID FROM USERS WHERE PHONE_NUMBER = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, phoneNumber);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                userId = rs.getLong("ID");
            } else {
                System.out.println("User with phone number " + phoneNumber + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userId;
    }
}
