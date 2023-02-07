package main.java.chapter_12.duck;

public interface QuackObservable {
    void registerObserver(Observer observer);
    void notifyObservers();
}
