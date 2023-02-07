# 싱글톤 패턴

## 싱글톤 패턴

> 💡 싱글톤 패턴이란 클래스 인스턴스를 하나만 만들고, 그 인스턴스로의 전역 접근을 제공하는 것을 말한다.

<p align="center"><img width="300" alt="img" src="https://user-images.githubusercontent.com/76640167/210200061-f86d1136-bb44-408c-a939-65d4506c6dbb.png"></p>

private 생성자로 외부 생성을 막고, getInstance()를 통해 하나의 객체를 반환한다.

### 사용 이유

- 결과에 일관성을 챙길 수 있다.
- 하나만 필요한 인스턴스의 경우, 인스턴스를 여러개 생성할 경우 메모리만 낭비 될 수 있다.
  ex: 스레드 풀, 캐시, 사용자 설정, 로그 기록용 객체 등등

## 고전적인 싱글턴 패턴 생성 방법

```java
public class Singleton {
	private static Singleton uniqueInstance;
 
	private Singleton() {}
 
	public static Singleton getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Singleton();
		}
		return uniqueInstance;
	}
 
	// other useful methods here
	public String getDescription() {
		return "I'm a classic Singleton!";
	}
}
```

private 생성자를 통해 객체를 외부에서 생성하는 것을 막고, getInstance를 통해 객체를 전달 받는다.

이때, 이미 생성했던 객체가 있다면 이미 생성했던 객체를 반환한다.

이미 생성했던 객체는 static 변수이기 때문에 heap 메모리 부분에 저장되어 프로그램이 끝날 때까지 유지된다.

### 문제점

**멀티스레드 환경**에서 getInstance()의 if 문을 통과가 여러번되고 그 이후에 new Singleton()이 불린다면, 의도치 않게 **여러 인스턴스가 생성되고 이는 의도와 다르다.**

## 해결 방법

### **방법 1: getInstance() 동기화하기**

```java
public class Singleton {
	private static Singleton uniqueInstance;
 
	// other useful instance variables here
 
	private Singleton() {}
 
	public static synchronized Singleton getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Singleton();
		}
		return uniqueInstance;
	}
 
	// other useful methods here
	public String getDescription() {
		return "I'm a thread safe Singleton!";
	}
}
```

getInstance() 메서드에 접근을 하나의 스레드만 할 수 있으므로 위 문제는 해결되지만, 메서드를 동기화 하면 성능이 100배 정도 저하된다.

### **방법 2: 인스턴스가 필요할 때 생성하지 말고 처음부터 만든다. (Eager initialization)**

```java
public class Singleton {
	private static Singleton uniqueInstance = new Singleton();
 
	private Singleton() {}
 
	public static Singleton getInstance() {
		return uniqueInstance;
	}
	
	// other useful methods here
	public String getDescription() {
		return "I'm a statically initialized Singleton!";
	}
}
```

이 방법을 사용하면 클래스가 로딩될 때 JVM에서 Singleton의 하나뿐인 인스턴스를 생성해준다. JVM에서 하나뿐인 인스턴스를 생성하기 전까지 그 어떤 스레드도 uniqueInstance에 접근할 수 없다.

당연하지만, 시작부터 heap 메모리 부분을 차지하기 때문에

### **방법 3: DCL(Double Checked Locking)을 사용한다.**

```java
public class Singleton {
	private volatile static Singleton uniqueInstance;
 
	private Singleton() {}
 
	public static Singleton getInstance() {
		if (uniqueInstance == null) {
			synchronized (Singleton.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Singleton();
				}
			}
		}
		return uniqueInstance;
	}
}
```

인스턴스가 생성되어 있는지 확인한 다음 생성되어 있지 않았을 때만 동기화할 수 있다.

이 방식을 사용하면, 처음 생성할 때만 동기화하고 나중에는 동기화하지 않을 수 있다.

당연하겠지만 volatile을 사용하여 성능 측면에서 불리하다.

주의: DCL은 자바 1.4 이전 버전에서는 사용할 수 없다.

참고 : volatile

변수를 Main Memory에 저장하겠다는 것을 명시한다. 즉, 매번 읽을 때도 캐시가 아닌 메인 메모리에서 읽고, 쓸 때도 메인 메모리에 까지 작성한다. (당연히 캐시에 비해 성능이 떨어진다.) 멀티 스레드 환경에서 한 스레드가 write했을 때 나머지 스레드에서도 그 값을 Read할 수 있게하기 때문에 변수 값 불일치 문제를 예방할 수 있다.

### 위 3가지 방법의 공통적인 문제

- 클래스 로더가 2개 이상이라면, 클래스 로더마다 다른 네임스페이스를 정의하기에 클래스 로더 마다 한번씩 로딩할 수도 있다. 즉, 인스턴스가 여러개 만들어지게 되고 이때는 위 3가지 방법을 지양하거나, 클래스 로더를 직접 지정해야한다.
- 리플렉션, 직렬화, 역직렬화도 싱글턴에서 문제가 될 수 있다.



## 위 공통 문제 해결 방법

### **방법 4: Enum 사용**

```java
public enum EnumSingleton {
    uniqueInstance;
}
```

- **리플렉션을 통해 싱글톤을 깨트리는 공격에 안전**
- **직렬화 보장**

성능 문제 : Eager Initialization로 인한 문제와 싱글톤이 Enum 외의 클래스를 상속해야 하는 경우에는 사용할 수 없다.

또한 안드로이드 같이 Context 의존성이 존재하는 환경일 경우 싱글턴 초기화시 Context라는 의존성이 끼어들 가능성이 있다.

### **방법 5: Lazy Holder**

```java
public class Singleton {

    private Singleton() {}
    
    // private static inner class 인 LazyHolder
    private static class LazyHolder {
        // LazyHolder 클래스 초기화 과정에서 JVM 이 Thread-Safe 하게 instance 를 생성
        private static final Singleton instance = new Singleton();
    }

    // LazyHolder 의 instance 에 접근하여 반환
    public static Singleton getInstance() {
        return LazyHolder.instance;
    }
    
}
```

Eager Initialization를 개선한 방법.

private static class 인 LazyHolder안에 instace 를 final 로 선언하는 방법이다.

JVM은 클래스의 초기화 과정에서 원자성을 보장하는데, 이 원리를 이용하는 방식이다.

과정
1. getInstance() 가 호출되면 LazyHolder의 instance 변수에 접근하는데, 이때 LazyHolder 가 static class 이기 때문에 클래스의 초기화 과정이 이루어 진다.

1. LazyHolder 클래스가 초기화 되면서 instance 객체의 생성도 이루어 진다.
2. JVM 은 이러한 클래스 초기화 과정에서 원자성을 보장한다.

즉, 정리하면 final 로 선언한 instance 는 getInstace() 호출 시 LazyHolder 클래스의 초기화가 이루어 지면서 원자성이 보장된 상태로 단 한번 생성되고, final 변수 이므로 이후로 다시 instance 가 할당되는 것 또한 막을 수 있다.

장단점

- JVM 자체의 특성을 최대한 이끌어내어 성능 저하를 막는 방식이다.
- syncronized를 사용하지 않아도 JVM이 원자성을 보장한다.
- 역직렬화 수행시 개로운 객체가 생성되거, 리플랙션을 이용해 내부 생성자 호출이 가능하다.

---

### 정리

LazyHolder는 최근 가장 많이 사용되는 기법이다.

직렬화 등의 안정성이 중요시 되는 환경이라면 Enum, 성능이 중요시 되는 환경이라면 LazyHolder를 사용하도록 하자.