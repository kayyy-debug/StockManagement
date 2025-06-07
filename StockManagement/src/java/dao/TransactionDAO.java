package dao;

import dto.TransactionDTO;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public List<TransactionDTO> getAllTransactions() throws SQLException {
        List<TransactionDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblTransactions ORDER BY transDate DESC";

        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new TransactionDTO(
                        rs.getInt("transID"),
                        rs.getString("userID"),
                        rs.getString("ticker"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("transDate")
                ));
            }
        }
        return list;
    }

    public List<TransactionDTO> getTransactionsByUser(String userID) throws SQLException {
        List<TransactionDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblTransactions WHERE userID = ? ORDER BY transDate DESC";

        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new TransactionDTO(
                            rs.getInt("transID"),
                            rs.getString("userID"),
                            rs.getString("ticker"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getTimestamp("transDate")
                    ));
                }
            }
        }
        return list;
    }

    public TransactionDTO getTransactionByID(int transID) throws SQLException {
        String sql = "SELECT * FROM tblTransactions WHERE transID = ?";
        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TransactionDTO(
                            rs.getInt("transID"),
                            rs.getString("userID"),
                            rs.getString("ticker"),
                            rs.getDouble("price"),
                            rs.getInt("quantity"),
                            rs.getTimestamp("transDate")
                    );
                }
            }
        }
        return null;
    }

    public boolean insertTransaction(TransactionDTO trans) throws SQLException {
        String sql = "INSERT INTO tblTransactions (userID, ticker, price, quantity) VALUES (?, ?, ?, ?)";
        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trans.getUserID());
            ps.setString(2, trans.getTicker());
            ps.setDouble(3, trans.getPrice());
            ps.setInt(4, trans.getQuantity());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateTransaction(TransactionDTO trans) throws SQLException {
        String sql = "UPDATE tblTransactions SET ticker=?, price=?, quantity=? WHERE transID = ? AND userID = ?";
        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trans.getTicker());
            ps.setDouble(2, trans.getPrice());
            ps.setInt(3, trans.getQuantity());
            ps.setInt(4, trans.getTransID());
            ps.setString(5, trans.getUserID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteTransaction(int transID, String userID) throws SQLException {
        String sql = "DELETE FROM tblTransactions WHERE transID = ? AND userID = ?";
        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transID);
            ps.setString(2, userID);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean checkStockInTransaction(String ticker) throws Exception {
        boolean inUse = false;
        String sql = "SELECT COUNT(*) FROM tblTransactions WHERE ticker = ?";
        try ( Connection con = DBUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ticker);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    inUse = true;
                }
            }
        }
        return inUse;
    }

    public boolean checkUserInTransaction(String userID) throws Exception {
        boolean inUse = false;
        String sql = "SELECT COUNT(*) FROM tblTransactions WHERE userID = ?";
        try ( Connection con = DBUtil.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userID);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    inUse = true;
                }
            }
        }
        return inUse;
    }

}
