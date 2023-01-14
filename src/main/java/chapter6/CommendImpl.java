package main.java.chapter6;

public class CommendImpl implements Commend{

    private Receiver receiver;

    public CommendImpl(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.on();
    }
}
