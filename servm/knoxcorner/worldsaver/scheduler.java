/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servm.knoxcorner.worldsaver;
import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author JSM
 */
public class scheduler {
    boolean Ptrue = false;
     static Calendar time = Calendar.getInstance();
     public static int hour = time.get(Calendar.HOUR_OF_DAY);
     public static int minute = time.get(Calendar.MINUTE);
     //public int second = time.get(Calendar.SECOND);
     public static int day = time.get(Calendar.DAY_OF_MONTH);
     public static int dayofweekint = time.get(Calendar.DAY_OF_WEEK);
     public static int month = time.get(Calendar.MONTH) + 1;
     public static int year = time.get(Calendar.YEAR);
     static long tickstemp;
     static long minstemp;
     int hourP;
     int minuteP;
     int dayP;
     int monthP;
     int yearP;
     static long hourstemp;
     public String dayOfWeek;
     private static boolean junk;
     
     
     
     public String monthtext(){
         if (month > -1 && month < 12 ){
         switch (month) {
             case (1):
                 return "January";
             case (2):
                 return "February";
             case (3):
                 return "March";
             case (4):
                 return "April";
             case (5):
                 return "May";
             case (6):
                 return "June";
             case (7):
                 return "July";
             case (8):
                 return "August";
             case (9):
                 return "September";
             case (10):
                 return "October";
             case (11):
                 return "November";
             case (12):
                 return "December";
             default:
                 return null;
         }
         } else {return null;}
            
             
     }
     
     public void regSave() {
         junk = forceSave.save();
     }
     

     
          public static void updateCalendar() {
     //hourP = hour;
     //minuteP = minute;
     //dayP = day;
     //monthP = month;
     //yearP = year;
      time = Calendar.getInstance();
      hour = time.get(Calendar.HOUR_OF_DAY);
      minute = time.get(Calendar.MINUTE);
      day = time.get(Calendar.DAY_OF_MONTH);
      month = time.get(Calendar.MONTH) + 1; 
      year = time.get(Calendar.YEAR);

     }
     
     public void saveRoutine() {
         if (worldSaver.runSaveRoutine = false) { }
         else {
         updateCalendar();
         
      
      
      
      
      
     
                 }

     
}
     public static long mintoticks(int mins) {
         tickstemp = mins * 1200;
         return tickstemp;
         
     }
     
     public static long tickstomin(long ticks) {
          minstemp = ticks / 1200;
          return minstemp;
     
     }
     
     public static long hourstoticks(int hours){
         tickstemp = hours * 72000;
         return tickstemp;
        }
     
     public static long tickstohours(int ticks){
        hourstemp = ticks / 72000;
        return hourstemp;
     }
     
     public static String currentTime() {
         updateCalendar();
         return "" + year + "_" + month + "_" + day + "--" + hour + "-" + minute;
         
     }

}

        
            
 
           
            


    

    

    

       





    

