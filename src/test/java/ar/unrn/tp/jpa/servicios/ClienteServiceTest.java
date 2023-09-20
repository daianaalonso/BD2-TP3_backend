package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClienteServiceTest {
    private EntityManagerFactory emf;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("jpa-derby-embedded");
    }

    @Test
    public void crearCliente() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA(emf);
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448077", "dalonso@gmail.com");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.suNombreEs("Daiana"));
                    assertTrue(cliente.suApellidoEs("Alonso"));
                    assertTrue(cliente.suDniEs("42448077"));
                    assertTrue(cliente.suEmailEs("dalonso@gmail.com"));
                }
        );
    }

    @Test
    public void crearClienteConDNIRepetido() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA(emf);
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448077", "dalonso@gmail.com");
        assertThrows(RuntimeException.class,
                () -> clienteServiceJPA.crearCliente("Andres", "Blanco", "42448077", "ablanco@gmail.com"));
    }

    @Test
    public void modificarClienteInexistente() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA(emf);
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448076", "dalonso@gmail.com");
        assertThrows(RuntimeException.class,
                () -> clienteServiceJPA.modificarCliente(10L, "Dai", "Ramos", "42448078", "dramos@gmail.com"));
    }

    @Test
    public void modificarClienteExistente() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA(emf);
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448076", "dalonso@gmail.com");
        clienteServiceJPA.modificarCliente(1L, "Dai", "Ramos", "42448078", "dramos@gmail.com");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.suNombreEs("Dai"));
                    assertTrue(cliente.suApellidoEs("Ramos"));
                    assertTrue(cliente.suDniEs("42448078"));
                    assertTrue(cliente.suEmailEs("dramos@gmail.com"));
                }
        );
    }

    @Test
    public void agregarTarjetaAClienteExistente() {
        String nombreTarjeta = "VISA";
        String nroTarjeta = "123456789";
        Tarjeta tarjetaVisa = new Tarjeta(nombreTarjeta, Integer.parseInt(nroTarjeta));

        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA(emf);
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448070", "dalonso@gmail.com");
        clienteServiceJPA.agregarTarjeta(1L, nroTarjeta, nombreTarjeta);

        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.suNombreEs("Daiana"));
                    assertTrue(cliente.suApellidoEs("Alonso"));
                    assertTrue(cliente.suDniEs("42448070"));
                    assertTrue(cliente.suEmailEs("dalonso@gmail.com"));
                    assertTrue(cliente.miTarjeta(tarjetaVisa));
                }
        );
    }

    @Test
    public void listarTarjetasDeClienteExistente() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA(emf);
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448073", "dalonso@gmail.com");
        clienteServiceJPA.agregarTarjeta(1L, "123456789", "VISA");
        List<Tarjeta> tarjetas = clienteServiceJPA.listarTarjetas(1L);

        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.suNombreEs("Daiana"));
                    assertTrue(cliente.suApellidoEs("Alonso"));
                    assertTrue(cliente.suDniEs("42448073"));
                    assertTrue(cliente.suEmailEs("dalonso@gmail.com"));
                    assertTrue(!tarjetas.isEmpty());
                }
        );
    }

    public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            bloqueDeCodigo.accept(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen())
                em.close();
            if (emf != null)
                emf.close();
        }
    }

    @AfterEach
    public void tearDown() {
        emf.close();
    }
}