package com.backend.transfscheduler.service;

import com.backend.transfscheduler.error.TransferValidationException;
import com.backend.transfscheduler.model.Transfer;
import com.backend.transfscheduler.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TransferService {
    private final TransferRepository transferRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    private static final double[] TAX_RATES = {2.5, 0.0, 8.2, 6.9, 4.7, 1.7};
    private static final int[] DAYS_RANGES = {0, 10, 20, 30, 40, 50};

    @Autowired
    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public Transfer scheduleTransfer(Transfer transfer) {
        validateTransfer(transfer);

        double fee = calculateFee(transfer.getTransferDate(), transfer.getAmount());
        double valueWithFee = transfer.getAmount() - (transfer.getAmount() * fee / 100.0);

        transfer.setFee(fee);

        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        transfer.setAmount(Double.parseDouble(df.format(valueWithFee)));

        transfer.setSchedulingDate(new Date());

        Transfer savedTransfer = transferRepository.save(transfer);

        logger.info("Transfer scheduled successfully: {}", savedTransfer);

        return savedTransfer;
    }

    void validateTransfer(Transfer transfer) {
        if (transfer.getAmount() <= 0) {
            throw new TransferValidationException("O valor da transferência deve ser maior que zero.", HttpStatus.BAD_REQUEST);
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate transferLocalDate = transfer.getTransferDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (transferLocalDate.isBefore(currentDate)) {
            throw new TransferValidationException("A data de transferência deve ser a partir de hoje.", HttpStatus.BAD_REQUEST);
        }
    }

    private double calculateFee(Date transferDate, double transferAmount) {
        LocalDate currentDate = LocalDate.now();
        LocalDate transferLocalDate = transferDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long days = Math.abs(ChronoUnit.DAYS.between(currentDate, transferLocalDate));

        int index = 0;
        while (index < DAYS_RANGES.length && days > DAYS_RANGES[index]) {
            index++;
        }

        if (index < TAX_RATES.length) {
            double fee = transferAmount > 0 ? TAX_RATES[index] : 0.0;
            logger.info("Calculated fee: {}", fee);
            return fee;
        } else {
            throw new RuntimeException("Não há taxa aplicável para esta transferência.");
        }
    }
}
