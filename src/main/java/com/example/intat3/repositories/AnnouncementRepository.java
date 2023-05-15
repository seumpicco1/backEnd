package com.example.intat3.repositories;

import com.example.intat3.Dto.AllAnnouncementDto;
import com.example.intat3.Dto.PageDTO;
import com.example.intat3.Entity.Announcement;
import com.example.intat3.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
//  List<Announcement> findAllByAnnouncementDisplay(String mode);
//  Page<Announcement> findAllByAnnouncementDisplay(String mode, Pageable pageable);
List<Announcement> findAllByCategory(Category category);
//
//   @Query("select a from Announcement a where a.closeDate < :currentDate ")
//  Page<Announcement> findByCloseDateAfterAndCategoryAndAnnouncementDisplayOrCloseDateIsNullAndCategoryAndAnnouncementDisplay(ZonedDateTime currentDate, Category category1, String mode1,  Category category, String mode,  Pageable pageable);
//  Page<Announcement> findByCategoryAndCloseDateBeforeAndAnnouncementDisplay(Category category, ZonedDateTime currentDate, String mode, Pageable pageable);
//
////     @Query("select a from Announcement a where a.closeDate < :currentDate ")
//    Page<Announcement> findByCloseDateIsNullOrCloseDateAfterAndAnnouncementDisplay(ZonedDateTime currentDate, String mode, Pageable pageable);
//    Page<Announcement> findByCloseDateBeforeAndAnnouncementDisplay(ZonedDateTime currentDate, String mode, Pageable pageable);
    List<Announcement> findByCloseDateIsNullOrCloseDateAfterAndPublishDateBefore(ZonedDateTime currentDate, ZonedDateTime currentDate1);
    List<Announcement> findAllByAnnouncementDisplay(String mode);
}
