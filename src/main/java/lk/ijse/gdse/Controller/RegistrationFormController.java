package lk.ijse.gdse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.Util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationFormController {
    @FXML
    private JFXButton btnRegister;

    @FXML
    private Hyperlink linkLogin;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPw;

    @FXML
    private TextField txtUserId;

    public void initialize() {
        txtUserId.setOnKeyPressed(this::handleEnterKeyPressed);
        txtName.setOnKeyPressed(this::handleEnterKeyPressed);
    }

    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (event.getSource() == txtUserId) {
                txtName.requestFocus();
            } else if (event.getSource() == txtName) {
                txtPw.requestFocus();
            }
        }
    }



    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String name = txtName.getText();
        String password = txtPw.getText();

        try {
            boolean isSaved = saveUser(userId, name, password);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean saveUser(String userId, String name, String password) throws SQLException {
        String sql = "INSERT INTO users VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);
        pstm.setObject(2, name);
        pstm.setObject(3, password);

        return pstm.executeUpdate() > 0;
    }

    public void linkLoginOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_form.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");

        stage.show();
    }

    public void txtUserIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.ID,txtUserId);
    }
}
