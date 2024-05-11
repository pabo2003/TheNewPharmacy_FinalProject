package lk.ijse.gdse.Controller;

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
import lk.ijse.gdse.model.Customer;
import lk.ijse.gdse.repository.CustomerRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    @FXML
    private TableView<Customer> tblCustomer;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colNICNo;
    @FXML
    private TableColumn<?, ?> colTel;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtNICNo;
    @FXML
    private TextField txtTel;

    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String tel = txtId.getText();

        Customer customer = CustomerRepo.searchByTel(tel);

        if (customer != null) {
            txtId.setText(customer.getCuId());
            txtName.setText(customer.getName());
            txtNICNo.setText(customer.getNicNo());
            txtAddress.setText(customer.getAddress());
            txtTel.setText(customer.getTel());
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Customer is not found!");
        }
    }

    public void initialize(){
        setCellValueFactory();
        loadAllCustomers();

        txtId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtNICNo.requestFocus();
            }
        });

        txtNICNo.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus();
            }
        });
        txtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtTel.requestFocus();
            }
        });

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getCuId());
                txtName.setText(newSelection.getName());
                txtNICNo.setText(newSelection.getNicNo());
                txtAddress.setText(newSelection.getAddress());
                txtTel.setText(newSelection.getTel());
            }
        });
    }

    private void loadAllCustomers() {
        ObservableList<Customer> objList = FXCollections.observableArrayList();
        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                Customer customer1 = new Customer(
                        customer.getCuId(),
                        customer.getName(),
                        customer.getNicNo(),
                        customer.getAddress(),
                        customer.getTel()
                );
                objList.add(customer1);
            }
            tblCustomer.setItems(objList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        this.colId.setCellValueFactory(new PropertyValueFactory<>("cuId"));
        this.colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colNICNo.setCellValueFactory(new PropertyValueFactory<>("nicNo"));
        this.colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String nicNo = txtNICNo.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        Customer customer = new Customer(id,name,nicNo,address,tel);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (id.isEmpty() || name.isEmpty() || nicNo.isEmpty() || address.isEmpty() || tel.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
                return;
            }

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer saved successfully!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllCustomers();
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtNICNo.clear();
        txtAddress.clear();
        txtTel.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String nicNo = txtNICNo.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        Customer customer = new Customer(id,name,nicNo,address,tel);

        try {
            boolean isUpdate = CustomerRepo.update(customer);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION,"Customer is updated!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        loadAllCustomers();
        clearFields();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Deleted!");
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        loadAllCustomers();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootnode = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(rootnode));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void txtCustomerIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.ID,txtId);
    }

    public void txtCustomerNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NAME,txtName);
    }

    public void txtCustomerNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC,txtNICNo);
    }

    public void txtCustomerTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.TEL,txtTel);
    }
}
