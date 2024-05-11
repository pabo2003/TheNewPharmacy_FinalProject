package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.gdse.Util.Regex;
import lk.ijse.gdse.model.Employee;
import lk.ijse.gdse.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    public TableView tblCustomer;
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
    private TableColumn<?, ?> colNICNo;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTel;



    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNICNo;

    @FXML
    private TextField txtName;

    @FXML

    private TextField txtTel;

    @FXML
    private TextField txtSalary;



    public void initialize() {
       setCellValueFactory();
       loadAllEmployee();

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
        txtTel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtSalary.requestFocus();
            }
        });

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getEmployeeId());
                txtName.setText(newSelection.getName());
                txtNICNo.setText(newSelection.getNICNo());
                txtAddress.setText(newSelection.getAddress());
                txtTel.setText(newSelection.getTel());
                txtSalary.setText(String.valueOf(newSelection.getSalary()));
            }
        });
    }

    private void loadAllEmployee() {
        ObservableList<Employee> objList = FXCollections.observableArrayList();
        try {
            List<Employee> employeeList = EmployeeRepo.getAll();
            for (Employee employee : employeeList) {
                Employee employee1 = new Employee(
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getNICNo(),
                        employee.getAddress(),
                        employee.getTel(),
                        employee.getSalary()
                );
                objList.add(employee1);
            }
            tblEmployee.setItems(objList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        this.colId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        this.colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colNICNo.setCellValueFactory(new PropertyValueFactory<>("NICNo"));
        this.colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        this.colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }


    public void txtSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String tel = txtTel.getText();

        Employee employee = EmployeeRepo.searchByTel(tel);

        if (employee != null) {
            txtId.setText(employee.getEmployeeId());
            txtName.setText(employee.getName());
            txtNICNo.setText(employee.getNICNo());
            txtAddress.setText(employee.getAddress());
            txtTel.setText(employee.getTel());
            txtSalary.setText(String.valueOf(employee.getSalary()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer is not found!");
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted!");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        Button btn = (Button) actionEvent.getSource();
        Stage stage = (Stage) btn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard_form.fxml"));
        Parent rootNode = loader.load();

        Scene scene = new Scene(rootNode);
        stage.setScene(scene);

        stage.show();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String nicNo = txtNICNo.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        Employee employee = new Employee(id, name, nicNo, address, tel, salary);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (id.isEmpty() || name.isEmpty() || nicNo.isEmpty() || address.isEmpty() || tel.isEmpty() || txtSalary.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
                return;
            }


            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved successfully!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtNICNo.clear();
        txtAddress.clear();
        txtTel.clear();
        txtSalary.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String nicNo = txtNICNo.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        double salary = Double.parseDouble(txtSalary.getText());

        Employee employee = new Employee(id, name, nicNo, address, tel, salary);

        try {
            boolean isUpdate = EmployeeRepo.update(employee);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee is updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
        clearFields();
    }

    public void txtEmployeeIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.ID,txtId);
    }

    public void txtEmployeeNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NAME,txtName);
    }

    public void txtEmployeeNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC,txtNICNo);
    }

    public void txtEmployeeTelOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.TEL,txtTel);
    }

    public void txtEmployeeSalaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.SALARY,txtSalary);
    }

}
