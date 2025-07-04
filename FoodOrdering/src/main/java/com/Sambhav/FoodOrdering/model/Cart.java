package com.Sambhav.FoodOrdering.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Users customer;

    private Long total;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private List<CartItem> item = new ArrayList<>();


}
