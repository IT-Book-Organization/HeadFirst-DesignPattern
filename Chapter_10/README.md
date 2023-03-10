# μν ν¨ν΄

> π‘ μν ν¨ν΄μ μ¬μ©νλ©΄ κ°μ²΄μ λ΄λΆ μνκ° λ°λμ λ°λΌμ κ°μ²΄μ νλμ λ°κΏ μ μλ€. λ§μΉ κ°μ²΄μ ν΄λμ€κ° λ°λλ κ²κ³Ό κ°μ κ²°κ³Όλ₯Ό μ»μ μ μλ€.

<br/>

**μνλ₯Ό λ³λμ ν΄λμ€λ‘ μΊ‘μν**ν λ€μ **νμ¬ μνλ₯Ό λνλ΄λ κ°μ²΄μκ² νλμ μμ**νλ―λ‘ λ΄λΆ μνκ° λ°λ λ νλμ΄ λ¬λΌμ§κ² λλ€λ μ¬μ€μ μ½κ² μ μ μλ€.

ν΄λΌμ΄μΈνΈμ κ΄μ μμ μ§κΈ μνμ λ°λΌ μ¬μ©νλ κ°μ²΄μ νλμ΄ μμ ν λ¬λΌμ Έ λ§μΉ κ·Έ κ°μ²΄κ° λ€λ₯Έ ν΄λμ€λ‘λΆν° λ§λ€μ΄μ§ κ°μ²΄μ²λΌ λκ»΄μ§λ€.

μνν¨ν΄μ μ¬μ©νμ§ μμΌλ©΄, μνλ§λ€μ λͺ¨λ  λΆκΈ°λ₯Ό ifλ¬Έμ μ¬μ©νμ¬ λΆκΈ° μ²λ¦¬νμ¬μΌ νλ€. μ¦, νμ₯μ λ«νμκ² λκ³ , μνν¨ν΄μ μ΄μ©νλ©΄ νμ₯μ λΉκ΅μ  μ΄λ €μκ² λλ€.

<br/>

### ν΄λμ€ λ€μ΄μ΄κ·Έλ¨

<p align="center"><img width="700" alt="State" src="https://user-images.githubusercontent.com/76640167/211810640-308977a8-2def-4d4a-a198-0c2f28e25899.png">

- `Context` : νμ¬ μνλ₯Ό κ΅¬μ±μΌλ‘ μ μ₯νκ³  μλ€. νμ¬ μνμκ² νλμ μμνλ€.
- `State` : λͺ¨λ  κ΅¬μ μν ν΄λμ€μ κ³΅ν΅ μΈν°νμ΄μ€λ€. λͺ¨λ  μν ν΄λμ€μμ κ°μ μΈν°νμ΄μ€λ₯Ό κ΅¬ννλ―λ‘ λ°κΏ μΈ μ μλ€.
- `ConcreteState`: `Context`λ‘λΆν° μ λ¬λ μμ²­μ μκΈ° λλ¦μ λ°©μμΌλ‘ κ΅¬νν΄μ μ²λ¦¬νλ€. μ΄λ κ² κ΅¬ννμ¬ `Context`μ μνλ₯Ό λ°κΎΈλ©΄ νλλ λ°λκ² λλ€.
- `handle()` : μ€μ§μ μΌλ‘ `Context`μ μνλ₯Ό λ³κ²½νλ€.

<br/>

e.g

**Context** `GumballMachine`

```java
public class GumballMachine {

    State soldOutState;
    State noQuarterState;
    State hasQuarterState;
    State soldState;

    State state;
    int count = 0;

    public GumballMachine(int numberGumballs) {
        soldOutState = new SoldOutState(this);
        noQuarterState = new NoQuarterState(this);
        hasQuarterState = new HasQuarterState(this);
        soldState = new SoldState(this);

        this.count = numberGumballs;
        if (numberGumballs > 0) {
            state = noQuarterState;
        } else {
            state = soldOutState;
        }
    }

    public void insertQuarter() {
        state.insertQuarter();
    }

    public void ejectQuarter() {
        state.ejectQuarter();
    }

    public void turnCrank() {
        state.turnCrank();
        state.dispense();
    }

    void releaseBall() {
        System.out.println("A gumball comes rolling out the slot...");
        if (count > 0) {
            count = count - 1;
        }
    }

    int getCount() {
        return count;
    }

    void refill(int count) {
        this.count += count;
        System.out.println("The gumball machine was just refilled; its new count is: " + this.count);
        state.refill();
    }

    void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("\nMighty Gumball, Inc.");
        result.append("\nJava-enabled Standing Gumball Model #2004");
        result.append("\nInventory: " + count + " gumball");
        if (count != 1) {
            result.append("s");
        }
        result.append("\n");
        result.append("Machine is " + state + "\n");
        return result.toString();
    }
}
```

state λ³μμ νμ¬ μνλ₯Ό μ μ₯νλ€.

λ, μ‘΄μ¬νλ κ°κ°μ μνλ₯Ό μΈμ€ν΄μ€ λ³μλ‘ λͺ¨λ κ°κ³ μμ΄ κ°κ°μ `ConcreteState`μμ μνλ₯Ό λ³κ²½νκ³  μΆμ λ `Context.get~()`λ‘ μνλ₯Ό κ°μ Έμ `Context.setState()`λ‘ νμ¬
μνλ₯Ό λμμ λ§κ² λ³κ²½ν  μ μλ€.

μ¦, `ConcreteState`λ `Context`λ₯Ό μΈμ€ν΄μ€ λ³μλ‘ κ°μ§κ³  μλ€.

getter λ©μλλ‘ κ΅³μ΄ κ°μ Έμ€λ μ΄μ λ `ConcreteState` κ°μ μμ‘΄μ±μ μ΅μννλ €κ³  νκΈ° λλ¬Έμ΄λ€.

<br/>

**State** `State`

```java
public interface State {

    public void insertQuarter();

    public void ejectQuarter();

    public void turnCrank();

    public void dispense();

    public void refill();
}
```

μνμ μΈν°νμ΄μ€μ΄λ€. λμΌν νλμ νλ€λ©΄ μΆμν΄λμ€λ‘λ λ§λ€ μ μλ€.

<br/>

**ConcreteState** `HasQuarterState`

```java
public class HasQuarterState implements State {
    GumballMachine gumballMachine;

    public HasQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void insertQuarter() {
        System.out.println("You can't insert another quarter");
    }

    public void ejectQuarter() {
        System.out.println("Quarter returned");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }

    public void turnCrank() {
        System.out.println("You turned...");
        gumballMachine.setState(gumballMachine.getSoldState());
    }

    public void dispense() {
        System.out.println("No gumball dispensed");
    }

    public void refill() {
    }

    public String toString() {
        return "waiting for turn of crank";
    }
}
```

κ·Έλ μνμ λ§λ νλλ€μ κ΅¬ννκ³  μλ€. μμμ λ§νλ―μ΄ `Context`λ₯Ό μΈμ€ν΄μ€ λ³μλ‘ κ°κ³ μμ΄ νλμ λ§κ² `Context`μ μνλ λ³κ²½νλ€.

<br/>

### μνμ λ³κ²½ : Context vs ConcreteState

μν μ νμ `State` ν΄λμ€λ‘ μ μ΄ν  μλ μκ³ , `Context` ν΄λμ€λ‘ μ μ΄ν  μλ μλ€.

<br/>
μμ μ½λμμλ μνμ λ³κ²½μ <b>κ΅¬μ μν ν΄λμ€,</b> <code>ConcreteState</code>κ° λ§‘μλ€.
(μν μ νμ΄ λμ μΌλ‘ κ²°μ λλ€λ©΄ μν ν΄λμ€ λ΄μμ μ²λ¦¬νλ κ²μ΄ μ’μ)

μν μ νμ΄ κ³ μ λμ΄ μλ€λ©΄ μ΄λ₯Ό `Context`κ° μ±μμ Έλ λ¬΄λ°©νλ€.

<br/>
<b>μν ν΄λμ€κ° μν μ νμ λ§‘μμ κ²½μ°, μν ν΄λμ€ μ¬μ΄μ μμ‘΄μ±μ΄ μκΈ°λ λ¨μ μ΄ μλ€.</b>

μ΄λ μμ μ½λμ²λΌ `Context κ°μ²΄μ getter λ©μλ`λ₯Ό μ¨μ μμ‘΄μ±μ μ΅μννλ €κ³  ν  μ μλ€.

```java
@Override
public void dispense() {
        gumballMachine.releaseBall();
        if (gumballMachine.getCount() > 0) {
            gumballMachine.setState(gumballMachine.getNoQuarterState());
        } else {
            System.out.println("Oops, out of gumballs!");
            gumballMachine.setState(gumballMachine.getSoldOutState());
        }
}
```

<br/>

μ λ¦¬νμλ©΄, μν λ³κ²½μ κ΅¬μ μν ν΄λμ€(ConcreteState)μμ λ§‘κ² λ  κ²½μ°, μν λ³κ²½μ μν΄μλ

1. κ΅¬μ μν ν΄λμ€ λ΄μμ, λ³κ²½ν  κ΅¬μ μν ν΄λμ€λ₯Ό newλ‘ μλ‘ μμ±ν΄μ λ³κ²½νκ±°λ
2. `Context`μ μλ μν ν΄λμ€λ₯Ό getterλ₯Ό ν΅ν΄μ κ°μ Έμμ λ³κ²½ν΄μΌ νλ€.

<br/>
μν μ νμ νλ¦μ κ²°μ νλ μ½λλ₯Ό μ΄λ μͺ½μ λ£λμ§μ λ°λΌμ μμ€νμ΄ μ μ  μ»€μ§κ² λ  λ, μ΄λ€ ν΄λμ€κ° λ³κ²½μ λ«ν μκ² λλμ§λ κ²°μ λλ€.

**μ¦, `ConcreteState`κ° λ§‘κ²λλ©΄ `ConcreteState`κ° λ³κ²½μ λ«νμκ² λκ³ ,
`Context`κ° λ§‘κ²λλ©΄ `Context`κ° λ³κ²½μ λ«νμκ² λλ€.**

<br/>

### ConcreteStateμ κ³΅μ 

μν κ°μ²΄ λ΄μ μμ²΄ μνλ₯Ό λ³΄κ΄νμ§ μλλ€λ©΄ μ¬λ¬ `Context`μμ μν κ°μ²΄λ₯Ό κ³΅μ νλ κ²μ΄ κ°λ₯νλ€.

μνλ₯Ό κ³΅μ ν  λλ μΌλ°μ μΌλ‘ κ° μνλ₯Ό μ μ  μΈμ€ν΄μ€ λ³μμ ν λΉνλ λ°©λ²μ μ¬μ©νλ€.

<br/>

### μ λ΅ ν¨ν΄ vs μν ν¨ν΄

λ ν¨ν΄μ ν΄λμ€ λ€μ΄μ΄κ·Έλ¨μ κ°μ§λ§ κ·Έ μ©λκ° λ€λ₯΄λ€.

μ λ΅ ν¨ν΄μμλ `Context`λ₯Ό λ§λ€ λ νλκ³Ό μκ³ λ¦¬μ¦μ μ ννκ³  μ΄λ₯Ό μΊ‘μννλ λ°λ©΄, μν ν¨ν΄μ **μνλ₯Ό κΈ°λ°μΌλ‘ νλ νλμ μΊ‘μν**νκ³  **νλμ νμ¬ μνμκ² μμ**νλ€.