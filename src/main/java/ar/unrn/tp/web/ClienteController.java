package ar.unrn.tp.web;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.modelo.Tarjeta;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Tarjeta>> listarTarjetas(@NonNull Long id) {
        return new ResponseEntity<>(clienteService.listarTarjetas(id), HttpStatus.OK);
    }
}