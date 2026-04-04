package com.socketprogramming.atestat;

public class Email {

    private String personalEmail;
    private String companyEmail;
    private String customerServiceEmail;

    public String getPersonalEmail() {
        return personalEmail;
    }
    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }
    public String getCompanyEmail() {
        return companyEmail;
    }
    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }
    public String getCustomerServiceEmail() {
        return customerServiceEmail;
    }
    public void setCustomerServiceEmail(String customerServiceEmail) {
        this.customerServiceEmail = customerServiceEmail;
    }

    Email(String personalEmail, String companyEmail, String customerServiceEmail){
        this.personalEmail = personalEmail;
        this.companyEmail = companyEmail;
        this.customerServiceEmail = customerServiceEmail;
    }

}
