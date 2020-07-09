package com.sihenzhang.library.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sihenzhang.library.app.entity.Student;
import com.sihenzhang.library.app.mapper.StudentMapper;
import com.sihenzhang.library.app.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
