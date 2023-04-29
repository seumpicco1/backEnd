package com.example.intat3.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "announcement")


public class Anouncement {
    @Id
    @Column(name = "announcementId", nullable = false)
    private Integer announcementId;

    @Column(name = "announcemenTitle", nullable = false)
    private String announcemenTitle;

    @Column(name = "announcemenDescription", nullable = false)
    private String announcemenDescription;

    @Column(name = "publishDate", nullable = false)
    private Date publishDate;

    @Column(name = "closeDate", nullable = false)
    private Date closeDate;



    @Column(name = "announcementDisplay", nullable = false)
   private  String  announcementDisplay ;

    @Id
    @Column(name = "catagoryId", nullable = false)
    private Category



}
