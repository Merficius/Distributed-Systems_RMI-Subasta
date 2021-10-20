package client;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;

import server.InformacionProducto;
import server.Subasta;

import javax.swing.event.ListSelectionEvent;

public class SubastaControlador implements ActionListener, ListSelectionListener, Serializable {

    SubastaVista vista;
    Subasta modelo;
    Hashtable listaConPrecios;

    public SubastaControlador(SubastaVista v, Subasta m) {

        vista = v;
        modelo = m;
    }

    public void obtenerLista() {
        Vector lista;
        try {
            vista.reinicializaListaProductos();
            lista = modelo.obtieneCatalogo();
            Enumeration it;
            InformacionProducto info;
            listaConPrecios = new Hashtable();
            it = lista.elements();
            while (it.hasMoreElements()) {
                info = (InformacionProducto) it.nextElement();
                listaConPrecios.put(info.producto, String.valueOf(info.precioActual));
                vista.agregaProducto(info.getNombreProducto());
            }
        } catch (RemoteException e) {
            System.err.println("Controlador exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent evento) {

        String usuario;
        String producto;
        float monto;

        System.out.println("<<" + evento.getActionCommand() + ">>");

        if (evento.getActionCommand().equals("Salir")) {
            System.exit(1);
        } else if (evento.getActionCommand().equals("Conectar")) {
            usuario = vista.getUsuario();
            System.out.println("Registrarse como usuario: " + usuario);
            try {
                modelo.registraUsuario(usuario);
            } catch (RemoteException e) {
                System.err.println("Controlador exception a: " + e.toString());
                e.printStackTrace();
            }
        } else if (evento.getActionCommand().equals("Poner a la venta")) {
            usuario = vista.getUsuario();
            producto = vista.getProducto();
            monto = vista.getPrecioInicial();
            System.out.println("Haciendo oferta del producto: " + producto);
            try {
                modelo.agregaProductoALaVenta(usuario, producto, monto);
                modelo.callback();
            } catch (RemoteException e) {
                System.err.println("Controlador exception: " + e.toString());
                e.printStackTrace();
            }

        } else if (evento.getActionCommand().equals("Obtener lista")) {
            obtenerLista();

        } else if (evento.getActionCommand().equals("Ofrecer")) {
            producto = vista.getProductoSeleccionado();
            monto = vista.getMontoOfrecido();
            usuario = vista.getUsuario();
            try {
                modelo.agregaOferta(usuario, producto, monto);
                modelo.callback();
            } catch (RemoteException e) {
                System.err.println("Controlador exception: " + e.toString());
                e.printStackTrace();
            }
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            JList lista = (JList) e.getSource();
            String item = (String) lista.getSelectedValue();
            if (item != null) {
                System.out.println(item);
                String precio = (String) listaConPrecios.get(item);
                vista.desplegarPrecio(precio);
            }
        }
    }
}
