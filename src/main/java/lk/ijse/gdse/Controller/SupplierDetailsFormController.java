package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class SupplierDetailsFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnAddStock;

    @FXML
    private JFXButton btnAddSupplier;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colStockId;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private ComboBox<?> comStockId;

    @FXML
    private ComboBox<?> comSupplierId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblStockDescription;

    @FXML
    private Label lblSupplierName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<?> tblSupplierDetails;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddStockOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) {

    }

    @FXML
    void comStockIdOnAction(ActionEvent event) {

    }

    @FXML
    void comSupplierIdOnActiion(ActionEvent event) {

    }

}
