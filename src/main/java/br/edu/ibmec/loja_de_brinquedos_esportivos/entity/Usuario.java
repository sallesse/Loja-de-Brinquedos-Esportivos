package br.edu.ibmec.loja_de_brinquedos_esportivos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;


@Entity
@Table(name="TDS_TB_Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID_Usuario")
    private Long id;

    @NotBlank(message="Nome e obrigatorio")
    @Column(nullable = false, length = 100)
    private String nome;



}
