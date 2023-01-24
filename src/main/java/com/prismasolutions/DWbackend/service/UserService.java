package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.mapper.UserMapper;
import com.prismasolutions.DWbackend.repository.UserRepository;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService implements IUserService{

    private final Utility utility;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public String generateValidBase64Code() {

        return null;
    }
    
    /**
     * @inheritDoc Get information about the current logged in user in Dto form.
     */
    @Override
    public UserDto getCurrentUserDto() {
        return userMapper.toDto(utility.getCurrentUser());
    }

}
