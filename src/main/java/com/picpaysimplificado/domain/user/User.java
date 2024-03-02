package com.picpaysimplificado.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName, lastName;
    @Column(unique = true)  //para definir que o campo CPF eh unico
    private String docCPF;
    @Column(unique = true)  //para definir que o campo email eh unico
    private String email;
    private String password;
    private BigDecimal saldo;   //saldo bancario usuario
    @Enumerated(EnumType.STRING)
    private UserType userType;  //busca do enumerador o Tipo de Usuario (CLIENTE, LOJISTA)

}
