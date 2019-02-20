package application;

import application.producer_consumer.Producer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {
    public static GridPane GROUP_ROOT = new GridPane();
    public static FlowPane WAITING_AREA_FLOWPANE = new FlowPane(Orientation.HORIZONTAL, 5, 5);
    public static int time = 10 * 60;

    //Metric
    public static volatile int SCORE = 0;
    public static String NAME = "Powder Pop";
//    public static int TIME = 2;

    //    Label
    public static Label LABEL_NAME = new Label("Name: " + String.valueOf(NAME));
    public static Label LABEL_SCORE = new Label("Score: " + String.valueOf(SCORE));
    public static Label LABEL_TIMER = new Label("Time: " + String.valueOf("10:00"));


    public static HBox SCORE_BOARD = new HBox();
    public static StackPane SCORE_STACK = new StackPane();


    @Override
    public void start(Stage primaryStage) throws Exception {
//        createLane();
        prepareUI(primaryStage);

        //Create producer and consumer
        Producer producer = new Producer();
        producer.setName("Producer 0");
        producer.start();

//        CountdownThread countdownThread = new CountdownThread(3000);
//        countdownThread.start();

    }



    public static void main(String[] args) {
        launch(args);
    }
    public static void prepareUI(Stage primaryStage) {
        //1. Init the properties and constrains of GROUP_ROOT
        GROUP_ROOT.setHgap(100);
        GROUP_ROOT.setVgap(4);
        GROUP_ROOT.setPadding(new Insets(0));
        final int numCols = 10;
        final int numRows = 10;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(10);
            GROUP_ROOT.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(100);
            GROUP_ROOT.getRowConstraints().add(rowConst);
        }
        GROUP_ROOT.add(WAITING_AREA_FLOWPANE, 0, 10);


//        2. Scoreboard
        SCORE_BOARD.setSpacing(200);
//        2.2 add rectangle
        Rectangle rectangle = new Rectangle(1000, 100, Color.WHITE);
        SCORE_STACK.getChildren().add(rectangle);
        SCORE_STACK.setStyle("-fx-font: 30  arial; -fx-base: #b6e7c9;");

//        2.2 Add label
        SCORE_BOARD.getChildren().add(LABEL_NAME);
        SCORE_BOARD.getChildren().add(LABEL_SCORE);
        SCORE_BOARD.getChildren().add(LABEL_TIMER);
        LABEL_NAME.setTextFill(Color.RED);
        LABEL_SCORE.setTextFill(Color.RED);
        LABEL_TIMER.setTextFill(Color.RED);

        SCORE_STACK.getChildren().add(SCORE_BOARD);

        GROUP_ROOT.add(SCORE_STACK, 0, 0);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (time <= 0) {
                    timer.cancel();
                    //TODO Stop the game
                }

                long mm = time / 60 % 60;
                long ss = time % 60;
                Platform.runLater(() -> LABEL_TIMER.setText("Time: " + (mm > 0 ? (mm + "m") : "") + ss + "s"));
                time--;
            }
        }, 0, 1000);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(GROUP_ROOT, 1000, 1000));
        primaryStage.show();
    }
}
