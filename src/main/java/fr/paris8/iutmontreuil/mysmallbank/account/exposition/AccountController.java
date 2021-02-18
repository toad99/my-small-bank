package fr.paris8.iutmontreuil.mysmallbank.account.exposition;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.AccountService;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> listAllAccounts() {
        List<Account> accounts = accountService.listAllAccount();
        return AccountMapper.toDTOs(accounts);
    }


    @GetMapping("/{id}")
    public AccountDTO getAccount(@PathVariable("id") String accountUid) {
        return AccountMapper.toDTO(accountService.getAccount(accountUid));
    }


    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws URISyntaxException {
        Account tempo = AccountMapper.toAccount(accountDTO);
        Account createdAccount = accountService.createAccounts(tempo);
        AccountDTO createdAccountDTO = AccountMapper.toDTO(createdAccount);
        return ResponseEntity.created(new URI("/accounts/" + createdAccountDTO.getUid())).body(createdAccountDTO);
    }

    @PostMapping("/batch")
    public List<AccountDTO> createAccount(@RequestBody List<AccountDTO> accountDTO) {
        List<Account> account = AccountMapper.toAccount(accountDTO);
        List<Account> createdAccount = accountService.createAccounts(account);
        return AccountMapper.toDTOs(createdAccount);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable("id") String accountUid,@RequestBody Optional<String> accountTransferUid) throws URISyntaxException {
        accountService.delete(accountUid,accountTransferUid);
    }

    @PostMapping("/compute-interest")
    public double computeInterest() {
        return accountService.computeInterest();
    }
}
