package edu.ccrm.config;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Student;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataStore {
   private static DataStore ds;

   private final List<Student> studs;
   private final List<Course> crss;
   private final List<Enrollment> enrlls;
   private final List<Instructor> insts;

   private DataStore(){
    studs=new CopyOnWriteArrayList<>();
    crss=new CopyOnWriteArrayList<>();
    enrlls=new CopyOnWriteArrayList<>();
    insts=new CopyOnWriteArrayList<>();
   }

   public static synchronized DataStore getInstance(){
    if(ds==null){ ds=new DataStore(); }
    return ds;
   }

   public List<Student> getStudents(){ return studs; }
   public List<Course> getCourses(){ return crss; }
   public List<Enrollment> getEnrollments(){ return enrlls; }
   public List<Instructor> getInstructors(){ return insts; }
}
