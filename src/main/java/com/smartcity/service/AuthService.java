package com.smartcity.service;

import com.smartcity.dto.auth.AuthResponse;
import com.smartcity.dto.auth.LoginRequest;
import com.smartcity.dto.auth.RegisterRequest;
import com.smartcity.exception.BadRequestException;
import com.smartcity.exception.UnauthorizedException;
import com.smartcity.model.AppUser;
import com.smartcity.model.UserRole;
import com.smartcity.security.JwtService;
import jakarta.annotation.PostConstruct;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final ConcurrentHashMap<String, AppUser> usersByEmail = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  @PostConstruct
  public void initAdmin() {
    if (!usersByEmail.containsKey("admin@smartcity.com")) {
      AppUser admin = new AppUser();
      admin.setId(idCounter.getAndIncrement());
      admin.setName("System Admin");
      admin.setEmail("admin@smartcity.com");
      admin.setPasswordHash(passwordEncoder.encode("admin123"));
      admin.setRole(UserRole.ADMIN);
      usersByEmail.put(admin.getEmail(), admin);
    }
  }

  public void register(RegisterRequest request) {
    String email = request.getEmail().toLowerCase(Locale.ROOT).trim();
    if (usersByEmail.containsKey(email)) {
      throw new BadRequestException("Email already registered");
    }

    AppUser user = new AppUser();
    user.setId(idCounter.getAndIncrement());
    user.setName(request.getName().trim());
    user.setEmail(email);
    user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    user.setRole(UserRole.USER);
    usersByEmail.put(email, user);
  }

  public AuthResponse login(LoginRequest request) {
    String email = request.getEmail().toLowerCase(Locale.ROOT).trim();
    AppUser user = usersByEmail.get(email);
    if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
      throw new UnauthorizedException("Invalid email or password");
    }
    return new AuthResponse(jwtService.generateToken(user), user.getRole().name());
  }
}

