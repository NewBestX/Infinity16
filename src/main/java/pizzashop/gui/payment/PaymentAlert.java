package pizzashop.gui.payment;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import pizzashop.gui.ExceptionAlert;
import pizzashop.model.PaymentType;
import pizzashop.service.PaymentsService;
import pizzashop.validator.ValidationException;

import java.util.Optional;

public class PaymentAlert implements PaymentOperation {
    private PaymentsService paymentsService;

    public PaymentAlert(PaymentsService paymentsService){
        this.paymentsService=paymentsService;
    }

    @Override
    public void cardPayment() {
        System.out.println("--------------------------");
        System.out.println("Paying by card...");
        System.out.println("Please insert your card!");
        System.out.println("--------------------------");
    }
    @Override
    public void cashPayment() {
        System.out.println("--------------------------");
        System.out.println("Paying cash...");
        System.out.println("Please show the cash...!");
        System.out.println("--------------------------");
    }
    @Override
    public void cancelPayment() {
        System.out.println("--------------------------");
        System.out.println("Payment choice needed...");
        System.out.println("--------------------------");
    }
      public boolean showPaymentAlert(int tableNumber, double totalAmount ) {
        Alert paymentAlert = new Alert(Alert.AlertType.CONFIRMATION);
        paymentAlert.setTitle("Payment for Table "+tableNumber);
        paymentAlert.setHeaderText("Total amount: " + totalAmount);
        paymentAlert.setContentText("Please choose payment option");
        ButtonType cardPayment = new ButtonType("Pay by Card");
        ButtonType cashPayment = new ButtonType("Pay Cash");
        ButtonType cancel = new ButtonType("Cancel");
        paymentAlert.getButtonTypes().setAll(cardPayment, cashPayment, cancel);
        Optional<ButtonType> result = paymentAlert.showAndWait();
        if (result.isPresent() && result.get() == cardPayment) {
            try {
                paymentsService.addPayment(tableNumber, PaymentType.Card,totalAmount);
            } catch (ValidationException e) {
                ExceptionAlert.showExceptionAlert(e.getMessage());
                return false;
            }
            cardPayment();
            return true;
        } else if (result.isPresent() && result.get() == cashPayment) {
            try {
                paymentsService.addPayment(tableNumber, PaymentType.Cash,totalAmount);
            } catch (ValidationException e) {
                ExceptionAlert.showExceptionAlert(e.getMessage());
                return false;
            }
            cashPayment();
            return true;
        } else {
            cancelPayment();
            return false;
        }
    }
}
