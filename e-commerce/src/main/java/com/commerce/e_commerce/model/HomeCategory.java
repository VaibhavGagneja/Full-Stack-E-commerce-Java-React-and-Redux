package com.commerce.e_commerce.model;


import com.commerce.e_commerce.domain.HomeCategorySection;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String image;
    private String categoryId;
    private HomeCategorySection section;
}
