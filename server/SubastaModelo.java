package server;

import java.util.*;

import client.ClientInterface;
import client.SubastaVista;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SubastaModelo implements Subasta {

    Hashtable usuarios;
    Hashtable productos;
    Hashtable ofertas;

    private List<ClientInterface> clients = new LinkedList<>();

    public SubastaModelo() {
        usuarios = new Hashtable();
        productos = new Hashtable();
        ofertas = new Hashtable();
    }

    public synchronized boolean registraUsuario(String nombre) {
        if (!usuarios.containsKey(nombre)) {
            System.out.println("Agregando un nuevo usuario: " + nombre);
            usuarios.put(nombre, nombre);
            return true;
        } else
            return false;
    }

    public synchronized boolean agregaProductoALaVenta(String vendedor, String producto, float precioInicial) {
        if (!productos.containsKey(producto)) {
            System.out.println("Agregando un nuevo producto: " + producto);
            productos.put(producto, new InformacionProducto(vendedor, producto, precioInicial));
            return true;
        } else
            return false;
    }

    public synchronized boolean agregaOferta(String comprador, String producto, float monto) {
        if (productos.containsKey(producto)) {
            InformacionProducto infoProd;
            infoProd = (InformacionProducto) productos.get(producto);
            if (infoProd.actualizaPrecio(monto)) {
                ofertas.put(producto + comprador, new InformacionOferta(comprador, producto, monto));
                callback();
                return true;
            } else
                return false;
        } else
            return false;
    }

    public synchronized Vector obtieneCatalogo() {
        Vector resultado;
        resultado = new Vector(productos.values());
        return resultado;
    }

    int index = 0;

    public void nuevoCliente(ClientInterface client) {
        try {
            clients.add(client);
            index++;
        } catch (Exception e) {
            clients.remove(index);
        }
    }

    public void callback() {
        try {
            for (ClientInterface client_interface : clients) {
                client_interface.refrescarProductos();
                // client_interface.send("Nuevo precio disponible");
            }
        } catch (Exception e) {
            System.err.println("Error in callback" + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            SubastaModelo obj = new SubastaModelo();
            Subasta server_stub = (Subasta) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Subasta", server_stub);

        } catch (Exception e) {
            System.err.println("Error del server: " + e.toString());
            e.printStackTrace();
        }
    }

}
