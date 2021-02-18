package fr.paris8.iutmontreuil.mysmallbank.account.domain;

import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Address;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HolderService {

    private final HolderRepository holderRepository;

    public HolderService(HolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }

    public Address updateAddress(String id, Address address) {
        Holder holder = holderRepository.getHolder(id);
        Holder nouveau_holder = new Holder(null,null,null,address,null,null);
        holder = holder.merge(nouveau_holder);

        List<ValidationError> validationErrors = validateHolder(holder);
        if (!validationErrors.isEmpty()) {
            throw new ValidationErrorException(validationErrors);
        }
        return holderRepository.create(holder).getAddress();
    }

    public Holder getHolder(String id) {
        return holderRepository.getHolder(id);
    }

    public List<Holder> listHolders() {
        return holderRepository.listHolders();
    }

    public Holder create(Holder holder) {
        List<ValidationError> validationErrors = validateHolder(holder);
        if (!validationErrors.isEmpty()) {
            throw new ValidationErrorException(validationErrors);
        }

        return holderRepository.create(holder);
    }

    public Holder update(String id, Holder new_holder) {
        Holder holder = getHolder(id).merge(new_holder);
        List<ValidationError> validationErrors = validateHolder(holder);
        if (!validationErrors.isEmpty()) {
            throw new ValidationErrorException(validationErrors);
        }
        return holderRepository.create(holder);
    }

    private List<ValidationError> validateHolder(Holder holder) {
        List<ValidationError> listeErreurs = new ArrayList<ValidationError>();

        if (holder.getFirstName() == null || holder.getFirstName().length() == 0)
            listeErreurs.add(new ValidationError("prenom n'est pas renseigné"));
        if (holder.getLastName() == null || holder.getLastName().length() == 0)
            listeErreurs.add(new ValidationError("nom de famille n'est pas renseigné"));

        if (holder.getBirthDate() == null)
            listeErreurs.add(new ValidationError("date de naissance n'est pas renseignée"));
        else if (holder.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            listeErreurs.add(new ValidationError("n'a pas 18 ans ou plus"));
        }

        if (holder.getAddress() == null)
            listeErreurs.add(new ValidationError("l'adresse n'est pas renseignée"));
        else if (holder.getAddress().estIncomplet()) {
            listeErreurs.add(new ValidationError("adresse incomplet"));
        }


        return listeErreurs;
    }

}
