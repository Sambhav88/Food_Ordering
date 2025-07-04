package com.Sambhav.FoodOrdering.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInformation
{
    private String email;
    private String mobile;
    private String twitter;
    private String instagram;
}
