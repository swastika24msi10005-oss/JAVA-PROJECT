package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {
 private static final Path EXP=Paths.get("data","exports");
 private static final Path BCK=Paths.get("data","backups");

 public void performBackup() throws IOException {
  if(!Files.exists(EXP) || Files.list(EXP).count()==0){
   System.out.println("No exports found to backup.");
   return;
  }

  String ts=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
  Path bdir=BCK.resolve("backup_"+ts);
  Files.createDirectories(bdir);

  try(var s=Files.walk(EXP,1)){
   s.filter(Files::isRegularFile).forEach(p->{
    try{
     Path d=bdir.resolve(p.getFileName());
     Files.copy(p,d,StandardCopyOption.REPLACE_EXISTING);
    }catch(IOException e){
     System.err.println("Copy failed: "+e.getMessage());
    }
   });
  }

  System.out.println("Backup done: "+bdir.toAbsolutePath());
 }
}
