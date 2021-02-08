package com.example.demopro.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogLogoItemsBean {
    private int id;
    private int watching_numbers;
    private int comment_numbers;
    private int thumbsup_numbers;
}
