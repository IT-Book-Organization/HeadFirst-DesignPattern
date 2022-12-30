public class MuteQuack implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("아무 소리 내지 않음");
    }
}
