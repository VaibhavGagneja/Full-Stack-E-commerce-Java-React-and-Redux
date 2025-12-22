package com.commerce.e_commerce.service.impl;

import com.commerce.e_commerce.domain.HomeCategorySection;
import com.commerce.e_commerce.model.Deal;
import com.commerce.e_commerce.model.Home;
import com.commerce.e_commerce.model.HomeCategory;
import com.commerce.e_commerce.repository.DealRespository;
import com.commerce.e_commerce.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final DealRespository dealRepository;

    @Override
    public Home createHomePageData(List<HomeCategory> allCategories) {
        List<HomeCategory> gridCategories = allCategories.stream().filter(category -> category.getSection() == HomeCategorySection.GRID).collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream().filter(category -> category.getSection() == HomeCategorySection.SHOP_BY_CATEGORIES).collect(Collectors.toList());
        List<HomeCategory> electricCategories = allCategories.stream().filter(category -> category.getSection() == HomeCategorySection.ELECTRIC_CATEGORIES).collect(Collectors.toList());

        List<HomeCategory> dealCategories = allCategories.stream().filter(category -> category.getSection() == HomeCategorySection.DEALS).toList();
        List<Deal> createdDeals = new ArrayList<>();

        if (dealRepository.findAll().isEmpty()) {
            List<Deal> deals = allCategories.stream().filter(category -> category.getSection() == HomeCategorySection.DEALS).map(category -> new Deal(null, 10, category)).collect(Collectors.toList());
            createdDeals = dealRepository.saveAll(deals);
        } else createdDeals = dealRepository.findAll();

        Home home = new Home();
        home.setGrid(gridCategories);
        home.setShopByCategories(shopByCategories);
        home.setElectricCategories(electricCategories);
        home.setDeals(createdDeals);
        home.setDealCategories(dealCategories);

        return home;
    }
}
