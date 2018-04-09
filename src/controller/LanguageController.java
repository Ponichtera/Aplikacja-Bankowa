package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.UTF8Control;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageController implements Initializable, Controller {
    private HomeController controller;
    @FXML
    private Label header;

    public void setController(HomeController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reloadLanguage();
    }

    @FXML
    private void setLocaleEN(MouseEvent mouseEvent) {
        Launcher.localeCode = "en-UK";
        System.out.println(Launcher.localeCode);
        reloadLanguages();
    }


    @FXML
    private void setLocalePL(MouseEvent mouseEvent) {
        Launcher.localeCode = "pl-PL";
        reloadLanguages();
    }

    @FXML
    private void setLocaleFR(MouseEvent mouseEvent) {
        Launcher.localeCode = "fr-FR";
        reloadLanguages();
    }

    @FXML
    private void setLocaleRU(MouseEvent mouseEvent) {
        Launcher.localeCode = "ru-RU";
        reloadLanguages();
    }

    private void reloadLanguages() {
        reloadLanguage();
        controller.setLanguage();
    }

    private void reloadLanguage() {
        ResourceBundle res = Launcher.getResourceBundle();
        header.setText(res.getString("language.header"));
    }

    @FXML
    private void closePane(MouseEvent mouseEvent) {
        ImageView imageView;
        if ( mouseEvent.getSource() instanceof Pane) {
            Pane pane = (Pane) mouseEvent.getSource();
            pane.setVisible(false);
        }
        if (mouseEvent.getSource() instanceof ImageView)
            imageView = (ImageView) mouseEvent.getSource();
        else return;
        Parent parent = imageView.getParent();
        Pane pane = (Pane) (parent.getChildrenUnmodifiable()).get(0);
        pane.setVisible(false);
    }

    @FXML
    private void openPane(MouseEvent mouseEvent) {
        ImageView imageView;
        if (mouseEvent.getSource() instanceof ImageView)
            imageView = (ImageView) mouseEvent.getSource();
        else return;
        Parent parent = imageView.getParent();
        Pane pane = (Pane) (parent.getChildrenUnmodifiable()).get(0);
        pane.setVisible(true);
    }
}
