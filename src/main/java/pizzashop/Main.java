package pizzashop;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pizzashop.controller.MainGUIController;
import pizzashop.gui.ExceptionAlert;
import pizzashop.gui.KitchenGUI;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.MenuService;
import pizzashop.service.PaymentsService;


import java.util.Optional;

public class Main extends Application {

    KitchenGUI kitchenGUI;

    @Override
    public void start(Stage primaryStage) throws Exception{

        MenuRepository repoMenu=new MenuRepository("data/menu.txt");
        PaymentRepository payRepo= new PaymentRepository();
        MenuService menuService = new MenuService(repoMenu);
        PaymentsService paymentsService = new PaymentsService(payRepo);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainFXML.fxml"));
        //VBox box = loader.load();
        Parent box = loader.load();
        MainGUIController ctrl = loader.getController();
        ctrl.setService(menuService, paymentsService);
        primaryStage.setTitle("PizeriaX");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setOnCloseRequest(event -> {
            if(kitchenGUI.isOpen()) {
                ExceptionAlert.showExceptionAlert("Kitchen is not closed");
                event.consume();
                return;
            }
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit the Main window?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES){
                //Stage stage = (Stage) this.getScene().getWindow();
                System.out.println("Incasari cash: "+paymentsService.getTotalAmount(paymentsService.getPayments(), PaymentType.Cash));
                System.out.println("Incasari card: "+paymentsService.getTotalAmount(paymentsService.getPayments(), PaymentType.Card));

                primaryStage.close();
            }
            // consume event
            else if (result.isPresent() && result.get() == ButtonType.NO){
                event.consume();
            }
            else {
                event.consume();

            }

        });
        primaryStage.setScene(new Scene(box));
        primaryStage.show();
        kitchenGUI = new KitchenGUI();
    }

    public static void main(String[] args) { launch(args);
    }
}
