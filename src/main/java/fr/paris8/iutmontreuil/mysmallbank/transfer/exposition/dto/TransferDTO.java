package fr.paris8.iutmontreuil.mysmallbank.transfer.exposition.dto;

import java.time.LocalDateTime;

public class TransferDTO {

    private String id;
    private String accountIdFrom;
    private String accountIdTo;
    private double amount;
    private LocalDateTime executionDate;

    public TransferDTO() {

    }

    public TransferDTO(String accountIdFrom, String accountIdTo, double amount) {
        this.id = id;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
        this.executionDate = executionDate;
    }

    public TransferDTO(String id, String accountIdFrom, String accountIdTo, double amount,
                       LocalDateTime executionDate) {
        this.id = id;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.amount = amount;
        this.executionDate = executionDate;
    }

    public String getId() {
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

    public void setId(String id) {
        this.id = id;
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
