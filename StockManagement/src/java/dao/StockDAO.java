package dao;

import dto.StockDTO;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO {

    public List<StockDTO> getAllStocks() throws SQLException {
        List<StockDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblStocks ORDER BY ticker";

        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new StockDTO(
                        rs.getInt("stockID"),
                        rs.getString("ticker"),
                        rs.getString("companyName"),
                        rs.getDouble("price"),
                        rs.getBoolean("status")
                ));
            }
        }
        return list;
    }

    public StockDTO getStockByTicker(String ticker) throws SQLException {
        String sql = "SELECT * FROM tblStocks WHERE ticker = ?";
        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticker);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new StockDTO(
                            rs.getInt("stockID"),
                            rs.getString("ticker"),
                            rs.getString("companyName"),
                            rs.getDouble("price"),
                            rs.getBoolean("status")
                    );
                }
            }
        }
        return null;
    }

    public boolean insertStock(StockDTO stock) throws SQLException {
        String sql = "INSERT INTO tblStocks (ticker, companyName, price, status) VALUES (?, ?, ?, ?)";
        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, stock.getTicker());
            ps.setString(2, stock.getCompanyName());
            ps.setDouble(3, stock.getPrice());
            ps.setBoolean(4, stock.isStatus());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateStock(StockDTO stock) throws SQLException {
        String sql = "UPDATE tblStocks SET companyName = ?, price = ?, status = ? WHERE ticker = ?";
        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, stock.getCompanyName());
            ps.setDouble(2, stock.getPrice());
            ps.setBoolean(3, stock.isStatus());
            ps.setString(4, stock.getTicker());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteStock(String ticker) throws SQLException {
        String sql = "DELETE FROM tblStocks WHERE ticker = ?";
        try ( Connection conn = DBUtil.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticker);
            return ps.executeUpdate() > 0;
        }
    }

}
