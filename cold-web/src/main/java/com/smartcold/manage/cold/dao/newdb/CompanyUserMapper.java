package com.smartcold.manage.cold.dao.newdb;

import com.smartcold.manage.cold.entity.newdb.CompanyUser;

public interface CompanyUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyUser record);

    int insertSelective(CompanyUser record);

    CompanyUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompanyUser record);

    int updateByPrimaryKey(CompanyUser record);
    
    CompanyUser selectByUserId(Integer userid);
}