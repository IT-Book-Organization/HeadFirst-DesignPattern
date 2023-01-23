# 복합 패턴


> 💡 여러 패턴을 함께 사용하여 디자인 문제를 해결하는 방법을 복합 패턴이라한다.

<br/>

## 오리 시뮬레이션 게임에 다양한 패턴 적용해보기

오리 시뮬레이션 게임을 처음부터 다시 만들면서 기능을 추가해보자.

<br/>

오리는 모두 울음소리를 낼 수 있으므로 `Quackable 인터페이스`를 추가한다.

```java
public interface Quackable {
    public void quack();
}
```

<br/>

`Quackable`을 구현해서 오리 클래스를 만든다.

```java
public class MallardDuck implements Quackable{
    @Override
    public void quack() {
        System.out.println("꽥꽥");
    }
}

// 등등의 여러 오리들
```

<br/>

이제 다형성을 활용해 모든 `Quackable 구현체`에서 quack 메소드들을 실행할 수 있다.

```java
public class DuckSimulator {
	public static void main(String[] args) {
		DuckSimulator simulator = new DuckSimulator();
		simulator.simulate();
	}

	void simulate() {
		Quackable mallardDuck = new MallardDuck();
		Quackable redheadDuck = new RedheadDuck();
		
		System.out.println("\n오리 시뮬레이션 게임");
 
		simulate(mallardDuck);
		simulate(redheadDuck);
	}
 
	// 다형성이 활용됨.
	void simulate(Quackable duck) {
		duck.quack();
	}
}
```

<br/>

### 어댑터 패턴

이때 다르게 우는 Goose 객체가 생겼다고 하자. Goose 객체는 거위라서 quack() 메소드가 아니라 honk() 메소드를 통해서만 울 수 있다.

이때, Goose 는 `Quackable`을 상속하여 `quack 메소드`를 실행할 수 없으므로 어댑터 패턴을 사용하여야한다.

<br/>

**Goose**

```java
public class Goose {
	public void honk() {
		System.out.println("끽끽");
	}
}
```

<br/>

**GooseAdapter**

```java
public class GooseAdapter implements Quackable {
	Goose goose;
 
	public GooseAdapter(Goose goose) {
		this.goose = goose;
	}
 
	public void quack() {
		goose.honk();
	}

}
```

원래 객체를 구성으로 하여 `Quackable`을 구현하는 `GooseAdapter` 구현하였다.

<br/>

**Simulator**

```java
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
```

<br/>

### 데코레이터 패턴

`Quackable`에 **몇번의 소리를 내는지 기능 추가**를 위해 데코레이터 패턴을 사용해보자.

<br/>

**decorator**

```java
public class QuackCounter implements Quackable{
    Quackable duck;
    static int numberOfQuacks;

    public QuackCounter(Quackable duck) {
        this.duck = duck;
    }

    @Override
    public void quack() {
        duck.quack();
        numberOfQuacks++;
    }

    public static int getNumberOfQuacks() {
        return numberOfQuacks;
    }
}
```

Duck 객체를 감싸 객체에 기능을 추가할 수 있다.

<br/>

**Simulator**

```java
public class DuckSimulator {
	public static void main(String[] args) {
		DuckSimulator simulator = new DuckSimulator();
		simulator.simulate();
	}

	void simulate() {
		Quackable mallardDuck = new QuackCounter(new MallardDuck());
		Quackable redheadDuck = new QuackCounter(new RedheadDuck());
		Quackable goose = new GooseAdapter(new Goose());
		System.out.println("\n오리 시뮬레이션 게임");
 
		simulate(mallardDuck);
		simulate(redheadDuck);
		simulate(goose);

		System.out.println(QuackCounter.getNumberOfQuacks());
	}

	// 다형성이 활용됨.
	void simulate(Quackable duck) {
		duck.quack();
	}
}
```

<br/>

### 팩토리 패턴

위 데코레이터 패턴을 사용하려면 객체를 무조건 포장을 해주어야만 한다.

즉, 객체를 생성하는 일도 잘 관리 되어야한다.

이를 위해 팩토리 패턴을 적용해보자.

<br/>

**Factory**

```java
public abstract class AbstractDuckFactory {
    public abstract Quackable createMallardDuck();
    public abstract Quackable createRedheadDuck();
}

public class CountingDuckFactory extends AbstractDuckFactory{
    @Override
    public Quackable createMallardDuck() {
        return new QuackCounter(new MallardDuck());
    }

    @Override
    public Quackable createRedheadDuck() {
        return new QuackCounter(new RedheadDuck());
    }
}
```

추상 클래스로 만들어 개수를 셀 수 있는 `DuckFactory` 뿐만 아니라 그냥 `Duck`을 반환하는 `Factory`도 구현하여 바꿀 수 있다.

<br/>

**Simulator**

```java
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
 
		simulate(mallardDuck);
		simulate(redheadDuck);
		simulate(goose);

		System.out.println(QuackCounter.getNumberOfQuacks());
	}

	// 다형성이 활용됨.
	void simulate(Quackable duck) {
		duck.quack();
	}
}
```

팩토리 패턴을 사용하여 객체의 생성도 관리할 수 있다.

<br/>

### 컴포지트 패턴과 반복자 패턴

위의 여러 오리 무리들을 시뮬레이터에서 하나하나 관리하는 것은 좋은 코드가 아니다.

객체들로 구성된 컬렉션을 개별 객체와 같은 방식으로 다룰 수 있게 해주는 컴포지트 패턴을 사용해 오리들을 모두 관리하는 클래스를 만들어보자.

또, 이 컬렉션에 반복자 패턴을 적요해보자.

<br/>

**Composite**

```java
public class Flock implements Quackable{
    List<Quackable> quackers = new ArrayList<>();

    public void add(Quackable quacker) {
        quackers.add(quacker);
    }

    @Override
    public void quack() {
        // 반복자 패턴
        Iterator<Quackable> iterator = quackers.iterator();
        while (iterator.hasNext()) {
            Quackable quacker = iterator.next();
            quacker.quack();
        }
    }
}
```

<br/>

**Simulator**

```java
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
```

<br/>

**안정성** vs **투명성**

컴포지트 패턴을 생각할 때, 복합 객체(부모 노드)와 잎 객체(자식 노드)에 똑같은 메소드가 들어있었다. 즉, 잎 객체에는 필요 없는 복합 객체 메소드들이 포함되어야했고, 복합 객체에는 필요 없는 잎 객체 메소드들이 포함되어야 했다. 이를 통해 **복합 객체와 잎 객체 사이의 차이점을 투명하게 만들 수 있어** 클라이언트는 어떤 객체를 다루고 있는지 신경쓰지 않고 무조건 같은 메소들르 호출하면 됐다.

위 코드에서는 복합 객체에서 자식을 관리하는 메소드를 복합 객체에만 넣었다. 이렇게 하면 잎 객체들은 add() 메소드 자체를 호출할 수 없으므로 **안전성을 확보하긴 하지만, 복합 객체와 잎 객체 사이의 차이가 두드러져 투명성을 잃게 된다.** 즉, 클라이언트에서는 어떤 객체를 복합 객체처럼 사용하려면 그 객체가 `Flock`인지 아닌지를 확실히 해야한다.

<br/>

### 옵저버 패턴

오리가 울 때 알림을 받는 오리학자 클래스를 만들기 위해 옵저버 패턴을 사용해보자.

옵저버 패턴을 만들 때, **`Observable 보조 클래스`를 만들어서 실제 `QuackObservable`(실제 오리 객체들)에 구성으로 포함하여 등록 옵저버에 등록 및 연락하는 기능을 캡슐화**해보자. 

즉, 실제 등록 및 연락 코드는 `Observable`에 포함되고, `QuackObservable`이 필요한 작업을 Observable 보조 클래스에 전부 위임하게 만들 수 있다.

<br/>

**QuackObservable**

```java
public interface QuackObservable {
    void registerObserver(Observer observer);
    void notifyObservers();
}

// 모든 울 수 있는 오리 객체들이 관찰 대상
public interface Quackable extends QuackObservable{
    public void quack();
}

// 구현체
public class MallardDuck implements Quackable{

    Observable observable;

    public MallardDuck() {
        this.observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("꽥꽥");
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        observable.notifyObservers();
    }
}
```

실제 옵저버에 등록 및 연락하는 기능을 할 Observable 클래스를 구성으로 사용하고 있다.

`Quackable`이 `QuackObservable`을 상속하게 만들었으므로, `Quackable`을 상속받는 모든 객체에서도 `QuackObservable` 기능을 구현해야만한다.

<br/>

**Observable**

```java
public class Observable implements QuackObservable{
    List<Observer> observers = new ArrayList<>();
    QuackObservable duck;

    public Observable(QuackObservable duck) {
        this.duck = duck;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(duck);
        }
    }
}
```

`Observable`은 여러 옵저버를 가질 수 있으므로 Observer 컬렉션으로 구성하고 있고, 구성하고 있는 옵저버들에 연락을 돌리는 코드를 실질적으로 맡고 있다.

<br/>

**Observer**

```java
public interface Observer {
    void update(QuackObservable duck);
}

public class Quackologist implements Observer{
    @Override
    public void update(QuackObservable duck) {
        System.out.println("꽥꽥 학자: "+ duck + "이 방금 소리를 쳤다.");
    }
}
```

<br/>

**Composite**

```java
public class Flock implements Quackable{
    List<Quackable> quackers = new ArrayList<>();

    public void add(Quackable quacker) {
        quackers.add(quacker);
    }

    @Override
    public void quack() {
        // 반복자 패턴
        Iterator<Quackable> iterator = quackers.iterator();
        while (iterator.hasNext()) {
            Quackable quacker = iterator.next();
            quacker.quack();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        for (Quackable duck : quackers) {
            duck.registerObserver(observer);
        }
    }

    @Override
    public void notifyObservers() {

    }
}
```

`Composite 객체`에서 오리들을 관리하고 있으므로 `Composite 객체`에 있는 오리들을 한번에 등록할 수 있게끔 메소드를 작성했다.

<br/>

**Simulator**

```java
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

		Quackologist quackologist = new Quackologist();
		flockOfDucks.registerObserver(quackologist);

		simulate(flockOfDucks);

		System.out.println(QuackCounter.getNumberOfQuacks());
	}

	// 다형성이 활용됨.
	void simulate(Quackable duck) {
		duck.quack();
	}
}
```

`Composite 객체`에서 한번에 옵저버를 등록한다.

---

위에서 많은 패턴을 사용하였는데 이렇게 패턴을 섞어서 사용했다고 복합 패턴이라고 할 수 없다. 복합 패턴은 몇개의 패턴을 복합적으로 사용해서 일반적인 문제를 해결할 수 있어야한다.

<br/>

# MVC 패턴

MVC 패턴은 모델-뷰-컨트롤러를 줄인 표현이다.

<p align="center"><img width="680" alt="MVC" src="https://user-images.githubusercontent.com/76640167/213649386-400dfd66-34bb-4f21-80d4-d712b414668a.png">

- `View`
    - **모델을 표현하는 방법을 제공**한다.
    - 변경이 일어나면 변경 통지에 대한 처리방법을 구현해야한다. 뷰의 변경을 `Controller` 에게 전달하는 방법을 구현해야한다.
    - 표시할 때 필요한 상태와 데이터는 모델에서 직접 가져온다. 이는 관점에 따라 다르다. 모델이나 컨트롤러를 몰라야한다는 관점도 있다. 확실한건 모델을 사용한 후에 정보를 저장해서는 안된다.
- `Controller`
    - 사용자로부터 입력을 받으며 입력받은 내용이 모델에게 어떤 의미가 있는지 파악한다.
    - `Model` 과 뷰는 서로를 모르고, 변경을 외부로 알리고, 수신하는 방법을 가지고 있어 이를 컨트롤러가 중재하기 위해 `Model` 과 `View`를 구성하고 있어야한다.
- `Model`
    - 모델은 모든 데이터, 상태와 애플리케이션 로직을 가지고 있어야한다.
    - 뷰와 컨트롤러에서 모델의 상태를 조작하거나 가져올 때 필요한 인터페이스를 제공해야한다.
    - 모델이 자신의 상태 변화를 `옵저버(View)` 에게 통지하는 방법을 구현해야 한다.
    - `View`나 `Controller`에 대한 어떠한 정보도 알지 말아야한다.
<br/>

1. **사용자는 뷰에만 접촉할 수 있다.** 뷰는 모델을 보여주는 창이라고 할 수 있다. 사용자가 뷰에 뭔가를 하면 뷰는 무슨 일이 일어났는지 컨트롤러에게 알려주고 컨트롤러는 상황에 맞게 작업한다.
2. **컨트롤러가 모델에게 상태를 변경하라고 요청한다.** 컨트롤러는 사용자의 요청을 받아서 해석하고(소리를 키우는 버튼, 내리는 버튼 등등), 모델을 어떤 식으로 조작해야하는지 결정한다.
3. **컨트롤러가 뷰를 변경해달라고 요청할 수도 있다.** 컨트롤러는 뷰로부터 어떤 행동을 받았을 때, 그 행동의 결과로 뷰에게 뭔가를 바꿔 달라고 할 수 있다. 예를 들어, 인터페이스에 있는 버튼이나 메뉴를 비활성화할 수 있다.
4. **상태가 변경되면 모델이 뷰에게 그 사실을 알린다.** 사용자의 행동이나 다른 내부적인 변화등으로 모델에서 뭔가가 바뀌면 모델은 뷰에게 상태가 변경되었다고 알린다.
5. 뷰가 모델에게 상태를 요청한다. 뷰는 화면에 표시할 상태를 모델로부터 직접 가져온다. 모델이 뷰에게 새로운 곡이 재생되었다고 알려주면 뷰는 모델에게 곡 제목을 요청하고, 그것을 받아서 화면에 표시한다.

<br/>

위 방법을 통해 모델, 뷰, 컨트롤러가 서로 느슨하게 결합되어 깔끔하면서도 유연한 구현이 가능하다.

새로운 모델을 기존의 뷰와 컨트롤러에서 연결해서 쓸 때는 어댑터 패턴을 활용할 수 있다.

### 웹과 MVC

수많은 웹 프레임워크에도 MVC가 적용되어있다.

- `thin 클라이언트 접근법` : 대부분의 모델과 뷰, 그리고 컨트롤러가 모두 서버로 들어가고 브라우저는 뷰를 화면에 표시하고 컨트롤러로 입력을 받아오는 역할만 한다.
- `단일 페이지 애플리케이션` : 대부분의 모델과 뷰, 그리고 컨트롤러까지 클라이언트에 들어간다.

<br/>

### 옵저버 패턴

<p align="center"><img width="680" alt="Observer" src="https://user-images.githubusercontent.com/76640167/213649384-7674e184-1aca-4f7f-9b6d-6d40266ef2fa.png">

모델은 옵저버 패턴을 써서 상태가 변경되었을 때 그 모델과 연관된 객체들에게 연락한다.

이를 통해 모델을 뷰와 컨트롤러로부터 완전히 독립시킬 수 있다.

<br/>

### 전략 패턴

<p align="center"><img width="680" alt="Strategy" src="https://user-images.githubusercontent.com/76640167/213649382-9906ecc5-8dbc-456a-8327-fe9184dc2507.png">

뷰와 컨트롤러는 고전적인 전략 패턴으로 구현되어있다.

뷰는 애플리케이션의 겉모습에만 신경을 쓰고, 인터페이스의 행동을 결정하는 일은 모두 컨트롤러에 캡슐화한다.

또, 사용자가 요청한 내역을 처리하려고 모델과 얘기하는 일을 컨트롤러가 맡게되어 모델과 뷰를 분리할 수 있다.

<br/>

### 컴포지트 패턴

<p align="center"><img width="680" alt="Composite" src="https://user-images.githubusercontent.com/76640167/213649369-9e84f9c3-4bc1-4c2d-ae17-f5144b7720f7.png">

디스플레이는 여러 단계로 겹쳐있는 윈도우, 패널, 버튼 등으로 구성되어있다.

컨트롤러가 뷰에게 화면을 갱신해 달라고 요청하면 최상위 뷰 구성 요소에게만 화면을 갱신하라고 얘기하면 된다.

<br/>

## MVC로 BPM 제어도구 만들기

- `View`는 사용자가 BPM 제어 도구를 설정하는 화면을 표현하는 책임을 갖는다.
- `Model`은 비트를 조절하고 노래를 내보내는 등의 작업을 한다.
- `Controller`는 `View`와 `Model` 사이에서 사용자의 입력을 바탕으로 `Model`이 해야하는 적절한 행동으로 바꾼다.

<br/>

### Model BeatModel

Model의 내부 상태가 바뀌었을 때 뷰에게 바뀌었다는 것을 공유해야하므로, 옵저버 패턴을 사용해야한다.

<br/>

**Observer** `BeatObserver`, `BPMObserver`

```java
public interface BeatObserver {
	void updateBeat();
}

public interface BPMObserver {
	void updateBPM();
}
```

<br/>

**Model** `BeatModel`

```java
public interface BeatModelInterface {
	void initialize();
  
	void on();
  
	void off();
  
    void setBPM(int bpm);
  
	int getBPM();

	// 모델은 옵저버 패턴을 사용한다.
	void registerObserver(BeatObserver o);
  
	void removeObserver(BeatObserver o);
  
	void registerObserver(BPMObserver o);
  
	void removeObserver(BPMObserver o);
}

public class BeatModel implements BeatModelInterface, Runnable {
	List<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
	List<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();
	int bpm = 90;
	// 스레드와 flag를 통해서 노래 재생
	Thread thread;
	boolean stop = false;
	Clip clip;

	public void initialize() {
		try {
			File resource = new File("clap.wav");
			clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(AudioSystem.getAudioInputStream(resource));
		}
		catch(Exception ex) {
			System.out.println("Error: Can't load clip");
			System.out.println(ex);
		}
	}

	public void on() {
		bpm = 90;
		//notifyBPMObservers();
		thread = new Thread(this);
		stop = false;
		thread.start();
	}

	public void off() {
		stopBeat();
		stop = true;
	}

	public void run() {
		while (!stop) {
			playBeat();
			notifyBeatObservers();
			try {
				Thread.sleep(60000/getBPM());
			} catch (Exception e) {}
		}
	}

	public void setBPM(int bpm) {
		this.bpm = bpm;
		notifyBPMObservers();
	}

	public int getBPM() {
		return bpm;
	}

	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);
	}

	public void notifyBeatObservers() {
		for(int i = 0; i < beatObservers.size(); i++) {
			BeatObserver observer = (BeatObserver)beatObservers.get(i);
			observer.updateBeat();
		}
	}

	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	public void notifyBPMObservers() {
		for(int i = 0; i < bpmObservers.size(); i++) {
			BPMObserver observer = (BPMObserver)bpmObservers.get(i);
			observer.updateBPM();
		}
	}

	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if (i >= 0) {
			beatObservers.remove(i);
		}
	}

	public void removeObserver(BPMObserver o) {
		int i = bpmObservers.indexOf(o);
		if (i >= 0) {
			bpmObservers.remove(i);
		}
	}

	public void playBeat() {
		clip.setFramePosition(0);
		clip.start();
	}
	public void stopBeat() {
		clip.setFramePosition(0);
		clip.stop();
	}

}
```

즉, `Model`은 각종 로직을 담당하고 상태가 바뀌었을 때 `View`에게 연락을 취하기 위해 옵저버 패턴을 사용한다.

<br/>

### View DJView

현재 BPM과 통통 튀는 막대를 보여주는 화면과 제어용 인터페이스 두개의 화면을 만든다.

<br/>

**View** `DJView`

```java
public class DJView implements ActionListener, BeatObserver, BPMObserver {
	BeatModelInterface model;
	ControllerInterface controller;
    JFrame viewFrame;
    JPanel viewPanel;
	BeatBar beatBar;
	JLabel bpmOutputLabel;
    JFrame controlFrame;
    JPanel controlPanel;
    JLabel bpmLabel;
    JTextField bpmTextField;
    JButton setBPMButton;
    JButton increaseBPMButton;
    JButton decreaseBPMButton;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem startMenuItem;
    JMenuItem stopMenuItem;

    public DJView(ControllerInterface controller, BeatModelInterface model) {	
		this.controller = controller;
		this.model = model;
		model.registerObserver((BeatObserver)this);
		model.registerObserver((BPMObserver)this);
    }
    
    public void createView() {
		// Create all Swing components here
        viewPanel = new JPanel(new GridLayout(1, 2));
        viewFrame = new JFrame("View");
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.setSize(new Dimension(100, 80));
        bpmOutputLabel = new JLabel("offline", SwingConstants.CENTER);
		beatBar = new BeatBar();
		beatBar.setValue(0);
        JPanel bpmPanel = new JPanel(new GridLayout(2, 1));
		bpmPanel.add(beatBar);
        bpmPanel.add(bpmOutputLabel);
        viewPanel.add(bpmPanel);
        viewFrame.getContentPane().add(viewPanel, BorderLayout.CENTER);
        viewFrame.pack();
        viewFrame.setVisible(true);
	}
  

    // 사용자의 입력에 따라 컨트롤러 호출
    public void createControls() {
		// Create all Swing components here
        JFrame.setDefaultLookAndFeelDecorated(true);
        controlFrame = new JFrame("Control");
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlFrame.setSize(new Dimension(100, 80));

        controlPanel = new JPanel(new GridLayout(1, 2));

        menuBar = new JMenuBar();
        menu = new JMenu("DJ Control");
        startMenuItem = new JMenuItem("Start");
        menu.add(startMenuItem);
        startMenuItem.addActionListener((event) -> controller.start());
        // was....
        /*
        startMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.start();
            }
        });
        */
        stopMenuItem = new JMenuItem("Stop");
        menu.add(stopMenuItem); 
        stopMenuItem.addActionListener((event) -> controller.stop());
        // was...
        /*
        stopMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.stop();
            }
        });
        */
        JMenuItem exit = new JMenuItem("Quit");
        exit.addActionListener((event) -> System.exit(0));
        // was...
        /*
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        */

        menu.add(exit);
        menuBar.add(menu);
        controlFrame.setJMenuBar(menuBar);

        bpmTextField = new JTextField(2);
        bpmLabel = new JLabel("Enter BPM:", SwingConstants.RIGHT);
        setBPMButton = new JButton("Set");
        setBPMButton.setSize(new Dimension(10,40));
        increaseBPMButton = new JButton(">>");
        decreaseBPMButton = new JButton("<<");
        setBPMButton.addActionListener(this);
        increaseBPMButton.addActionListener(this);
        decreaseBPMButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		buttonPanel.add(decreaseBPMButton);
		buttonPanel.add(increaseBPMButton);

        JPanel enterPanel = new JPanel(new GridLayout(1, 2));
        enterPanel.add(bpmLabel);
        enterPanel.add(bpmTextField);
        JPanel insideControlPanel = new JPanel(new GridLayout(3, 1));
        insideControlPanel.add(enterPanel);
        insideControlPanel.add(setBPMButton);
        insideControlPanel.add(buttonPanel);
        controlPanel.add(insideControlPanel);
        
        bpmLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        controlFrame.getRootPane().setDefaultButton(setBPMButton);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);

        controlFrame.pack();
        controlFrame.setVisible(true);
    }

	public void enableStopMenuItem() {
    	stopMenuItem.setEnabled(true);
	}

	public void disableStopMenuItem() {
    	stopMenuItem.setEnabled(false);
	}

	public void enableStartMenuItem() {
    	startMenuItem.setEnabled(true);
	}

	public void disableStartMenuItem() {
    	startMenuItem.setEnabled(false);
	}

    public void actionPerformed(ActionEvent event) {
		if (event.getSource() == setBPMButton) {
			int bpm = 90;
			String bpmText = bpmTextField.getText();
			if (bpmText == null || bpmText.contentEquals("")) {
				bpm = 90;
			} else {
				bpm = Integer.parseInt(bpmTextField.getText());
			}
        	controller.setBPM(bpm);
		} else if (event.getSource() == increaseBPMButton) {
			controller.increaseBPM();
		} else if (event.getSource() == decreaseBPMButton) {
			controller.decreaseBPM();
		}
    }

    // 옵저버 패턴
    // 모델의 상태가 변경되었다는 연락을 받으면 View의 메소드가 불려서 화면 변경
	public void updateBPM() {
		if (model != null) {
			int bpm = model.getBPM();
			if (bpm == 0) {
				if (bpmOutputLabel != null) {
        			bpmOutputLabel.setText("offline");
				}
			} else {
				if (bpmOutputLabel != null) {
        			bpmOutputLabel.setText("Current BPM: " + model.getBPM());
				}
			}
		}
	}
  
	public void updateBeat() {
		if (beatBar != null) {
			 beatBar.setValue(100);
		}
	}
}
```

옵저버 패턴에 따라 모델의 상태가 변경되면 변경되었다는 알림을 받는다. 이때, push 방식이 아닌 pull 방식을 사용하여 객체로부터 데이터를 가져오는 것을 알 수 있다.

또 입력에 따라 controller를 호출하고, controller는 그에 알맞는 처리를 한다.

<br/>

### Controller BeatController

```java
public interface ControllerInterface {
    void start();
    void stop();
    void increaseBPM();
    void decreaseBPM();
    void setBPM(int bpm);
}

public class BeatController implements ControllerInterface {
	BeatModelInterface model;
	DJView view;
  
	// 뷰를 생성하고, 인자로 model을 받아온다.
	public BeatController(BeatModelInterface model) {
		this.model = model;
		view = new DJView(this, model);
        view.createView();
        view.createControls();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
		model.initialize();
	}
  
	// 사용자의 입력에 따라 적절한 처리를 해준다.
	public void start() {
		model.on();
		view.disableStartMenuItem();
		view.enableStopMenuItem();
	}
  
	public void stop() {
		model.off();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
	}
    
	public void increaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm + 1);
	}
    
	public void decreaseBPM() {
        int bpm = model.getBPM();
        model.setBPM(bpm - 1);
  	}
  
 	public void setBPM(int bpm) {
		model.setBPM(bpm);
	}
}
```