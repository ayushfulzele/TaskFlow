package com.project.todo.service.impl;

import com.project.todo.dto.JwtAuthResponse;
import com.project.todo.dto.LoginDto;
import com.project.todo.dto.RegisterDto;
import com.project.todo.entity.Role;
import com.project.todo.entity.User;
import com.project.todo.exception.TodoApiException;
import com.project.todo.repository.RoleRepository;
import com.project.todo.repository.UserRepository;
import com.project.todo.security.JwtTokenProvider;
import com.project.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    public String register(RegisterDto registerDto){

        //check username is already exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST,"Username already exists!");
        }
        //check email is already exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST,"Email is already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");

        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!";
    }

    public JwtAuthResponse login(LoginDto loginDto){
       Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                    loginDto.getPassword()
            ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        Optional<User>  userOptional= userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());

        String role = null;
        if(userOptional.isPresent()){
          User loggedInUser = userOptional.get();
          Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();

          if(optionalRole.isPresent()){
              Role userRole = optionalRole.get();
              role = userRole.getName();
          }
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;


    }
}
