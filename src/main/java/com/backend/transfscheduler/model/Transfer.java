package com.backend.transfscheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sourceAccount;
    private String destinationAccount;
    private double amount;
    private double fee;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Sao_Paulo")
    private Date transferDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private Date schedulingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        if (sourceAccount == null || sourceAccount.trim().isEmpty()) {
            throw new RuntimeException("A conta de origem não pode ser nula ou vazia.");
        }
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        if (destinationAccount == null || destinationAccount.trim().isEmpty()) {
            throw new RuntimeException("A conta de destino não pode ser nula ou vazia.");
        }
        this.destinationAccount = destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new RuntimeException("O valor da transferência não pode ser negativo.");
        }
        this.amount = amount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        if (fee < 0) {
            throw new RuntimeException("A taxa da transferência não pode ser negativa.");
        }
        this.fee = fee;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Date getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(Date schedulingDate) {
        this.schedulingDate = schedulingDate;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", sourceAccount='" + sourceAccount + '\'' +
                ", destinationAccount='" + destinationAccount + '\'' +
                ", amount=" + amount +
                ", fee=" + fee +
                ", transferDate=" + transferDate +
                ", schedulingDate=" + schedulingDate +
                '}';
    }
}
