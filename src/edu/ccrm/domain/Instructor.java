package edu.ccrm.domain;

import java.util.UUID;

public class Instructor extends Person {
    private final UUID id;
    private String department;

    public Instructor(Name fullName, String email, String department) {
        super(fullName, email);
        this.id = UUID.randomUUID();
        this.department = department;
    }

    public UUID getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String getDetails() {
        return "Instructor{" +
                "id=" + id +
                ", fullName=" + getFullName() +
                ", email='" + getEmail() + "'" +
                ", department='" + department + "'" +
                '}';
    }
}
