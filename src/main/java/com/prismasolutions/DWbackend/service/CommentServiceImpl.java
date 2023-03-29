package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.comment.CommentDto;
import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.entity.CommentEntity;
import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.mapper.CommentMapper;
import com.prismasolutions.DWbackend.repository.CommentRepository;
import com.prismasolutions.DWbackend.repository.DiplomaRepository;
import com.prismasolutions.DWbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final UserRepository userRepository;
    private final DiplomaRepository diplomaRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto create(CommentDto commentDto) {
        if(commentDto.getUser() == null){
            throw new IllegalArgumentException("User cannot be null!");
        }

        UserEntity userEntity = userRepository.findById(commentDto.getUser().getId()).orElseThrow(()->{
            throw new IllegalArgumentException("User cannot be null!");
        });

        DiplomaEntity diplomaEntity = diplomaRepository.findById(commentDto.getDiploma().getDiplomaId()).orElseThrow(()->{
            throw new IllegalArgumentException("Diploma cannot be null!");
        });

        if(commentDto.getMessage()==null){
            throw new IllegalArgumentException("Message cannot be null!");

        }
        commentDto.setDate(Calendar.getInstance().getTime());
        commentDto.setViewed(false);
        CommentEntity newComment = commentRepository.save(commentMapper.toEntity(commentDto));

        return commentMapper.toDto(newComment);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException("NO entity found!");
        });

        commentRepository.delete(commentEntity);
    }

    @Override
    public List<CommentDto> getByDiploma(Long diplomaID) {
        if(diplomaID == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }
        List<CommentEntity> commentEntities = commentRepository.findByDiploma_DiplomaId(diplomaID);
        List<CommentDto> commentDtos= commentMapper.toDtoList(commentEntities);

        for(CommentEntity commentEntity: commentEntities){
            commentEntity.setViewed(true);
        }
        commentRepository.saveAll(commentEntities);

        return commentDtos;
    }
}
