package application.model;

import application.Main;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import sun.audio.AudioPlayer;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static application.Main.SCORE;

public class Ballon extends Thread {
    private int ballonID;
    private static int idCount;
    private boolean right;
    private StackPane stackPane;
    private TranslateTransition translateTransition = new TranslateTransition();

    public Ballon(String text, boolean right) throws FileNotFoundException{
        idCount++;
        ballonID = idCount;

        //Begin create UI
        StackPane stackPane = new StackPane();
        Label label = new Label(text);
        label.setStyle("-fx-font: 22  arial; -fx-base: #b6e7c9; -fx-font-weight: bold ");
        this.right = right;
        Circle circle = new Circle();
//        circle.setCenterX(30.0f);
//        circle.setCenterY(13.0f);
        circle.setRadius(70.0f);
        FileInputStream balloon = new FileInputStream("ball.png");
        Image image1 = new Image(balloon);
        FileInputStream burst = new FileInputStream("burst.png");
        Image image2 = new Image(burst);
        ImageView imageView = new ImageView();
        imageView.setImage(image1);
        stackPane.setStyle("-fx-cursor: hand;");
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
//                rectangle.setFill(Color.RED);
                if(right){
                    String urlString = "http://localhost:8000/increase";
                    URL url = null;
                    try {
                        url = new URL(urlString);
                        URLConnection conn = url.openConnection();
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                conn.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null)
                            System.out.println(inputLine);
                        in.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    //destory
                    //add score
                    System.out.println("+1");
//                    SCORE++;
//                    Main.LABEL_SCORE.setText("Score: " + String.valueOf(SCORE));
                    imageView.setImage(null);
                    label.setText(null);
                    stackPane.getChildren().removeAll();

                    Main.GROUP_ROOT.getChildren().remove(this);
                    AudioClip ALERT_AUDIOCLIP = new AudioClip(Main.class.getResource("true.mp3").toString());
                    ALERT_AUDIOCLIP.play();
                }else{
                    //destory
                    //minus score
                    String urlString = "http://localhost:8000/decrease";
                    URL url = null;
                    try {
                        url = new URL(urlString);
                        URLConnection conn = url.openConnection();
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                conn.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null)
                            System.out.println(inputLine);
                        in.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    imageView.setImage(image2);
                    System.out.println("-1");
//                    SCORE--;
//                    Main.LABEL_SCORE.setText("Score: " + String.valueOf(SCORE));
                    AudioClip ALERT_AUDIOCLIP = new AudioClip(Main.class.getResource("boom.wav").toString());
                    ALERT_AUDIOCLIP.play();
                }
            }
        });

        circle.setFill(Color.ORANGE);
//        stackPane.getChildren().addAll(circle,label);
        stackPane.getChildren().addAll(imageView,label);
        stackPane.setAccessibleText("Ballon: "+ ballonID);
        this.setStackPane(stackPane);
        //End UI
    }
    public void playMedia(Media m){
        if (m != null){
            MediaPlayer mp = new MediaPlayer(m);
            mp.play();
        }
    }
    public void run() {
        try {
            enter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void enter() throws InterruptedException {
        //Begin UI: stay at Waiting line
        Platform.runLater(new MyRunnable(this) {
            int width = (int) ((Math.random() * ((8 - 0) + 1)) + 0);

            @Override
            public void run() {
                Main.GROUP_ROOT.add(this.getBallon().getStackPane(), width
                        , 20);
//                Main.LANE_LIST.get(0).getLaneFlowPane().getChildren().add(this.getBallon().getStackPane());
            }
        });

        //End UI
        flyUp();
    }

    private void flyUp() throws InterruptedException {
        translateTransition.setByY(-1300);
        translateTransition.setNode(this.getStackPane());
        translateTransition.setDuration(Duration.millis(9000));
        translateTransition.play();

        Thread.sleep(17000);

        //Begin deletion in UI
        Platform.runLater(new MyRunnable(this) {
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
