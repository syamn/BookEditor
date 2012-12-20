/**
 * BookEditor - Package: syam.BookEditor.Book
 * Created: 2012/09/08 14:23:24
 */
package syam.BookEditor;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * BookActions (BookActions.java)
 * @author syam
 */
public class Book {
    private ItemStack item;
    private BookMeta meta;
    
    /**
     * コンストラクタ
     * @param item ItemStack: BOOK_AND_QUILL or WRITTEN_BOOK
     */
    public Book(ItemStack item){
        setItem(item);
    }

    /**
     * 本アイテムを設定
     * @param item ItemStack: BOOK_AND_QUILL or WRITTEN_BOOK
     */
    public void setItem(final ItemStack item){
        // 本かどうかチェック
        if (item.getType() != Material.BOOK_AND_QUILL && item.getType() != Material.WRITTEN_BOOK){
            throw new IllegalArgumentException("Parameter item must be " + Material.BOOK_AND_QUILL.name() + " or " + Material.WRITTEN_BOOK.name() + ".");
        }
        if (!(item.getItemMeta() instanceof BookMeta)){
            throw new IllegalArgumentException("Could not cast to BookMeta!");
        }
        
        this.item = item;
        this.meta = (BookMeta) item.getItemMeta();
    }
    
    /**
     * 本アイテムを取得
     * @return ItemStack
     */
    public ItemStack getItem(){
        this.item.setItemMeta(this.meta);
        return item;
    }

	/**
	 * 本の著者を取得する
	 * @return null or 著者名
	 */
	public String getAuthor(){
	    return meta.getAuthor();
	}
	/**
	 * 本の著者を設定する
	 * @param name 著者名
	 */
	public void setAuthor(String name){
	    meta.setAuthor(name);
	}

	/**
	 * 本のタイトルを取得する
	 * @return null or 本のタイトル
	 */
	public String getTitle(){
	    return meta.getTitle();
	}
	/**
	 * 本のタイトルを設定する
	 * @param title 本のタイトル
	 */
	public void setTitle(String title){
	    meta.setTitle(title);
	}

	/**
	 * 本のページを取得する
	 * @return ページリスト
	 */
	public List<String> getPages() {
	    return meta.getPages();
	}
	/**
	 * 本のページを設定する
	 * @param pages ページリスト
	 */
	public void setPages(List<String> pages){
	    meta.setPages(pages);
	}
}
