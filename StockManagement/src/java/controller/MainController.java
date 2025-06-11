package controller;

import dao.*;
import dto.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = "login.jsp";

        try {
            HttpSession session = request.getSession();
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

            // Nếu chưa đăng nhập và không phải đang thực hiện Login thì chuyển về login.jsp
            if (loginUser == null && (action == null || !"Login".equals(action))) {
                request.setAttribute("ERROR", "Phiên đăng nhập đã hết. Vui lòng đăng nhập lại.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            if ("Login".equals(action)) {
                // Xử lý đăng nhập
                String userID = request.getParameter("userID");
                String password = request.getParameter("password");

                if (userID.isEmpty() || password.isEmpty()) {
                    request.setAttribute("ERROR", "Vui lòng nhập đầy đủ thông tin!");
                } else {
                    UserDAO dao = new UserDAO();
                    UserDTO user = dao.checkLogin(userID, password);
                    if (user == null) {
                        request.setAttribute("ERROR", "Sai tài khoản hoặc mật khẩu!");
                    } else {
                        session.setAttribute("LOGIN_USER", user);
                        url = "welcome.jsp";
                    }
                }

            } else if ("Logout".equals(action)) {
                // Xử lý đăng xuất
                session.invalidate();
                url = "login.jsp";

            } else if ("ListUsers".equals(action)) {
                // Hiển thị danh sách người dùng
                UserDAO dao = new UserDAO();
                request.setAttribute("USER_LIST", dao.getAllUsers());
                url = "userList.jsp";

            } else if ("AddUserPage".equals(action)) {
                // Chuyển đến trang thêm người dùng
                url = "addUser.jsp";

            } else if ("CreateUser".equals(action)) {
                // Xử lý thêm người dùng
                String userID = request.getParameter("userID");
                String fullName = request.getParameter("fullName");
                String roleID = request.getParameter("roleID");
                String password = request.getParameter("password");

                if (userID.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
                    request.setAttribute("ERROR", "Vui lòng nhập đầy đủ thông tin!");
                    url = "addUser.jsp";
                } else {
                    UserDAO dao = new UserDAO();
                    if (dao.getUserByID(userID) != null) {
                        request.setAttribute("ERROR", "UserID đã tồn tại!");
                        url = "addUser.jsp";
                    } else {
                        dao.createUser(new UserDTO(userID, fullName, roleID, password));
                        request.setAttribute("MESSAGE", "Thêm người dùng thành công!");
                        request.setAttribute("USER_LIST", dao.getAllUsers());
                        url = "userList.jsp";
                    }
                }

            } else if ("EditUser".equals(action)) {
                // Chuyển đến trang cập nhật người dùng
                String userID = request.getParameter("userID");
                UserDAO dao = new UserDAO();
                request.setAttribute("EDIT_USER", dao.getUserByID(userID));
                url = "updateUser.jsp";

            } else if ("UpdateUser".equals(action)) {
                // Xử lý cập nhật người dùng
                String userID = request.getParameter("userID");
                String fullName = request.getParameter("fullName");
                String roleID = request.getParameter("roleID");
                String password = request.getParameter("password");

                UserDAO dao = new UserDAO();
                dao.updateUser(new UserDTO(userID, fullName, roleID, password));
                request.setAttribute("MESSAGE", "Cập nhật thành công!");
                request.setAttribute("USER_LIST", dao.getAllUsers());
                url = "userList.jsp";

            } else if ("DeleteUser".equals(action)) {
                // Xử lý xóa người dùng (kiểm tra họ có giao dịch không)
                String userID = request.getParameter("userID");
                UserDAO dao = new UserDAO();
                TransactionDAO tdao = new TransactionDAO();

                if (tdao.checkUserInTransaction(userID)) {
                    request.setAttribute("ERROR", "Không thể xóa vì người dùng đang có giao dịch!");
                } else {
                    dao.deleteUser(userID);
                    request.setAttribute("MESSAGE", "Xóa người dùng thành công!");
                }

                request.setAttribute("USER_LIST", dao.getAllUsers());
                url = "userList.jsp";

            } else if ("ListStocks".equals(action)) {
                // Hiển thị danh sách cổ phiếu
                StockDAO dao = new StockDAO();
                request.setAttribute("STOCK_LIST", dao.getAllStocks());
                url = "stockList.jsp";

            } else if ("AddStockPage".equals(action)) {
                // Chuyển đến trang thêm cổ phiếu
                url = "addStock.jsp";

            } else if ("CreateStock".equals(action)) {
                // Xử lý thêm cổ phiếu
                String ticker = request.getParameter("ticker");
                String companyName = request.getParameter("companyName");
                double price = Double.parseDouble(request.getParameter("price"));
                boolean status = true;

                StockDAO dao = new StockDAO();
                if (dao.getStockByTicker(ticker) != null) {
                    request.setAttribute("ERROR", "Cổ phiếu đã tồn tại!");
                    url = "addStock.jsp";
                } else {
                    dao.insertStock(new StockDTO(0, ticker, companyName, price, status));
                    request.setAttribute("MESSAGE", "Thêm cổ phiếu thành công!");
                    request.setAttribute("STOCK_LIST", dao.getAllStocks());
                    url = "stockList.jsp";
                }

            } else if ("EditStock".equals(action)) {
                // Chuyển đến trang cập nhật cổ phiếu
                String ticker = request.getParameter("ticker");
                StockDAO dao = new StockDAO();
                request.setAttribute("EDIT_STOCK", dao.getStockByTicker(ticker));
                url = "updateStock.jsp";

            } else if ("UpdateStock".equals(action)) {
                // Xử lý cập nhật cổ phiếu
                String ticker = request.getParameter("ticker");
                String sector = request.getParameter("sector");
                double price = Double.parseDouble(request.getParameter("price"));
                boolean status = "1".equals(request.getParameter("status"));

                StockDAO dao = new StockDAO();
                dao.updateStock(new StockDTO(0, ticker, sector, price, status));
                request.setAttribute("MESSAGE", "Cập nhật thành công!");
                request.setAttribute("STOCK_LIST", dao.getAllStocks());
                url = "stockList.jsp";

            } else if ("DeleteStock".equals(action)) {
                // Xử lý xóa cổ phiếu (kiểm tra có giao dịch không)
                String ticker = request.getParameter("ticker");
                StockDAO dao = new StockDAO();
                TransactionDAO tdao = new TransactionDAO();

                if (tdao.checkStockInTransaction(ticker)) {
                    request.setAttribute("ERROR", "Không thể xóa vì cổ phiếu đang tồn tại trong giao dịch!");
                } else {
                    dao.deleteStock(ticker);
                    request.setAttribute("MESSAGE", "Xóa cổ phiếu thành công!");
                }
                request.setAttribute("STOCK_LIST", dao.getAllStocks());
                url = "stockList.jsp";

            } else if ("ListTransactions".equals(action)) {
                // Hiển thị danh sách giao dịch (Admin xem tất cả, NV xem của chính mình)
                TransactionDAO dao = new TransactionDAO();
                List<TransactionDTO> list = "AD".equals(loginUser.getRoleID())
                        ? dao.getAllTransactions()
                        : dao.getTransactionsByUser(loginUser.getUserID());
                request.setAttribute("TRANS_LIST", list);
                url = "transactionList.jsp";

            } else if ("AddTransactionPage".equals(action)) {
                // Chuyển đến trang thêm giao dịch
                StockDAO sdao = new StockDAO();
                List<StockDTO> list = sdao.getAllStocks();
                request.setAttribute("STOCK_LIST", list);
                url = "addTransaction.jsp";

            } else if ("CreateTransaction".equals(action)) {
                // Xử lý thêm giao dịch
                String ticker = request.getParameter("ticker");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                TransactionDAO dao = new TransactionDAO();
                TransactionDTO trans = new TransactionDTO(
                        0,
                        loginUser.getUserID(), // userID đúng là ID người đang đăng nhập
                        ticker, // ticker là mã cổ phiếu
                        price,
                        quantity,
                        null
                );
                dao.insertTransaction(trans);

                request.setAttribute("MESSAGE", "Tạo giao dịch thành công!");
                List<TransactionDTO> list = dao.getTransactionsByUser(loginUser.getUserID());
                request.setAttribute("TRANS_LIST", list);
                url = "transactionList.jsp";

            } else if ("EditTransaction".equals(action)) {
                // Xử lý Sửa giao dịch
                int transID = Integer.parseInt(request.getParameter("transID"));

                TransactionDAO dao = new TransactionDAO();
                TransactionDTO trans = dao.getTransactionByID(transID);

                if (trans == null || !loginUser.getUserID().equals(trans.getUserID())) {
                    request.setAttribute("ERROR", "Không thể sửa giao dịch này.");
                    List<TransactionDTO> list = dao.getTransactionsByUser(loginUser.getUserID());
                    request.setAttribute("TRANS_LIST", list);
                    url = "transactionList.jsp";
                } else {
                    StockDAO sdao = new StockDAO();
                    List<StockDTO> stocks = sdao.getAllStocks();
                    request.setAttribute("STOCK_LIST", stocks);
                    request.setAttribute("EDIT_TRANS", trans);
                    url = "updateTransaction.jsp";
                }

            } else if ("UpdateTransaction".equals(action)) {
                // Xử lý cập nhật giao dịch
                int transID = Integer.parseInt(request.getParameter("transID"));
                String ticker = request.getParameter("ticker");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                TransactionDAO dao = new TransactionDAO();
                TransactionDTO updatedTrans = new TransactionDTO(
                        transID,
                        loginUser.getUserID(),
                        ticker,
                        price,
                        quantity,
                        null
                );
                dao.updateTransaction(updatedTrans);

                request.setAttribute("MESSAGE", "Cập nhật giao dịch thành công!");
                List<TransactionDTO> list = dao.getTransactionsByUser(loginUser.getUserID());
                request.setAttribute("TRANS_LIST", list);
                url = "transactionList.jsp";

            } else if ("DeleteTransaction".equals(action)) {
                // Xử lý xóa giao dịch
                int transID = Integer.parseInt(request.getParameter("transID"));

                TransactionDAO dao = new TransactionDAO();
                boolean deleted = dao.deleteTransaction(transID, loginUser.getUserID());

                if (deleted) {
                    request.setAttribute("MESSAGE", "Xóa giao dịch thành công!");
                } else {
                    request.setAttribute("ERROR",
                            "Không thể xóa giao dịch (có thể không tồn tại hoặc không phải giao dịch của bạn).");
                }

                List<TransactionDTO> list = dao.getTransactionsByUser(loginUser.getUserID());
                request.setAttribute("TRANS_LIST", list);
                url = "transactionList.jsp";

            } else if (action.equals("ListAlert") || action.equals("ViewAlerts")) {
                String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
                AlertDAO dao = new AlertDAO();
                List<AlertDTO> list = dao.getAlertsByUser(loginUser.getUserID(), keyword);
                request.setAttribute("ALERT_LIST", list);
                request.setAttribute("KEYWORD", keyword);
                url = "alertList.jsp";
            } else if (action.equals("AddAlert")) {
                url = "addAlert.jsp";
            } else if (action.equals("AddAlert")) {
                String ticker = request.getParameter("ticker");
                double threshold = Double.parseDouble(request.getParameter("threshold"));
                String direction = request.getParameter("direction");
                String status = "inactive";

                if (ticker.isEmpty() || direction.isEmpty()) {
                    request.setAttribute("ERROR", "Please enter full");
                } else if (threshold <= 0) {
                    request.setAttribute("ERROR", "Threshold price must be greater than 0!");
                } else if (!direction.equals("increase") && !direction.equals("decrease")) {
                    request.setAttribute("ERROR", "Hướng phải là increase hoặc decrease!");
                } else {
                    AlertDAO dao = new AlertDAO();
                    AlertDTO alert = new AlertDTO(0, loginUser.getUserID(), ticker, (float) threshold, direction, status);
                    if (dao.createAlert(alert)) {
                        request.setAttribute("MESSAGE", "Added successfully");
                        url = "alertList.jsp";
                    } else {
                        request.setAttribute("ERROR", "Added failed!");
                    }
                }
            } else if (action.equals("UpdateAlertPage")) {
                int alertID = Integer.parseInt(request.getParameter("alertID"));
                AlertDAO dao = new AlertDAO();
                AlertDTO alert = dao.getAlertById(alertID);
                if (!alert.getUserID().equals(loginUser.getUserID())) {
                    request.setAttribute("ERROR", "No rights!");
                    url = "alertList.jsp";
                } else {
                    request.setAttribute("ALERT", alert);
                    url = "updateAlert.jsp";
                }
            } else if (action.equals("UpdateAlert")) {
                int alertID = Integer.parseInt(request.getParameter("alertID"));
                AlertDAO dao = new AlertDAO();
                AlertDTO alert = dao.getAlertById(alertID);
                if (!alert.getUserID().equals(loginUser.getUserID())) {
                    request.setAttribute("ERROR", "Không có quyền!");
                    url = "alertList.jsp";
                } else if (!alert.getStatus().equals("inactive")) {
                    request.setAttribute("ERROR", "Không thể chỉnh sửa cảnh báo đã kích hoạt!");
                    url = "updateAlert.jsp";
                } else {
                    double threshold = Double.parseDouble(request.getParameter("threshold"));
                    String direction = request.getParameter("direction");
                    String status = request.getParameter("status");

                    if (threshold <= 0) {
                        request.setAttribute("ERROR", "Threshold price must be greater than 0!");
                    } else if (!direction.equals("increase") && !direction.equals("decrease")) {
                        request.setAttribute("ERROR", "Invalid direction!");
                    } else if (!status.equals("inactive") && !status.equals("active") && !status.equals("pending")) {
                        request.setAttribute("ERROR", "Invalid status!");
                    } else {
                        alert.setThreshold((float) threshold);
                        alert.setDirection(direction);
                        alert.setStatus(status);
                        if (dao.updateAlert(alert)) {
                            request.setAttribute("MESSAGE", "Cập nhật thành công!");
                            url = "alertList.jsp";
                        } else {
                            request.setAttribute("ERROR", "Cập nhật thất bại!");
                        }
                    }
                }
            } else if (action.equals("DeleteAlert")) {
                int alertID = Integer.parseInt(request.getParameter("alertID"));
                AlertDAO dao = new AlertDAO();
                AlertDTO alert = dao.getAlertById(alertID);
                if (!alert.getUserID().equals(loginUser.getUserID())) {
                    request.setAttribute("ERROR", "Không có quyền!");
                } else if (!alert.getStatus().equals("inactive")) {
                    request.setAttribute("ERROR", "Không thể xóa cảnh báo đã kích hoạt!");
                } else if (dao.deleteAlert(alertID)) {
                    request.setAttribute("MESSAGE", "Xóa thành công!");
                } else {
                    request.setAttribute("ERROR", "Xóa thất bại!");
                }
                url = "alertList.jsp";
            } else if (action.equals("ListNews")) {
                url = "newsList.jsp";
            } else if (action.equals("AddNews")) {
                url = "addNews.jsp";
            } else if (action.equals("CreateNews")) {
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String sectorID = request.getParameter("sectorID");
                String dateStr = request.getParameter("publicDate");
                Date publicDate = Date.valueOf(dateStr);
                NewsDAO newsDAO = new NewsDAO();

                newsDAO.createNews(new NewsDTO(0, title, content, sectorID, publicDate));
                request.setAttribute("MESSAGE", "Them tin moi thanh cong");
                request.setAttribute("NEWS_LIST", newsDAO.getAllNews());
                url = "addNews.jsp";
                
            } else if (action.equals("EditNews")) {
                int newsID = Integer.parseInt(request.getParameter("newsID"));
                NewsDAO newsDAO = new NewsDAO();
                request.setAttribute("EDIT_NEWS", newsDAO.getNewsByID(newsID));
                url = "updateNews.jsp";
            } else if (action.equals("UpdateNews")) {
                int newsID = Integer.parseInt(request.getParameter("newsID"));
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String sectorID = request.getParameter("sectorID");
                String dateStr = request.getParameter("publicDate");
                Date publicDate = Date.valueOf(dateStr);
                NewsDAO newsDAO = new NewsDAO();
                newsDAO.updateNews(new NewsDTO(newsID, title, content, sectorID, publicDate));
                request.setAttribute("MESSAGE", "Cap nhat thanh cong");
                request.setAttribute("NEWS_LIST", newsDAO.getAllNews());
                url = "newsList.jsp";
            } else if (action.equals("DeleteNews")) {
                int newsID = Integer.parseInt(request.getParameter("newsID"));
                NewsDAO newsDAO = new NewsDAO();
                boolean check = newsDAO.deleteNews(newsID);
                if (check) {
                    request.setAttribute("MESSAGE", "Xoa tin thanh cong");
                }
                request.setAttribute("NEWS_LIST", newsDAO.getAllNews());
                url = "newsList.jsp";
            } else if (action.equals("ViewNews")) {
                String search = request.getParameter("keyword");
                if (search == null) {
                    search = "";
                }
                NewsDAO newsDAO = new NewsDAO();
                List<NewsDTO> newsDTOs = newsDAO.searchNews(search);
                request.setAttribute("NEWS_LIST", newsDTOs);
                request.setAttribute("KEYWORD", search);
                url = "newsList.jsp";
            } else {
                request.setAttribute("ERROR", "Chức năng không hợp lệ.");
                url = "welcome.jsp";
            }

        } catch (Exception e) {
            request.setAttribute("ERROR", "Lỗi hệ thống: " + e.getMessage());
            url = "login.jsp";
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
