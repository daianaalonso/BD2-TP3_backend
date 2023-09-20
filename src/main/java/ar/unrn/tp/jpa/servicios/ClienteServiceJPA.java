package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceJPA implements ClienteService {
    private EntityManagerFactory emf;

    public ClienteServiceJPA(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void crearCliente(String nombre, String apellido, String dni, String email) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Cliente> q = em.createQuery("SELECT c FROM Cliente c WHERE c.dni = :dni", Cliente.class);
            q.setParameter("dni", dni);
            List<Cliente> clientes = q.getResultList();
            if (!clientes.isEmpty())
                throw new RuntimeException("El DNI ya existe.");

            Cliente c = new Cliente(nombre, apellido, dni, email);
            em.persist(c);
            tx.commit();
        } catch (PersistenceException pe) {
            tx.rollback();
            throw new RuntimeException("El DNI ya existe.");
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void modificarCliente(Long idCliente, String nombre, String apellido, String dni, String email) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cliente c = em.find(Cliente.class, idCliente);
            if (c == null)
                throw new RuntimeException("El cliente no existe.");
            c.setNombre(nombre);
            c.setDni(dni);
            c.setApellido(apellido);
            c.setEmail(email);
            tx.commit();
        } catch (PersistenceException pe) {
            tx.rollback();
            throw new RuntimeException("El DNI ya existe.");
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void agregarTarjeta(Long idCliente, String nro, String nombre) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cliente c = em.find(Cliente.class, idCliente);
            if (c == null)
                throw new RuntimeException("El cliente no existe.");

            c.agregarTarjeta(new Tarjeta(nombre, Integer.parseInt(nro)));
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
    public List<Tarjeta> listarTarjetas(Long idCliente) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cliente c = em.find(Cliente.class, idCliente);
            if (c == null)
                throw new RuntimeException("El cliente no existe.");
            tx.commit();
            return c.getTarjetas();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public Cliente buscarCliente(Long idCliente) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cliente c = em.find(Cliente.class, idCliente);
            if (c == null)
                throw new RuntimeException("El cliente no existe.");
            tx.commit();
            return c;
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}