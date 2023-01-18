package main.java.chapter12.duck;

public class DuckSimulator {
	public static void main(String[] args) {
		DuckSimulator simulator = new DuckSimulator();
		simulator.simulate();
	}

	void simulate() {
		Quackable mallardDuck = new MallardDuck();
		Quackable redheadDuck = new RedheadDuck();
		Quackable goose = new GooseAdapter(new Goose());
		System.out.println("\n오리 시뮬레이션 게임");
 
		simulate(mallardDuck);
		simulate(redheadDuck);
		simulate(goose);
	}

	// 다형성이 활용됨.
	void simulate(Quackable duck) {
		duck.quack();
	}
}
