package fr.paris8.iutmontreuil.mysmallbank.account.domain;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountRepository;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.TransferService;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Transfer;
import fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransferService transferService;

    public AccountService(AccountRepository accountRepository,
                          TransferService transferService) {
        this.accountRepository = accountRepository;
        this.transferService = transferService;
    }

    public List<Account> listAllAccount() {
        return accountRepository.listAccounts();
    }

    public Account getAccount(String accountUid) {
        return accountRepository.getAccount(accountUid);
    }

    public Account createAccounts(Account account) {
        List<ValidationError> validationErrors = validate(account);
        if (!validationErrors.isEmpty()) {
            throw new ValidationErrorException(validationErrors);
        }
        return accountRepository.create(account);
    }

    public List<Account> createAccounts(List<Account> account) {
        List<ValidationError> validationErrors = new ArrayList<>();
        List<Account> listeValidé = account.stream()
                .filter(x->!validationErrors.addAll(validate(x)))
                .collect(Collectors.toList());
        if (!validationErrors.isEmpty()) {
            throw new ValidationErrorException(validationErrors);
        }
        return listeValidé.stream()
                    .map(x->accountRepository.create(x))
                    .collect(Collectors.toList());
    }

    public void delete(String accountUid, Optional<String> accountTransferUid) {
        Account account = accountRepository.getAccount(accountUid);
        List<ValidationError> listeErreurs = new ArrayList<>();
        if(account.getBalance() < 0)
            listeErreurs.add(new ValidationError("balance est negatif"));
        if(account.getBalance() > 1 && accountTransferUid.isEmpty())
            listeErreurs.add(new ValidationError("deuxieme compte pour transferer n'a pas été renseigné"));
        if(!listeErreurs.isEmpty())
            throw new ValidationErrorException(listeErreurs);

        if(account.getBalance() > 1) {
            Account account_transfer = accountRepository.getAccount(accountTransferUid.get());
            Transfer transfer = new Transfer(accountUid,account_transfer.getUid(),account.getBalance());
            transferService.createTransfer(transfer);
        }
        accountRepository.delete(accountUid);
    }


    private List<ValidationError> validate(Account account) {
        List<ValidationError> listeErreurs = new ArrayList<>();
        if (account.getHolder() == null)
            listeErreurs.add(new ValidationError("holder n'est pas renseigné"));
        else{
            if (account.getHolder().getId() == null || account.getHolder().getId().length() == 0)
                listeErreurs.add(new ValidationError("id n'est pas renseigné"));
            else{
                if(accountRepository.getHolder(account.getHolder().getId()) == null)
                    listeErreurs.add(new ValidationError("holder n'existe pas"));
            }
            if (account.getCategory() == null)
                listeErreurs.add(new ValidationError("type n'est pas renseigné"));
            else
                if (account.getBalance() < account.getCategory().getMinimumBalance() || account.getBalance() < 0)
                    listeErreurs.add(new ValidationError("balance plus basse que le minimum ou balance est negatif"));
        }
        return listeErreurs;
    }

    public double computeInterest() {
        List<Account> liste = accountRepository.listAccounts();
        return liste.stream()
                .map(x->x.getBalance() * x.getCategory().getInterestRate())
                .reduce((double) 0, (x, y) -> x+y);
    }
}
