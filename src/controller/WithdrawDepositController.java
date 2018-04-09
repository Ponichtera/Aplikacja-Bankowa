package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import model.CreditHelper;

import java.net.URL;
import java.util.ResourceBundle;

public class WithdrawDepositController implements Initializable, Controller{
    @FXML
    private JFXSlider slider,  creditCashSlider, timeSlider, creditSlider;
    @FXML
    private JFXButton sliderButton, depositOtherButton, cashSliderButton, withdrawButton, getCreditButton, timeButton,
            creditButton, withdrawOtherButton, depositButton;
    @FXML
    private Label creditTotalLabelValue, creditCostsLabelValue, creditPaymentLabelValue, creditMonthsLabelValue;

    //depositPane
    @FXML
    private Label depositHeader, depositSavingsBalanceLabelValue, depositSelectedAmountLabel, depositAmountLabelValue,
            depositCurrentAccountTypeLabel, depositCurrentAccountTypeLabelValue, depositCurrentAccountNameLabel,
            depositCurrentAccountNameLabelValue, depositCurrentAccountNumberLabel, depositCurrentAccountNumberLabelValue,
            depositCurrentCardLabel, depositCurrentCardLabelValue, depositCurrentBalanceLabel,
            depositCurrentBalanceLabelValue, depositSavingsAccountTypeLabel, depositSavingsAccountValueLabel,
            depositSavingsAccountNameLabel, depositSavingsAccountNameValueLabel, depositSavingsAccountNumberLabel,
            depositSavingsAccountNumberValueLabel, depositSavingsCardLabel, depositSavingsCardValueLabel,
            depositSavingsBalanceLabel;

    //withdraw pane labels
    @FXML
    private Label withdrawHeader, withdrawCurrentAccountTypeLabel, withdrawCurrentAccountTypeLabelValue,
            withdrawCurrentAccountNameLabel, withdrawCurrentAccountNameLabelValue, withdrawCurrentAccountNumberLabel,
            withdrawCurrentAccountNumberLabelValue, withdrawCurrentCardLabel, withdrawCurrentCardLabelValue,
            withdrawCurrentBalanceLabel, withdrawCurrentBalanceLabelValue, withdrawSavingsAccountTypeLabel,
            withdrawSavingsAccountValueLabel, withdrawSavingsAccountNameLabel, withdrawSavingsAccountNameValueLabel,
            withdrawSavingsAccountNumberLabel, withdrawSavingsAccountNumberValueLabel, withdrawSavingsCardLabel,
            withdrawSavingsCardValueLabel, withdrawSavingsBalanceLabel, withdrawSavingsBalanceLabelValue,
            withdrawSelectedAmountLabel, withdrawAmountLabelValue;

    // credit pane
    @FXML
    private Label creditTotalLabel, creditCostsLabel, creditPaymentLabel, creditPaymentsLabel, creditMonths,
            creditSliderTime, creditAmount, creditSliderCash, creditHeader1, creditHeader;

    @FXML
    private HBox otherPane;


    @FXML
    private Tab depositCurrentTab, depositSavingsTab, withdrawSavingsTab, withdrawCurrentTab;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path = location.toString();
        if (path.endsWith("withdraw.fxml") | path.endsWith("deposit.fxml")) {
            slider.setMin(10);
            slider.setMax(1000);
            slider.setValue(80);

            slider.setMajorTickUnit(5);
            slider.setMinorTickCount(0);
            slider.setSnapToTicks(true);

            sliderButton.setText("$" +((Double) slider.getValue()).intValue());
            // Adding Listener to value property.
            slider.valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observable, //
                                    Number oldValue, Number newValue) {

                    sliderButton.setText("$" + (newValue.intValue() - newValue.intValue()%5));
                }
            });
        } if (path.endsWith("credit.fxml")) {
            creditButton.setText("$" +((Double) creditSlider.getValue()).intValue());

            creditSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, //
                                    Number oldValue, Number newValue) {

                    String text = "$" + (newValue.intValue() - newValue.intValue()%50);
                    creditButton.setText(text);
                    calculateCredit();
                }
            });

            timeSlider.setSnapToTicks(true);
            timeButton.setText("" +((Double) timeSlider.getValue()).intValue());
            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, //
                        Number oldValue, Number newValue) {
                    String text = "" + newValue.intValue();
                    timeButton.setText(text);
                    creditMonthsLabelValue.setText(text);
                    calculateCredit();
                }
            });
            calculateCredit();
        }
    }

    void loadDepositLanguage() {
        ResourceBundle res = Launcher.getResourceBundle();

//        amountLabel.setText(res.getString("amount"));
        depositOtherButton.setText(res.getString("other"));
        depositCurrentTab.setText(res.getString("account.type.current"));
        depositSavingsTab.setText(res.getString("account.type.savings"));
        depositHeader.setText(res.getString("deposit.header"));
        depositCurrentAccountTypeLabel.setText(res.getString("account.type"));
        depositCurrentAccountTypeLabelValue.setText(res.getString("account.type.current"));
        depositCurrentAccountNameLabel.setText(res.getString("account.name"));
        depositCurrentAccountNumberLabel.setText(res.getString("account.number"));
        depositCurrentCardLabel.setText(res.getString("creditCard"));
        depositCurrentBalanceLabel.setText(res.getString("balance"));
        depositSavingsAccountTypeLabel.setText(res.getString("account.type"));
        depositSavingsAccountValueLabel.setText(res.getString("account.type.savings"));
        depositSavingsAccountNameLabel.setText(res.getString("account.name"));
        depositSavingsAccountNumberLabel.setText(res.getString("account.number"));
        depositSavingsCardLabel.setText(res.getString("creditCard"));
        depositSavingsBalanceLabel.setText(res.getString("balance"));
        depositSelectedAmountLabel.setText(res.getString("selectedAmount"));
    }

    void loadCreditLanguage() {
        ResourceBundle res = Launcher.getResourceBundle();

        creditHeader.setText(res.getString("credit.header"));
        creditHeader1.setText(res.getString("credit.header1"));
        creditSliderTime.setText(res.getString("credit.sliderTime"));
        creditSliderCash.setText(res.getString("credit.sliderCash"));
        creditTotalLabel.setText(res.getString("credit.total"));
        creditCostsLabel.setText(res.getString("credit.costs"));
        creditPaymentLabel.setText(res.getString("credit.payment"));
        creditPaymentsLabel.setText(res.getString("credit.payments"));
        creditMonths.setText(res.getString("months"));
        creditAmount.setText(res.getString("amount"));
    }

    void loadWithdrawLanguage() {
        ResourceBundle res = Launcher.getResourceBundle();

//        amountLabel.setText(res.getString("amount"));
        withdrawOtherButton.setText(res.getString("other"));
        withdrawCurrentTab.setText(res.getString("account.type.current"));
        withdrawSavingsTab.setText(res.getString("account.type.savings"));
        withdrawHeader.setText(res.getString("withdraw.header"));
        withdrawCurrentAccountTypeLabel.setText(res.getString("account.type"));
        withdrawCurrentAccountTypeLabelValue.setText(res.getString("account.type.current"));
        withdrawCurrentAccountNameLabel.setText(res.getString("account.name"));
        withdrawCurrentAccountNumberLabel.setText(res.getString("account.number"));
        withdrawCurrentCardLabel.setText(res.getString("creditCard"));
        withdrawCurrentBalanceLabel.setText(res.getString("balance"));
        withdrawSavingsAccountTypeLabel.setText(res.getString("account.type"));
        withdrawSavingsAccountValueLabel.setText(res.getString("account.type.savings"));
        withdrawSavingsAccountNameLabel.setText(res.getString("account.name"));
        withdrawSavingsAccountNumberLabel.setText(res.getString("account.number"));
        withdrawSavingsCardLabel.setText(res.getString("creditCard"));
        withdrawSavingsBalanceLabel.setText(res.getString("balance"));
        withdrawSelectedAmountLabel.setText(res.getString("selectedAmount"));
    }

    @FXML
    private void setAmount(ActionEvent actionEvent) {
        String amount = ((JFXButton) actionEvent.getSource()).getText();
        withdrawAmountLabelValue.setText(amount);
        withdrawButton.setDisable(false);
    }

    @FXML
    private void showOtherPane(ActionEvent actionEvent) {
        otherPane.setVisible(true);
    }

    @FXML
    private void withdraw(ActionEvent actionEvent) {
    }

    @FXML
    private void less(ActionEvent actionEvent) {
        slider.setValue(slider.getValue() - 5.0);
    }

    @FXML
    private void more(ActionEvent actionEvent) {
        slider.setValue(slider.getValue() + 5.0);
    }

    @FXML
    private void deposit(ActionEvent actionEvent) {
    }

    @FXML
    private void getCredit(ActionEvent actionEvent) {

    }

    @FXML
    private void setTime(ActionEvent actionEvent) {
    }

    @FXML
    private void moreTime(ActionEvent actionEvent) {
        timeSlider.setValue(timeSlider.getValue() + 1.0);
    }

    @FXML
    private void lessTime(ActionEvent actionEvent) {
        timeSlider.setValue(timeSlider.getValue() - 1.0);
    }

    @FXML
    private void setCredit(ActionEvent actionEvent) {
    }

    @FXML
    private void moreCredit(ActionEvent actionEvent) {
        creditSlider.setValue(creditSlider.getValue() + 50.0);
    }

    @FXML
    private void lessCredit(ActionEvent actionEvent) {
        creditSlider.setValue(creditSlider.getValue() - 50.0);
    }

    @FXML
    private void calculateCredit() {
        CreditHelper.calculateCredit(timeSlider.getValue(), creditSlider.getValue());
        creditTotalLabelValue.setText(CreditHelper.getCredit().toString());
        creditCostsLabelValue.setText(CreditHelper.getCosts().toString());
        creditPaymentLabelValue.setText(CreditHelper.getInstallment().toString());
    }

}
