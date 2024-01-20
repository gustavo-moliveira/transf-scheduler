package com.backend.transfscheduler.service;

import com.backend.transfscheduler.model.Transfer;
import com.backend.transfscheduler.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @MockBean
    private TransferRepository transferRepository;

    @Test
    void testGetAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        when(transferRepository.findAll()).thenReturn(transfers);

        List<Transfer> result = transferService.getAllTransfers();

        assertEquals(transfers, result);
    }

    @Test
    void testScheduleTransfer() {
        Transfer transfer = new Transfer();
        transfer.setAmount(100.0);
        transfer.setTransferDate(new Date());

        when(transferRepository.save(any(Transfer.class))).thenReturn(transfer);

        Transfer result = transferService.scheduleTransfer(transfer);

        assertNotNull(result);
        assertEquals(97.5, result.getAmount()); // Adjust the expected value based on your calculation
        assertNotNull(result.getSchedulingDate());
        assertNotNull(result.getFee());
    }


    @Test
    void testInvalidTransferAmount() {
        Transfer transfer = new Transfer();
        transfer.setAmount(0.0);

        assertThrows(RuntimeException.class, () -> transferService.validateTransfer(transfer));
    }

    @Test
    void testInvalidTransferDate() {
        Transfer transfer = new Transfer();
        transfer.setAmount(100.0);
        transfer.setTransferDate(new Date(System.currentTimeMillis() - 86400000));

        assertThrows(RuntimeException.class, () -> transferService.validateTransfer(transfer));
    }

}
