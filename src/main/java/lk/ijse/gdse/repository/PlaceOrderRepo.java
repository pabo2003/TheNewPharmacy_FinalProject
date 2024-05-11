package lk.ijse.gdse.repository;

import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isPayUpdated = PaymentRepo.save(po.getPayment());
            if (isPayUpdated) {
                boolean isOrderSaved = OrderRepo.save(po.getOrder());
                if (isOrderSaved) {
                    boolean isQtyUpdated = ItemRepo.update1(po.getOdList());
                    if (isQtyUpdated) {
                        boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                        if (isOrderDetailSaved) {

                            connection.commit();
                            return true;
                        }
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
