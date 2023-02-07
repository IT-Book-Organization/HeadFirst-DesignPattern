package main.java.chapter_01;

public abstract class Duck {

    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public void performQuack(){
        quackBehavior.quack();
    }

    public void performFly(){
        flyBehavior.fly();
    }

    public void swim(){

    }

    public void display(){

    }

    public void setFlyBehavior(FlyBehavior fb){
        this.flyBehavior = fb;
    }


//    public void fly(){
//        // 날 수 있다고 추가 -> RubberDuck은 날지 못함. fly override? 맞는걸까?
//        // 또 다른 구현체가 생겼을 때 일일히 따져 주어야만 하기 때문에 아님.
//        // -> 인터페이스를 만들자.
//    }
}
