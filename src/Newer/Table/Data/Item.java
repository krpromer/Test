package Newer.Table.Data;

public class Item {
    int field;
    Object value;
    String str;

    public Item(int field, Object value){
        this.field = field;
        this.value = value;
        this.str = null;
    }

    public int getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public String getStr() {
        if (str == null) return value.toString();
        return str;
    }

    public void setField(int field) {
        this.field = field;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "field = " + field + ", value = " + value + ", str = " + str;
    }
}
