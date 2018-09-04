package hr.firma.sp.studentskiposao.algorithm.RedBlackBST

object Colors {
    val RED = true
    val BLACK = false
}

class RedBlackBST<Key : Comparable<Key>, Value> {

    private var root: Node<Key, Value>? = null

    private fun isRed(h: Node<Key, Value>?) : Boolean {
        if (h == null) return false
        return h.color!!
    }

    private fun rotateLeft(h: Node<Key,  Value>) : Node<Key, Value> {

        var x = h.right!!
        h.right = x.left
        x.left = h
        x.color = h.color
        h.color = Colors.RED
        x.N = h.N
        h.N = 1 + size(h.left) + size(h.right)

        return x

    }

    private fun rotateRight(h: Node<Key, Value>) : Node<Key, Value> {

        var x = h.left!!
        h.left = x.right
        x.right = h
        x.color = h.color
        h.color = Colors.RED
        x.N = h.N
        h.N = 1 + size(h.left) + size(h.right)

        return x
    }

    private fun flipColors(h: Node<Key, Value>) {

        h.color =  Colors.RED
        h.left?.color = Colors.BLACK
        h.right?.color = Colors.BLACK

    }

    private fun size(x: Node<Key, Value>?) : Int {
        if (x == null) return 0
        else return x.N
    }

    private fun put(h: Node<Key, Value>?, key: Key, value: Value) : Node<Key, Value> {

        if (h == null) return Node(key, value, 1, Colors.RED)

        var cmp: Int = key.compareTo(h.key!!)

        if (cmp < 0) h.left = put(h.left, key,  value)
        else if (cmp > 0) h.right = put(h.right, key, value)
        else h.value = value

        if (isRed(h.right) && !isRed(h.left)) h.setMe(rotateLeft(h))
        if (isRed(h.left) && isRed(h.left?.left)) h.setMe(rotateRight(h))
        if (isRed(h.left) && isRed(h.right)) flipColors(h)

        h.N = size(h.left) + size(h.right) + 1

        return h

    }

    private fun get(h: Node<Key, Value>?, key: Key) : Value? {
        if (h == null) return null
        var cmp = key.compareTo(h.key!!)
        if (cmp < 0) return get(h.left, key)
        else if (cmp > 0) return get(h.right, key)
        else return h.value
    }

    public fun size() : Int {
        return size(root)
    }

    public fun put(key: Key, value: Value) {
        root = put(root, key, value)
        root?.color = Colors.RED
    }

    public fun get(key: Key) : Value? {
        return get(root, key)
    }

}
