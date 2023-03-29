package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.FinishedStudentDiplomaMappingEntity;
import com.prismasolutions.DWbackend.entity.StudentDiplomaMappingEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.exception.NoAuthority;
import com.prismasolutions.DWbackend.mapper.DiplomaMapper;
import com.prismasolutions.DWbackend.mapper.UserMapper;
import com.prismasolutions.DWbackend.repository.DiplomaRepository;
import com.prismasolutions.DWbackend.repository.Finished_D_S_MRepository;
import com.prismasolutions.DWbackend.repository.StudentDiplomaMappingRepository;
import com.prismasolutions.DWbackend.repository.UserRepository;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

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
        if(diplomaDto.getPeriod() == null){
            throw new IllegalArgumentException("Period cannot be null!");
        }

        diplomaDto.setScore(0.0);
        diplomaDto.setStage("plan");
        diplomaDto.setAbstractName(file.getOriginalFilename());


        diplomaDto.setTeacher(userMapper.toUserResponseDto(utility.getCurrentUser()));

        DiplomaEntity newDiploma = diplomaRepository.save(diplomaMapper.toEntity(diplomaDto));

        diplomaFilesService.create(file,newDiploma.getDiplomaId());

        return diplomaMapper.toDto(newDiploma);
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
        return diplomaMapper.toDtoList(diplomaRepository.findByTeacher_Id(utility.getCurrentUser().getId()));
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
        if(diplomaDto.getPeriod() == null){
            throw new IllegalArgumentException("Period cannot be null!");
        }



        if(file != null){
            diplomaFilesService.update(file,diplomaDto.getDiplomaId());
            diplomaDto.setAbstractName(file.getOriginalFilename());
        }
        DiplomaEntity updatedDiploma = diplomaRepository.save(diplomaMapper.toEntity(diplomaDto));

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
                throw new IllegalArgumentException("Only can be applied to 3 diplomas at once!");
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
    public List<DiplomaDto> getAllDiplomaApplies() {
        List<DiplomaDto> diplomaDtos =diplomaMapper.toDtoList(diplomaRepository.findByStudentNotNull()) ;
        for(DiplomaDto diplomaDto: diplomaDtos){
            if(finishedDSMRepository.existsByDiploma_DiplomaIdAndStudent_Id(diplomaDto.getDiplomaId(),diplomaDto.getStudent().getId())){
                diplomaDto.setEnabled(true);
            }else{
                diplomaDto.setEnabled(false);
            }
        }
        return diplomaDtos;
    }

    @Override
    public void sortStudentsForDiploma(){
        if(!utility.getCurrentUser().getRole().equals("departmenthead")){
            throw new NoAuthority("You have no authority for this action!");
        }
        List<UserEntity> students = userRepository.getAllUsersFromDiplomaMapping();

        students.sort(Comparator.comparing(UserEntity::getMedia));

        for(UserEntity student : students){
            List<StudentDiplomaMappingEntity> studentDiplomaMappingEntities = studentDiplomaMappingRepository.findByStudent_IdOrderByPriorityAsc(student.getId());
            for(StudentDiplomaMappingEntity mapping : studentDiplomaMappingEntities){
                if(mapping.getDiploma().getStudent() == null){
                    DiplomaEntity diloma = mapping.getDiploma();
                    diloma.setStudent(student);
                    diplomaRepository.save(diloma);
                    break;
                }
            }
        }
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

        FinishedStudentDiplomaMappingEntity finishedStudentDiplomaMappingEntity = new FinishedStudentDiplomaMappingEntity();
        finishedStudentDiplomaMappingEntity.setDiploma(diplomaEntity);
        finishedStudentDiplomaMappingEntity.setStudent(userEntity);
//        System.out.println(finishedDSMRepository.existsByDiploma_DiplomaIdAndStudent_Id(diplomaID,studentID));
        if(!finishedDSMRepository.existsByDiploma_DiplomaIdAndStudent_Id(diplomaID,studentID)){
            finishedDSMRepository.save(finishedStudentDiplomaMappingEntity);
        }else{
            finishedStudentDiplomaMappingEntity = finishedDSMRepository.findByDiploma_DiplomaIdAndStudent_Id(diplomaID,studentID);
            finishedDSMRepository.delete(finishedStudentDiplomaMappingEntity);
        }
    }

    @Override
    public void enableAllStudentDiploma() {
        if(!utility.getCurrentUser().getRole().equals("departmenthead")){
            throw new NoAuthority("You have no authority for this action!");
        }

        List<DiplomaEntity> diplomaEntities = diplomaRepository.findByStudentNotNull();

        for(DiplomaEntity diplomaEntity: diplomaEntities){
            FinishedStudentDiplomaMappingEntity finishedStudentDiplomaMappingEntity = new FinishedStudentDiplomaMappingEntity();
            finishedStudentDiplomaMappingEntity.setDiploma(diplomaEntity);
            finishedStudentDiplomaMappingEntity.setStudent(diplomaEntity.getStudent());
            finishedDSMRepository.save(finishedStudentDiplomaMappingEntity);
        }

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
}
