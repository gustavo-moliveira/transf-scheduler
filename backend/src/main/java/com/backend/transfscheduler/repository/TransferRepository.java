package com.backend.transfscheduler.repository;

import com.backend.transfscheduler.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
