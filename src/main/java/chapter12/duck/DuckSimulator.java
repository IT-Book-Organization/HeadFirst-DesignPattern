package main.java.chapter12.duck;

public class DuckSimulator {
	public static void main(String[] args) {
		DuckSimulator simulator = new DuckSimulator();
		AbstractDuckFactory duckFactory = new CountingDuckFactory();
		simulator.simulate(duckFactory);
	}

	void simulate(AbstractDuckFactory duckFactory) {
		Quackable mallardDuck = duckFactory.createMallardDuck();
		Quackable redheadDuck = duckFactory.createRedheadDuck();
		Quackable goose = new GooseAdapter(new Goose());
		System.out.println("\n오리 시뮬레이션 게임");

		Flock flockOfDucks = new Flock();

		flockOfDucks.add(mallardDuck);
		flockOfDucks.add(redheadDuck);
		flockOfDucks.add(goose);

		simulate(flockOfDucks);

		System.out.println(QuackCounter.getNumberOfQuacks());
	}

	// 다형성이 활용됨.
	void simulate(Quackable duck) {
		duck.quack();
	}
}
