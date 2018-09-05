package hr.firma.sp.studentskiposao.algorithm;

// isto ko u knjizi - Key i Value su tipovi koje zadajes prilikom kreiranja objekta RedBlackBST
public class RedBlackBST<Key extends Comparable<Key>, Value> {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node<Key, Value> root;

    // primjeti, u knjizi je h.color (sintaksa C# jezika), ovdje je razlika samo u sintaksi - h.getColor()
    // slicno u nastavcima set() get()
    private boolean isRed(Node<Key, Value> h) {
        if (h == null) return false;
        return h.getColor() == RED;
    }

    private Node<Key, Value> rotateLeft(Node<Key, Value> h) {
        Node<Key, Value> x = h.getRight();
        h.setRight(x.getLeft());
        x.setLeft(h);
        x.setColor(h.getColor());
        h.setColor(RED);
        x.setN(h.getN());
        h.setN(1 + size(h.getLeft()) + size(h.getRight()));

        return x;
    }

    private Node<Key, Value> rotateRight(Node<Key, Value> h) {
        Node<Key, Value> x = h.getLeft();
        h.setLeft(x.getRight());
        x.setRight(h);
        x.setColor(h.getColor());
        h.setColor(RED);
        x.setN(h.getN());
        h.setN(1 + size(h.getLeft()) + size(h.getRight()));

        return x;
    }

    private void flipColors(Node<Key, Value> h) {
        h.setColor(RED);
        h.getLeft().setColor(BLACK);
        h.getRight().setColor(BLACK);
    }

    private int size(Node<Key, Value> x) {
        if (x == null) return 0;
        else return x.getN();
    }

    private Node<Key, Value> put(Node<Key, Value> h, Key key, Value val) {
        if (h == null) return new Node(key, val, 1, RED);

        int cmp = key.compareTo(h.getKey());

        if (cmp < 0) h.setLeft(put(h.getLeft(), key, val));
        else if (cmp > 0) h.setRight(put(h.getRight(), key, val));
        else h.setVal(val);

        if (isRed(h.getRight()) && !isRed(h.getLeft())) h = rotateLeft(h);
        if (isRed(h.getLeft()) && isRed(h.getLeft().getLeft())) h = rotateRight(h);
        if (isRed(h.getLeft()) && isRed(h.getRight())) flipColors(h);

        h.setN(size(h.getLeft()) + size(h.getRight()) + 1);

        return h;
    }

    // ova metoda opisana pod binary search tree obicnim cini mi se, ne podredblackbst, ali je ista za oba algoritma
    private Value get(Node<Key,  Value> h, Key key) {
        if (h == null) return null;
        int cmp = key.compareTo(h.getKey());
        if (cmp < 0) return get(h.getLeft(), key);
        else if (cmp > 0) return get(h.getRight(),  key);
        else return h.getVal();
    }

    public int size() {
        return size(root);
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.setColor(RED);
    }

    public Value get(Key key) {
        return get(root, key);
    }

}
