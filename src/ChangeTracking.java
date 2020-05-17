import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChangeTracking {

    public static final List<LineItem> lines = new ArrayList<LineItem>();

    public static void main(String[] args) {
        String fileNameFirst = "";
        String fileNameSecond = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            fileNameFirst = reader.readLine();
            fileNameSecond = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader buffReaderForFileFirst = new BufferedReader(new FileReader(fileNameFirst));
             BufferedReader buffReaderForFileSecond = new BufferedReader(new FileReader(fileNameSecond))) {
            /*
             * reading two lines from each file at once and immediately determine
             * what happened to the line in the second file
             */
            while (true) {
                String lineFromFirstFile = buffReaderForFileFirst.readLine();
                String lineFromSecondFile = buffReaderForFileSecond.readLine();
                /*
                 * this block  is used to exit the loop when the last element in
                 * one of the files is reached
                 */
                if (lineFromSecondFile == null) {
                    lines.add(new LineItem(Type.REMOVED, lineFromFirstFile));
                    break;
                } else if (lineFromFirstFile == null) {
                    lines.add(new LineItem(Type.ADDED, lineFromSecondFile));
                    break;
                }
                /* each of the next two rows in the stream is marked to control
                 * the offset of the rows
                 */
                buffReaderForFileFirst.mark(10000);
                String secondLineFromFirstFile = buffReaderForFileFirst.readLine();

                buffReaderForFileSecond.mark(10000);
                String secondLineFromSecondFile = buffReaderForFileSecond.readLine();

                if (lineFromFirstFile.equals(lineFromSecondFile)) {
                    lines.add(new LineItem(Type.SAME, lineFromFirstFile));
                    buffReaderForFileFirst.reset();
                    buffReaderForFileSecond.reset();
                    continue;
                }
                if (lineFromFirstFile.equals(secondLineFromSecondFile)) {
                    lines.add(new LineItem(Type.ADDED, lineFromSecondFile));
                    lines.add(new LineItem(Type.SAME, secondLineFromSecondFile));
                    buffReaderForFileFirst.reset();
                } else if (secondLineFromFirstFile.equals(lineFromSecondFile)) {
                    lines.add(new LineItem(Type.REMOVED, lineFromFirstFile));
                    lines.add(new LineItem(Type.SAME, secondLineFromFirstFile));
                    buffReaderForFileSecond.reset();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

