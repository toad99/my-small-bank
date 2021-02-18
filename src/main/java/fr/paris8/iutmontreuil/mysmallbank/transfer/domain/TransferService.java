package fr.paris8.iutmontreuil.mysmallbank.transfer.domain;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountRepository;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Transfer;
import fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto.TransferDTO;
import fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure.TransferRepository;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountRepository;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    public Transfer getTransfer(String id) {
        return transferRepository.getTransfer(id);
    }

    public void faireTransfer (Transfer transfer) {
        double account1Balance =
                accountRepository.getAccount(transfer.getAccountIdFrom()).getBalance() - transfer.getAmount();
        double account2Balance =
                accountRepository.getAccount(transfer.getAccountIdTo()).getBalance() + transfer.getAmount();

        Account account1 = new Account(transfer.getAccountIdFrom(),
                accountRepository.getAccount(transfer.getAccountIdFrom()).getHolder(),
                accountRepository.getAccount(transfer.getAccountIdFrom()).getCategory(),account1Balance);

        Account account2 = new Account(transfer.getAccountIdTo(),
                accountRepository.getAccount(transfer.getAccountIdTo()).getHolder(),
                accountRepository.getAccount(transfer.getAccountIdTo()).getCategory(),account2Balance);

        accountRepository.create(account1);
        accountRepository.create(account2);
    }

    public Transfer createTransfer(Transfer transfer) {
        List<ValidationError> validationErrors = validate(transfer);
        if (!validationErrors.isEmpty()) {
            throw new ValidationErrorException(validationErrors);
        }
        faireTransfer(transfer);
        return transferRepository.create(transfer);
    }

    public List<ValidationError> validate(Transfer transfer){
        List<ValidationError> listeErreurs = new ArrayList<>();

        if(transfer.getAmount() < 1)
            listeErreurs.add(new ValidationError("le montant minimum est 1"));
        Account account = accountRepository.getAccount(transfer.getAccountIdFrom());
        if(transfer.getAccountIdFrom().equals(transfer.getAccountIdTo()))
            listeErreurs.add(new ValidationError("ne peut pas transferer a soit même"));
        else if(account.getBalance() - transfer.getAmount() - account.getCategory().getMinimumBalance() < 0 ){
            listeErreurs.add(new ValidationError("pas assez d'argent pour le transfer"));
        }
        return listeErreurs;
    }

    public List<Transfer> getTransfers() {
        return transferRepository.getAll();
    }
    
}
