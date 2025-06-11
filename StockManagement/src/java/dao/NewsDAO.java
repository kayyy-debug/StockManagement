package dao;

import dto.NewsDTO;
import dto.UserDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

public class NewsDAO {

    private static final String CREATE = "INSERT INTO tblNews(title, content, sectorID, publicDate) VALUES(?,?,?,?)";
    private static final String UPDATE = "UPDATE tblNews SET title=?, content=? WHERE newsID=? ";
    private static final String DELETE = "DELETE FROM tblNews WHERE newsID=?";
    private static final String SEARCH = "SELECT* FROM tblNews WHERE title LIKE ?";

    public List<NewsDTO> getAllNews() throws SQLException {
        List<NewsDTO> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement("SELECT * FROM tblNews");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    list.add(new NewsDTO(rs.getInt("newsID"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getString("sectorID"),
                            rs.getDate("publicDate")));
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

    public NewsDTO getNewsByID(int newsID) throws SQLException {
        NewsDTO newsDTO = null;

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement("SELECT * FROM tblNews WHERE newsID=?");
                ptm.setInt(1, newsID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String sectorID = rs.getString("sectorID");
                    Date publicDate = rs.getDate("publicDate");
                    newsDTO = new NewsDTO(newsID, title, content, sectorID, publicDate);
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
        return newsDTO;
    }

    public boolean createNews(NewsDTO newsDTO) throws SQLException {
        boolean check = false;

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE);

                ptm.setString(1, newsDTO.getTitle());
                ptm.setString(2, newsDTO.getContent());
                ptm.setString(3, newsDTO.getSectorID());
                ptm.setDate(4, newsDTO.getPublicDate());

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

    public boolean updateNews(NewsDTO newsDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        boolean check = false;
        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);

                ptm.setString(1, newsDTO.getTitle());
                ptm.setString(2, newsDTO.getContent());
                ptm.setInt(3, newsDTO.getNewsID());

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

    public boolean deleteNews(int newsID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        boolean check = false;

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE);

                ptm.setInt(1, newsID);

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

    public List<NewsDTO> searchNews(String search) throws SQLException {
        List<NewsDTO> newsDTO = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH);

                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int newsID = rs.getInt("newsID");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String sectorID = rs.getString("sectorID");
                    Date publicDate = rs.getDate("publicDate");
                    newsDTO.add(new NewsDTO(newsID, title, content, sectorID, publicDate));
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
        return newsDTO;
    }

}
