package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.comment.CommentDto;
import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.entity.CommentEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;
    private final DiplomaMapper diplomaMapper;
    public CommentEntity toEntity(CommentDto commentDto){
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCommentId(commentDto.getCommentId());
        commentEntity.setDate(commentDto.getDate());
        commentEntity.setUser(userMapper.toEntity(commentDto.getUser()));
        commentEntity.setMessage(commentDto.getMessage());
        commentEntity.setDiploma(diplomaMapper.toEntity(commentDto.getDiploma()));
        commentEntity.setScore(commentDto.getScore());
        commentEntity.setViewed(commentDto.getViewed());

        return commentEntity;
    }

    public List<CommentEntity> toEntityList(List<CommentDto> commentDtos){
        return commentDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public CommentDto toDto(CommentEntity commentEntity){
        CommentDto commentDto = new CommentDto();

        commentDto.setCommentId(commentEntity.getCommentId());
        commentDto.setDate(commentEntity.getDate());
        commentDto.setUser(userMapper.toDto(commentEntity.getUser()));
        commentDto.setMessage(commentEntity.getMessage());
        commentDto.setDiploma(diplomaMapper.toDto(commentEntity.getDiploma()));
        commentDto.setScore(commentEntity.getScore());
        commentDto.setViewed(commentEntity.getViewed());

        return commentDto;
    }

    public List<CommentDto> toDtoList(List<CommentEntity> commentEntities){
        return commentEntities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
