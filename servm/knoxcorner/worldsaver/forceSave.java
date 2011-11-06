/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servm.knoxcorner.worldsaver;


import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.permissions.Permission.*;


/**
 *
 * @author JSM
 */
public class forceSave {
    private static final Logger log = Logger.getLogger("forceSave");
    
    

  
    
    
    public static boolean save() {
        Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[WorldSaver] Saving worlds, you may experience lag");
        saveRunner.waiting(3000);
        Bukkit.getServer().savePlayers();
    for( World world : Bukkit.getServer().getWorlds() ){
        world.save();
        
        
        
    }
    
    return true;

       
    
    
    }


}
