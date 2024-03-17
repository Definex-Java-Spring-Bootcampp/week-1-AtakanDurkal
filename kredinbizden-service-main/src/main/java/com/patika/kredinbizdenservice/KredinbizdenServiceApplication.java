package com.patika.kredinbizdenservice;

import com.patika.kredinbizdenservice.enums.SectorType;
import com.patika.kredinbizdenservice.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.patika.kredinbizdenservice.enums.ApplicationStatus;

import com.patika.kredinbizdenservice.model.Campaign;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


import static com.patika.kredinbizdenservice.model.Application.findLastMonthApplications;

@SpringBootApplication
public class KredinbizdenServiceApplication {

    public static void main(String[] args) {


        /* 1- Ilk madde icin lutfen mail adresini ayni yapin hata mesajini alirsiniz Hash ile user->email yapisinda tutulabilirdi ve
        ContainsKey methodu ile hashmapte o usera ait email degilse hata mesaji dondurulebilirdi ama bu sekilde de calisiyor.
        */
        /* 2- Ikinci madde icin debuglarsaniz gorebilirsiniz hashlendigini mesela password1 icin
         hash = bc547750b92797f955b36112cc9bdd5cddf7d0862151d03a167ada8995aa24a9ad24610b36a68bc02da24141ee51670aea13ed6469099a4453f335cb239db5da oluyor.
        */
        User user1 = new User("Atakan", "Durkal", LocalDate.of(1990, 5, 15), "atakan.drkl@gmail.com", "password", "1234567890", true);
        User user2 = new User("Cem", "Dirman", LocalDate.of(1985, 10, 20), "cem.drm@gmail.com", "password1", "9876543210", true);
        User user3 = new User("Mehmet", "Koyuncu", LocalDate.of(1983, 8, 25), "cem1.drm@gmail.com", "password2", "5555555555", true);

        /*3- Bir application dizisi olusturalim ve en cok basvuru yapan useri donelim
        */

        User user4 = new User("Ahmet", "Ekinci", "ahmetEkin@gmail.com", "password123", "5551234567", true);
        User user5 = new User("Ayşe", "Ozum", "deneme@gmail.com", "password123212311", "5551234567", true);

        Product product1 = new VehicleLoan();
        Product product2 = new HouseLoan() ;
        Product product3 = new ConsumerLoan();

        LocalDateTime localDateTime1 = LocalDateTime.now();


        Application apply1 = new Application(product1,user4, localDateTime1);
        Application apply2 = new Application(product2,user4, localDateTime1);
        Application apply3 = new Application(product3,user5, localDateTime1);

        apply1.setLoan(new VehicleLoan(new BigDecimal("20000"), 24, 1.1));
        apply2.setLoan(new HouseLoan(new BigDecimal("30000"), 36, 1.2));
        apply3.setLoan(new ConsumerLoan(new BigDecimal("43000"), 48, 1.3));

        apply1.setApplicationStatus(ApplicationStatus.DONE);
        apply2.setApplicationStatus(ApplicationStatus.DONE);
        apply3.setApplicationStatus(ApplicationStatus.DONE);

        List<Application> applications = new ArrayList<>();
        applications.add(apply1);
        applications.add(apply2);
        applications.add(apply3);


        User mostAppliedUser = Application.findMostAppliedUser(applications);
        System.out.println("En çok başvuru yapan kullanıcı: " + mostAppliedUser.getName());

    /*4-
    */
        User userWithHighestLoanAmount = Application.findUserWithHighestLoanAmount(applications);
        if (userWithHighestLoanAmount != null) {
            System.out.println("En yüksek kredi isteyen kullanıcı: " + userWithHighestLoanAmount.getName() +"   "+userWithHighestLoanAmount.getSurname());

        } else {
            System.out.println("Hiçbir kullanıcı kredi başvurusu yapmamış.");
        }

    /* 5-Bunun icin bir ay oncesine ait bir basvuru ekleyelim ve onun sonucumuzda listelenmedigini gorelim.
    */
        LocalDateTime localDateTime2 = LocalDateTime.of(2023, 3, 17, 12, 30, 0);//geçen seneye ait tarih
        User user7 = new User("Hasan", "Surmene", "Hasansrmn@gmail.com", "password123", "45656434353", true);
        Product product4 = new ConsumerLoan();
        Application apply4 = new Application(product4,user7, localDateTime2);
        apply4.setApplicationStatus(ApplicationStatus.DONE);
        applications.add(apply4);
        apply4.setLoan(new ConsumerLoan(new BigDecimal("20000"), 48, 1.3));

        //simdi applications listesini findLastMonthApplications methoduna yollayalim
        List<Application> lastMonthApplications = findLastMonthApplications(applications);
        for (Application application : lastMonthApplications) {
            System.out.println("Gecen ayki basvurular "+application.getLocalDateTime().format(DateTimeFormatter.ISO_DATE));
        }

        /*6-Once alakali kredi kartlarini olusturalim ve methodu cagiralim
         */
        List<Campaign> campaignList1 = new ArrayList<>();
        List<String> newlist = new ArrayList<>();

        Campaign cmp = new Campaign("Campaign 1", "Content 1", LocalDate.now(), LocalDate.now(), LocalDate.now(), SectorType.MARKET);

        campaignList1.add(new Campaign("Campaign 1", "Content 1", LocalDate.now(), LocalDate.now(), LocalDate.now(), SectorType.MARKET));
        campaignList1.add(new Campaign("Campaign 2", "Content 2", LocalDate.now(), LocalDate.now(), LocalDate.now(), SectorType.MARKET));

        List<Campaign> campaignList2 = new ArrayList<>();
        campaignList2.add(new Campaign("Campaign 3", "Content 3", LocalDate.now(), LocalDate.now(), LocalDate.now(), SectorType.MARKET));

        CreditCard creditCard1 = new CreditCard(BigDecimal.valueOf(100), campaignList1);
        CreditCard creditCard2 = new CreditCard(BigDecimal.valueOf(200), campaignList2);

        // CreditCard'ları bir listeye koyalım
        List<CreditCard> creditCardList = new ArrayList<>();
        creditCardList.add(creditCard1);
        creditCardList.add(creditCard2);

        // BankService sınıfından bir örnek oluşturalım
        Bank bank = new Bank();

        // BankService üzerinden listCreditCardsByCampaignCount methodunu çağırıp sonucu yazdıralım
        List<CreditCard> sortedCreditCards = bank.listCreditCardsByCampaignCount(creditCardList);
        for (CreditCard card : sortedCreditCards) {
            System.out.println(card);
        }
        SpringApplication.run(KredinbizdenServiceApplication.class, args);
    }


}
