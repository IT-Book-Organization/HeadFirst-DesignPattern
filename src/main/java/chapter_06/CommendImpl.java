package main.java.chapter_06;

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
