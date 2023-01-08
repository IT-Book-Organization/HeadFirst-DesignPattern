package main.java.chapter6;

public class Invoker {
    Commend commend;

    public void setCommend(Commend commend) {
        this.commend = commend;
    }

    public void onButtonPushed() {
        commend.execute();
    }
}
