package ar.unrn.tp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PromocionDTO {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double porcentaje;
    private String tipo;
    private String descripcion;
}
