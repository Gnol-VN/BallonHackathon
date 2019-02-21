package application.producer_consumer;
//
import application.Main;
import application.Utils.MathGenerator;
import application.model.Ballon;
import application.model.MyRunnable;
import javafx.application.Platform;

import java.io.FileNotFoundException;

import static application.Main.SCORE_STACK;

public class Producer extends Thread {
    public void run(){
        while (Main.running){
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            Ballon ballon = null;
            try {
                ballon = MathGenerator.generatorMathBalloon();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ballon.start();
            Platform.runLater(new Runnable(){

                @Override
                public void run() {
                    SCORE_STACK.toFront();

                }
            });

        }
    }
}