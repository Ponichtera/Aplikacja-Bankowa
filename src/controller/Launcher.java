package controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Account;
import model.DatabaseHandler;
import model.UTF8Control;
import model.User;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Launcher extends Application {
    public static final URL propertiesURL = Launcher.class.getResource("/resources/home.properties");
    public static String localeCode = "en-UK";
    public static User user;
    private double xOffset = 0;
    private double yOffset = 0;


    static void createUser(int id) {
        List<Account> list = Account.getAccountForUser(id);
        user = new User(id, list.toArray(new Account[2]));
        localeCode=(user.getLang());
    }

    public static ResourceBundle getResourceBundle(){
        System.out.println(Launcher.localeCode);
        Locale locale = Locale.forLanguageTag(Launcher.localeCode);
        Locale.setDefault(locale);
        System.out.println("new Locale: " + locale);
        return ResourceBundle.getBundle("home", new UTF8Control());
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/test.fxml"));
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.getIcons().add(new Image("/resources/images/icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });


        new Thread(() -> DatabaseHandler.getInstance()).start();
        Runtime.getRuntime().addShutdownHook(new Thread( () -> DatabaseHandler.getInstance().closeConnection()));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
