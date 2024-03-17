package com.patika.kredinbizdenservice.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Bank {

    private String name;
    private List<Loan> loanList;
    private List<CreditCard> creditCards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }

    public List<CreditCard> listCreditCardsByCampaignCount(List<CreditCard> creditCards) {
        List<CreditCard> crd = creditCards.stream()
                .sorted(Comparator.comparingInt(card -> card.getCampaignList().size()))
                .collect(Collectors.toList());
        return crd.reversed();
    }


    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", loanList=" + loanList +
                '}';
    }
}
