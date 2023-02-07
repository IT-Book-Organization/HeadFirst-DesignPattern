package main.java.chapter_01;

public class MallardDuck extends Duck{

    public MallardDuck(){
        this.flyBehavior = new FlyWithWings();
        this.quackBehavior = new Quack();
    }

    @Override
    public void display() {
        // 오버라이드
        // 적절한 모양을 표시
    }
}
