package com.example.intat3.repositories;

import com.example.intat3.Entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement,Integer> {
    public List<Announcement> findByCategory(Integer id);

}
