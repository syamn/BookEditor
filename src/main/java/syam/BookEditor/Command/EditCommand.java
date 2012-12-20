/**
 * BookEditor - Package: syam.BookEditor.Command
 * Created: 2012/09/09 12:12:17
 */
package syam.BookEditor.Command;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import syam.BookEditor.Enum.Permission;
import syam.BookEditor.Util.Actions;

/**
 * EditCommand (EditCommand.java)
 * @author syam
 */
public class EditCommand extends BaseCommand{
    public EditCommand(){
        bePlayer = true;
        name = "edit";
        argLength = 0;
        usage = "<- unsign your book";
        needInHandBookType = Material.WRITTEN_BOOK;
    }

    @Override
    public void execute() {
        // Check Author
        if (!player.getName().equalsIgnoreCase(handBook.getAuthor()) && !Permission.EDIT_OTHER.hasPerm(player)){
            Actions.message(null, player, "&cそれはあなたが書いた本ではありません！");
            return;
        }

        // Pay cost
        boolean paid = false;
        double cost = plugin.getConfigs().cost_copy; // get cost

        if (plugin.getConfigs().useVault && cost > 0 && !Permission.EDIT_FREE.hasPerm(sender)){
            paid = Actions.takeMoney(player.getName(), cost);
            if (!paid){
                Actions.message(null, player, "&cお金が足りません！ " + Actions.getCurrencyString(cost) + "必要です！");
                return;
            }
        }

        String title = handBook.getTitle();

        // Remove sign
        handBook.setAuthor("");
        handBook.setTitle("");

        ItemStack newBook = handBook.getItem();
        newBook.setType(Material.BOOK_AND_QUILL);

        // Set
        player.setItemInHand(newBook);

        String msg = "&aタイトル'&6" + title + "&a'の本を未署名に戻しました！";
        if (paid) msg = msg + " &c(-" + Actions.getCurrencyString(cost) + ")";
        Actions.message(null, player, msg);

        return;
    }

    @Override
    public boolean permission() {
        return Permission.EDIT.hasPerm(sender);
    }
}
