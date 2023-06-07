package com.prismasolutions.DWbackend.dto.period;

import com.prismasolutions.DWbackend.dto.major.MajorDto;
import com.prismasolutions.DWbackend.dto.year.YearDto;
import lombok.Data;

import java.util.Date;

@Data
public class PeriodDto {
    private Long periodId;
    private MajorDto major;
    private YearDto year;
    private Date startOfEnteringTopics;
    private Date endOfEnteringTopics;
    private Date firstTopicAdvertisement;
    private Date firstTopicAdvertisementEnd;
    private Date firstAllocation;
    private Date secondTopicAdvertisement;
    private Date secondTopicAdvertisementEnd;
    private Date secondAllocation;
    private Date implementationOfTopics;
    private Date documentumUpload;
    private Date diplomaDefend;
}