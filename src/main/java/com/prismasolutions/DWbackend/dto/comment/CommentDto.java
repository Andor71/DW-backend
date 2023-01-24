package com.prismasolutions.DWbackend.dto.comment;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.entity.UserEntity;
import lombok.Data;
import java.util.Date;

@Data
public class CommentDto {
    private Long commentId;
    private String message;
    private DiplomaDto diploma;
    private UserDto user;
    private Integer score;
    private Date date;
}
