package hr.firma.sp.studentskiposao.algorithm;

// Parametrizirana klassa - kad se kreira objekt ove klase, definira se tip Key i Value
// tako ova klasa mozebiti neovisna otipu podatka jer raditce sa cime god dobije (int, string, double ...)
// npr. new Node<String, int>() gdje polje key je onda tipa String, a polje val je tipa int
// left i right su cvorovi djeca od trenutnog cvora - Node je cvor u stablu
// ovo je znaci samo najobicniji model za red black tree
public class Node<Key, Value> {

    Key key;
    Value val;
    Node<Key, Value> left, right;
    int N;
    boolean color;

    public Node(Key key, Value val, int N, boolean color) {
        this.key = key;
        this.val = val;
        this.N = N;
        this.color = color;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Value getVal() {
        return val;
    }

    public void setVal(Value val) {
        this.val = val;
    }

    public Node<Key, Value> getLeft() {
        return left;
    }

    public void setLeft(Node<Key, Value> left) {
        this.left = left;
    }

    public Node<Key, Value> getRight() {
        return right;
    }

    public void setRight(Node<Key, Value> right) {
        this.right = right;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
