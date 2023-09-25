package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public boolean esTarjeta(String tarjeta) {
        return this.nombre.equals(tarjeta);
    }
}