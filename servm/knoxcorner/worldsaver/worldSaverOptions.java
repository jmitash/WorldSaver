/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servm.knoxcorner.worldsaver;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
/**
 *
 * @author JSM
 */
public class worldSaverOptions {

    private static final Logger log = Logger.getLogger("worldSaverOptions");
    static File WorldSaver;
    static File options;
    static String temppath;
    static String runSaveString;
    static String runBackupString;
    static String hourbackupString;
    static String saveminuteString;
    static String currentPath;
    static File backup;
    static String saveplugin;
    static String savepluginall;
    static String playerlist[];
    static String autosavetemp;
    static Player playerops[];
    static int tempint;
    static File excludeworlds;
    static int worldint;
    //static String[] worldname;
    static boolean end = false;
    static String junk;
    static String exworldstemp;
    static String expluginstemp;
    static boolean isboolean[];
    
    


    
    
/*
public static void loadOptions() throws FileNotFoundException {
    currentPath = getCurrentPath();
    options = new File(currentPath + File.separator + "plugins" + File.separator + "WorldSaver" + File.separator + "Options.txt");
    excludeworlds = new File(currentPath + File.separator + "plugins" + File.separator + "WorldSaver" + File.separator + "excludeworlds.txt");


    if (options.exists() == true){


try {
            
            BufferedReader inputStream = new BufferedReader(new FileReader(options));
            
            
            String versiontemp = inputStream.readLine();
                        if (versiontemp.equalsIgnoreCase(worldSaver.version) == false) {
                            if (inputStream != null) {inputStream.close();}
            log.log(Level.INFO, "[WorldSaver] Options.txt is outdated, deleting");
            options.delete();
            excludeworlds.delete();
            log.log(Level.INFO, "[WorldSaver] All options set back to default");
            worldSaver.optionstodefault();
            saveOptions();
            sendToOps("Outdated options file found, options set back to default");
            log.log(Level.INFO, "[WorldSaver] Outdated options file, options set back to default");
            return;
            } else {
            String runSave = inputStream.readLine();
            String runBackup = inputStream.readLine();
            String saveminute = inputStream.readLine();
            String backuphour = inputStream.readLine();
            String savepluginS = inputStream.readLine();
            String savepluginallS = inputStream.readLine();
            String manageautosaveS = inputStream.readLine();
            String exworldsR = inputStream.readLine();
            String expluginsR = inputStream.readLine();
            runSave = runSave.replaceAll("=Run save routine", "");
            runBackup = runBackup.replaceAll("=Run Backup routine", "");
            saveminute = saveminute.replaceAll("=Interval to save worlds (in minutes)", "");
            backuphour = backuphour.replaceAll("=Interval to backup (in hours)", "");
            savepluginS = savepluginS.replaceAll("=Backup the data folders in the plugins folder", "");
            savepluginallS = savepluginallS.replaceAll("=Back up the entire plugins folder", "");
            manageautosaveS = manageautosaveS.replaceAll("=Let WorldSaver manage the default save commands", "");
            exworldsR = exworldsR.replaceAll("=Check the exclude worlds file", "");
            expluginsR = expluginsR.replaceAll("=Check the exclude plugins file", "");
            int t3 = 0;
            /*System.out.println("runSave");
            isboolean[0] = checkIfBoolean(runSave); 
            System.out.println("runBackup");
                    isboolean[1] = checkIfBoolean(runBackup);
                    System.out.println("savepluginS");
                        isboolean[2] = checkIfBoolean(savepluginS);
                        System.out.println("savepallS");
                            isboolean[3] = checkIfBoolean(savepluginallS);
                            System.out.println("manageautosaveS");
                                isboolean[4] = checkIfBoolean(manageautosaveS);
                                System.out.println("exworldsR");
                                    isboolean[5] = checkIfBoolean(exworldsR);
                                    System.out.println("expluginsR");
                                        isboolean[6] = checkIfBoolean(expluginsR);
             for (boolean tempb : isboolean) {
            if (tempb == false) {
                sendToOps("Corrupted options file found, options set back to default");
                worldSaver.optionstodefault();
                log.log(Level.INFO, "[WorldSaver] Corrupted options file, options set back to default");
                System.out.println(tempb);
                return;
                    } else {}
             }
            
            try {
            worldSaver.runSaveRoutine = Boolean.parseBoolean(runSave);
            worldSaver.runBackupRoutine = Boolean.parseBoolean(runBackup);
            worldSaver.saveminute = Integer.parseInt(saveminute);
            worldSaver.hourbackup = Integer.parseInt(backuphour);
            worldSaver.savePluginFolder = Boolean.parseBoolean(savepluginS);
            worldSaver.savePluginFolderAll = Boolean.parseBoolean(savepluginallS);
            worldSaver.autosave = Boolean.parseBoolean(manageautosaveS);
            worldSaver.cExworlds = Boolean.parseBoolean(exworldsR);
            } catch (NumberFormatException nfe) {
            options.delete();
            log.log(Level.SEVERE, "[WorldSaver] Error loading integers (NumberFormatException)");
            log.log(Level.SEVERE, "[WorldSaver] Options.txt may have been tampered with.");
            log.log(Level.SEVERE, "[WorldSaver] You will need to reset the settings");
            System.out.println(nfe);
            sendToOps("Setting need to be reset");
            }

            
            
            
            
            if (inputStream != null) {
                inputStream.close();
            }
                        }

} catch (IOException e) {     
    log.log(Level.SEVERE, "[WorldSaver] Caught IOException: " + e.getMessage());
    return;

        
    }
} else {log.log(Level.INFO, "[WorldSaver] No options found, building config...");
    saveOptions();
    }
    
    if (excludeworlds.exists() == true) {
        try {
            BufferedReader exwis = new BufferedReader(new FileReader(excludeworlds));
            Boolean finished = false;
            int tempint4 = 0;
            String tempstring = "";
            String[] worldlist = null;
            while (!finished) {
                tempstring = exwis.readLine();
                System.out.println(tempstring);
                if (tempstring == null) {break;}
                if (tempstring.startsWith("//")) {}
                else if (tempstring.startsWith(";")) {worldlist[tempint4] = tempstring.replace(";", ""); tempint4++;}
                else if (tempstring == null) {printWorlds(); finished = true; break;}
            }
            
            
            

           

           
            
        } catch (IOException ioe) {System.out.println("[WorldSaver] Caught IOException " + ioe + " while trying to write exludeworlds");
        } 
    } else {
        try {BufferedWriter exwos = new BufferedWriter(new FileWriter(excludeworlds));
        exwos.write("//Any world names found in this file will be excluded from the backup of WorldSaver");
        exwos.newLine();
        exwos.write("//There should only be lines that start with a ';' followed by the name of the world, '//' to ignore what is on that line,");
        exwos.newLine();
        exwos.write("//or '*' to mark the end of the list. If it starts with anything else or is an empty line (highlight to see), an error will be returned");
        exwos.newLine();
        exwos.write("//To exclude a world, type it's name EXACTLY as it is in the folder and start with a ';'");
        exwos.newLine();
        exwos.write("//At the end of the list, put  a '*' on the next line");
        exwos.newLine();
        exwos.write("//You may now put world names, do not skip ANY lines");
        exwos.newLine();
        exwos.write("//----------------------------------");
        exwos.newLine();
        exwos.write(";ExampleWorld (You may remove this, it's just an example.");
        exwos.newLine();
        exwos.write("*");
        exwos.newLine();
        exwos.write("//There should be nothing past this. If there is, it will not be read.");
        exwos.close();
        } catch (IOException ioe) {System.out.println("[WorldSaver] Caught IOException " + ioe + " while trying to write exludeworlds");
        }
    }
    
}


public static void saveOptions() {
   currentPath = getCurrentPath();
    options = new File(currentPath + File.separator + "plugins" + File.separator+ "WorldSaver" + File.separator +  "Options.txt");
    WorldSaver = new File(currentPath + File.separator + "plugins" + File.separator + "WorldSaver");
    backup = new File(currentPath + File.separator + "backups");

    int saveminuteTemp = worldSaver.saveminute;
    int backuphourTemp = worldSaver.hourbackup;
    hourbackupString = Integer.toString(backuphourTemp);
    saveminuteString = Integer.toString(saveminuteTemp);
        if (backup.exists() == false) {
    backup.mkdir();
    }
    
    
    if (options.exists() == true) {
        if (options.canWrite() == true) {
            options.delete();
            
        } else {log.log(Level.SEVERE, "[WorldSaver] No access to Options.txt"); return;}
        

        }
    else if (WorldSaver.exists() == false) {
    WorldSaver.mkdir();

    }
    try {
       BufferedWriter outputStream = new BufferedWriter(new FileWriter(options));
        
        if (worldSaver.runSaveRoutine == true) {
        runSaveString = "true";}
        else {runSaveString = "false";}
        if (worldSaver.runBackupRoutine == true) {
        runBackupString = "true";}
        else {runBackupString = "false";}
        if (worldSaver.savePluginFolder == true) {
        saveplugin = "true";}
        else {saveplugin = "false";}
        if (worldSaver.savePluginFolderAll == true) {
        savepluginall = "true";}
        else {savepluginall = "false";}
        if (worldSaver.autosave == true) {
        autosavetemp = "true";
        } else {autosavetemp = "false";}
        if (worldSaver.cExworlds == true) {
        exworldstemp = "true";
        } else {exworldstemp = "false";}
        if (worldSaver.cExPlugins == true) {
        expluginstemp = "true";
        } else {expluginstemp = "false";}
        //outputStream.

        outputStream.write(worldSaver.version);
        outputStream.newLine();
        outputStream.write(runSaveString + "=Run save routine");
        outputStream.newLine();
        outputStream.write(runBackupString + "=Run Backup routine");
        outputStream.newLine();
        outputStream.write(saveminuteString + "=Interval to save worlds (in minutes)");
        outputStream.newLine();
        outputStream.write(hourbackupString + "=Interval to backup (in hours)");
        outputStream.newLine();
        outputStream.write(saveplugin + "=Backup the data folders in the plugins folder");
        outputStream.newLine();
        outputStream.write(savepluginall + "=Back up the entire plugins folder");
        outputStream.newLine();
        outputStream.write(autosavetemp + "=Let WorldSaver manage the default save commands");
        outputStream.newLine();
        outputStream.write(exworldstemp + "=Check the exclude worlds file");
        outputStream.newLine();
        outputStream.write(expluginstemp + "=Check the exclude plugins file");
        outputStream.close();
        
    }
    catch (IOException e) {
    log.log(Level.SEVERE, "[WorldSaver] (While writing) Caught IOException " + e);
    }
}
*/
public static String getCurrentPath()
{
    try {
    temppath = new File(".").getCanonicalPath();
    } catch (IOException e) {
        log.log(Level.INFO, "[WorldSaver] Could not get current path. Error " + e);
        return null;
    }
    return temppath;
}
/*

public static void getOps() {
    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
        if (players.isOp()) {
        tempint++;
        playerops[tempint] = players;
        }
    }


}

public static void sendToOps(String message) {
    tempint = 0;
    getOps();
    int tempint2 = 0;
    while (tempint2 != tempint) {
    playerops[tempint2].chat(ChatColor.RED + "[W.Saver][Ops] " + message);
    tempint2++;
    }
    
}

public static boolean checkIfBoolean(String bool) {
    try {
    if (bool.equalsIgnoreCase("true")) {return true;}
    else if (bool.equalsIgnoreCase("false")) {return true;}
    else {System.out.println("Test: " + bool); return false;}
    
    } catch (NullPointerException npe) {return false;}
    
    }

/*public static void printWorlds() {
    for (String worldname : worldlist){
            System.out.println(tworldname);
            
            }
        
        }*/


}


    

