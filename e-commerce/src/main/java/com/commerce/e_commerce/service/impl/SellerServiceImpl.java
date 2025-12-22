package com.commerce.e_commerce.service.impl;

import com.commerce.e_commerce.config.JwtProvider;
import com.commerce.e_commerce.domain.AccountStatus;
import com.commerce.e_commerce.domain.USER_ROLE;
import com.commerce.e_commerce.exception.SellerException;
import com.commerce.e_commerce.model.Address;
import com.commerce.e_commerce.model.BankDetails;
import com.commerce.e_commerce.model.BusinessDetails;
import com.commerce.e_commerce.model.Seller;
import com.commerce.e_commerce.repository.AddressRespository;
import com.commerce.e_commerce.repository.SellerRepository;
import com.commerce.e_commerce.service.SellerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRespository addressRespository;

    @Override
    public Seller getSellerProfile(String jwt) {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) {
        Seller sellerExists = sellerRepository.findByEmail(seller.getEmail());
        if (sellerExists != null) {
            throw new IllegalArgumentException("Seller with email " + seller.getEmail() + " already exists");
        }
        Address savedAddress = addressRespository.save(seller.getPickupAddress());
        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException{
        return sellerRepository.findById(id).orElseThrow(() -> new SellerException("Seller with id " + id + " not found"));
    }

    @Override
    public Seller getSellerByEmail(String email) {
        Seller seller = sellerRepository.findByEmail(email);
        if (seller == null) {
            throw new EntityNotFoundException("Seller with email " + email + " not found");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existingSeller = this.getSellerById(id);

        if (seller.getSellerName() != null)
            existingSeller.setSellerName(seller.getSellerName());

        if (seller.getMobile() != null)
            existingSeller.setMobile(seller.getMobile());

        // ---------- Business Details ----------
        if (seller.getBusinessDetails() != null) {

            if (existingSeller.getBusinessDetails() == null)
                existingSeller.setBusinessDetails(new BusinessDetails());

            BusinessDetails req = seller.getBusinessDetails();
            BusinessDetails bd = existingSeller.getBusinessDetails();

            if (req.getBusinessName() != null)
                bd.setBusinessName(req.getBusinessName());

            if (req.getBusinessEmail() != null)
                bd.setBusinessEmail(req.getBusinessEmail());

            if (req.getBusinessMobile() != null)
                bd.setBusinessMobile(req.getBusinessMobile());

            if (req.getBusinessAddress() != null)
                bd.setBusinessAddress(req.getBusinessAddress());

            if (req.getLogo() != null)
                bd.setLogo(req.getLogo());

            if (req.getBanner() != null)
                bd.setBanner(req.getBanner());
        }

        // ---------- Bank Details ----------
        if (seller.getBankDetails() != null) {

            if (existingSeller.getBankDetails() == null)
                existingSeller.setBankDetails(new BankDetails());

            BankDetails req = seller.getBankDetails();
            BankDetails bd = existingSeller.getBankDetails();

            if (req.getAccountHolderName() != null)
                bd.setAccountHolderName(req.getAccountHolderName());

            if (req.getAccountNumber() != null)
                bd.setAccountNumber(req.getAccountNumber());

            if (req.getIfscCode() != null)
                bd.setIfscCode(req.getIfscCode());

            if (req.getBankName() != null)
                bd.setBankName(req.getBankName());
        }

        // ---------- Pickup Address ----------
        if (seller.getPickupAddress() != null) {

            if (existingSeller.getPickupAddress() == null)
                existingSeller.setPickupAddress(new Address());

            Address req = seller.getPickupAddress();
            Address addr = existingSeller.getPickupAddress();

            if (req.getAddress() != null)
                addr.setAddress(req.getAddress());

            if (req.getCity() != null)
                addr.setCity(req.getCity());

            if (req.getState() != null)
                addr.setState(req.getState());

            if (req.getPinCode() != null)
                addr.setPinCode(req.getPinCode());

            if (req.getMobile() != null)
                addr.setMobile(req.getMobile());
        }

        if (seller.getGSTIN() != null)
            existingSeller.setGSTIN(seller.getGSTIN());

        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
        Seller seller = getSellerById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) {
        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
        Seller seller = getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }
}
