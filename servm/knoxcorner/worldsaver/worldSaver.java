/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servm.knoxcorner.worldsaver;

/**
 *
 * @author JSM
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.permissions.Permission.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;



       
    

public class worldSaver extends JavaPlugin implements CommandExecutor  {
    boolean saveExecuted = false;
    public static boolean runSaveRoutine;
    public static boolean runBackupRoutine;
    static String command = "1337";
    public static int hourbackup = 6;
    public static int saveminute = 10;
    Plugin worldsaver = this;
    public int saveticks;
    int tempmin;
    boolean gaveInvalidNumberMin;
    boolean gaveInvalidNumberHour;
    int temphour;
    int task1 = -1;
    int task2 = -1;
    int backupticks;
    public static boolean savePluginFolder = true;
    public static boolean savePluginFolderAll = true;
    String arguments;
    String permrequester;
    static CommandSender cstemp;
    static boolean autosave = true;
    final static String version = "v1.3";
    static boolean origsave;
    static boolean cExworlds = true;
    static boolean cExPlugins = true;
    static List wlist;
    static List plist;
    static boolean bconsave = true;
    static boolean bconbu = true;
    

        private static final Logger log = Bukkit.getServer().getLogger();
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        cstemp = cs;
        try {
        command = strings[0];
        if (strings[0] == null) {sendHelp();}
        } catch (ArrayIndexOutOfBoundsException err) {sendHelp(); command = "1337";}

        
       
        permrequester = cs.getName();
        if ( cs.isOp() == true) { // =)try now
      
        if (command.equalsIgnoreCase("forcesave") == true) {
            
            cs.sendMessage(ChatColor.LIGHT_PURPLE + "Executing save");
            new saveRunner().run();//looks all done
            command = "1337";
        } else if (command.equalsIgnoreCase("servertime") == true) {
            cs.sendMessage(ChatColor.LIGHT_PURPLE + scheduler.currentTime());
            return true;
        } else  if (command.equalsIgnoreCase("saveroutine") == true) {
            
            cs.sendMessage(ChatColor.LIGHT_PURPLE + "Run save routine = " + runSaveRoutine);
            setSaveRoutine();
            log.log(Level.INFO, "[WorldSaver]Run save routine = " + runSaveRoutine + " (Changed by " + permrequester + ")");
            return true;
            
        } else  if (command.equalsIgnoreCase("backuproutine") == true) {
            setBackupRoutine();
            cs.sendMessage((ChatColor.LIGHT_PURPLE + "Run backup routine = " + runBackupRoutine));
            log.log(Level.INFO, "[WorldSaver]Run backup routine = " + runBackupRoutine + " (Changed by " + permrequester + ")");
            return true;
        } else if (command.equalsIgnoreCase("help") == true) {
            try {arguments = strings[1];} catch (ArrayIndexOutOfBoundsException lol){sendHelp(); return true;}
            if (arguments.equalsIgnoreCase("2")) {sendHelp2();}
            else {sendHelp();}
            return true;
        } else if (command.equalsIgnoreCase("setbackuphour") == true) {
           try {
            arguments = strings[1];
             if (strings[1] == null) {cs.sendMessage(ChatColor.RED + "/ws setbackuphour [hour]");
             }
            } catch (ArrayIndexOutOfBoundsException err) {cs.sendMessage(ChatColor.RED + "/ws setbackuphour [hour]"); return true;}
            try {
            temphour = Integer.parseInt(arguments);
            } catch (NumberFormatException nfe) {
                cs.sendMessage(ChatColor.RED + arguments + " is not a valid number");
                gaveInvalidNumberHour = true;
                return true;
            }
           setBackupHour(temphour);
           cs.sendMessage(ChatColor.LIGHT_PURPLE + "Will backup every " +  hourbackup + " hours");
            log.log(Level.INFO, "[WorldSaver] Will backup every " + hourbackup + " hours");
            return true; 
         
        } else if (command.equalsIgnoreCase("setsaveminute") == true) {
                    try {
        arguments = strings[1];
        if (strings[1] == null) {sendHelp();}
        } catch (ArrayIndexOutOfBoundsException err) {cs.sendMessage(ChatColor.RED + "/ws setsaveminute [minute]"); return true;}
            try {
            tempmin = Integer.parseInt(arguments);
            } catch (NumberFormatException nfe) {
                cs.sendMessage(ChatColor.RED + arguments + " is not a valid number");
                gaveInvalidNumberMin = true;
                return true;
            }
            
            setSaveMinute(tempmin);
            cs.sendMessage(ChatColor.LIGHT_PURPLE + "Will save every " + saveminute + " minutes");
            log.log(Level.INFO, "[WorldSaver] Will save every " + saveminute + " minutes");
            return true;
        } else if (command.equalsIgnoreCase("saveoptions") == true) {
            saveConfiguration();
            cs.sendMessage(ChatColor.LIGHT_PURPLE + "Options saved");
            return true;
        } else if (command.equalsIgnoreCase("reloadoptions") == true) {
                loadConfiguration();
            cs.sendMessage(ChatColor.LIGHT_PURPLE + "Options reloaded");
            return true;
        } else if (command.equalsIgnoreCase("forcebackup")) {
            cs.sendMessage(ChatColor.LIGHT_PURPLE + "Executing backup");
             new saveScheduler().run();
             command = "1337";
             return true;
        } else if (command.equalsIgnoreCase("viewoptions")){
        cs.sendMessage(ChatColor.LIGHT_PURPLE + "Run Save Routine: " + runSaveRoutine + "   Run Backup Routine: " + runBackupRoutine);
        cs.sendMessage(ChatColor.LIGHT_PURPLE + "Save every " + saveminute + " minutes    " + "Backup every " + hourbackup + " hours");
        cs.sendMessage(ChatColor.LIGHT_PURPLE + "Backup plugins folder: " + savePluginFolder + "   Backup everything in plugins folder: " + savePluginFolderAll);
        cs.sendMessage(ChatColor.LIGHT_PURPLE + "Let worldsaver manage default world save options = " + autosave);
        }else if (command.equalsIgnoreCase("plugininfo")) { plugininfo();
        }else if (command.equalsIgnoreCase("autosavemanage")) { autoSaving();
        }else if (command.equalsIgnoreCase("optionsdefault")) { optionstodefault();
        }else if (command.equalsIgnoreCase("1337")) {return true;}
            
        else
            {
            sendHelp();
            return true;
        }
        
        
        
        } else {  
            cs.sendMessage(ChatColor.GRAY + "You don't have access to this command");
            log.log(Level.INFO,  "[WorldSaver]" + cs + " has tried to access " + command);
        }
        return true;
    }
    
    public void saveSchedule() {
        if (task1 == -1) {
        saveticks = (int) scheduler.mintoticks(saveminute);
        task1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new saveRunner(), saveticks, saveticks);
        }
        else {
            Bukkit.getScheduler().cancelTask(task1);
            saveticks = (int) scheduler.mintoticks(saveminute);
            task1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new saveRunner(), saveticks, saveticks);}
    }
    
        public void backupSchedule() {
        if (task2 == -1) {
        backupticks = (int) scheduler.hourstoticks(hourbackup);
        task2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new saveScheduler(), backupticks, backupticks);
        }
        else {
            Bukkit.getScheduler().cancelTask(task2);
            backupticks = (int) scheduler.hourstoticks(hourbackup);
            task2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new saveScheduler(), backupticks, backupticks);
        }
    }
    
    public void setSaveMinute(int tempsavemin) {
    saveminute = tempsavemin;
    saveConfiguration();
    saveSchedule();
    return;
    }
    
    public void setBackupHour(int tempbackuphour) {
    hourbackup = tempbackuphour;
    saveConfiguration();
    backupSchedule();
    }
    
    public void setSaveRoutine() {
        runSaveRoutine = !runSaveRoutine;
        saveConfiguration();
        return;
    }
    
     public void setBackupRoutine() {
     runBackupRoutine = !runBackupRoutine;
     saveConfiguration();
     return;
    }
     
     public void setSavePlugins() {
         savePluginFolder = !savePluginFolder;
         saveConfiguration();
         cstemp.sendMessage("Will save plugin folder data = " + savePluginFolder);
         return;
     }
     
     public void setSavePluginsAll() {
         savePluginFolderAll = !savePluginFolderAll;
         saveConfiguration();
         cstemp.sendMessage("Will save entire plugin folder (if saveplugins is enabled) = " + savePluginFolderAll);
         return;
     }
     public static void optionstodefault() {
     runSaveRoutine = true;
     runBackupRoutine = true;
     savePluginFolderAll = true;
     savePluginFolder = true;
     hourbackup = 6;
     saveminute = 10;
     autosave = true;
     cExworlds = true;
     cExPlugins = true;
     saveConfiguration();
     if (cstemp == null) {
     log.log(Level.INFO, "[WorldSaver] Options set back to default");} 
     else {
     cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "Options set back to default");
     log.log(Level.INFO, "[WorldSaver] Options set back to default");
     }
     }
     public void autoSaving() {
     autosave = !autosave;
     saveConfiguration();
     cstemp.sendMessage("WorldSaver will manage save options of the world: " + autosave);
     if (autosave == false) {
         aSave(origsave);
        } else {
            if (runSaveRoutine == true) { aSave(true);}
            else {aSave(origsave);}
        }
     }
     
     public void plugininfo() {
     cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "Plugin created by knoxcorner");
     cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "To report bugs/suggestions/feedback (all of which I beg to get), visit the link below");
     cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "Current version: " + version + ", tested with Bukkit[#1317], to check if there is a newer version,");
     cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "Visit goo.gl/HWbai");
     }
     
     public void sendHelp() {
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws help [page#] (Shows help for worldsaver)");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws forcesave (forces a world wide save)");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws forcebackup (forces a backup of the server");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws setsaveminute [#of min] (set the save interval (minutes))");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws setbackuphour [#of hours] (set the backup interval (hours))");//
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws saveroutine (toggles save routine on/off)");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws backuproutine (toggles backup routine on/off)");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws optionsdefault (Will set the options back to default)");
            
            return;

     }
     
     public void sendHelp2() {
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws servertime (returns the current time of the server)");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws saveoptions (Forces a save of WorldSaver's options)");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws reloadoptions (Reload's WorldSaver's options)");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws viewoptions (Shows current settings for WorldSaver)");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws plugininfo (Shows plugin details)");
            cstemp.sendMessage(ChatColor.LIGHT_PURPLE + "/ws autosavemanage (Will let worldsaver manage save options of a world)");
     }
     
     public void aSave(boolean truefalse) {
     for (World world : Bukkit.getServer().getWorlds()) {
        world.setAutoSave(truefalse);
     }
     
         
    
         }
    public void getOrigSave() {
        for (World tempworld : Bukkit.getServer().getWorlds()) {
        origsave = tempworld.isAutoSave();}
        }

    //end of oncommand
    @Override
    public void onDisable() {
        loadConfiguration();
        Bukkit.getScheduler().cancelTasks(worldsaver);
        log.log(Level.INFO, "[WorldSaver] disabled");  
    }

    @Override
    public void onEnable() {
        getOrigSave();
        loadConfiguration();
        aSave(autosave);
        saveSchedule();
        backupSchedule();
        log.log(Level.INFO, "[WorldSaver] enabled. Visit goo.gl/HWbai for plugin info");
        
    }
    
    public static void saveConfiguration() {
        //File configfile = new File(worldSaverOptions.getCurrentPath() + File.separator + "plugins" + File.separator + "WorldSaver" + File.separator + "config.yml");
        /*if (!configfile.exists()) {
            boolean oldrsr = runSaveRoutine;
            boolean oldrbr = runBackupRoutine;
            boolean oldspfa = savePluginFolderAll;
            boolean oldspf = savePluginFolder;
            boolean oldas = autosave;
            boolean oldcew = cExworlds;
            boolean oldcep = cExPlugins;
            List oldwl = wlist;
            List oldpl = plist;
            int oldhb = hourbackup;
            int oldsm = saveminute;
            loadConfiguration();
            runSaveRoutine = oldrsr;
            runBackupRoutine = oldrbr;
            savePluginFolderAll = oldspfa;
            savePluginFolder = oldspf;
            autosave = oldas;
            cExworlds = oldcew;
            cExPlugins = oldcep;
            wlist = oldwl;
            plist = oldpl;
            hourbackup = oldhb;
            saveminute = oldsm;
            }*/
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldSaver");
        
        String exworlds = "WorldSaver.Exclude_Worlds";
        String vpath = "WorldSaver.Version";
        String worlds[] = {"ExampleWorld"};
        String plugins[] = {"ExamplePlugin"};
        String ppath = "WorldSaver.Exclude_Plugins";
        String poptions = "WorldSaver.Options";
        FileConfiguration cf = plugin.getConfig();
        plugin.getConfig().set(exworlds, wlist);
        plugin.getConfig().set(vpath, version);
        plugin.getConfig().set(ppath, plist);
        plugin.getConfig().set(poptions + ".RunSaveRoutine", runSaveRoutine);
        plugin.getConfig().set(poptions + ".RunBackupRoutine", runBackupRoutine);
        plugin.getConfig().set(poptions + ".saveEntirePluginFolder", savePluginFolderAll);
        plugin.getConfig().set(poptions + ".savePluginData", savePluginFolder);
        plugin.getConfig().set(poptions + ".HoursBetweenBackups", hourbackup);
        plugin.getConfig().set(poptions + ".MinutesBetweenSaves", saveminute);
        plugin.getConfig().set(poptions + ".AutoSaveManage", autosave);
        plugin.getConfig().set(poptions + ".CheckExcludeWorlds", cExworlds);
        plugin.getConfig().set(poptions + ".CheckExcludePlugins", cExPlugins);
        plugin.getConfig().set(poptions + ".BroadcastOnSave", bconsave);
        plugin.getConfig().set(poptions + ".BroadcastOnBackup", bconbu);
        plugin.saveConfig();
        
        
        }
    
    
    
    public static void loadConfiguration() {
        File configfile = new File(worldSaverOptions.getCurrentPath() + File.separator + "plugins" + File.separator + "WorldSaver" + File.separator + "config.yml");
        File oldoptionsfile = new File(worldSaverOptions.getCurrentPath() + File.separator + "plugins" + File.separator + "WorldSaver" + File.separator + "Options.txt");
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldSaver");
        String exworlds = "WorldSaver.Exclude_Worlds";
        String vpath = "WorldSaver.Version";
        String pdispath = "WorldSaver.Disabled";
        String worlds[] = {"ExampleWorld"};
        String plugins[] = {"ExamplePlugin"};
        String ppath = "WorldSaver.Exclude_Plugins";
        String poptions = "WorldSaver.Options";
        //plugin.getConfig().addDefault(exworlds, worlds);
        plugin.getConfig().addDefault(exworlds, Arrays.asList(worlds));
        plugin.getConfig().addDefault(pdispath, false);
        plugin.getConfig().addDefault(vpath, version);
        plugin.getConfig().addDefault(ppath, Arrays.asList(plugins));
        plugin.getConfig().addDefault(poptions + ".RunSaveRoutine", true);
        plugin.getConfig().addDefault(poptions + ".RunBackupRoutine", true);
        plugin.getConfig().addDefault(poptions + ".saveEntirePluginFolder", true);
        plugin.getConfig().addDefault(poptions + ".savePluginData", true);
        plugin.getConfig().addDefault(poptions + ".HoursBetweenBackups", 6);
        plugin.getConfig().addDefault(poptions + ".MinutesBetweenSaves", 10);
        plugin.getConfig().addDefault(poptions + ".AutoSaveManage", true);
        plugin.getConfig().addDefault(poptions + ".CheckExcludeWorlds", true);
        plugin.getConfig().addDefault(poptions + ".CheckExcludePlugins", true);
        plugin.getConfig().addDefault(poptions + ".BroadcastOnSave", true);
        plugin.getConfig().addDefault(poptions + ".BroadcastOnBackup", true);
        if (configfile.exists()) {
        String tver = plugin.getConfig().getString(vpath);
        if (!tver.equalsIgnoreCase(version)) {
            log.log(Level.INFO, "[WorldSaver] Outdated config, deleting. Options will need to be reset.");
            configfile.delete();
            if (oldoptionsfile.exists()) {oldoptionsfile.delete(); System.out.println("[WorldSaver] Old options.txt file deleted");}
            loadConfiguration();
            optionstodefault();
            return;
        }
        FileConfiguration cf = plugin.getConfig();
        wlist = cf.getList(exworlds);
        plist = cf.getList(ppath);
        boolean disabled = cf.getBoolean("WorldSaver.Disabled");
        //if (disabled) {Bukkit.getServer().getPluginManager().disablePlugin(plugin); return;
        //}
        runSaveRoutine = cf.getBoolean(poptions + ".RunSaveRoutine");
        runBackupRoutine = cf.getBoolean(poptions + ".RunBackupRoutine");
        savePluginFolderAll = cf.getBoolean(poptions + ".saveEntirePluginFolder");
        savePluginFolder = cf.getBoolean(poptions + ".savePluginData");
        autosave = cf.getBoolean(poptions + ".AutoSaveManage");
        cExworlds = cf.getBoolean(poptions + ".CheckExcludeWorlds");
        cExPlugins = cf.getBoolean(poptions + ".CheckExcludePlugins");
        hourbackup = cf.getInt(poptions + ".HoursBetweenBackups");
        saveminute = cf.getInt(poptions + ".MinutesBetweenSaves");
        bconsave = cf.getBoolean(poptions + ".BroadcastOnSave");
        bconbu = cf.getBoolean(poptions + ".BroadcastOnBackup");
       
        
        
        
        if (!cExworlds) {
        wlist.clear();
        }
        if (!cExPlugins) {
        plist.clear();
        }
        
        
        
                
                }
        else {
        optionstodefault();
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        return;
        
        }
        
    }
    

    
    
    
}
