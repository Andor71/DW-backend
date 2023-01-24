package com.prismasolutions.DWbackend.dto.diplomaFiles;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import lombok.Data;


@Data
public class DiplomaFilesDto {
    private Long diplomaFilesId;

    private String title;

    private DiplomaDto diploma;

    private String type;

    private String path;

    private Integer visibility;
}
