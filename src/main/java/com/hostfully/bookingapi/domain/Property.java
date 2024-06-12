package com.hostfully.bookingapi.domain;

import com.hostfully.bookingapi.domain.validation.DomainValidation;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class Property implements DomainValidation {

    private Long id;

    private String name;

    public Property(Long id) {
        this.id = id;
    }

    public Property(String name){
        this.name = name;
    }

    public Property(Long id, String name) {
        this(name);
        this.id = id;
        validate();
    }

    public void validate(){
        validateField(id == null && StringUtils.isEmpty(name), "Property name is required.");
    }
}
