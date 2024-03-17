package com.patika.kredinbizdenservice.model;

import java.math.BigDecimal;

public interface LoanFactory {
    Loan createLoan(BigDecimal amount, Integer installment, Double interestRate);
}