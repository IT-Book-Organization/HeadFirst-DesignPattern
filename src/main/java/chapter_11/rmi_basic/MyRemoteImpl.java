package main.java.chapter_11.rmi_basic;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

    //serializable uid는 생성할 필요가 굳이 없다 UnicastRemoteObject가 이미 갖고있다.
    protected MyRemoteImpl() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello";
    }

    public static void main(String[] args) {
        try {
            MyRemoteImpl service = new MyRemoteImpl();

            Naming.rebind("RemoteHello",service);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
