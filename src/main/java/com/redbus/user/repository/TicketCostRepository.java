package com.redbus.user.repository;

import com.redbus.operator.entity.TicketCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCostRepository extends JpaRepository<TicketCost, String> {
}
