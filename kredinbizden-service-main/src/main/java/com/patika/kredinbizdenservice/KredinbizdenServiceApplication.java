package com.patika.kredinbizdenservice;

import com.patika.kredinbizdenservice.enums.SectorType;
import com.patika.kredinbizdenservice.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.patika.kredinbizdenservice.enums.ApplicationStatus;
import com.patika.kredinbizdenservice.enums.LoanType;

import com.patika.kredinbizdenservice.model.Campaign;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


import static com.patika.kredinbizdenservice.model.Application.findLastMonthApplications;
import static com.patika.kredinbizdenservice.model.Application.getUserApplicationsByEmail;

@SpringBootApplication
public class KredinbizdenServiceApplication {

    public static void main(String[] args) {
        //once 4.soruyu yapmaya baslayip sonra 3e gecince factory design patterni entegre etmem zor oldu ama en azindan coktan olusturduklarimi bozmadan
        //Factory design patterni ayrica Loan classi icin kullanmaya calistim.Haftaici dolu oldugu icin anca yetistirebildim alttaki islemi yaptiktan sonra
        // digerleri icin de yapabilecegimi dusunuyorum.
        //3.Soru Factory kullanimi
        LoanFactory consumerLoanFactory = LoanFactoryProvider.getFactory(LoanType.IHTIYAC_KREDISI);
        Loan consumerLoan = consumerLoanFactory.createLoan(BigDecimal.valueOf(10000), 12, 0.1);
    //    System.out.println("İhtiyaç Kredisi: " + consumerLoan);

        // Konut kredisi oluşturulması
        LoanFactory houseLoanFactory = LoanFactoryProvider.getFactory(LoanType.KONUT_KREDISI);
        Loan houseLoan = houseLoanFactory.createLoan(BigDecimal.valueOf(200000), 120, 0.08);

   //     System.out.println("Konut Kredisi: " + houseLoan);
        /* 4.1- Ilk madde icin lutfen mail adresini ayni yapin hata mesajini alirsiniz Hash ile user->email yapisinda tutulabilirdi ve
        ContainsKey methodu ile hashmapte o usera ait email degilse hata mesaji dondurulebilirdi ama bu sekilde de calisiyor.
        */
        /* 4.2- Ikinci madde icin debuglarsaniz gorebilirsiniz hashlendigini mesela password1 icin
         hash = bc547750b92797f955b36112cc9bdd5cddf7d0862151d03a167ada8995aa24a9ad24610b36a68bc02da24141ee51670aea13ed6469099a4453f335cb239db5da oluyor.
        */
        User user1 = new User("Atakan", "Durkal", LocalDate.of(1990, 5, 15), "atakan.drkl@gmail.com", "password", "1234567890", true);
        User user2 = new User("Cem", "Dirman", LocalDate.of(1985, 10, 20), "cem.drm@gmail.com", "password1", "9876543210", true);
        User user3 = new User("Mehmet", "Koyuncu", LocalDate.of(1983, 8, 25), "cem1.drm@gmail.com", "password2", "5555555555", true);

        /*4.3- Bir application dizisi olusturalim ve en cok basvuru yapan useri donelim
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
        //System.out.println("En çok başvuru yapan kullanıcı: " + mostAppliedUser.getName());

    /*4.4-
    */
        User userWithHighestLoanAmount = Application.findUserWithHighestLoanAmount(applications);
        if (userWithHighestLoanAmount != null) {
            //System.out.println("En yüksek kredi isteyen kullanıcı: " + userWithHighestLoanAmount.getName() +"   "+userWithHighestLoanAmount.getSurname());

        } else {
          //  System.out.println("Hiçbir kullanıcı kredi başvurusu yapmamış.");
        }

    /* 4.5-Bunun icin bir ay oncesine ait bir basvuru ekleyelim ve onun sonucumuzda listelenmedigini gorelim.
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
          //  System.out.println("Gecen ayki basvurular "+application.getLocalDateTime().format(DateTimeFormatter.ISO_DATE));
        }

        /*4.6-Once alakali kredi kartlarini olusturalim ve methodu cagiralim
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

        // 3.Soru BankService sınıfından bir örnek oluşturalım ve singleton kullanalim
        Bank bank = Bank.getInstance();
        bank.setName("KredinBizDen Bankası");

        // BankService üzerinden listCreditCardsByCampaignCount methodunu çağırıp sonucu yazdıralım
        List<CreditCard> sortedCreditCards = bank.listCreditCardsByCampaignCount(creditCardList);
        for (CreditCard card : sortedCreditCards) {
          //  System.out.println(card);
        }

        /* 4.7-parametre olarak mail adresi ve Listeyi gonderiyorum
         */
        User userNew1 = new User("Zeki", "Temiz", LocalDate.of(1990, 5, 15), "zekitmz@gmail.com", "password123", "5555555555", true);
        User userNew2 = new User("Fehime", "Nizam", LocalDate.of(1985, 8, 21), "fehime.nizam@gmail.com", "password456", "5556666666", true);

        // Örnek başvurular
        Application application1 = new Application(new VehicleLoan(), userNew1, LocalDateTime.now());
        Application application2 = new Application(new HouseLoan(), userNew1, LocalDateTime.now());
        Application application3 = new Application(new ConsumerLoan(), userNew2, LocalDateTime.now());
        Application application4 = new Application(new ConsumerLoan(), userNew2, LocalDateTime.now());

        List<Application> newApplications = new ArrayList<>();
        newApplications.add(application1);
        newApplications.add(application2);
        newApplications.add(application3);
        newApplications.add(application4);

        String userEmail = "zekitmz@gmail.com";

        List<Application> userApplications = getUserApplicationsByEmail(userEmail, newApplications);

        System.out.println("Kullanıcının Başvuruları:");
        for (Application application : userApplications) {
         //   System.out.println(application);
        }

        /*
            Son soru ve şartları aşağıdadır.
         */
        // Musteri olusturma
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Ali", "Ali.patika@gmail.com","Vatan caddesi,ömür apt,no:14,sevim sokak",22));
        customers.add(new Customer("Cem", "Cem.patika@gmail.com","Vatan caddesi,ömür apt,no:14,sevim sokak",25));
        customers.add(new Customer("Mehmet", "Mehmet.patika@gmail.com","Vatan caddesi,ömür apt,no:14,sevim sokak",26));
        customers.add(new Customer("Serdar", "Serdar.patika@gmail.com", "Vatan caddesi,ömür apt,no:14,sevim sokak",32));
        customers.add( new Customer("Cem", "cem@example.com", "Istanbul",26));
        customers.add(new Customer("Serdar", "Serdar.patika@gmail.com", "Vatan caddesi,ömür apt,no:14,sevim sokak",27));



        Customer customer1 = new Customer("Cem", "cem@example.com", "Istanbul",43);
        Customer customer2 = new Customer("John", "john@example.com", "Ankara",53);
        customers.add(customer1);
        customers.add(customer2);



        // Siparişler oluştur
        Order order1 = new Order(customer1);
        order1.addProduct(new ConcreteProduct("Product 1", 100));
        order1.addProduct(new ConcreteProduct("Product 2", 150));

        Order order2 = new Order(customer2);
        order2.addProduct(new ConcreteProduct("Product 3", 200));

        //Toplam musteri sayisini bul
        int totalCustomerCount = Customer.getTotalCustomerCount();
        System.out.println(totalCustomerCount);

        // İsmi Cem olan müşterilerin aldıkları ürün sayısını bul
        int totalProductCountForCem = Customer.getTotalProductCountForCustomer(Customer.getCustomers(), "Cem");
        System.out.println("Total product count for customers named Cem: " + totalProductCountForCem);



        // İsmi Cem olup yaşı 30’dan küçük 25’ten büyük müşterilerin toplam alışveriş tutarını hesapla
        //Hocam bunu yapamadim vaktinizi harcamayin isterseniz
        
        BigDecimal totalAmount = Customer.calculateTotalAmountForCustomersBetweenAge("Cem",25,30);
        System.out.println("Total amount for customers named Cem between the ages of 25 and 30: " + totalAmount);


        SpringApplication.run(KredinbizdenServiceApplication.class, args);
    }


}
