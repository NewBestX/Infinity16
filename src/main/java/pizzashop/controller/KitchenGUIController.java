package pizzashop.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import pizzashop.gui.ExceptionAlert;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class KitchenGUIController {
    @FXML
    private ListView kitchenOrdersList;
    @FXML
    public Button cook;
    @FXML
    public Button ready;

    public static ObservableList<String> order = FXCollections.observableArrayList();
    private Object selectedOrder;
    private Calendar now = Calendar.getInstance();
    private String extractedTableNumberString = new String();
    private int extractedTableNumberInteger;

    private final ScheduledExecutorService orderExecutor = Executors.newSingleThreadScheduledExecutor();

    public void shutDownExecutor() {
        orderExecutor.shutdown();
    }

    public void initialize() {
        //starting thread for adding data to kitchenOrderList
        orderExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    kitchenOrdersList.setItems(order);
                });
            }
        }, 100, 100, TimeUnit.MILLISECONDS);
        //Controller for Cook Button
        cook.setOnAction(event -> {
            selectedOrder = kitchenOrdersList.getSelectionModel().getSelectedItem();
            if (selectedOrder == null) {
                ExceptionAlert.showExceptionAlert("No order selected");
                return;
            }

            kitchenOrdersList.getItems().remove(selectedOrder);
            kitchenOrdersList.getItems().add(selectedOrder.toString()
                    .concat(" Cooking started at: ").toUpperCase()
                    .concat(now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE)));
        });
        //Controller for Ready Button
        ready.setOnAction(event -> {
            selectedOrder = kitchenOrdersList.getSelectionModel().getSelectedItem();
            if (selectedOrder == null) {
                ExceptionAlert.showExceptionAlert("No order selected");
                return;
            }
            kitchenOrdersList.getItems().remove(selectedOrder);
            extractedTableNumberString = selectedOrder.toString().subSequence(5, 6).toString();
            extractedTableNumberInteger = Integer.valueOf(extractedTableNumberString);
            System.out.println("--------------------------");
            System.out.println("Table " + extractedTableNumberInteger + " ready at: " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE));
            System.out.println("--------------------------");
        });
    }
}