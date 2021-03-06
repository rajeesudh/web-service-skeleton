package uk.co.mruoc.app.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.api.CustomerDto;
import uk.co.mruoc.api.CustomerNotFoundErrorDto;
import uk.co.mruoc.api.ErrorDto;
import uk.co.mruoc.api.Regex;
import uk.co.mruoc.app.facade.CustomerFacade;
import uk.co.mruoc.app.model.CustomerNotFoundException;

import javax.validation.constraints.Pattern;
import java.util.Optional;

@RestController
@Validated
public class GetCustomersController {

    @Autowired
    private final CustomerFacade facade;

    public GetCustomersController(CustomerFacade facade) {
        this.facade = facade;
    }

    @RequestMapping(value = "/customers/{accountNumber}", method = RequestMethod.GET)
    @ApiOperation(value = "View a specific customer specified by account number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved customer", response = CustomerDto.class),
            @ApiResponse(code = 400, message = "Invalid account number", response = ErrorDto.class),
            @ApiResponse(code = 404, message = "Customer not found", response = CustomerNotFoundErrorDto.class)
    })
    public CustomerDto getCustomer(@PathVariable @Pattern(regexp = Regex.ACCOUNT_NUMBER) String accountNumber) {
        Optional<CustomerDto> customer = facade.getCustomer(accountNumber);
        if (customer.isPresent()) {
            return customer.get();
        }
        throw new CustomerNotFoundException(accountNumber);
    }

}
