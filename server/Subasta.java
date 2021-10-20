package server;

import java.util.Vector;

import javax.print.DocFlavor.STRING;

import client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Subasta extends Remote {
    boolean registraUsuario(String nombre) throws RemoteException;

    boolean agregaProductoALaVenta(String vendedor, String producto, float precioInicial) throws RemoteException;

    boolean agregaOferta(String comprador, String producto, float monto) throws RemoteException;

    Vector obtieneCatalogo() throws RemoteException;

    void callback() throws RemoteException;

    void eliminarUsuario(String nombre) throws RemoteException;

    void nuevoCliente(ClientInterface client) throws RemoteException;
}
