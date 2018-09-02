package hr.firma.sp.studentskiposao.algorithm

import hr.firma.sp.studentskiposao.model.AbstractData
import hr.firma.sp.studentskiposao.model.CompanyData
import hr.firma.sp.studentskiposao.model.JobData

class Sort {

    fun quickSort(items: MutableList<AbstractData>, fieldName: String) {
        items.shuffle()
        sort(items, 0, items.size - 1, fieldName)
    }

    fun sort(items: MutableList<AbstractData>, lo: Int, hi: Int, fieldName: String) {
        if (hi <= lo) { return }
        val j: Int = partition(items, lo, hi, fieldName)
        sort(items, lo, j-1, fieldName)
        sort(items, j+1, hi, fieldName)
    }

    fun partition(items: MutableList<AbstractData>, lo: Int, hi: Int, fieldName: String) : Int {

        var i: Int = lo
        var j: Int = hi + 1

        val v = items.get(lo)

        while(true) {
            while(less(items.get(++i), v, fieldName)) { if (i == hi) break }
            while(less(v, items.get(--j), fieldName)) { if (j == lo) break }
            if (i >= j) break
            exch(items, i,  j)
        }

        exch(items, lo, j)
        return j

    }

    fun exch(items: MutableList<AbstractData>, index1: Int, index2: Int) {
        val item = items.get(index1)
        items.set(index1, items.get(index2))
        items.set(index2, item)
    }

    fun less(item1: AbstractData, item2: AbstractData, fieldName: String) : Boolean {
        return item1.compareTo(item2, fieldName) < 0
    }


}