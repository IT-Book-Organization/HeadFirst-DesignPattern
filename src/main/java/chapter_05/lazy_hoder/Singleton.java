package main.java.chapter_05.lazy_hoder;

public class Singleton {

    private Singleton() {}

    // private static inner class 인 LazyHolder
    private static class LazyHolder {
        // LazyHolder 클래스 초기화 과정에서 JVM 이 Thread-Safe 하게 instance 를 생성
        private static final Singleton instance = new Singleton();
    }

    // LazyHolder 의 instance 에 접근하여 반환
    public static Singleton getInstance() {
        return LazyHolder.instance;
    }

}
