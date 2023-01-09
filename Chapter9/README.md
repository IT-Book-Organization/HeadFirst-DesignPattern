## 반복자 패턴

> 💡 반복자 패턴(Iterator Pattern)은 **컬렉션의 구현 방법을 노출하지 않으면서 집합체 내의 모든 항목에 접근하는 방법**을 제공한다.

즉, 반복자 패턴을 통해 **접근기능과 컬랙션 자료구조를 분리시켜서 객체화**한다. 

즉, 항목에 일일이 접근할 수 있게 해주는 기능을 집합체가 아닌 반복자 객체가 책임지게 한다.

반복자 패턴을 통해 서로 다른 구조를 가지고 있는 저장 객체에 대해서 접근하기 위해 접근 기능을 반복자(Iterator) interface로 통일 시킬 수 있다.

<br/>

### 클래스 다이어그램
<p align="center"><img width="480" alt="Iterator" src="https://user-images.githubusercontent.com/76640167/211367126-c17eb635-a43d-4ad7-863b-03bacd761b92.png">

- `Iterator` : 다른 구조를 가지고 있는 저장 객체에 대해서 접근하기 위해 통일할 인터페이스이다. (자바에서 실제로 Iterator 인터페이스를 제공한다.)
- `ConcreteIterator` : 반복 작업 중에 현재 위치를 관리하는 일을 맡는다. 실질적인 반복 작업을 구현한다.
- `ConcreteAggregate` : 실질적인 객체 컬랙션을 가지고 있으며, 그 안에 들어있는 컬렉션을 Iteratro로 반환하는 메소드를 구현한다.
- `Aggregate` : 공통된 인터페이스가 있으면 클라이언트는 매우 편리하게 작업을 처리할 수 있다. 다양한 객체 컬렉션을 가지고 있는 저장 객체들의 동일 인터페이스이다. 이 객체들은 모두 `creatIterator()`  메소드를 가지므로 똑같은 인터페이스를 상속할 수 있다.

참고 : **반복자 패턴을 사용할 때는 반복자(Iterator)에는 특별한 순서가 정해져있지 않다는 것에 주의**하여야한다. 컬랙션이 해시맵처럼 정렬되지 않은 것일 수 있고, 중복된 항목이 있을 수도 있다. 즉, 접근 순서는 사용된 컬렉션의 특성 및 구현에 연관이 있고, 반복자에게는 없다는 것에 주의하여야한다.

<br/>

e.g

ConcreteIterator `DinnerMenuIterator`

```java
import java.util.Iterator;

public class DinnerMenuIterator implements Iterator<MenuItem> {
    MenuItem[] items;
    int position = 0;

    public DinnerMenuIterator(MenuItem[] items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public MenuItem next() {
        return null;
    }
}
```

자바에서 제공하는 Iterator를 사용하였다. 즉, 위 클래스 다이어그램에서 Iterator 인터페이스를 java.util에서 가져와 사용하였다.

자바에서 제공하는 Iterator를 굳이 사용하지 않더라도 실제로 Iterator 인터페이스를 구현하여 자바의 Iterator보다 기능을 확장하여 사용할 수도 있다.

실질적인 컬렉션(자료구조)을 가지고있는 `ConcreteAggregate`로부터 컬랙션을 받아와서 각 항목에 **접근**할 수 있게 해주는 기능을 책임진다.

<br/>

Aggregate `Menu`

```java
import java.util.Iterator;

public interface Menu {
    public Iterator<MenuItem> createIterator();
}
```

<br/>

ConcreteAggregate `DinnerMenu`

```java
import java.util.Iterator;

public class DinnerMenu implements Menu
{
    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;

    public DinnerMenu() {
        menuItems = new MenuItem[MAX_ITEMS];

        addItem("Vegetarian BLT",
                "(Fakin') Bacon with lettuce & tomato on whole wheat", true, 2.99);
        addItem("BLT",
                "Bacon with lettuce & tomato on whole wheat", false, 2.99);
        addItem("Soup of the day",
                "Soup of the day, with a side of potato salad", false, 3.29);
        addItem("Hotdog",
                "A hot dog, with sauerkraut, relish, onions, topped with cheese",
                false, 3.05);
        addItem("Steamed Veggies and Brown Rice",
                "Steamed vegetables over brown rice", true, 3.99);
        addItem("Pasta",
                "Spaghetti with Marinara Sauce, and a slice of sourdough bread",
                true, 3.89);
    }

    public void addItem(String name, String description,
                        boolean vegetarian, double price)
    {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        if (numberOfItems >= MAX_ITEMS) {
            System.err.println("Sorry, menu is full!  Can't add item to menu");
        } else {
            menuItems[numberOfItems] = menuItem;
            numberOfItems = numberOfItems + 1;
        }
    }

    public MenuItem[] getMenuItems() {
        return menuItems;
    }

    @Override
    public Iterator<MenuItem> createIterator() {
        return new DinnerMenuIterator(menuItems);
    }
}
```

가지고 있는 컬랙션에 대하여 Iterator를 생성한다.

만약 컬랙션이 ArrayList, LinkedList 등의 자바 자료구조라면 내부에 iterator 메소드를 가지고있어서 ConcreteIterator조차 만들 필요가 사라진다.

즉, 위의 클래스 다이어그램에서 ConcreteIterator와 Iterator를 자바에서 모두 제공해주는 것이다.

<br/>

Client `Waitress`

```java
import java.util.Iterator;

public class Waitress {
    Menu dinnerMenu;

    public Waitress(Menu dinnerMenu) {
        this.dinnerMenu = dinnerMenu;
    }

    public void printMenu() {
        printMenu(dinnerMenu.createIterator());
    }

    public void printMenu(Iterator iterator) {
        while (iterator.hasNext()) {
            MenuItem menuItem = (MenuItem) iterator.next();
            System.out.println(menuItem.getName());
        }
    }
}
```

이제 다른 구조를 가지고 있는 저장 객체가 와도 반복자 패턴을 사용하여 `printMenu(Iterator iterator)` 메소드를 활용할 수 있다.

<br/>

## 디자인 원칙: 단일 역할 원칙

하나의 클래스는 하나의 역할만 맡아야한다.

어떤 클래스에서 맡고 있는 모든 역할은 나중에 코드 변화를 불러올 수 있다.

역할이 2개 이상 있으면 바뀔 수 있는 부분이 2개 이상이 되는 것이고, 어떤 클래스를 변경하는 이유가 두개 이상이 될 수 있다.

변경 이유가 2개 이상이되면 한 책임의 변경으로부터 다른 책임의 변경으로의 연쇄작용이 생길 수 있다.

<br/>

### 응집도

클래스 또는 모듈이 특정 목적이나 역할을 얼마나 일관되게 지원하는지를 나타내는 척도이다.

어떤 모듈이나 클래스의 응집도가 높다는 것은 서로 연관된 기능이 묶여있다는 것을 뜻한다.

즉, 클래스가 2개 이상의 역할을 맡고 있는 클래스에 비해 하나만 맡고 있는 클래스가 응집도가 높다고 할 수 있다.

<br/>

## Iterable 인터페이스

자바의 모든 컬렉션 유형에서는 Iterable 인터페이스를 구현한다.

<p align="center"><img width="480" alt="Iterable" src="https://user-images.githubusercontent.com/76640167/211368051-4617d6c6-282f-4849-a080-b4bffa490276.png">

모든 자바 Collection 클래스는 Collection인터페이스를 구현하므로, 모든 Collection class는 위에서 봤던 iterator를 반환하는 Iterable이다.

이러한 iterable 인터페이스를 구현하는 클래스 객체는 향상된 For문을 사용할 수 있다.

(그냥 배열도 향상된 for문을 사용할 수 있다.)

그러나 위의 Menu 예시를 생각해봤을 때, 자바 배열은 Iterable 인터페이스를 상속받고 있지 않기 때문에 `printMenu(Iterable ~)`를 통해 메소드를 고치는 것은 불가능하다.

즉, 자바의 향상된 for문을 사용할 수 없어 Collection을 상속 받도록 바꾸는 등의 리팩터링이 필요하다.

<br/>

참고 : Iterable인터페이스, Collection 인터페이스와 그 구현체들외에 다양한 인터페이스 등을 모아 놓은 것을 **자바 컬렉션 프레임워크**라고 한다. **컬랙션 객체에서 `iterator()` 메소드를 통해 구상 Iterator클래스가 반환되는 것을 사용할 수 있다.**

## 컴포지트 패턴

컴포지트 패턴(Composite Pattern)으로 객체를 **트리구조**로 구성해서 부분-전체 계층을 구현한다. 컴포지트 패턴을 사용하면 클라이언트에서 개별 객체와 복합 객체를 똑같은 방법으로 다룰 수 있다.

부분-전체 계층 구조(part-whole hierarchy)란, 부분들이 계층을 이루고 있지만 모든 부분을 묶어서 전체로 다룰 수 있는 구조를 뜻한다. (트리에서 부모 노드와 자식 노드들의 가장 작은 부분들이 합쳐져서 트리의 전체 구조가 된다는 것을 생각하면 이해하기에 쉽다.)

컴포지트 패턴을 사용하면 객체의 구성과 개별 객체를 노드로 가지는 트리 형태의 객체 구조를 만들 수 있다.

트리와 구분되는 점은 트리에선 모든 Leaf 노드가 부모 노드가 될 수 있지만, 컴포지트 패턴에서는 Composite 객체만 Leaf 객체들을 관리하는 부모 노드가 될 수 있다는 것이다.

<br/>

### 클래스 다이어그램

<p align="center"><img width="480" alt="Composite Pattern" src="https://user-images.githubusercontent.com/76640167/211368053-e4a287e3-9114-4e9c-86c4-93321537bf75.png">

- Client : 클라이언트는 Component 인터페이스를 사용해서 복합 객체 내의 객체들을 조작할 수 있다.
- Component : 복합 객체 내에 들어있는 모든 객체의 인터페이스를 정의한다. 즉, 복합 노드와 잎에 관한 메소드까지 정의한다.
- Composite(복합 객체) : Composite에서 Leaf들을 관리하는 기능을 구현해야한다. 그런 기능들이 복합 객체에게 별 쓸모가 없다면 예외를 던지는 방법으로 처리해도 된다. 자식이 있는 구성요소의 행동을 정의하고 자식 구성 요소를 저장하는 역할을 맡는다. 즉, 실질적인 부모 노드가 된다.
- Leaf : 자식을 갖지 않는다. getChild() 등의 메소드는 필요가 없다. (UnSupportedOperation 오류를 내거나 비워둔다.) Leaf는 그 안에 들어있는 원소의 행동을 정의한다.

즉, **Component 인터페이스는 Composite 객체 (부모 노드)가 가지고 있는 기능과 Leaf 객체가 가지고 있는 기능을 둘다 가지고 있고, Leaf와 Coposite 객체는 기능들 중 선택해서 구현**하여야한다.

이런 복합 구조를 사용하여 Composite 객체(복합 객체)와 개별 객체(Leaf 객체)를 대상으로 똑같은 작업을 적용할 수 있고, 둘을 구분할 필요가 거의 없어진다.

<br/>

### **동작 과정**

1. Component를 상속받는 Composite 객체 Parent가 존재한다.
2. Parent에 Composite객체가 구현해야하는 기능(add,remove 등)을 구현한다.
3. 원하는 Component를 상속받는 Leaf 객체들을 Parent에 추가한다. (여기서 추가한다의 의미는 컬렉션에 add하는 것을 의미한다. 이 Leaf 객체들은 Leaf의 기능을 구현해야한다.)
4. 트리구조를 만들기 위해 Parent 객체에 새로운 Composite 객체 Parent2를 추가한다. (즉, Leaf 객체를 더한 것과 동일하게 더한다.)
5. Parent2도 Composite 객체이므로, Leaf들을 추가할 수 있다.

<br/>

### 투명성(transparency)

Component 인터페이스에서 Leaf와 Composite 기능을 전부 넣어서 클라이언트가 Composite객체와 Leaf 객체를 똑같은 방식으로 처리하게 만들어 **클라이언트 입장에서 어떤 원소가 복합 객체인지 잎인지 투명하게 보이게끔 보이게 하는 것**.

컴포지트 패턴에서는 Component를 상속 받는 객체들은 모두 Leaf와 Composite 두가지 역할을 가지고 있으므로 단일 역할 원칙을 깨고 있다.

대신 이 패턴에서는 투명성을 확보한다.

물론 인터페이스를 분리하여 다른 방향으로 디자인할 수 있다.

그렇게 하면 안전성은 증가하겠지만 투명성이 떨어지게 되고, 코드에서 조건문이라든가 instanceof 연사자를 사용하여야한다.

<br/>

즉, 여기서 알 수 있듯이 상황에 따라 원칙을 적절하게 사용하여야한다. 디자인 원칙 가이드라인 대로 따르면 좋지만, 그 원칙이 디자인에 어떤 영향을 끼칠지를 항상 고민하고 적용해야한다. 때때로 일부러 원칙에 위배되는 방식으로 디자인을 하는 경우도 있다.

<br/>

e.g

위 Menu 예시에서 저녁 메뉴 뿐만 아니라 저녁 메뉴에 디저트 메뉴들이 존재하게 되었다고 가정해보자.

기본 저녁 메뉴들과 디저트 메뉴는 구분되어야하고 이는 트리구조로 쉽게 구현할 수 있다.

<br/>

Component `MenuComponent`

```java
public abstract class MenuComponent {
   
	public void add(MenuComponent menuComponent) {
		throw new UnsupportedOperationException();
	}
	public void remove(MenuComponent menuComponent) {
		throw new UnsupportedOperationException();
	}
	public MenuComponent getChild(int i) {
		throw new UnsupportedOperationException();
	}
  
	public String getName() {
		throw new UnsupportedOperationException();
	}
	public String getDescription() {
		throw new UnsupportedOperationException();
	}
	public double getPrice() {
		throw new UnsupportedOperationException();
	}
	public boolean isVegetarian() {
		throw new UnsupportedOperationException();
	}
  
	public void print() {
		throw new UnsupportedOperationException();
	}
}
```

모든 구성 요소에 Component 인터페이스를 구현해야하지만, Composite과 Leaf는 역할이 다르므로 모든 메소드에 알맞는 기본 메소드 구현은 불가능 하다.

그래서 자기 역할에 맞지 않는 상황(Leaf에 addChild)을 기준으로 예외를 던진다.

<br/>

Leaf `MenuItem`

```java
public class MenuItem extends MenuComponent {
	String name;
	String description;
	boolean vegetarian;
	double price;
    
	public MenuItem(String name, 
	                String description, 
	                boolean vegetarian, 
	                double price) 
	{ 
		this.name = name;
		this.description = description;
		this.vegetarian = vegetarian;
		this.price = price;
	}
  
	public String getName() {
		return name;
	}
  
	public String getDescription() {
		return description;
	}
  
	public double getPrice() {
		return price;
	}
  
	public boolean isVegetarian() {
		return vegetarian;
	}
  
	public void print() {
		System.out.print("  " + getName());
		if (isVegetarian()) {
			System.out.print("(v)");
		}
		System.out.println(", " + getPrice());
		System.out.println("     -- " + getDescription());
	}
}
```

Leaf 객체의 역할에 알맞은 기능만 구현한 것을 볼 수 있다.

<br/>

Composite `Menu`

```java
import java.util.Iterator;
import java.util.ArrayList;

public class Menu extends MenuComponent {
	ArrayList<MenuComponent> menuComponents = new ArrayList<MenuComponent>();
	String name;
	String description;
  
	public Menu(String name, String description) {
		this.name = name;
		this.description = description;
	}
 
	public void add(MenuComponent menuComponent) {
		menuComponents.add(menuComponent);
	}
 
	public void remove(MenuComponent menuComponent) {
		menuComponents.remove(menuComponent);
	}
 
	public MenuComponent getChild(int i) {
		return (MenuComponent)menuComponents.get(i);
	}
 
	public String getName() {
		return name;
	}
 
	public String getDescription() {
		return description;
	}
 
	public void print() {
		System.out.print("\n" + getName());
		System.out.println(", " + getDescription());
		System.out.println("---------------------");
  
		Iterator<MenuComponent> iterator = menuComponents.iterator();
		while (iterator.hasNext()) {
			MenuComponent menuComponent = 
				(MenuComponent)iterator.next();
			menuComponent.print();
		}
	}
}
```

Leaf와 동일한 추상 클래스를 상속 받았다.

Composite은 내부에 Leaf를 저장하기 위해 List 컬렉션은 가지고 있다.

Leaf를 관리하는 메소드 **`add()`, `remove()`, `getChild()`** 등을 가지고 있다.

print() 메소드를 잘 보자.

Composite 객체는 구성 요소로 Composite 객체와 Leaf객체를 모두 가질 수 있고, 위 print() 문처럼 구성 요소를 모두 탐색하여 현재 자신의 Leaf 객체 뿐만 아니라 **Composite 객체가 가지고 있는 Composite 객체의 Leaf 객체들까지 재귀적으로 모두 호출될 수 있다.**

이렇게 하지 않으면 복합 객체를 모두 돌아다니면서 그 안에 들어있는 내용도 모두 출력해야한다.

<br/>

Client `Waitress`

```java
public class Waitress {
	MenuComponent allMenus;
 
	public Waitress(MenuComponent allMenus) {
		this.allMenus = allMenus;
	}
 
	public void printMenu() {
		allMenus.print();
	}
}
```

클라이언트는 가장 최상위 루트 Composite 객체만 알고 있어도 재귀 구조를 통해 모든 Leaf 객체와 Composite 객체를 탐색할 수 있다.

<br/>

TestCode

```java
public class MenuTestDrive {
	public static void main(String args[]) {
		MenuComponent pancakeHouseMenu = 
			new Menu("PANCAKE HOUSE MENU", "Breakfast");
		MenuComponent dinerMenu = 
			new Menu("DINER MENU", "Lunch");
		MenuComponent cafeMenu = 
			new Menu("CAFE MENU", "Dinner");
		MenuComponent dessertMenu = 
			new Menu("DESSERT MENU", "Dessert of course!");
		MenuComponent coffeeMenu = new Menu("COFFEE MENU", "Stuff to go with your afternoon coffee");
  
		MenuComponent allMenus = new Menu("ALL MENUS", "All menus combined"); // 루트 복합 객체
  
		allMenus.add(pancakeHouseMenu); // 복합 객체
		allMenus.add(dinerMenu); // 복합 객체
		allMenus.add(cafeMenu); // 복합 객체
		
		dinerMenu.add(new MenuItem(
			"Pasta",
			"Spaghetti with marinara sauce, and a slice of sourdough bread",
			true, 
			3.89));
   
		dinerMenu.add(dessertMenu); // 복합 객체

		dessertMenu.add(new MenuItem(
			"Apple Pie",
			"Apple pie with a flakey crust, topped with vanilla icecream",
			true,
			1.59)); // desserMenu에 더해지는 객체는 Leaf 객체, MenuItem은 Leaf 객체다.

		Waitress waitress = new Waitress(allMenus);
   
		waitress.printMenu();
```

<br/>

즉, 복합 객체들의 구성을 보면 대략 다음의 그림과 같을 것이다.


<p align="center"><img width="480" alt="Tree" src="https://user-images.githubusercontent.com/76640167/211369034-f540667f-751d-42cd-a64c-11ef795928dd.png">