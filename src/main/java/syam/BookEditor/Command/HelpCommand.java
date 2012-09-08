/**
 * BookEditor - Package: syam.BookEditor.Command
 * Created: 2012/09/08 12:53:08
 */
package syam.BookEditor.Command;

import syam.BookEditor.BookEditor;
import syam.BookEditor.Util.Actions;

/**
 * HelpCommand (HelpCommand.java)
 * @author syam
 */
public class HelpCommand extends BaseCommand {
	public HelpCommand(){
		bePlayer = false;
		name = "help";
		argLength = 0;
		usage = "<- show command help";
	}

	@Override
	public void execute() {
		Actions.message(sender, null, "&c===================================");
		Actions.message(sender, null, "&bBookEditor Plugin version &3%version &bby syamn");
		Actions.message(sender, null, " &b<>&f = required, &b[]&f = optional");
		// 全コマンドをループで表示
		for (BaseCommand cmd : BookEditor.commands.toArray(new BaseCommand[0])){
			cmd.sender = this.sender;
			if (cmd.permission()){
				Actions.message(sender, null, "&8-&7 /"+command+" &c" + cmd.name + " &7" + cmd.usage);
			}
		}
		Actions.message(sender, null, "&c===================================");

		return;
	}

	@Override
	public boolean permission() {
			return true;
	}

}
