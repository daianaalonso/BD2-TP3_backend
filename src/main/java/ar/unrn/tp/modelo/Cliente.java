package ar.unrn.tp.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String dni;
    private String email;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private List<Tarjeta> tarjetas;

    protected Cliente() {
    }

    public Cliente(String nombre, String apellido, String dni, String email) {
        if (nombre == null || nombre.isEmpty())
            throw new RuntimeException("El nombre debe ser valido");
        this.nombre = nombre;

        if (apellido == null || apellido.isEmpty())
            throw new RuntimeException("El apellido debe ser valido");
        this.apellido = apellido;

        if (dni == null || nombre.isEmpty())
            throw new RuntimeException("El DNI debe ser valido");
        this.dni = dni;

        if (!validarEmail(email))
            throw new RuntimeException("El email debe ser valido");
        this.email = email;

        this.tarjetas = new ArrayList<>();
    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validarEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public void agregarTarjeta(Tarjeta tarjeta) {
        this.tarjetas.add(tarjeta);
    }

    public boolean miTarjeta(Tarjeta tarjeta) {
        return this.tarjetas.stream().anyMatch(t -> t.esTarjeta(tarjeta));
    }

    public Long getId() {
        return id;
    }

    public List<Tarjeta> getTarjetas() {
        return this.tarjetas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean suNombreEs(String nombre) {
        return this.nombre.equals(nombre);
    }

    public boolean suApellidoEs(String apellido) {
        return this.apellido.equals(apellido);
    }

    public boolean suDniEs(String dni) {
        return this.dni.equals(dni);
    }

    public boolean suEmailEs(String email) {
        return this.email.equals(email);
    }

}