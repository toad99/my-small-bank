package fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure;

import fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferDAO extends JpaRepository<TransferEntity, String> {

}
