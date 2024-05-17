package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.*;
import lk.ijse.gdse.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OrderPlacementFormController {

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private JFXButton btnAddEmployee;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private ComboBox<String> comCustomerTel;

    @FXML
    private ComboBox<String> comEmployeeTel;

    @FXML
    private ComboBox<String> comItemId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private Label lblItemDescription;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblPayId;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblQty;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CartTm> tblOrderPlacement;

    @FXML
    private TextField txtQty;

    @FXML
    private String payIdValue;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblEmployeeId;
    public void initialize() {
        setDate();
        getCurrentOrderId();
        getCurrentPayId();
        getCustomerTels();
        getEmployeeTels();
        getItemIds();
        setCellValueFactory();
        applyButtonAnimations();
        applyLabelAnimations();
    }

    private void applyButtonAnimations() {
        applyAnimation(btnAddToCart);
        applyAnimation(btnClear);
        applyAnimation(btnPlaceOrder);
        applyAnimation(btnDashboard);
        applyAnimation(btnAddEmployee);
        applyAnimation(btnAddCustomer);
    }

    private void applyLabelAnimations() {
        applyAnimation(lblCustomerName);
        applyAnimation(lblAmount);
        applyAnimation(lblOrderDate);
        applyAnimation(lblEmployeeName);
        applyAnimation(lblItemDescription);
        applyAnimation(lblOrderId);
        applyAnimation(lblUnitPrice);
        applyAnimation(lblQty);
        applyAnimation(lblPayId);
    }

    private void applyAnimation(Button button) {
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

    private void applyAnimation(Label label) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), label);
        label.setOnMouseEntered(event -> {
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0.5);
            fadeTransition.play();
        });
        label.setOnMouseExited(event -> {
            fadeTransition.setFromValue(0.5);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("I_ID"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));

    }
    private void getCurrentOrderId() {

        try {
            String currentId = OrderRepo.getCurrentId();
            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if (currentId != null && currentId.startsWith("ORD")) {
            int idNum = Integer.parseInt(currentId.substring(3)) + 1;
            return "ORD" + String.format("%03d", idNum);
        }
        return "ORD001";
    }
    private void getCurrentPayId() {
        try {
            String currentId = PaymentRepo.getPayCurrentId();
            String nextPayId = generateNextPay(currentId);
            lblPayId.setText(nextPayId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPay(String currentId) {
        if (currentId != null && currentId.startsWith("PAY")) {
            try {
                int idNum = Integer.parseInt(currentId.substring(3)) + 1;
                return "PAY" + String.format("%03d", idNum);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid current payment ID format");
            }
        }
        return "PAY001";
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String I_Id = comItemId.getValue();
        String Description = lblItemDescription.getText();
        double Unit_Price = Double.parseDouble(lblUnitPrice.getText());
        int Qty = Integer.parseInt(txtQty.getText());
        double Amount = Unit_Price * Qty;

        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                CartTm selectedIndex = tblOrderPlacement.getSelectionModel().getSelectedItem();
                obList.remove(selectedIndex);

                tblOrderPlacement.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderPlacement.getItems().size(); i++) {
            if (I_Id.equals(colId.getCellData(i))) {
                CartTm tm = obList.get(i);
                Qty += tm.getQty();
                Amount = Qty * Unit_Price;
                tm.setQty(Qty);
                tm.setAmount(Amount);

                tblOrderPlacement.refresh();
                calculateNetTotal();
                return;
            }
        }

        CartTm tm = new CartTm(I_Id, Description, Qty, Unit_Price, Amount, btnRemove);
        obList.add(tm);
        tblOrderPlacement.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");
    }

    private void calculateNetTotal() {
        double netTotal = 0;
        for (int i = 0; i < tblOrderPlacement.getItems().size(); i++) {
            netTotal += (double) colAmount.getCellData(i);
        }
        lblAmount.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        comCustomerTel.setValue(null);
        comEmployeeTel.setValue(null);
        comItemId.setValue(null);
        lblCustomerName.setText("");
        lblEmployeeName.setText("");
        lblItemDescription.setText("");
        lblAmount.setText("");
        lblQty.setText("");
        lblUnitPrice.setText("");
    }

    @FXML
     void btnDashBoardOnAction(ActionEvent event) {
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
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException, JRException {
        String orderID = lblOrderId.getText();
        String customerID = lblCustomerId.getText();
        String EmployeeID = lblEmployeeId.getText();
        String paymentID = lblPayId.getText();
        String desc  = lblItemDescription.getText();
        Date date = Date.valueOf(LocalDate.now());
        double Amount = Double.parseDouble(lblAmount.getText());
        String PayMethod = "Cash";


        Order order = new Order(orderID,desc,Amount,date,customerID,paymentID,EmployeeID);
        List<OrderDetails> odList = new ArrayList<>();

        for (int i = 0; i < tblOrderPlacement.getItems().size(); i++) {
            CartTm tm = obList.get(i);
            OrderDetails od = new OrderDetails(
                    tm.getI_ID(),
                    orderID,
                    tm.getQty(),
                    tm.getUnitPrice()

            );

            odList.add(od);

        }

        Payment payment = new Payment(paymentID,PayMethod,Amount, date);
        PlaceOrder po = new PlaceOrder(order, odList, payment);

        boolean isPlaced = PlaceOrderRepo.placeOrder(po);
        if (isPlaced) {
            btnPrintBillOnAction(null);
            obList.clear();
            txtQty.clear();
            getCurrentOrderId();
            getCurrentPayId();

            new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
        }
    }
    private void getCustomerTels() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = CustomerRepo.getTel();

            for(String tel : telList) {
                obList.add(tel);
            }

            comCustomerTel.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getEmployeeTels() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> telList = EmployeeRepo.getTel();

            for(String tel : telList) {
                obList.add(tel);
            }

            comEmployeeTel.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getItemIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = ItemRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            comItemId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void comEmployeeTelOnAction(ActionEvent event) {
        String tel = comEmployeeTel.getValue();
        try {
            Employee employee = EmployeeRepo.searchByTel(tel);

            lblEmployeeName.setText(employee.getName());
            lblEmployeeId.setText(employee.getEmployeeId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void comCustomerTelOnAction(ActionEvent event) {
        String tel =  comCustomerTel.getValue();
        try {
            Customer customer = CustomerRepo.searchByTel(tel);

            lblCustomerName.setText(customer.getName());
            lblCustomerId.setText(customer.getCuId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void comItemIdOnAction(ActionEvent event) {
        String id = String.valueOf(comItemId.getValue());
        try {
            Item item = ItemRepo.searchById(id);

            if (item != null) {
                lblItemDescription.setText(item.getDescription());
                lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                lblQty.setText(String.valueOf(item.getQtyOnHand()));
            } else {
                System.out.println("Item with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void btnAddEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/employee_form.fxml"));
            Parent rootNode = loader.load();
            root.getChildren().clear();
            root.getChildren().add(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view/customer_form.fxml"));
            Parent rootNode = loader.load();
            root.getChildren().clear();
            root.getChildren().add(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void btnPrintBillOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/resources/Report/PlaceOrder.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("OrderId",lblOrderId.getText());
        data.put("NetTotal",lblAmount.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data,DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);


    }
}