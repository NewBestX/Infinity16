package pizzashop.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pizzashop.gui.ExceptionAlert;
import pizzashop.model.MenuDataModel;
import pizzashop.service.MenuService;
import pizzashop.gui.payment.PaymentAlert;
import pizzashop.service.PaymentsService;


import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TableGUIController {

    @FXML
    private ComboBox<Integer> orderQuantity;
    @FXML
    private TableView orderTable;
    @FXML
    private TableColumn tableQuantity;
    @FXML
    protected TableColumn tableMenuItem;
    @FXML
    private TableColumn tablePrice;
    @FXML
    private Label pizzaTypeLabel;
    @FXML
    private Button addToOrder;
    @FXML
    private Label orderStatus;
    @FXML
    private Button placeOrder;
    @FXML
    private Button orderServed;
    @FXML
    private Button payOrder;
    @FXML
    private Button newOrder;

    private List<String> orderList = FXCollections.observableArrayList();
    private List<Double> orderPaymentList = FXCollections.observableArrayList();
    private TableState state;

    public static double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private MenuService menuService;
    private PaymentsService paymentsService;
    private int tableNumber;

    public ObservableList<String> observableList;
    private TableView<MenuDataModel> table = new TableView<MenuDataModel>();
    private ObservableList<MenuDataModel> menuData;// = FXCollections.observableArrayList();
    private Calendar now = Calendar.getInstance();
    private static double totalAmount;

    public TableGUIController() {
        state = null;
    }

    private void initData() {
        menuData = FXCollections.observableArrayList(menuService.getMenuData());
        menuData.setAll(menuService.getMenuData());
        orderTable.setItems(menuData);

        //Controller for Place Order Button
        placeOrder.setOnAction(event -> {
            if(state != TableState.ORDERING){
                ExceptionAlert.showExceptionAlert("No pizza added to order");
                return;
            }

            orderTable.getSelectionModel().clearSelection();
            orderList = menuData.stream()
                    .filter(x -> x.getQuantity() > 0)
                    .map(menuDataModel -> menuDataModel.getQuantity() + " " + menuDataModel.getMenuItem())
                    .collect(Collectors.toList());
            observableList = FXCollections.observableList(orderList);
            KitchenGUIController.order.add("Table" + tableNumber + " " + orderList.toString());
            orderStatus.setText("Order placed at: " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE));
            state = TableState.ORDER_PLACED;
        });

        //Controller for Order Served Button
        orderServed.setOnAction(event -> {
            if(state != TableState.ORDER_PLACED) {
                ExceptionAlert.showExceptionAlert("No order placed");
                return;
            }
            orderStatus.setText("Served at: " + now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE));
            state = TableState.ORDER_SERVED;
        });

        //Controller for Pay Order Button
        payOrder.setOnAction(event -> {
            if(state != TableState.ORDER_SERVED) {
                ExceptionAlert.showExceptionAlert("The order has not been served");
                return;
            }
            orderPaymentList = menuData.stream()
                    .filter(x -> x.getQuantity() > 0)
                    .map(menuDataModel -> menuDataModel.getQuantity() * menuDataModel.getPrice())
                    .collect(Collectors.toList());
            setTotalAmount(orderPaymentList.stream().mapToDouble(e -> e.doubleValue()).sum());
            orderStatus.setText("Total amount: " + getTotalAmount());
            System.out.println("--------------------------");
            System.out.println("Table: " + tableNumber);
            System.out.println("Total: " + getTotalAmount());
            System.out.println("--------------------------");
            PaymentAlert pay = new PaymentAlert(paymentsService);
            if(pay.showPaymentAlert(tableNumber, this.getTotalAmount()))
                state = TableState.ORDER_PAID;
        });
    }

    public void initialize() {

        //populate table view with menuData from OrderGUI
        table.setEditable(true);
        tableMenuItem.setCellValueFactory(
                new PropertyValueFactory<MenuDataModel, String>("menuItem"));
        tablePrice.setCellValueFactory(
                new PropertyValueFactory<MenuDataModel, Double>("price"));
        tableQuantity.setCellValueFactory(
                new PropertyValueFactory<MenuDataModel, Integer>("quantity"));

        //bind pizzaTypeLabel and quantity combo box with the selection on the table view
        orderTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MenuDataModel>() {
            @Override
            public void changed(ObservableValue<? extends MenuDataModel> observable, MenuDataModel oldValue, MenuDataModel newValue) {
                if (newValue != null)
                    pizzaTypeLabel.textProperty().bind(newValue.menuItemProperty());
            }
        });

        //Populate Combo box for Quantity
        ObservableList<Integer> quantityValues = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5);
        orderQuantity.getItems().addAll(quantityValues);
        orderQuantity.setPromptText("Quantity");

        //Controller for Add to order Button
        addToOrder.setOnAction(event -> {
            if(state == TableState.ORDER_PAID){
                ExceptionAlert.showExceptionAlert("Comanda a fost deja achitata.");
                return;
            }
            if (orderTable.getSelectionModel().isEmpty()) {
                ExceptionAlert.showExceptionAlert("No pizza selected");
                return;
            }
            if (orderQuantity.getValue() == null) {
                ExceptionAlert.showExceptionAlert("No quantity selected");
                return;
            }
            state = TableState.ORDERING;

            orderTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MenuDataModel>() {
                @Override
                public void changed(ObservableValue<? extends MenuDataModel> observable, MenuDataModel oldValue, MenuDataModel newValue) {
                    oldValue.setQuantity(orderQuantity.getValue());
                    orderTable.getSelectionModel().selectedItemProperty().removeListener(this);
                }
            });
        });

        //Controller for Exit table Button
        newOrder.setOnAction(event -> {
            if(state != TableState.ORDER_PAID && state != null) {
                ExceptionAlert.showExceptionAlert("Order was not paid");
                return;
            }
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Exit table?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Stage stage = (Stage) newOrder.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void setService(MenuService menuService, PaymentsService paymentsService, int tableNumber) {
        this.menuService = menuService;
        this.paymentsService = paymentsService;
        this.tableNumber = tableNumber;
        initData();

    }
}

enum TableState {
    ORDERING, ORDER_PLACED, ORDER_SERVED, ORDER_PAID
}