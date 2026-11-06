package br.edu.ibmec.loja_de_brinquedos_esportivos.controller;


import br.edu.ibmec.loja_de_brinquedos_esportivos.entity.Brinquedo;
import br.edu.ibmec.loja_de_brinquedos_esportivos.service.BrinquedoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brinquedo")
public class BrinquedoController {
    private final BrinquedoService service;

    public BrinquedoController (BrinquedoService service){
        this.service = service;
    }

    @GetMapping
    public List<Brinquedo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Brinquedo buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Brinquedo criar(@Valid @RequestBody Brinquedo brinquedo) {
        return service.criar(brinquedo);
    }

    @PutMapping("/{id}")
    public Brinquedo atualizar(@PathVariable Long id, @Valid @RequestBody Brinquedo brinquedo) {
        return service.atualizar(id, brinquedo);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }


}
