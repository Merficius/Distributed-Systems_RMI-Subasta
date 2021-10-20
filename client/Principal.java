package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import server.*;

public class Principal {
    public static void main(String args[]) {

        SubastaVista vista;
        SubastaControlador controlador;
        SubastaModelo modelo;

        String host = null;
        try {
            vista = new SubastaVista();

            Registry registry = LocateRegistry.getRegistry(host);
            Subasta server_stub = (Subasta) registry.lookup("Subasta");

            controlador = new SubastaControlador(vista, server_stub);

            vista.asignarActionListener(controlador);
            vista.asignarListSelectionListener(controlador);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }
}
