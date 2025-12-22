package com.commerce.e_commerce.service;

import com.commerce.e_commerce.model.Home;
import com.commerce.e_commerce.model.HomeCategory;

import java.util.List;

public interface HomeService {
    public Home createHomePageData(List<HomeCategory> categories);
}
