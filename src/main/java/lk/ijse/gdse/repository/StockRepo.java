package lk.ijse.gdse.repository;

import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockRepo {
    public static boolean save(Stock stock) throws SQLException {
        String sql = "INSERT INTO stock VALUES(?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, stock.getStockId());
        pstm.setObject(2, stock.getDescription());
        pstm.setObject(3, stock.getCategory());

        return pstm.executeUpdate() > 0;
    }

    public static List<Stock> getAll() throws SQLException {
        String sql = "SELECT * FROM stock";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Stock> stocksList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);
            String category = resultSet.getString(3);


            Stock stock = new Stock(id,description,category);
            stocksList.add(stock);
        }
        return stocksList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM stock WHERE stockId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Stock stock) throws SQLException {
        String sql = "UPDATE stock SET description = ? , category = ? WHERE stockId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,stock.getDescription());
        pstm.setObject(2,stock.getCategory());
        pstm.setObject(3,stock.getStockId());


        return pstm.executeUpdate() > 0;
    }

    public static Stock searchById(String id) throws SQLException {
        String sql = "SELECT*FROM stock WHERE stockId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String stockId = resultSet.getString(1);
            String description = resultSet.getString(2);
            String category = resultSet.getString(3);

            Stock stock = new Stock(stockId, description, category);

            return stock;
        }
        return null;
    }

    public static List<String> getId() throws SQLException {
        String sql = "SELECT stockId from stock";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> stockList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            stockList.add(id);
        }
        return stockList;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT stockId FROM stock ORDER BY stockId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String employeeId = resultSet.getString(1);
            return employeeId;
        }
        return null;
    }
}
