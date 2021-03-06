package uk.co.mruoc.app.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.mruoc.api.CustomerDto;
import uk.co.mruoc.api.CustomerDtoConverter;
import uk.co.mruoc.app.model.Customer;
import uk.co.mruoc.app.model.CustomerAlreadyExistsException;
import uk.co.mruoc.app.model.CustomerConverter;
import uk.co.mruoc.app.model.CustomerRepository;

import java.util.Optional;

@Component
public class DefaultCustomerFacade implements CustomerFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCustomerFacade.class);

    private final CustomerDtoConverter dtoConverter = new CustomerDtoConverter();

    @Autowired
    private final CustomerConverter modelConverter;

    @Autowired
    private CustomerRepository repository;

    public DefaultCustomerFacade(CustomerRepository repository, CustomerConverter modelConverter) {
        this.repository = repository;
        this.modelConverter = modelConverter;
    }

    @Override
    public Optional<CustomerDto> getCustomer(String accountNumber) {
        LOGGER.info("get customer with account number " + accountNumber);

        Optional<Customer> customer = repository.findByAccountNumber(accountNumber);
        if (!customer.isPresent()) {
            LOGGER.info(String.format("customer with account number %s not found", accountNumber));
            return Optional.empty();
        }

        CustomerDto dto = modelConverter.toDto(customer.get());
        LOGGER.info(String.format("returning customer %s", dtoConverter.toJson(dto)));
        return Optional.of(dto);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        LOGGER.info(String.format("creating customer %s", dtoConverter.toJson(dto)));

        String accountNumber = dto.getAccountNumber();
        Optional<Customer> customer = repository.findByAccountNumber(accountNumber);
        if (customer.isPresent()) {
            LOGGER.info(String.format("customer with id %s already exists", accountNumber));
            throw new CustomerAlreadyExistsException(accountNumber);
        }

        Customer customerToCreate = modelConverter.toModel(dto);
        Customer createdCustomer = repository.create(customerToCreate);
        return modelConverter.toDto(createdCustomer);
    }

}
