package com.example.jobportalbackend.model.entity;

import com.example.jobportalbackend.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import jakarta.persistence.*;
import org.hibernate.id.factory.IdentifierGeneratorFactory;


@Entity
public class Employer extends User {

    @Column
    private String companyName;

    @Column
    private String companyDescription = "No description";

    public Employer() {}

    public Employer(Long id, String username, String password,Role role, String companyName, String companyDescription) {
        super(id, username, password, role);
        this.companyName = companyName;
        this.companyDescription = companyDescription;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }


}
