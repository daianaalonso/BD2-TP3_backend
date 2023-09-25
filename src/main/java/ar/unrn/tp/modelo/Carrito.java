package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<Producto> productos;

    public Carrito() {
        productos = new ArrayList<>();
    }

    public void agregarProductosAlCarrito(List<Producto> productos) {
        if (productos.isEmpty()) {
            throw new RuntimeException("La lista de productos no puede ser vacia.");
        }
        this.productos.addAll(productos);
    }

    public void agregarProductoAlCarrito(Producto p) {
        if (p == null) {
            throw new RuntimeException("El producto no puede ser vacio.");
        }
        this.productos.add(new Producto(p.getDescripcion(), p.getCodigo(), p.getPrecio(), p.getMarca(), p.getCategoria()));
    }

    public double calcularMontoCarrito(List<MarcaPromocion> marcaPromociones, PagoPromocion pagoPromocion, Tarjeta tarjeta) {
        double total = 0;
        if (tarjeta == null)
            throw new RuntimeException("La tarjeta no puede ser vacia.");
        for (Producto producto : productos) {
            total += producto.getPrecio();
            if (!marcaPromociones.isEmpty()) {
                double descuento = marcaPromociones.stream()
                        .mapToDouble(promo -> producto.getPrecio() * promo.aplicarDescuento(producto))
                        .sum();
                total -= descuento;
            }
        }
        if (pagoPromocion != null)
            total = pagoPromocion.aplicarDescuento(total, tarjeta);
        return total;
    }

    public Venta pagar(List<MarcaPromocion> marcaPromociones, PagoPromocion pagoPromocion, Cliente cliente, Tarjeta tarjeta) {
        return new Venta(LocalDateTime.now(), cliente, tarjeta, this.productos, calcularMontoCarrito(marcaPromociones, pagoPromocion, tarjeta));
    }
}