package com.prismasolutions.DWbackend.dto.comment;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.user.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private String message;
    private DiplomaDto diploma;
    private UserDto user;
    private Integer score;
    private Date date;
    private Boolean viewed;

}
