package com.smartcold.zigbee.manage.dao;

import com.smartcold.zigbee.manage.entity.CompanyDeviceEntity;

import java.util.List;

/**
 * Author: qiunian.sun
 * Date: qiunian.sun(2016-05-21 22:38)
 */
public interface CompanyDeviceMapper {

    List<CompanyDeviceEntity> findAll();

}
