import java.util.ArrayList;
import java.util.List;

public class Tree {
    Node root;    //начало дерева

    ///////////Обход в глубину

    //публичная функция поиска необходиимого значения
    public boolean exist(int value){
        if(root != null){                              // проверка что дерево заполнено
            Node node = find (root, value);            // запуск поиска
            if (node != null){                         // если нашли возвращаем
                return true;
            }
        }return false;
    }


    //поиск ноды с определенным значением в нутри
    // ссылка на ноду, значение которое хотим проверить
    private Node find(Node node, int value){
        if(node.value == value){                       //если наша нода равна value
            return node;                               // возвращаем ноду
        }else {                                       //если это не так, далее берем детей и перебираем
            for (Node child : node.children){
                Node result = find(child, value);     // рекурсивно
                if(result != null){
                    return result;
                }
            }
        }
        return  null;
    }


    //////////////Обход в ширину

    private Node findwidth(int value){
        List<Node> line = new ArrayList<>();          //лист нодов-линия
        line.add(root);                               // добавляем в список
        while (line.size() > 0) {                     // до тех пор пока в нашем обьекте есть дети
            List<Node> nextLine = new ArrayList<>();  //заводим новый список
            for (Node node:line) {
                if(node.value == value){              // если нашли возвращаем
                    return node;
                }
                nextLine.addAll(node.children);       //если нет добавляем всех детей в список
            }
            line = nextLine;                          //обновляем линию и перезапускаем поиск
        }
        return null;
    }



    public class Node{
        int value;
        List<Node> children;
    }
}
