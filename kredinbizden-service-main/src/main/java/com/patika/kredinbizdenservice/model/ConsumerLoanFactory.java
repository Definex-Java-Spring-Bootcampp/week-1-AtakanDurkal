package com.patika.kredinbizdenservice.model;

import java.math.BigDecimal;

public class ConsumerLoanFactory implements LoanFactory {
    @Override
    public Loan createLoan(BigDecimal amount, Integer installment, Double interestRate) {
        return new ConsumerLoan(amount, installment, interestRate);
    }
}
