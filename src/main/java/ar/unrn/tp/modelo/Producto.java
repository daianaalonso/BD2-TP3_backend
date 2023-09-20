package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto {

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

    public Producto(Long id, String descripcion, String codigo, Double precio, Marca marca, Categoria categoria) {
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

        this.id = id;
    }


    public Producto(String descripcion, String codigo, Double precio, Marca marca, Categoria categoria) {
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

    protected Producto() {
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean suDescripcionEs(String descripcion) {
        return this.descripcion.equals(descripcion);
    }

    public boolean suCodigoEs(String codigo) {
        return this.codigo.equals(codigo);
    }

    public boolean suPrecioEs(Double precio){
        return this.precio.equals(precio);
    }

    public boolean suMarcaEs(Marca marca) {
        return this.marca.esMarca(marca);
    }

    public boolean suCategoriaEs(Categoria categoria){
        return this.categoria.esCategoria(categoria);
    }

    public String descripcion() {
        return this.descripcion;
    }

    public String codigo() {
        return this.codigo;
    }

    public Double precio() {
        return this.precio;
    }

    public Marca marca() {
        return this.marca;
    }

    public Categoria categoria() {
        return this.categoria;
    }
}