package com.example.intat3.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AnnouncementDto {

    private String announcementTitle;
    public  String getCategory (){
        return  categoryname == null ? "-" : categoryname.getName();
    }

    private String announcementDescription;
    private Date publishDate;
    private Date closeDate;
    private String announcementDisplay ;

    @JsonIgnore
     private  CategoryDTO categoryname;

}
