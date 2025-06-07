package dao;

import dto.AlertDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

public class AlertDAO {

    private static final String CREATE = "INSERT INTO tblAlerts(userID, ticker, threshold, direction) VALUES(?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE tblAlerts SET threshold = ?, status = ? WHERE alertID = ? AND userID = ?";
    private static final String DELETE = "DELETE FROM tblAlerts WHERE alertID = ? AND status = 'inactive'";
    private static final String READ = "SELECT * FROM tblAlerts WHERE alertID LIKE ? OR ticker LIKE ?";
    private static final String GET_ALERT_BY_ID = "SELECT * FROM tblAlerts WHERE alertID = ?";

    public List<AlertDTO> getAlertsByUser(String search, String keyword) throws SQLException, ClassNotFoundException {
        List<AlertDTO> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(READ);
                ptm.setString(1, "%" + search + "%");
                ptm.setString(2, "%" + keyword + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int alertID = rs.getInt("alertID");
                    String userID = rs.getString("userID");
                    String ticker = rs.getString("ticker");
                    float threshold = rs.getFloat("threshold");
                    String direction = rs.getString("direction");
                    String status = rs.getString("status");
                    list.add(new AlertDTO(alertID, userID, ticker, threshold, direction, status));
                }
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return list;
    }

    public boolean createAlert(AlertDTO alert) throws ClassNotFoundException, SQLException {
        boolean check = false;

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);
                ptm.setString(1, alert.getUserID());
                ptm.setString(2, alert.getTicker());
                ptm.setFloat(3, alert.getThreshold());
                ptm.setString(4, alert.getDirection());
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;

    }

    public AlertDTO getAlertById(int alertID) throws SQLException {
        AlertDTO alertDTO = null;

        Connection conn = null;//connect database
        PreparedStatement ptm = null;// set gia tri len cau query and executeQuery
        ResultSet rs = null;//nhan ve ket qua khi query duoc thuc thi xong

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALERT_BY_ID);
                ptm.setInt(1, alertID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String userID = rs.getString("userID");
                    String ticker = rs.getString("ticker");
                    float threshold = rs.getFloat("threshold");
                    String direction = rs.getString("direction");
                    String status = rs.getString("status");
                    alertDTO = new AlertDTO(alertID, userID, ticker, threshold, direction, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return alertDTO;
    }

    public boolean updateAlert(AlertDTO alert) throws ClassNotFoundException, SQLException {
        boolean check = false;

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setFloat(1, alert.getThreshold());
                ptm.setString(2, alert.getStatus());
                ptm.setInt(3, alert.getAlertID());
                ptm.setString(4, alert.getUserID());
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean deleteAlert(int alertID) throws ClassNotFoundException, SQLException {
        boolean check = false;

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE);
                ptm.setInt(1, alertID);
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
}
