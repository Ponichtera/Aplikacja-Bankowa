package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import model.DatabaseHandler;
import model.UTF8Control;
import ringProgressIndicator.FillProgressIndicator;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashController implements Initializable, Controller {
    private FillProgressIndicator indicator1 , indicator2;
    private ProgressAnimationTread pat1, pat2;

    @FXML
    private Pane filler1, filler2;
    @FXML
    private JFXTextField amount1, amount2;
    @FXML
    private JFXButton button1, button2, successButton1, successButton2;
    @FXML
    private VBox hidePane1, hidePane2, success1, success2;

    @FXML
    private Label  accountNameValue1, homeAvailableBalance1, homeSettingsPrompt1, homeSettingsPrompt2, homeSuccess1, homeSuccess2,
            accountNameValue2, homeAvailableBalance2, dashHeaderLabel, nameLabel;
    @FXML
    private Label homeAvailableBalance1Value;
    @FXML
    private Label homeAvailableBalance2Value;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLanguage();
        nameLabel.setText(Launcher.user.getName());
        accountNameValue1.setText(Launcher.user.accounts[0].getName());
        accountNameValue2.setText(Launcher.user.accounts[1].getName());
        homeAvailableBalance1Value.setText("" + Launcher.user.accounts[0].getBalance());
        homeAvailableBalance2Value.setText("" + Launcher.user.accounts[1].getBalance());
        amount1.setText("" + Launcher.user.accounts[0].getAvailablePercent());
        amount2.setText("" + Launcher.user.accounts[1].getAvailablePercent());

        indicator1 = new FillProgressIndicator();
        indicator2 = new FillProgressIndicator();

//        Slider slider = new Slider(0, 100, 50);
//        slider.valueProperty().addListener((o, oldVal, newVal) -> indicator.setProgress(newVal.intValue()));
//        FillProgressIndicator indicator = new FillProgressIndicator();
        pat1 = new ProgressAnimationTread(indicator1, calculatePercentage(Launcher.user.accounts[0].getAvailablePercent(), Launcher.user.accounts[0].getBalance()));
        pat2 = new ProgressAnimationTread(indicator2,  calculatePercentage(Launcher.user.accounts[1].getAvailablePercent(), Launcher.user.accounts[1].getBalance()));

        filler1.getChildren().addAll(indicator1);
        filler2.getChildren().addAll(indicator2);

        pat1.start();
        pat2.start();
    }

    void setLanguage() {
        ResourceBundle res = Launcher.getResourceBundle();
        dashHeaderLabel.setText(res.getString("home.header"));
        homeAvailableBalance1.setText(res.getString("home.availableBalance"));
        homeAvailableBalance2.setText(res.getString("home.availableBalance"));
        homeSuccess1.setText(res.getString("home.success"));
        homeSuccess2.setText(res.getString("home.success"));
        homeSettingsPrompt1.setText(res.getString("home.settingsPrompt"));
        homeSettingsPrompt2.setText(res.getString("home.settingsPrompt"));


    }

    @FXML
    private void checkValue(ActionEvent actionEvent) {
        JFXTextField field = (JFXTextField) actionEvent.getSource();
        String fieldId = field.getId();
        String input;
        if (fieldId.endsWith("1")) {
            input = amount1.getText();

            if (!input.matches("[0-9]*")) {
                amount1.setText("");
            } else armButton(button1);
        } else {
            input = amount2.getText();

            if (!input.matches("[0-9]*")) {
                amount2.setText("");
            } else armButton(button2);
        }
    }

    @FXML
    private void armButton(JFXButton button) {
        button.setDisable(false);
        button.requestFocus();
        button.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                if(button.getId().endsWith("1"))
                    changePercentValue1();
                else changePercentValue2();

                ev.consume();
            }
        });
    }

    @FXML
    private void toggleSettings1() {
        hidePane1.setVisible(!hidePane1.isVisible());
    }
    @FXML
    private void toggleSettings2() {
        hidePane2.setVisible(!hidePane2.isVisible());
    }

    @FXML
    private void changePercentValue1() {
        hidePane1.setVisible(false);

        Integer percentValue = Integer.parseInt(amount1.getText());
        Launcher.user.accounts[0].setAvailablePercent(percentValue);
        DatabaseHandler.getInstance().execAction("UPDATE ACCOUNT SET percent=" +percentValue + " WHERE number='" + Launcher.user.accounts[0].getNumber() +"'");
        success1.setVisible(true);
        successButton1.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                success1();
                ev.consume();
            }
        });

        successButton1.requestFocus();
    }

    @FXML
    private void changePercentValue2() {
        hidePane2.setVisible(false);
        Integer percentValue = Integer.parseInt(amount2.getText());
        Launcher.user.accounts[1].setAvailablePercent(percentValue);
        DatabaseHandler.getInstance().execAction("UPDATE ACCOUNT SET percent=" +percentValue + " WHERE number='" + Launcher.user.accounts[1].getNumber() +"'");
        success2.setVisible(true);
        successButton2.requestFocus();
        successButton2.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                success2();
                ev.consume();
            }
        });

    }

    @FXML
    private void success1() {
        pat1 = new ProgressAnimationTread(indicator1, calculatePercentage(Integer.parseInt(amount1.getText()), Launcher.user.accounts[0].getBalance()));
        success1.setVisible(false);
        pat1.start();
    }

    private int calculatePercentage(int i, BigDecimal balance) {
        int percent = i / 100 != 0 ? i/100:1;


        return balance.divide(new BigDecimal(percent)).intValue();
    }

    @FXML
    private void success2() {
        pat2 = new ProgressAnimationTread(indicator2, calculatePercentage(Integer.parseInt(amount2.getText()), Launcher.user.accounts[1].getBalance()));
        success2.setVisible(false);
        pat2.start();
    }


    class ProgressAnimationTread extends Thread {
        FillProgressIndicator indicator;
        int progress, max = 100;

        ProgressAnimationTread(FillProgressIndicator indicator, int max) {
            this.indicator = indicator;
            progress = 0;
            if (max > 100 ) this.max = 100;
            else this.max = max;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(10);
                    Platform.runLater( () -> indicator.setProgress(progress) );
                    if ( ++progress == max) break;
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
