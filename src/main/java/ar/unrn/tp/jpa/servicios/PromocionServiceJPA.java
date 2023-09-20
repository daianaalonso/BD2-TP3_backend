package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.PromocionService;
import ar.unrn.tp.dto.PromocionDTO;
import ar.unrn.tp.modelo.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PromocionServiceJPA implements PromocionService {

    private EntityManagerFactory emf;

    public PromocionServiceJPA(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, Double porcentaje) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Promocion p = new PagoPromocion(fechaDesde, fechaHasta, porcentaje, new Tarjeta(marcaTarjeta));
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, Double porcentaje) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            MarcaPromocion p = new MarcaPromocion(fechaDesde, fechaHasta, porcentaje, new Marca(marcaProducto));
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    public List<PromocionDTO> listarDescuentosActivos() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<PromocionDTO> promosActivasDTO = new ArrayList<>();
        List<PagoPromocion> promosPago;
        List<MarcaPromocion> promosMarca;
        LocalDate fechaActual = LocalDate.now();

        try {
            tx.begin();
            TypedQuery<PagoPromocion> q = em.createQuery("SELECT p FROM PagoPromocion p WHERE :fecha BETWEEN p.fechaInicio AND p.fechaFin", PagoPromocion.class);
            q.setParameter("fecha", fechaActual);
            promosPago = q.getResultList();

            for (PagoPromocion p : promosPago) {
                promosActivasDTO.add(new PromocionDTO(p.getFechaInicio(), p.getFechaFin(), p.getPorcentaje(), "pago", p.getTarjeta()));
            }

            TypedQuery<MarcaPromocion> qm = em.createQuery("SELECT p FROM MarcaPromocion p WHERE :fecha BETWEEN p.fechaInicio AND p.fechaFin", MarcaPromocion.class);
            qm.setParameter("fecha", fechaActual);
            promosMarca = qm.getResultList();

            for (MarcaPromocion p : promosMarca) {
                promosActivasDTO.add(new PromocionDTO(p.getFechaInicio(), p.getFechaFin(), p.getPorcentaje(), "marca"), p.getMarca());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
        return promosActivasDTO;
    }
}