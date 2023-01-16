# 프록시 패턴(Proxy Pattern)

> 💡 프록시 패턴은 <b>특정 객체로의 접근을 제어하는 대리인(특정 객체를 대변하는 객체, 즉 프록시)</b>을 제공한다.

이를 통해 **클라이언트의 접근을 제어**할 수 있으며, 접근을 관리하는 방법에는 여러가지가 존재한다.

<br/>

### 클래스 다이어그램

<p align="center"><img width="680" alt="클래스 다이어그램" src="https://user-images.githubusercontent.com/86337233/212689080-b46f70ca-0561-4bdd-b6c5-3c7f59945506.png">

<br/>
<br/>

- `Subject` 인터페이스


- `RealSubject` 클래스
    - 진짜 작업을 대부분 처리하는 서비스 객체


- `Proxy` 클래스
    - 서비스 객체의 대변인 역할을 하면서 이 객체로의 접근을 제어한다.
    - 서비스 객체의 레퍼런스가 들어있어, 클라이언트가 서비스가 필요하다면 이 레퍼런스를 사용해서 서비스 객체에 요청을 전달한다.
    - Proxy에서 RealSubject의 인스턴스를 생성하거나, 그 객체의 생성 과정에 관여하는 경우가 많다.

<br/>

### 종류

> 💡 프록시 패턴에는 다양한 변종들이 존재하는데,  
> 모든 변종의 공통점은 클라이언트가 실제 객체의 메소드를 호출하면 **그 호출을 프록시가 중간에 가로챈다**는 점이다.

<br/>

`원격 프록시(Remote Proxy)`  
: 원격 객체로의 접근을 제어 (클라이언트와 원격 객체 사이의 데이터 전달을 관리)

`가상 프록시(Virtual Proxy)`  
: 생성하기 힘든 자원, 즉 인스턴스를 만드는 데 많은 비용이 드는 객체로의 접근을 제어

`보호 프록시(Protection Proxy)`  
: 접근 권한이 필요한 자원으로의 접근을 제어 (호출하는 쪽의 권한에 따라서 객Ω체에 있는 메서드로의 접근을 제어)

`방화벽 프록시(Firewall Proxy)`  
: 일련의 네트워크 자원으로의 접근을 제어 → 주제를 ‘나쁜’ 클라이언트로부터 보호해줌

`스마트 레퍼런스 프록시(Smart Reference Proxy)` : 주제가 참조될 때마다 추가 행동을 제공

`캐싱 프록시(Caching Proxy)`  
(1) 비용이 많이 드는 작업의 결과를 임시로 저장해줌, (2) 여러 클라이언트에서 결과를 공유하게 해줌  
→ 계산 시간과 네트워크 지연을 줄여주는 효과가 존재

`동기화 프록시(Synchronization Proxy)`  
: 여러 스레드에서 주제에 접근할 때 안전하게 작업을 처리할 수 있도록 해줌

`복잡도 숨김 프록시(Complexity Hiding Proxy)`, `퍼사드 프록시(Facade Proxy)`  
복잡한 클래스의 집합으로의 접근을 제어하고, 그 복잡도를 숨겨줌  
(vs. 퍼사드 패턴 : 프록시는 접근을 제어, **퍼사드 패턴은 대체 인터페이스만 제공함**)

`지연 복사 프록시(Copy-On-Write Proxy)`  
: 객체의 복사를 제어(클라이언트에서 필요로 할 때까지 객체가 복사되는 것을 지연시킴)

<br/>

### 동적 프록시(dynamic proxy)

프록시를 사용하기 위해서는 대상 클래스 수만큼의 프록시 클래스를 하나하나 만들어 주어야 한다는 단점이 존재하며,  
이에 코드 중복 또한 발생하게 된다.

이 단점을 보완하기 위해 `동적 프록시`가 존재하는데,  
컴파일 시점이 아닌, 런타임 시점에 프록시 클래스를 만들어주는 방식이다.

> 동적 프록시를 사용하면 프록시를 적용할 코드를 하나만 만들어 두고 프록시 객체를 런타임 시점에만 생성해주면 된다.

<br/>

자바의 `java.lang.reflect` 패키지 안에 프록시 기능이 내장되어 있다.

<p align="center"><img width="600" alt="동적 프록시" src="https://user-images.githubusercontent.com/86337233/212689085-cd285023-fb6d-4650-88f7-c56207a5bd91.png">

<br/>
<br/>


`Proxy` 클래스에게 무슨 일을 해야 하는지를 알려주는 코드는 `InvocationHandler` 클래스에 넣으면 된다.  
_(이후 보호 프록시 구현 예제에서 동적 프록시의 사용법에 대해 다룰 것이다.)_

<br/>
<br/>

# 원격 프록시와 원격 메소드 호출

> 💡 원격 프록시는 **원격 객체의 로컬 대변자 역할**을 한다.

<br/>

`원격 객체`는 다른 자바 가상 머신의 힙에서 살고 있는 객체(즉, **다른 주소 공간에서 돌아가고 있는 객체**)를 말하며,

이때 `힙(Heap) 메모리`는 자바 프로그램이 실행되면서 동적으로 생성된 객체(new 연산자로 생성된 객체 또는 인스턴스)가 저장되는 공간이다.

<br/>

`로컬 대변자`는 어떤 메소드를 호출하면, **다른 원격 객체에게 그 메소드 호출을 전달해주는 객체**를 말한다.

<br/>

아래 그림처럼 네트워크 통신과 관련된 저수준 작업은 `프록시 객체`에서 처리해준다.

<p align="center"><img width="900" alt="원격" src="https://user-images.githubusercontent.com/86337233/212689075-4fd55f45-443e-48d4-a0bf-bf02ebdb91a3.png">

<br/>
<br/>

1. `클라이언트 객체(Client object)`는 원격 서비스 객체의 메소드 호출을 하는 것처럼 행동하지만,  
   **실제로는 로컬 힙에 들어있는 클라이언트 보조 객체의 메소드를 호출하고 있는 것이다.**


2. `클라이언트 보조 객체(Client helper, 즉 프록시 객체)`는 서버에 연락을 취하고,  
   메서드 호출에 관한 정보를 전달한 후에 서버로부터 리턴되는 정보를 기다린다.


3. `서비스 보조 객체(Service helper)`는 클라이언트 보조 객체로부터 요청을 받고, 이를 해석해서 서비스 객체에 있는 메서드를 호출한다.  
   *(이에 서비스 객체는 그 메서드 호출이 원격 클라이언트가 아닌, 로컬 객체로부터 들어온다고 생각함)*


4. `서비스 객체(Service object)`에는 실제로 작업을 처리하는 메서드가 들어있다.  
   호출된 메서드의 리턴값을 서비스 보조 객체에게 전달한다.


5. 서비스 보조 객체는 서비스 객체로부터 리턴값을 받아서 **Socket의 출력 스트림**으로 클라이언트 보조 객체에게 전송한다.


6. 클라이언트 보조 객체는 그 정보를 해석해서 클라이언트 객체에 리턴한다.

<br/>

### 자바 RMI

자바에서는 `RMI(Remote Method Invocation, 원격 메소드 호출)`를 통해 원격 JVM에 있는 객체를 찾아서 그 객체의 메소드를 호출할 수 있다.

RMI가 클라이언트와 서비스 보조 객체를 만들어 준다.

- 클라이언트 보조 객체 = `스텁(stub)`
- 서비스 보조 객체 = `스켈레톤(skeleton)`

<br/>

## 원격 서비스 만들기

1. 원격 인터페이스 만들기
2. 서비스 구현 클래스 만들기
3. RMI 레지스트리(rmiregistry) 실행하기

   (클라이언트는 rmiregistry를 통해서 `스텁(프록시)`을 받아감)

4. 원격 서비스 실행하기

<br/>

<details>
<summary>전체 코드</summary>

#### 원격 인터페이스

```java
import java.rmi.*;

public interface MyRemote extends Remote {
    public String sayHello() throws RemoteException;
}
```

<br/>

#### 원격 서비스를 구현한 클래스

```java
import java.rmi.*;
import java.rmi.server.*;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

    private static final long serialVersionUID = 1L;

    public String sayHello() {
        return "Server says, 'Hey'";
    }

    public MyRemoteImpl() throws RemoteException {
    }

    public static void main(String[] args) {
        try {
            MyRemote service = new MyRemoteImpl();
            Naming.rebind("RemoteHello", service);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
```

<br/>

#### 클라이언트 코드

```java
import java.rmi.*;

public class MyRemoteClient {
    public static void main(String[] args) {
        new MyRemoteClient().go();
    }

    public void go() {
        try {
            MyRemote service = (MyRemote) Naming.lookup("rmi://127.0.0.1/RemoteHello");
            String s = service.sayHello();
            System.out.println(s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
```

</details>

<br/>

### 1단계 :  원격 인터페이스 만들기

1. `java.rmi.Remote` 확장

    ```java
    public interface MyRemote extends Remote {
    ```

<br/>

2. 모든 메서드를 `RemoteException`을 던지도록 선언
    - 스텁이 입출력 작업을 처리할 때 예외 사항들이 발생할 수 있기 때문에 미리 대비해야 한다.

    ```java
    import java.rmi.*;
    
    public interface MyRemote extends Remote {
            public String sayHello() throws RemoteException();
    }
    ```

<br/>

3. 원격 메서드의 인자와 리턴값은 반드시 `원시 형식(primitive)` 또는 `Serializable 형식`으로 선언
    - 원격 메소드의 인자와 리턴값은 모두 네트워크로 전달되어야 하며, 직렬화로 포장되기에 **모두 직렬화가 가능해야 하기 때문이다.**
    - 따라서 직접 만든 형식을 전달한다면, 클래스를 만들 때 Serializable 인터페이스도 구현해야 한다.

<br/>

참고 : JVM에서 `transient` 키워드가 추가된 필드는 직렬화하지 않는다.

```java
transient GumballMachine gumballMachine;
```

<br/>

### 2단계 : 서비스 구현 클래스 만들기

1. 서비스 클래스에 **원격 인터페이스**를 구현

    ```java
    public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
    		public String sayHello() {
    				return "Server says, 'Hey'";
    		}
    		// 기타 코드
    }
    ```

<br/>

2. `UnicastRemoteObject` 확장
    - 객체에 **‘원격 객체’ 기능**을 추가하기 위한 과정이다.
    - java.rmi.server 패키지에 들어있는 UnicastRemoteObject를 확장해서, 슈퍼클래스에서 제공하는 기능으로 처리하는 것이다.

    ```java
    public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
            // UnicastRemoteObject는 Serializable을 구현하므로 serialVersionUID 필드가 필요함
            private static final long serialVersionUID = 1L;
    ```

<br/>

2. `RemoteException`을 선언하는 생성자 구현

    ```java
    public MyRemoteImpl() throws RemoteException { }
    ```

<br/>

3. 서비스를 RMI 레지스트리에 등록
    - 원격 서비스를 원격 클라이언트에서 쓸 수 있게 만드는 과정이다.
    - 서비스를 구현한 객체를 등록하면 RMI 시스템은 레지스트리에 스텁만 등록한다. (클라이언트는 스텁만 필요하기 때문)
    - 서비스 등록 : `java.rmi.Naming.rebind()`

    ```java
    try {
            MyRemote service = new MyRemoteImpl();
            Naming.rebind("RemoteHello", service); // 서비스 등록 시 이름 지정이 필요 → 이 이름으로 레지스트리를 검색
    } catch(Exception ex) { ... }
    ```

<br/>

### 3단계 : rmiregistry 실행하기

- 터미널을 통해 rmiregistry를 실행
- 클래스에 접근할 수 있는 디렉토리에서 실행해야 한다.

<br/>

### 4단계 : 원격 서비스를 실행하기

- 다른 터미널을 열고 서비스를 실행

<br/>
<br/>

## 작동 방식

<p align="center"><img width="900" alt="작동 방식" src="https://user-images.githubusercontent.com/86337233/212689076-b32920b1-8d56-40bc-a999-f1fc2329c703.png">

<br/>
<br/>

1. 클라이언트는 RMI 레지스트리를 `룩업(lookup)`한다.

    ```java
    MyRemote service = (MyRemote) Naming.lookup("rmi://127.0.0.1/RemoteHello"); // 서비스 등록 시 사용한 이름을 적어주어야 함
    // 프록시를 리턴, 찾을 수 없다면 예외를 던짐
    ```

<br/>

2. RMI 레지스트리에서 스텁 객체를 리턴한다.
    - 스텁 객체는 lookup() 메서드의 리턴값으로 전달된다.
    - RMI에서는 그 스텁을 자동으로 역직렬화한다.

<br/>

3. 클라이언트는 스텁의 메서드(sayHello())를 호출한다.

<br/>

<p align="center"><img width="900" alt="프록시 메서드 호출" src="https://user-images.githubusercontent.com/86337233/212689078-36cb9370-bfd3-4120-a6bc-e2294a8f1c90.png">

<br/>
<br/>

4. 스텁의 메서드가 호출되면 스텁은 그 호출을 원격 서비스로 전달하고,  
   스켈레톤은 그 요청을 받아서 서비스 객체에게 전달한다.

<br/>

<p align="center"><img width="900" alt="리턴" src="https://user-images.githubusercontent.com/86337233/212689066-a447aa6d-17da-4210-b91a-46c1d62951a4.png">

<br/>
<br/>

5. 서비스 객체는 스켈레톤에게 상태를 리턴한다.


6. 스켈레톤은 리터값을 직렬화한 다음, 네트워크로 스텁에게 전달한다.


7. 스텁은 리턴값을 역직렬화해서 클라이언트에게 리턴한다.

<br/>

### 참고 : 데이터 직렬화와 역직렬화

**데이터 직렬화** : 메모리를 디스크에 저장하거나, 네트워크 통신에 사용하기 위한 형식으로 변환하는 것

**데이터 역직렬화** : 디스크에 저장한 데이터를 읽거나, 네트워크 통신으로 받은 데이터를 메모리에 쓸 수 있도록 변환하는 것

<br/>

**직렬화를 하는 이유**

직렬화를 하게 되면 각 주소 값이 가지는 데이터를 전부 끌어 모아서 값 형식 데이터로 변환해 준다.

직렬화가 된 데이터는 언어에 따라서 텍스트 또는 바이너리 등의 형태가 되는데, 이러한 형태가 되었을 때 저장하거나 통신할 때 파싱이 가능한 유의미한 데이터가 된다.

즉, 직렬화를 하는 이유는 사용하고 있는 데이터를 파일 저장 혹은 데이터 통신에서 파싱할 수 있는 유의미한 데이터를 만들기 위함이다.

[[참고 링크] 직렬화와 역직렬화](https://steady-coding.tistory.com/576)

<br/>
<br/>

# 가상 프록시

## 원격 프록시 vs. 가상 프록시

### 원격 프록시(Remote Proxy)

> 💡 **다른 JVM에 들어있는 객체**의 대리인에 해당하는 로컬 객체이다.

1. 프록시의 메서드 호출
2. 호출이 네트워크로 전달되어 원격 객체의 메서드가 호출된다.
3. 그 결과는 프록시를 거쳐서 클라이언트에게 전달된다.

<br/>

### 가상 프록시(Virtual Proxy)

> 💡 **생성하는 데 많은 비용이 드는 객체**를 대신한다.

- 진짜 객체(RealSubject)가 필요한 상황이 오기 전까지 객체의 생성을 미루는 기능을 제공한다.

- 객체 생성 전이나 객체 생성 도중에 객체를 숨기기 위하여 그를 대신하기도 한다.
- RealSubject가 생성되어 있는 상태에서는 RealSubject에 바로 요청을 전달한다.

<br/>

## 가상 프록시 구현 예시

이미지 뷰어

1. 주어진 URL로부터 이미지를 로딩
2. **이미지를 불러오는 동안** 화면에 메시지(”Loading album cover, please wait...”)를 보여주기

   *→ 가상 프록시가 해야할 일*

3. 이미지 로딩이 끝나면 화면에 이미지 띄우기

<br/>

여기서는 프록시는 `생성하는 데 많은 비용이 드는 객체를 숨기는 용도`로 쓰인다.  
(데이터를 네트워크로 가져와야 하기 때문에 시간이 많이 소요됨)

<br/>

아래 코드에서 `paintIcon` 함수를 주의 깊게 보자.

```java
class ImageProxy implements Icon {

    volatile ImageIcon imageIcon;
    final URL imageURL;
    Thread retrievalThread;
    boolean retrieving = false;

    public ImageProxy(URL url) {
        imageURL = url; // 이미지 로딩이 끝나면 이 URL에 있는 이미지를 화면에 표시
    }

    // 중략

    synchronized void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    // 이미지 출력
    public void paintIcon(final Component c, Graphics g, int x, int y) {
        // (1) 이미지가 완전히 생성되었을 경우
        if (imageIcon != null) {
            imageIcon.paintIcon(c, g, x, y);
        }
        // (2) 이미지가 아직 완전히 생성되지 않았을 경우
        else {
            g.drawString("Loading album cover, please wait...", x + 300, y + 190);

            // 이미지를 가져오고 있는 중이 아니라면
            if (!retrieving) {
                retrieving = true;
                retrievalThread = new Thread(() -> {
                    try {
                        setImageIcon(new ImageIcon(imageURL, "Album Cover"));
                        c.repaint(); // 화면 갱신
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                retrievalThread.start();
            }
        }
    }

}
```

<br/>

<p align="center"><img width="700" alt="가상 프록시" src="https://user-images.githubusercontent.com/86337233/212689081-3c2fc3fd-2f44-4182-be79-547568408c1e.png">

<br/>
<br/>

### 데코레이터 패턴 vs. 가상 프록시

- `데코레이터 패턴` : 클래스에 새로운 행동을 추가하는 용도로 사용
- `프록시` : 어떤 클래스로의 접근을 제어하는 용도로 사용

<br/>

위 예시에서 이미지를 불러오는 동안 로딩 메시지를 표시하도록 “행동을 추가”한 것으로 볼 수 있지만,  
`ImageProxy`는 **클라이언트와** `ImageIcon`**을 분리하여** `ImageIcon`로의 접근을 제어하고 있는 것이다.

만약 클라이언트와 `ImageIcon`이 결합되어 있었다면  
이미지가 완전히 로딩되기 전(`ImageIcon` 객체 생성이 완료되기 전)까지는 인터페이스를 화면에 표시할 수 없다.

<br/>

이처럼 클라이언트에서 진짜 객체가 아닌 프록시를 사용하도록 만드는 방법들 중 `팩토리`를 사용하는 것이 가장 흔히 쓰이는 기법이다.

팩토리에서 **진짜 객체의 인스턴스를 생성해서 리턴**하면 된다.

<br/>
<br/>

# 보호 프록시

> 💡 **접근 권한을 바탕으로** 객체로의 접근을 제어하는 프록시

<br/>

## 보호 프록시 구현 예시

데이팅 서비스를 구현해본다고 하자.

이 서비스에서의 요구사항은 다음과 같다.

1. 고객이 자기 정보를 직접 수정할 수 있도록 하면서, **다른 고객은 그 정보를 수정할 수 없도록** 해야 한다.
2. 선호도는 반대로, 다른 고객만 점수를 매길 수 있고 **자신은 점수를 매길 수 없도록** 해야 한다.

<br/>

이들을 해결하기 위해서는 2개의 프록시가 필요하다.

- 본인의 `Person` 객체에 접근하는 프록시
- 다른 사람들의 `Person` 객체에 접근하는 프록시

<br/>

`Person` 객체는 다음과 같다.

```java
public interface Person {

    String getName();

    String getGender();

    String getInterests();

    int getGeekRating();

    void setName(String name);

    void setGender(String gender);

    void setInterests(String interests);

    void setGeekRating(int rating);

}
```

<br/>

위에서 살펴보았던 동적 프록시 기술을 사용하여 동적 프록시를 구현해보자.

<br/>

<p align="center"><img width="600" alt="동적 프록시" src="https://user-images.githubusercontent.com/86337233/212689085-cd285023-fb6d-4650-88f7-c56207a5bd91.png">

<br/>
<br/>

1. 2개의 `InvocationHandler` 생성
2. 동적 프록시 생성 코드 만들기
3. 적절한 프록시로 `Person` 객체 감싸기
    - 고객 자신의 객체 → owner
    - 다른 고객의 객체 → non-owner

   <p align="center"><img width="430" alt="Person 객체 - 프록시" src="https://user-images.githubusercontent.com/86337233/212689087-1575a91e-0325-47e8-83b3-ac9f59633e77.png">

<br/>

### 1단계 : InvocationHandler 생성

프록시의 메서드가 호출되면 프록시는 그 호출을 `InvocationHandler`(호출 핸들러)에게 넘기는데,  
여기에는 메서드가 `invoke()` 하나밖에 없다.

즉, 프록시의 어떤 메서드가 호출되든 무조건 `invoke()` 메서드가 호출되는 것이며,  
그 메서드 속에서 주어진 요청을 어떻게 처리할지를 결정한다.

```java
return method.invoke(person,args);
// method : invoke() 메서드 호출로 전달된 객체
// person : 진짜 객체에 메서드를 호출
// args : 처음에 받았던 인자들을 전달
```

<br/>

#### 본인용 프록시를 위한 호출 핸들러 : OwnerInvocationHandler

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OwnerInvocationHandler implements InvocationHandler {

    Person person;

    public OwnerInvocationHandler(Person person) {
        this.person = person;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException {
        try {
            if (method.getName().startsWith("get")) {
                return method.invoke(person, args);
            } else if (method.getName().equals("setGeekRating")) {
                throw new IllegalAccessException(); // 메서드 호출을 막음
            } else if (method.getName().startsWith("set")) {
                return method.invoke(person, args);
            }
            // 나머지 메서드는 모두 허용 → 주제에 있는 메서드를 호출
        }
        // 진짜 주제에서 예외를 던졌을 경우
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 다른 메서드가 호출되었을 경우
        return null;
    }

}
```

<br/>

#### 타인용 프록시를 위한 호출 핸들러 : NonOwnerInvocationHandler

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NonOwnerInvocationHandler implements InvocationHandler {

    Person person;

    public NonOwnerInvocationHandler(Person person) {
        this.person = person;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException {
        try {
            if (method.getName().startsWith("get")) {
                return method.invoke(person, args);
            } else if (method.getName().equals("setGeekRating")) {
                return method.invoke(person, args);
            } else if (method.getName().startsWith("set")) {
                throw new IllegalAccessException(); // 메서드 호출을 막음
            }
            // 나머지 메서드는 모두 허용 → 주제에 있는 메서드를 호출
        }
        // 진짜 주제에서 예외를 던졌을 경우
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 다른 메서드가 호출되었을 경우
        return null;
    }

}
```

<br/>

### 2단계 : 동적 프록시 생성 코드 만들기

```java
/**
 * TODO: 동적 프록시 생성 코드
 *
 * @param person Person 객체(진짜 주제)
 * @return 프록시
 * (프록시의 인터페이스 == 주제의 인터페이스)
 */
    Person getOwnerProxy(Person person) {
            return (Person) Proxy.newProxyInstance(
            person.getClass().getClassLoader(), // person의 클래스 로더
            person.getClass().getInterfaces(), // 프록시에서 구현해야 하는 인터페이스
            new OwnerInvocationHandler(person)); // 호출 핸들러
    }

    Person getNonOwnerProxy(Person person) {
            return (Person) Proxy.newProxyInstance(
            person.getClass().getClassLoader(),
            person.getClass().getInterfaces(),
            new NonOwnerInvocationHandler(person));
    }
```

<br/>

> `InvocationHandler` 자체는 프록시가 아니라, **메서드 호출을 처리하는 클래스일 뿐**이다.  
> 실제 프록시는 `Proxy.newProxyInstance()` 정적 메서드에 의해서 실행 중에 동적으로 생성된다.

<br/>

`newProxyInstance()`를 호출할 때의 형식에는 몇가지 제한이 존재한다.

- 인터페이스의 배열을 인자로 전달해야 한다.
- `public`으로 지정되지 않은 인터페이스는 같은 패키지에 들어있는 인터페이스만 인자로 전달할 수 있다.

더 자세한 사항들은 [해당 링크](https://docs.oracle.com/javase/7/docs/api/java/lang/reflect/Proxy.html)를 참고하길 바란다.

<br/>

### 3단계 : 적절한 프록시로 Person 객체 감싸기

<details>
<summary>전체 코드</summary>

```java
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class MatchMakingTestDrive {

    HashMap<String, Person> datingDB = new HashMap<>();

    public static void main(String[] args) {
        MatchMakingTestDrive test = new MatchMakingTestDrive();
        test.drive();
    }

    public MatchMakingTestDrive() {
        initializeDatabase();
    }

    public void drive() {
        // 1-1. 인물 정보를 DB로부터 가져옴
        Person joe = getPersonFromDatabase("Joe Javabean");

        // 1-2. 본인용 프록시 생성
        Person ownerProxy = getOwnerProxy(joe);
        System.out.println("Name is " + ownerProxy.getName());
        ownerProxy.setInterests("bowling, Go");
        System.out.println("Interests set from owner proxy");

        // 1-3. 선호도 설정 시도 (본인용 프록시에서는 불가)
        try {
            ownerProxy.setGeekRating(10);
        } catch (Exception e) {
            System.out.println("Can't set rating from owner proxy");
        }
        System.out.println("Rating is " + ownerProxy.getGeekRating());

        // 2-1. 타인용 프록시 생성
        Person nonOwnerProxy = getNonOwnerProxy(joe);
        System.out.println("Name is " + nonOwnerProxy.getName());

        // 2-2. 관심사 변경 시도 (타인용 프록시에서는 불가)
        try {
            nonOwnerProxy.setInterests("bowling, Go");
        } catch (Exception e) {
            System.out.println("Can't set interests from non owner proxy");
        }

        // 2-3. 선호도 설정
        nonOwnerProxy.setGeekRating(3);
        System.out.println("Rating set from non owner proxy");
        System.out.println("Rating is " + nonOwnerProxy.getGeekRating());
    }


    /**
     * TODO: 동적 프록시 생성 코드
     *
     * @param person Person 객체(진짜 주제)
     * @return 프록시
     * (프록시의 인터페이스 == 주제의 인터페이스)
     */
    Person getOwnerProxy(Person person) {
        return (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(), // person의 클래스 로더
                person.getClass().getInterfaces(), // 프록시에서 구현해야 하는 인터페이스
                new OwnerInvocationHandler(person)); // 호출 핸들러
    }

    Person getNonOwnerProxy(Person person) {
        return (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new NonOwnerInvocationHandler(person));
    }

    Person getPersonFromDatabase(String name) {
        return (Person) datingDB.get(name);
    }

    void initializeDatabase() {
        Person joe = new PersonImpl();
        joe.setName("Joe Javabean");
        joe.setInterests("cars, computers, music");
        joe.setGeekRating(7);
        datingDB.put(joe.getName(), joe);

        Person kelly = new PersonImpl();
        kelly.setName("Kelly Klosure");
        kelly.setInterests("ebay, movies, music");
        kelly.setGeekRating(6);
        datingDB.put(kelly.getName(), kelly);
    }

}
```
</details>

<br/>

#### 본인용 프록시 생성 후 테스트

```java
// 1-1. 인물 정보를 DB로부터 가져옴
Person joe=getPersonFromDatabase("Joe Javabean");

// 1-2. 본인용 프록시 생성
Person ownerProxy = getOwnerProxy(joe);
System.out.println("Name is " + ownerProxy.getName());
ownerProxy.setInterests("bowling, Go");
System.out.println("Interests set from owner proxy");

// 1-3. 선호도 설정 시도 (본인용 프록시에서는 불가)
try {
    ownerProxy.setGeekRating(10);
} catch (Exception e) {
    System.out.println("Can't set rating from owner proxy");
}
System.out.println("Rating is " + ownerProxy.getGeekRating());
```

<br/>

출력 결과

```
Name is Joe Javabean
Interests set from owner proxy
Can't set rating from owner proxy
Rating is 7
```

<br/>

#### 타인용 프록시 생성 후 테스트

```java
// 2-1. 타인용 프록시 생성
Person nonOwnerProxy = getNonOwnerProxy(joe);
System.out.println("Name is " + nonOwnerProxy.getName());

// 2-2. 관심사 변경 시도 (타인용 프록시에서는 불가)
try {
    nonOwnerProxy.setInterests("bowling, Go");
} catch (Exception e) {
    System.out.println("Can't set interests from non owner proxy");
}

// 2-3. 선호도 설정
nonOwnerProxy.setGeekRating(3);
System.out.println("Rating set from non owner proxy");
System.out.println("Rating is " + nonOwnerProxy.getGeekRating());
```

<br/>

출력 결과

```
Name is Joe Javabean
Can't set interests from non owner proxy
Rating set from non owner proxy
Rating is 5
```
