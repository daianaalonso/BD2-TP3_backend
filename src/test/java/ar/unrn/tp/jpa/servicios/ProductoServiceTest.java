package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;
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

public class ProductoServiceTest {

    private EntityManagerFactory emf;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
    }

    @Test
    public void crearProducto() {
        Categoria cateIndumentaria = new Categoria("Indumentaria");
        Marca marcaNike = new Marca("Nike");
        inTransactionExecute(
                (em) -> {
                    em.persist(cateIndumentaria);
                    em.persist(marcaNike);
                }
        );

        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA(emf);
        productoServiceJPA.crearProducto("123", "Remera", 5000.0, cateIndumentaria.getId(), marcaNike.getId());

        inTransactionExecute(
                (em) -> {
                    Producto producto = em.find(Producto.class, 3L);
                    assertTrue(producto.suCodigoEs("123"));
                    assertTrue(producto.suDescripcionEs("Remera"));
                    assertTrue(producto.suPrecioEs(5000.0));
                    assertTrue(producto.suCategoriaEs(cateIndumentaria));
                    assertTrue(producto.suMarcaEs(marcaNike));
                }
        );
    }

    @Test
    public void crearProductoConCodigoRepetido() {
        Categoria cateIndumentaria = new Categoria("Indumentaria");
        Marca marcaNike = new Marca("Nike");
        inTransactionExecute(
                (em) -> {
                    em.persist(cateIndumentaria);
                    em.persist(marcaNike);
                }
        );

        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA(emf);
        productoServiceJPA.crearProducto("123", "Remera", 5000.0, cateIndumentaria.getId(), marcaNike.getId());
        assertThrows(RuntimeException.class,
                () -> productoServiceJPA.crearProducto("123", "Camiseta", 5000.0, cateIndumentaria.getId(), marcaNike.getId()));
    }

    @Test
    public void crearProductoSinMarcaYCategoria() {
        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA(emf);
        assertThrows(RuntimeException.class,
                () -> productoServiceJPA.crearProducto("123", "Camiseta", 5000.0, null, null));
    }

    @Test
    public void modificarProductoExistente() {
        Categoria cateIndumentaria = new Categoria("Indumentaria");
        Marca marcaNike = new Marca("Nike");
        inTransactionExecute(
                (em) -> {
                    em.persist(cateIndumentaria);
                    em.persist(marcaNike);
                }
        );

        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA(emf);
        productoServiceJPA.crearProducto("123", "Remera", 5000.0, cateIndumentaria.getId(), marcaNike.getId());
        productoServiceJPA.modificarProducto(3L, "Remera", "123", 4000.0, marcaNike.getId(), cateIndumentaria.getId());

        inTransactionExecute(
                (em) -> {
                    Producto producto = em.find(Producto.class, 3L);
                    assertTrue(producto.suCodigoEs("123"));
                    assertTrue(producto.suDescripcionEs("Remera"));
                    assertTrue(producto.suPrecioEs(4000.0));
                    assertTrue(producto.suCategoriaEs(cateIndumentaria));
                    assertTrue(producto.suMarcaEs(marcaNike));
                }
        );
    }

    @Test
    public void modificarProductoInexistente() {
        Categoria cateIndumentaria = new Categoria("Indumentaria");
        Marca marcaNike = new Marca("Nike");
        inTransactionExecute(
                (em) -> {
                    em.persist(cateIndumentaria);
                    em.persist(marcaNike);
                }
        );
        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA(emf);
        productoServiceJPA.crearProducto("123", "Remera", 5000.0, cateIndumentaria.getId(), marcaNike.getId());

        assertThrows(RuntimeException.class,
                () -> productoServiceJPA.modificarProducto(9L, "Remera", "123", 4000.0, marcaNike.getId(), cateIndumentaria.getId()));
    }

    @Test
    public void listarProductosExistentes() {
        Categoria cateIndumentaria = new Categoria("Indumentaria");
        Marca marcaNike = new Marca("Nike");
        inTransactionExecute(
                (em) -> {
                    em.persist(cateIndumentaria);
                    em.persist(marcaNike);
                }
        );
        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA(emf);
        productoServiceJPA.crearProducto("123", "Remera", 5000.0, cateIndumentaria.getId(), marcaNike.getId());
        productoServiceJPA.crearProducto("456", "Pantalon", 9000.0, cateIndumentaria.getId(), marcaNike.getId());
        List<Producto> productosPersistidos = productoServiceJPA.listarProductos();

        assertTrue(!productosPersistidos.isEmpty());
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
        }
    }

    @AfterEach
    public void tearDown() {
        emf.close();
    }
}