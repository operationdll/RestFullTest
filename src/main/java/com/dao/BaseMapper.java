package com.dao;

import java.util.List;

import com.dto.SchoolDto;

/**
 * 数据库查询BaseMapper
 * 
 * @author Administrator
 * 
 */
public interface BaseMapper {
	// 学校信息
	List<SchoolDto> selectSchools();

	int insertSchool(SchoolDto schoolDto);

	int deleteSchool(int id);

	int updateSchool(SchoolDto schoolDto);

}
