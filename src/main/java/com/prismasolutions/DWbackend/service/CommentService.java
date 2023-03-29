package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto create(CommentDto commentDto);

    void delete(Long id);

    List<CommentDto> getByDiploma(Long diplomaID);
}
