package main.java.chapter3;

public class Application {
    public static void main(String[] args) {
        Beverage beverage = new HouseBlend();
        beverage = new Mocha(beverage);
        beverage = new Mocha(beverage);
        beverage = new Whip(beverage);

        System.out.println(beverage.getDescription() + " $ " + beverage.cost());
    }
}
