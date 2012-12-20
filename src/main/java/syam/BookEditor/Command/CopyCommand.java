/**
 * BookEditor - Package: syam.BookEditor.Command
 * Created: 2012/09/08 15:30:53
 */
package syam.BookEditor.Command;

import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

import syam.BookEditor.Enum.Permission;
import syam.BookEditor.Util.Actions;
import syam.BookEditor.Util.Util;

/**
 * CopyCommand (CopyCommand.java)
 * @author syam
 */
public class CopyCommand extends BaseCommand{
	public CopyCommand(){
		bePlayer = true;
		name = "copy";
		argLength = 0;
		usage = "([quantity]) <- copy your book";
		needInHandBookType = Material.WRITTEN_BOOK;
	}

	@Override
	public void execute() {
        int quantity = 1; // Number of the books to copy
    
        // Check arguments
        if (args.size()>0){
            if(!Util.isInteger(args.get(0))){
                Actions.message(null, player, "&c数量は整数で指定してください！");
                return;
            }else{
                quantity = Integer.parseInt(args.get(0));
            }
        }
        
        // Check if quantity is positive
        if (quantity < 1){
            Actions.message(null, player, "&c数量は自然数で指定してください！");
            return;
        }
                
		// Check Author
		if (!player.getName().equalsIgnoreCase(handBook.getAuthor()) && !Permission.COPY_OTHER.hasPerm(player)){
			Actions.message(null, player, "&cそれはあなたが書いた本ではありません！");
			return;
		}

		// Check empty slot
		PlayerInventory inv = player.getInventory();

		if (Util.getEmptySlotNum(inv) < quantity){
			Actions.message(null, player, "&cインベントリの空きが足りません！");
			return;
		}

		// Pay cost
		boolean paid = false;
		double cost = plugin.getConfigs().cost_copy * (double) quantity; // get cost

		if (plugin.getConfigs().useVault && cost > 0 && !Permission.COPY_FREE.hasPerm(sender)){
			paid = Actions.takeMoney(player.getName(), cost);
			if (!paid){
				Actions.message(null, player, "&cお金が足りません！ " + Actions.getCurrencyString(cost) + "必要です！");
				return;
			}
		}

		// Copy
        for(int i = quantity; i > 0; i--){
            inv.addItem(player.getItemInHand().clone());
        }
                
		String msg = "&aタイトル'&6" + handBook.getTitle() + "&a'の本を" + quantity + "冊コピーしました！";
		if (paid) msg = msg + " &c(-" + Actions.getCurrencyString(cost) + ")";
		Actions.message(null, player, msg);

		return;
	}

	@Override
	public boolean permission() {
		return Permission.COPY.hasPerm(sender);
	}
}
