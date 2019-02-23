package application.utils;

import application.model.Ballon;

import java.io.FileNotFoundException;

public class MathGenerator {
    public static Ballon generatorMathBalloon() throws FileNotFoundException {
        int n1 = (int) (Math.random() * 10);
        int n2 = (int) (Math.random() * 10);

        int answer = n1 * n2;
        boolean right = true;
        if (Math.random() > 0.5) {
            while (answer == n1 * n2) {
                right = false;
                answer = (int) (Math.random() * 100);
            }
        }
        return new Ballon(n1 + "×" + n2 + "=" + answer, right);
    }

    public static void main(String[] args) {
        int n1 = (int) (Math.random() * 10);
        int n2 = (int) (Math.random() * 10);
        System.out.println(n1 + " " + n2);
    }
}
