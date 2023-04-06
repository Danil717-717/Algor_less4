import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) {

//        final RedBlackTree tree = new RedBlackTree();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
//            while (true) {
//                try {
//                    int value = Integer.parseInt(reader.readLine());
//                    tree.add(value);
//                    System.out.println("finish");
//                } catch (Exception ignored) {
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.add(4);
        tree.add(5);
        tree.add(2);
        System.out.println(tree.contains(6));

    }
}