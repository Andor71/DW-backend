package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.errorResponse.ErrorResponseDto;
import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.entity.MajorYearStudentEntity;
import com.prismasolutions.DWbackend.entity.PeriodEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.enums.PeriodEnums;
import com.prismasolutions.DWbackend.exception.UserFriendlyException;
import com.prismasolutions.DWbackend.mapper.UserMapper;
import com.prismasolutions.DWbackend.repository.MajorYearStudentMappingRepository;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import com.prismasolutions.DWbackend.repository.UserRepository;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final Utility utility;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PeriodRepository periodRepository;
    private final MajorYearStudentMappingRepository majorYearStudentMappingRepository;

    @Override
    public String generateValidBase64Code() {

        return null;
    }

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

    @Override
    public List<UserResponseDto> getAllTeachers() {

        List<UserResponseDto> userResponseDtos = userMapper.toUserResponseList(userRepository.findByRoleOrRole("teacher","departmenthead"));

        for(UserResponseDto userResponseDto: userResponseDtos){
            if(userResponseDto.getId() == utility.getCurrentUser().getId()){
                userResponseDtos.remove(userResponseDto);
                break;
            }
        }

        return userResponseDtos;
    }

    @Override
    public UserResponseDto createStudent(UserResponseDto userResponseDto) {
        if(userResponseDto.getFirstName() == null){
            throw new IllegalArgumentException("First name cannot be null!");
        }
        if(userResponseDto.getLastName() == null){
            throw new IllegalArgumentException("Last name cannot be null!");
        }
        if(userResponseDto.getEmail() == null){
            throw new IllegalArgumentException("Email cannot be null!");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userResponseDto.getEmail());

        if(userEntityOptional.isPresent()){
            throw new UserFriendlyException("Email is already in use!");
        }

        if(userResponseDto.getMajorDto() == null){
            throw new IllegalArgumentException("Major cannot be null!");
        }
        if(userResponseDto.getMedia() == null){
            throw new IllegalArgumentException("Media cannot be null!");
        }
        userResponseDto.setActive(null);
        userResponseDto.setRole("student");
        userResponseDto.setStatus(0);


        //TODO Set validation code and send email
        UserEntity userEntity = userRepository.save(userMapper.toEntityFromResponse(userResponseDto));

        return userMapper.toUserResponseDto(userEntity);
    }

    @Override
    public UserResponseDto updateStudent(UserResponseDto userResponseDto) {
        if(userResponseDto.getId() == null){
            throw new IllegalArgumentException("ID cannot be null!");
        }
        if(userResponseDto.getFirstName() == null){
            throw new IllegalArgumentException("First name cannot be null!");
        }
        if(userResponseDto.getLastName() == null){
            throw new IllegalArgumentException("Last name cannot be null!");
        }
        if(userResponseDto.getEmail() == null){
            throw new IllegalArgumentException("Email cannot be null!");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userResponseDto.getEmail());


        //TODO ON different email send email

        if(userEntityOptional.isEmpty()){
            throw new UserFriendlyException("User not found!");
        }

        if(userRepository.existsByEmail(userResponseDto.getEmail())){
            throw new UserFriendlyException("Email is already in use!");
        }

        if(userResponseDto.getMajorDto() == null){
            throw new IllegalArgumentException("Major cannot be null!");
        }
        if(userResponseDto.getMedia() == null){
            throw new IllegalArgumentException("Media cannot be null!");
        }


        UserEntity userEntity = userRepository.findById(userResponseDto.getId()).orElseThrow(()->{
            throw new EntityNotFoundException("Student not found!");
        });

        userEntity.setFirstName(userResponseDto.getFirstName());
        userEntity.setLastName(userResponseDto.getLastName());
        userEntity.setMedia(userResponseDto.getMedia());
        userEntity.setEmail(userEntity.getEmail());

        userEntity = userRepository.save(userEntity);

        return userMapper.toUserResponseDto(userEntity);
    }

    @Override
    public void deleteStudent(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException("Student not found!");
        });

        if(userEntity.getStatus() != 0){
            throw new UserFriendlyException("Student cannot be deleted, already working on a Diploma");
        }

        userRepository.delete(userEntity);

    }

    @Override
    public List<UserResponseDto> createStudentsViaFile(MultipartFile file, Long periodID) {
        if(file == null){
            throw new IllegalArgumentException("File cannot be null!");
        }

        if(periodID == null){
            throw new IllegalArgumentException("Period ID cannot be null!");
        }


        PeriodEntity periodEntity = periodRepository.findById(periodID).orElseThrow(()->{
            throw new EntityNotFoundException("Period not found!");
        });

        List<UserEntity> userEntities = userRepository.saveAll(utility.createUsersFromCSVFIle(file,periodEntity));

        return userMapper.toUserResponseList(userEntities);
    }

    @Override
    public List<UserResponseDto> getAllStudentsForPeriod(Long periodID) {
        if(periodID == null){
            throw new IllegalArgumentException("Id cannot be null");
        }

        PeriodEntity periodEntity = periodRepository.findById(periodID).orElseThrow(()->{
            throw new EntityNotFoundException("Period not found!");
        });

        List<MajorYearStudentEntity> majorYearStudentEntities = majorYearStudentMappingRepository.findByMajor_MajorIdAndYear_Id(periodEntity.getMajor().getMajorId(),periodEntity.getYear().getId());

        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for(MajorYearStudentEntity majorYearStudent : majorYearStudentEntities){
            userResponseDtos.add(userMapper.toUserResponseDto(majorYearStudent.getStudent()));
        }

        return userResponseDtos;
    }

}
