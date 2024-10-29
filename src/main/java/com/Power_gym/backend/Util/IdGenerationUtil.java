package com.Power_gym.backend.Util;

import com.Power_gym.backend.models.User;
import com.Power_gym.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class IdGenerationUtil {

    final UserRepository userRepository;


    public String userCodeGenerator(){
        Optional<User> lastRow = userRepository.findTopByOrderByIdDesc();
        return lastRow.map(user -> {
            String lastRowCode = user.getUserCode();
            int numericPart = Integer.parseInt(lastRowCode.substring(1)) + 1;
            return "U" + String.format("%04d", numericPart);
        }).orElse("U0001");
    }

    public String otpGenerator() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }


}
