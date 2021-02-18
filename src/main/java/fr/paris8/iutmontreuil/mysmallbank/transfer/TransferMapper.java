package fr.paris8.iutmontreuil.mysmallbank.transfer;

import fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure.TransferEntity;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Transfer;
import fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto.TransferDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TransferMapper {

    private TransferMapper() { }

    public static TransferDTO toDTO(Transfer transfer) {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setId(transfer.getId());
        transferDTO.setAccountIdFrom(transfer.getAccountIdFrom());
        transferDTO.setAccountIdTo(transfer.getAccountIdTo());
        transferDTO.setAmount(transfer.getAmount());
        transferDTO.setExecutionDate(transfer.getExecutionDate());
        return transferDTO;
    }

    public static Transfer toTransfer(TransferEntity transferEntity){
        return new Transfer(transferEntity.getUid(),transferEntity.getAccountIdFrom(),
                transferEntity.getAccountIdTo(),transferEntity.getAmount(),transferEntity.getExecutionDate());
    }

    public static Transfer toTransfer(TransferDTO transferDTO){
        return new Transfer(transferDTO.getId(),transferDTO.getAccountIdFrom(),
                transferDTO.getAccountIdTo(),transferDTO.getAmount(),transferDTO.getExecutionDate());
    }

    public static TransferEntity toTransferEntity(Transfer transfer) {
        TransferEntity transferEntity = new TransferEntity();
        transferEntity.setUid(transfer.getId());
        transferEntity.setAccountIdFrom(transfer.getAccountIdFrom());
        transferEntity.setAccountIdTo(transfer.getAccountIdTo());
        transferEntity.setAmount(transfer.getAmount());
        transferEntity.setExecutionDate(transfer.getExecutionDate());
        return transferEntity;
    }

    public static List<TransferEntity> toTransferEntities(List<Transfer> transfer) {
        return transfer.stream()
                .map(x->toTransferEntity(x))
                .collect(Collectors.toList());
    }

}
