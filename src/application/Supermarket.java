//package application;
//
//import application.model.*;
//import application.producer_consumer.Consumer;
//import application.producer_consumer.Producer;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Orientation;
//import javafx.scene.Scene;
//import javafx.scene.chart.*;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.Slider;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.paint.Paint;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
///**
// * Created by Long laptop on 9/23/2018.
// */
//public class Supermarket extends Application {
//    //CONSTANT
//    public static int NUMBER_OF_CHECKOUT_TILL = 6;
//    //    public static int NUMBER_OF_CUSTOMER = 1000;
//    public static int TILL_LENGTH = 6;
//    public static int MAXIMUM_LOOK_TIMES = 6;
//    public static Random RANDOM = new Random();
//
//    //Flag
//    public static final Object WORKING_OBJECT = new Object();
//    public static volatile boolean WORKING_FLAG = true;
//    public static volatile boolean NEW_SCANNER = false;
//
//    //Time
//    //For Customer object
//    public static int FIRST_LOOK_TIME = 2000;
//    public static int LOOK_AGAIN_TIME = 1000;
//    //For Consumer and Producer objects
//    public static int SPAWN_TIME = 600; //200-240
//    public static int CONSUMER_START_AFTER_PRODUCER_TIME = 4000;
//
//    //Metrics
//    public static double ENQUEUE_REQUESTED = 0;
//    public static double DEQUEUE_SUCCESSED = 0;
//    public static double TRADE_BALANCE = 0;
//    public static volatile int LEFT_CUSTOMER_NUMBER = 0;
//    public static volatile int TOTAL_WAIT_TIME = 0;
//    public static volatile double AVERAGE_WAIT_TIME = 0;
//    public static volatile double AVERAGE_UTIL = 0;
//    public static volatile int SUPERMARKET_WORKING_TIME = 0;
//    public static int clickedTime = 0;
//
//    //List
//    public static List<CheckoutTill> CHECKOUT_TILL_LIST = new ArrayList<CheckoutTill>();
//    public static List<Consumer> consumerList = new ArrayList<>();
//    public static List<LeftCustomerTuple> LEFT_CUSTOMER_LIST = new ArrayList<>();
//    //Common UI
//    public static GridPane GROUP_ROOT = new GridPane();
//    public static GridPane PAUSE_ROOT = new GridPane();
//    public static GridPane GRAPH_ROOT = new GridPane();
//    public static GridPane USELESS_ROOT = new GridPane();
//    public static FlowPane WAITING_AREA_FLOWPANE = new FlowPane(Orientation.HORIZONTAL, 5, 5);
//    public static Label LABEL_SCALE = new Label("Trade balance: " + String.valueOf(TRADE_BALANCE));
//    public static Label LABEL_SPAWN_RATE = new Label("Spawn rate: " + String.valueOf(SPAWN_TIME));
//    public static Label LABEL_ENQUEUE_REQUESTED = new Label("Enqueued customer: " + String.valueOf(ENQUEUE_REQUESTED));
//    public static Label LABEL_DEQUEUE_SUCCESSED = new Label("Dequeued customer: " + String.valueOf(DEQUEUE_SUCCESSED));
//    public static Label LABEL_LEFT_CUSTOMER = new Label("Left customer: " + String.valueOf(LEFT_CUSTOMER_NUMBER));
//    public static Label LABEL_TOTAL_WAIT_TIME = new Label("Total wait time " + String.valueOf(TOTAL_WAIT_TIME));
//    public static Label LABEL_AVERAGE_CUSTOMER_WAIT_TIME = new Label("Average wait time " + String.valueOf(AVERAGE_WAIT_TIME));
//    public static Label LABEL_AVERAGE_UTIL = new Label("Average utilization " + String.valueOf(AVERAGE_UTIL));
//    public static Label LABEL_UTIL_1 = new Label("0");
//    public static Label LABEL_UTIL_2 = new Label("0");
//    public static Label LABEL_UTIL_3 = new Label("0");
//    public static Label LABEL_UTIL_4 = new Label("0");
//    public static Label LABEL_UTIL_5 = new Label("0");
//    public static Label LABEL_UTIL_6 = new Label("0");
//    public static Label LABEL_WORKING_TIME = new Label("0");
//
//    public static void main(String[] args) throws InterruptedException {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        //Create checkout till
//        createTIll();
//        CHECKOUT_TILL_LIST.get(0).setLessThanFiveItems(true);
//        //Prepare UI
//        prepareUI(primaryStage);
//        //Create Till Watcher
//        createTillWatcher();
//
//        //Create Period Counter
//        createPeriodCounter();
//
//        //Create producer and consumer
//        Producer producer = new Producer();
//        producer.setName("Producer 0");
//
//        for (int i = 0; i < NUMBER_OF_CHECKOUT_TILL; i++) {
//            Consumer consumer = new Consumer(i);
//            consumerList.add(consumer);
//        }
//
//        //Start producer
//        producer.start();
//
//        //Start consumer
//        for (int i = 0; i < NUMBER_OF_CHECKOUT_TILL; i++) {
//            Consumer consumer = consumerList.get(i);
//            int consumerIndex = i + 1;
//            consumer.setName("Consumer " + consumerIndex);
//            consumer.start();
//        }
//
//
//    }
//
//    private void createPeriodCounter() {
//        PeriodCounter periodCounter = new PeriodCounter();
//        periodCounter.setName("Period Counter");
//        periodCounter.start();
//    }
//
//    private void createTillWatcher() {
//        TillWatcher tillWatcher = new TillWatcher();
//        tillWatcher.setName("Till watcher");
//        tillWatcher.setPriority(10);
//        tillWatcher.start();
//
//    }
//
//    public static void createTIll() {
//        for (int i = 0; i < NUMBER_OF_CHECKOUT_TILL; i++) {
//            CheckoutTill checkoutTill = new CheckoutTill();
//            checkoutTill.setWorkingStatus(true);
//            CHECKOUT_TILL_LIST.add(checkoutTill);
//        }
//    }
//
//    public static void prepareUI(Stage primaryStage) {
//        //1. Init the properties and constrains of GROUP_ROOT
//        GROUP_ROOT.setHgap(80);
//        GROUP_ROOT.setVgap(4);
//        GROUP_ROOT.setPadding(new Insets(5));
//        final int numCols = 16;
//        final int numRows = 16;
//        for (int i = 0; i < numCols; i++) {
//            ColumnConstraints colConst = new ColumnConstraints();
//            colConst.setPrefWidth(10);
//            GROUP_ROOT.getColumnConstraints().add(colConst);
//        }
//        for (int i = 0; i < numRows; i++) {
//            RowConstraints rowConst = new RowConstraints();
//            rowConst.setPrefHeight(50);
//            GROUP_ROOT.getRowConstraints().add(rowConst);
//        }
//
//        //2. Add nodes to GROUP_ROOT
//        //2.1 Add Waiting area node
//        StackPane shoppingAreaStackPane = new StackPane();
//        Label label = new Label("Waiting area");
//        Rectangle rectangle = new Rectangle(80 * 5, 50);
//        rectangle.setFill(Color.WHITE);
//        shoppingAreaStackPane.getChildren().addAll(rectangle, label);
//        shoppingAreaStackPane.setAccessibleText("Waiting area");
//        GROUP_ROOT.add(shoppingAreaStackPane, 6, 8);
//
//        //2.2 Add 5 item or less button
//        VBox vBoxForFiveItems = new VBox();
//        vBoxForFiveItems.setSpacing(32);
//
//        for (int i = 0; i < NUMBER_OF_CHECKOUT_TILL; i++) {
//            Button button = new Button();
//            Label status_fiveItems = new Label(String.valueOf(CHECKOUT_TILL_LIST.get(i).isLessThanFiveItems()));
//            HBox hBox = new HBox();
//            hBox.setSpacing(10);
//            hBox.getChildren().add(button);
//            hBox.getChildren().add(status_fiveItems);
//            vBoxForFiveItems.getChildren().add(hBox);
//            int tillIndex = i + 1;
//            button.setText("Five items " + tillIndex);
//            button.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    String tillNumber = button.getText().substring(11);
//                    int tillIndex = Integer.parseInt(tillNumber) - 1;
//                    if (CHECKOUT_TILL_LIST.get(tillIndex).isLessThanFiveItems() == true) {
//                        CHECKOUT_TILL_LIST.get(tillIndex).setLessThanFiveItems(false);
//                    } else CHECKOUT_TILL_LIST.get(tillIndex).setLessThanFiveItems(true);
//                    String status = String.valueOf(CHECKOUT_TILL_LIST.get(tillIndex).isLessThanFiveItems());
//                    System.out.println(tillNumber + status);
//                    status_fiveItems.setText(status);
//                }
//            });
//        }
//
//        GROUP_ROOT.add(vBoxForFiveItems, 10, 1, 4, 6);
//
//        //2.3 Add bottom-left VBOX to store metric values in a vertical box
//        VBox vBoxForMetrics = new VBox();
//        vBoxForMetrics.setSpacing(10);
//        vBoxForMetrics.getChildren().add(LABEL_ENQUEUE_REQUESTED);
//        vBoxForMetrics.getChildren().add(LABEL_DEQUEUE_SUCCESSED);
//        vBoxForMetrics.getChildren().add(LABEL_SCALE);
//        vBoxForMetrics.getChildren().add(LABEL_SPAWN_RATE);
//        vBoxForMetrics.getChildren().add(LABEL_LEFT_CUSTOMER);
//        vBoxForMetrics.getChildren().add(LABEL_TOTAL_WAIT_TIME);
//        vBoxForMetrics.getChildren().add(LABEL_AVERAGE_CUSTOMER_WAIT_TIME);
//        vBoxForMetrics.getChildren().add(LABEL_AVERAGE_UTIL);
//
//        vBoxForMetrics.getChildren().add(LABEL_UTIL_1);
//        vBoxForMetrics.getChildren().add(LABEL_UTIL_2);
//        vBoxForMetrics.getChildren().add(LABEL_UTIL_3);
//        vBoxForMetrics.getChildren().add(LABEL_UTIL_4);
//        vBoxForMetrics.getChildren().add(LABEL_UTIL_5);
//        vBoxForMetrics.getChildren().add(LABEL_UTIL_6);
//        vBoxForMetrics.getChildren().add(LABEL_WORKING_TIME);
//        GROUP_ROOT.add(vBoxForMetrics, 1, 10, 4, 4);
//
//        //2.4 Add spawn rate slider
//        Slider spawnSlider = new Slider();
//        spawnSlider.setMin(200);
//        spawnSlider.setMax(1000);
//        spawnSlider.setValue(600);
//        spawnSlider.setShowTickLabels(true);
//        spawnSlider.setShowTickMarks(true);
//        spawnSlider.setMinorTickCount(0);
//        spawnSlider.setMajorTickUnit(200);
//        spawnSlider.setBlockIncrement(100);
//        GROUP_ROOT.add(spawnSlider, 1, 16, 6, 1);
//
//        spawnSlider.valueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, //
//                                Number oldValue, Number newValue) {
//                SPAWN_TIME = newValue.intValue();
//                LABEL_SPAWN_RATE.setText("Spawn rate: " + String.valueOf(SPAWN_TIME));
//
//            }
//        });
//
//        //2.5 Add WAITING_AREA_FLOWPANE node
//        GROUP_ROOT.add(WAITING_AREA_FLOWPANE, 6, 10, 6, 6);
//
//        //2.6 Add TILL FLOWPANE for each till
//        for (int i = 0; i < NUMBER_OF_CHECKOUT_TILL; i++) {
//            CheckoutTill checkoutTill = CHECKOUT_TILL_LIST.get(i);
//            GROUP_ROOT.add(checkoutTill.getTillFlowPane(),
//                    1, checkoutTill.getCheckoutId(), 10, 1);
//        }
//
//        //2.7 Add pause button
//        Button setWorkingFlag = new Button("PAUSE supermarket");
//        GROUP_ROOT.add(setWorkingFlag, 13, 13, 4, 4);
//        setWorkingFlag.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                synchronized (WORKING_OBJECT) {
//                    if (WORKING_FLAG == true) {
//                        WORKING_FLAG = false;
//
//                    } else {
//                        WORKING_FLAG = true;
//
//                        WORKING_OBJECT.notifyAll();
//                    }
//                }
//
//                System.out.println("Working flag: " + WORKING_FLAG);
//            }
//        });
//
//
//        //3 prepare scene
//        Scene scene1 = new Scene(GROUP_ROOT, 1280, 900); //Main scene for displaying the supermarket
//        Scene scene2 = new Scene(PAUSE_ROOT, 1280, 900); //Setting scene when press pause butoon
//        Scene scene3 = new Scene(GRAPH_ROOT, 1280, 900); //Scene for displaying the graph
//        Scene scene4 = new Scene(USELESS_ROOT, 1280, 900); //Scene for happy day
//        scene2Build(primaryStage, scene1, scene2,scene3,scene4);
//        scene3Build(primaryStage,scene2,scene3);
//        scene4Build(primaryStage,scene2);
//        //Pause button
//        Button settingButton = new Button("Setting");
//        GROUP_ROOT.add(settingButton, 13, 0, 4, 4);
//
//        settingButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                primaryStage.setScene(scene2);
//            }
//        });
//
//        primaryStage.setTitle("Vietnamese Supermarket");
//        primaryStage.setScene(scene1);
//
//        primaryStage.show();
//    }
//
//    private static void scene4Build(Stage primaryStage, Scene scene2) {
//        Label label = new Label("HAVE A GOOD DAY\nDr. Chris ^^");
//        label.setFont(new Font("Arial", 70));
//        label.setTextFill(Color.CYAN);
//        USELESS_ROOT.add(label,  3,5,10,7);
//
//        USELESS_ROOT.setHgap(80);
//        USELESS_ROOT.setVgap(4);
//        USELESS_ROOT.setPadding(new Insets(5));
//        final int numCols = 16;
//        final int numRows = 16;
//        for (int i = 0; i < numCols; i++) {
//            ColumnConstraints colConst = new ColumnConstraints();
//            colConst.setPrefWidth(10);
//            USELESS_ROOT.getColumnConstraints().add(colConst);
//        }
//        for (int i = 0; i < numRows; i++) {
//            RowConstraints rowConst = new RowConstraints();
//            rowConst.setPrefHeight(50);
//            USELESS_ROOT.getRowConstraints().add(rowConst);
//        }
//    }
//
//    private static void scene3Build(Stage primaryStage, Scene scene2, Scene scene3) {
//        Button backButton = new Button("Back");
//        backButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                primaryStage.setScene(scene2);
//                GRAPH_ROOT.getChildren().remove(1,3);
//            }
//        });
//        GRAPH_ROOT.add(backButton,7,10,4,4);
//
//
//
//
//
//        GRAPH_ROOT.setHgap(80);
//        GRAPH_ROOT.setVgap(4);
//        GRAPH_ROOT.setPadding(new Insets(5));
//        final int numCols = 16;
//        final int numRows = 16;
//        for (int i = 0; i < numCols; i++) {
//            ColumnConstraints colConst = new ColumnConstraints();
//            colConst.setPrefWidth(10);
//            GRAPH_ROOT.getColumnConstraints().add(colConst);
//        }
//        for (int i = 0; i < numRows; i++) {
//            RowConstraints rowConst = new RowConstraints();
//            rowConst.setPrefHeight(50);
//            GRAPH_ROOT.getRowConstraints().add(rowConst);
//        }
//    }
//
//    public static void scene2Build(Stage primaryStage, Scene scene1, Scene scene2, Scene scene3, Scene scene4) {
//        Label labelActiveNewScanner = new Label(String.valueOf(NEW_SCANNER));
//        Button resumeButton = new Button("Resume");
//        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                primaryStage.setScene(scene1);
//            }
//        });
//
//        Button activateNewScanner = new Button("Activate new scanner");
//        activateNewScanner.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if(NEW_SCANNER) NEW_SCANNER = false;
//                else NEW_SCANNER = true;
//                Platform.runLater(new MyRunnable(){
//                    @Override
//                    public void run() {
//                        labelActiveNewScanner.setText(String.valueOf(NEW_SCANNER));
//                    }
//                });
//            }
//        });
//        Button uselessButton = new Button("More");
//        uselessButton.setStyle("-fx-font: 30  arial; -fx-base: #b6e7c9;");
//
//
//        uselessButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if(clickedTime == 0){
//                    clickedTime++;
//                    uselessButton.setText("Dont");
//                    return;
//                }
//                 if(clickedTime == 1){
//                    clickedTime++;
//                    uselessButton.setText("Click");
//                     return;
//                }
//                 if(clickedTime == 2){
//                    clickedTime++;
//                    uselessButton.setText("Me");
//                     return;
//                }
//                 if(clickedTime == 3){
//                    clickedTime++;
//                    uselessButton.setText("More");
//                     return;
//                }
//                 if(clickedTime == 4){
//                    clickedTime++;
//                    uselessButton.setText("Or");
//                     return;
//                }
//                 if(clickedTime == 5){
//                    clickedTime++;
//                    uselessButton.setText("I");
//                     return;
//                }
//                 if(clickedTime == 6){
//                    clickedTime++;
//                    uselessButton.setText("WILL");
//                     return;
//                }
//                if(clickedTime == 7){
//                    clickedTime++;
//                    uselessButton.setText("Say");
//                    return;
//                }
//                if(clickedTime == 8){
//                    clickedTime++;
//                    uselessButton.setText("Something");
//                    return;
//                }
//                if(clickedTime == 9){
//                    clickedTime++;
//                    uselessButton.setText("Like");
//                    return;
//                }
//                if(clickedTime == 10){
//                    clickedTime++;
//                    uselessButton.setText("WILL");
//                    primaryStage.setScene(scene4);
//                }
//
//            }
//        });
//        PAUSE_ROOT.add(uselessButton,13,14,4,4);
//
//        Button graphButton = new Button("Statistic");
//        PAUSE_ROOT.add(graphButton, 7, 3, 4, 4);
//
//        graphButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                primaryStage.setScene(scene3);
//                createUtilGraph();
//                createLeftCustomerGraph();
//
//
//            }
//
//            private void createLeftCustomerGraph() {
//                //Defining the axes
//                NumberAxis xAxis = new NumberAxis(0, SUPERMARKET_WORKING_TIME,1);
//                xAxis.setLabel("Time");
//
//                NumberAxis yAxis = new NumberAxis();
//                yAxis.setLabel("Left customer");
//                LineChart<Number, Number> barChart = new LineChart<>(xAxis, yAxis);
//                barChart.setTitle("Left customer");
//
//                XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
//                series1.setName("Left customer");
//                for (LeftCustomerTuple x: LEFT_CUSTOMER_LIST) {
//                    series1.getData().add(new XYChart.Data<>(x.getSecond(),x.getNumberOfLeft() ));
//                }
//
//
//                barChart.getData().addAll(series1);
//                GRAPH_ROOT.add(barChart,8,1,6,6);
//            }
//
//            private void createUtilGraph() {
//                //Defining the axes
//                CategoryAxis xAxis = new CategoryAxis();
//                xAxis.setCategories(FXCollections.<String>
//                        observableArrayList(Arrays.asList("Till 1", "Till 2", "Till 3", "Till 4", "Till 5", "Till 6")));
//                xAxis.setLabel("Till number");
//
//                NumberAxis yAxis = new NumberAxis();
//                yAxis.setLabel("Utilization");
//                BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
//                barChart.setTitle("Utilization between checkout tills");
//                XYChart.Series<String, Number> series1 = new XYChart.Series<>();
//                series1.setName("Utilization");
//                for (CheckoutTill checkoutTill: CHECKOUT_TILL_LIST) {
//                    series1.getData().add(new XYChart.Data<>("Till "+checkoutTill.getCheckoutId(), checkoutTill.getUtilization()));
//                }
//
//
//                barChart.getData().addAll(series1);
//                GRAPH_ROOT.add(barChart,1,1,6,6);
//            }
//        });
//        PAUSE_ROOT.add(resumeButton, 7, 10, 4, 4);
//        PAUSE_ROOT.add(activateNewScanner, 5, 2, 4, 4);
//        PAUSE_ROOT.add(labelActiveNewScanner, 9, 2, 4, 4);
//
//
//        PAUSE_ROOT.setHgap(80);
//        PAUSE_ROOT.setVgap(4);
//        PAUSE_ROOT.setPadding(new Insets(5));
//        final int numCols = 16;
//        final int numRows = 16;
//        for (int i = 0; i < numCols; i++) {
//            ColumnConstraints colConst = new ColumnConstraints();
//            colConst.setPrefWidth(10);
//            PAUSE_ROOT.getColumnConstraints().add(colConst);
//        }
//        for (int i = 0; i < numRows; i++) {
//            RowConstraints rowConst = new RowConstraints();
//            rowConst.setPrefHeight(50);
//            PAUSE_ROOT.getRowConstraints().add(rowConst);
//        }
//    }
//}
