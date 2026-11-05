package br.edu.ibmec.loja_de_brinquedos_esportivos.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nome obrigatorio")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "email obrigatorio")
    @Email(message = "informe um email valido")
    @Column(nullable = false,  unique = true)
    private String email;

    @NotBlank(message = "senha obrigatoria")
    @Column(nullable = false)
    @Size(min = 6, message = "A enha deve possuir pelo menos 6 caracteres")
    private String senha;

    @NotBlank(message = "O perfil e obrigatorio")
    @Column(nullable = false)
    private TiposUsuario perfil;


    public Usuario(){}

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public TiposUsuario getPerfil() {
        return perfil;
    }
    public void setPerfil(TiposUsuario perfil) {
        this.perfil = perfil;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
