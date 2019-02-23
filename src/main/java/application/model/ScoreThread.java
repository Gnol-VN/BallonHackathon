package application.model;

import application.Main;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static application.Main.SCORE;

public class ScoreThread extends Thread{



    public void run(){
        try {
            while(true){
                Thread.sleep(1000);
                String urlString = "http://localhost:8000/get";
                URL url = null;
                try {
                    url = new URL(urlString);
                    URLConnection conn = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            conn.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null){
                        System.out.println(inputLine);
                        Platform.runLater(new MyRunnable(inputLine) {
                            @Override
                            public void run() {
                                SCORE = Integer.valueOf(this.getInputLNEEEE());
                                Main.LABEL_SCORE.setText("Score: " + String.valueOf(SCORE));
                            }
                        });

                    }
                    in.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
