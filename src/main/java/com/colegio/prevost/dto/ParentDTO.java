package com.colegio.prevost.dto;

import com.colegio.prevost.model.Parent;
import com.colegio.prevost.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentDTO extends UserDTO {

    private Integer mobileNumber;

    public ParentDTO getParentDTOFromEntity(Parent parent, User user) {
        ParentDTO parentDTO = new ParentDTO();
        parentDTO.setNames(user.getNames());
        parentDTO.setSurNames(user.getSurNames());
        parentDTO.setEmail(user.getEmail());
        parentDTO.setMobileNumber(parent.getMobileNumber());
        return parentDTO;
    }

    public Integer getMobileNumber() {
        return mobileNumber != null ? mobileNumber : null;
    }

}
