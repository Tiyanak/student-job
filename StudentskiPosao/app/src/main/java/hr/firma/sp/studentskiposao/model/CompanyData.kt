package hr.firma.sp.studentskiposao.model

data class CompanyData(
        var id: Long = 0,
        var name: String = "",
        var description: String = "",
        var image: Int = 0
) : AbstractData() {

    override fun compareTo(item: AbstractData, fieldName: String): Int {
        item as CompanyData
        var compared = 0
        when(fieldName) {
            "id" -> compared = id.compareTo(item.id)
            "name" -> compared = name.compareTo(item.name)
            "description" -> compared = description.compareTo(item.description)
            else -> compared = 0
        }

        return compared
    }

}