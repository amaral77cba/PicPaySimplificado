package com.picpaysimplificado.domain.user;

import com.picpaysimplificado.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    //Criei este get para utilizar na sequencia do codigo
    public UserType getUserType() {
        return userType;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(UserDTO data){
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.docCPF = data.document();
        this.email = data.email();
        this.password = data.password();
        this.saldo = data.balance();
        this.userType = data.userType();
    }
}
