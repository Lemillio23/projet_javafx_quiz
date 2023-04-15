package com.example.authentification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private final Connection connection;

    public AdminDAO() {
        connection = Database.getConnection();
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();

        String query = "SELECT * FROM admins";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Admin admin = new Admin(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
                admins.add(admin);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return admins;
    }

    public int addAdmin(Admin admin) {
        int rowsInserted = 0;

        String query = "INSERT INTO admins (name, email, password) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getEmail());
            statement.setString(3, admin.getPassword());

            rowsInserted = statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rowsInserted;
    }

    public int updateAdmin(Admin admin) {
        int rowsUpdated = 0;

        String query = "UPDATE admins SET name = ?, email = ?, password = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getEmail());
            statement.setString(3, admin.getPassword());
            statement.setInt(4, admin.getId());

            rowsUpdated = statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rowsUpdated;
    }

    public int deleteAdmin(Admin admin) {
        int rowsDeleted = 0;

        String query = "DELETE FROM admins WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, admin.getId());

            rowsDeleted = statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rowsDeleted;
    }

}