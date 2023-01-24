package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserEntity toEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.getId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setUserImage(userDto.getImage());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRole(userDto.getRole());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setActive(userDto.getActive());

        return userEntity;
    }

    public List<UserEntity> toEntityList(List<UserDto> userDtos){
        return userDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public UserResponseDto toUserResponseDto(UserEntity userEntity){
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(userEntity.getId());
        userResponseDto.setFirstName(userEntity.getFirstName());
        userResponseDto.setLastName(userEntity.getLastName());
        userResponseDto.setEmail(userEntity.getEmail());
        userResponseDto.setRole(userEntity.getRole());
        userResponseDto.setActive(userEntity.getActive());

        return userResponseDto;
    }

    public List<UserResponseDto> toUserResponseList(List<UserEntity> userDtos){
        return userDtos.stream().map(this::toUserResponseDto).collect(Collectors.toList());
    }
    public UserDto toDto(UserEntity userEntity){
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setImage(userEntity.getUserImage());
        userDto.setEmail(userEntity.getEmail());
        userDto.setRole(userEntity.getRole());
        userDto.setPassword(userEntity.getPassword());
        userDto.setActive(userEntity.getActive());

        return userDto;
    }

    public List<UserDto> toDtoList(List<UserEntity> userDtos){
        return userDtos.stream().map(this::toDto).collect(Collectors.toList());
    }

}
