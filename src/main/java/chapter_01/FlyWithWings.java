package main.java.chapter_01;

public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("날 수 있다!");
    }
}
