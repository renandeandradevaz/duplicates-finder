package com.renanvaz.duplicatesfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String zipCode;
    private String address;
}