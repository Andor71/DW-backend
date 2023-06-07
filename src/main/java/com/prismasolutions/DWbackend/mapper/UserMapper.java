package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.entity.DepartmentEntity;
import com.prismasolutions.DWbackend.entity.MajorEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.repository.MajorRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {

    private final MajorRepository majorRepository;
    private final DepartmentMapper departmentMapper;
    private final MajorMapper majorMapper;

    public UserEntity toEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userDto.getId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRole(userDto.getRole());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setActive(userDto.getActive());
        userEntity.setStatus(userDto.getStatus());

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
        userResponseDto.setMedia(userEntity.getMedia());
        userResponseDto.setStatus(userEntity.getStatus());
        if(userEntity.getMajor() != null){
            userResponseDto.setMajorDto(majorMapper.toDto(userEntity.getMajor()));
        }
        if(userEntity.getRole().equals("departmenthead")){
            userResponseDto.setDepartment(departmentMapper.toDto(userEntity.getDepartmentEntity()));
        }


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
        userDto.setEmail(userEntity.getEmail());
        userDto.setRole(userEntity.getRole());
        userDto.setActive(userEntity.getActive());
        userDto.setStatus(userEntity.getStatus());

        return userDto;
    }

    public List<UserDto> toDtoList(List<UserEntity> userDtos){
        return userDtos.stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserEntity toEntityFromResponse(UserResponseDto userResponseDto){
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userResponseDto.getId());
        userEntity.setFirstName(userResponseDto.getFirstName());
        userEntity.setLastName(userResponseDto.getLastName());
        userEntity.setEmail(userResponseDto.getEmail());
        userEntity.setRole(userResponseDto.getRole());
        userEntity.setActive(userResponseDto.getActive());
        userEntity.setMedia(userResponseDto.getMedia());
        userEntity.setStatus(userResponseDto.getStatus());
        if(userResponseDto.getMajorDto() != null){
            userEntity.setMajor(majorMapper.toEntity(userResponseDto.getMajorDto()));
        }
        if(userResponseDto.getRole().equals("departmenthead")){
            userEntity.setDepartmentEntity(departmentMapper.toEntity(userResponseDto.getDepartment()));
        }
        return userEntity;
    }
    public List<UserEntity> toEntityListFromResponse(List<UserResponseDto> userResponseDtos){
        return userResponseDtos.stream().map(this::toEntityFromResponse).collect(Collectors.toList());
    }

}
