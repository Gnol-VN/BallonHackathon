package application.producer_consumer;
//
import application.Utils.MathGenerator;
import application.model.Ballon;
import javafx.application.Platform;

import static application.Main.SCORE_STACK;
//
//import static application.Supermarket.*;
//
///**
// * Created by Long laptop on 10/9/2018.
// */
//public class Producer extends Thread {
//
//    /**
//     * Sleep 100ms to stimulate the entering people
//     * Create customer and make this customer look for a queue
//     */
//    public void run() {
////        for (int i = 0; i < Supermarket.NUMBER_OF_CUSTOMER; i++) {
//        this.setPriority(1);
//        while (true) {
//            synchronized (WORKING_OBJECT){
//                while(WORKING_FLAG == false){
//                    try {
//                        WORKING_OBJECT.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    WORKING_OBJECT.notifyAll();
//
//                }
//            }
//            try {
//                Thread.sleep(SPAWN_TIME);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////              Create customer and make this customer look for a queue
//            Customer customer = new Customer();
//
//            ENQUEUE_REQUESTED++;
//            customer.setName(String.valueOf(customer.getCustomerId()));
//            customer.start();
//
//        }
//    }
//
//}

public class Producer extends Thread {
    public void run(){
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            Ballon ballon = MathGenerator.generatorMathBalloon();
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
