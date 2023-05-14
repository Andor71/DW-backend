package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.enums.PeriodEnums;
import com.prismasolutions.DWbackend.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public interface UserService {
    /**
     * Checks if a string is of a valid email form
     * @param email The email string
     * @return True if it has email form, false otherwise
     */
    static boolean isInvalidEmail(String email) {
        return !email.matches("^[a-z1-9.\\-_]{1,64}@([a-z]{2,6}\\.)+([a-z]{2,})$");
    }

    /**
     * Generates a random base 64 String of a specified length
     * @param byteLength The length of the String
     * @return The random String
     */
    static String generateRandomBase64String(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token); //base64 encoding
    }

    /**
     * Generate a random one time password
     * @return the temporary password
     */
    static String generateBase64Code() {
        return generateRandomBase64String(20);
    }

    static String generateOnetimePassword() {
        String password = generateRandomBase64String(8);

        while(isPasswordInvalid(password))
            password = generateRandomBase64String(8);

        return password;
    }

    /**
     * Keeps generating a Base 64 Code ({@link #generateBase64Code()}), checking it against the repository
     * ({@link UserRepository}) for it to be unique.
     * @return The valid Base 64 Code.
     */
    String generateValidBase64Code();

    /**
     * Checks if a password has at least 8 characters and at least one uppercase character
     * @param password The password to check
     * @return True if the password meets the constraints, false if not
     */
    static boolean isPasswordInvalid(String password) {
        return password.length() < 8 || password.equals(password.toLowerCase());
    }


    /**
     * Fetches a {@link UserDto} of the logged in user containing all user data except password.
     * @return The {@link UserDto}
     */
    UserDto getCurrentUserDto();

    UserResponseDto getById(Long id);

    List<UserResponseDto> getAll();

    List<UserResponseDto> getAllActiveStudents();

    List<UserResponseDto> getAllTeachers();

    UserResponseDto createStudent(UserResponseDto userResponseDto);

    UserResponseDto updateStudent(UserResponseDto userResponseDto);

    void deleteStudent(Long id);

    List<UserResponseDto> createStudentsViaFile(MultipartFile file,Long periodID);

    List<UserResponseDto> getAllStudentsForPeriod(Long periodID);
}
