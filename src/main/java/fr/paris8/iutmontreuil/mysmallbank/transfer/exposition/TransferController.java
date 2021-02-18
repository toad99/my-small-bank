package fr.paris8.iutmontreuil.mysmallbank.transfer.exposition;

import fr.paris8.iutmontreuil.mysmallbank.transfer.TransferMapper;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.TransferService;
import fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto.ComparateurDateCroissante;
import fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto.ComparateurDateDecroissante;
import fr.paris8.iutmontreuil.mysmallbank.transfer.domain.model.Transfer;
import fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto.TransferDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/{id}")
    public TransferDTO getOne(@PathVariable("id") String id){
        return TransferMapper.toDTO(transferService.getTransfer(id));
    }

    @GetMapping
    public SortedSet<TransferDTO> getAll(@RequestBody Optional<String> ordre) {
        SortedSet<TransferDTO> transfers;
        if(ordre.isPresent() && ordre.get().equals("ASC"))
            transfers = new TreeSet<>(new ComparateurDateCroissante());
        else
            transfers = new TreeSet<>(new ComparateurDateDecroissante());

        List<TransferDTO> listeTransfers = transferService
                                        .getTransfers()
                                        .stream()
                                        .map(TransferMapper::toDTO)
                                        .collect(Collectors.toList());
        transfers.addAll(listeTransfers);
        return transfers;
    }

    @PostMapping
    public ResponseEntity<TransferDTO> createTransfer(@RequestBody TransferDTO transferDTO) throws URISyntaxException {
        Transfer tempo = new Transfer(transferDTO.getAccountIdFrom(),transferDTO.getAccountIdTo(),transferDTO.getAmount());
        Transfer createdTransfer = transferService.createTransfer(tempo);
        TransferDTO createdTransferDTO = TransferMapper.toDTO(createdTransfer);
        return ResponseEntity.created(new URI("/transfers/" + createdTransferDTO.getId())).body(createdTransferDTO);
    }



}
