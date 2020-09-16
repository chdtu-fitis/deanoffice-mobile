package ua.edu.deanoffice.mobile.studentchdtu.service.pojo;

public class ApplicationTypePOJO {
    private int id;
    public String name;

    public ApplicationTypeIdPOJO toApplicationTypeId() {
        return new ApplicationTypeIdPOJO(id);
    }

}
