package edu.ccrm.domain;

import java.time.LocalDate;

public class Instructor extends Person {
  private String dept;

  public Instructor(int id,String nm,String mail,LocalDate dob,String dept){
   super(id,nm,mail,dob);
   this.dept=dept;
  }

  public String getDepartment(){
   return dept;
  }

  @Override
  public String getDetailedProfile(){
   return String.format("--- Instructor Profile ---\nDepartment: %s\n%s",dept,super.toString());
  }
}
