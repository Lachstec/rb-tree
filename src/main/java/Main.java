import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Integer[] testdata = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        RBTree<Integer> tree = new RBTree<>();
        for(int i : testdata) {
            tree.insert(i);
        }
    }

    private static int[] getTestData() {
        return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(-100, 100))
                .limit(15)
                .toArray();
    }
}
