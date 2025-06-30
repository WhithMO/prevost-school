package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.ParentDTO;

public interface ParentDeletage {
    ParentDTO getParentByUsername(String id);
    List<ParentDTO> getAllParents();
    ParentDTO createParent(ParentDTO parent);
    ParentDTO updateParent(String username, ParentDTO parent);
    void deleteParent(String username);
}
