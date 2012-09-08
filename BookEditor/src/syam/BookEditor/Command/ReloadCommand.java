/**
 * BookEditor - Package: syam.BookEditor.Command
 * Created: 2012/09/08 17:14:30
 */
package syam.BookEditor.Command;

import syam.BookEditor.Enum.Permission;
import syam.BookEditor.Util.Actions;

/**
 * ReloadCommand (ReloadCommand.java)
 * @author syam
 */
public class ReloadCommand extends BaseCommand{
	public ReloadCommand(){
		bePlayer = false;
		name = "reload";
		argLength = 0;
		usage = "<- reload config.yml";
	}

	@Override
	public void execute() {
		try{
			plugin.getConfigs().loadConfig(false);
		}catch (Exception ex){
			log.warning(logPrefix+"an error occured while trying to load the config file.");
			ex.printStackTrace();
			return;
		}
		Actions.message(sender, null, "&aConfiguration reloaded!");
		return;
	}

	@Override
	public boolean permission() {
		return Permission.RELOAD.hasPerm(sender);
	}
}
