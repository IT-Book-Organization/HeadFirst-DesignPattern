package main.java.chapter_07;

public class TurkeyAdapter implements Duck {

	// Target Interface를 상속하고 있기 때문에 클라이언트에서 호환가능하다.
	// Adaptee를 구성하여 Target Interface의 기능을 Adaptee의 기능으로 구현한다.
	Turkey turkey;

	public TurkeyAdapter(Turkey turkey) {
		this.turkey = turkey;
	}

	@Override
	public void quack() {
		turkey.gobble();
	}

	@Override
	public void fly() {
		for(int i=0; i < 5; i++) {
			turkey.fly();
		}
	}
}
