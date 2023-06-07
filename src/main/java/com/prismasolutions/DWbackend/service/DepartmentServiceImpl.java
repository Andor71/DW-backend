package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.department.DepartmentDto;
import com.prismasolutions.DWbackend.mapper.DepartmentMapper;
import com.prismasolutions.DWbackend.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDto> getAll() {
        return departmentMapper.toDtoList(departmentRepository.findAll());
    }
}
