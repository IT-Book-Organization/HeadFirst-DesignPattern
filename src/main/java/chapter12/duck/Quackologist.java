package main.java.chapter12.duck;

public class Quackologist implements Observer{
    @Override
    public void update(QuackObservable duck) {
        System.out.println("꽥꽥 학자: "+ duck + "이 방금 소리를 쳤다.");
    }
}
