package fr.paris8.iutmontreuil.mysmallbank.account;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Address;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AddressDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.HolderDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HolderMapper {

    private HolderMapper() { }

    public static Holder toHolder(HolderEntity entity) {
        return new Holder(entity.getId(), entity.getLastName(), entity.getFirstName(),new Address(entity.getStreet(),
                entity.getZipCode(),entity.getCity(),entity.getCountry()),
                entity.getBirthDate(),
                toAccount(entity.getAccounts()));
    }

    public static HolderEntity toEntity(Holder holder) {
        HolderEntity entity = new HolderEntity();
        entity.setBirthDate(holder.getBirthDate());
        entity.setFirstName(holder.getFirstName());
        entity.setLastName(holder.getLastName());
        entity.setId(holder.getId());
        entity.setStreet(holder.getAddress().getStreet());
        entity.setZipCode(holder.getAddress().getZipCode());
        entity.setCity(holder.getAddress().getCity());
        entity.setCountry(holder.getAddress().getCountry());

        return entity;
    }

    public static HolderDTO toDTO(Holder holder) {
        HolderDTO holderDTO = new HolderDTO();

        holderDTO.setId(holder.getId());
        holderDTO.setLastName(holder.getLastName());
        holderDTO.setFirstName(holder.getFirstName());
        holderDTO.setBirthDate(holder.getBirthDate());
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(holder.getAddress().getStreet());
        addressDTO.setCity(holder.getAddress().getCity());
        addressDTO.setZipCode(holder.getAddress().getZipCode());
        addressDTO.setCountry(holder.getAddress().getCountry());
        holderDTO.setAddress(addressDTO);
        holderDTO.setAccounts(toAccounts(holder.getAccounts()));

        return holderDTO;
    }

    public static List<HolderDTO> toDto(List<Holder> holders) {
        return holders.stream()
                .map(HolderMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static Account toAccount(AccountEntity entity) {
        return new Account(entity.getUid(), null, entity.getAccountType(), entity.getBalance());
    }

    private static AccountDTO toAccount(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(account.getBalance());
        accountDTO.setUid(account.getUid());
        return accountDTO;
    }

    public static Address toAddress(AddressDTO addressDTO) {
        return new Address(addressDTO.getStreet(), addressDTO.getZipCode(), addressDTO.getCity(), addressDTO.getCountry());
    }

    public static AddressDTO toDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setZipCode(address.getZipCode());
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());

        return addressDTO;
    }

    public static Holder toHolder(HolderDTO holderDTO) {
        if(holderDTO.getAddress() == null)
            return new Holder(null, holderDTO.getLastName(), holderDTO.getFirstName(), null,
                holderDTO.getBirthDate(), Collections.emptyList());
        else
            return new Holder(null, holderDTO.getLastName(), holderDTO.getFirstName(), toAddress(holderDTO.getAddress()),
                    holderDTO.getBirthDate(), Collections.emptyList());
    }

    private static List<AccountDTO> toAccounts(List<Account> accounts) {
        if(accounts == null) {
            return Collections.emptyList();
        }

        // Call toAccount method with java 8 stream
        return null;
    }

    private static List<Account> toAccount(List<AccountEntity> entities) {
        if(entities == null) {
            return Collections.emptyList();
        }
        // Call toAccount method with java 8 stream
        return null;
    }

}
