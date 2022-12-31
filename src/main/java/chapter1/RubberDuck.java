package main.java.chapter1;

public class RubberDuck extends Duck{
    public RubberDuck() {
        this.flyBehavior = new FlyNoWay();
        this.quackBehavior = new MuteQuack();
    }

    @Override
    public void display() {
        // 오버라이드
        // 적절한 모양을 표시
    }
}
