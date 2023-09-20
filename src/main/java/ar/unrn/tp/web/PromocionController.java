package ar.unrn.tp.web;

import ar.unrn.tp.api.PromocionService;
import ar.unrn.tp.dto.PromocionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/promocion")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class PromocionController {
    private final PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<PromocionDTO>> listarPromocionesActivas() {
        return new ResponseEntity<>(promocionService.listarDescuentosActivos(), HttpStatus.OK);
    }
}
