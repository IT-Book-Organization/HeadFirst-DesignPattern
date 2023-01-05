# 팩토리 패턴

새로운 객체가 필요한 곳 마다 new를 사용하여 구상 클래스를 바탕으로 코딩하면, 나중에 코드를 수정해야할 가능성이 커지고, 유연성이 떨어진다.

예를들어, 일련의 구상클래스가 있다면 어쩔 수 없이 다음과 같이 만들어야한다.

```java
Duck duck;

if(picnic) {
	duck = new MallardDuck();
} else if(~) {
	duck = new Duck();
}
//이하 생략
```

구상클래스를 많이 사용하면 새로운 구상 클래스가 추가될 때마다 코드를 고쳐야 하므로 수많은 문제가 생길 수 있다. 유연성이 매우 덜어지는 코드가 된다.

즉, 첫번째 디자인 원칙대로 바뀌는 부분을 찾아내서 바뀌지 않는 부분과 분리하여 캡슐화 해야한다.

팩토리 패턴에서는 **인스턴스를 만드는 부분을 캡슐화하여 분리**한다.

<br/><br/>

## 간단한 팩토리

간단한 팩토리는 어떻게 보면 팩토리 패턴이 아니라, 전략 패턴과 유사하다.

인스턴스를 만드는 부분을 알고리즘 군으로 생각하고, 이를 캡슐화하여 상호 교체 가능하게 만든다.

### 간단한 팩토리 예제 코드

피자를 만드는 클래스를 생성한다고 가정해보자.

여러 피자를 만들 수 있으므로, 처음에는 다음과 같이 코드를 짤 것이다.

```java
public class PizzaStore {

    public Pizza orderPizza(String type) {
        Pizza pizza;

        if (type.equals("cheese")) {
            pizza = new CheesePizza();
        } else if (type.equals("pepperoni")) {
            pizza = new PepperoniPizza();
        } else {
            pizza = new CheesePizza();
        }

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
```

이렇게 되면, 피자의 종류가 늘어날 때 구상 클래스인 PizzaStore의 코드가 변경되어야하고, 이는 OCP 규칙을 지키지 못한다.

인스턴스 만드는 부분은 **바뀌는 부분이므로 캡슐화**를 해보자.

인스턴스 만드는 클래스의 이름을 보통 Factory 라고 한다.

**PizzaStore**

```java
public class PizzaStore {
    SimplePizzaFactory factory;

    public PizzaStore(SimplePizzaFactory factory) {
        this.factory = factory;
    }

    public Pizza orderPizza(String type) {
        Pizza pizza;

        pizza = factory.createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
```

**Factory**

```java

public class SimplePizzaFactory {

    public Pizza createPizza(String type) {
        Pizza pizza;

        if (type.equals("cheese")) {
            pizza = new CheesePizza();
        } else if (type.equals("pepperoni")) {
            pizza = new PepperoniPizza();
        } else {
            pizza = new CheesePizza();
        }

        return pizza;
    }
}
```

이렇게 간단한 팩토리를 만들면 구현을 변경할 때 여기저기 변경할 필요 없이 팩토리 클래스 하나만 고치면 된다.

### 한계

팩토리가 클래스로 이루어져있어 확장이 어렵다. (OCP 위배)

이를 위해 인터페이스로 변경하면 추상 팩토리 패턴에서 제품군을 하나만 사용한 것과 유사하다.

참고 : 간단한 팩토리는 디자인 패턴이라기 보다는 자주 쓰이는 관용구에 가깝다.

<br/><br/>

## 팩토리 메소드 패턴

팩토리 메소드 패턴에서는 객체를 생성할 때 필요한 인터페이스를 만드는데, 어떤 클래스의 인스턴스를 만들지 서브 클래스에서 맡게 되는 것을 말한다.

즉, 팩토리의 역할을 PizzaStore()의 서브클래스가 대신 createPizza()를 오버라이드 및 구현해서 수행한다는 것이다.

팩토리 메서드 패턴의 구조를 보면 다음과 같다.

<p align="center"><img width="750" alt="img1" src="https://user-images.githubusercontent.com/76640167/210176136-3a73a9de-a289-48db-94bf-8009f91c20a5.png"></p>

중요한 것은 **사용하는 서브클래스에 따라 생산되는 객체 인스턴스가 결정된다**는 것이다.

### 팩토리 메서드 패턴 예제

SimplePizzaFactory 말고도, 여러 지점이 생기고 여러 지점마다 특징을 살려야 했기에 NYPizzaFactory, ChicagoPizzaFactory 등이 생긴다고 해보자.

간단한 팩토리였다면 Factory를 인터페이스화 해서 다형성을 이용하여 Factory를 상속 받은 구현체들을 PizzaStore 생성자를 통해 주입 해주었을 것이다.

팩토리 메서드 패턴에서는 위에 정의와 같이 PizzaStore의 서브 클래스에서 createPizza()를 오버라이드 및 구현한다.

**PizzaStore와 PizzaStore상속 구현체**

```java
public abstract class PizzaStore {
 
	abstract Pizza createPizza(String item);
 
	public Pizza orderPizza(String type) {
		//중요! 팩토리 메서드를 통해 객체 생성
		Pizza pizza = createPizza(type);

		System.out.println("--- Making a " + pizza.getName() + " ---");
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}
}

public class NYPizzaStore extends PizzaStore {

	Pizza createPizza(String item) {
		if (item.equals("cheese")) {
			return new NYStyleCheesePizza();
		} else if (item.equals("veggie")) {
			return new NYStyleVeggiePizza();
		} else if (item.equals("clam")) {
			return new NYStyleClamPizza();
		} else if (item.equals("pepperoni")) {
			return new NYStylePepperoniPizza();
		} else return null;
	}
}

//당연히 똑같이 PizzaStore를 상속받는 Store를 생성할 수 있다.
```

**Pizza와 Pizza 상속 구현체**

```java
public abstract class Pizza {
	String name;
	String dough;
	String sauce;
	ArrayList<String> toppings = new ArrayList<String>();
 
	void prepare() {
		System.out.println("Prepare " + name);
		System.out.println("Tossing dough...");
		System.out.println("Adding sauce...");
		System.out.println("Adding toppings: ");
		for (String topping : toppings) {
			System.out.println("   " + topping);
		}
	}
  
	void bake() {
		System.out.println("Bake for 25 minutes at 350");
	}
 
	void cut() {
		System.out.println("Cut the pizza into diagonal slices");
	}
  
	void box() {
		System.out.println("Place pizza in official PizzaStore box");
	}
 
	public String getName() {
		return name;
	}

	public String toString() {
		StringBuffer display = new StringBuffer();
		display.append("---- " + name + " ----\n");
		display.append(dough + "\n");
		display.append(sauce + "\n");
		for (String topping : toppings) {
			display.append(topping + "\n");
		}
		return display.toString();
	}
}

public class NYStyleCheesePizza extends Pizza {

	public NYStyleCheesePizza() { 
		name = "NY Style Sauce and Cheese Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";
 
		toppings.add("Grated Reggiano Cheese");
	}
}

//당연히 똑같이 Pizza를 상속받는 다양한 Pizza를 생성할 수 있다.
```

즉, 위 코드에서는 createPizza()가 어떤 Pizza를 반환할지 결정하는 팩토리 메서드가 된다.

PizzaStore는  Pizza 객체를 가지고 여러가지 작업을 하지만, 실제로 어떤 구상 클래스에서 작업이 처리되고 있는지는 전혀 알 수 없다. 즉, 느슨한 결합이 완성되어있다.

**클라이언트 코드**

```java
public class PizzaTestDrive {
 
	public static void main(String[] args) {
		PizzaStore nyStore = new NYPizzaStore();
		PizzaStore chicagoStore = new ChicagoPizzaStore();
 
		Pizza pizza = nyStore.orderPizza("cheese");
		System.out.println("Ethan ordered a " + pizza.getName() + "\n");
 
		pizza = chicagoStore.orderPizza("cheese");
		System.out.println("Joel ordered a " + pizza.getName() + "\n");
	}
}
```

클라이언트 코드에서 알 수 있듯이 **어떤 서브클래스를 선택했느냐에 따라 생산되는 객체 인스턴스가 결정**된다.

### 장점

- Factory Method 패턴의 가장 큰 장점은 지금까지 본 것처럼 **수정에 닫혀있고 확장에는 열려있는 OCP 원칙을 지킬 수 있다.**
- 다른 객체 없이 상속을 통해서 구현하므로 비교적 메모리를 아낄 수 있다.

### 단점

- 간단한 기능을 사용할 때보다 많은 클래스를 정의해야 하기 때문에 코드량이 증가한다.

<br/><br/>

## 디자인 원칙 (DIP, Dependency Injection Principle)

**추상화된 것에 의존하게 만들고, 구상 클래스에 의존하지 않게 만든다.**

보통 **고수준 구성 요소**는 다른 **저수준 구성 요소에 의해 정의되는 행동이 들어있는 구성 요소**를 뜻한다.

고수준 구성 요소에서 다른 객체를 사용하게 되는 경우가 빈번한데, **의존이란 이렇게 다른 객체의 기능에 의존하게 되는 것을 의미**하고, **저수준 구성 요소란 고수준 구성 요소에 의해 사용되는 객체**라고 볼 수 있다.

객체를 사용하다보면 대부분 다른 객체의 기능을 사용하여야 한다. 이 말은 즉슨, 의존하게 된다는 말과 같다.

즉, 정리하면 DIP란 **다른 객체를 사용할 때 구상 클래스가 아니라 인터페이스, 추상 클래스 등의 추상화된 것을 사용하여야한다는 원칙**을 말한다.

위 팩토리 메소드 패턴에서 고수준 구성 요소인 PizzaStore는 추상 클래스인 Pizza에 의존하고 있는 것을 볼 수 있다.

위 방법만 있는 것이 아니라 외부에서 setter 혹은 생성자 등으로 의존성을 주입하는 등 여러가지 방법이 있다.

### DIP를 지키는 방법

- 변수에 구상 클래스의 래퍼런스를 저장하지 않는다.
- 구상 클래스에서 유도된 클래스를 만들지 않는다.
- 베이스 클래스에 이미 구현되어 있는 메소드를 오버라이드 하지 않는다.

다른 원칙들과 마찬가지로 모두 지킬 수는 없다. 즉, 바뀔 확률이 큰 부분을 잘 정의하고 그러한 부분에서 규칙을 지키도록 노력해야한다. (ex: String 객체는 변경될 일이 거의 없으므로 인스턴스를 별 생각 없이 만들어서 쓴다.)

참고 : 왜 역전인가?

객체 지향 디자인을 할 때 일반적으로 생각하는 방법과는 반대로 생각해야하기 때문이다.

위 예시와 같이 설명하면, 보통 PizzaStore를 만들고, 여러 구상 객체인 Pizza를 만들 것이다. PizzaStore → PizzaImpl 의존관계가 한방향으로만 간다. 그러나 의존관계역전 원칙을 지키키 위하여 Pizza를 추상화하여 인터페이스로 만들어야하고, 여러 구상 객체들이 Pizza를 상속 받게된다. 즉 다음과 같은 방향을 갖는다. PizzaStore → Pizza ← Pizzaimpl 이렇게 구상 클래스의 방향이 반대로 역전된 것을 볼 수 있다. 그래서 역전이다.

<br/><br/>

## 추상 팩토리 패턴

추상 팩토리 패턴은 구상 클래스에 의존하지 않고도 서로 연관되거나 의존적인 객체로 이루어진 제품군을 생산하는 인터페이스를 제공한다.

추상 팩토리 패턴에서 중요한 것은 **제품군과 인터페이스**라는 키워드이다. 서로 관련이 있는 객체들을 통째로 묶어서 조건에 따라 객체들을 생성하도록 팩토리 클래스를 만들어서 객체를 생성하는 패턴이다.

<p align="center"><img width="750" alt="img1" src="https://user-images.githubusercontent.com/76640167/210176137-0fa6f45f-3402-40ee-a237-9a902f04fded.png"></p>

- 추상 팩토리 패턴에서 제공하는 인터페이스를 모든 팩토리가 상속받아야한다.
- 상속받은 메소드를 구현할 때 당연하지만, 추상 제품을 반환해야한다.
  ex: Cheese createCheese(){ return new Mozzarella();}
- 클라이언트에서 추상 팩토리를 바탕으로 실제 팩토리를 선택하여 생성한다.

참고: 간단한 팩토리를 인터페이스로 추상화하고, 내부 메서드를 늘리면 추상 팩토리 패턴과 완벽히 같아진다.

위 피자 예시를 이어서 들어보자.

각 피자집에 제품들을 전달해야한다. 그러나 각 지점마다 원하는 재료들이 다르다고 가정해보자.

먼저, 팩토리 메소드 패턴을 그대로 쓰는 방법을 생각해보자.

1. 팩토리 객체를 제품마다 만든다.
   제품을 추가할 때마다 팩토리를 추가하고, 이를 PizzaStore에 추가하는 것은 PizzaStore에 변경을 야기한다.
2. 팩토리 메소드를 여러개 만든다.
   팩토리 메소드를 여러개 만들면 Factory객체를 추가적으로 계속 만드는 것은 방지할 수 있겠지만, 결국 해당 객체들을 전부 Pizza 객체에 전달해야한다. 이는 상위 클래스인 PizzaStore가 해야하고, 변경을 야기한다.

**사실 이 방법은 상위 인터페이스가 팩토리 메서드로만 이루어져있지 않다는 것을 제외하면 추상 팩토리 패턴과 똑같다. 실제로 추상 팩토리 패턴에서 팩토리 메소드로 구현되는 경우가 있다.** 그러나 일련의 제품군이 아닌 Pizza 자체와 그 내부 제품군들을 함께 묶어서 사용되기 때문에 이곳에선 적합하지 못하다.

재료가 추가될 때마다 PizzaStore 코드에 변경을 주지 않으려면 Pizza 구상 객체로 여러 팩토리 메소드가 정의된 팩토리를 전달하고, Pizza에서 팩토리로부터 재료군들을 빼와야한다.

코드를 보도록 하자.

**재료들** (나머지는 코드가 다 비슷하므로, 중복되는 재료들은 제거)

```java
public interface Dough {
	public String toString();
}

public class ThinCrustDough implements Dough {
	public String toString() {
		return "Thin Crust Dough";
	}
}
```

**추상 팩토리**

```java
// 팩토리 메서드와 다른 점이 나타나는 부분이다.
// 팩토리 메서드는 하나의 팩터리 메서드만 정의하고, 이를 반환한다.
// 추상 팩토리 패턴은 제품군을 묶어 모든 메서드를 정의하고, 여러 객체를 반환한다.
public interface PizzaIngredientFactory {
 
	public Dough createDough();
	public Sauce createSauce();
	public Cheese createCheese();
	public Veggies[] createVeggies();
	public Pepperoni createPepperoni();
	public Clams createClam();
 
}

public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
 
	public Dough createDough() {
		return new ThinCrustDough();
	}
 
	public Sauce createSauce() {
		return new MarinaraSauce();
	}
 
	public Cheese createCheese() {
		return new ReggianoCheese();
	}
 
	public Veggies[] createVeggies() {
		Veggies veggies[] = { new Garlic(), new Onion(), new Mushroom(), new RedPepper() };
		return veggies;
	}
 
	public Pepperoni createPepperoni() {
		return new SlicedPepperoni();
	}

	public Clams createClam() {
		return new FreshClams();
	}
}
```

**NTPizzaStore**

```java
public class NYPizzaStore extends PizzaStore {
 
	protected Pizza createPizza(String item) {
		Pizza pizza = null;
		//추상 팩토리 구현체 생성 및 전달
		PizzaIngredientFactory ingredientFactory = 
			new NYPizzaIngredientFactory();
 
		if (item.equals("cheese")) {
  
			pizza = new CheesePizza(ingredientFactory);
			pizza.setName("New York Style Cheese Pizza");
  
		} else if (item.equals("veggie")) {
 
			pizza = new VeggiePizza(ingredientFactory);
			pizza.setName("New York Style Veggie Pizza");
 
		} else if (item.equals("clam")) {
 
			pizza = new ClamPizza(ingredientFactory);
			pizza.setName("New York Style Clam Pizza");
 
		} else if (item.equals("pepperoni")) {

			pizza = new PepperoniPizza(ingredientFactory);
			pizza.setName("New York Style Pepperoni Pizza");
 
		} 
		return pizza;
	}
}
```

추상 팩토리의 생성은 각 지점마다 재료가 다른 것이므로, 기존 팩토리 메소드가 구현체(NTPizzaStore)이다.

이곳에서는 뉴욕 지점인 것을 알고 있으므로 바로 뉴욕재료팩토리를 생성하여 Pizza에게 전달할 수 있다.

즉, 클라이언트가 NYPizzaStore가 될 것이다.

**Pizza**

```java
public class CheesePizza extends Pizza {
	PizzaIngredientFactory ingredientFactory;
 
	public CheesePizza(PizzaIngredientFactory ingredientFactory) {
		this.ingredientFactory = ingredientFactory;
	}
 
	void prepare() {
		System.out.println("Preparing " + name);
		dough = ingredientFactory.createDough();
		sauce = ingredientFactory.createSauce();
		cheese = ingredientFactory.createCheese();
	}
}
```

추상 팩토리로부터 객체를 생성 받아와 초기화 한다.

### 장점

팩토리 메소드 패턴에 비해 여러 객체를 생성해낼 수 있다.

### 단점

제품군을 추가하려면 인터페이스에 메소드를 추가해야한다.

<br/><br/>

### 간단한 팩토리 vs 팩토리 메소드 패턴 vs 추상 팩토리 패턴

|  | 간단한 팩토리 | 팩토리 메소드 패턴 | 추상 팩토리 패턴 |
| --- | --- | --- | --- |
| 팩토리 생성 | 고수준 객체의 변수로 구성 | 상속으로 객체를 생성 | 고수준 객체의 변수로 구성 |
| 상속 | X | 인터페이스 혹은 추상클래스 | 인터페이스 |
| 객체 생성 개수 | 1개 | 1개 | n개 |