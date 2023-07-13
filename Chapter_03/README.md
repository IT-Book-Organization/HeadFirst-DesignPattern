# 데코레이터 패턴

### 디자인 원칙 **OCP(Open-Closed Principle)**

> 💡 클래스는 확장에 대해서는 열려 있어야 하지만 코드 변경에 대해서는 닫혀 있어야 한다.

이해하기 쉽게 말하면, 기존의 코드의 변경 없이 새로운 행동을 추가할 수 있어야 한다는 것이다.

OCP 원칙을 모든 부분에서 준수하려고 한다면 쓸데 없는 시간을 낭비할 수 있고,  
필요 이상으로 복잡하고 이해하기 힘든 코드를 만들게 되는 부작용이 발생할 수 있다.

즉, 디자인한 것들 중 가장 바뀔 가능성이 높은 부분을 중점적으로 살피고, OCP를 적용하는 것이 좋다.

<br/>

### 데코레이터 패턴

> 💡 `데코레이터 패턴(Decorator pattern)`이란 주어진 상황 및 용도에 따라 동적 혹은 정적으로 어떤 객체에 책임을 덧붙이는 패턴으로,  
> 기능 확장이 필요할 때 서브클래싱 대신 쓸 수 있는 유연한 대안이 될 수 있다.

여기서 동적으로 추가할 때는 보통 특정 객체를 결합하는 방식을 사용한다.

<br/>

데코레이터 패턴의 구조를 살펴보자.

<p align="center"><img width="450" alt="img" src="https://user-images.githubusercontent.com/76640167/210151122-0fe4ec81-4f84-4ff9-8d53-9421b453b3d4.png"></p>


ConcreteComponent가 핵심 기능을 담당하는 인스턴스이다.

추가 기능을 담당하는 ConcreteDecorator는 **component를 인스턴스 변수로 가지고 있어**  
Component의 기본 기능과 Decorator의 추가 기능을 모두 제공한다.

<br/>

ConcreteDecorator에는 당연하지만 새로운 메소드를 추가할 수도 있다.

하지만 일반적으로 새로운 메소드를 추가하는 대신 Component에 원래 있던 메소드를 별도의 작업으로 처리해서 새로운 기능을 추가한다.

<br/>

component를 인스턴스 변수로 가지고 있어 Chapter 1의 **상속보다는 구성을 활용한다**의 디자인 법칙을 지키고 있고,  
추가 기능을 위해서라면 ConcreteDecorator를 추가하거나 하면 되기 때문에 위의 **OCP 규칙**을 지킬 수 있다.

(설명보다 예시를 보면 이해가 더 쉽다.)

<br/>

### 데코레이터 패턴 예시

특정 커피의 가격이 얼마인지 출력하는 프로그램을 만든다고 하자. 

그렇다면 커피의 종류는 다양하므로 다음과 같이 구조를 짜게 될 것이다.

<p align="center"><img width="400" alt="img" src="https://user-images.githubusercontent.com/76640167/210151155-8f2815dd-c624-4d63-9b0d-9e018c627d69.png"></p>

<br/>
<br/>

만약 여기서 휘핑크림을 추가한 가격을 계산해야 한다거나 하면 어떻게 디자인할 수 있을까?

데코레이터 패턴을 추가하지 않는다면, Beverage에 setWhip(), hasWhip() 등의 메서드를 넣고 cost()에 로직을 짜야할 것이다.

그러나 새로운 커피가 추가될 때 휘핑크림을 추가할 수 없는 커피가 온다거나 하면, hasWhip() 등의 메서드가 여전히 상속 받게되고  
새로운 재료의 추가에 따라 Beverage의 메서드가 무한정 많아지게 될 것이다.

<br/>

추가 기능을 데코레이터 패턴을 통해 적용한 구조를 보자.

<p align="center"><img width="600" alt="그림1" src="https://user-images.githubusercontent.com/76640167/210564737-88bd306a-a3ab-4cd7-82b3-06bd50aa4e34.png"></p>

이제 재료가 추가되어도 Decorator를 상속 받는 클래스를 구현하면 된다. (OCP, 확장되어도 기존의 코드에는 변경이 없다.)

<br/>

코드를 보자. (편의상 중복되는 Whip과 Mocha, HouseBlend와 Espresso는 하나씩만 보도록 하자.)

<br/>

#### Beverage와 핵심 기능 구현체

```java
public abstract class Beverage {
    String description = "제목 없음";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}

public class Espresso extends Beverage{
    public Espresso() {
        description = "에스프레소";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
```

<br/>

#### Decorator

```java
public abstract class CondimentDecorator extends Beverage{
    Beverage beverage;
    public abstract String getDescription();
}

public class Whip extends CondimentDecorator{
    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 휘핑";
    }

    public double cost() {
        return beverage.cost() + .10;
    }
}
```

<br/>

Decorator는 Beverage를 상속 받아 cost와 getDescription()을 모두 가지고 있고, 이를 상속받은 Whip은 이 두 메서드를 구현하여야 한다.

여기서 주목할 점은 위에서도 설명했지만 Decorator의 구현체에서 핵심 기능 구현체를 참조하기 위하여 필드에 Beverage 생성자를 통해서 값을 주입받는다.

이렇게 주입받은 핵심 기능 구현체의 기능을 기본적으로 사용하고, 추가 기능을 구현하면 된다.

<br/>

클라이언트에서 사용하는 방법도 보도록 하자.

```java
public class Application {
    public static void main(String[] args) {
        Beverage beverage = new HouseBlend();
        beverage = new Mocha(beverage);
        beverage = new Mocha(beverage);
        beverage = new Whip(beverage);

        System.out.println(beverage.getDescription() + " $ " + beverage.cost());
    }
}
```

<br/>

#### 결과

```
하우스 블렌드 커피, 모카, 모카, 휘핑 $ 1.3900000000000001
```

<br/>

재귀적으로 Whip.cost() → Mocha.cost() → Mocha.cost() → HouseBlend.cost() return → Mocha.cost() return → Mocha.cost() return → Whip.cost() return 순서로 호출된다.

즉, <b>장식하고 있는 객체(구성)</b>에 가격을 구하는 작업을 **위임**해서 값을 구하고, 거기에 추가기능을 더해 반환하는 것이다.

<br/>

### 고려할 점과 장단점

**장점**

- 데코레이터 패턴의 장점은 이미 여러가지로 분리되어있는 커피를 상위 클래스에 추가로 뭔가를 작성하는 것이 아니라,  
  데코레이터를 이어붙여서 **추가 기능을 핵심 기능과 분리**할 수 있다는 것이다.
- 상속 대신 구성과 위임으로 동적으로 새로운 행동을 추가할 수 있다.

<br/>

**단점 및 고려해야하는 점**

- 만약, **구상 구성 요소(ConcreteComponent, 위 예시에서는 Esfresso)에서 메서드를 통해 특별 할인 행사를 한다거나 하는 작업을 한다**고 하면 어떨까?  
  데코레이터로 감싸지면 **구상 구성 요소로 어떤 작업을 처리하는 코드는 제대로 작동하지 않을 수 있다.**  
  즉, 구상 구성 요소로 돌아가는 코드를 만들어야 한다면 데코레이터 패턴 사용을 다시 한번 생각해보아야 한다.
- 데코레이터 패턴을 쓰면 관리해야할 객체가 늘어나 코딩할 때 실수할 가능성이 높아져  
  실제로는 **팩토리나 빌더 같은 다른 패턴으로 데코레이터를 만들고 사용한다.**
- 코드가 매우 복잡해질 수 있다

<br/>

### DeepDive : 자바 I/O와 데코레이터 패턴

자바 I/O는 데코레이터 패턴으로 만들어져있다.

<p align="center"><img width="600" alt="img" src="https://user-images.githubusercontent.com/76640167/210151247-45f13cbf-ca26-4176-8b26-a8ff4a550d58.png"></p>

<br/>
<br/>

#### 구성

- InputStream이 추상 구성 요소(맨 위 다이어그램에서 Component)
- FilterInputStream이 Decorator
- 나머지 InputStream을 바로 상속하는 클래스들은 구상 구성요소(ConcreteComponent)
- FilterInputStream을 바로 상속하는 클래스들은 구상 데코레이터(ConcreteDecorator)이다.
- 즉, 이를 활용하여 FilterInputStream을 상속받는 구상 데코레이터를 만들 수 있다.

<br/>

예를 들어, 대문자를 전부 소문자로 바꿔 주는 데코레이터를 만들어보자.

#### Decorator

```java
public class LowerCaseInputStream extends FilterInputStream {

	public LowerCaseInputStream(InputStream in) {
		super(in);
	}
 
	public int read() throws IOException {
		int c = in.read();
		return (c == -1 ? c : Character.toLowerCase((char)c));
	}
		
	public int read(byte[] b, int offset, int len) throws IOException {
		int result = in.read(b, offset, len);
		for (int i = offset; i < offset+result; i++) {
			b[i] = (byte)Character.toLowerCase((char)b[i]);
		}
		return result;
	}
}
```

<br/>

#### 클라이언트

```java
public class InputTest {
	public static void main(String[] args) throws IOException {
		int c;
		
		try (InputStream in2 = 
				new LowerCaseInputStream(
					new BufferedInputStream(
						new FileInputStream("test.txt")))) 
		{
			while((c = in2.read()) >= 0) {
				System.out.print((char)c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```

<br/>

이렇게 구현하면 새로운 데코레이터를 추가하여 java.io에 대문자에서 소문자로 바꿔주는 새로운 기능을 추가할 수 있다.

java.io 패키지에서도 느꼈겠지만, 데코레이터 패턴은 패턴을 알고 있다면 활용성이 크지만 알고 있지 않다면 내부 코드가 복잡해 이해하기에 매우 어렵다.
