package sample;

import java.text.DecimalFormat;
import java.util.UUID;

public class Student {
    public String Fname;
    public String Lname;
    public String major;
    public int age;
    public double gpa;
    public UUID id;

    @Override
    public String toString(){ //formating gpa to only two decimals: https://stackoverflow.com/questions/14678523/formatting-a-decimal-within-a-tostring-method
        DecimalFormat format = new DecimalFormat("#.00");
        return (this.Fname + " / " + this.Lname + " / " + this.major + " / " + this.age + " / " + format.format(this.gpa) + " / " +this.id);
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
    public double getGpa() { return gpa; }
    public UUID getId() { return id; }
}
