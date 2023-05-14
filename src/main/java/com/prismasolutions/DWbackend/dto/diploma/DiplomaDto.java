package com.prismasolutions.DWbackend.dto.diploma;

import com.prismasolutions.DWbackend.dto.period.PeriodDto;
import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.entity.UserEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import java.time.Period;
import java.util.List;

@Data
public class DiplomaDto {
    private Long diplomaId;
    private String title;
    private List<PeriodDto> periods;
    private UserResponseDto student;
    private Double score;
    private String stage;
    private Integer visibility;
    private String keywords;
    private String type;
    private Boolean taken;
    private String abstractName;
    private String description;
    private Boolean applied;
    private Boolean enabled;
    private String bibliography;
    private String details;
    private String necessaryKnowledge;
    private String differentExpectations;
    private List<UserResponseDto> teachers;
}
