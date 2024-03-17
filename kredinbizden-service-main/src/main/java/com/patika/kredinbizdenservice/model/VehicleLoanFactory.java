package com.patika.kredinbizdenservice.model;

import java.math.BigDecimal;

public class VehicleLoanFactory implements LoanFactory{
    @Override
    public Loan createLoan(BigDecimal amount, Integer installment, Double interestRate) {
        return new VehicleLoan(amount, installment, interestRate);
    }
}
