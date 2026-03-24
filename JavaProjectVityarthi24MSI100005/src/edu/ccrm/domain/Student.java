package edu.ccrm.domain;

import java.time.LocalDate;

public class Student extends Person {
  private final String rno;

  public Student(int pid,String rno,String nm,String mail,LocalDate dob){
   super(pid,nm,mail,dob);
   this.rno=rno;
  }

  public String getRegNo(){
   return rno;
  }

  @Override
  public String getDetailedProfile(){
   return String.format("--- Student Profile ---\nRegistration No: %s\n%s",rno,super.toString());
  }
}
