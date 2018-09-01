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
)