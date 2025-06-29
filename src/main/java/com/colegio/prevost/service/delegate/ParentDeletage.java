package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.ParentDTO;
import com.colegio.prevost.model.Parent;
import com.colegio.prevost.model.User;

public interface ParentDeletage {
    Parent getParentById(Long id);
    List<Parent> getAllParents();
    Parent createParent(Parent parent);
    ParentDTO updateParent(Long id, Parent parent, User user);
    void deleteParent(Long id);
}
