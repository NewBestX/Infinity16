package pizzashop.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pizzashop.controller.TableGUIController;
import pizzashop.service.MenuService;
import pizzashop.service.PaymentsService;


import java.io.IOException;


public class TableGUI {

    protected int tableNumber;
    public int getTableNumber() {
        return tableNumber;
    }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }


    public void displayOrdersForm(MenuService menuService, PaymentsService paymentsService) {
        VBox vBoxOrders = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OrdersGUIFXML.fxml"));

            //vBoxOrders = FXMLLoader.load(getClass().getResource("/fxml/OrdersGUIFXML.fxml"));
            vBoxOrders = loader.load();
            TableGUIController ordersCtrl= loader.getController();
            ordersCtrl.setService(menuService, paymentsService, tableNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Table"+getTableNumber()+" order form");
        stage.setResizable(false);
        // disable X on the window
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // consume event
                event.consume();
            }
        });
        stage.setScene(new Scene(vBoxOrders));
        stage.show();
    }
}
