/**
 * BookEditor - Package: syam.BookEditor.Util
 * Created: 2012/09/09 18:57:26
 */
package syam.BookEditor.Util;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Util (Util.java)
 * @author syam
 */
public class Util {
    /**
     * 文字列が整数型に変換できるか返す
     * @param str チェックする文字列
     * @return 変換成功ならtrue、失敗ならfalse
     */
    public static boolean isInteger(String str) {
        try{
            Integer.parseInt(str);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * 文字列がdouble型に変換できるか返す
     * @param str チェックする文字列
     * @return 変換成功ならtrue、失敗ならfalse
     */
    public static boolean isDouble(String str) {
        try{
            Double.parseDouble(str);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * PHPの join(array, delimiter) と同じ関数
     * @param s 結合するコレクション
     * @param delimiter デリミタ文字
     * @return 結合後の文字列
     */
    public static String join(Collection<?> s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator<?> iter = s.iterator();

        // 要素が無くなるまでループ
        while (iter.hasNext()){
            buffer.append(iter.next());
            // 次の要素があればデリミタを挟む
            if (iter.hasNext()){
                buffer.append(delimiter);
            }
        }
        // バッファ文字列を返す
        return buffer.toString();
    }

    /**
     * ファイル名から拡張子を返します
     * @param fileName ファイル名
     * @return ファイルの拡張子
     */
    public static String getSuffix(String fileName) {
        if (fileName == null)
            return null;
        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            return fileName.substring(point + 1);
        }
        return fileName;
    }
    
    /**
     * インベントリ内にある空スロットの総数を返します
     * @param inv インベントリ
     * @return 空スロットの総数
     */
    public static int getEmptySlotNum(Inventory inv){
        Iterator<ItemStack> iter = inv.iterator();
        int i=0;
        
        while(iter.hasNext()){
            if(iter.next()==null){
                i++;
            }
        }
        return i;
    }
}
