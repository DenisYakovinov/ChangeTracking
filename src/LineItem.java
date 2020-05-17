public class LineItem {
    public Type type;
    public String line;

    public LineItem(Type type, String line) {
        this.type = type;
        this.line = line;
    }

    @Override
    public String toString() {
        return String.format("%s   %s", type, line);
    }
}
