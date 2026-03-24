package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {
   protected int pid;
   protected String name;
   protected String mail;
   protected LocalDate dob;

   public Person(int pid,String name,String mail,LocalDate dob){
    this.pid=pid;
    this.name=name;
    this.mail=mail;
    this.dob=dob;
   }

   public abstract String getDetailedProfile();

   public int getId(){return pid;}
   public String getFullName(){return name;}
   public void setFullName(String n){this.name=n;}
   public String getEmail(){return mail;}
   public void setEmail(String m){this.mail=m;}
   public LocalDate getDateOfBirth(){return dob;}

   @Override
   public String toString(){
    return "ID="+pid+", Name='"+name+"', Email='"+mail+"'";
   }
}
