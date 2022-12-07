package com.shree.treasurer;

public class Investor_list {
    String investor_name;

    public String getInvestor_name() {
        return investor_name;
    }

    public void setInvestor_name(String investor_name) {
        this.investor_name = investor_name;
    }

    public String getInvestor_contact() {
        return investor_contact;
    }

    public void setInvestor_contact(String investor_contact) {
        this.investor_contact = investor_contact;
    }

    public String getInvestor_email() {
        return investor_email;
    }

    public void setInvestor_email(String investor_email) {
        this.investor_email = investor_email;
    }

    public String getInvestor_website() {
        return investor_website;
    }

    public void setInvestor_website(String investor_website) {
        this.investor_website = investor_website;
    }

    public Investor_list(String investor_name, String investor_contact, String investor_email, String investor_website) {
        this.investor_name = investor_name;
        this.investor_contact = investor_contact;
        this.investor_email = investor_email;
        this.investor_website = investor_website;
    }

    String investor_contact;
    String investor_email;
    String investor_website;
}
