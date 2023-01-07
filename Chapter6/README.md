# 커맨드 패턴(Command Pattern)

> 💡 커맨드 패턴을 사용하면 요청 내역을 객체로 **캡슐화**해서 객체를 서로 다른 요청 내역에 따라 **매개변수화**할 수 있다.

즉, 요청하는 객체와 요청을 수행하는 객체를 분리할 수 있다.

<br/>

**계산 과정의 각 부분을 결정화**할 수 있기에 계산하는 코드를 호출한 객체는 그 일이 어떤 식으로 처리되는지 전혀 신경쓸 필요가 없다.

또한, 이를 통해 요청을 큐에 저장하거나, 로그로 기록하거나, 작업을 취소하는 기능들도 구현할 수 있다.

<br/>

## Command 객체

> 💡 Command 객체는 일련의 행동을 특정 Receiver와 연결함으로써 해당 Receiver와 그에 대한 요청을 **캡슐화**한다.

<br/>

이를 위해서는 **행동과 Receiver를 한 객체에 넣고**, `execute()`라는 메서드 하나만 외부에 공개해야 한다.

`execute()` 메서드 호출에 따라 Receiver는 일련의 작업을 처리한다.

```java
public interface Command {
    public void execute(); // 행동을 캡슐화하며, Receiver에 있는 특정 행동을 호출함
}
```

<br/>

e.g.,

Receiver `Light`

```java
public class Light {

    public void on() {
        System.out.println("Light is on");
    }

    public void off() {
        System.out.println("Light is off");
    }

}

```

<br/>

Command `LightOnCommand`

```java
public class LightOnCommand implements Command {

    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }

}
```

<br/>

<p align="center"><img width="480" alt="Command" src="https://user-images.githubusercontent.com/86337233/210893241-30de6a35-f835-45fa-b317-f187a29a1cb6.png">

<br/>
<br/>

*왜 캡슐화인가?*

밖에서 볼 때는 어떤 객체가 Receiver의 역할을 하는지, 그 Receiver가 어떤 일을 하는지 알 수 없기 때문이다.

그저 `execute()` 메서드를 호출하면 해당 요청이 처리된다는 사실만 알 수 있을 뿐이다.

<br/>
<br/>

## 과정

### 1. Invoker Loading

<p align="center"><img width="780" alt="Invoker Loading" src="https://user-images.githubusercontent.com/86337233/210893236-3c68b332-1d5a-484a-96f5-8c1a545ac849.png">

<br/>
<br/>

**① Client에서 Command 객체를 생성한다.**  
Command 객체에는 행동과 Receiver의 정보가 같이 들어있다.

**② Client는 Invoker 객체의 `setCommand()` 메서드를 호출해서, Invoker에 Command 객체를 저장한다.**   
그 Command 객체는 나중에 쓰이기 전까지 Invoker 객체에 보관된다.

**③ 나중에 Client에서 Invoker에게 그 명령을 실행하라고 요청한다.**

일단 어떤 명령을 Invoker에 로딩한 다음,  
(1) 한 번만 작업을 처리하고 Command 객체를 지우도록 할 수 있고,  
(2) 저장해둔 명령을 여러 번 수행하게 할 수도 있다.

<br/>

### 2. execute()

Invoker에서 Command 객체의 `execute()` 메서드를 호출하면, Receiver에 있는 행동 메서드가 호출된다.

<br/>

<p align="center"><img width="570" alt="execute" src="https://user-images.githubusercontent.com/86337233/210893243-e9800646-3686-4acf-807b-80cac07b679c.png">

<br/>
<br/>

## 클래스 다이어그램

<br/>

<p align="center"><img width="830" alt="클래스 다이어그램" src="https://user-images.githubusercontent.com/86337233/210893244-dee4f3c4-6ba8-44d8-98be-9a40ab0c5c14.png">

<br/>
<br/>

### Client

ConcreteCommand를 생성하고 Reciever를 설정한다.

<br/>

### Invoker

여기에는 명령이 들어있다.

`executer()` 메서드를 호출함으로써 **Command 객체에 특정 작업을 수행해달라는 요구**를 하게 된다.

<br/>

### Receiver

요구 사항을 수행할 때 어떤 일을 처리해야 하는지를 알고 있는 객체이다.

<br/>

### Command (Interface)

모든 Command 객체에서 구현해야 하는 인터페이스이다.

이를 통해 **Invoker를 매개변수화**할 수 있으며, 실행 중 동적으로도 매개변수화를 설정할 수도 있다.

모든 명령은 `execute()` 메서드 호출로 수행되며, 이 메서드는 Receiver에 특정 작업을 처리하라는 지시를 전달한다.

<br/>

### ConcreteCommand

Command 인터페이스의 구현체로, 특정 행동과 Receiver를 연결해준다.

즉, Invoker에서 `execute()` 호출로 요청을 하면, 이 객체에서 Receiver에 있는 메서드를 호출해서 요청된 작업을 처리한다.

<br/>
<br/>

## 명령으로 객체를 매개변수화한다

`‘객체를 서로 다른 요청 내역에 따라 매개변수화한다’`는 것은 무슨 의미일까?

<br/>

아래의 예제를 보자.

각각의 객체는 어떤 역할을 하는지 주석으로 작성해놓았다.

```java
public class RemoteControlTest {

    public static void main(String[] args) {
        // Invoker
        SimpleRemoteControl remote = new SimpleRemoteControl();

        // Receiver
        Light light = new Light();
        GarageDoor garageDoor = new GarageDoor();

        // Command (Command에 Receiver를 전달)
        LightOnCommand lightOn = new LightOnCommand(light);
        GarageDoorOpenCommand garageOpen = new GarageDoorOpenCommand(garageDoor);

        /*
         * Invoker에 작업을 요청
         * 1. Invoker에 Command 객체를 전달
         * 2. 행동 메소드 execute() 호출
         */
        // light
        remote.setCommand(lightOn); // 조명 켜기
        remote.buttonWasPressed();
        // garage
        remote.setCommand(garageOpen); // 차고 문 열기
        remote.buttonWasPressed();
    }

}
```

<br/>

실행 결과

```
Light is on
Garage Door is Open
```

<br/>

`main` 함수에서 **Invoker에 작업을 요청하는 부분**에 주목해보자.

이처럼 같은 `SimpleRemoteControl` 객체를 통해서 ‘조명 켜기’ 명령을 로딩했다가, 나중에 ‘차고 문 열기’ 명령을 로딩할 수가 있다.

<br/>

즉, Command 인터페이스만 구현되어 있다면 그것의 구현체(Command 객체)에서 **실제로 어떤 일을 하는지 신경 쓸 필요가 없으며**,  
명령으로 객체를 `매개변수화`할 수 있는 것이다.

<br/>
<br/>

## NoCommand 객체

NoCommand 객체는 일종의 `null 객체`이다.

이는 딱히 return 할 객체가 없는데 Client가 null을 처리하지 않도록 하고 싶을 때 활용하면 좋다.

<br/>

예시를 보면서 NoCommand 객체를 사용하는 방법에 대해 알아보자.

### 인터페이스 Command

```java
public interface Command {
    public void execute();
}
```

<br/>

### Command의 구현체, NoCommand

`execute()` 메서드에는 어떠한 로직도 구현되지 않으며, 아무것도 반환하지 않는다.

```java
public class NoCommand implements Command {

    @Override
    public void execute() {
    }

}
```

<br/>

### Invoker 객체, RemoteControl

`Command[]`를 보면 알 수 있듯, Invoker가 저장할 수 있는 Command의 개수는 **총 일곱 쌍**이다.  
(onCommand, offCommand → 한 쌍)

- 모든 Command들은 NoCommand 객체로 초기화된다.
- `setCommand()` 메서드가 불려졌다면, 각 slot은 해당 Command 객체로 변경된다.

<br/>

즉, 어떤 slot에 대한 `setCommand()` 메서드가 불려지지 않았다면 계속 NoCommand 객체를 가지고 있게 되는 것이다.

```java
public class RemoteControl {

    Command[] onCommands;
    Command[] offCommands;

    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    // 중략

    // 슬롯 별 명령 출력
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n------ Remote Control -------\n");
        for (int i = 0; i < onCommands.length; i++) {
            stringBuilder.append("[slot " + i + "] " + onCommands[i].getClass().getName()
                    + "\t\t\t" + offCommands[i].getClass().getName() + "\n");
        }
        return stringBuilder.toString();
    }
}
```

<br/>

`toString()`을 통해 onCommands와 offCommands의 각 슬롯마다 어떠한 Command가 들어있는지를 출력하도록 하였다.

[ 출력 형식 ] : `[slot <slot number>] <onCommands의 Command 경로> \t\t\t <offCommands에서의 Command 경로>`

<br/>

### main

```java
public class RemoteLoader {

    public static void main(String[] args) {
        // Invoker
        RemoteControl remoteControl = new RemoteControl();

        // Receiver
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        CeilingFan ceilingFan = new CeilingFan("Living Room");
        GarageDoor garageDoor = new GarageDoor("Garage");
        Stereo stereo = new Stereo("Living Room");

        // Command
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);

        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

        CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);

        // set command(버튼 할당) only to slot 0 ~ 2
        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
        remoteControl.setCommand(2, ceilingFanOn, ceilingFanOff);

        System.out.println(remoteControl);

        // Invoker에 작업을 요청하는 코드는 생략
    }

}
```

<br/>

실행 결과

```
------ Remote Control -------
[slot 0] Chapter_6.remote.commands.LightOnCommand         Chapter_6.remote.commands.LightOffCommand
[slot 1] Chapter_6.remote.commands.LightOnCommand         Chapter_6.remote.commands.LightOffCommand
[slot 2] Chapter_6.remote.commands.CeilingFanOnCommand    Chapter_6.remote.commands.CeilingFanOffCommand
[slot 3] Chapter_6.remote.commands.NoCommand              Chapter_6.remote.commands.NoCommand
[slot 4] Chapter_6.remote.commands.NoCommand              Chapter_6.remote.commands.NoCommand
[slot 5] Chapter_6.remote.commands.NoCommand              Chapter_6.remote.commands.NoCommand
[slot 6] Chapter_6.remote.commands.NoCommand              Chapter_6.remote.commands.NoCommand
```

<br/>

**slot 0, 1, 2에만** `setCommand()`**을 호출했으므로** slot 3 이후부터는 NoCommand라고 출력되는 것을 확인할 수 있다.

또한, NoCommand로 지정된 slot에 작업을 요청해도 NoCommand의 `execute()`에는 아무것도 구현되어 있지 않기 때문에 **오류가 발생하지 않는다.**

<br/>

따라서 null 객체는 여러 디자인 패턴에서 유용하게 쓰이며, null 객체를 일종의 디자인 패턴으로 분류하기도 한다.

<br/>

## 기능 추가

### 1. 이전 작업 취소하기

속도의 상태가 4가지(HIGH, MEDIUM, LOW, OFF) 중 하나인 선풍기를 예시로 들어보자.

우리는 작업 취소 기능을 호출하면 `이전 속도로 되돌리는 기능`을 구현하고 싶다.

```java
public class CeilingFan {

    public static final int HIGH = 3;
    public static final int MEDIUM = 2;
    public static final int LOW = 1;
    public static final int OFF = 0;

    String location;
    int speed; // 선풍기의 속도

    public CeilingFan(String location) {
        this.location = location;
        speed = OFF;
    }

    public int getSpeed() {
        return speed;
    }

    public void high() {
        // turns the ceiling fan on to high
        speed = HIGH;
        System.out.println(location + " ceiling fan is on high");
    }

    public void medium() {
        // turns the ceiling fan on to medium
        speed = MEDIUM;
        System.out.println(location + " ceiling fan is on medium");
    }

    public void low() {
        // turns the ceiling fan on to low
        speed = LOW;
        System.out.println(location + " ceiling fan is on low");
    }

    public void off() {
        // turns the ceiling fan off
        speed = OFF;
        System.out.println(location + " ceiling fan is off");
    }

}
```

<br/>

Command에서 작업 취소 기능을 지원하기 위해서는 아래를 따르면 된다.

<br/>

1️⃣ **인터페이스 Command**에 `undo()` 메서드 추가

이는 execute() 메서드에서 했던 작업과 정반대의 작업을 처리하면 된다.

```java
public interface Command {

    public void execute();

    public void undo();

}
```

<br/>

2️⃣ **Command 객체**에 `undo()를 구현`, 선풍기의 `이전 속도 상태를 저장하는 필드`를 추가

e.g.,

```java
public class CeilingFanHighCommand implements Command {

    CeilingFan ceilingFan;
    int prevSpeed; // 선풍기의 이전 속도 상태를 저장

    public CeilingFanHighCommand(CeilingFan ceilingFan) {
        this.ceilingFan = ceilingFan;
    }

    @Override
    public void execute() {
        prevSpeed = ceilingFan.getSpeed();
        ceilingFan.high();
    }

    @Override
    public void undo() {
        // 선풍기의 속도를 이전 속도 상태로 되돌림
        switch (prevSpeed) {
            case CeilingFan.HIGH:
                ceilingFan.high();
                break;

            case CeilingFan.MEDIUM:
                ceilingFan.medium();
                break;

            case CeilingFan.LOW:
                ceilingFan.low();
                break;

            case CeilingFan.OFF:
                ceilingFan.off();
                break;
        }
    }

}
```

<br/>

3️⃣ **Invoker**에 undoCommand 필드 추가, Comman 객체의 `undo()를 호출`하는 메서드를 추가

```java
public class RemoteControlWithUndo {

    Command[] onCommands;
    Command[] offCommands;

    // UNDO 버튼을 눌렀을 때를 대비해서 마지막으로 사용한 커맨드의 레퍼런스를 저장하는 변수
    Command undoCommand;

    public RemoteControlWithUndo() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }

    // 중략

    // 마지막으로 했던 작업을 취소
    public void undoButtonWasPushed() {
        undoCommand.undo(); // undoCommand에 저장된 Command 객체의 undo() 메서드를 호출
    }

    public String toString() {
        // 중략
        stringBuilder.append("[undo] " + undoCommand.getClass().getName() + "\n");
        return stringBuilder.toString();
    }

}
```

<br/>

---

<br/>

제대로 구현되었는지를 확인해보기 위해 main 함수를 다음과 같이 고치고 실행시켜보았다.

```java
public class RemoteLoader {

    public static void main(String[] args) {
        // Invoker
        RemoteControlWithUndo remoteControl = new RemoteControlWithUndo();

        // Receiver
        CeilingFan ceilingFan = new CeilingFan("Living Room");

        // Command
        CeilingFanMediumCommand ceilingFanMedium = new CeilingFanMediumCommand(ceilingFan);
        CeilingFanHighCommand ceilingFanHigh = new CeilingFanHighCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);

        // Command를 버튼에 할당
        remoteControl.setCommand(0, ceilingFanMedium, ceilingFanOff);
        remoteControl.setCommand(1, ceilingFanHigh, ceilingFanOff);

        // 테스트
        remoteControl.onButtonWasPushed(0); // → MEDIUM으로 설정됨 (1)
        remoteControl.offButtonWasPushed(0); // → 선풍기 끄기
        System.out.println(remoteControl);

        remoteControl.undoButtonWasPushed(); // 작업 취소 → MEDIUM으로 되돌아감 (2)

        remoteControl.onButtonWasPushed(1); // → HIGH로 설정됨
        System.out.println(remoteControl);

        remoteControl.undoButtonWasPushed(); // 작업 취소 → MEDIUM으로 되돌아감 (3)
    }

}
```

<br/>

실행 결과

```
------ Remote Control -------
[slot 0] Chapter_6.undo.commands.CeilingFanMediumCommand	Chapter_6.undo.commands.CeilingFanOffCommand
[slot 1] Chapter_6.undo.commands.CeilingFanHighCommand		Chapter_6.undo.commands.CeilingFanOffCommand
[slot 2] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[slot 3] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[slot 4] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[slot 5] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[slot 6] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[undo] Chapter_6.undo.commands.CeilingFanOffCommand

Living Room ceiling fan is on medium // (1)
Living Room ceiling fan is on high   // (2)

------ Remote Control -------
[slot 0] Chapter_6.undo.commands.CeilingFanMediumCommand	Chapter_6.undo.commands.CeilingFanOffCommand
[slot 1] Chapter_6.undo.commands.CeilingFanHighCommand		Chapter_6.undo.commands.CeilingFanOffCommand
[slot 2] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[slot 3] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[slot 4] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[slot 5] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[slot 6] Chapter_6.undo.commands.NoCommand			Chapter_6.undo.commands.NoCommand
[undo] Chapter_6.undo.commands.CeilingFanHighCommand

Living Room ceiling fan is on medium // (3)
```

<br/>

(1), (2), (3) 전부가 출력되는 것이 확인된다.

이렇게 `이전 작업 취소 기능`을 구현할 수가 있다.

<br/>

### 2. 매크로 커맨드 - 여러 동작을 한 번에 처리하기

매크로 커맨드는 **커맨드를 확장해서 여러 개의 커맨드를 한 번에 호출할 수 있게 해주는** 가장 간편한 방법이다.

예시로, 버튼 한 개만 누르면 (1) 조명이 어두워지면서, (2) 오디오가 켜지고, (3) 욕조에 물이 채워지는 것까지  
`한 번에 처리하는 기능`을 추가하고 싶다면 어떻게 해야 하는가?

<br/>

이는 Command 객체를 하나 더 만든 후, main 함수만 고쳐주면 된다.

<br/>

1️⃣ Command 객체, `MacroCommand`

- 매크로 커맨드의 대상이 되는 Command 객체들을 배열로 가지고 있다.
- `execute()` : Command 객체 배열에 저장된 순서대로 `각 객체의 execute() 메서드`를 호출
- `undo()` : Command 객체 배열에 저장된 순서의 **반대로** `각 객체의 undo() 메서드`를 호출

```java
public class MacroCommand implements Command {

    Command[] commands;

    public MacroCommand(Command[] commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    /**
     * NOTE: these commands have to be done backwards to ensure proper undo functionality
     */
    @Override
    public void undo() {
        for (int i = commands.length - 1; i >= 0; i--) {
            commands[i].undo();
        }
    }

}
```

<br/>

2️⃣ main 함수 수정

한꺼번에 처리하고 싶은 동작들을 배열을 통해 하나로 묶어준 후, MacroCommand 객체 선언 시 인자로 넘겨주면 된다.

이후에는 전과 똑같이 객체에 버튼을 할당해주고, 메서드를 실행해주면 된다.

```java
public class RemoteLoader {

    public static void main(String[] args) {
        // Invoker
        RemoteControl remoteControl = new RemoteControl();

        // Receiver
        Light light = new Light("Living Room");
        Stereo stereo = new Stereo("Living Room");
        Hottub hottub = new Hottub();

        // Command
        // ON Commands
        LightOnCommand lightOn = new LightOnCommand(light);
        StereoOnCommand stereoOn = new StereoOnCommand(stereo);
        HottubOnCommand hottubOn = new HottubOnCommand(hottub);
        // OFF Commands
        LightOffCommand lightOff = new LightOffCommand(light);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);
        HottubOffCommand hottubOff = new HottubOffCommand(hottub);

        // 커맨드용 배열 생성
        Command[] partyOn = {lightOn, stereoOn, hottubOn}; // ON 커맨드용 배열
        Command[] partyOff = {lightOff, stereoOff, hottubOff}; // OFF 커맨드용 배열

        // 각 배열을 전달하여 매크로 커맨드를 생성
        MacroCommand partyOnMacro = new MacroCommand(partyOn);
        MacroCommand partyOffMacro = new MacroCommand(partyOff);

        // 매크로 커맨드를 버튼에 할당
        remoteControl.setCommand(0, partyOnMacro, partyOffMacro);

        // 테스트
        System.out.println(remoteControl);

        System.out.println("\n--- Pushing Macro On---");
        remoteControl.onButtonWasPushed(0);

        System.out.println("\n--- Pushing Macro Off---");
        remoteControl.offButtonWasPushed(0);
    }

}
```

<br/>

실행 결과

```
------Remote Control-------
[slot 0]Chapter_6.party.commands.MacroCommand Chapter_6.party.commands.MacroCommand
[slot 1]Chapter_6.party.commands.NoCommand Chapter_6.party.commands.NoCommand
[slot 2]Chapter_6.party.commands.NoCommand Chapter_6.party.commands.NoCommand
[slot 3]Chapter_6.party.commands.NoCommand Chapter_6.party.commands.NoCommand
[slot 4]Chapter_6.party.commands.NoCommand Chapter_6.party.commands.NoCommand
[slot 5]Chapter_6.party.commands.NoCommand Chapter_6.party.commands.NoCommand
[slot 6]Chapter_6.party.commands.NoCommand Chapter_6.party.commands.NoCommand
[undo]Chapter_6.party.commands.NoCommand

---Pushing Macro On---
Light is on
Living Room stereo is on
Hottub is heating to a steaming 104 degrees
Hottub is bubbling!

---Pushing Macro Off---
Light is off
Living Room stereo is off
Hottub is cooling to 98 degrees
```

<br/>

### 3. 히스토리 기능 - 작업 취소 여러 번 하기

이전 작업 취소 기능을 위해서는 마지막으로 실행한 Command의 레퍼런스만 저장했었다.

히스토리 기능을 구현하기 위해서는 이전 작업 취소 기능의 확장으로, 실행한 Command 자체를 `스택`에 넣으면 된다.

1. Command 실행 시 stack에 `push`
2. Client가 작업 취소를 할 때마다 stack을 `pop` → 해당 객체의 `undo()` 메서드 호출

<br/>
<br/>

## 활용

### 1. 작업 큐와 작업 처리 스레드

Command를 통해 컴퓨테이션(computation)의 한 부분을 패키지로 묶어서 `일급 객체` 형태로 전달할 수 있다.

<br/>

`일급 객체(first-class object)`

- **다른 객체들에 일반적으로 적용 가능한 연산을 모두 지원하는 객체**를 말한다.
- 보통 함수에 인자로 넘기기, 수정하기, 변수에 대입하기와 같은 연산을 지원할 때 일급 객체라고 한다.

<br/>

이를 통해서 클라이언트 애플리케이션에서 커맨드 객체를 생성한 뒤 시간이 지나도 그 컴퓨테이션을 호출할 수 있게 되며, 다른 스레드에서 호출할 수도 있다.

→ 커맨드 패턴을 스케줄러, 스레드 풀, 작업 큐 등에 적용할 수 있다!

<br/>

e.g., 작업 큐

1. Command 인터페이스를 구현하는 Command 객체를 큐에 추가한다.
2. 컴퓨터이션을 고정된 개수의 스레드로 제한한다.
3. 각 스레드는 큐에서 Command 객체를 하나씩 가져온다.
4. 해당 Command 객체에서 `execute()` 메서드를 호출하고, 호출이 완료되면 현재의 Command 객체를 버리고 새로운 Command 객체를 가져온다.

<br/>

### 2. 트랜잭션 시스템 - store()와 load()

**애플리케이션에서 모든 행동을 기록해 두었다가 애플리케이션이 다운되었을 때 그 행동을 다시 호출해서 복구하는 기능**은  
커맨드 패턴에서 `store()`와 `load()` 메서드를 추가해서 구현할 수 있다.

1. 각 커맨드가 실행될 때마다 디스크에 그 내역을 `store()`
2. 시스템이 다운된 후에, 객체를 다시 `load()` → 순서대로 작업을 다시 처리

<br/>

<p align="center"><img width="700" alt="트랜잭션 시스템" src="https://user-images.githubusercontent.com/86337233/210893247-4a2b7e75-70f0-4074-b6eb-2f97ba67365b.png">
