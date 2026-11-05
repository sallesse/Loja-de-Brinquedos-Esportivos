package br.edu.ibmec.loja_de_brinquedos_esportivos.service;


import br.edu.ibmec.loja_de_brinquedos_esportivos.entity.Brinquedo;
import br.edu.ibmec.loja_de_brinquedos_esportivos.repository.BrinquedoRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BrinquedoService {
    private final BrinquedoRepository repository;

    public BrinquedoService(BrinquedoRepository repository) {
        this.repository = repository;
    }

    public List<Brinquedo> listar(){
        return repository.findAll();
    }

    public Brinquedo buscar(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "BRINQUEDO nao encontrado"));


    }

    public Brinquedo criar(Brinquedo brinquedo){
        brinquedo.setId(null);
        return repository.save(brinquedo);
    }

    public Brinquedo atualizar(Long id, Brinquedo dados){
        Brinquedo brinquedo = buscar(id);
        brinquedo.setNome(dados.getNome());
        brinquedo.setTipo(dados.getTipo());
        brinquedo.setClassificacao(dados.getClassificacao());
        brinquedo.setTamanho(dados.getTamanho());
        return repository.save(brinquedo);
    }

    public void deletar(Long id){
        repository.delete(buscar(id));
    }

}
