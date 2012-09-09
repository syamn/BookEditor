/**
 * BookEditor - Package: syam.BookEditor.Listener
 * Created: 2012/09/09 17:41:20
 */
package syam.BookEditor.Listener;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import syam.BookEditor.BookEditor;

/**
 * BEServerListener (BEServerListener.java)
 * @author syam
 */
public class BEServerListener implements Listener{
    // Logger
    private static final Logger log = BookEditor.log;
    private static final String logPrefix = BookEditor.logPrefix;
    private static final String msgPrefix = BookEditor.msgPrefix;

    private final BookEditor plugin;

    public BEServerListener(final BookEditor plugin){
        this.plugin = plugin;
    }

    // plugin unloading..
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginDisable(final PluginDisableEvent event){
        if (!plugin.getConfigs().useVault){
            return;
        }

        String pname = event.getPlugin().getName();

        if (pname.equals("Vault")){
            log.warning(logPrefix + "Detected unloading Vault plugin. Disabled Vault integration.");
            plugin.getConfigs().useVault = false;
        }
    }
}
