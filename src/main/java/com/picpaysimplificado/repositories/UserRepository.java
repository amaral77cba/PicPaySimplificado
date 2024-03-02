package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {  //os parametros do JPA sao o tipo da classe e tipo da chave primaria
    //no caso exemplo da DEV Fernanda Kipper, ela utilizou um metodo para buscar as informacoes
    Optional<User> findUserByDocCPF(String document);   //o tipo de retorno eh Optional pq pode encontrar ou nao a ocorrencia
                                                        //o nome do metodo tem que seguir esse padrao com o nome do atributo
                                                        //desta forma o JPA consegue internamente fazer o select e trazer os dados.

    Optional<User> findUserById(Long id); //alteracao de comentario

}
