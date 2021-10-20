package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote{
    void send(String message) throws RemoteException;
    void refrescarProductos() throws RemoteException;
}
