package com.smartcold.bgzigbee.manage.dao;

import java.util.List;

import com.smartcold.bgzigbee.manage.entity.StorageStructureTypeEntity;

/**
 * Author: qiunian.sun
 * Date: qiunian.sun(2016-05-21 22:34)
 */
public interface StorageStructureTypeMapper {

    List<StorageStructureTypeEntity> findAll();
    
}
