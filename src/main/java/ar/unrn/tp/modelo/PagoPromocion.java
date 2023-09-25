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
@DiscriminatorValue("pago")
public class PagoPromocion extends Promocion {

    private String tarjeta;

    public PagoPromocion(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, String tarjeta) {
        super(fechaInicio, fechaFin, porcentaje);
        this.tarjeta = tarjeta;
    }

    public double aplicarDescuento(double total, Tarjeta tarjeta) {
        if (tarjeta.esTarjeta(this.tarjeta))
            return total - (total * descuento());
        return total;
    }

    private double descuento() {
        if (estaEnCurso())
            return super.getPorcentaje();
        return 0;
    }

    public boolean suTarjetaEs(Tarjeta tarjeta) {
        return tarjeta.equals(this.tarjeta);
    }
}