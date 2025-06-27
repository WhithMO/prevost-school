package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.AnnouncementDTO;
import com.colegio.prevost.model.Announcement;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {

    AnnouncementDTO toDto(Announcement announcement);
    Announcement toEntity(AnnouncementDTO announcementDTO);

}
