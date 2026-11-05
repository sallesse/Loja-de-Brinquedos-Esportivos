package br.edu.ibmec.loja_de_brinquedos_esportivos.repository;


import br.edu.ibmec.loja_de_brinquedos_esportivos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}