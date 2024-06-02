package service;

import java.sql.Connection;

public interface UserService {
    public long addUser(Connection con);

    public long findUserByEmail(Connection con, String email);

    public void findUserById(Connection con, long userId);

    public void updateUserbyEmail(Connection con, String email);

    public void deleteUserByEmail(Connection con, String email);

    public void showUsers(Connection con);

    public boolean userExists(Connection con, String phoneNumber);

    public long getUserIdByPhoneNumber(Connection con, String phoneNumber);
}
