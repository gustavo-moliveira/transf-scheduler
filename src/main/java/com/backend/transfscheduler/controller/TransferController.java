package com.backend.transfscheduler.controller;

import com.backend.transfscheduler.error.TransferValidationException;
import com.backend.transfscheduler.model.Transfer;
import com.backend.transfscheduler.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/transfers")
public class TransferController {
    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public List<Transfer> getAllTransfers() {
        return transferService.getAllTransfers();
    }

    @PostMapping
    public ResponseEntity<?> scheduleTransfer(@RequestBody Transfer transfer) {
        try {
            Transfer savedTransfer = transferService.scheduleTransfer(transfer);
            return ResponseEntity.ok(savedTransfer);
        } catch (TransferValidationException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor.");
        }
    }

}
