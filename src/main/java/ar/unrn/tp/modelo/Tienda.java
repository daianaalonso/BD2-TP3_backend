package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.List;

public class Tienda {
    private List<Venta> ventas;

    public Tienda() {
        ventas = new ArrayList<>();
    }

    public void agregarVenta(Venta venta) {
        ventas.add(venta);
    }

    public boolean existeVenta(Venta v) {
        return ventas.stream().anyMatch(venta -> venta.equals(v));
    }
}