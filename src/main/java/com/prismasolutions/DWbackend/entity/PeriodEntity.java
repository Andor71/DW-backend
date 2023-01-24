package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "period")
@Data
public class PeriodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id")
    private Long periodId;

    @Column(name = "start_of_entering_topics")
    private Date startOfEnteringTopics;

    @Column(name = "end_of_entering_topics")
    private Date endOfEnteringTopics;

    @Column(name = "first_topic_advertisement")
    private Date firstTopicAdvertisement;

    @Column(name = "first_topic_advertisement_end")
    private Date firstTopicAdvertisementEnd;

    @Column(name = "first_allocation")
    private Date firstAllocation;

    @Column(name = "second_topic_advertisement")
    private Date secondTopicAdvertisement;

    @Column(name = "second_topic_advertisement_end")
    private Date secondTopicAdvertisementEnd;

    @Column(name = "implementation_of_topics")
    private Date implementationOfTopics;

    @Column(name = "documentum_upload")
    private Date documentumUpload;

    @Column(name = "diploma_defend")
    private Date diplomaDefend;

}