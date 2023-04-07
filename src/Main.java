public class Main {
    static class ListNode {
        int value;
        ListNode next;

        public ListNode(int value) {
            this.value = value;
        }

        public ListNode(int value, ListNode next) {
            this.value = value;
            this.next = next;
        }
    }

    public static void main(String[] args) {
//        intro();
//        ListNode first = new ListNode(1, new ListNode(2, new ListNode(3)));
//        ListNode second = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(4))));
//        ListNode merge = merge(first, second);
//        iterate(merge); // 1 -> 1 -> 2 -> 2 -> 2 -> 3 -> 4

        ListNode node = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(4)))); // 1 -> 2 -> 2 -> 4
//        ListNode node1 = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(4, new ListNode(5))))); // 1 -> 2 -> 2 -> 4 -> 5
        iterate(node);
//        iterate(node1);

        System.out.println("Размер списка равен: " + size(node)); // 4
        System.out.println("Строковое представление списка: " + asString(node)); // [1 -> 2 -> 2 -> 4]

        System.out.println("Получение элемента списка по индексу: ");
        System.out.println(getByIndex(node, 0) != null ? "index=0:  " + getByIndex(node, 0) :
                "index=0:  " + new IndexOutOfBoundsException(0)); // 1
        System.out.println(getByIndex(node, 3) != null ? "index=3:  " + getByIndex(node, 3) :
                "index=3:  " + new IndexOutOfBoundsException(3)); // 4
        System.out.println(getByIndex(node, 4) != null ? "index=4:  " + getByIndex(node, 4) :
                "index=4:  " + new IndexOutOfBoundsException(4)); // new IndexOutOfBoundsException
    }

    /**
     * Посчитать размер списка.
     */
    static int size(ListNode head) {
        ListNode iter = head;
        int sizeNode = 0;
        while (iter != null) {
           sizeNode++;
           iter = iter.next;
        }
        return sizeNode;
    }

    /**
     * Написать строковое представление списка в формате
     * [first_value -> second_value -> ... -> last_value]
     */
    static String asString(ListNode head) {
        ListNode iter = head;
        String stringNode = "[";
        while (iter != null) {
            stringNode = stringNode + String.valueOf(iter.value) + " -> ";
            iter = iter.next;
        }
        stringNode = stringNode.substring(0, stringNode.length() - 4) + "]";
        return stringNode;
    }

    /**
     * Найти значение по индексу
     */
    static Integer getByIndex(ListNode head, int index) {
        ListNode iter = head;
        if (index >= size(iter)) {
            return null;
        }
        int count = 0;
        while (iter != null) {
            if (count == index) {
                break;
            }
            iter = iter.next;
            count++;
        }
        return iter.value;
    }

    /**
     * Дано 2 отсортированных связных списка.
     * Нужно их смержить и получить новый отсортированный связный список.
     *
     * (1, 2, 3), (1, 2, 2, 4) -> (1, 1, 2, 2, 2, 3, 4)
     * (1, 2), (3, 4, 5, 6) -> 1, 2, 3, 4, 5, 6
     */

    // (a && b) ~ !a || !b
    // a || b ~ !a && !b
    static ListNode merge(ListNode first, ListNode second) {
        ListNode head = null; // 1 -> 1 -> 2
        ListNode iterator = null; // 2
        while (first != null || second != null) {
            int nextValue = -1; // 2

            if (first == null) { // second != null
                nextValue = second.value;
                second = second.next;
            } else if (second == null) {
                nextValue = first.value;
                first = first.next;
            } else if (first.value > second.value) {
                nextValue = second.value;
                second = second.next;
            } else { // if (first.value <= second.value) {
                nextValue = first.value;
                first = first.next;
            }

            if (head == null) {
                head = new ListNode(nextValue);
                iterator = head;
            } else {
                iterator.next = new ListNode(nextValue);
                iterator = iterator.next;
            }
        }
        return head;
    }

    static void intro() {
        // head -> second -> third -> ... -> last -> null
        // 1 -> 2 -> 3
        ListNode third = new ListNode(3); // 3
        ListNode second = insertFirst(third, 2); // 2 -> 3
        ListNode first = insertFirst(second, 1); // 1 -> 2 -> 3
        iterate(first); // 1 -> 2 -> 3 -> null

        ListNode head = insertFirst(first, 0); // 0 -> 1 -> 2 -> 3
        iterate(head); // 0 -> 1 -> 2 -> 3 -> null

        insertLast(head, 4); // 0 -> 1 -> 2 -> 3 -> 4
        iterate(head); // 0 -> 1 -> 2 -> 3 -> 4 -> null

        remove(head, 2);
        iterate(head); // 0 -> 1 -> 3 -> 4 -> null

        head = reverse(head);
        iterate(head); // 4 -> 3 -> 1 -> 0 -> null
    }

    /**
     * Распечатать все элементы связного списка
     */
    static void iterate(ListNode node) {
        ListNode iter = node; // [null]
        while (iter != null) {
            System.out.print(iter.value + " -> ");
            iter = iter.next;
        }
        System.out.println("null");
    }

    /**
     * Создать список, получанный из head прибавлением value в начало
     */
    static ListNode insertFirst(ListNode head, int value) {
        return new ListNode(value, head);
    }

    /**
     * Добавить к существующему списку в конце значение value
     */
    static void insertLast(ListNode head, int value) {
        ListNode last = new ListNode(value);
        ListNode iter = head;
        while (iter.next != null) {
            iter = iter.next;
        }
        iter.next = last;
    }

    /**
     * Удаляет из списка первое вхождение value.
     */
    static void remove(ListNode head, int value) {
        // iter -> 1
        // iter.next -> 2
        // iter.next.next -> 3

        // -> .. -> 1 -> 1 -> null

        ListNode iter = head; // [null]
        while (iter.next != null) {
            if (iter.next.value == value) {
                iter.next = iter.next.next;
                break;
            }
            iter = iter.next;
        }
    }

    static ListNode reverse(ListNode head) {
        // 0 -> 1 -> 3 -> 4 -> null
        ListNode node = null;
        ListNode iterator = head;
        while (iterator != null) {
            if (node == null) {
                node = new ListNode(iterator.value);
            } else {
                node = insertFirst(node, iterator.value);
            }
            iterator = iterator.next;
        }
        return node;
    }

}