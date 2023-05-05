package com.example.intat3.services;

import com.example.intat3.Dto.AllAnnouncementDto;
import com.example.intat3.Dto.AnnouncementDto;
import com.example.intat3.Dto.UpdateAnnouncementDto;
import com.example.intat3.Dto.UpdateDTO;
import com.example.intat3.Entity.Announcement;
import com.example.intat3.Entity.Category;
import com.example.intat3.repositories.AnnouncementRepository;


import com.example.intat3.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        System.out.println(a);
        System.out.println(modelMapper.map(a,AnnouncementDto.class));

        return modelMapper.map(a,AnnouncementDto.class);

    }


    public List<AllAnnouncementDto> getAllAnnouncement() {
        List<Announcement> aa = announcementrepository.findAll();
        Collections.reverse(aa);
        return aa.stream().map(x->modelMapper.map(x, AllAnnouncementDto.class)).collect(Collectors.toList());
    }


    public AnnouncementDto createAnn( UpdateAnnouncementDto upAnn) {
        System.out.println(upAnn.getCategoryId());

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
    return  modelMapper.map(curAnn,UpdateDTO.class); // เอาอันที่มันเปลี่ยนข้อมูลแล้วมา mapDto เลยเทสผ่าน
//     return  modelMapper.map(nAnn,UpdateDTO.class); มันเอาอันที่ส่งมา มาแปลงเป็นตัว announcent แล้วมันไม่มี catName มีแต่ catId


}




}
