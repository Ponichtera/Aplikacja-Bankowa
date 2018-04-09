package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomeController implements Initializable, Controller {
    private final String LANGUAGE = "/view/language.fxml";
    private final String DASH_BOARD = "/view/dashBoard.fxml";
    private final String DEPOSIT = "/view/deposit.fxml";
    private final String WITHDRAW = "/view/withdraw.fxml";
    private final String CREDIT = "/view/credit.fxml";
    private final String LOGIN = "/view/test.fxml";

    private double xOffset = 0;
    private double yOffset = 0;
    private ResourceBundle res;
    private Controller controller;

    @FXML
    public StackPane rootPane;
    @FXML
    public BorderPane rootBorder;
    @FXML
    private Pane depositPane;
    @FXML
    private AnchorPane withdrawPane;
    @FXML
    private AnchorPane creditPane;
    @FXML
    private AnchorPane logoutPane;
    @FXML
    private AnchorPane homePane;
    @FXML
    private AnchorPane languagePane;
    @FXML
    private Label homeButtonLabel;
    @FXML
    private Label depositButtonLabel;
    @FXML
    private Label withdrawButtonLabel;
    @FXML
    private Label creditButtonLabel;
    @FXML
    private Label languageButtonLabel;
    @FXML
    private Label logoutButtonLabel;
    @FXML
    private AnchorPane shutdownPane;
    @FXML
    private Label shutdownButtonLabel;
    @FXML
    private StackPane centerPane;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        rootPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Stage) rootPane.getScene().getWindow()).setX(event.getScreenX() - xOffset);
                ((Stage) rootPane.getScene().getWindow()).setY(event.getScreenY() - yOffset);
            }
        });
        setLanguage();
        loadWindow(DASH_BOARD);
    }

    void setLanguage() {

        res = Launcher.getResourceBundle();

        homeButtonLabel.setText(res.getString("home.button"));
        depositButtonLabel.setText(res.getString("deposit.button"));
        withdrawButtonLabel.setText(res.getString("withdraw.button"));
        creditButtonLabel.setText(res.getString("credit.button"));
        languageButtonLabel.setText(res.getString("language.button"));
        logoutButtonLabel.setText(res.getString("logout.button"));
        shutdownButtonLabel.setText(res.getString("shutdown.button"));
    }


    private Controller loadWindow(String path) {
        Pane vBox = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            vBox = loader.load();
            Controller controller = loader.getController();
            System.out.println("loadwindow: " + controller.getClass().getName());
            centerPane.getChildren().setAll(vBox);
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void loadLogin() {
        Parent root = null;
        try {
            ((Stage) rootPane.getScene().getWindow()).close();
            root = FXMLLoader.load(getClass().getResource(LOGIN));


            Stage stage = new Stage(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.getIcons().add(new Image("/resources/images/icon.png"));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //buttons
    @FXML
    private void closeAllButtons() {
        depositPane.setVisible(false);
        withdrawPane.setVisible(false);
        creditPane.setVisible(false);
        logoutPane.setVisible(false);
        homePane.setVisible(false);
        languagePane.setVisible(false);
        shutdownPane.setVisible(false);
    }

//    Load windows

    @FXML
    private void loadDashBoard(MouseEvent mouseEvent) {
        controller = loadWindow(DASH_BOARD);
//        if (controller instanceof WithdrawDepositController)
//            ((DashController) controller).loadHomeLanguage();
    }

    @FXML
    private void loadDeposit(MouseEvent mouseEvent) {
        controller = loadWindow(DEPOSIT);
        if (controller instanceof WithdrawDepositController)
            ((WithdrawDepositController) controller).loadDepositLanguage();
    }

    @FXML
    private void loadWithdraw(MouseEvent mouseEvent) {
        controller = loadWindow(WITHDRAW);
        if (controller instanceof WithdrawDepositController)
            ((WithdrawDepositController) controller).loadWithdrawLanguage();
    }

    @FXML
    private void loadCredit(MouseEvent mouseEvent) {
        controller = loadWindow(CREDIT);
        if (controller instanceof WithdrawDepositController)
            ((WithdrawDepositController) controller).loadCreditLanguage();
    }

    @FXML
    private void loadLanguage() {
        controller = loadWindow(LANGUAGE);
        if (controller instanceof LanguageController)
             ( (LanguageController) controller).setController(this);
    }

    @FXML
    private void logout(MouseEvent mouseEvent) {
        JFXButton noButton = new JFXButton(res.getString("no"));
        JFXButton yesButton = new JFXButton(res.getString("yes"));
        noButton.getStyleClass().add("no-button");
        yesButton.getStyleClass().add("yes-button");
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> loadLogin());
        DialogWindow.showMaterialDialog(centerPane, anchorPane, Arrays.asList(yesButton, noButton), res.getString("logout.button"), res.getString("logout"));
    }

    @FXML
    private void shutdown(MouseEvent mouseEvent) {
        JFXButton noButton = new JFXButton(res.getString("no"));
        JFXButton yesButton = new JFXButton(res.getString("yes"));
        noButton.getStyleClass().add("no-button");
        yesButton.getStyleClass().add("yes-button");
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> System.exit(0));
        DialogWindow.showMaterialDialog(centerPane, anchorPane, Arrays.asList(yesButton, noButton), res.getString("shutdown.button"), res.getString("close"));
    }

//    buttons "animation"

    @FXML
    private void openHome() {
        closeAllButtons();
        homePane.setVisible(true);
    }

    @FXML
    private void openDeposit() {
        closeAllButtons();
        depositPane.setVisible(true);
    }

    @FXML
    private void openWithdraw() {
        closeAllButtons();
        withdrawPane.setVisible(true);
    }

    @FXML
    private void openCredit() {
        closeAllButtons();
        creditPane.setVisible(true);
    }

    @FXML
    private void openLanguage() {
        closeAllButtons();
        languagePane.setVisible(true);
    }

    @FXML
    private void openLogout() {
        closeAllButtons();
        logoutPane.setVisible(true);
    }

    @FXML
    private void openShutdown() {
        closeAllButtons();
        shutdownPane.setVisible(true);
    }

}
