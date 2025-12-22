package com.commerce.e_commerce.service;

import com.commerce.e_commerce.model.Seller;
import com.commerce.e_commerce.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);

}
