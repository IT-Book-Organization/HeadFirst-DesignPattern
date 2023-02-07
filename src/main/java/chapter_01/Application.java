package main.java.chapter_01;

public class Application {
    public static void main(String[] args) {
        Duck rubber = new RubberDuck();
        rubber.performFly();
        rubber.setFlyBehavior(new FlyWithWings());
        rubber.performFly();
    }
}
