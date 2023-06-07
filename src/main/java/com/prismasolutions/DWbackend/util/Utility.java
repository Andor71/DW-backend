package com.prismasolutions.DWbackend.util;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.prismasolutions.DWbackend.dto.year.YearDto;
import com.prismasolutions.DWbackend.entity.PeriodEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.entity.YearEntity;
import com.prismasolutions.DWbackend.enums.PeriodEnums;
import com.prismasolutions.DWbackend.enums.UserStatus;
import com.prismasolutions.DWbackend.exception.UserFriendlyException;
import com.prismasolutions.DWbackend.repository.Finished_D_S_MRepository;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import com.prismasolutions.DWbackend.repository.UserRepository;
import com.prismasolutions.DWbackend.service.EmailSenderService;
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
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

@Component
@AllArgsConstructor
public class Utility {
    private final UserRepository userRepository;
    private final PeriodRepository periodRepository;
    private final Finished_D_S_MRepository finishedDSMRepository;
    private final YearService yearService;
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

    public UserEntity getUserById(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if(userEntityOptional.isEmpty())
            throw new ServiceException("User with ID " + id + " does not exist");

        return userEntityOptional.get();
    }
    public long getSubscribeTimeout() {
        return Long.MAX_VALUE;
    }

    public String generateRandomBase64String(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token); //base64 encoding
    }
    @Transactional
    public List<UserEntity> createUsersFromCSVFIle(MultipartFile file, PeriodEntity periodEntity){
        List<UserEntity> users = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
            CSVReader csvReader = new CSVReader(reader);

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {

                UserEntity user = new UserEntity();
                user.setFirstName(nextLine[0]);
                user.setLastName(nextLine[1]);
                if(userRepository.existsByEmail(nextLine[2])){
                    throw new UserFriendlyException("Email :" + nextLine[2] + " már használatban van!");
                }

                user.setEmail(nextLine[2]);
                user.setMedia(Double.parseDouble(nextLine[3]));
                user.setRole("student");
                user.setActive(false);
                user.setStatus(UserStatus.SEARCHING);
                user.setMajor(periodEntity.getMajor());
                user.setValidationCode(generateRandomBase64String(20));
                users.add(user);

            }

            csvReader.close();
        } catch (IOException e) {
            throw new UserFriendlyException("Hiba a CSV-ben.");
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    public Boolean requestAccepted(PeriodEnums enums){

        if(!EnumUtils.isValidEnum(PeriodEnums.class, enums.toString())){
            throw new IllegalArgumentException("Invalid Enum type!");
        }

        YearDto yearDto = yearService.getCurrent();
        Date now = new Date();

        try {
            return switch (enums) {
                case NONE -> {
                    yield true;
                }
                case START_OF_ENTERING_TOPICS -> {
                    UserEntity user = getCurrentUser();
                    List<PeriodEntity> periodEntity = periodRepository.findByYear_Id(yearService.getCurrent().getId());

                    if (!user.getRole().equals("teacher") && !user.getRole().equals("departmenthead")) {
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
                    UserEntity user = getCurrentUser();
                    List<PeriodEntity> periodEntity = periodRepository.findByYear_Id(yearService.getCurrent().getId());

                    if (!user.getRole().equals("teacher") && !user.getRole().equals("departmenthead")) {
                        yield false;
                    }

                    for (PeriodEntity period : periodEntity) {
                        if (period.getEndOfEnteringTopics().after(now)) {
                            yield false;
                        }
                    }
                    yield true;
                }
                case FIRST_TOPIC_ADVERTISMENT -> {
                    UserEntity user = getCurrentUser();
                    PeriodEntity period = periodRepository.findByMajor_MajorIdAndYear_Id(user.getMajor().getMajorId(), yearDto.getId());

                    if (period.getFirstTopicAdvertisement().after(now)) {
                        yield false;
                    }
                    yield true;

                }
                case FIRST_TOPIC_ADVERTISMENT_END -> {
                    UserEntity user = getCurrentUser();
                    PeriodEntity period = periodRepository.findByMajor_MajorIdAndYear_Id(user.getMajor().getMajorId(), yearDto.getId());

                    if (period.getFirstTopicAdvertisementEnd().after(now)) {
                        yield false;
                    }
                    yield true;
                }
                case FIRST_ALOCATION ->{
                    List<PeriodEntity> periodEntity = periodRepository.findByYear_Id(yearService.getCurrent().getId());


                    for (PeriodEntity period : periodEntity) {
                        if(period.getFirstAllocationSorted()){
                            yield false;
                        }

                        if (period.getFirstAllocation().after(now)) {
                            yield false;
                        }
                    }

                    for (PeriodEntity period : periodEntity) {
                        period.setFirstAllocationSorted(true);
                        periodRepository.save(period);
                    }

                    yield true;
                }
                case SECOND_TOPIC_ADVERTISMENT -> {
                    UserEntity user = getCurrentUser();
                    PeriodEntity period = periodRepository.findByMajor_MajorIdAndYear_Id(user.getMajor().getMajorId(), yearDto.getId());

                    if (period.getSecondTopicAdvertisement().after(now)) {
                        yield false;
                    }
                    yield true;
                }

                case SECOND_TOPIC_ADVERTISMENT_END -> {
                    UserEntity user = getCurrentUser();
                    PeriodEntity period = periodRepository.findByMajor_MajorIdAndYear_Id(user.getMajor().getMajorId(), yearDto.getId());

                    if (period.getSecondTopicAdvertisementEnd().after(now)) {
                        yield false;
                    }
                    yield true;
                }

                case SECOND_ALLOCATION -> {
                    List<PeriodEntity> periodEntity = periodRepository.findByYear_Id(yearService.getCurrent().getId());

                    for (PeriodEntity period : periodEntity) {
                        if(period.getSecondAllocationSorted()){
                            yield false;
                        }

                        if (period.getSecondAllocation().after(now)) {
                            yield false;
                        }
                    }

                    for (PeriodEntity period : periodEntity) {
                        period.setSecondAllocationSorted(true);
                        periodRepository.save(period);
                    }

                    yield true;
                }
                case IMPLEMENTATION_OF_TOPICS -> true;
                case DOCUMENTUM_UPLOADE -> {
                    UserEntity user = getCurrentUser();
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

    public boolean sortedAlready(){
        if(finishedDSMRepository.countByIdNotNull() != 0){
            return true;
        }
        return false;
    }

}
