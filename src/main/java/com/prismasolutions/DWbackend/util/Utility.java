package com.prismasolutions.DWbackend.util;


import com.prismasolutions.DWbackend.dto.year.YearDto;
import com.prismasolutions.DWbackend.entity.PeriodEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.entity.YearEntity;
import com.prismasolutions.DWbackend.enums.PeriodEnums;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import com.prismasolutions.DWbackend.repository.UserRepository;
import com.prismasolutions.DWbackend.service.PeriodService;
import com.prismasolutions.DWbackend.service.YearService;
import liquibase.repackaged.org.apache.commons.lang3.EnumUtils;
import lombok.AllArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class Utility {
    private final UserRepository userRepository;
    private final PeriodRepository periodRepository;
    private final YearService yearService;

    /**
     * Fetches a {@link UserEntity} of the logged in user.
     * @return The {@link UserEntity}
     */
    @Transactional(readOnly = true)
    public UserEntity getCurrentUser() {
        long id;
        try {
            id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        }
        catch (Exception ex) {
            throw new ServiceException("Current JWS token subject ID not valid. Could not retrieve user");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if(userEntityOptional.isEmpty())
            throw new ServiceException("Current JWS token subject ID not valid. Could not retrieve user");

        return userEntityOptional.get();
    }

    /**
     * Fetches the user with the given id
     * @param id The id of the user to fetch
     * @return The {@link UserEntity}
     */
    public UserEntity getUserById(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if(userEntityOptional.isEmpty())
            throw new ServiceException("User with ID " + id + " does not exist");

        return userEntityOptional.get();
    }
    public long getSubscribeTimeout() {
        return Long.MAX_VALUE;
    }

    public List<UserEntity> createUsersFromCSVFIle(MultipartFile file, PeriodEntity periodEntity){
        List<UserEntity> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length == 5) {
                    UserEntity user = new UserEntity();
                    user.setFirstName(fields[0]);
                    user.setLastName(fields[1]);
                    user.setEmail(fields[2]);
                    user.setMajor(periodEntity.getMajor());
                    user.setMedia(Double.parseDouble(fields[4]));
                    users.add(user);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public Boolean requestAccepted(PeriodEnums enums){

        if(!EnumUtils.isValidEnum(PeriodEnums.class, enums.toString())){
            throw new IllegalArgumentException("Invalid Enum type!");
        }
        UserEntity user = getCurrentUser();
        YearDto yearDto = yearService.getCurrent();
        Date now = new Date();

        try {


            return switch (enums) {
                case NONE -> {
                    yield true;
                }
                case START_OF_ENTERING_TOPICS -> {
                    List<PeriodEntity> periodEntity = periodRepository.findByYear_Id(yearService.getCurrent().getId());

                    if (!user.getRole().equals("teacher")) {
                        yield false;
                    }

                    for (PeriodEntity period : periodEntity) {
                        if (period.getStartOfEnteringTopics().after(now)) {
                            yield false;
                        }
                    }
                    yield true;
                }
                case END_OF_ENTERING_TOPICS -> {
                    List<PeriodEntity> periodEntity = periodRepository.findByYear_Id(yearService.getCurrent().getId());

                    if (!user.getRole().equals("teacher")) {
                        yield false;
                    }

                    for (PeriodEntity period : periodEntity) {
                        if (period.getEndOfEnteringTopics().before(now)) {
                            yield false;
                        }
                    }
                    yield true;
                }
                case FIRST_TOPIC_ADVERTISMENT -> {

                    PeriodEntity period = periodRepository.findByMajor_MajorIdAndYear_Id(user.getMajor().getMajorId(), yearDto.getId());

                    if (period.getFirstTopicAdvertisement().before(now)) {
                        yield false;
                    }
                    yield true;

                }
                case FIRST_TOPIC_ADVERTISMENT_END -> {
                    PeriodEntity period = periodRepository.findByMajor_MajorIdAndYear_Id(user.getMajor().getMajorId(), yearDto.getId());

                    if (period.getFirstTopicAdvertisementEnd().after(now)) {
                        yield false;
                    }
                    yield true;
                }
                case FIRST_ALOCATION -> user.getRole().equals("departmentHead") ? true : false;

                case SECOND_TOPIC_ADVERTISMENT_END -> {
                    PeriodEntity period = periodRepository.findByMajor_MajorIdAndYear_Id(user.getMajor().getMajorId(), yearDto.getId());

                    if (period.getSecondTopicAdvertisement().before(now)) {
                        yield false;
                    }
                    yield true;
                }

                case SECOND_TOPIC_ADVERTIMENT_END -> {
                    PeriodEntity period = periodRepository.findByMajor_MajorIdAndYear_Id(user.getMajor().getMajorId(), yearDto.getId());

                    if (period.getSecondTopicAdvertisementEnd().after(now)) {
                        yield false;
                    }
                    yield true;
                }
                case SECOND_ALLOCATION -> user.getRole().equals("departmentHead") ? true : false;
                case IMPLEMENTATION_OF_TOPICS -> true;
                case DOCUMENTUM_UPLOADE -> {
                    PeriodEntity period = periodRepository.findByMajor_MajorIdAndYear_Id(user.getMajor().getMajorId(), yearDto.getId());

                    if (period.getDocumentumUpload().after(now)) {
                        yield false;
                    }
                    yield true;
                }
                case DIPLOMA_DEFEND -> true;
            };
        }catch (Exception e){
            return false;
        }
   
    }

}
