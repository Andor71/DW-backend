package com.prismasolutions.DWbackend.dto.period;

import lombok.Data;

import java.util.Date;

@Data
public class PeriodDto {

    private Long periodId;

    private Date startOfEnteringTopics;

    private Date endOfEnteringTopics;

    private Date firstTopicAdvertisement;

    private Date firstTopicAdvertisementEnd;

    private Date firstAllocation;

    private Date secondTopicAdvertisement;

    private Date secondTopicAdvertisementEnd;

    private Date implementationOfTopics;

    private Date documentumUpload;

    private Date diplomaDefend;

}