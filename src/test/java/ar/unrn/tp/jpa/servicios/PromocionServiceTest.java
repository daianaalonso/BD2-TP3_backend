package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.MarcaPromocion;
import ar.unrn.tp.modelo.PagoPromocion;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PromocionServiceTest {
    private EntityManagerFactory emf;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
    }

    @Test
    public void crearPromoMarca() {
        PromocionServiceJPA promocionServiceJPA = new PromocionServiceJPA(emf);
        promocionServiceJPA.crearDescuento("Nike", LocalDate.now().minusDays(10), LocalDate.now().minusDays(2), 0.05);

        inTransactionExecute(
                (em) -> {
                    MarcaPromocion marcaPromocion = em.find(MarcaPromocion.class, 1L);
                    assertTrue(marcaPromocion.suMarcaEs(new Marca("Nike")));
                    assertTrue(marcaPromocion.inicia(LocalDate.now().minusDays(10)));
                    assertTrue(marcaPromocion.finaliza(LocalDate.now().minusDays(2)));
                    assertTrue(marcaPromocion.suDescuentoEs(0.05));
                }
        );
    }

    @Test
    public void crearrPromoPago() {
        PromocionServiceJPA promocionServiceJPA = new PromocionServiceJPA(emf);
        promocionServiceJPA.crearDescuentoSobreTotal("VISA", LocalDate.now().minusDays(10), LocalDate.now().minusDays(2), 0.08);

        inTransactionExecute(
                (em) -> {
                    PagoPromocion pagoPromocion = em.find(PagoPromocion.class, 1L);
                    assertTrue(pagoPromocion.suTarjetaEs(new Tarjeta("VISA")));
                    assertTrue(pagoPromocion.inicia(LocalDate.now().minusDays(10)));
                    assertTrue(pagoPromocion.finaliza(LocalDate.now().minusDays(2)));
                    assertTrue(pagoPromocion.suDescuentoEs(0.08));
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
        }
    }

    @AfterEach
    public void tearDown() {
        emf.close();
    }
}