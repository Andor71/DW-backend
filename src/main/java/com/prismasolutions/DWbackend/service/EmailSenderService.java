package com.prismasolutions.DWbackend.service;


import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.entity.DiplomaEntity;

public interface EmailSenderService {
     void sendInvitationEmail(String to,String link);

     void sendAcceptedNotification(String to , DiplomaDto diplomaDto);

     void sendDeclinedNotification(String to);

}