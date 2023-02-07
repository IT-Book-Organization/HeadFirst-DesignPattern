# 상태 패턴

> 💡 상태 패턴을 사용하면 객체의 내부 상태가 바뀜에 따라서 객체의 행동을 바꿀 수 있다. 마치 객체의 클래스가 바뀌는 것과 같은 결과를 얻을 수 있다.

<br/>

**상태를 별도의 클래스로 캡슐화**한 다음 **현재 상태를 나타내는 객체에게 행동을 위임**하므로 내부 상태가 바뀔 때 행동이 달라지게 된다는 사실을 쉽게 알 수 있다.

클라이언트의 관점에서 지금 상태에 따라 사용하는 객체의 행동이 완전히 달라져 마치 그 객체가 다른 클래스로부터 만들어진 객체처럼 느껴진다.

상태패턴을 사용하지 않으면, 상태마다의 모든 분기를 if문을 사용하여 분기 처리하여야 한다. 즉, 확장에 닫혀있게 되고, 상태패턴을 이용하면 확장에 비교적 열려있게 된다.

<br/>

### 클래스 다이어그램

<p align="center"><img width="700" alt="State" src="https://user-images.githubusercontent.com/76640167/211810640-308977a8-2def-4d4a-a198-0c2f28e25899.png">

- `Context` : 현재 상태를 구성으로 저장하고 있다. 현재 상태에게 행동을 위임한다.
- `State` : 모든 구상 상태 클래스의 공통 인터페이스다. 모든 상태 클래스에서 같은 인터페이스를 구현하므로 바꿔 쓸 수 있다.
- `ConcreteState`: `Context`로부터 전달된 요청을 자기 나름의 방식으로 구현해서 처리한다. 이렇게 구현하여 `Context`의 상태를 바꾸면 행동도 바뀌게 된다.
- `handle()` : 실질적으로 `Context`의 상태를 변경한다.

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

state 변수에 현재 상태를 저장한다.

또, 존재하는 각각의 상태를 인스턴스 변수로 모두 갖고있어 각각의 `ConcreteState`에서 상태를 변경하고 싶을 때 `Context.get~()`로 상태를 가져와 `Context.setState()`로 현재
상태를 동작에 맞게 변경할 수 있다.

즉, `ConcreteState`도 `Context`를 인스턴스 변수로 가지고 있다.

getter 메소드로 굳이 가져오는 이유는 `ConcreteState` 간의 의존성을 최소화하려고 했기 때문이다.

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

상태의 인터페이스이다. 동일한 행동을 한다면 추상클래스로도 만들 수 있다.

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

그때 상태에 맞는 행동들을 구현하고 있다. 위에서 말했듯이 `Context`를 인스턴스 변수로 갖고있어 행동에 맞게 `Context`의 상태도 변경한다.

<br/>

### 상태의 변경 : Context vs ConcreteState

상태 전환은 `State` 클래스로 제어할 수도 있고, `Context` 클래스로 제어할 수도 있다.

<br/>
예시 코드에서는 상태의 변경을 <b>구상 상태 클래스,</b> <code>ConcreteState</code>가 맡았다.
(상태 전환이 동적으로 결정된다면 상태 클래스 내에서 처리하는 것이 좋음)

상태 전환이 고정되어 있다면 이를 `Context`가 책임져도 무방하다.

<br/>
<b>상태 클래스가 상태 전환을 맡았을 경우, 상태 클래스 사이에 의존성이 생기는 단점이 있다.</b>

이때 예시 코드처럼 `Context 객체의 getter 메서드`를 써서 의존성을 최소화하려고 할 수 있다.

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

정리하자면, 상태 변경을 구상 상태 클래스(ConcreteState)에서 맡게 될 경우, 상태 변경을 위해서는

1. 구상 상태 클래스 내에서, 변경할 구상 상태 클래스를 new로 새로 생성해서 변경하거나
2. `Context`에 있는 상태 클래스를 getter를 통해서 가져와서 변경해야 한다.

<br/>
상태 전환의 흐름을 결정하는 코드를 어느 쪽에 넣는지에 따라서 시스템이 점점 커지게 될 때, 어떤 클래스가 변경에 닫혀 있게 되는지도 결정된다.

**즉, `ConcreteState`가 맡게되면 `ConcreteState`가 변경에 닫혀있게 되고,
`Context`가 맡게되면 `Context`가 변경에 닫혀있게 된다.**

<br/>

### ConcreteState의 공유

상태 객체 내의 자체 상태를 보관하지 않는다면 여러 `Context`에서 상태 객체를 공유하는 것이 가능하다.

상태를 공유할 때는 일반적으로 각 상태를 정적 인스턴스 변수에 할당하는 방법을 사용한다.

<br/>

### 전략 패턴 vs 상태 패턴

두 패턴은 클래스 다이어그램은 같지만 그 용도가 다르다.

전략 패턴에서는 `Context`를 만들 때 행동과 알고리즘을 선택하고 이를 캡슐화하는 반면, 상태 패턴은 **상태를 기반으로 하는 행동을 캡슐화**하고 **행동을 현재 상태에게 위임**한다.