package br.edu.ibmec.loja_de_brinquedos_esportivos.controller;


import br.edu.ibmec.loja_de_brinquedos_esportivos.entity.Brinquedo;
import br.edu.ibmec.loja_de_brinquedos_esportivos.service.BrinquedoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping




}
