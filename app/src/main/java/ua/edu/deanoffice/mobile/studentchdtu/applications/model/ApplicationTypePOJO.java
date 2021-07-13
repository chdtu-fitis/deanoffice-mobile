package ua.edu.deanoffice.mobile.studentchdtu.applications.model;

import androidx.annotation.Keep;

@Keep
public class ApplicationTypePOJO {
    private int id;
    public String name;

    public ApplicationTypeIdPOJO toApplicationTypeId() {
        return new ApplicationTypeIdPOJO(id);
    }

}
