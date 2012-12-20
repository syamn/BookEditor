/**
 * BookEditor - Package: syam.BookEditor.Command
 * Created: 2012/09/08 12:10:18
 */
package syam.BookEditor.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import syam.BookEditor.Book;
import syam.BookEditor.BookEditor;
import syam.BookEditor.Enum.Permission;
import syam.BookEditor.Util.Actions;

/**
 * BaseCommand (BaseCommand.java)
 * @author syam
 */
public abstract class BaseCommand {
	// Logger
	protected static final Logger log = BookEditor.log;
	protected static final String logPrefix = BookEditor.logPrefix;
	protected static final String msgPrefix = BookEditor.msgPrefix;
	/* コマンド関係 */
	public CommandSender sender;
	public List<String> args = new ArrayList<String>();
	public String name;
	public int argLength = 0;
	public String usage;
	public boolean bePlayer = true;
	public Player player;
	public String command;
	public BookEditor plugin;

	/* BookEditor */
	public Material needInHandBookType = null;
	public Book handBook = null;
	public Permission editOtherPermission = null;


	public boolean run(BookEditor plugin, CommandSender sender, String[] preArgs, String cmd) {
		this.plugin = plugin;
		this.sender = sender;
		this.command = cmd;

		// 引数をソート
		args.clear();
		for (String arg : preArgs)
			args.add(arg);

		// 引数からコマンドの部分を取り除く
		// (コマンド名に含まれる半角スペースをカウント、リストの先頭から順にループで取り除く)
		for (int i = 0; i < name.split(" ").length && i < args.size(); i++)
			args.remove(0);

		// 引数の長さチェック
		if (argLength > args.size()){
			sendUsage();
			return true;
		}

		// 実行にプレイヤーであることが必要かチェックする
		if (bePlayer && !(sender instanceof Player)){
			Actions.message(sender, null, "&cThis command cannot run from Console!");
			return true;
		}
		if (sender instanceof Player){
			player = (Player)sender;
		}

		// 権限チェック
		if (!permission()){
			Actions.message(sender, null, "&cYou don't have permission to use this!");
			return true;
		}

		// 手に持ったアイテムチェック
		if (needInHandBookType != null && player != null){
		    ItemStack is = player.getItemInHand();

		    switch (needInHandBookType){
		        /* 署名済みまたは未署名の本 */
		        case BOOK:
		            if (is.getType() != Material.BOOK_AND_QUILL && is.getType() != Material.WRITTEN_BOOK){
		                Actions.message(sender, null, "&c持っているアイテムが本ではありません！");
		                return true;
		            }
		            break;
		        /* 署名済み(閉じられた)の本 */
		        case WRITTEN_BOOK:
		            if (is.getType() != Material.WRITTEN_BOOK){
		                if (is.getType() == Material.BOOK_AND_QUILL) Actions.message(sender, null, "&cこの本は署名されていません！");
		                else Actions.message(sender, null, "&c持っているアイテムが署名済みの本ではありません！");
		                return true;
		            }
		            break;
		        /* 未署名(編集可能)の本 */
		        case BOOK_AND_QUILL:
		            if (is.getType() != Material.BOOK_AND_QUILL){
		                if (is.getType() == Material.WRITTEN_BOOK) Actions.message(sender, null, "&cこの本は署名されています！");
		                else Actions.message(sender, null, "&c持っているアイテムが未署名の本ではありません！");
		                return true;
	                }
		            break;
		        /* 未定義 */
		        default:
		            log.severe(logPrefix+ needInHandBookType.name() + " is not defined on BaseCommand! Please contact developer!");
		            Actions.message(sender, null, "&can error occured while running this command. Please contact server administrator!");
		            return true;
		    }

		    handBook = new Book(is);
		}

		// 実行
		execute();

		return true;
	}

	/**
	 * コマンドを実際に実行する
	 * @return 成功すればtrue それ以外はfalse
	 */
	public abstract void execute();

	/**
	 * コマンド実行に必要な権限を持っているか検証する
	 * @return trueなら権限あり、falseなら権限なし
	 */
	public abstract boolean permission();

	/**
	 * コマンドの使い方を送信する
	 */
	public void sendUsage(){
		Actions.message(sender, null, "&c/"+this.command+" "+name+" "+usage);
	}
}
