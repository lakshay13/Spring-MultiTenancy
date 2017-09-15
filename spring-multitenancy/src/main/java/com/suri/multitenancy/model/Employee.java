package com.suri.multitenancy.model;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * @Id annotation indicates it is the primary key of the current entity.
 * @GeneratedValue annotation indicates the way the field should be incremented
 */
@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EMP_ID")
    private Long empId;

    @Column(name = "EMP_NAME")
    private String name;

    public Employee() {

    }

    public Employee(String empName) {
        this.name = empName;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!empId.equals(employee.empId)) return false;
        return name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        int result = empId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                '}';
    }
}
