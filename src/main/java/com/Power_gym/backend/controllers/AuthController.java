package com.Power_gym.backend.controllers;

import com.Power_gym.backend.DTO.JwtResponseDTO;
import com.Power_gym.backend.DTO.LoginRequestDTO;
import com.Power_gym.backend.DTO.common.ResponseMessage;
import com.Power_gym.backend.DTO.SignupRequestDTO;
import com.Power_gym.backend.Util.IdGenerationUtil;
import com.Power_gym.backend.models.Privilege;
import com.Power_gym.backend.models.Role;
import com.Power_gym.backend.models.User;
import com.Power_gym.backend.models.enums.ERole;
import com.Power_gym.backend.repository.PrivilegeDetailRepository;
import com.Power_gym.backend.repository.RoleRepository;
import com.Power_gym.backend.repository.UserRepository;
import com.Power_gym.backend.security.jwt.JwtUtils;
import com.Power_gym.backend.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/power-gym/auth")
@RequiredArgsConstructor
public class AuthController {
    final AuthenticationManager authenticationManager;

    final UserRepository userRepository;

    final RoleRepository roleRepository;

    final PasswordEncoder encoder;

    final JwtUtils jwtUtils;

    final IdGenerationUtil idGenerationUtil;

    @Autowired
    final PrivilegeDetailRepository privilegeDetailRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        // Fetch roles as Set<Role>
        Set<Role> rolesSet = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDetails.getUsername()))
                .getRoles();

        // Process each Role object
        List<String> privileges = new ArrayList<>();
        for (Role role : rolesSet) {
            privileges = privilegeDetailRepository.findPrivilegeIdsByRole(role)
                    .stream()
                    .map(Privilege::getPrivilegeName)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(new JwtResponseDTO(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles, privileges));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_MEMBER).orElseThrow(() -> new RuntimeException("Error: ROLE_MEMBER Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error:ROLE_ADMIN Role is not found."));
                        roles.add(adminRole);
                    }
                    case "trainer" -> {
                        Role modRole = roleRepository.findByName(ERole.ROLE_TRAINER).orElseThrow(() -> new RuntimeException("Error: ROLE_TRAINER Role is not found."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_MEMBER).orElseThrow(() -> new RuntimeException("Error: ROLE_MEMBER Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setMobileNum(signUpRequest.getMobileNum());
        user.setName(signUpRequest.getName());
        user.setAddress(signUpRequest.getAddress());
        user.setGender(signUpRequest.getGender());
        user.setHeight(signUpRequest.getHeight());
        user.setWeight(signUpRequest.getWeight());
        user.setBmi(signUpRequest.getBmi());
        user.setAge(signUpRequest.getAge());
        user.setRoles(roles);
        user.setUserCode(idGenerationUtil.userCodeGenerator());
        user.setIsActive(1);
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
    }
}
