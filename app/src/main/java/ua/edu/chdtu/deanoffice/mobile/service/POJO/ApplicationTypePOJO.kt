package ua.edu.chdtu.deanoffice.mobile.service.POJO

data class ApplicationTypePOJO(
    private val id: Int,
    val name: String
    ){

    fun toApplicationTypeId() : ApplicationTypeIdPOJO{
        return ApplicationTypeIdPOJO(id)
    }
}