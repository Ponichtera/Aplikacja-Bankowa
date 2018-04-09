package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.List;

public class DialogWindow {

    public static void showMaterialDialog(StackPane root, Node nodeToBeBlurred, List<JFXButton> controls, String header, String body) {
        BoxBlur blur = new BoxBlur(3, 3, 3);


        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);

        dialog.getStylesheets().add(HomeController.class.getResource("/stylesheet/dashboard.css").toExternalForm());
        dialog.getStyleClass().add("dialogText");

        controls.forEach(controlButton -> {
            controlButton.getStyleClass().add("JFXButton-rounded-dialog");
            controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> dialog.close());
        });

        dialogLayout.setHeading(new Label(header));
        dialogLayout.setBody(new Label(body));
        dialogLayout.setActions(controls);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> nodeToBeBlurred.setEffect(null));
        nodeToBeBlurred.setEffect(blur);
    }
}
