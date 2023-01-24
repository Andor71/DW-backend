package com.prismasolutions.DWbackend.dto.document;

import com.prismasolutions.DWbackend.dto.major.MajorDto;
import com.prismasolutions.DWbackend.dto.period.PeriodDto;
import com.prismasolutions.DWbackend.entity.MajorEntity;
import com.prismasolutions.DWbackend.entity.PeriodEntity;
import lombok.Data;

@Data
public class DocumentDto {

    private Long documentId;

    private String name;

    private MajorDto major;

    private PeriodDto period;

    private String path;
}
