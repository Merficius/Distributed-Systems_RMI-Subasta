package server;

import java.io.Serializable;

public class InformacionProducto implements Serializable {

    String vendedor;
    public String producto;
    float precioInicial;
    public float precioActual;

    public InformacionProducto(String v, String p, float pi) {

        vendedor = v;
        producto = p;
        precioInicial = pi;
        precioActual = pi;
    }

    public boolean actualizaPrecio(float monto) {

        if (monto > precioActual) {
            precioActual = monto;
            return true;
        } else
            return false;
    }

    public String getNombreProducto() {

        return producto;
    }

    public float getPrecioActual() {

        return precioActual;
    }
}
