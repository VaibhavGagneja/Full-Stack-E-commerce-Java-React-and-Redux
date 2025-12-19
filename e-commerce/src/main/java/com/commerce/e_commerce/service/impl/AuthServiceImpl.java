package com.commerce.e_commerce.service.impl;

import com.commerce.e_commerce.config.JwtProvider;
import com.commerce.e_commerce.domain.USER_ROLE;
import com.commerce.e_commerce.model.Cart;
import com.commerce.e_commerce.model.User;
import com.commerce.e_commerce.model.VerificationCode;
import com.commerce.e_commerce.repository.CartRepository;
import com.commerce.e_commerce.repository.UserRepository;
import com.commerce.e_commerce.repository.VerificationCodeRepository;
import com.commerce.e_commerce.request.LoginRequest;
import com.commerce.e_commerce.response.AuthResponse;
import com.commerce.e_commerce.response.SignupRequest;
import com.commerce.e_commerce.service.AuthService;
import com.commerce.e_commerce.service.EmailService;
import com.commerce.e_commerce.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())) {
            throw new Exception("Invalid OTP");
        }

        User user = userRepository.findByEmail(req.getEmail());

        if (user == null) {
            User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setPhoneNumber("8964765423");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public void sendLoginOtp(String email)  throws Exception {
        String SIGNIN_PREFIX = "signin_";

        if(email.startsWith(SIGNIN_PREFIX)){
            email = email.substring(SIGNIN_PREFIX.length());
            User user = userRepository.findByEmail(email);
            if(user == null){
                throw new Exception("User not found with email: " + email);
            }
        }
        VerificationCode isExist = verificationCodeRepository.findByEmail(email);
        if(isExist != null){
            verificationCodeRepository.delete(isExist);
        }
        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "Your Login OTP Code";
        String body = "Your OTP code for login is: " + otp + ". It is valid for 10 minutes.";

        emailService.sendVerificationOtpEmail(email,otp,subject,body);
    }

    @Override
    public AuthResponse validateOtp(LoginRequest req) throws Exception {
        String username = req.getEmail();
        String otp = req.getOtp();
        
        Authentication authentication = authenticate(username, otp);
        return null;
    }

    private Authentication authenticate(String username, String otp) {
        return null;
    }
}
