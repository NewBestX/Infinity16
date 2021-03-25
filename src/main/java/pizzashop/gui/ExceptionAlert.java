package pizzashop.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import pizzashop.model.PaymentType;

import java.util.Optional;

public class ExceptionAlert {

    public static void showExceptionAlert(String message) {
        Alert exceptionAlert = new Alert(Alert.AlertType.WARNING);
        exceptionAlert.setTitle("Warning");
        exceptionAlert.setContentText(message);
        ButtonType ok = new ButtonType("OK");
        exceptionAlert.getButtonTypes().setAll(ok);
        Optional<ButtonType> result = exceptionAlert.showAndWait();
    }
}
