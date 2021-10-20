package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.*;

public class Principal {
    public static void main(String args[]) {
        
        SubastaVista vista;
        SubastaControlador controlador;
        SubastaModelo modelo;

        String host = null;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Subasta stub = (Subasta) registry.lookup("Subasta");

            vista = new SubastaVista();

            controlador = new SubastaControlador(vista, stub);

            vista.asignarActionListener(controlador);
            vista.asignarListSelectionListener(controlador);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }
}
