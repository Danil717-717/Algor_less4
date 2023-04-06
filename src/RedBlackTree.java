public class RedBlackTree {

    private Node root;   //рутовая нода, начало


    public boolean add(int value){
        if(root != null){                           //если root  нода уже существует
            boolean result = addNode(root,value);   // то создаем новую ноду
            root = rebalance(root);                 // root балансируем
            root.color = Color.BLACK;               // ставим root черным
            return result;
        }else {                                    //если его нет
            root = new Node();                     //создаем его
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }

    private boolean addNode(Node node, int value){            //добавление новой ноды
        if(node.value == value){                              // т.к все ноды уникальные, если такая
            return false;                                     // нода уже есть уже false
        }else {
            if(node.value > value){                           //если значение нашей ноды больше чем искомое значение
                if(node.leftChild != null){                   // при этом левый ребенок существует
                    boolean result = addNode(node.leftChild, value); // запускаем рекурсивную проверку по левому ребенку
                    node.leftChild = rebalance(node.leftChild);      // вниз вглубину, если его нет и можно добавить и он вернет значение
                    return result;
                }else {                                       //если левой ноды не существует
                    node.leftChild = new Node();              //генерируем левою ноду
                    node.leftChild.color = Color.RED;         // присвеваем ей цвет(все ноды при создании получают красный цыет0
                    node.leftChild.value = value;            // ПРИСВАЕВАЕМ ЗНАЧЕНИЕ
                    return true;
                }
            }else{
                if(node.rightChild != null){                    // если правого ребенок есть так же запуск оиска и возвращ значен
                    boolean result = addNode(node.rightChild, value);
                    node.rightChild = rebalance(node.rightChild);
                    return result;
                }else {   //если его нет
                    node.rightChild = new Node();              //генерируем
                    node.rightChild.color = Color.RED;         //присваив цвет
                    node.rightChild.value = value;
                    return true;
                }
            }
        }
    }

    private Node rebalance(Node node){
        Node result = node;
        boolean needRebalance;
        do{
            needRebalance = false;
            if(result.rightChild != null && result.rightChild.color ==Color.RED &&  //если есть правый ребенок и этот ребенок красный
                    (result.leftChild == null || result.leftChild.color == Color.BLACK)){  //при этом левый ребенок имеет черный цвет
                needRebalance = true;
                result = rightSwap(result);  //то производим правый поворот
            }
            if(result.leftChild != null && result.leftChild.color ==Color.RED &&  //если есть левый ребенок и он красный
                    result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED){ //при этои у левого ребенка есть свой леый ребенок и он красный
                needRebalance = true;
                result = leftSwap(result); //то выполняем левый поворот
            }
            if(result.leftChild != null && result.leftChild.color ==Color.RED &&  // в случ если правый и левый ребенок имеет красный цвет
                    result.rightChild != null && result.rightChild.color == Color.RED){
                needRebalance = true;
                colorSwap(result);   //выполняем Swap
            }
        }
        while (needRebalance);  //если условия выше не выполняются выходим из цикла
        return  result;         //возвращаем результат
    }

    private Node rightSwap(Node node){
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    private Node leftSwap(Node node){             //левый переворот
        Node leftChild = node.leftChild;          //берем левого ребенка в отдельную переменную
        Node betweenChild = leftChild.rightChild; // берем элемент который будет менять своего родителя
        leftChild.rightChild = node;              //т.к левая нода красная, вместо правого ребенка красной ноды, назначаем наш элемент
        node.leftChild = betweenChild;            //у родителя левый ребенок, становится промежуточным
        leftChild.color = node.color;             //лев ребенок получ цвет своего родителя
        node.color = Color.RED;                   //сам корень опускется ниже и становится красным
        return leftChild;
    }

    private void colorSwap(Node node){       // смена цвета, это происходит когда у ноды два красных ребенка
        node.rightChild.color = Color.BLACK; //мы берем детей ноды
        node.leftChild.color = Color.BLACK; // присваеваем ей черный
        node.color = Color.RED;             //а сама нода становится красной
    }

    private  class Node{

        private int value;        //значение которое хранится в узле
        private Color color;     //цыет
        private Node leftChild;  //лев ребенок
        private Node rightChild; // прав ребенок


        public String toString(){
            return "Node{" +
                    "value=" + value +
                    ", color=" + color +
                    "}";
        }
    }

    private enum Color{
        RED, BLACK
    }

}
