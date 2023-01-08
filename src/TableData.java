public class TableData {
    private int field;
    private Object value;

    public TableData(int field, Object value){
        this.field = field;
        this.value = value;
    }

    public int getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
