package br.edu.ibmec.loja_de_brinquedos_esportivos.repository;

import br.edu.ibmec.loja_de_brinquedos_esportivos.entity.Brinquedo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrinquedoRepository extends JpaRepository<Brinquedo, Long> {
}
