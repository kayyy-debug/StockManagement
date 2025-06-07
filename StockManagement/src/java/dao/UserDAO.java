package dao;

import dto.UserDTO;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public UserDTO checkLogin(String userID, String password) throws SQLException {
        String sql = "SELECT * FROM tblUsers WHERE userID = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserDTO(
                        rs.getString("userID"),
                        rs.getString("fullName"),
                        rs.getString("roleID"),
                        rs.getString("password")
                    );
                }
            }
        }
        return null;
    }

    public boolean createUser(UserDTO user) throws SQLException {
        String sql = "INSERT INTO tblUsers (userID, fullName, roleID, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserID());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getRoleID());
            ps.setString(4, user.getPassword());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateUser(UserDTO user) throws SQLException {
        String sql = "UPDATE tblUsers SET fullName = ?, roleID = ?, password = ? WHERE userID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getRoleID());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(String userID) throws SQLException {
        String sql = "DELETE FROM tblUsers WHERE userID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            return ps.executeUpdate() > 0;
        }
    }

    public List<UserDTO> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM tblUsers";
        List<UserDTO> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new UserDTO(
                    rs.getString("userID"),
                    rs.getString("fullName"),
                    rs.getString("roleID"),
                    rs.getString("password")
                ));
            }
        }
        return list;
    }

    public UserDTO getUserByID(String userID) throws SQLException {
        String sql = "SELECT * FROM tblUsers WHERE userID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserDTO(
                        rs.getString("userID"),
                        rs.getString("fullName"),
                        rs.getString("roleID"),
                        rs.getString("password")
                    );
                }
            }
        }
        return null;
    }
}
