package fr.paris8.iutmontreuil.mysmallbank.transfer.infrastructure;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
public class TransferEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "account_id_from")
    private String accountIdFrom;

    @Column(name = "account_id_to")
    private String accountIdTo;

    @Column(name = "amount")
    private double amount;

    @Column(name = "execution_date")
    private LocalDateTime executionDate;

    public String getUid() {
        return id;
    }

    public String getAccountIdFrom() {
        return accountIdFrom;
    }

    public String getAccountIdTo() {
        return accountIdTo;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public void setUid(String uid) {
        this.id = uid;
    }

    public void setAccountIdFrom(String accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public void setAccountIdTo(String accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
    }

}
