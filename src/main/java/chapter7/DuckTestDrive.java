package main.java.chapter7;

public class DuckTestDrive {
	public static void main(String[] args) {
		Duck duck = new MallardDuck();

		Turkey turkey = new WildTurkey();
		Duck turkeyAdapter = new TurkeyAdapter(turkey);

		System.out.println("The Turkey says...");
		turkey.gobble();
		turkey.fly();

		System.out.println("\nThe Duck says...");
		testDuck(duck);

		System.out.println("\nThe TurkeyAdapter says...");
		testDuck(turkeyAdapter);

	}

	static void testDuck(Duck duck) {
		duck.quack();
		duck.fly();
	}
}
