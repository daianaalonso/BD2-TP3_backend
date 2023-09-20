package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tarjeta {

    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private int numero;

    public Tarjeta(String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public Tarjeta(String nombre) {
        this.nombre = nombre;
    }

    protected Tarjeta() {

    }

    public boolean esTarjeta(Tarjeta tarjeta) {
        return this.nombre.equals(tarjeta.nombre());
    }

    public String nombre() {
        return this.nombre;
    }

    public int numero() {
        return this.numero;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


}