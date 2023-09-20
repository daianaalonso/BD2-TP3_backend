package ar.unrn.tp.web;

import ar.unrn.tp.api.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venta")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class VentaController {
    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<Double> calcularMontoCarrito(@RequestParam List<Long> productos, Long idTarjeta) {
        Double monto = ventaService.calcularMonto(productos, idTarjeta);
        return new ResponseEntity<>(monto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> crearVenta(@RequestParam Long idCliente, List<Long> productos, Long idTarjeta) {
        ventaService.realizarVenta(idCliente, productos, idTarjeta);
        return new ResponseEntity<>("Venta registrada correctamente.", HttpStatus.OK);
    }
}
