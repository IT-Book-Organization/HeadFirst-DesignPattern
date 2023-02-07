package main.java.chapter_06;

public class Receiver {
    private int status = 0;

    public void on() {
        System.out.println("Receiver on");
        this.status = 1;
    }

    public void off() {
        this.status = 0;
    }
}
