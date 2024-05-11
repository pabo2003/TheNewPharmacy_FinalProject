package lk.ijse.gdse.Controller;


import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.Util.Regex;
import lk.ijse.gdse.model.Supplier;
import lk.ijse.gdse.repository.SupplierRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtTel;

    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();

        Supplier supplier = SupplierRepo.searchById(id);

        if (supplier != null) {
            txtId.setText(supplier.getSupplierId());
            txtName.setText(supplier.getName());
            txtDescription.setText(supplier.getDescription());
            txtAddress.setText(supplier.getAddress());
            txtTel.setText(supplier.getTel());
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Supplier is not found!");
        }
    }

    public void initialize(){
        setCellValueFactory();
        loadAllSuppliers();

        txtId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDescription.requestFocus();
            }
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus();
            }
        });
        txtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtTel.requestFocus();
            }
        });

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                txtId.setText(newSelection.getSupplierId());
                txtName.setText(newSelection.getName());
                txtDescription.setText(newSelection.getDescription());
                txtAddress.setText(newSelection.getAddress());
                txtTel.setText(newSelection.getTel());

            }
        });
    }

    private void loadAllSuppliers() {
        ObservableList<Supplier> objList = FXCollections.observableArrayList();
        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                Supplier supplier1 = new Supplier(
                        supplier.getSupplierId(),
                        supplier.getName(),
                        supplier.getDescription(),
                        supplier.getAddress(),
                        supplier.getTel()
                );
                objList.add(supplier1);
            }
            tblSupplier.setItems(objList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtDescription.clear();
        txtAddress.clear();
        txtTel.clear();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException {
        AnchorPane rootnode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(rootnode));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Deleted!");
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        loadAllSuppliers();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        Supplier supplier = new Supplier(id,name,description,address,tel);

        try {
            boolean isSaved = SupplierRepo.save(supplier);
            if (id.isEmpty() || name.isEmpty() || description.isEmpty() || address.isEmpty() || tel.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
                return;
            }

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier saved successfully!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllSuppliers();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        Supplier supplier = new Supplier(id,name,description,address,tel);

        try {
            boolean isUpdate = SupplierRepo.update(supplier);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier is updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        loadAllSuppliers();
        clearFields();
    }

    public void txtSupplierNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NAME,txtName);
    }

    public void txtSupplierTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.TEL,txtTel);
    }

    public void txtSupplierIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.ID,txtId);
    }
}
