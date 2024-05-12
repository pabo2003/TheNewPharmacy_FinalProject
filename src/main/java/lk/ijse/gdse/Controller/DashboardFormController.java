
package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardFormController {
    @FXML
    private BarChart<?, ?> BarChartOrderDetails;

    @FXML
    private AnchorPane SpecialPane;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnItems;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXButton btnSignOut;

    @FXML
    private JFXButton btnStock;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private JFXButton btnSupplierDetails;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private void initialize() {
        addButtonHoverEffect(btnCustomer);
        addButtonHoverEffect(btnEmployee);
        addButtonHoverEffect(btnItems);
        addButtonHoverEffect(btnPlaceOrder);
        addButtonHoverEffect(btnSignOut);
        addButtonHoverEffect(btnStock);
        addButtonHoverEffect(btnSupplier);
        addButtonHoverEffect(btnSupplierDetails);
    }

    @FXML
    private void addButtonHoverEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        button.setOnMouseEntered((MouseEvent event) -> {
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });
        button.setOnMouseExited((MouseEvent event) -> {
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/customer_form.fxml"));
            Parent root = loader.load();
            SpecialPane.getChildren().clear();
            SpecialPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/employee_form.fxml"));
            Parent root = loader.load();
            SpecialPane.getChildren().clear();
            SpecialPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnItemsOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/item_form.fxml"));
            Parent root = loader.load();
            SpecialPane.getChildren().clear();
            SpecialPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/orderPlacement_form.fxml"));
            Parent root = loader.load();
            SpecialPane.getChildren().clear();
            SpecialPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSignOutOnAction(ActionEvent event) {
        try {
            Button btn = (Button) event.getSource();
            Stage stage = (Stage) btn.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/login_form.fxml"));
            Parent rootNode = loader.load();

            Scene scene = new Scene(rootNode);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnStockOnAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/stock_form.fxml"));
            Parent root = loader.load();
            SpecialPane.getChildren().clear();
            SpecialPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSupplierOAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/supplier_form.fxml"));
            Parent root = loader.load();
            SpecialPane.getChildren().clear();
            SpecialPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSupplierDetailsOAction(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/SupplierDetails_form.fxml"));
            Parent root = loader.load();
            SpecialPane.getChildren().clear();
            SpecialPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
