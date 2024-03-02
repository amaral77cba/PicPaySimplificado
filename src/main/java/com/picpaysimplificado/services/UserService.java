package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired  //declaracao para fazer a ingestao do codigo abaixo
    private UserRepository repository;  //para ter acesso ao usario e fazer as manipulacoes

    //Escrevendo as regras de negocio
    public void validateTransaction(@NotNull User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.LOJISTA){
            throw new Exception("Usuario do tipo lojista, não esta autorizado a realizar transação.");
        }
        if (sender.getSaldo().compareTo(amount) < 0){
            throw new Exception("O usuario nao tem saldo suficiente para a transacao");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(()-> new Exception("Usuario nao encontrado"));

    }

    public User createUser(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }

    //Para persistir no banco de dados
    public void saveUser(User user){
        this.repository.save(user);
    }

}
