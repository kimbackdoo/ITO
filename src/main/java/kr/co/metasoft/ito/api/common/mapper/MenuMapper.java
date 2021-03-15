package kr.co.metasoft.ito.api.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.common.dto.MenuParamDto;
import kr.co.metasoft.ito.api.common.entity.MenuEntity;
import kr.co.metasoft.ito.common.util.PageRequest;

@Repository
@Mapper
public interface MenuMapper {

    public List<MenuEntity> selectMenuList(
            @Param (value = "menuParamDto") MenuParamDto menuParamDto,
            @Param (value = "pageRequest") PageRequest pageRequest);

    public Integer selectMenuListCount(
            @Param (value = "menuParamDto") MenuParamDto menuParamDto);

    public MenuEntity selectMenu(
            @Param (value = "id") Long id);

}