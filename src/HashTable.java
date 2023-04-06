//1. Начинаем реализацию хэш-таблицы с подготовки структуры и
//   необходимых классов.
//2. Давайте напишем реализацию односвязного списка, в котором мы
//   и будем хранить пары ключ-значение.
// 3. Стоит обратить внимание, что можно использовать как дженерики,
//   для обобщения возможных типов ключей и значений, так и
//   заранее определить для себя конкретные типы, которые будут
//   использоваться в качестве ключа и значения. Оба подхода
//   допустимы для реализации
//4. Добавляем массив связных списков с фиксированным размером (массив
//   бакетов), либо передаваемым в конструкторе.
//5. Хэш-таблица оперирует индексами, потому массив будет идеальным
//   вариантов для представления бакетов.
//6. Также реализуем метод вычисления индекса на основании хэш-кода
//   ключа.
//7. Реализуем метод поиска данных по ключу в хэш-таблице.
//8. Теперь, когда у нас есть базовая структура нашей хэш-таблицы, можно
//   написать алгоритм поиска элементов, включающий в себя поиск нужного
//   бакета и поиск по бакету.
//9. Необходимо реализовать методы добавления элементов в связный
//   список, если там еще нет пары с аналогичным ключом и удаления
//   элемента с аналогичным ключом из списка.
//10. Все значения ключей в хэш-таблице уникальны, а значит и в каждом из
//   связных список это правило будет также выполняться.
//11. Реализуем алгоритм добавления и удаления элементов из хэш-таблицы по ключу.
//12. Добавляем информацию о размере хэш-таблицы, а также алгоритм
//    увеличения количества бакетов при достижении количества элементов до
//    определенного размера относительно количества бакетов (load factor).
//13. Чтобы хэш-таблица сохраняла сложность поиска близкой к O(1), нам
//    необходимо контролировать количество бакетов, чтобы в них не
//    скапливалось слишком много элементов, которые способны увеличить
//    длительность операции поиска и добавления.
//14. В Java load factor для хэш-таблицы – 0.75, что значит, что при достижении
//    количества значений 75% от общего количества бакетов – это количество
//    необходимо увеличить. Это позволяет минимизировать шансы, что в
//    бакетах будет больше 1-2 значений, а значит сохранит скорость поиска на
//    уровне сложности O(1).



public class HashTable<T, K> {

    private static final int BASKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.75;
    private int size = 0;
    private Basket<T, K>[] baskets;

    public K get(T key) {
        int index = calculateIndex(key);
        Basket<T, K> basket = baskets[index];
        if(basket != null){
            return basket.find(key);

        }
        return null;
    }

    private void recalculate(){
        Basket<T, K>[] old = baskets;
        baskets = (Basket<T, K>[]) new Object[old.length * 2];
        for(int i = 0; i < old.length; i++){
            Basket basket = old[i];
            Basket.Node node = basket.head;
            while(node != null){
                put((T) node.entity.key, (K) node.entity.value);
                node = node.next;
            }
            old[i] = null;
        }
    }

    private int calculateIndex(T key){
        int hashcode = key.hashCode();
        int basketIndex = Math.abs(hashcode) % baskets.length;
        return basketIndex;
    }

    public HashTable(int size){
        baskets = (Basket<T, K>[]) new Object[size];
    }

    public HashTable(){
        this(BASKET_COUNT);
    }

    public boolean put(T key, K value){
        if(baskets.length * LOAD_FACTOR < size){
            recalculate();
        }
        int index = calculateIndex(key);
        Basket<T, K> basket = baskets[index];
        if(basket == null){
            basket = new Basket<>();
            baskets[index] = basket;
        }
        Entity<T, K> entity = new Entity<>();
        entity.key = key;
        entity.value = value;
        boolean add =  basket.add(entity);
        if(add) size++;
        return add;
    }

    public boolean remove(T key){
        int index = calculateIndex(key);
        Basket<T, K> basket = baskets[index];
        boolean dell =  basket.delete(key);
        if(dell) size--;
        return dell;
    }

    private class Basket<T, K> {
        Node head;

        public K find(T key) {
            Node node = head;
            while (node != null) {
                if (node.entity.key.equals(key)) {
                    return node.entity.value;
                } else {
                    node = node.next;
                }
            }
            return null;
        }

        private class Node {
            Node next;
            HashTable.Entity<T, K> entity;
        }

        public boolean add(Entity entity){
            Node node = new Node();
            node.entity = entity;
            if(head != null){
                Node currentNode = head;
                while (currentNode != null){
                    if(currentNode.entity.equals(entity)){
                        return false;
                    }
                    if(currentNode.next == null){
                        currentNode.next = node;
                        return true;
                    }else{
                        currentNode = currentNode.next;
                    }
                }
            }else {
                head = node;
                return true;
            }
            return false;
        }

        public boolean delete(T key){
            if(head != null){
                if(head.entity.equals(key)){
                    head = head.next;
                    return true;
                }else{
                    Node node = head;
                    while(node.next != null){
                        if(node.next.entity.key.equals(key)){
                            node.next = node.next.next;
                            return true;
                        }
                        node= node.next;
                    }
                }
            }
            return false;
        }

    }

    private static class Entity<T, K> {
        T key;
        K value;
    }

}
