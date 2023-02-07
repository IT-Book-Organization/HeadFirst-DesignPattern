package main.java.chapter_06;

public class Invoker {
    Commend commend;

    public void setCommend(Commend commend) {
        this.commend = commend;
    }

    public void onButtonPushed() {
        commend.execute();
    }
}
