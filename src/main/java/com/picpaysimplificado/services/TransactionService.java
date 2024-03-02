package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDTO;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.usuarioId());
        User receiver = this.userService.findUserById(transaction.fornecedorId());

        //chama o metodo para validar se o usuario vai validar o
        userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if(! isAuthorized){
            throw new Exception("Transacao não autorizada!");
        }

        //criando a instancia da transacao
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReciver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        //subtraindo os dados do usuario e acrescentando a outro
        sender.setSaldo(sender.getSaldo().subtract(transaction.value()));
        receiver.setSaldo(receiver.getSaldo().add(transaction.value()));

        //Persistindo os dados no banco de dados
        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transacao realizada com sucesso para o server");
        this.notificationService.sendNotification(receiver, "Transacao criada com sucesso para o reciever");

        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);
        /*
        if(authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody();//.getKey("message");// .getElement("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
        */
        return true;    //AQUI
    }

    public void save(Transaction newTransaction){
        this.repository.save(newTransaction);
    }

}
