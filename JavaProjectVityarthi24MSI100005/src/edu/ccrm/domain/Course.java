package edu.ccrm.domain;

public class Course {
  private final String cde;
   private final String ttl;
 private final int crdts;
   private final String dept;
  private final Semester sem;
   private Instructor inst;

   private Course(Builder b){
    this.cde=b.cde;
    this.ttl=b.ttl;
    this.crdts=b.crdts;
    this.dept=b.dept;
    this.sem=b.sem;
    this.inst=b.inst;
   }

   public String getCode(){return cde;}
   public String getTitle(){return ttl;}
   public int getCredits(){return crdts;}
   public String getDepartment(){return dept;}
   public Semester getSemester(){return sem;}
   public Instructor getInstructor(){return inst;}

   public void assignInstructor(Instructor i){
    this.inst=i;
   }

   public String getDetailedInfo(){
    String iname=(inst!=null)?inst.getFullName()+" (ID: "+inst.getId()+")":"Not Yet Assigned";
    return String.format(
     "--- Course Details ---\n"+
     "Code:       %s\n"+
     "Title:      %s\n"+
     "Credits:    %d\n"+
     "Department: %s\n"+
     "Semester:   %s\n"+
     "Instructor: %s\n"+
     "----------------------",
     cde,ttl,crdts,dept,sem.name(),iname
    );
   }

   @Override
   public String toString(){
    String iname=(inst!=null)?inst.getFullName():"TBD";
    return String.format("Course{Code='%s', Title='%s', Credits=%d, Dept='%s', Instructor='%s'}",
     cde,ttl,crdts,dept,iname);
   }

   public static class Builder{
    private final String cde;
    private final String ttl;
    private final int crdts;
    private String dept="General";
    private Semester sem=Semester.FALL;
    private Instructor inst=null;

    public Builder(String c,String t,int cr){
     this.cde=c; this.ttl=t; this.crdts=cr;
    }
    public Builder department(String d){this.dept=d; return this;}
    public Builder semester(Semester s){this.sem=s; return this;}
    public Builder instructor(Instructor i){this.inst=i; return this;}
    public Course build(){return new Course(this);}
   }
}
