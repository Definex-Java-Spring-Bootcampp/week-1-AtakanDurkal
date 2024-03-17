package com.patika.kredinbizdenservice.model;

import com.patika.kredinbizdenservice.enums.LoanType;

import static com.patika.kredinbizdenservice.enums.LoanType.IHTIYAC_KREDISI;

public class LoanFactoryProvider {
    public static LoanFactory getFactory(LoanType loanType) {
        switch (loanType) {
            case IHTIYAC_KREDISI:
                return new ConsumerLoanFactory();
            case KONUT_KREDISI:
                return new HouseLoanFactory();
            case  ARAC_KREDISI:
                return new VehicleLoanFactory();
            default:
                throw new IllegalArgumentException("Geçersiz kredi türü: " + loanType);
        }
    }
}
