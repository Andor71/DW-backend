package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.department.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAll();
}
