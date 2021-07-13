package ua.edu.deanoffice.mobile.studentchdtu.course.selective.model;

import androidx.annotation.Keep;

import lombok.Data;

@Data
@Keep
public class ExistingId {
    private int id;

    public ExistingId(int id){
        this.id = id;
    }
}
