package com.socketprogramming.atestat;

public class PhoneNumber {

    private String personalPhoneNumber;
    private String companyPhoneNumber;
    private String customerServicePhoneNumber;

    public String getCustomerServicePhoneNumber() {
        return customerServicePhoneNumber;
    }
    public void setCustomerServicePhoneNumber(String customerServicePhoneNumber) {
        this.customerServicePhoneNumber = customerServicePhoneNumber;
    }
    public String getPersonalPhoneNumber() {
        return personalPhoneNumber;
    }
    public void setPersonalPhoneNumber(String personalPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
    }
    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }
    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public PhoneNumber(String personalPhoneNumber, String customerServicePhoneNumber, String companyPhoneNumber) {
        this.personalPhoneNumber = personalPhoneNumber;
        this.customerServicePhoneNumber = customerServicePhoneNumber;
        this.companyPhoneNumber = companyPhoneNumber;
    }


}
