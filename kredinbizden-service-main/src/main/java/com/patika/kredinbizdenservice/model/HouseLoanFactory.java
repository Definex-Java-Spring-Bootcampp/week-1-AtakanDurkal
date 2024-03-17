package com.patika.kredinbizdenservice.model;

import java.math.BigDecimal;

public class HouseLoanFactory implements LoanFactory{
    @Override
    public Loan createLoan(BigDecimal amount, Integer installment, Double interestRate) {
        return new HouseLoan(amount, installment, interestRate);}
}
