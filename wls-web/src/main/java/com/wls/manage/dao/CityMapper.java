package com.wls.manage.dao;

import com.wls.manage.entity.City_infoEntity;
import com.wls.manage.entity.Province_infoEntity;
import com.wls.manage.entity.School_infoEntity;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author jiangkaiqiang
 * @version 创建时间：2016-11-7 下午1:56:40 
 *
 */
public interface CityMapper {
    List<City_infoEntity> findCitysByProvinceId(@Param("provinceID") int provinceID);
    
    List<City_infoEntity> findCityList();
    
    List<School_infoEntity> findSchoolsByCityId(@Param("cityID") int cityID);

    City_infoEntity findCityById(@Param("CityID") int CityID);

	Province_infoEntity findProvinceById(@Param("provinceID") int provinceID);

	School_infoEntity findSchoolById(@Param("schoolID") int schoolID);

	List<School_infoEntity>  findSchoolByName(@Param("schoolName") String schoolName);

	List<Province_infoEntity> findProvinceByName(@Param("provinceName") String provinceName);

	List<City_infoEntity> findCityByNameAndProvinceId(@Param("cityName") String cityName, @Param("provinceID") Integer provinceID);

	List<School_infoEntity> findSchoolByNameAndCityId(@Param("schoolName") String schoolName, @Param("cityID")Integer cityID);
}
