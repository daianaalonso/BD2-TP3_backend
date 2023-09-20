package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@DiscriminatorValue("marca")
public class MarcaPromocion extends Promocion {

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Marca marca;

    public MarcaPromocion(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, Marca marca) {
        super(fechaInicio, fechaFin, porcentaje);
        this.marca = marca;
    }

    protected MarcaPromocion() {

    }

    public double aplicarDescuento(Producto producto) {
        if (producto.suMarcaEs(this.marca)) {
            return descuento();
        }
        return 0;
    }

    private double descuento() {
        if (estaEnCurso())
            return super.getPorcentaje();
        return 0;
    }

    public boolean suMarcaEs(Marca marca) {
        return this.marca.esMarca(marca);
    }
}