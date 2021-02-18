package fr.paris8.iutmontreuil.mysmallbank.account.exposition;

import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.HolderService;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Address;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AddressDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.HolderDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/holders")
public class HolderController {

    private final HolderService holderService;

    public HolderController(HolderService holderService) {
        this.holderService = holderService;
    }

    @GetMapping
    public List<HolderDTO> findAll() {
        return HolderMapper.toDto(holderService.listHolders());
    }

    @GetMapping("/{id}")
    public HolderDTO getOne(@PathVariable("id") String id) {
        return HolderMapper.toDTO(holderService.getHolder(id));
    }

    @PostMapping
    public HolderDTO create(@RequestBody HolderDTO holderDTO) {
        Holder holder = holderService.create(HolderMapper.toHolder(holderDTO));
        return HolderMapper.toDTO(holder);
    }

    @PutMapping("{id}/address")
    public AddressDTO updateAddress(@PathVariable("id") String id,@RequestBody AddressDTO addressDTO) {
        Address address = holderService.updateAddress(id,HolderMapper.toAddress(addressDTO));
        return HolderMapper.toDTO(address);
    }

    @PatchMapping("/{id}")
    public HolderDTO update(@PathVariable("id") String id, @RequestBody HolderDTO new_holder) {
        Holder holder = HolderMapper.toHolder(new_holder);
        holder = holderService.update(id,holder);
        return HolderMapper.toDTO(holder);
    }
}
