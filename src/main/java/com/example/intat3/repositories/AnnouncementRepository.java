package com.example.intat3.repositories;

import com.example.intat3.Dto.AllAnnouncementDto;
import com.example.intat3.Dto.PageDTO;
import com.example.intat3.Entity.Announcement;
import com.example.intat3.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
  public Page<Announcement> findAllByAnnouncementDisplay(String mode, Pageable pageable);
  public Page<Announcement> findAllByCategoryAndAnnouncementDisplay(Category category, String mode, Pageable pageable);
//   public Page<AllAnnouncementDto> findAllByCategoryAndAnnouncementDisplay(Category category, String mode, Pageable pageable);
}
