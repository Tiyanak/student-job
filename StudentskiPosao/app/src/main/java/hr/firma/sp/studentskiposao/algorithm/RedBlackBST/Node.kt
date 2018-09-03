package hr.firma.sp.studentskiposao.algorithm.RedBlackBST

class Node<Key, Value> {

    var key: Key? = null
    var value: Value? = null
    var left: Node<Key, Value>? = null
    var right: Node<Key,Value>? = null
    var N: Int = 0
    var color: Boolean? = null

    constructor()

    constructor(key: Key, value: Value, N: Int, color: Boolean) {
        this.key = key
        this.value = value
        this.N = N
        this.color = color
    }

    fun setMe(x: Node<Key, Value>) {
        key = x.key
        value = x.value
        left = x.left
        right = x.right
        N = x.N
        color = x.color
    }

}