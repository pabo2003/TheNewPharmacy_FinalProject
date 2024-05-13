package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.model.Stock;
import lk.ijse.gdse.model.Supplier;
import lk.ijse.gdse.model.SupplierDetails;
import lk.ijse.gdse.repository.StockRepo;
import lk.ijse.gdse.repository.SupplierDetailsRepo;
import lk.ijse.gdse.repository.SupplierRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
    private ComboBox<Object> comStockId;

    @FXML
    private ComboBox<String> comSupplierId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblStockDescription;

    @FXML
    private Label lblSupplierName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplierDetails> tblSupplierDetails;

    public void initialize() {
        setDate();
        getSupplierIds();
        getStockIds();
        setCellValueFactory();
        applyButtonAnimations();
        applyLabelAnimations();
        loadAllSupplierDetails();
    }

    private void setCellValueFactory() {
        colStockId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void applyLabelAnimations() {
        applyAnimation(lblDate);
        applyAnimation(lblSupplierName);
        applyAnimation(lblStockDescription);
    }

    private void applyButtonAnimations() {
        applyAnimation(btnAdd);
        applyAnimation(btnClear);
        applyAnimation(btnAddStock);
        applyAnimation(btnDashboard);
        applyAnimation(btnAddSupplier);
    }

    private void applyAnimation(Label label) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), label);
        label.setOnMouseEntered(event -> {
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });
        label.setOnMouseExited(event -> {
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });
    }

    private void applyAnimation(JFXButton button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        button.setOnMouseEntered(event -> {
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });
        button.setOnMouseExited(event -> {
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });
    }

    private void getStockIds() {
        ObservableList<Object> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = StockRepo.getId();
            for(String id : idList) {
                obList.add(id);
            }
            comStockId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = SupplierRepo.getId();

            for(String id : idList) {
                obList.add(id);
            }

            comSupplierId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllSupplierDetails() {
        ObservableList<SupplierDetails> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDetails> supplierDetailsList = SupplierDetailsRepo.getAll();
            for (SupplierDetails supplierDetails : supplierDetailsList) {
                SupplierDetails supplierDetails1 = new SupplierDetails();
                supplierDetails1.setStockId(supplierDetails.getStockId());
                supplierDetails1.setSupplierId(supplierDetails.getSupplierId());
                supplierDetails1.setDate(supplierDetails.getDate());
                obList.add(supplierDetails1);
            }
            tblSupplierDetails.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/view/supplier_form.fxml"));
        Parent rootNode = loader.load();
        root.getChildren().clear();
        root.getChildren().add(rootNode);
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String stockId = (String) comStockId.getValue();
        String supplierId = comSupplierId.getValue();
        Date date = Date.valueOf(lblDate.getText());

        if (stockId == null || supplierId == null || stockId.isEmpty() || supplierId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
            return;
        }

        SupplierDetails supplierDetails = new SupplierDetails(stockId, supplierId, date);

        try {
            boolean isSaved = SupplierDetailsRepo.save(List.of(supplierDetails));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Details saved successfully!").show();
                clearFields();
                loadAllSupplierDetails();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event){
        clearFields();
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException{
        try {
            AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/resources/view/dashboard_form.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(new Scene(rootNode));
            stage.setTitle("Dashboard Form");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void comStockIdOnAction(ActionEvent event) {
        String id = (String) comStockId.getValue();
        try {
            Stock stock = StockRepo.searchById(id);
            lblStockDescription.setText(stock.getDescription());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void comSupplierIdOnAction(ActionEvent event) {
        String id = comSupplierId.getValue();
        try {
            Supplier supplier = SupplierRepo.searchById(id);
            lblSupplierName.setText(supplier.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        comStockId.setValue(null);
        comSupplierId.setValue(null);
        lblDate.setText("");
    }

    public void btnAddStockOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/view/stock_form.fxml"));
        Parent rootNode = loader.load();
        root.getChildren().clear();
        root.getChildren().add(rootNode);
    }
}