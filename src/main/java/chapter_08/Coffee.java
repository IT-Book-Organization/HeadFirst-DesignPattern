package main.java.chapter_08;

public class Coffee extends CaffeineBeverage{
    @Override
    void brew() {
        System.out.println("커피를 우려내는 중");
    }

    @Override
    void addCondiments() {
        System.out.println("설탕과 우유를 추가하는 중");
    }
}
