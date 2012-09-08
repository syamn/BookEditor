/**
 * BookEditor - Package: syam.BookEditor.Book
 * Created: 2012/09/08 14:23:24
 */
package syam.BookEditor.Book;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;

import org.bukkit.craftbukkit.inventory.CraftItemStack;

/**
 * BookActions (BookActions.java)
 * @author syam
 */
public class BookActions {

	/**
	 * 本の著者を取得する
	 * @param item CraftItemStack
	 * @return null or 著者名
	 */
	public static String getAuthor(CraftItemStack item){
		NBTTagCompound tag = item.getHandle().getTag();
		if(tag == null) return null;
		return item.getHandle().getTag().getString("author");
	}
	/**
	 * 本の著者を設定する
	 * @param item CraftItemStack
	 * @param name 著者名
	 */
	public static void setAuthor(CraftItemStack item, String name){
		NBTTagCompound tag = item.getHandle().getTag();
		if(tag == null) tag = new NBTTagCompound("tag");
		tag.setString("author", name);
		item.getHandle().setTag(tag);
	}

	/**
	 * 本のタイトルを取得する
	 * @param item CraftItemStack
	 * @return null or 本のタイトル
	 */
	public static String getTitle(CraftItemStack item){
		NBTTagCompound tag = item.getHandle().getTag();
		if(tag == null) return null;
		return item.getHandle().getTag().getString("title");
	}
	/**
	 * 本のタイトルを設定する
	 * @param item CraftItemStack
	 * @param title 本のタイトル
	 */
	public static void setTitle(CraftItemStack item, String title){
		NBTTagCompound tag = item.getHandle().getTag();
		if(tag == null) tag = new NBTTagCompound("tag");
		tag.setString("title", title);
		item.getHandle().setTag(tag);
	}

	/**
	 * 本のページを取得する
	 * @param item CraftItemStack
	 * @return ページリスト
	 */
	public static List<String> getPages(CraftItemStack item) {
		NBTTagList pagesTagList = getPagesTagList(item);
		int n = pagesTagList.size();
		int i = 0;
		List<String> result = new ArrayList<String>();
		while(i < n) {
			result.add(((NBTTagString)pagesTagList.get(i)).data);
			i++;
		}
		return result;
	}
	/**
	 * 本のページを設定する
	 * @param item CraftItemStack
	 * @param pages ページリスト
	 */
	public static void setPages(CraftItemStack item, List<String> pages){
		NBTTagList pagesTagList = new NBTTagList();
		int i = 0;
		for (String page : pages) {
			pagesTagList.add(new NBTTagString("page" + i, page));
			i++;
		}
		setPagesTagList(item, pagesTagList);
	}

	/**
	 * ページのタグリスト(NBTTagList)を取得する
	 * @param item CraftItemStack
	 * @return pagesタグのリスト NBTTagList
	 */
	public static NBTTagList getPagesTagList(CraftItemStack item){
		NBTTagCompound tag = item.getHandle().getTag();
		if(tag == null) return new NBTTagList("pages");
		return tag.getList("pages");
	}
	/**
	 * ページのタグリスト(NBTTagList)を設定する
	 * @param item CraftItemStack
	 * @param pagesタグのリスト NBTTagList
	 */
	public static void setPagesTagList(CraftItemStack item, NBTTagList tagList){
		NBTTagCompound tag = item.getHandle().getTag();
		if(tag == null) tag = new NBTTagCompound("tag");
		tag.set("pages", tagList);
		item.getHandle().setTag(tag);
	}

	/**
	 * 本のタグ(NBTTagCompound)を取得する
	 * @param item CraftItemStack
	 * @return NBTTagCompound (or null)
	 */
	public static NBTTagCompound getTag(CraftItemStack item){
		NBTTagCompound tag = item.getHandle().getTag();
		return tag;
	}
	/**
	 * 本のタグ(NBTTagCompound)を設定する
	 * @param item CraftItemStack
	 * @param tag NBTTagCompound
	 */
	public static void setTag(CraftItemStack item, NBTTagCompound tag){
		if (tag == null) tag = new NBTTagCompound("tag");
		item.getHandle().setTag(tag);
	}
}
