package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "marca")
public class Marca {

    @Id
    @GeneratedValue
    private Long id;
    private String nombre;

    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public boolean esMarca(String marca) {
        return this.nombre.equals(marca);
    }
}
