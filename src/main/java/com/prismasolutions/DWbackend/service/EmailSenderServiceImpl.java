package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.enums.PeriodEnums;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService{

    private final JavaMailSender javaMailSender;

    private final Utility utility;

    @Override
    public void sendInvitationEmail(String to, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("werozg72@gmail.com");
        message.setTo(to);
        message.setText("Meglettél invitálva a Sapientia Diploma Manager platfromra. \n Az alábbi linken beléphetsz: "+ link);
        message.setSubject("Diploma Manager Invitáció");

        javaMailSender.send(message);
    }

    @Override
    public void sendAcceptedNotification(String to, DiplomaDto diplomaDto) {
        SimpleMailMessage message = new SimpleMailMessage();

        String teachers ="";

        for (UserResponseDto teacher : diplomaDto.getTeachers()) {
            teachers += teacher.getFirstName()+ teacher.getLastName() + ",\n";
        }

        if(utility.requestAccepted(PeriodEnums.FIRST_ALOCATION) && !utility.requestAccepted(PeriodEnums.SECOND_ALLOCATION)){
            message.setFrom("werozg72@gmail.com");
            message.setTo(to);
            message.setText("Gratulálok! \n Sikeresen ki lettél választva a követekező diplomára: \n"+diplomaDto.getTitle() + "\n Vezető tanár(ok): "+ teachers);
            message.setSubject("Diploma Manager első leosztás");
        }else {
            message.setFrom("werozg72@gmail.com");
            message.setTo(to);
            message.setText("Gratulálok! \n Sikeresen ki lettél választva a követekező diplomára: \n"+diplomaDto.getTitle() + "\n Vezető tanár(ok): "+ teachers);
            message.setSubject("Diploma Manager második leosztás");
        }


        javaMailSender.send(message);
    }

    @Override
    public void sendDeclinedNotification(String to) {
        SimpleMailMessage message = new SimpleMailMessage();

        if(utility.requestAccepted(PeriodEnums.FIRST_ALOCATION) && !utility.requestAccepted(PeriodEnums.SECOND_ALLOCATION)){
            message.setFrom("werozg72@gmail.com");
            message.setTo(to);
            message.setText("Nem lettél kiválasztva egy diplomára sem. \n Jelentkezbe be ismént a Diploma Managerbe és jelentkezz diplomákra a második elosztásra! Sok sikert!" );
            message.setSubject("Diploma Manager első leosztás");
        }else {
            message.setFrom("werozg72@gmail.com");
            message.setTo(to);
            message.setText("Sajnálatos modon nem lettél kiválasztva egy diplomára sem.\n Vedd fel a kapcsolatott más diploma vezetővel egy saját diploma ötlett reményében!");
            message.setSubject("Diploma Manager második leosztás");
        }
        javaMailSender.send(message);
    }
}
