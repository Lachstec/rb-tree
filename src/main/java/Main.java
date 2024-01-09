import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Main {

    private static final String filename = "output/rbtree-%d.dot";

    public static void main(String[] args) {
        Integer[] testdata = getTestData();
        RBTree<Integer> tree = new RBTree<>();
        for(int i = 0; i < testdata.length; i += 1) {
            tree.insert(testdata[i]);
            try {
                writeToFile(tree.toDotFile(), i);
            } catch (IOException ex) {
                System.out.println("error writing to files");
                ex.printStackTrace();
            }
        }
    }

    private static void writeToFile(String dotfile, int number) throws IOException {
        File file = new File(String.format(filename, number));
        FileOutputStream fos = new FileOutputStream(file);
        byte[] strBytes = dotfile.getBytes();
        fos.write(strBytes);
        fos.close();
    }

    private static Integer[] getTestData() {
        return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(0, 100))
                .distinct()
                .limit(15)
                .boxed()
                .toArray(Integer[]::new);
    }
}
