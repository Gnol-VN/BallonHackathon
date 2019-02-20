package application;

import application.producer_consumer.Producer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    public static GridPane GROUP_ROOT = new GridPane();
//    public static List<Lane> LANE_LIST = new ArrayList<>();
    public static FlowPane WAITING_AREA_FLOWPANE = new FlowPane(Orientation.HORIZONTAL, 5, 5);



    @Override
    public void start(Stage primaryStage) throws Exception {
//        createLane();
        prepareUI(primaryStage);

        //Create producer and consumer
        Producer producer = new Producer();
        producer.setName("Producer 0");
        producer.start();
    }

//    private void createLane() {
//        for (int i = 0; i < 5; i++) {
//            Lane lane = new Lane();
//            LANE_LIST.add(lane);
//        }
//    }


    public static void main(String[] args) {
        launch(args);
    }
    public static void prepareUI(Stage primaryStage) {
        //1. Init the properties and constrains of GROUP_ROOT
        GROUP_ROOT.setHgap(100);
        GROUP_ROOT.setVgap(4);
        GROUP_ROOT.setPadding(new Insets(5));
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
        GROUP_ROOT.setGridLinesVisible(true);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(GROUP_ROOT, 1000, 1000));
        primaryStage.show();
    }
}
