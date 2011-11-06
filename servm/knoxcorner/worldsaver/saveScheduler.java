/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servm.knoxcorner.worldsaver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit.*;
import org.bukkit.ChatColor;
import java.lang.Object;
import org.bukkit.Bukkit;
import org.bukkit.World;
/**
 *
 * @author JSM
 */
public class saveScheduler implements Runnable{
    String tempfilepath = worldSaverOptions.getCurrentPath();
    String timestamp = scheduler.currentTime();
    File backupfolder = new File(tempfilepath + File.separator + "backups");
    File pluginfolder = new File(tempfilepath + File.separator + "plugins");
    boolean pluginsaved = false;
    
    String worldname;
    String filetesttemp;

    
    int x = 0;
    int y = 0;
  

    private static final Logger log = Logger.getLogger("saveScheduler");
    @Override
    public void run() {


        
        if (worldSaver.runBackupRoutine == true || worldSaver.command.equalsIgnoreCase("forcebackup")) {
            Bukkit.getServer().broadcastMessage(ChatColor.GREEN +"[WorldSaver] Backing up worlds, you may experience lag");
            log.log(Level.INFO, "[WorldSaver] Backing up worlds, WARNING: The server may stop responding, do not close!");
            saveRunner.waiting(1000);
            try {
                        for (World world : Bukkit.getServer().getWorlds()) {
                        worldname = world.getName();
                        //for (String worldtemp : worldSaverOptions.worldname) {
                        
                        if (!worldSaver.wlist.contains(worldname)) {
                        
                        
                        File worldfile = new File(tempfilepath + File.separator + worldname);
                        File backuptimestamp = new File(tempfilepath + File.separator + "backups" + File.separator + timestamp.replaceAll(":", "-"));
                        File backuptimestampworld = new File(tempfilepath + File.separator + "backups" + File.separator + timestamp.replaceAll(":", "-") + File.separator + worldname);
                        File backuptimestampplugins = new File(tempfilepath + File.separator + "backups" + File.separator + timestamp.replaceAll(":", "-") + File.separator + "plugins");
                        backuptimestamp.mkdir();
                        if (worldSaver.savePluginFolder == true) {
                        backuptimestampplugins.mkdir();
                        }
                        copyFolder(worldfile, backuptimestampworld);
                        if (!pluginsaved) {
                        pluginsaved = true;
                        if (worldSaver.savePluginFolder == true) {
                        File filetestarr[] = pluginfolder.listFiles();
                        
                        if (worldSaver.savePluginFolderAll == false) {
                        for (File filetest : filetestarr) {
                            if (filetest.isDirectory()) {
                            filetesttemp = filetest.getName();
                            File backuptimestampplugin = new File(tempfilepath + File.separator + "backups" + File.separator + timestamp.replaceAll(":", "-") + File.separator + "plugins" + File.separator + filetesttemp);
                            copyFolder(filetest, backuptimestampplugin);
                            } else {}
                
                
                        }
                        } else {
                        for (File filetest : filetestarr) {
                            filetesttemp = filetest.getName();
                            File backuptimestampplugin = new File(tempfilepath + File.separator + "backups" + File.separator + timestamp.replaceAll(":", "-") + File.separator + "plugins" + File.separator + filetesttemp);
                           
                            copyFolder(filetest, backuptimestampplugin);
                        }
                            //} else {System.out.println("Skipping plugin: " + filetesttemp);}
                            
                        }
                        } else {}
                        } else {}
                        } else {System.out.println("[WorldSaver] Skipping: " + worldname);}
                        
                        }
                        pluginsaved = false;
                        System.out.println("[WorldSaver] Backup Finished");
            } catch (IOException ex) {
                Logger.getLogger(saveScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }

    public static void copyFolder(File src, File dest)
    	throws IOException{
        boolean goodtocopy = true;
        String tpath = src.getCanonicalPath();
        for (Object tobj : worldSaver.wlist.toArray()) {
        String tstring = tobj.toString();
        if (tpath.contains(tstring)){goodtocopy = false; break;}
        }
        if (!goodtocopy) {System.out.println("[WorldSaver] Skipping plugin: " + src); return;}
        else {
 
    	if(src.isDirectory()){
 
    		
    		if(!dest.exists()){
    		   dest.mkdirs();
    		}
 
    		
    		String files[] = src.list();
 
    		for (String file : files) {
    		   
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   
    		   copyFolder(srcFile,destFile);
    		}
 
    	}else{
    		
    		
    		InputStream in = new FileInputStream(src);
    	        OutputStream out = new FileOutputStream(dest); 
                
                
    	        byte[] buffer = new byte[1024];
 
    	        int length;
    	        
    	        while ((length = in.read(buffer)) > 0){
                  
    	    	   out.write(buffer, 0, length);
    	        }
 
    	        in.close();
    	        out.close();
    	}
        }
    }
    

}