package br.edu.ibmec.loja_de_brinquedos_esportivos.service;

import br.edu.ibmec.loja_de_brinquedos_esportivos.entity.Usuario;
import br.edu.ibmec.loja_de_brinquedos_esportivos.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Usuario cadastrar(Usuario usuario){
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new RuntimeException("email ja cadastrado");
        }
        //parte de criptografar a senha
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // cadastro publico cria perfil user
        usuario.setPerfil("USER");

        return usuarioRepository.save(usuario);

    }
    public void excluir(Long id){

        if(!usuarioRepository.existsById(id)){
            throw new RuntimeException("usuario nao encontrado");
        }

        usuarioRepository.deleteById(id);
    }
}