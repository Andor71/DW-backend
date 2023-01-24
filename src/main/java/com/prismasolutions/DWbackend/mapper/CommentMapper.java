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
    public CommentEntity toEntity(CommentDto commentDto){
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setCommentId(commentDto.getCommentId());
        commentEntity.setDate(commentDto.getDate());
        commentEntity.setUser(userMapper.toEntity(commentDto.getUser()));
        commentEntity.setMessage(commentDto.getMessage());
//        commentEntity.setDiploma(commentDto.getDiploma());
//        commentEntity.setScore(commentDto.getScore());

        return commentEntity;
    }

//    public List<UserEntity> toEntityList(List<UserDto> userDtos){
//        return userDtos.stream().map(this::toEntity).collect(Collectors.toList());
//    }
}
