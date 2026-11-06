package br.edu.ibmec.loja_de_brinquedos_esportivos.config;

import br.edu.ibmec.loja_de_brinquedos_esportivos.entity.TiposUsuario;
import br.edu.ibmec.loja_de_brinquedos_esportivos.entity.Usuario;
import br.edu.ibmec.loja_de_brinquedos_esportivos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DadosIniciais {

    @Bean
    CommandLineRunner criarUsuarios(UsuarioRepository repository,
                                    PasswordEncoder encoder) {
        return args -> {
            criar(repository, encoder, "Usuário", "user@loja.com", "user123", TiposUsuario.USER);
            criar(repository, encoder, "Administrador", "admin@loja.com", "admin123", TiposUsuario.ADMIN);
        };
    }

    private void criar(UsuarioRepository repository, PasswordEncoder encoder,
                       String nome, String email, String senha, TiposUsuario perfil) {
        if (repository.findByEmail(email).isEmpty()) {
            Usuario usuario =new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(encoder.encode(senha));
            usuario.setPerfil(perfil);
            repository.save(usuario);
        }
    }
}