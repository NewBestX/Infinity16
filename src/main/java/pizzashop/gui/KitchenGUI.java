package pizzashop.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import pizzashop.controller.KitchenGUIController;
import pizzashop.controller.MainGUIController;

import java.io.IOException;
import java.util.Optional;


public class KitchenGUI {

    private boolean isOpen;
    private  KitchenGUIController kitchenGUIController;

    public KitchenGUI() {
        VBox vBoxKitchen = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/kitchenGUIFXML.fxml"));
            vBoxKitchen = loader.load();
             kitchenGUIController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Kitchen");
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(MainGUIController.getOpenTableCount() != 0) {
                    ExceptionAlert.showExceptionAlert("There are still open tables");
                    event.consume();
                    return;
                }
                Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit Kitchen window?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> result = exitAlert.showAndWait();
                if (result.get() == ButtonType.YES){
                    //Stage stage = (Stage) this.getScene().getWindow();
                    stage.close();
                    kitchenGUIController.shutDownExecutor();
                    isOpen = false;
                }
                // consume event
                else if (result.get() == ButtonType.NO){
                    event.consume();
                }
                else {
                    event.consume();
                }
            }

            });
        stage.setAlwaysOnTop(false);
        stage.setScene(new Scene(vBoxKitchen));
        stage.show();
        isOpen = true;
        stage.toBack();
    }

    public boolean isOpen() {
        return isOpen;
    }
}

