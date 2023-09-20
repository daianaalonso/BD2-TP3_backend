package ar.unrn.tp.modelo;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venta {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime fecha;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cliente cliente;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Tarjeta tarjeta;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_venta")
    private List<ProductoVendido> productosVendidos;
    private Double montoTotal;

    public Venta(LocalDateTime fecha, Cliente cliente, Tarjeta tarjeta, List<Producto> productosVendidos, Double montoTotal) {
        this.productosVendidos = new ArrayList<>();
        this.fecha = fecha;
        this.cliente = cliente;
        this.tarjeta = tarjeta;
        this.montoTotal = montoTotal;
        agregarProductos(productosVendidos);
    }

    protected Venta() {
    }

    private void agregarProductos(List<Producto> productosVendidos) {
        productosVendidos.stream()
                .map(p -> new ProductoVendido(p.descripcion(), p.codigo(), p.precio(), p.marca(), p.categoria()))
                .forEach(this.productosVendidos::add);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean montoEs(Double montoTotal){
        return this.montoTotal.equals(montoTotal);
    }
}