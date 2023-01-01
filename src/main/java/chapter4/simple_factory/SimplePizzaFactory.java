package main.java.chapter4.simple_factory;

public class SimplePizzaFactory {

    public Pizza createPizza(String type) {
        Pizza pizza;

        if (type.equals("cheese")) {
            pizza = new CheesePizza();
        } else if (type.equals("pepperoni")) {
            pizza = new PepperoniPizza();
        } else {
            pizza = new CheesePizza();
        }

        return pizza;
    }
}
