package pizzashop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import  javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pizzashop.gui.TableGUI;
import pizzashop.service.MenuService;
import pizzashop.service.PaymentsService;


public class MainGUIController  {
    @FXML
    private Button table1;
    @FXML
    private Button table2;
    @FXML
    private Button table3;
    @FXML
    private Button table4;
    @FXML
    private Button table5;
    @FXML
    private Button table6;
    @FXML
    private Button table7;
    @FXML
    private Button table8;
    @FXML
    private MenuItem help;

    TableGUI table1Orders = new TableGUI();
    TableGUI table2Orders = new TableGUI();
    TableGUI table3Orders = new TableGUI();
    TableGUI table4Orders = new TableGUI();
    TableGUI table5Orders = new TableGUI();
    TableGUI table6Orders = new TableGUI();
    TableGUI table7Orders = new TableGUI();
    TableGUI table8Orders = new TableGUI();

    private MenuService menuService;
    private PaymentsService paymentsService;

    private static int openTableCount = 0;

    public MainGUIController(){}

    public void setService(MenuService menuService, PaymentsService paymentsService){
        this.menuService=menuService;
        this.paymentsService = paymentsService;
        tableHandlers();
    }

    private void tableHandlers(){
        table1.setOnAction(event -> {
            table1Orders.setTableNumber(1);
            table1Orders.displayOrdersForm(menuService, paymentsService);
            openTableCount++;
        });
        table2.setOnAction(event -> {
            table2Orders.setTableNumber(2);
            table2Orders.displayOrdersForm(menuService, paymentsService);
            openTableCount++;
        });
        table3.setOnAction(event -> {
            table3Orders.setTableNumber(3);
            table3Orders.displayOrdersForm(menuService, paymentsService);
            openTableCount++;
        });
        table4.setOnAction(event -> {
            table4Orders.setTableNumber(4);
            table4Orders.displayOrdersForm(menuService, paymentsService);
            openTableCount++;
        });
        table5.setOnAction(event -> {
            table5Orders.setTableNumber(5);
            table5Orders.displayOrdersForm(menuService, paymentsService);
            openTableCount++;
        });
        table6.setOnAction(event -> {
            table6Orders.setTableNumber(6);
            table6Orders.displayOrdersForm(menuService, paymentsService);
            openTableCount++;
        });
        table7.setOnAction(event -> {
            table7Orders.setTableNumber(7);
            table7Orders.displayOrdersForm(menuService, paymentsService);
            openTableCount++;
        });
        table8.setOnAction(event -> {
            table8Orders.setTableNumber(8);
            table8Orders.displayOrdersForm(menuService, paymentsService);
            openTableCount++;
        });
    }


    public void initialize(){

        help.setOnAction((ActionEvent event) -> {
            Stage stage = new Stage();

            stage.setTitle("How to use..");
            final Group rootGroup = new Group();
            final Scene scene = new Scene(rootGroup, 600, 250);
            final Text text1 = new Text(
                    25, 25,
                    "1. Click on a Table button - a table order form will pop up. "+ System.lineSeparator()
                    +System.lineSeparator()+
                            "2.Choose a Menu item from the menu list, choose Quantity from drop down list, " +  System.lineSeparator()
                            +"press Add to order button and Click on the Menu list (Repeat)" + System.lineSeparator()
                    +System.lineSeparator()+
                            "3.Press Place order button, the order will appear in the Kitchen window"+ System.lineSeparator()
                    +System.lineSeparator()+
                            "4.On the Kitchen window Click on the order in the Orders List and Press the Cook button, " + System.lineSeparator()
                            +"then after Click on the order in the Orders list and then on the Ready button"+ System.lineSeparator()
                    +System.lineSeparator()+
                             "5.On the Table order form press the Order served button, at the end press" + System.lineSeparator()
                             +"the Pay order button "+ System.lineSeparator()
            );

            text1.setFont(Font.font(java.awt.Font.SERIF, 15 ) );
            rootGroup.getChildren().add(text1 );

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
             });
    }

    public static void tableClosed() {
        openTableCount--;
    }

    public static int getOpenTableCount() {
        return openTableCount;
    }
}
