package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DatabaseHandler;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable, Controller {

    private Stage stage;
    private HomeController controller;
    @FXML
    public JFXTextField loginField;
    @FXML
    public JFXPasswordField passwordField;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView wrongPassword, wrongLogin;

    @FXML
    private void loginAction() {
        String login = DigestUtils.sha1Hex(loginField.getText());
        String password = DigestUtils.sha1Hex(passwordField.getText());

        String query = "SELECT * FROM MEMBER WHERE login='" +login+ "'";
        ResultSet rs = DatabaseHandler.getInstance().execQuery(query);

        try {
            if(rs.next()) {
                if (rs.getString("login").equals(login)) {
                    if (rs.getString("password").equals(password)) {
                        createUser(rs.getInt("id"));
                        closeWindow();
                        loadMain();
                    }
                    else wrongCredentials();
                } else wrongCredentials();
            } else wrongCredentials();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void wrongCredentials() {
            passwordField.setText("");
            loginField.setText("");
            loginField.requestFocus();
            wrongLogin.setVisible(true);
            wrongPassword.setVisible(true);
    }


    public void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ResourceBundle res = Launcher.getResourceBundle();
//        loginField.setText("login1");
//        passwordField.setText("1234");
    }

    public void loadMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/new atm.fxml"));
            Parent parent = loader.load();
            controller = loader.getController();
            stage = new Stage(StageStyle.TRANSPARENT);
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.getIcons().add(new Image("/resources/images/icon.png"));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createUser(int id) {
        Launcher.createUser(id);
    }
}
