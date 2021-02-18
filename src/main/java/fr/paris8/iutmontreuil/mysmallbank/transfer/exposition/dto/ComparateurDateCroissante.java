package fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto;

import fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto.TransferDTO;

import java.util.Comparator;

public class ComparateurDateCroissante implements Comparator<TransferDTO> {

    @Override
    public int compare(TransferDTO o1, TransferDTO o2) {
        return o1.getExecutionDate().compareTo(o2.getExecutionDate());
    }
}
