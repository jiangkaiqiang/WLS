package com.smartcold.manage.cold.dao.olddb;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartcold.manage.cold.entity.olddb.PlatformDoorSetEntity;

public interface PlatformDoorSetMapping {

	PlatformDoorSetEntity findById(@Param("id") int id);

	List<PlatformDoorSetEntity> findByRdcId(@Param("rdcId") int rdcId);
}
