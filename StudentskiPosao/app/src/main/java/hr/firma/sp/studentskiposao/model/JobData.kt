package hr.firma.sp.studentskiposao.model

data class JobData(
        var id: Long = 0L,
        var title: String = "",
        var description: String = "",
        var image: Int = 0,
        var workTime: String = "",
        var price: Double = 0.0,
        var location: String = "",
        var company: String = "",
        var category: String = "",
        var dateTo: String = ""
) : AbstractData() {

    init {

    }

    override fun compareTo(item: AbstractData, fieldName: String) : Int {
        item as JobData
        var compared = 0
        when(fieldName) {
            "id" -> compared = id.compareTo(item.id)
            "title" -> compared = title.compareTo(item.title)
            "description" -> compared = description.compareTo(item.description)
            "price" -> compared = price.compareTo(item.price)
            "location" -> compared = location.compareTo(item.location)
            "company" -> compared = company.compareTo(item.company)
            "category" -> compared = category.compareTo(item.category)
            "dateTo" -> compared = dateTo.compareTo(item.dateTo)
            else -> compared = 0
        }

        return compared
    }

    override fun getValueForField(fieldName: String): String {
        var value = ""
        when(fieldName) {
            "id" -> value = id.toString()
            "title" -> value = title
            "description" -> value = description
            "price" -> value = price.toString()
            "location" -> value = location
            "company" -> value = company
            "category" -> value = category
            "dateTo" -> value = dateTo
            else -> value = ""
        }

        return value
    }

}