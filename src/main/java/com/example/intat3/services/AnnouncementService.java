package com.example.intat3.services;

import com.example.intat3.Dto.*;
import com.example.intat3.Entity.Announcement;
import com.example.intat3.Entity.Category;
import com.example.intat3.repositories.AnnouncementRepository;


import com.example.intat3.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementrepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public AnnouncementDto getAnnouncementById(Integer announcementId) {
        Announcement a = announcementrepository.findById(announcementId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Announcement id " + announcementId +  " " + "does not exist !!!"));
        return modelMapper.map(a,AnnouncementDto.class);
    }

    public List<AllAnnouncementDto> getAllAnnouncement() {
        List<Announcement> aa = announcementrepository.findAll();
        Collections.reverse(aa);
        return aa.stream().map(x->modelMapper.map(x, AllAnnouncementDto.class)).collect(Collectors.toList());
    }

    public AnnouncementDto createAnn( UpdateAnnouncementDto upAnn) {
        Category cat = categoryRepository.findById(upAnn.getCategoryId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category id " + upAnn.getCategoryId() + " does not exist !!!"));
        Announcement aa = modelMapper.map(upAnn,Announcement.class);
        aa.setCategory(cat);
        announcementrepository.saveAndFlush(aa);
        return modelMapper.map(aa,AnnouncementDto.class);
    }

    public void deleteAnn(int id){
        Announcement a = announcementrepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Announcement id " + id +  " " + "does not exist !!!"));
        announcementrepository.delete(a);
    }

    public UpdateDTO updateAnn(int id, UpdateAnnouncementDto newAnn) {
        Announcement curAnn = announcementrepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement id " + id + " " + "does not exist !!!"));
        Category cat = categoryRepository.findById(newAnn.getCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category id " + newAnn.getCategoryId() + " does not exist !!!"));
        Announcement nAnn = modelMapper.map(newAnn,Announcement.class);
            curAnn.setCategory(cat);
            curAnn.setAnnouncementTitle(nAnn.getAnnouncementTitle());
            curAnn.setAnnouncementDescription(nAnn.getAnnouncementDescription());
            curAnn.setPublishDate(nAnn.getPublishDate());
            curAnn.setCloseDate(nAnn.getCloseDate());
            curAnn.setAnnouncementDisplay(nAnn.getAnnouncementDisplay());
        announcementrepository.saveAndFlush(curAnn) ;
        return  modelMapper.map(curAnn,UpdateDTO.class);
    }

    public PageDTO<AllAnnouncementDto> getAllPageAnn(int page, int size, String mode, int id){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
//         List<Announcement> all = announcementrepository.findAllByAnnouncementDisplay(mode.equals("active")?"Y":"N");
//         List<Announcement> all = announcementrepository.findAll();
//         all.forEach(x -> { // loop เพื่อเช็ค close date ของข้อมูลที่ทำการ query ออกมา
//             ZonedDateTime currentTime = ZonedDateTime.now();
//             if(x.getCloseDate() != null){
//                 ZonedDateTime xTime = x.getCloseDate();
//                 if(currentTime.compareTo(xTime) > 0 && x.getAnnouncementDisplay().equals("Y")){// มากกว่า 0 คือเลยเวลา close date มาแล้ว
//                     x.setAnnouncementDisplay("N");
//                 }
//             }
//             if(x.getPublishDate() != null && currentTime.compareTo(x.getPublishDate()) < 0){
//                 x.setAnnouncementDisplay("N");
//             }
//             announcementrepository.saveAndFlush(x);
//         });
        Page<Announcement> ann = getAnnByModeNCategory(mode, id, pageable);
        List<AllAnnouncementDto> ListDto = ann.getContent().stream().map(x->modelMapper.map(x, AllAnnouncementDto.class)).collect(Collectors.toList());
        return new PageDTO<>(ListDto,ann.isLast(),ann.isFirst(),ann.getTotalPages(),ann.getNumberOfElements(),ann.getSize(),ann.getNumber()); // รี
    }

    public Page<Announcement> getAnnByModeNCategory(String mode, int id, Pageable pageable){
        if(id == 0){
            return mode.equals("active")?announcementrepository.findByCloseDateIsNullOrCloseDateAfterAndAnnouncementDisplay(ZonedDateTime.now(), "Y",  pageable):announcementrepository.findByCloseDateBeforeAndAnnouncementDisplay(ZonedDateTime.now(), "Y",  pageable);
//          หา Ann โดยที่ id == 0 จะ return ทุกตัวโดยสนแค่ annDisplay เท่านั้น
//             return announcementrepository.findAllByAnnouncementDisplay(mode.equals("active")?"Y":"N", pageable); //Y no cat sort
        }else {
//          หา Ann โดยที่ id != 0 จะทำการหา Object ของ Category เพื่อไปใช้ใน findAllByCategoryAndAnnouncementDisplay()
            Category cat = categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category id " + id + " does not exist !!!"));
            return mode.equals("active")?announcementrepository.findByCloseDateAfterAndCategoryAndAnnouncementDisplayOrCloseDateIsNullAndCategoryAndAnnouncementDisplay( ZonedDateTime.now(), cat,"Y",  cat,"Y", pageable):announcementrepository.findByCategoryAndCloseDateBeforeAndAnnouncementDisplay(cat, ZonedDateTime.now(), "Y", pageable);// Y with cat sort
        }
    }

    public List<AllAnnouncementDto> getAnnByDisplay(String mode){
//         announcementrepository.findAllByAnnouncementDisplay(mode.equals("active")?"Y":"N").forEach( x -> { // loop เพื่อเช็ค close date ของข้อมูลที่ทำการ query ออกมา
//         announcementrepository.findAll().forEach( x -> { // loop เพื่อเช็ค close date ของข้อมูลที่ทำการ query ออกมา
//             ZonedDateTime currentTime = ZonedDateTime.now();
//             if(x.getCloseDate() != null){
//                 if(currentTime.compareTo(x.getCloseDate()) > 0 && x.getAnnouncementDisplay().equals("Y")){// มากกว่า 0 คือเลยเวลา close date มาแล้ว
//                     x.setAnnouncementDisplay("N");
//                 }
//             }
//             if(x.getPublishDate() != null && currentTime.compareTo(x.getPublishDate()) < 0){
//                 x.setAnnouncementDisplay("N");
//             }
//             announcementrepository.saveAndFlush(x);
//         });
        List<Announcement> all = mode.equals("active")?announcementrepository.findByCloseDateAfter(ZonedDateTime.now()):announcementrepository.findByCloseDateBefore(ZonedDateTime.now());
        return all.stream().map(x->modelMapper.map(x, AllAnnouncementDto.class)).collect(Collectors.toList());
    }


}
