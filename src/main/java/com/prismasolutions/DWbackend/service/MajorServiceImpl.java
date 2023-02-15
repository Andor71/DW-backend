package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.major.AllMajorDto;
import com.prismasolutions.DWbackend.dto.major.MajorDto;
import com.prismasolutions.DWbackend.dto.year.YearDto;
import com.prismasolutions.DWbackend.entity.MajorEntity;
import com.prismasolutions.DWbackend.entity.YearEntity;
import com.prismasolutions.DWbackend.mapper.MajorMapper;
import com.prismasolutions.DWbackend.mapper.YearMapper;
import com.prismasolutions.DWbackend.repository.MajorRepository;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import com.prismasolutions.DWbackend.repository.YearRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MajorServiceImpl implements MajorService{

    private final MajorRepository majorRepository;
    private final MajorMapper majorMapper;
    private final PeriodRepository periodRepository;
    private final YearService yearService;
    private final YearRepository yearRepository;
    private final YearMapper yearMapper;

    @Override
    public List<MajorDto> getAllWithoutPeriod() {

        List<MajorEntity> majorEntitiesAll = majorRepository.findAll();
        List<MajorEntity> majorEntitiesWithoutPeriod = new ArrayList<>();
        for (MajorEntity major:majorEntitiesAll){
            if(!majorRepository.exists(major.getMajorId().intValue())){
                majorEntitiesWithoutPeriod.add(major);
            }
        }
        return majorMapper.toDtoList(majorEntitiesWithoutPeriod);
    }

    @Override
    public List<MajorDto> create() {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int secondYear =Calendar.getInstance().get(Calendar.YEAR) +1;

        if(yearRepository.existsByStartYearAndEndYear(thisYear,secondYear)){
            throw new IllegalArgumentException("Year already created!");
        }
        YearEntity yearEntity =  yearService.create();

        int n = 12;
        List<MajorDto> newMajors = new ArrayList();
        for(int i = 0 ; i < n ; i++){
            MajorEntity tempMajor = new MajorEntity();
            tempMajor.setDiplomaType("Type");
            tempMajor.setProgramme("TestMajor"+i);
            tempMajor.setYear(yearEntity);
            MajorEntity newMajor= majorRepository.save(tempMajor);
            newMajors.add(majorMapper.toDto(newMajor));
        }
        return newMajors;
    }

    @Override
    public List<AllMajorDto> getAll() {
        int startYear = Calendar.getInstance().get(Calendar.YEAR);
        int endYear =Calendar.getInstance().get(Calendar.YEAR) +1;

        ArrayList<AllMajorDto> allMajorDtos = new ArrayList<>();

        for(int i = 0; i <2 ; i++){
            // Get year from DB , if null means no years was creted so return this section;

            YearEntity yearEntity = yearRepository.findByStartYearAndEndYear(startYear,endYear);
            if(yearEntity == null){
                return allMajorDtos;
            }
            YearDto yearDto = yearMapper.toDto(yearEntity);

            List<MajorEntity> majorEntities = majorRepository.findByYear_Id(yearDto.getId());
            List<MajorDto> majorDtos = new ArrayList();

            //Check if major has period created if yes, pushes it into the final AllMajorDto , majors field.

            for(MajorEntity major : majorEntities){
                if(periodRepository.existsByMajor_MajorId(major.getMajorId())){
                    majorDtos.add(majorMapper.toDto(major));
                }
            }

            AllMajorDto allMajorDto = new AllMajorDto();

            allMajorDto.setYear(yearDto);
            allMajorDto.setMajors(majorDtos);

            allMajorDtos.add(allMajorDto);
            startYear++;
            endYear++;

        }

        return allMajorDtos;
    }
}
