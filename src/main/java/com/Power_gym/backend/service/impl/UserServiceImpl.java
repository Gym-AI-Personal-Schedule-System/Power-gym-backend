package com.Power_gym.backend.service.impl;

import com.Power_gym.backend.DTO.OtpDTO;
import com.Power_gym.backend.DTO.UserDTO;
import com.Power_gym.backend.DTO.common.ResponseMessage;
import com.Power_gym.backend.Util.IdGenerationUtil;
import com.Power_gym.backend.exception.CustomException;
import com.Power_gym.backend.models.Role;
import com.Power_gym.backend.models.User;
import com.Power_gym.backend.repository.UserRepository;
import com.Power_gym.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IdGenerationUtil idGenerationUtil;
    @Autowired
    private MailServiceImpl mailService;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> getUserDataByMobileNumber(UserDTO userDTO) throws Exception {
       if(!userDTO.getMobileNum().isBlank()){
           Optional<User> byMobileNum = userRepository.findByMobileNum(userDTO.getMobileNum());
           if (byMobileNum.isPresent()){
              UserDTO userData = modelMapper.map(byMobileNum,UserDTO.class);
               return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success",userData), HttpStatus.OK);
           }throw new CustomException("Please enter valid user mobile number!");
       }throw new CustomException("Mobile Number is empty");
    }

    @Override
    public ResponseMessage otpSend(UserDTO userData) throws Exception {
        if (!userData.getEmail().isEmpty()) {
            Optional<User> byEmail = userRepository.findByEmail(userData.getEmail());
            if (byEmail.isPresent()) {
                String otp = idGenerationUtil.otpGenerator();
                OtpDTO otpDTO = OtpDTO.builder().otp(otp).build();
                mailService.sendEmailAsync(byEmail.get().getEmail(), "Password Reset Request – Your One-Time Password (OTP) Inside", generateForgotPasswordEmailBody(otp));
                return new ResponseMessage(HttpStatus.OK.value(), "success", otpDTO);
            } else {
                throw new CustomException("Can't find a user");
            }
        }
        throw new CustomException("email is empty");
    }

    @Override
    public ResponseMessage updatePassword(UserDTO userData) throws Exception {
        return userRepository.findById(userData.getId())
                .map(user -> {
                    if (!userData.getPassword().isEmpty()) {
                        user.setPassword(encoder.encode(userData.getPassword()));
                    }
                    User saveUser = userRepository.save(user);
                    return new ResponseMessage(HttpStatus.OK.value(), "User Password updated successfully", saveUser);
                })
                .orElse(new ResponseMessage(HttpStatus.NOT_FOUND.value(), "User not found"));
    }

    public String generateForgotPasswordEmailBody(String otp) {
        String emailTemplate = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Forgot Password Request</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        background-color: #ffffff;
                        width: 80%%;
                        max-width: 600px;
                        margin: 20px auto;
                        padding: 20px;
                        border-radius: 10px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                    }
                    .header {
                        background-color: #007bff;
                        color: #ffffff;
                        text-align: center;
                        padding: 10px;
                        border-top-left-radius: 10px;
                        border-top-right-radius: 10px;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 24px;
                    }
                    .content {
                        margin: 20px 0;
                        line-height: 1.6;
                    }
                    .content p {
                        margin: 10px 0;
                    }
                    .otp {
                        font-weight: bold;
                        font-size: 18px;
                        color: #007bff;
                    }
                    .footer {
                        text-align: center;
                        color: #666666;
                        font-size: 14px;
                        padding-top: 10px;
                        border-top: 1px solid #dddddd;
                    }
                    .footer p {
                        margin: 5px 0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Password Reset Request</h1>
                    </div>
                    <div class="content">
                        <p>Dear User,</p>
                        <p>You have requested a One-Time Password (OTP) to reset your password.</p>
                        <p>Your OTP is:</p>
                        <p class="otp">%s</p>
                        <p>Please use this OTP to reset your password. For security reasons, the OTP will expire in 10 minutes.</p>
                        <p>If you did not request a password reset, please contact our support team immediately.</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2024 Power gym. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """;

        return emailTemplate.formatted(otp);
    }


}
