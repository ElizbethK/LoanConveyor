package ru.neostudy.loanConveyorProject.deal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.neostudy.loanConveyorProject.conveyor.dto.LoanApplicationRequestDTO;
import ru.neostudy.loanConveyorProject.deal.entity.Client;
import ru.neostudy.loanConveyorProject.deal.repository.ClientRepository;

@Service
public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        logger.info("Конструктор ClientService");
        this.clientRepository = clientRepository;

    }

    public Client createClient(LoanApplicationRequestDTO loanApplicationRequestDTO){
        logger.info("Запуск метода createClient");

        logger.info("Создание экземпляра Client");
        Client client = new Client();

        logger.info("Определение полей client через поля loanApplicationRequestDTO");
        client.setLastName(loanApplicationRequestDTO.getLastName());
        client.setFirstName(loanApplicationRequestDTO.getFirstName());
        client.setMiddleName(loanApplicationRequestDTO.getMiddleName());
        client.setBirthDate(loanApplicationRequestDTO.getBirthdate());
        client.setEmail(loanApplicationRequestDTO.getEmail());

        logger.info("Сохранение client в БД");
        clientRepository.save(client);

        logger.info("Возвращение созданного client");
        return client;
    }
}
