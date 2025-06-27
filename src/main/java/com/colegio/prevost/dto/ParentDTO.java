package com.colegio.prevost.dto;

import com.colegio.prevost.model.Parent;
import com.colegio.prevost.model.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ParentDTO extends UserDTO {

    private Integer mobileNumber;

    public ParentDTO getParentDTO(Parent parent, User user) {
        ParentDTO parentDTO = new ParentDTO();
        parentDTO.setCode(user.getCode());
        parentDTO.setNames(user.getNames());
        parentDTO.setSurNames(user.getSurNames());
        parentDTO.setEmail(user.getEmail());
        parentDTO.setMobileNumber(parent.getMobileNumber());
        return parentDTO;
    }
}
