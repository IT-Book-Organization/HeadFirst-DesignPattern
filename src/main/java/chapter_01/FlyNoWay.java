package main.java.chapter_01;

public class FlyNoWay implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("날 수 없다.");
    }
}
