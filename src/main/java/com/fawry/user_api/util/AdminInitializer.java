package com.fawry.user_api.util;

import com.fawry.user_api.entity.User;
import com.fawry.user_api.enums.UserRole;
import com.fawry.user_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdminInitializer implements CommandLineRunner {

       private final UserRepository userRepository;
       private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if(!userRepository.existsByRole(UserRole.ADMIN)) {

            User user= User.builder().username("deesha").email("mustafatarek112@gmail.com")
                    .password(passwordEncoder.encode("12345Mustafa@Tarek!"))
                    .role(UserRole.ADMIN)
                    .isActive(true)
                    .build();
            userRepository.save(user);
        }
    }
}
