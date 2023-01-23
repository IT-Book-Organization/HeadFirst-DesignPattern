package main.java.chapter12.duck;

public interface QuackObservable {
    void registerObserver(Observer observer);
    void notifyObservers();
}
