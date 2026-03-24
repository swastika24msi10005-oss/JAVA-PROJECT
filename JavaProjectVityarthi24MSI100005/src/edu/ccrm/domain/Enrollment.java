package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
   private final Student stud;
 private final Course crs;
    private final LocalDateTime dt;
   private Grade grd;

   public Enrollment(Student s,Course c){
    this.stud=s;
     this.crs=c;
      this.dt=LocalDateTime.now();
   this.grd=null;
   }

   public Student getStudent(){return stud;}
   public Course getCourse(){return crs;}
   public LocalDateTime getEnrollmentDate(){return dt;}
   public Grade getGrade(){return grd;}
   public void setGrade(Grade g){this.grd=g;}

   @Override
   public String toString(){
     return String.format("Enrollment[Student: %s, Course: %s, Grade: %s]",
      stud.getFullName(),crs.getCode(),(grd!=null?grd:"Not Graded"));
   }
}
