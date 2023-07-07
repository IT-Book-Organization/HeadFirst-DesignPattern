# 옵저버 패턴

### 옵저버 패턴
> 💡  옵저버 패턴(Observer Pattern)은 한 객체의 상태가 바뀌면 
> 그 객체에 의존하는 다른 객체에 연락이 가고 자동으로 내용이 갱신되는 방식으로 일대다(one-to-many) 의존성을 정의한다.

<p align="center"><img width="350" alt="스크린샷 2022-12-30 오후 7 11 09" src="https://user-images.githubusercontent.com/76640167/210133695-cea04c8d-ddef-49b9-8e8c-aeb60b2caccf.png"></p>

간단히 정리하자면, 어떤 객체의 상태가 변할 때 그와 연관된 객체들에게 알림을 보내는 디자인 패턴이 옵저버 패턴이다.

위의 그림과 똑같이 구현하게 된다면 Observer 객체의 종류가 다양할 때, 정적으로 Subject 객체에 하나씩 더해주어야만 한다.

즉, 코드의 유연성이 현저히 떨어진다.

이를 해결하기 위해서는, 다형성을 활용하여 Observer 객체들은 Observer Interface를 상속하여 Subject 객체에 List로 Observer 객체들을 담아야 한다.

이 Observer들은 Subject의 상태가 바뀔 때 갱신이 되어야하므로, notify라는 함수를 가져야한다. Subject는 Observer.notify(~)의 형식을 활용하여 상태가 바뀔 때 Observer들을 call하거나 data를 전송할 수 있게 된다.

즉, 다음과 같은 구조가 될 것이다.

<p align="center"><img width="350" alt="스크린샷 2022-12-30 오후 7 11 09" src="https://user-images.githubusercontent.com/76640167/210133630-f43db5e5-3170-4545-a4fb-f26bafc7da88.png"></p>


### 디자인 원칙

> 💡 상호작용하는 객체 사이에는 가능하면 느슨한 결합을 사용해야 한다.

느슨하게 결합하는 디자인을 사용하면 변경사항이 생겨도 무난히 처리할 수 있는 유연한 객체지향 시스템을 구축할 수 있다.

옵저버 패턴은 느슨한 결합을 보여주는 훌륭한 예이다.

### 느슨한 결합(Loose Coupling)

> 💡 객체들이 상호작용할 수는 있지만 서로를 잘 모르는 관계를 의미한다.

옵저버 패턴이 느슨한 결합을 만드는 방식

- Subject는 옵저버가 특정 인터페이스를 구현한다는 사실만 알고 있다.
- 옵저버는 언제든지 새로 추가할 수 있다.
- 새로운 형식의 옵저버를 추가해도 subject 코드를 변경할 필요가 없다.
- subject와 옵저버는 서로 독립적으로 재사용할 수 있다.
- subject와 옵저버는 달라져도 서로에게 영향을 끼치지 않는다.

---

### 옵저버 패턴 예시

### 1. data push

기상 관측 결과가 update 되는 WeatherData 객체가 있고, 이 update 된 결과를 다양한 Display 객체들이 받아서 원하는 형식으로 display 해야한다고 해보자.

Display들이 Observer, WeatherData가 Subject가 된다는 것을 쉽게 알 수 있다.

UML을 먼저 보도록 하자.

<p align="center"><img width="500" alt="스크린샷 2022-12-30 오후 7 11 09" src="https://user-images.githubusercontent.com/76640167/210133722-e30a9034-c97d-423b-b73b-4a109ff5e8ca.png"></p>


각기 다른 방식으로 display()하기 때문에 Interface를 통해 상속받았고, 위에서 설명한 구조와 똑같이 UML이 만들어진 것을 볼 수 있다.

대략적인 호출 순서

1. Observer들은 생성자로 받아온 weathreData에 자기 자신을 register 한다.
2. 기상에 변화가 생기면 WeatherData의 상태가 변경되는 코드와 함께 measurementChanged()가 호출된다.
3. measurementChanged()에서 notifyObservers()를 호출
4. notifyObservers()에서 List에 등록된 Observer에 notify(update 메서드)를 보낸다.
5. Observer는 update 메서드를 통해 data를 받아 Display 객체들에서는 해당 data를 Display 특성에 맞게 display()한다.

이제 구체적인 코드를 보도록 하자.

Subject code

```java
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

public class WeatherData implements Subject{
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: observers) {
            observer.update(temperature,humidity,pressure);
        }
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}
```

Observer code

```java
public interface Observer {
    void update(float temp, float humidity, float pressure);
}

public class ConditionDisplay implements Observer, DisplayElement{
    private float humidity;
    private float temperature;

    private WeatherData weatherData;

    public ConditionDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("now temp: " + temperature + ", 습도: " + humidity);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        display();
    }
}
```

main code

```java
public class Application {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        ConditionDisplay conditionDisplay = new ConditionDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);

        weatherData.setMeasurements(12.0f,55f,20f);
        weatherData.setMeasurements(-1.0f,55f,17f);
    }
}
```

result

```
now temp: 12.0, 습도: 55.0
Avg/Max/Min temperature = 12.0/12.0/12.0
now temp: -1.0, 습도: 55.0
Avg/Max/Min temperature = 5.5/12.0/-1.0

Process finished with exit code 0
```

위와 같이 **느슨한 결합**으로 옵저버 패턴이 구현된 것을 볼 수 있다.

### 2. data pull

위에서는 subject가 data를 push하고, observer가 data를 가공하여 사용했다.

이렇게 되면, 필요한 data만 가지고 있는 것이 아니라 필요 없는 data도 일단은 받고 사용하여야 한다.

확장성의 측면으로 볼때, observer에서 notify 함수가 불렸을 때, **subject객체로부터 data를 가져오는 방식(pull)**이 더 괜찮다고 볼 수 있다.

observer는 subject에 등록과 삭제하기 위해 subject 객체를 가지므로 subject.getData()를 통해 데이터를 pull 해올 수 있다.

코드를 바꿔보도록 하자.

Observer code

```java
public interface Observer {
    void update();
}

public class ConditionDisplay implements Observer, DisplayElement{
    private float humidity;
    private float temperature;

    private WeatherData;

    public ConditionDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("now temp: " + temperature + ", 습도: " + humidity);
    }

    @Override
    public void update() {
        // 당연하지만, interface 및 부수적인 코드도 변경해야한다.
        this.temperature = weatherData.getTemperature();
        this.humidity = weatherData.getHumidity();
        display();
    }
}
```

subject의 상태는 계속 바뀌는 것을 가정하기 때문에 get을 사용하는 것을 notify() 내부에서만 하는 것을 권장한다. 

(이러한 측면에서 볼때 get 메서드를 삭제하고 data를 push하는 것이 나을 수도 있다.)

참고 : Subject에서 List<Observer>의 순서에 의존하지 말라는 JDK 권고가 있으니 주의하여야 한다.

---

### Deep Dive : 멀티 스레드 환경과 옵저버 패턴

위 코드가 멀티스레드 환경에서도 안전한가?

List를 set처럼 contain하고 있다면 추가 하지 않도록 구현하여도 멀티스레드 환경에서는 같은 Observer가 여러개 등록될 수도 있고, 막 등록된 옵저버가 알림을 받지 못하거나, 막 삭제된 옵저버가 잘못 알림을 받는 등의 race condition이 발생할 수 있다.

이러한 점들을 고려하여 자바에서는 자바 8 이하에서 Observable 클래스와 Observable 인터페이스를 제공한다.

내부 코드를 보자.

```java
public class Observable {
    private boolean changed = false;
    private Vector<Observer> obs;

    /** Construct an Observable with zero Observers. */

    public Observable() {
        obs = new Vector<>();
    }

// ... 중략
```

Observer 들의 집합을 가지고 있고 changed 라는 flag 변수를 가지고 있다.

```java
public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }
```

옵저버의 배열도 set과 같이 사용하고, 위에서 말한 의도치 않게 여러 Observer가 등록되는 상황을 고려하여 메서드를 syncronized 시킨 것을 볼 수 있다.

```java
public void notifyObservers(Object arg) {
        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

        synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             */
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, arg);
    }
```

notify 하는 코드인데, syncronized 블럭으로 위에서 말한 막 등록된 옵저버가 알림을 받지 못하거나, 막 삭제된 옵저버가 잘못 알림을 받는 등의 race condition을 방지하고 있다.

Observable 클래스는 상속을 해야하는데, 상속을 하면 다중상속을 못하기 때문에 한계가 생기고, 옵저버 패턴을 자유롭게 custom 할 수 없어진다.

때문에 옵저버 패턴을 스스로 구현하는게 낫다고 생각하는 사람들과 더 강력한 기능을 스스로 구현하는게 낫다고 생각하는 사람들이 늘어 **자바 9 이후로는 사용이 불가함**을 알아두자.

위에서 얻을 수 있는 것은 **멀티스레드 환경에서 옵저버 패턴을 사용하려면 나타날 수 있는 race condition들에 유의하고 적절히 사용하여야 한다는 것이다.**
