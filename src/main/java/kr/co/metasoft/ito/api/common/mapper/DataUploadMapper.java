package kr.co.metasoft.ito.api.common.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.entity.PersonEntity;


@Repository
@Mapper
public interface DataUploadMapper {

    public Long selectUploadFileOrder();

    public void insertUploadFileOrder(
            @Param (value = "list") List<PersonEntity> param );

/*
    public List<DataUploadCMDBEntity> selectCmdbList(
            @Param (value = "cmdbParamDto") CmdbParamDto cmdbParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectCmdbListCount(
            @Param (value = "cmdbParamDto") CmdbParamDto cmdbParamDto);

    public DataUploadCMDBEntity selectCmdb(
            @Param (value = "uploadFileOrder") Long uploadFileOrder,
            @Param (value = "fileRowNum") Long fileRowNum);


    public void insertUploadFileOrderByCmdb(
            @Param (value = "uploadFileOrder") Long uploadFileOrder);


    public void insertUploadFileOrderByCmdbOrigin(
            @Param (value = "list") List<DataUploadCMDBEntity> param );


    public List<UploadFileLogDto> selectCmdbLogList(
            @Param (value = "pageRequest") PageRequest pageRequest);


    public Integer selectCmdbLogListCount();
*/


}
