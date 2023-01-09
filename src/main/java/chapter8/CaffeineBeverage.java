package main.java.chapter8;

public abstract class CaffeineBeverage {
    // 템플릿 메소드
    // 서브 클래스에서 오버라이드를 막기 위해 final로 선언
    final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    // 서브 클래스에서 구현해야하는 알고리즘의 단계는 abstract 메소드로 구현
    abstract void brew();

    abstract void addCondiments();

    void boilWater() {
        System.out.println("물 끓이는 중");
    }

    void pourInCup() {
        System.out.println("컵에 따르는 중");
    }
}
