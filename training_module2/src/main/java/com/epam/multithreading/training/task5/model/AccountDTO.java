package com.epam.multithreading.training.task5.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountDTO {
    private String uuid;
    private String ssn;
    private String fullName;

    private LocalDate dob;
    private List<BankAccountDTO> bankAccounts = new ArrayList<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<BankAccountDTO> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccountDTO> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "uuid='" + uuid + '\'' +
                ", ssn='" + ssn + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dob=" + dob +
                ", bankAccounts=" + bankAccounts +
                '}';
    }
}
