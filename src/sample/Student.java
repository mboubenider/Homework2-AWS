package sample;

import java.util.UUID;

public class Student {
    private String Fname;
    private String Lname;
    private String major;
    private int age;
    private float gpa;
    private UUID id;

    Student(String Fname, String Lname, String major, int age, float gpa, UUID id){
        this.Fname = Fname;
        this.Lname = Lname;
        this.major = major;
        this.age = age;
        this.gpa = gpa;
        this.id = id;
    }

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
    public float getGpa() { return gpa; }
    public UUID getId() { return id; }
}
