package pizzashop.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import pizzashop.gui.ExceptionAlert;

import java.text.SimpleDateFormat;
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
    private String extractedTableNumberString = "";
    private int extractedTableNumberInteger;

    private final ScheduledExecutorService orderExecutor = Executors.newSingleThreadScheduledExecutor();
    private static final SimpleDateFormat hourFormat  = new SimpleDateFormat("HH:mm");

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
                    .concat(hourFormat.format(Calendar.getInstance().getTime())));
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
            System.out.println("Table " + extractedTableNumberInteger + " ready at: " + hourFormat.format(Calendar.getInstance().getTime()));
            System.out.println("--------------------------");
        });
    }
}