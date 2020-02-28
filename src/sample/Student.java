package sample;

import java.util.UUID;

public class Student {
    public String Fname;
    public String Lname;
    public String major;
    public int age;
    public double gpa;
    public UUID id;


    public String getFName() {
        return Fname;
    }
    public String getLName() {
        return Lname;
    }
    public String getMajor() {
        return major;
    }
    public int getAge() { return age; }
    public double getGpa() { return gpa; }
    public UUID getId() { return id; }
}
