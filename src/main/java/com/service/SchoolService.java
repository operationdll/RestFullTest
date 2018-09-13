package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.BaseMapper;
import com.dto.SchoolDto;

@Service
@Transactional // 会把所有public方法加上事务
public class SchoolService {

	@Autowired
	private BaseMapper baseMapper;

	public List<SchoolDto> selectSchools() {
		return baseMapper.selectSchools();
	}

	public int insertSchool(SchoolDto schoolDto) {
		return baseMapper.insertSchool(schoolDto);
	}

	public int deleteSchool(int id) {
		return baseMapper.deleteSchool(id);
	}

	public int updateSchool(SchoolDto schoolDto) {
		return baseMapper.updateSchool(schoolDto);
	}

	public BaseMapper getBaseMapper() {
		return baseMapper;
	}

	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}
}
