package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.*;

public class Principal implements ClientInterface {

    
    public static SubastaVista vista;
    public static SubastaControlador controlador;
    public static SubastaModelo modelo;


    public void send(String message) {
        System.out.println(message);
    }

    public void refrescarProductos(){
        try {
            controlador.obtenerLista();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        String host = null;
        try {
            Principal client = new Principal();
            ClientInterface client_stub = (ClientInterface) UnicastRemoteObject.exportObject(client, 0);

            vista = new SubastaVista();

            Registry registry = LocateRegistry.getRegistry(host);
            Subasta server_stub = (Subasta) registry.lookup("Subasta");

            controlador = new SubastaControlador(vista, server_stub);

            vista.asignarActionListener(controlador);
            vista.asignarListSelectionListener(controlador);

            server_stub.nuevoCliente(client_stub);
            
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }
}
