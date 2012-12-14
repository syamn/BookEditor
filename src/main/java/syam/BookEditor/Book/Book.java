/**
 * BookEditor - Package: syam.BookEditor.Book
 * Created: 2012/09/08 12:11:41
 */
package syam.BookEditor.Book;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_4_5.NBTTagCompound;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_4_5.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Book (Book.java)
 * @author syam
 */
public class Book implements Cloneable{
	private String author;
	private String title;
	private List<String> pages;
	private CraftItemStack item;

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
	public void setItem(ItemStack item){
		// 本かどうかチェック
		if (item.getType() != Material.BOOK_AND_QUILL && item.getType() != Material.WRITTEN_BOOK){
			throw new IllegalArgumentException("Parameter item must be " + Material.BOOK_AND_QUILL.name() + " or " + Material.WRITTEN_BOOK.name() + ".");
		}

		// プレイヤーがゲーム内から生成
		if (item instanceof CraftItemStack){
			this.item = (CraftItemStack) item;

			author = BookActions.getAuthor(this.item);
			title = BookActions.getTitle(this.item);
			pages = BookActions.getPages(this.item);
		}
		// ファイルから生成
		else{
			this.item = new CraftItemStack(item);

			author = null;
			title = null;
			pages = new ArrayList<String>();
		}
	}

	/**
	 * 本アイテムを取得
	 * @return ItemStack
	 */
	public ItemStack getItem(){
		CraftItemStack cis = this.item.clone();

		BookActions.setAuthor(cis, author);
		BookActions.setTitle(cis, title);
		BookActions.setPages(cis, pages);

		return cis;
	}

	/**
	 * Override Object.clone() for implements Cloneable
	 */
	@Override
	public Book clone(){
		return new Book(getItem());
	}

	/* getter / setter */

	// Material
	public Material getType(){
		return this.item.getType();
	}
	public void setType(final Material type){
		this.item.setType(type);
	}

	// Tag
	public NBTTagCompound getTag(){
		NBTTagCompound tag = this.item.getHandle().getTag();
		// 未設定なら初期設定
		if (tag == null){
			tag = new NBTTagCompound("tag");
			setTag(tag);
		}
		return tag;
	}
	public void setTag(final NBTTagCompound tag){
		this.item.getHandle().setTag(tag);
	}

	// Author
	public String getAuthor(){
		return this.author;
	}
	public void setAuthor(final String name){
		this.author = name;
	}

	// Title
	public String getTitle(){
		return this.title;
	}
	public void setTitle(final String title){
		this.title = title;
	}

	// Pages
	public List<String> getPages(){
		return this.pages;
	}
	public void setPages(final List<String> pages){
		this.pages = pages;
	}
}
