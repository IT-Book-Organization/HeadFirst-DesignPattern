# 어댑터 패턴과 퍼사드 패턴

## 어댑터 패턴

> 💡 어댑터 패턴은 **특정 클래스 인터페이스를 클라이언트에서 요구하는 다른 인터페이스로 변환**한다. 인터페이스가 호환되지 않아 같이 쓸 수 없었던 클래스를 사용할 수 있게 도와준다.

즉, 어댑터 패턴을 사용하면 인터페이스를 변환해주는 어댑터를 만들어 호환되지 않는 인터페이스를 사용하는 클라이언트를 그대로 활용할 수 있다.

<br/>

<p align="center"><img width="480" alt="Adapter" src="https://user-images.githubusercontent.com/76640167/211241569-1a420f3f-206b-4e67-a50a-0802296b4d6b.png">

1. Client는 원래 사용하고 있던 Target Interface를 사용하고 있다.
2. Adapter는 **Target Interface를 상속**하고, 내부에 **Adaptee 인터페이스(혹은 클래스)를 구성**한다.
3. 구성을 통해 **Adapter의 Target Interface의 기능을 Adaptee가 구현**한다.
4. Client에서 호환이 불가능했던 Adaptee 인터페이스(혹은 클래스)를 Target Interface를 상속한 Adapter 클래스를 사용하여 호환 가능하게 된다.

이러한 접근법은 Adaptee의 서브 클래스도 똑같은 Adapter를 쓸 수 있게 만든다.

<br/>

e.g

Target `Duck`

```java
// 클라이언트에서 이미 사용하고 있던 인터페이스
// 이 인터페이스와 호환시키기 위해 어댑터를 사용하여야 한다.
public interface Duck {
	public void quack();
	public void fly();
}
```

<br/>

Adapter `TurkeyAdapter`

```java
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
```

<br/>

Adaptee `Turkey`

```java
public interface Turkey {
	public void gobble();
	public void fly();
}
```

<br/>

Client `DuckTestDrive`

```java
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
```

<br/>

위 예시에서 볼 수 있는 어댑터가 사용되는 순서는 다음과 같다.

1. 클라이언트에서 Target 인터페이스로 메소드 호출해서 어댑터에 요청
2. 어댑터는 어댑티 인터페이스로 그 요청을 어댑티에 관한 메소드 호출로 변환
3. 클라이언트는 **어댑터가 있다는 사실을 모른체** 어댑티의 호출 결과를 받는다.

<br/>

참고 : 클래스 어댑터

위에서 설명한 어댑터는 객체 어댑터이다. 자바는 다중 상속이 불가능하기 때문에 객체 어댑터만 사용하여야하는데 다중 상속이 가능한 언어에서는 클래스 어댑터를 사용할 수 있다.

<p align="center"><img width="480" alt="class Adapter" src="https://user-images.githubusercontent.com/76640167/211241565-2e599439-ef0d-4b86-9653-f18acdffbef6.png">

상속을 사용한다는 점만 제외하면, 객체 어댑터와 하는 일은 전부 같다.

<br/>

### 활용

Enumeration을 사용하는 구형 코드를 다뤄야할 때도 있지만, 새로운 코드를 만들 때는 비교적 최근에 나온 Iterator를 쓰는게 더 좋다. 이때 어댑터 패턴을 적용할 수 있다.

<p align="center"><img width="480" alt="Iterator" src="https://user-images.githubusercontent.com/76640167/211241562-0a9c57a6-bb5f-4a1a-bda6-807815c5035a.png">

우리의 목적은 Iterator를 사용하는 것이므로 Iterator가 Target 인터페이스가 된다.

위에서 봤던대로 Enumeration이 Adaptee, Iterator를 상속하는 EnumeratioIterator가 Adapter가 되어 Iterator의 기능들을 Adapter가 Adaptee를 구성하여 구현해주면 된다.

<br/>

Adapter `EnumerationIterator`

```java
import java.util.*;

public class EnumerationIterator implements Iterator<Object> {
	Enumeration<?> enumeration;
 
	public EnumerationIterator(Enumeration<?> enumeration) {
		this.enumeration = enumeration;
	}
 
	@Override
	public boolean hasNext() {
		return enumeration.hasMoreElements();
	}

	@Override
	public Object next() {
		return enumeration.nextElement();
	}

	// 이 메소드는 Enumeration이 갖고 있지 않으므로, 지원하지 않는 메소드이다.
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
```

<br/>

## 퍼사드 패턴

> 💡 서브 시스템에 있는 일련의 인터페이스를 통합 인터페이스로 묶어준다. 또한 고수준 인터페이스도 정의하므로 서브시스템을 더 편하게 쓸 수 있다.

<p align="center"><img width="480" alt="facade" src="https://user-images.githubusercontent.com/76640167/211241561-f5dde364-11d4-4556-8b73-62b5dad1b5cd.png">

퍼사드 클래스는 어떤 서브시스템에 속한 일련의 복잡한 클래스를 단순하게 바꿔서 통합한 클래스이다.

퍼사드 패턴을 사용하면 **클라이언트는 서브 시스템과 서로 긴밀하게 연결되지 않아도 된다.**

즉, **클라이언트와 구성요소로 이루어진 서브 시스템을 분리하는 역할**을 한다.

<br/>

퍼사드 패턴을 이해하는데 가장 중요한 것은 **용도**이다.

어댑터 패턴은 인터페이스를 다른 인터페이스로 바꾸기 위해 사용했다면, 퍼사드 패턴은 **인터페이스를 단순화하여 서브 시스템을 더 편리하게 사용하기 위해 사용**된다.

<br/>

e.g

영화를 보기위해 홈시어터를 구축한다고 가정해보자.

<p align="center"><img width="480" alt="movie" src="https://user-images.githubusercontent.com/76640167/211241559-7440ad7f-ac84-46d0-85b1-ef44c5c5a8bd.png">

홈 시어터를 위해 여러 클래스가 필요하고 심지어 서로 복잡하게 얽혀있어서 제대로 사용하려면 많은 인터페이스를 배우고 쓸 수 있어야한다.

클라이언트에서 영화를 보는 것을 구현한다고 가정해보면, 필히 클래스들을 전부 구성하고 하나하나 전부 켜주어야만 할 것이다. 또, 추가적으로 라디오를 듣는다거나 영화가 끝난다거나 하는 일들이 추가되면 클라이언트의 코드는 매우 복잡해질 것이다.

<p align="center"><img width="480" alt="facade movid" src="https://user-images.githubusercontent.com/76640167/211241556-8a5638ee-3b79-4cee-9e6e-e2e24f76fdb3.png">

퍼사드 객체를 사용하여 클라이언트와 클래스들의 긴밀한 연결을 끊고, 클라이언트를 대신하여 복잡한 로직들을 구현한다.

<br/>

Facade `HomeTheaterFacade`

```java
public class HomeTheaterFacade {
	Amplifier amp;
	Tuner tuner;
	StreamingPlayer player;
	CdPlayer cd;
	Projector projector;
	TheaterLights lights;
	Screen screen;
	PopcornPopper popper;
 
	public HomeTheaterFacade(Amplifier amp, 
				 Tuner tuner, 
				 StreamingPlayer player, 
				 Projector projector, 
				 Screen screen,
				 TheaterLights lights,
				 PopcornPopper popper) {
 
		this.amp = amp;
		this.tuner = tuner;
		this.player = player;
		this.projector = projector;
		this.screen = screen;
		this.lights = lights;
		this.popper = popper;
	}
 
	public void watchMovie(String movie) {
		System.out.println("Get ready to watch a movie...");
		popper.on();
		popper.pop();
		lights.dim(10);
		screen.down();
		projector.on();
		projector.wideScreenMode();
		amp.on();
		amp.setStreamingPlayer(player);
		amp.setSurroundSound();
		amp.setVolume(5);
		player.on();
		player.play(movie);
	}
 
 
	public void endMovie() {
		System.out.println("Shutting movie theater down...");
		popper.off();
		lights.on();
		screen.up();
		projector.off();
		amp.off();
		player.stop();
		player.off();
	}

	public void listenToRadio(double frequency) {
		System.out.println("Tuning in the airwaves...");
		tuner.on();
		tuner.setFrequency(frequency);
		amp.on();
		amp.setVolume(5);
		amp.setTuner(tuner);
	}

	public void endRadio() {
		System.out.println("Shutting down the tuner...");
		tuner.off();
		amp.off();
	}
}
```

즉, 클라이언트는 퍼사드의 watchMovie(), endMoive() 등을 호출하여 똑같은 기능을 사용할 수 있다.

이를 통해 클라이언트는 서브 시스템과 긴밀한 연결을 끊고 분리될 수 있다.

<br/>

## 디자인 원칙: 최소 지식 원칙

> 💡 진짜 절친에게만 이야기해야한다.

위 말은 시스템을 디자인할 때 어떤 객체든 그 객체와 상호작용을 하는 클래스의 개수와 상호작용 방식에 주의를 기울여야한다는 뜻이다.

최소 지식 원칙은 소프트웨어 모듈 사이의 결합도를 줄여서 코드의 품질을 높이는 것이 목표이다.

이 원칙을 지키면, 여러 클래스가 복잡하게 얽혀있어 시스템의 한 부분을 변경했을 때 다른 부분까지 줄줄이 고쳐야하는 상황을 미리 방지할 수 있다.

<br/>

이 원칙을 지키기 위해 객체의 모든 메소드는 다음에 해당하는 메소드만을 호출 해야한다.

- 객체 자체
- 메소드에 매개변수로 전달된 객체
- 메소드를 생성하거나 인스턴스를 만든 객체
- 객체에 속하는 구성 요소

<br/>

e.g

```java
public float getTemp() {
	Thermometer thermometer = station.getThermometer();
	return thermometer.getTemperature();
}
```

위 코드를 보면 station 인스턴스로부터 return 받은 객체의 메소드를 호출하여 최소 지식 원칙을 지키지 못하였다. 지키기 위해선 Station 객체에 getTemperature() 메소드를 생성하는 것이 옳다.

퍼사드 패턴에서는 클라이언트의 친구를 퍼사드 객체 하나로 단순화하여, 최소 지식 원칙을 지켰다.