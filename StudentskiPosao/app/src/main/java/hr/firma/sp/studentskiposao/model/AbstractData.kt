package hr.firma.sp.studentskiposao.model

abstract class AbstractData {

    abstract fun compareTo(item: AbstractData, fieldName: String) : Int

    abstract fun getValueForField(fieldName: String) : String

}