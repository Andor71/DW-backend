package com.prismasolutions.DWbackend.util;


import com.prismasolutions.DWbackend.enums.PeriodEnums;
import com.prismasolutions.DWbackend.service.DiplomaService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Schedule {
    private final DiplomaService diplomaService;
    private final Utility utility;
    @Scheduled(cron = "1 * * * * ?")
    public void sortStudents(){
        System.out.println("Request for sorting students!");
        if(utility.requestAccepted(PeriodEnums.FIRST_ALOCATION)){
            System.out.println("Request accepted! Sorting students FIRST ALLOCATION");
            diplomaService.sortStudentsForDiploma();
            System.out.println("Student are sorted successfully!");
        }else{
            System.out.println("Dates not match or sorted already!");
        }

        if(utility.requestAccepted(PeriodEnums.SECOND_ALLOCATION)){
            System.out.println("Request accepted! Sorting students SECOND ALLOCATION");
            diplomaService.sortStudentsForDiploma();
            System.out.println("Student are sorted successfully!");
        }else{
            System.out.println("Dates not match or sorted already!");
        }
    }
}
