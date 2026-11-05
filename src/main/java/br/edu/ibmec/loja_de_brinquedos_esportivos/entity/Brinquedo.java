package br.edu.ibmec.loja_de_brinquedos_esportivos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="TDS_TB_Brinquedos")
public class Brinquedo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome e obrigatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O tipo e obrigatorio")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String tipo;

    @NotBlank(message = "A classificacao e obrigatoria")
    @Column(nullable = false)
    private String classificacao;

    @NotBlank(message = "Tamanho e obrigatorio")
    @Column(nullable = false)
    private String tamanho;

    @NotNull(message = "O preco e obrigatorio")
    @DecimalMin(value = "0.01", message="O preco deve ser maior do que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    public Brinquedo(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getClassificacao() {
        return classificacao;
    }
    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }
    public String getTamanho() {
        return tamanho;

    }
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }



}
