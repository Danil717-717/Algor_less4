//1. Реализуем структуру бинарного дерева.
//2. Для бинарного дерева характерно наличии двух потомков, где левый
//   меньше родителя, а правый – больше.
//3. Для реализации можно использовать как и простое числовое дерево, так
//   и обобщенный тип. Учитывая, что мы строим именно бинарное дерево, то
//   при использовании обобщенных типов убедитесь, что значение
//   поддерживает сравнение (интерфейс Comparable)


public class BinaryTree <T extends Comparable<T>>{

    private Node root;

    public boolean contains(T value){
        Node node = root;
        while(node != null){
            if(node.value.equals(value)){
                return true;
            }
            if (node.value.compareTo(value) > 0){
                node = node.left;
            }else {
                node=node.right;
            }
        }
        return false;
    }

    public void add(T value){
        Node node = root;
        Node newNode = new Node();
        newNode.value = value;
        if(root != null){
            if(node.value.compareTo(value) > 0){
                node.left = newNode;
            }else{
                node.right = newNode;
            }
        }else{
            root = newNode;
        }
    }

    private class Node{
        private T value;
        private Node left;
        private Node right;


    }


}
