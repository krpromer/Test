package Newer.Table.Data;

import java.util.ArrayList;
import java.util.Collections;

public class Record {
    ArrayList<Item> itemList;
    public Record(){
        itemList = new ArrayList<>();
    }

    public void add(Item...items){
        Collections.addAll(itemList, items);
    }

    public Item getItem(int index){
        return itemList.get(index);
    }

    public Item getItemByField(int field){
        for(Item item : itemList){
            if (item.getField() == field)
                return item;
        }
        return null;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public int getSize(){
        return itemList.size();
    }
}
