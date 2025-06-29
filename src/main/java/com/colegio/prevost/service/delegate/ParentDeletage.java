package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.ParentDTO;

public interface ParentDeletage {
    ParentDTO getParentById(Long id);
    List<ParentDTO> getAllParents();
    ParentDTO createParent(ParentDTO parent);
    ParentDTO updateParent(Long id, ParentDTO parent);
    void deleteParent(Long id);
}
