package ru.neostudy.loanConveyorProject.conveyor.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.neostudy.loanConveyorProject.conveyor.dto.LoanApplicationRequestDTO;
import ru.neostudy.loanConveyorProject.conveyor.dto.LoanOfferDTO;
import ru.neostudy.loanConveyorProject.conveyor.service.LoanOfferService;
import ru.neostudy.loanConveyorProject.conveyor.util.LoanApplicationErrorResponse;
import ru.neostudy.loanConveyorProject.conveyor.util.LoanApplicationNotCreatedException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/conveyor")
public class ConveyorController {



    @PostMapping("/offers")
    public List<LoanOfferDTO> getPossibleOffers(@RequestBody @Valid LoanApplicationRequestDTO loanApplicationRequestDTO,
                                                BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors ){
               errorMsg.append(error.getField())
                       .append(" - ").append(error.getDefaultMessage())
                       .append(";");
            }
            throw new LoanApplicationNotCreatedException(errorMsg.toString());
        }

        LoanOfferDTO loanOfferDTO = new LoanOfferDTO(loanApplicationRequestDTO);
        LoanOfferService loanOfferService = new LoanOfferService();
        return loanOfferService.createLoanOffers(loanOfferDTO);
    }

   // @PostMapping("/calculation")

    /*


    POST: /conveyor/calculation - валидация присланных данных + скоринг данных + полный расчет параметров кредита.
    Request - ScoringDataDTO, response CreditDTO.
     */

    @ExceptionHandler
    private ResponseEntity<LoanApplicationErrorResponse> handleException(
            LoanApplicationNotCreatedException e){
        LoanApplicationErrorResponse response = new LoanApplicationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}