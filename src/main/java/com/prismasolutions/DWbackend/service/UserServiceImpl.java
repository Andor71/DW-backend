package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.mapper.UserMapper;
import com.prismasolutions.DWbackend.repository.UserRepository;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

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

    @Override
    public UserResponseDto getById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null!");
        }
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if(userEntity.isEmpty()){
            throw new EntityNotFoundException("No user found!");
        }

        return userMapper.toUserResponseDto(userEntity.get());
    }

    @Override
    public List<UserResponseDto> getAll() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    @Override
    public List<UserResponseDto> getAllActiveStudents() {
        return userMapper.toUserResponseList(userRepository.findByActiveAndRole(true,"student"));
    }

}
