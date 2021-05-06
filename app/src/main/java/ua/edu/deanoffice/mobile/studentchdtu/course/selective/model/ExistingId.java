package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import lombok.Data;

@Data
public class ExistingId {
    private int id;

    public ExistingId(int id){
        this.id = id;
    }
}
