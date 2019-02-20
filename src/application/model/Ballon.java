package application.model;

import application.Main;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ballon extends Thread {
    private int ballonID;
    private static int idCount;
    private StackPane stackPane;

    public Ballon() {
        idCount++;
        ballonID = idCount;

        //Begin create UI
        StackPane stackPane = new StackPane();
        Label label = new Label(": "+ ballonID);
        Circle circle = new Circle();
//        circle.setCenterX(30.0f);
//        circle.setCenterY(13.0f);
        circle.setRadius(70.0f);
        circle.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t) {
//                rectangle.setFill(Color.RED);
                System.out.println("CLICKED!!!!!!!!!!!!!!!!!");
            }
        });

        circle.setFill(Color.ORANGE);
        stackPane.getChildren().addAll(circle,label);
        stackPane.setAccessibleText("Ballon: "+ ballonID);
        this.setStackPane(stackPane);
        //End UI
    }
    public void run(){
        try{
            enter();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void enter() throws InterruptedException {
        //Begin UI: stay at Waiting line
        Platform.runLater(new MyRunnable(this){
            int width = (int) ((Math.random() * ((8 - 0) + 1)) + 0);

            @Override
            public void run() {
                Main.GROUP_ROOT.add(this.getBallon().getStackPane(),width
                        ,20);
//                Main.LANE_LIST.get(0).getLaneFlowPane().getChildren().add(this.getBallon().getStackPane());
            }
        });

        //End UI
        flyUp();
    }

    private void flyUp() throws InterruptedException {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setByY(-1300);
        translateTransition.setNode(this.getStackPane());
        translateTransition.setDuration(Duration.millis(7000));
        translateTransition.play();

        Thread.sleep(17000);

        //Begin deletion in UI
        Platform.runLater(new MyRunnable(this){
            @Override
            public void run() {
                Main.GROUP_ROOT.getChildren().remove(this.getBallon().getStackPane());
                Main.WAITING_AREA_FLOWPANE.getChildren().remove(this.getBallon().getStackPane());
            }
        });
    }

    public int getBallonID() {
        return ballonID;
    }

    public void setBallonID(int ballonID) {
        this.ballonID = ballonID;
    }

    public static int getIdCount() {
        return idCount;
    }

    public static void setIdCount(int idCount) {
        Ballon.idCount = idCount;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }
}
