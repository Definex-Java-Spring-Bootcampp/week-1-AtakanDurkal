package com.patika.kredinbizdenservice.model;


import com.patika.kredinbizdenservice.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Application {

    private Loan loan;
    private Product product;
    private User user;
    private LocalDateTime localDateTime;
    private ApplicationStatus applicationStatus;

    private Application() {
    }

    /*
    public Application(CreditCard creditCard, User user, LocalDateTime localDateTime) {
        this.creditCard = creditCard;
        this.user = user;
        this.localDateTime = localDateTime;
        this.applicationStatus = ApplicationStatus.INITIAL;
    }*/

    public Application(Product product, User user, LocalDateTime localDateTime) {
        this.product = product;
        this.user = user;
        this.localDateTime = localDateTime;
        this.applicationStatus = ApplicationStatus.INITIAL;
    }

    public Application(Loan loan, User user, LocalDateTime localDateTime) {
        this.loan = loan;
        this.user = user;
        this.localDateTime = localDateTime;
        this.applicationStatus = ApplicationStatus.INITIAL;
    }
    public static User findMostAppliedUser(List<Application> applications) {
        if (applications.isEmpty()) {
            return null;
        }
        // Başvuru listesindeki tüm kullanıcıları birleştiriyoruz Distinctle map ediyorum ki ayni isimler tekrar etmesin
        List<User> users = applications.stream()
                .map(Application::getUser)
                .distinct()
                .toList();

        // En çok başvuru yapan kullanıcıyı buluyoruz
        return users.stream()
                .max(Comparator.comparingInt(user -> countApplications(user, applications)))
                .orElse(null);
    }
    public static List<Application> getUserApplicationsByEmail(String email, List<Application> applications) {
        List<Application> userApplications = new ArrayList<>();

        for (Application application : applications) {
            if (application.getUser().getEmail().equals(email)) {
                userApplications.add(application);
            }
        }

        return userApplications;
    }
    private static int countApplications(User user, List<Application> applications) {
        return (int) applications.stream()
                .filter(app -> app.getUser().equals(user))
                .count();
    }
    public static User findUserWithHighestLoanAmount(List<Application> applications){
        if(applications.isEmpty()){
            return null;
        }
        Application applicationWithHighestLoanAmount =applications.stream().max(Comparator.comparing(Application::getLoanAmount)).orElse(null);
        if (applicationWithHighestLoanAmount != null) {
            return applicationWithHighestLoanAmount.getUser();
        } else {
            return null;
        }
    }

    private static double getLoanAmount(Application application) {
        if (application != null && application.getLoan() != null) {
            return application.getLoan().getAmount().doubleValue();
        } else {
            return 0.0;
        }
    }

    public static List<Application> findLastMonthApplications(List<Application> applications) {
        List<Application> lastMonthApplications = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthAgo = now.minusMonths(1);//zamani onceki aya ayarladik
        for (Application application : applications) {
            if (application.getLocalDateTime().isAfter(oneMonthAgo)) {
                lastMonthApplications.add(application);
            }
        }
        return lastMonthApplications;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public String toString() {
        return "Application{" +
                "loan=" + loan +
                ", user=" + user +
                ", localDateTime=" + localDateTime +
                ", applicationStatus=" + applicationStatus +
                '}';
    }
}
