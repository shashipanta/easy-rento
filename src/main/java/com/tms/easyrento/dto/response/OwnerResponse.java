package com.tms.easyrento.dto.response;

import com.tms.easyrento.model.owner.Owner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author shashi
 * @version 1.0.0
 * @since 12/6/23 8:55 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerResponse {

    private Long id;

    private String name;
    private String nameNp;

    private boolean isActive;

    public OwnerResponse(Owner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.nameNp = owner.getNameNp();
        this.isActive = owner.isActive();
    }
}
