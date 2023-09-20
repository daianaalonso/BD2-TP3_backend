package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Inheritance
@DiscriminatorColumn(name = "tipo_promocion")
public abstract class Promocion {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    private Double porcentaje;

    public Promocion(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje) {
        if (!validarFecha(fechaInicio, fechaFin))
            throw new RuntimeException("Las fechas de la promoción no son validas.");
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.porcentaje = porcentaje;
    }

    protected Promocion() {
    }

    private boolean validarFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return fechaInicio.isBefore(fechaFin);
    }

    public boolean estaEnCurso() {
        LocalDate hoy = LocalDate.now();
        return hoy.isAfter(fechaInicio) && hoy.isBefore(fechaFin);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public double getPorcentaje() {
        return this.porcentaje;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    public boolean inicia(LocalDate fechaInicio) {
        return this.fechaInicio.equals(fechaInicio);
    }

    public boolean finaliza(LocalDate fechaFin) {
        return this.fechaFin.equals(fechaFin);
    }

    public boolean suDescuentoEs(double porcentaje) {
        return this.porcentaje.equals(porcentaje);
    }
}