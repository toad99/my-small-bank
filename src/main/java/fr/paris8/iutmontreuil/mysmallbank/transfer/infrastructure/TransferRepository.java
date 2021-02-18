package fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.transfer.TransferMapper;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Transfer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransferRepository {

    TransferDAO transferDAO;

    public TransferRepository(TransferDAO transferDAO) {
        this.transferDAO = transferDAO;
    }

    public Transfer getTransfer(String id) {
        return TransferMapper.toTransfer(transferDAO.getOne(id));
    }

    public Transfer create(Transfer transfer){
        TransferEntity entityToSave = TransferMapper.toTransferEntity(transfer);
        TransferEntity createdTransfer = transferDAO.save(entityToSave);
        return TransferMapper.toTransfer(createdTransfer);
    }

    public List<Transfer> getAll() {
        List<TransferEntity> all = transferDAO.findAll();
        return all.stream()
                .map(TransferMapper::toTransfer)
                .collect(Collectors.toList());
    }
}
