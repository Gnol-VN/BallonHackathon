//package application.model;
//
//import application.Main;
//import javafx.application.Platform;
//import javafx.geometry.Orientation;
//import javafx.scene.control.Label;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Lane {
//    private static int idCount;
//    private int laneId;
//    private List<Ballon> ballonList;
//    private StackPane laneStackPane;
//    private FlowPane laneFlowPane;
//
//    public Lane() {
//        idCount++;
//        laneId = idCount;
//        ballonList = new ArrayList<>();
//        System.out.println("Added lane with laneID: " + laneId);
//
//        StackPane stackPane = new StackPane();
//        Label label = new Label("Lane " + idCount);
//        label.setTextFill(Color.WHITE);
//        Rectangle rectangle = new Rectangle(100, 70);
//        rectangle.setFill(Color.GREEN);
//        stackPane.getChildren().addAll(rectangle, label);
//        stackPane.setAccessibleText("Lane " + idCount);
//        this.laneStackPane = stackPane;
//
//        laneFlowPane = new FlowPane(Orientation.VERTICAL,50,50);
//        laneFlowPane.getChildren().add(new Rectangle(20, 20, Color.BEIGE));
//        laneFlowPane.getChildren().add(new Rectangle(20, 20, Color.BEIGE));
//        laneFlowPane.getChildren().add(new Rectangle(20, 20, Color.BEIGE));
//        laneFlowPane.getChildren().add(new Rectangle(20, 20, Color.BEIGE));
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                Main.GROUP_ROOT.add(stackPane, getLaneId(), 2);
//
//            }
//        });
//    }
//
//    public static int getIdCount() {
//        return idCount;
//    }
//
//    public static void setIdCount(int idCount) {
//        Lane.idCount = idCount;
//    }
//
//    public int getLaneId() {
//        return laneId;
//    }
//
//    public void setLaneId(int laneId) {
//        this.laneId = laneId;
//    }
//
//    public List<Ballon> getBallonList() {
//        return ballonList;
//    }
//
//    public void setBallonList(List<Ballon> ballonList) {
//        this.ballonList = ballonList;
//    }
//
//    public StackPane getLaneStackPane() {
//        return laneStackPane;
//    }
//
//    public void setLaneStackPane(StackPane laneStackPane) {
//        this.laneStackPane = laneStackPane;
//    }
//
//    public FlowPane getLaneFlowPane() {
//        return laneFlowPane;
//    }
//
//    public void setLaneFlowPane(FlowPane laneFlowPane) {
//        this.laneFlowPane = laneFlowPane;
//    }
//}
