package ar.unrn.tp.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("marca")
public class MarcaPromocion extends Promocion {

    private String marca;

    public MarcaPromocion(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, String marca) {
        super(fechaInicio, fechaFin, porcentaje);
        this.marca = marca;
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

    public boolean esMarca(String marca){
        return this.marca.equals(marca);
    }
}