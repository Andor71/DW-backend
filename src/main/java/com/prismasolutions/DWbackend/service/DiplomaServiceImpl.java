package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.FinishedSDMappingDto.FinishedSDMappingDto;
import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.diploma.ScoreDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.entity.*;
import com.prismasolutions.DWbackend.enums.DiplomaStages;
import com.prismasolutions.DWbackend.enums.UserStatus;
import com.prismasolutions.DWbackend.exception.NoAuthority;
import com.prismasolutions.DWbackend.exception.UserFriendlyException;
import com.prismasolutions.DWbackend.mapper.DiplomaMapper;
import com.prismasolutions.DWbackend.mapper.FinishedSDMappingMapper;
import com.prismasolutions.DWbackend.mapper.PeriodMapper;
import com.prismasolutions.DWbackend.mapper.UserMapper;
import com.prismasolutions.DWbackend.repository.*;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiplomaServiceImpl implements DiplomaService{

    private final Utility utility;
    private final UserMapper userMapper;
    private final DiplomaRepository diplomaRepository;
    private final DiplomaMapper diplomaMapper;
    private final DiplomaFilesService diplomaFilesService;
    private final UserRepository userRepository;
    private final StudentDiplomaMappingRepository studentDiplomaMappingRepository;
    private final Finished_D_S_MRepository finishedDSMRepository;
    private final DiplomaPeriodMappingService diplomaPeriodMappingService;
    private final PeriodMapper periodMapper;
    private final DiplomaPeriodMappingRepository diplomaPeriodMappingRepository;
    private final TeacherDiplomaMappingService teacherDiplomaMappingService;
    private final TeacherDiplomaMappingRepository teacherDiplomaMappingRepository;
    private final FinishedSDMappingMapper finishedSDMappingMapper;
    private final EmailSenderService emailSenderService;
    private final DepartmentRepository departmentRepository;

    @Override
    public DiplomaDto create(MultipartFile file, DiplomaDto diplomaDto) throws IOException {
        if(diplomaDto.getTitle() == null){
            throw new IllegalArgumentException("Title cannot be null!");
        }
        if(diplomaDto.getDescription() == null){
            throw new IllegalArgumentException("Description cannot be null!");
        }
        if(diplomaDto.getKeywords() == null){
            throw new IllegalArgumentException("Keywords cannot be null!");
        }
        if(diplomaDto.getVisibility() == null){
            diplomaDto.setVisibility(0);
        }
        if(diplomaDto.getTaken() == null){
            diplomaDto.setTaken(false);
        }else{
            if(diplomaDto.getTaken()){
                if(diplomaDto.getStudent() == null){
                    throw new IllegalArgumentException("Student cannot be null!");
                }
            }
        }
        if(diplomaDto.getPeriods() == null){
            throw new IllegalArgumentException("Period cannot be null!");
        }

        diplomaDto.setScore(0.0);
        diplomaDto.setStage(DiplomaStages.PLAN);

        diplomaDto.setAbstractName(file.getOriginalFilename());


        if(diplomaDto.getStudent() != null){
            diplomaDto.setStage(DiplomaStages.UNDER_IMPLEMENTATION);
            UserEntity student = userMapper.toEntityFromResponse(diplomaDto.getStudent());
            student.setStatus(UserStatus.IMPLEMENTING);

            userRepository.save(student);
        }

        DiplomaEntity diploma = diplomaRepository.save(diplomaMapper.toEntity(diplomaDto));

        for(PeriodEntity periodEntity: periodMapper.toEntityList(diplomaDto.getPeriods())){
            diplomaPeriodMappingService.create(diploma,periodEntity);
        }

        teacherDiplomaMappingService.create(utility.getCurrentUser(),diploma);

        if(diplomaDto.getTeachers() != null){
            for(UserEntity userEntity : userMapper.toEntityListFromResponse(diplomaDto.getTeachers())){
                teacherDiplomaMappingService.create(userEntity,diploma);
            }
        }

        diplomaFilesService.createAbstract(file,diploma.getDiplomaId());



        DiplomaDto newDiplomaDto = diplomaMapper.toDto(diploma);
        newDiplomaDto.setPeriods(diplomaDto.getPeriods());

        return newDiplomaDto;
    }

    @Override
    public DiplomaDto getByID(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntity = diplomaRepository.findById(id);

        if(diplomaEntity.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }

        return diplomaMapper.toDto(diplomaEntity.get());
    }

    @Override
    public List<DiplomaDto> getAll() {
        return diplomaMapper.toDtoList(diplomaRepository.findAll());
    }

    @Override
    public List<DiplomaDto> getMyDiplomas() {
        return diplomaMapper.toDtoList(teacherDiplomaMappingRepository.findByTeacher_Id(utility.getCurrentUser().getId()).stream().map( TeacherDiplomaMappingEntity::getDiploma).collect(Collectors.toList()));
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntity = diplomaRepository.findById(id);

        if(diplomaEntity.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }
        diplomaRepository.delete(diplomaEntity.get());
    }

    @Override
    @Transactional
    public DiplomaDto update(MultipartFile file,DiplomaDto diplomaDto) throws IOException {
        if(diplomaDto.getDiplomaId()== null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntity = diplomaRepository.findById(diplomaDto.getDiplomaId());

        if(diplomaEntity.isEmpty()){
            throw new EntityNotFoundException("Entity not found");
        }

        if(diplomaDto.getTitle() == null){
            throw new IllegalArgumentException("Title cannot be null!");
        }
        if(diplomaDto.getDescription() == null){
            throw new IllegalArgumentException("Description cannot be null!");
        }
        if(diplomaDto.getKeywords() == null){
            throw new IllegalArgumentException("Keywords cannot be null!");
        }
        if(diplomaDto.getVisibility() == null){
            diplomaDto.setVisibility(0);
        }
        if(diplomaDto.getTaken() == null){
            diplomaDto.setTaken(false);
        }

        if(file != null){
            diplomaFilesService.updateAbstract(file,diplomaDto.getDiplomaId());
            diplomaDto.setAbstractName(file.getOriginalFilename());
        }
        DiplomaEntity updatedDiploma = diplomaRepository.save(diplomaMapper.toEntity(diplomaDto));

        diplomaPeriodMappingRepository.deleteByDiploma(updatedDiploma);

        for(PeriodEntity periodEntity: periodMapper.toEntityList(diplomaDto.getPeriods())){
            diplomaPeriodMappingService.create(updatedDiploma,periodEntity);
        }

        return diplomaMapper.toDto(updatedDiploma);
    }

    @Override
    public List<DiplomaDto> getAllVisible() {
        return diplomaMapper.toDtoList(diplomaRepository.findByVisibility(0));
    }

    @Override
    public void assignToDiploma(Long diplomaID, Long userID) {
        if(diplomaID == null){
            throw new IllegalArgumentException("CourseID cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntityOptional =diplomaRepository.findById(diplomaID);

        if(diplomaEntityOptional.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }

        if(userID == null){
            throw new IllegalArgumentException("UserID cannot be null!");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findById(userID);

        if(userEntityOptional.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }

        List<StudentDiplomaMappingEntity> studentDiplomaMappingEntities = studentDiplomaMappingRepository.findByStudent_Id(userID);

        int priorityNext = studentDiplomaMappingEntities.size() + 1 ;

        StudentDiplomaMappingEntity studentDiplomaMappingEntity = new StudentDiplomaMappingEntity();

        studentDiplomaMappingEntity.setDiploma(diplomaEntityOptional.get());
        studentDiplomaMappingEntity.setStudent(userEntityOptional.get());
        studentDiplomaMappingEntity.setPriority(priorityNext);

        if(studentDiplomaMappingRepository.existsByDiploma_DiplomaIdAndStudent_Id(diplomaID,userID)){
            studentDiplomaMappingRepository.removeFromStudentGroupMapping(userID.intValue(),diplomaID.intValue());
        }else{
            if(studentDiplomaMappingEntities.size() == 3){
                throw new UserFriendlyException("Csak 3 diplomára tudsz jelentkezni egyszerre!");
            }

            studentDiplomaMappingRepository.save(studentDiplomaMappingEntity);
        }

    }

    @Override
    public List<DiplomaDto> getAllAppliedDiplomas() {
        List<DiplomaDto> diplomaDtosTemp = diplomaMapper.toDtoList(diplomaRepository.findAll());
        List<DiplomaDto> returnDiplomaDtos = new ArrayList<>();
        for(DiplomaDto diplomaDto : diplomaDtosTemp){
            if(studentDiplomaMappingRepository.existsByDiploma_DiplomaIdAndStudent_Id(diplomaDto.getDiplomaId(),utility.getCurrentUser().getId())){
                returnDiplomaDtos.add(diplomaDto);
            }
        }

        Collections.sort(returnDiplomaDtos,
                (o1, o2) -> studentDiplomaMappingRepository.findByDiploma_DiplomaIdAndStudent_Id(o1.getDiplomaId(),utility.getCurrentUser().getId()).getPriority()
                        .compareTo(studentDiplomaMappingRepository.findByDiploma_DiplomaIdAndStudent_Id(o2.getDiplomaId(),utility.getCurrentUser().getId()).getPriority()));


        return returnDiplomaDtos;
    }

    @Override
    @Transactional
    public void changeAppliedPriority(List<DiplomaDto> diplomaDtos , Long userID) {
        if(userID == null){
            throw new IllegalArgumentException("ID cannot be null!");
        }
        UserEntity userEntity = userRepository.findById(userID).orElseThrow(() -> {
            throw new EntityNotFoundException("There is no user with provided id!");
        });

        studentDiplomaMappingRepository.deleteByStudent(userEntity);

        int priority = 1;
        for(DiplomaDto diplomaDto : diplomaDtos){
            StudentDiplomaMappingEntity studentDiplomaMappingEntity = new StudentDiplomaMappingEntity();
            studentDiplomaMappingEntity.setDiploma(diplomaMapper.toEntity(diplomaDto));
            studentDiplomaMappingEntity.setStudent(userEntity);
            studentDiplomaMappingEntity.setPriority(priority);
            studentDiplomaMappingRepository.save(studentDiplomaMappingEntity);
            priority++;
        }
    }

    @Override
    public DiplomaDto getByIdForStudent(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntity = diplomaRepository.findById(id);

        if(diplomaEntity.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }
        DiplomaDto diplomaDto = diplomaMapper.toDto(diplomaEntity.get());

        if(studentDiplomaMappingRepository.existsByDiploma_DiplomaIdAndStudent_Id(diplomaDto.getDiplomaId(),utility.getCurrentUser().getId())){
            diplomaDto.setApplied(true);
        }else{
            diplomaDto.setApplied(false);
        }

        return diplomaDto;
    }

    @Override
    public List<FinishedSDMappingDto> getAllDiplomaApplies(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }
        DepartmentEntity departmentEntity= departmentRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException("Entity not found!");
        });
        List<FinishedSDMappingDto> finishedSDMappingDtos = new ArrayList<>();

        finishedDSMRepository.findAll().forEach(x->{
            if(diplomaPeriodMappingRepository.existsByPeriod_Major_DepartmentEntityAndDiploma_DiplomaId(departmentEntity,x.getDiploma().getDiplomaId())){
                finishedSDMappingDtos.add(finishedSDMappingMapper.toDto(x));
            }
        });

        return finishedSDMappingDtos;
    }

    @Override
    public void sortStudentsForDiploma(){
        System.out.println("Sorting students!");

        List<UserEntity> students = userRepository.getAllUsersFromDiplomaMapping();

        students.sort(Comparator.comparing(UserEntity::getMedia).reversed());

        for(UserEntity student : students){
            if(student.getStatus().equals(UserStatus.SEARCHING)){
                List<StudentDiplomaMappingEntity> studentDiplomaMappingEntities = studentDiplomaMappingRepository.findByStudent_IdOrderByPriorityAsc(student.getId());
                for(StudentDiplomaMappingEntity mapping : studentDiplomaMappingEntities){
                    if(!finishedDSMRepository.existsByDiploma_DiplomaId(mapping.getDiploma().getDiplomaId())){
                        FinishedStudentDiplomaMappingEntity fsdme = new FinishedStudentDiplomaMappingEntity();
                        fsdme.setStudent(student);
                        fsdme.setDiploma(mapping.getDiploma());
                        fsdme.setAccepted(false);
                        finishedDSMRepository.save(fsdme);
                        break;
                    }
                }
            }
        }

        System.out.println("Students has been sorted!");
    }

    @Override
    public void enableStudentDiploma(Long diplomaID, Long studentID) {
        if(!utility.getCurrentUser().getRole().equals("departmenthead")){
            throw new NoAuthority("YOu have no authority for this action!");
        }
        if(diplomaID == null){
            throw new IllegalArgumentException("Diploma id cannot be null!");
        }
        DiplomaEntity diplomaEntity = diplomaRepository.findById(diplomaID).orElseThrow(()->{
            throw new EntityNotFoundException("No Entity found");
        });
        if(studentID == null){
            throw new IllegalArgumentException("Student id cannot be null!");
        }
        UserEntity userEntity = userRepository.findById(studentID).orElseThrow(()->{
            throw new EntityNotFoundException("No user found!");
        });

        if(!studentDiplomaMappingRepository.existsByDiploma_DiplomaIdAndStudent_Id(diplomaID,studentID)){
            throw new IllegalArgumentException("No diploma mapping found!");
        }

        FinishedStudentDiplomaMappingEntity entity = finishedDSMRepository.findByDiploma_DiplomaIdAndStudent_Id(diplomaEntity.getDiplomaId(),userEntity.getId()).orElseThrow(()->{
            throw new EntityNotFoundException("Entity not found!");
        });

        entity.setAccepted(!entity.getAccepted());

        finishedDSMRepository.save(entity);
    }

    @Override
    public List<FinishedSDMappingDto> enableAllStudentDiploma(Boolean allaccepted) {
        if(!utility.getCurrentUser().getRole().equals("departmenthead")){
            throw new NoAuthority("You have no authority for this action!");
        }
        List<FinishedStudentDiplomaMappingEntity> entities = finishedDSMRepository.findAll();
        for(FinishedStudentDiplomaMappingEntity entity : entities){
            entity.setAccepted(!allaccepted);
        }
        return finishedSDMappingMapper.toDtoList(finishedDSMRepository.saveAll(entities));
    }

    @Override
    public List<UserResponseDto> getAllStudentsApplied(Long diplomaID) {
        if(diplomaID == null){
            throw new IllegalArgumentException("ID cannot be null!");
        }
        List<StudentDiplomaMappingEntity> studentDiplomaMappingEntities = studentDiplomaMappingRepository.findByDiploma_DiplomaId(diplomaID);

        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for(StudentDiplomaMappingEntity studentDiplomaMappingEntity : studentDiplomaMappingEntities){
            userResponseDtos.add(userMapper.toUserResponseDto(studentDiplomaMappingEntity.getStudent()));
        }
        return userResponseDtos;
    }

    @Override
    public List<DiplomaDto> getAllVisibleForGivenMajor() {

        List<DiplomaDto> diplomas = new ArrayList<>();

        List<DiplomaEntity> diplomaEntities = diplomaRepository.findByStudentNull();

        for(DiplomaEntity diplomaEntity:diplomaEntities){
            if(diplomaPeriodMappingRepository.existsByDiploma_DiplomaIdAndPeriod_Major_MajorId(diplomaEntity.getDiplomaId(),utility.getCurrentUser().getMajor().getMajorId())){
                diplomas.add(diplomaMapper.toDto(diplomaEntity));
            }
        }

        return diplomas;
    }

    @Override
    public DiplomaDto getCurrentDiploma() {
       DiplomaEntity diplomaEntity= diplomaRepository.findByStudent_Id(utility.getCurrentUser().getId());
       if(diplomaEntity == null){
           throw new NoAuthority("No diploma found for this id");
       }
        return diplomaMapper.toDto(diplomaEntity);
    }

    @Override
    public void finalizeApplies() {
        if(!utility.getCurrentUser().getRole().equals("departmenthead")){
            throw new NoAuthority("You have no authority for this action!");
        }

        List<FinishedStudentDiplomaMappingEntity> fsdme = finishedDSMRepository.findAll();

        for(FinishedStudentDiplomaMappingEntity entity : fsdme){
            if(entity.getAccepted()){
                UserEntity user = entity.getStudent();
                user.setStatus(UserStatus.IMPLEMENTING);
                userRepository.save(user);

                DiplomaEntity diploma = entity.getDiploma();
                diploma.setStudent(user);
                diploma.setStage(DiplomaStages.UNDER_IMPLEMENTATION);

                diplomaRepository.save(diploma);

                emailSenderService.sendAcceptedNotification(user.getEmail(), diplomaMapper.toDto(diploma));

            }
        }
        userRepository.findByActiveTrueAndStatusAndRole(UserStatus.SEARCHING,"student").forEach(x->{
            emailSenderService.sendDeclinedNotification(x.getEmail());
        });


        studentDiplomaMappingRepository.deleteAll();
        finishedDSMRepository.deleteAll();;
    }

    @Override
    public void sortStudentsForDiplomaManual() {

        List<UserEntity> students = userRepository.getAllUsersFromDiplomaMapping();

        students.sort(Comparator.comparing(UserEntity::getMedia));

        for(UserEntity student : students){
            List<StudentDiplomaMappingEntity> studentDiplomaMappingEntities = studentDiplomaMappingRepository.findByStudent_IdOrderByPriorityAsc(student.getId());
            for(StudentDiplomaMappingEntity mapping : studentDiplomaMappingEntities){
                if(!finishedDSMRepository.existsByDiploma_DiplomaId(mapping.getDiploma().getDiplomaId())){
                    FinishedStudentDiplomaMappingEntity fsdme = new FinishedStudentDiplomaMappingEntity();
                    fsdme.setStudent(student);
                    fsdme.setDiploma(mapping.getDiploma());
                    fsdme.setAccepted(false);
                    finishedDSMRepository.save(fsdme);
                    break;
                }
            }
        }
        studentDiplomaMappingRepository.deleteAll();
    }

    @Override
    public List<DiplomaDto> getAllFinished() {
        return diplomaMapper.toDtoList(diplomaRepository.findByStage(DiplomaStages.FINISHED));
    }

    @Override
    public DiplomaDto getFinished(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        DiplomaDto diplomaDto = diplomaMapper.toDto(diplomaRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException("Entity not found!");
        }));

        if(!diplomaDto.getStage().equals(DiplomaStages.FINISHED)){
            throw new NoAuthority("No authority");
        }

        return diplomaDto;
    }

    @Override
    public DiplomaDto setScore(ScoreDto scoreDto) {
        if(scoreDto.getId() == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }
        DiplomaEntity diplomaEntity = diplomaRepository.findById(scoreDto.getId()).orElseThrow(()->{
            throw new EntityNotFoundException("Entity not found!");
        });
        if(scoreDto.getScore() == null){
            throw new IllegalArgumentException("Score cannot be null!");
        }

        diplomaEntity.setScore(scoreDto.getScore());
        diplomaEntity.setStage(DiplomaStages.FINISHED);

        UserEntity student = diplomaEntity.getStudent();
        student.setActive(false);
        student.setStatus(UserStatus.FINISHED);

        userRepository.save(student);

        return diplomaMapper.toDto(diplomaRepository.save(diplomaEntity));
    }


}
