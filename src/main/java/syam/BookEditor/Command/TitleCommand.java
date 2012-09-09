/**
 * BookEditor - Package: syam.BookEditor.Command
 * Created: 2012/09/09 18:14:24
 */
package syam.BookEditor.Command;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import syam.BookEditor.Enum.Permission;
import syam.BookEditor.Util.Actions;
import syam.BookEditor.Util.Util;

/**
 * TitleCommand (TitleCommand.java)
 * @author syam
 */
public class TitleCommand extends BaseCommand{
    public TitleCommand(){
        bePlayer = true;
        name = "title";
        argLength = 1;
        usage = "<title> <- set book title";
        needInHandBookType = Material.WRITTEN_BOOK;
    }

    @Override
    public void execute() {
        // Check Author
        if (!player.getName().equalsIgnoreCase(handBook.getAuthor()) && !Permission.TITLE_OTHER.hasPerm(player)){
            Actions.message(null, player, "&cそれはあなたが書いた本ではありません！");
            return;
        }

        // Pay cost
        boolean paid = false;
        double cost = plugin.getConfigs().cost_copy; // get cost

        if (plugin.getConfigs().useVault && cost > 0 && !Permission.TITLE_FREE.hasPerm(sender)){
            paid = Actions.takeMoney(player.getName(), cost);
            if (!paid){
                Actions.message(null, player, "&cお金が足りません！ " + Actions.getCurrencyString(cost) + "必要です！");
                return;
            }
        }

        // Get title
        String oldTitle = handBook.getTitle();
        String newTitle = Util.join(args, " ");

        if (Permission.TITLE_COLOR.hasPerm(sender)){
            newTitle = Actions.coloring(newTitle);
        }

        handBook.setTitle(newTitle);
        ItemStack newBook = handBook.getItem();

        player.setItemInHand(newBook);

        String msg = "&a本のタイトルを'&6" + oldTitle + "&a'から'&6" + newTitle + "&a'に変更しました！";
        if (paid) msg = msg + " &c(-" + Actions.getCurrencyString(cost) + ")";
        Actions.message(null, player, msg);
    }

    @Override
    public boolean permission() {
        return Permission.TITLE.hasPerm(sender);
    }
}
