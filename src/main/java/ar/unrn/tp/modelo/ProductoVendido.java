package ar.unrn.tp.modelo;

import jakarta.persistence.*;

@Entity
public class ProductoVendido {

    @Id
    @GeneratedValue
    private Long id;
    private String descripcion;
    @Column(unique = true)
    private String codigo;
    private Double precio;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Marca marca;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Categoria categoria;

    public ProductoVendido(String descripcion, String codigo, Double precio, Marca marca, Categoria categoria) {
        if (esDatoVacio(codigo))
            throw new RuntimeException("El codigo debe ser valido");
        this.codigo = codigo;

        if (descripcion == null || descripcion.isEmpty())
            throw new RuntimeException("La descripcion debe ser valida");
        this.descripcion = descripcion;

        if (esDatoVacio(String.valueOf(precio)) || esDatoNulo(precio))
            throw new RuntimeException("El precio debe ser valido");
        this.precio = precio;

        if (esDatoNulo(categoria))
            throw new RuntimeException("La categoria debe ser valida");
        this.categoria = categoria;

        if (esDatoNulo(marca))
            throw new RuntimeException("La marca debe ser valida");
        this.marca = marca;
    }

    protected ProductoVendido() {
    }

    private boolean esDatoVacio(String dato) {
        return dato.equals("");
    }

    private boolean esDatoNulo(Object dato) {
        return dato == null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

