# 실전 디자인 패턴

<br/>

## 디자인 패턴의 정의

> 💡 `패턴(Pattern)`은 특정 **컨텍스트** 내에서 주어진 **문제**의 **해결책**이다.

<br/>

### 컨텍스트(context)

- 패턴이 적용되는 상황
- **반복적으로** 일어날 수 있는 상황이어야만 한다.
- e.g., 객체들의 컬렉션이 주어져 있다.

<br/>

### 문제(problem)

- 컨텍스트 내에서 이뤄야 하는 목표
- 컨텍스트 내의 제약조건도 포함된다.
- e.g., 컬렉션의 구현을 드러내지 않으면서 그 안에 있는 각 객체를 대상으로 순환 작업을 할 수 있어야 한다.

<br/>

### 해결책(solution)

- 제약조건 속에서 누가 적용해도 목표를 이룰 수 있는 **일반적인** 디자인
- e.g., 반복 작업을 별도의 클래스로 캡슐화한다.

<br/>

> 어떤 컨텍스트 내에서   
> 일련의 제약조건에 의해 영향을 받는 문제가 발생했다면,  
> 그 제약조건 내에서 목적 달성을 위한 해결책이 되는 디자인을 적용하면 된다.

<br/>
<br/>

## 포스(force)

디자인 패턴의 정의에 따르면, `문제`**는 목적과 일련의 제약조건으로 구성된다.**

어떤 이들은 이를 합쳐서 `포스(force)`라고 부르기도 한다.

<br/>

즉, 패턴의 정의에서 포스란 **해결책을 형성하고 제어하는 것**이다.

<br/>
<br/>

The notion of `force` generalizes the kinds of criteria that software engineers **use to justify designs and
implementations.**

*For example, in the classic study of algorithms in computer science, the main force to be resolved is efficiency (time
complexity).*

<br/>

However, patterns deal with the larger, harder-to-measure, and conflicting sets of goals and constraints encountered in
the development of every artifact you ever create.

For example, correctness, resources, structure, construction, usage.

(더 자세한 내용은 [해당 링크](https://www.cs.unc.edu/~stotts/COMP723-s13/patterns/forces.html)를 참고하길 바란다.)

<br/>
<br/>

해결책이 포스의 양면 사이에서 적절하게 **균형**을 이룰 수 있어야 비로소 제대로 된 패턴이 만들어진다.

- 포스의 밝은 측면 - 목표
- 포스의 어두운 측면 - 제약조건

<br/>
<br/>
<br/>

# 패턴 카탈로그

### 구성

패턴 카탈로그에서는 패턴들이 다음과 같이 기술된다.

<br/>

<p align="center"><img width="770" alt="패턴 카탈로그" src="https://user-images.githubusercontent.com/86337233/214296188-4d49dff9-3360-40ec-b43c-a76dcb37ed6a.png">

<br/>
<br/>

- `용도, Intent`
- `동기, Motivation`
- `적용 대상(활용성), Applicability`
- `구조, Structure`
- `구성 요소, Participant`
- `협력 방법, Collaborations` : 각 구성 요소가 패턴 내에서 어떤 식으로 서로 도움을 주는지에 대한 설명
- `결과, Consequences`
- `구현, Implementation`
- `샘플 코드, Sample Code`
- `잘 알려진 사용 예, Known uses`
- `관련 패턴, Related Patterns` : 이 패턴과 밀접하게 관련된 다른 패턴들과 이들의 중요한 차이점 등

<br/>

패턴 카탈로그에 대해 찾아보다가 패턴들이 잘 정리되어 있는 좋은 사이트를 찾아서 아래에 공유한다.  
(한국어 번역이 존재하나, 부자연스러운 부분들이 중간중간 있으니 원문과 같이 읽는 것을 추천함)

[The Catalog of Design Patterns](https://refactoring.guru/design-patterns/catalog)

<br/>

### 활용법

1. 우선 모든 패턴을 익힌 후 **여러 패턴 사이의 관계를 파악한다.**


2. `적용 대상` 섹션과 `결과` 섹션을 통해 적절한 패턴인지 판단을 내린다.


3. 패턴을 디자인에 반영하고 구현할 경우
    1. `구조` 섹션을 보고 클래스 다이어그램을 살펴본다.
    2. `구성 요소` 섹션에서 각 클래스의 역할을 제대로 파악하고 있는지 확인한다.
    3. `구현/샘플 코드` 섹션에서 구현 기법과 주의사항 등을 공부한다.

<br/>

### 디자인 패턴을 고르는 방법

문제에 적합한 디자인 패턴을 찾아내는 여러가지 접근 방법은 다음과 같다.

- 패턴이 어떻게 문제를 해결하는지 파악한다.
- 패턴이 의도하는 부분을 본다.
- 패턴들간의 관련성을 파악한다.
- 비슷한 목적의 패턴들을 모아서 공부한다.
- 재설계의 원인을 파악한다.
- **설계에서 가변성을 가져야 하는 부분이 무엇인지 파악한다.**

<br/>

### 디자인 패턴의 참조 관계도

<br/>

<p align="center"><img width="600" alt="참조 관계도" src="https://user-images.githubusercontent.com/86337233/214296511-351a04d1-c002-4704-8e4c-d655b8ef2522.png">

<br/>
<br/>

`Abstract Factory 패턴`은 종종 `Factory Method 패턴`이나 `Prototype 패턴`을 이용해서 구현되며,  
`Singleton 패턴`을 이용해서 하나의 공유되는 객체를 관리할 수 있다.

<br/>

Builder 패턴은 복잡한 객체를 생성한다는 점에서 Abstract Factory 패턴과 유사하다.

그러나 `Abstract Factory 패턴`이 **제품군(Product Family) 별로 객체를 생성**하는데 중점을 둔다면  
`Builder 패턴`은 **단계적으로 객체를 생성**한다는 점이 커다란 차이점이다.

또한 `Builder 패턴`의 경우에는 몇 단계의 작업을 거친 후에야 최종적으로 결과를 되돌리는데 반해,  
`Abstract Factory 패턴`의 경우에는 곧바로 생성된 결과를 되돌린다.

Builder 패턴에 의해 생성되는 객체는 `Composite 패턴`의 형태일 때가 많다.

<br/>

`Factory Method 패턴`은 `Abstract Factory 패턴`을 구현할 때 많이 사용된다.

또한 Factory Method 패턴은 `Template Method 패턴` 내에서 불리어지는 경우가 많다.

<br/>

`Prototype 패턴`은 `Abstract Factory 패턴`과 같이 사용되거나 대신 사용될 수도 있다.

또한 `Prototype 패턴`은 `Composite 패턴`이나 `Decorator 패턴`이 많이 사용되는 곳에서 같이 사용하면 유용할 경우가 많다.

Prototype 패턴과 Factory Method 패턴을 비교해보면 `Prototype 패턴`은 객체 생성시 반드시 하위 클래스를 필요로 하지 않는 대신,  
생성된 객체를 초기화시키기 위한 인터페이스를 별도로 필요로 할 경우가 많다.

반면 `Factory Method 패턴`은 객체 생성시 하위 클래스를 정의해서 생성하는 형태를 취하게 된다.

<br/>

Adapter 패턴은 Bridge 패턴과 유사한 구조를 가진다. 그러나 두 패턴의 사용 목적은 완전히 다르다.

즉, `Bridge 패턴`의 경우에는 **인터페이스와 구현을 분리**시켜줌으로써 **서로 독립적으로 변경이 가능하게** 만들려는 목적인데 반해  
`Adapter 패턴`은 **이미 존재하는 객체의 인터페이스를 다른 형태로 변경시켜주는 것**이 목적이다.

<br/>

`Decorator 패턴`은 인터페이스의 변경 없이 객체의 기능을 추가할 수 있는 방식이며, 이런 측면에서 `Adapter 패턴`보다 좀 더 유연하다.

<br/>

Facade 패턴은 **기존 클래스의 기능을 추상화시킨다**는 점에서 Mediator 패턴과 유사하다.

그러나 `Mediator 패턴`에서는 다른 객체들이 Mediator 객체의 존재를 알아야 하지만, `Facade 패턴`에서는 그럴 필요가 없다는 것이 다른 점이다.

즉, Facade 패턴에서는 **서브 시스템 내의 객체들이 Facade 객체의 존재를 알 필요가 없다.**

더불어 `Mediator 패턴`에서는 각 객체들이 서로 직접적인 통신을 하지 않는 것이 원칙이나, Facade 패턴에서는 그런 제약이 없다.

한편 `Facade 객체`는 하나만 존재하면 될 경우가 많으므로, 주로 `Singleton 패턴` 형태로 구현될 경우가 많다.

<br/>

`Flyweight 패턴`은 `Strategy, State, Interpreter 패턴`에서 알고리즘이나 상태 정보 Terminal Symbol들을 각각 공유하기 위해 사용될 수 있다.

<br/>

`Decorator 패턴`은 **이미 존재하는 객체에게 새로운 기능을 덧입히는** 역할을 하는데 반해  
`Strategy 패턴`은 객체 내부의 구체적인 동작이나 알고리즘을 **외부 객체에게 위임**시키는 형태다.

따라서 Decorate 패턴은 객체의 겉을 변경시켜주는 역할을 하고, Strategy 패턴은 객체의 속을 변경시켜주는 역할을 한다고 할 수 있다.

<br/>

`Proxy 패턴`은 Client가 호출하는 인터페이스의 변경 없이 다른 객체의 기능 수행을 대행해주는 역할을 한다.

그러나 Proxy 패턴은 `Adapter 패턴`과는 달리 **인터페이스의 변경이 주목적이 아니다.**

<br/>

`Memento 패턴`은 `Iterator 패턴`의 반복 상태를 저장하기 위해 사용되거나,  
`Command 패턴`에서 명령이 수행되기 이전 상태로 되돌리기 위해 사용될 수 있다.

<br/>

`Observer 패턴`은 객체들간의 의존 관계를 **분산시켜** 관리하는데 반해 `Mediator 패턴`은 이들을 **중앙 집중적으로** 관리한다.

따라서 객체들간의 복잡한 의존 관계를 한 곳에서 관리하고 싶을 때 Mediator 패턴을 사용할 수 있다.

<br/>

`Template Method 패턴`에서는 내부의 알고리즘 단계를 정의할 때 `Strategy 패턴`을 사용할 수 있으며,  
대개 `Factory Method 패턴`을 사용해서 필요한 객체를 생성한다.

<br/>
<br/>

# 디자인 패턴의 범주

대부분의 카탈로그에서는 몇 가지 범주에 맞춰서 디자인 패턴을 분류하고 있는데,

그중 제일 유명한 분류 방법은 `생성`, `행동`, `구조`라는 3가지 범주로 용도에 따라 나누는 것이다.

<br/>

- 아래의 패턴 목록들 중 Chapter 1 ~ 12에서 설명되지 않은 패턴들은 Chapter 13에서 설명된다.
- 클래스 다이어그램들과 영문 설명은 [GoF Design Patterns Card](/Chapter_13/GoF%20Design%20Patterns%20Card.pdf)에서 가져왔다.

<br/>
<br/>

## 생성 패턴(Creational Pattern)

> 💡 객체 인스턴스를 생성하는 패턴

- 클라이언트와 그 클라이언트가 생성해야 하는 객체 인스턴스 사이의 연결을 끊어준다.
- 기존 코드의 유연성과 재사용을 증가시키는 다양한 객체 생성 매커니즘들을 제공한다.

<br/>

### 팩토리 메서드(Factory Method) 패턴

서브클래스에서 생성할 구상 클래스를 결정한다.

즉, 부모 클래스에서 객체들을 생성할 수 있는 인터페이스를 제공하지만 자식 클래스들이 생성될 객체들의 유형을 변경할 수 있도록 한다.

Define an interface for creating an object, but let subclasses decide which class to instantiate.

Lets a class defer instantiation to subclasses.

<br/>

<p align="center"><img width="400" alt="팩토리 메서드 패턴" src="https://user-images.githubusercontent.com/86337233/214294978-b8265fef-6a32-4b50-9c6b-2e917d0bf7bb.png">

<br/>
<br/>

### 추상 팩토리(Abstract Factory) 패턴

클라이언트에서 구상 클래스를 지정하지 않으면서도 객체군(관련 객체들의 모음)을 생성할 수 있도록 해준다.

Provides an interface for creating families of related or dependent objects without specifying their concrete class.

<br/>

<p align="center"><img width="430" alt="추상 팩토리 패턴" src="https://user-images.githubusercontent.com/86337233/214294986-683fb6d1-1475-4526-857b-fd92583b2e18.png">

<br/>
<br/>

### 빌더(Builder) 패턴

복잡한 객체들을 단계별로 생성할 수 있도록 해준다.

이 패턴을 통해 같은 제작 코드로 객체의 다양한 유형들과 표현을 제작할 수 있게 된다.

Separate the construction of a complex object from its representing so that the same construction process can create
different representations.

<br/>

<p align="center"><img width="400" alt="빌더 패턴" src="https://user-images.githubusercontent.com/86337233/214294989-ba27d74d-db30-44fd-8abf-b44f96ad2484.png">

<br/>
<br/>

### 프로토타입(Prototype) 패턴

코드를 그들의 클래스들에 의존시키지 않고 기존 객체들을 복사할 수 있도록 한다.

Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype.

<br/>

<p align="center"><img width="430" alt="프로토타입 패턴" src="https://user-images.githubusercontent.com/86337233/214294992-a6872db5-3ebe-4125-b7cd-ecfffeb45a13.png">

<br/>
<br/>

### 싱글턴(Singleton) 패턴

클래스에 인스턴스가 하나만 있도록 하면서 이 인스턴스에 대한 전역 접근(액세스) 지점을 제공한다.

Ensure a class only has one instance and provide a global point of access to it.

<br/>

<p align="center"><img width="170" alt="싱글턴 패턴" src="https://user-images.githubusercontent.com/86337233/214294997-420f2ee0-588f-4793-a9af-72cc69372e11.png">

<br/>
<br/>
<br/>

## 행동 패턴(Behavioral Pattern)

> 💡 클래스와 객체들이 상호작용하는 방법과 역할을 분담하는 방법을 다루는 패턴

- 알고리즘들 및 객체 간의 책임 할당과 관련이 있다.

<br/>

### 책임 연쇄(Chain of Responsibility) 패턴

핸들러들의 체인(사슬)을 따라 요청을 전달할 수 있게 해준다.

각 핸들러는 요청을 받으면 요청을 처리할지 아니면 체인의 다음 핸들러로 전달할지를 결정한다.

Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request.

Chain the receiving objects and pass the request along the chain until an object handles it.

<br/>

<p align="center"><img width="470" alt="책임 연쇄 패턴" src="https://user-images.githubusercontent.com/86337233/214295001-181055f8-9c58-4e18-977f-28ce721aadc5.png">

<br/>
<br/>

### 커맨드(Command) 패턴

요청을 요청에 대한 모든 정보가 포함된 독립실행형 객체로 변환한다.

이 변환은 다양한 요청들이 있는 메서드들을 인수화 할 수 있도록 하며,  
요청의 실행을 지연 또는 대기열에 넣을 수 있도록 하고, 또 실행 취소할 수 있는 작업을 지원할 수 있도록 한다.

Encapsulate a request as an object,  
thereby letting you parameterize clients with different requests, queue or log requests, and support undoable
operations.

<br/>

<p align="center"><img width="420" alt="커맨드 패턴" src="https://user-images.githubusercontent.com/86337233/214295003-7e3d1798-30ad-4715-8ee3-80039260db8e.png">

<br/>
<br/>

### 인터프리터(Interpreter) 패턴

어떤 언어의 대해, 그 언어의 문법에 대한 표현을 정의하면서 그 표현을 사용하여 해당 언어로 기술된 문장을 해석하는 해석자를 함께 정의한다.

Given a language, define a representation for its grammar along with an interpreter that uses the representation to
interpret sentences in the language.

<br/>

<p align="center"><img width="400" alt="인터프리터 패턴" src="https://user-images.githubusercontent.com/86337233/214295008-35160d5f-ad1e-4bd2-9a7c-d8a493a1aaba.png">

<br/>
<br/>

### 반복자(Iterator) 패턴

컬렉션이 어떤 식으로 구현되었는지(리스트, 스택, 트리 등) 드러내지 않으면서도  
컬렉션 내에 있는 모든 객체를 하나씩 순회하여 반복 작업을 처리할 수 있게 해준다.

Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

<br/>

<p align="center"><img width="400" alt="반복자 패턴" src="https://user-images.githubusercontent.com/86337233/214295009-4699fff0-ee01-4e68-a140-9b376841573f.png">

<br/>
<br/>

### 중재자(Mediator) 패턴

객체 간의 직접 통신을 제한하고 중재자 객체를 통해서만 협력하도록 한다.

따라서 이 패턴을 통해 객체 간의 혼란스러운 의존 관계들을 줄일 수 있다.

Define an object that encapsulates how a set of objects interact.

Promotes loose coupling by keeping objects from referring to each other explicitly and it lets you vary their
interactions independently.

<br/>

<p align="center"><img width="420" alt="중재자 패턴" src="https://user-images.githubusercontent.com/86337233/214295010-06dd07a2-f78d-409a-bf56-d65c61249962.png">

<br/>
<br/>

### 메멘토(Memento) 패턴

객체의 구현 세부 사항을 공개하지 않으면서 해당 객체의 이전 상태를 저장하고 복원할 수 있게 해준다.

Without violating encapsulation, capture and externalize an object's internal state so that the object can be restored
to this state later.

<br/>

<p align="center"><img width="370" alt="팩토리 메서드 패턴" src="https://user-images.githubusercontent.com/86337233/214295017-f9869e21-37c4-4f83-b974-e914fd556dfc.png">

<br/>
<br/>

### 옵저버(Observer) 패턴

여러 객체에 자신이 관찰 중인 객체에 발생하는 모든 이벤트에 대하여 알리는 구독 메커니즘을 정의할 수 있도록 한다.

Define a one-to-many dependency between objects  
so that when one object changes state, all its dependents are notified and updated automatically.

<br/>

<p align="center"><img width="400" alt="옵저버 패턴" src="https://user-images.githubusercontent.com/86337233/214295501-5b46e152-c93b-4ce6-b048-0506957d907f.png">

<br/>
<br/>

### 상태(State) 패턴

상태를 기반으로 하는 행동을 캡슐화한 다음 위임으로 필요한 행동을 선택한다.

즉, 객체의 내부 상태가 변경될 때 해당 객체가 그의 행동을 변경할 수 있도록 하기에  
객체가 행동을 변경할 때 객체가 클래스를 변경한 것처럼 보일 수 있다.

Allow an object to alter its behavior when its internal state changes.

The object will appear to change its class.

<br/>

<p align="center"><img width="420" alt="상태 패턴" src="https://user-images.githubusercontent.com/86337233/214295491-9ba0cd15-49a8-4d56-8916-56fe1c702a83.png">

<br/>
<br/>

### 전략(Strategy) 패턴

교환 가능한 행동을 캡슐화하고 위임으로 어떤 행동을 사용할지 결정한다.

즉, 알고리즘들의 패밀리를 정의하고, 각 패밀리를 별도의 클래스에 넣은 후 그들의 객체들을 상호교환할 수 있도록 한다.

Define a family of algorithms, encapsulate each one, and make them interchangeable.

Lets the algorithm vary independently from clients that use it.

<br/>

<p align="center"><img width="400" alt="전략 패턴" src="https://user-images.githubusercontent.com/86337233/214295505-9b6d9d52-7016-41b7-b519-5affe2ff80ab.png">

<br/>
<br/>

### 템플릿 메서드(Template Method) 패턴

서브클래스에서 생성할 구상 클래스를 결정한다.

부모 클래스에서 알고리즘의 골격을 정의하지만,  
해당 알고리즘의 구조를 변경하지 않고 자식 클래스들이 알고리즘의 특정 단계들을 오버라이드(재정의)할 수 있도록 한다.

Define the skeleton of an algorithm in an operation, deferring some steps to subclasses.

Lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.

<br/>

<p align="center"><img width="170" alt="템플릿 메서드 패턴" src="https://user-images.githubusercontent.com/86337233/214295509-5380869e-2c43-4492-a7af-7da4fc9e0521.png">

<br/>
<br/>

### 비지터(Visitor) 패턴

알고리즘들을 그들이 작동하는 객체들로부터 분리할 수 있도록 한다.

Represent an operation to be performed on the elements of an object structure.

Lets you define a new operation without changing the classes of the elements on which it operates.

<br/>

<p align="center"><img width="500" alt="팩토리 메서드 패턴" src="https://user-images.githubusercontent.com/86337233/214295511-1567113f-8d61-486c-9e2b-c521db10cf9a.png">

<br/>
<br/>
<br/>

## 구조 패턴(Structural Pattern)

> 💡 클래스와 객체들의 구조를 유연하고 효율적으로 유지하면서 더 큰 구조로 만들 수 있게 구상을 사용하는 패턴

- 클래스와 객체가 새로운 구조와 기능을 만들려고 클래스와 객체를 구성하는 방법들이다.

<br/>

### 어댑터(Adapter) 패턴

객체를 감싸서 다른 인터페이스를 제공하는 것을 통해 호환되지 않는 인터페이스를 가진 객체들이 협업할 수 있도록 한다.

Convert the interface of a class into another interface clients expect.

Lets classes work together that couldn't otherwise because of incompatible interfaces.

<br/>

<p align="center"><img width="400" alt="어댑터 패턴" src="https://user-images.githubusercontent.com/86337233/214295522-9da13b86-513f-4f8b-9858-08f3cfb07a16.png">

<br/>
<br/>

### 브리지(Bridge) 패턴

큰 클래스 또는 밀접하게 관련된 클래스들의 집합을 두 개의 개별 계층구조(추상화 및 구현)로 나눈 후 각각 독립적으로 개발할 수 있도록 한다.

Decouple an abstraction from its implementation so that the two can vary independently.

<br/>

<p align="center"><img width="400" alt="브리지 패턴" src="https://user-images.githubusercontent.com/86337233/214295524-e69998df-f120-41f9-9545-1d6301d1d0e3.png">

<br/>
<br/>

### 복합체(Composite) 패턴

객체들을 트리 구조들로 구성한 후, 클라이언트에서 객체 컬렉션과 개별 객체를 똑같이 다룰 수 있도록 한다.

Compose objects into tree structures to represent part-whole hierarchies.

Lets clients treat individual objects and compositions of objects uniformly.

<br/>

<p align="center"><img width="400" alt="복합체 패턴" src="https://user-images.githubusercontent.com/86337233/214295526-ff247c8f-2463-4a0a-a4d7-07afd42f0b42.png">

<br/>
<br/>

### 데코레이터(Decorator) 패턴

객체를 특수 래퍼(wrapper) 객체들로 감싸서 새로운 행동을 제공한다.

Attach additional responsibilities to an object dynamically.

Provide a flexible alternative to sub-classing for extending functionality.

<br/>

<p align="center"><img width="400" alt="팩토리 메서드 패턴" src="https://user-images.githubusercontent.com/86337233/214295531-44e372dc-f487-475a-a6c2-b8e7118d3aa2.png">

<br/>
<br/>

### 퍼사드(Facade) 패턴

라이브러리, 프레임워크 또는 다른 일련의 클래스들에 대한 단순화된 인터페이스를 제공한다.

Provide a unified interface to a set of interfaces in a subsystem.

Defines a high- level interface that makes the subsystem easier to use.

<br/>

<p align="center"><img width="430" alt="퍼사드 패턴" src="https://user-images.githubusercontent.com/86337233/214295538-ec46297c-1c7a-43c8-a4d3-16921323b9d3.png">

<br/>
<br/>

### 플라이웨이트(Flyweight) 패턴

각 객체에 모든 데이터를 유지하는 대신,  
여러 객체들 간에 상태의 공통 부분들을 공유하여 사용할 수 있는 RAM에 더 많은 객체들을 포함할 수 있도록 한다.

Use sharing to support large numbers of fine grained objects efficiently.

<br/>

<p align="center"><img width="510" alt="플라이웨이트 패턴" src="https://user-images.githubusercontent.com/86337233/214295540-094780b6-2953-4d67-9031-4190660aee71.png">

<br/>
<br/>

### 프록시(Proxy) 패턴

객체를 감싸서 그 객체로의 접근을 제어한다.

다른 객체에 대한 대체 또는 자리표시자를 제공할 수 있으며,  
클라이언트의 요청이 원래 객체에 전달되기 전 또는 후에 무언가를 수행할 수 있도록 한다.

Provide a surrogate or placeholder for another object to control access to it.

<br/>

<p align="center"><img width="430" alt="프록시 패턴" src="https://user-images.githubusercontent.com/86337233/214295547-9088ac3d-2f3b-4a2f-a2f4-db8707326e1b.png">

<br/>
<br/>
<br/>

---

<br/>

`데코레이터 패턴`은 3가지 범주 중 `구조 패턴`에 들어가 있는데,  
이 패턴은 행동을 추가하는 패턴이기 때문에 행동 패턴에 들어가야 한다고 생각하는 경우가 많다.

<br/>

- `구조 패턴`은 클래스와 객체가 **새로운 구조와 기능을 만들려고** 클래스와 객체를 구성하는 방법이다.
- `데코레이터 패턴`은 한 객체를 다른 객체로 감싸서 **새로운 기능을 제공해 주는** 패턴이다.

<br/>

따라서 행동 패턴의 용도인 객체 사이의 통신과 상호연결보다는
**객체들을 동적으로 구성해서 새로운 기능을 얻는 쪽에 초점을 맞춰야 한다.**

<br/>

`데코레이터(구조 패턴)`과 `프록시 패턴(행동 패턴)`이 상당히 유사하기 때문에 분류하기가 더욱 어려운데,  
이 둘은 용도가 다르다는 것을 반드시 기억하길 바란다.

- `데코레이터 패턴` : 클래스에 새로운 행동을 추가하는 용도
- `프록시 패턴` : 어떤 클래스로의 접근을 제어하는 용도

<br/>
<br/>
<br/>


이렇게 `생성`, `구조`, `행동의` 3가지 범주로 나눈 후,  
`분리 패턴(Decoupling Pattern)`과 같은 식으로 하위 범주로 다시 나누기도 한다.

(When we say two pieces of code are **“decoupled”**, we mean a change in one usually doesn’t require a change in the
other.)

<br/>
<br/>
<br/>
<br/>

위와는 별개로, `클래스`를 다루는 패턴인지, `객체`를 다루는 패턴인지에 따라 패턴을 분류하는 방법도 있다.

<br/>

### 클래스 패턴(Class Pattern)

> 💡 클래스 사이의 관계가 상속으로 어떻게 정의되는지를 다룬다.

클래스 사이의 관계는 대부분 컴파일할 때 결정된다.

<br/>

- 팩토리 메서드(Factory Method) 패턴
- 인터프리터(Interpreter) 패턴
- 템플릿 메서드(Template Method) 패턴
- 어댑터(Adaptor) 패턴

<br/>

### 객체 패턴(Object Pattern)

> 💡 객체 사이의 관계를 다루며, 객체 사이의 관계는 보통 구성으로 정의된다.

- 일반적으로 실행 중에 관계가 결정되기 때문에 보다 동적이고 유연하다.

<br/>

- 추상 팩토리(Abstract Factory) 패턴
- 빌더(Builder) 패턴
- 프로토타입(Prototype) 패턴
- 싱글턴(Singleton) 패턴
- 책임 연쇄(Chain of Responsibility) 패턴
- 커맨드(Command) 패턴
- 반복자(Iterator) 패턴
- 중재자(Mediator) 패턴
- 메멘토(Memento) 패턴
- 옵저버(Observer) 패턴
- 상태(State) 패턴
- 전략(Strategy) 패턴
- 비지터(Visitor) 패턴
- 브리지(Bridge) 패턴
- 복합체(Composite) 패턴
- 데코레이터(Decorator) 패턴
- 퍼사드(Facade) 패턴
- 플라이웨이트(Flyweight) 패턴
- 프록시(Proxy) 패턴

<br/>
<br/>
<br/>

# 패턴으로 생각하기

`패턴으로 생각한다`는 것은 어떤 디자인을 봤을 때 **패턴 적용 여부를 결정할 수 있는 안목**을 가졌다는 것이다.

<br/>

`디자인 패턴`은 필요할 때만 써야 하는 도구에 불과하다.

어떤 디자인이든 될 수 있으면 단순하게 만들어야 한다.

<br/>

따라서 `디자인 원칙들`을 바탕으로 제 할 일을 완수할 수 있는 가장 간단한 코드를 만들다가,  
**패턴이 꼭 필요한 상황(확장성이 필요한 경우)이 닥쳤을 때에만** 디자인 패턴을 사용하여 조금 복잡하게 만드는 것이 좋다.

> 실질적인 확장성만을 추구해야 한다.


<br/>


또한, 객체지향 원칙들을 종합적으로 고려하여 패턴이 필요한 상황이 왔을 때 패턴을 **필요에 따라 적절히 변형해서** 적용할 수도 있어야 한다.

<br/>
<br/>

다음은 패턴으로 생각하는 데 있어서 도움이 될 만한 내용들이다.

<br/>

### 최대한 단순하게

디자인을 할 때 가장 중요한 원칙은 `최대한 단순한 방법(KISS, Keep it Simple)으로 문제를 해결하기`이다.

“이 문제에 어떻게 패턴을 적용할 수 있을까?”가 아닌, <b>“어떻게 하면 단순하게 해결할 수 있을까?”</b>에 초점을 맞춰야 한다.

가장 단순하고 유연한 디자인을 만들 때 패턴이 있어야 한다면 그때 패턴을 적용하면 된다.

<br/>

### 디자인 패턴은 만병통치약이 아니다

패턴은 반복적으로 발생하는 문제의 일반적인 해결책이며, 수많은 개발자가 오랫동안 검증한 해결책이다.

하지만 패턴을 사용할 때는 그 패턴이 **우리가 설계한 디자인에 미칠 영향과 결과**를 주의 깊게 생각해봐야 한다.

<br/>

### 패턴이 필요할 때

어떤 경우에 패턴을 써야할까?

디자인을 할 때, **지금 디자인상의 문제에 적합하다는 확신이 든다면** 패턴을 도입해야 한다.

<br/>

> ✅ 간단한 해결책만으로는 부족하다고 확신을 가지면 **해결해야 할 문제와 제약조건을 종합적으로 고려해 봐야 한다.**

1. 만약 어떤 패턴을 써야 할지 잘 모르겠다면 문제 해결에 도움이 될 만한 패턴이 있는지를 훑어봐야 한다.

   이때 패턴 카탈로그의 용도와 적용 대상 섹션을 살펴보면 좋다.


2. 괜찮은 패턴을 찾았다면 패턴 카탈로그의 결과 섹션을 보고
   **디자인의 나머지 부분에 미치는 영향이 어느 정도인지** 확인해 보아야 한다.

<br/>

> ✅ 간단한 해결책으로 문제가 해결되는 데도 **시스템의 어떤 부분이 변경될 거라고 예측되는 상황에는** 디자인 패턴을 적용해야 한다.

- 디자인에서 변경될 수 있는 부분이 있다면(발생 가능성이 높은 실질적인 변경) 패턴을 적용할 여지가 있다.


- 하지만 발생 가능성이 그리 높지 않은 가상적인 변경에 대비해서 패턴을 적용하는 일은 바람직하지 않다.

<br/>

### 리팩터링과 패턴

리팩터링의 목적은 행동 변경이 아니라 `구조 개선`에 있다.

따라서 리팩터링은 패턴을 사용하면 구조가 더 개선될 수 있을지 검토해볼 수 있는 아주 좋은 기회이다.

<br/>

### 꼭 필요하지 않은 패턴은 빼자

시스템이 점점 복잡해지면서 처음에 기대했던 유연성이 전혀 발휘되지 못한다면 패턴을 과감하게 제거해 버리는 것이 낫다.

**즉, 패턴보다 간단한 해결책이 더 나을 것 같다 싶을 때 패턴을 제거하면 된다.**

<br/>

### 꼭 필요하지 않은 패턴을 미리 적용할 필요는 없다

지금 당장 변화에 대처하는 디자인을 만들어야 한다면 패턴을 적용해서 그 변화에 적응해야 하지만,  
꼭 필요하지 않은데도 괜히 패턴을 추가하는 일은 피해야 한다.

패턴을 쓰다 보면 시스템이 더 복잡해지는 경향이 있으며, 나중에 그 패턴을 사용하지 않을 수도 있기 때문이다.

<br/>
<br/>

> 💡 항상 가장 간단한 해결책으로 목적을 달성할 수 있도록 하고, 반드시 필요할 때만 디자인 패턴을 적용해야 한다.


<br/>
<br/>

# 안티 패턴(Anti-Pattern)

> 💡 안티 패턴은 어떤 문제의 나쁜 해결책에 이르는 길을 알려준다.

<br/>

- 일상적인 문제의 자주 반복되는 나쁜 해결책을 문서로 만들면 다른 개발자들이 똑같은 실수를 하지 않도록 방지할 수 있다.


- 요소
    - 어떤 이유로 나쁜 해결책에 유혹되는가
    - 장기적인 관점에서 그 해결칙이 나쁜 이유
    - 좋은 해결책을 만들 때 적용할 수 있는 다른 패턴에 대한 제안


- 종류
    - 개발 안티 패턴
    - 객체지향 안티 패턴
    - 조직 안티 패턴
    - 특정 영역 안티 패턴

<br/>

[해당 사이트](https://wiki.c2.com/?CategoryAntiPattern)에서 다양한 안티 패턴을 찾아볼 수 있다.

<br/>
<br/>

---

<br/>

### 디자인 패턴 관련 자료

[포틀랜드 패턴 레포지토리(The Portland Patterns Repository)](https://wiki.c2.com/?PortlandPatternRepository)

패턴과 객체지향 시스템에 관련된 모든 것을 담은 위키이다.
