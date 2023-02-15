package com.prismasolutions.DWbackend.dto.document;

import com.prismasolutions.DWbackend.dto.year.YearDto;
import lombok.Data;

import java.util.List;

@Data
public class DocumentResponseDto {
    YearDto yearDto;
    List<DocumentDto> documents;
}
