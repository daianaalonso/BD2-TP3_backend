package ar.unrn.tp.api;

import ar.unrn.tp.dto.PromocionDTO;

import java.time.LocalDate;
import java.util.List;

public interface PromocionService {
    // validar que las fechas no se superpongan
    void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde,
                                  LocalDate fechaHasta, Double porcentaje);

    // validar que las fechas no se superpongan
    void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate
            fechaHasta, Double porcentaje);

    List<PromocionDTO> listarDescuentosActivos();
}
