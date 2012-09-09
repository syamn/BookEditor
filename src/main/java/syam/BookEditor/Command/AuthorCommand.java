/**
 * BookEditor - Package: syam.BookEditor.Command
 * Created: 2012/09/09 15:15:47
 */
package syam.BookEditor.Command;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import syam.BookEditor.Enum.Permission;
import syam.BookEditor.Util.Actions;

/**
 * SetAuthorCommand (SetAuthorCommand.java)
 * @author syam
 */
public class AuthorCommand extends BaseCommand{
    public AuthorCommand(){
        bePlayer = true;
        name = "author";
        argLength = 1;
        usage = "<author> <- set book author";
        needInHandBookType = Material.WRITTEN_BOOK;
    }

    @Override
    public void execute() {
        // Edit book
        handBook.setAuthor(Actions.coloring(args.get(0)));

        ItemStack newBook = handBook.getItem();

        // Set
        player.setItemInHand(newBook);

        String msg = "&aタイトル'&6" + handBook.getTitle() + "&a'の本の著者を'&6" + handBook.getAuthor() + "&a'に変更しました！";
        Actions.message(null, player, msg);
    }

    @Override
    public boolean permission() {
        return Permission.SET_AUTHOR.hasPerm(sender);
    }
}
