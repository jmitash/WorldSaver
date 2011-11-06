/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servm.knoxcorner.worldsaver;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

    
/**
 *
 * @author JSM
 */
public class saveRunner implements Runnable {
        private static final Logger log = Logger.getLogger("saveRunner");

    @Override
    public void run() {

           if (worldSaver.runSaveRoutine == true || worldSaver.command.equalsIgnoreCase("forcesave")) {
            
            
            
        forceSave.save();
        log.log(Level.INFO, "[WorldSaver] Saved world");

    }
}
    
    public static void waiting (int n){
        
        long t0, t1;

        t0 =  System.currentTimeMillis();

        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (n));
    }

}
