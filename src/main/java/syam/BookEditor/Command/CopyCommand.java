/**
 * BookEditor - Package: syam.BookEditor.Command
 * Created: 2012/09/08 15:30:53
 */
package syam.BookEditor.Command;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import syam.BookEditor.BookEditor;
import syam.BookEditor.Book.Book;
import syam.BookEditor.Book.BookActions;
import syam.BookEditor.Enum.Permission;
import syam.BookEditor.Util.Actions;

/**
 * CopyCommand (CopyCommand.java)
 * @author syam
 */
public class CopyCommand extends BaseCommand{
	public CopyCommand(){
		bePlayer = true;
		name = "copy";
		argLength = 0;
		usage = "<- copy your book";
		needInHandBookType = Material.WRITTEN_BOOK;
	}

	@Override
	public void execute() {
		// Check Author
		if (!player.getName().equalsIgnoreCase(handBook.getAuthor()) && !Permission.COPY_OTHER.hasPerm(player)){
			Actions.message(null, player, "&cそれはあなたが書いた本ではありません！");
			return;
		}

		// Check empty slot
		PlayerInventory inv = player.getInventory();

		if (inv.firstEmpty() < 0){
			Actions.message(null, player, "&cインベントリがいっぱいです！");
			return;
		}

		// Pay cost
		boolean paid = false;
		double cost = plugin.getConfigs().cost_copy; // get cost

		if (plugin.getConfigs().useVault && cost > 0 && !Permission.COPY_FREE.hasPerm(sender)){
			paid = Actions.takeMoney(player.getName(), cost);
			if (!paid){
				Actions.message(null, player, "&cお金が足りません！ " + Actions.getCurrencyString(cost) + "必要です！");
				return;
			}
		}

		// Copy
		inv.addItem(player.getItemInHand().clone());

		String msg = "&aタイトル'&6" + handBook.getTitle() + "&a'の本をコピーしました！";
		if (paid) msg = msg + " &c(-" + Actions.getCurrencyString(cost) + ")";
		Actions.message(null, player, msg);

		return;
	}

	@Override
	public boolean permission() {
		return Permission.COPY.hasPerm(sender);
	}
}
