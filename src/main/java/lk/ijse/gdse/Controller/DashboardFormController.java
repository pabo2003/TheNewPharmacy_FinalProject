
package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardFormController {

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


    private void initialize() {
        addButtonHoverEffect(btnCustomer);
        addButtonHoverEffect(btnEmployee);
        addButtonHoverEffect(btnItems);
        addButtonHoverEffect(btnPlaceOrder);
        addButtonHoverEffect(btnSignOut);
        addButtonHoverEffect(btnStock);
        addButtonHoverEffect(btnSupplier);
    }

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/customer_form.fxml"));
        Parent rootNode = loader.load();
        SpecialPane.getChildren().clear();
        SpecialPane.getChildren().add(rootNode);
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/employee_form.fxml"));
        Parent rootNode = loader.load();
        SpecialPane.getChildren().clear();
        SpecialPane.getChildren().add(rootNode);
    }

    @FXML
    void btnItemsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/item_form.fxml"));
        Parent rootNode = loader.load();
        SpecialPane.getChildren().clear();
        SpecialPane.getChildren().add(rootNode);
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/orderPlacement_form.fxml"));
        Parent rootNode = loader.load();
        SpecialPane.getChildren().clear();
        SpecialPane.getChildren().add(rootNode);
    }

    @FXML
    void btnSignOutOnAction(ActionEvent event) throws IOException {
        Button btn = (Button) event.getSource();
        Stage stage = (Stage) btn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    void btnStockOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/stock_form.fxml"));
        Parent rootNode = loader.load();
        SpecialPane.getChildren().clear();
        SpecialPane.getChildren().add(rootNode);
    }

    @FXML
    void btnSupplierOAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/supplier_form.fxml"));
        Parent rootNode = loader.load();
        SpecialPane.getChildren().clear();
        SpecialPane.getChildren().add(rootNode);
    }

    public void btnSupplierDetailsOAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SupplierDetails_form.fxml"));
        Parent rootNode = loader.load();
        SpecialPane.getChildren().clear();
        SpecialPane.getChildren().add(rootNode);
    }
}