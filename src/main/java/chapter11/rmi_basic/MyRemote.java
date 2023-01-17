package main.java.chapter11.rmi_basic;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Remote 객체를 상속함으로써 rmi 저장소에 등록할 수 있음
public interface MyRemote extends Remote {
    // return 값은 직렬화할 수 있는 객체이거나 RemoteException을 던져야함.
    public String sayHello() throws RemoteException;
}
