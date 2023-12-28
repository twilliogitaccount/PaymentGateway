package com.redbus.operator.service;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.entity.TicketCost;
import com.redbus.operator.payload.BusOperatorDto;
import com.redbus.operator.repository.BusOperatorRepository;
import com.redbus.user.repository.TicketCostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class BusOperatorServiceImpl implements BusOperatorService{

    private BusOperatorRepository busOperatorRepository;

    private ModelMapper mapper;

    private TicketCostRepository ticketCostRepository;

    public BusOperatorServiceImpl(BusOperatorRepository busOperatorRepository,
                                  TicketCostRepository ticketCostRepository,
                                  ModelMapper mapper) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository = ticketCostRepository;
        this.mapper = mapper;
    }

    @Override
    public BusOperatorDto scheduleBus(BusOperatorDto busOperatorDto) {
        BusOperator busOperator = mapToEntity(busOperatorDto);

        //create a new ticketcost entity and set its properties
        TicketCost ticketCost = new TicketCost();
        ticketCost.setTicketId(busOperatorDto.getTicketCost().getTicketId());
        ticketCost.setCost(busOperatorDto.getTicketCost().getCost());
        ticketCost.setCode(busOperatorDto.getTicketCost().getCode());
        ticketCost.setDiscountAmount(busOperatorDto.getTicketCost().getDiscountAmount());

        //Set the TicketCost entity in the BusOperator entity
        busOperatorDto.setTicketCost(ticketCost);


        //Generate a unique busId
        String busId = UUID.randomUUID().toString();
        busOperator.setBusId(busId);
        BusOperator savedBusSchedule = busOperatorRepository.save(busOperator);
        return mapToDto(savedBusSchedule);
    }

    BusOperator mapToEntity(BusOperatorDto busOperatorDto){
        BusOperator busOperator = mapper.map(busOperatorDto, BusOperator.class);
        return busOperator;
    }

    BusOperatorDto mapToDto(BusOperator busOperator){
        BusOperatorDto busOperatorDto = mapper.map(busOperator, BusOperatorDto.class);
        return busOperatorDto;

    }
}
