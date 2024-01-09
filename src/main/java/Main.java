import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Integer[] testdata = getTestData();
        RBTree<Integer> tree = new RBTree<>();
        for(int i : testdata) {
            tree.insert(i);
        }
        System.out.println(tree.toDotFile());
    }

    private static Integer[] getTestData() {
        return IntStream.generate(() -> ThreadLocalRandom.current().nextInt(0, 100))
                .distinct()
                .limit(15)
                .boxed()
                .toArray(Integer[]::new);
    }
}
