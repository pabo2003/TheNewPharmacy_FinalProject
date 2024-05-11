package lk.ijse.gdse.repository;

import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.OrderDetails;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {
    public static boolean save(List<OrderDetails> odList) throws SQLException {
        for (OrderDetails od : odList) {
            boolean isSaved = saveOrderDetail(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveOrderDetail(OrderDetails od) throws SQLException {
        String sql = "INSERT INTO orderDetails (itemId, orderId, qty, unitPrice) VALUES (?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, od.getItemId());
        pstm.setString(2, od.getOrderId());
        pstm.setInt(3, od.getQty());
        pstm.setDouble(4, od.getUnitPrice());

        return pstm.executeUpdate() > 0;
    }

}
