package server;

import java.util.Vector;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Subasta extends Remote {
    boolean registraUsuario(String nombre) throws RemoteException;

    boolean agregaProductoALaVenta(String vendedor, String producto, float precioInicial) throws RemoteException;

    boolean agregaOferta(String comprador, String producto, float monto) throws RemoteException;

    Vector obtieneCatalogo() throws RemoteException;
}
