/**
 * BookEditor - Package: syam.BookEditor.Command
 * Created: 2012/09/09 12:12:17
 */
package syam.BookEditor.Command;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import syam.BookEditor.Book.Book;
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
    }

    @Override
    public void execute() {
        ItemStack is = player.getItemInHand();

        // Check inHand item
        if (is.getType() != Material.WRITTEN_BOOK){
            Actions.message(null, player, "&c持っているアイテムが署名済みの本ではありません！");
            return;
        }

        // Check Author
        Book book = new Book(is);

        if (!player.getName().equalsIgnoreCase(book.getAuthor()) && !Permission.EDIT_OTHER.hasPerm(player)){
            Actions.message(null, player, "&cそれはあなたが書いた本ではありません！");
            return;
        }

        // Pay cost
        boolean paid = false;
        double cost = plugin.getConfigs().cost_copy; // get cost

        if (plugin.getConfigs().useVault && cost > 0 && !Permission.EDIT_FREE.hasPerm(sender)){
            paid = Actions.takeMoney(player.getName(), cost);
            if (!paid){
                Actions.message(null, player, "&cお金が足りません！ " + cost + " Coin 必要です！");
                return;
            }
        }

        String title = book.getTitle();

        // Remove sign
        book.setAuthor("");
        book.setTitle("");

        is = book.getItem();
        is.setType(Material.BOOK_AND_QUILL);

        // Set
        player.setItemInHand(is);

        String msg = "&aタイトル'&6" + title + "&a'の本を未署名に戻しました！";
        if (paid) msg = msg + " &c(-" + cost + " Coins)";
        Actions.message(null, player, msg);

        return;
    }

    @Override
    public boolean permission() {
        return Permission.EDIT.hasPerm(sender);
    }
}
