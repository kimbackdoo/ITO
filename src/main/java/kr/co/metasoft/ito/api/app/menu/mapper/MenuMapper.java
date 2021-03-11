package kr.co.metasoft.ito.api.app.menu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import kr.co.metasoft.ito.api.app.menu.dto.MenuDto;
import kr.co.metasoft.ito.api.app.menu.dto.MenuParamDto;

@Repository
@Mapper
public interface MenuMapper {

    @Select (value = "<script>"
            + "WITH RECURSIVE tree_menu AS ( "
            + "    SELECT "
            + "        id, "
            + "        parent_id, "
            + "        name, "
            + "        url, "
            + "        icon, "
            + "        ranking, "
            + "        CAST(LPAD(ranking, 3, '0') AS VARCHAR(100)) AS ranking_path, "
            + "        1 AS depth "
            + "    FROM "
            + "        tb_menu "
            + "    WHERE "
            + "        <choose> "
            + "            <otherwise> "
            + "                parent_id IS NULL "
            + "            </otherwise> "
            + "            <when test='menuParamDto.upperId != null and menuParamDto.upperId != \"\"'> "
            + "                id = #{menuParamDto.upperId} "
            + "            </when> "
            + "            <when test='menuParamDto.upperParentId != null and menuParamDto.upperParentId != \"\"'> "
            + "                parent_id = #{menuParamDto.upperParentId} "
            + "            </when> "
            + "        </choose> "
            + "    UNION ALL "
            + "    SELECT "
            + "        m.id, "
            + "        m.parent_id, "
            + "        m.name, "
            + "        m.url, "
            + "        m.icon, "
            + "        m.ranking, "
            + "        CAST(CONCAT(tm.ranking_path, '/', LPAD(m.ranking, 3, '0')) AS VARCHAR(100)) AS ranking_path, "
            + "        tm.depth + 1 AS depth "
            + "    FROM "
            + "        tb_menu m "
            + "    INNER JOIN "
            + "        tree_menu tm "
            + "    ON "
            + "        m.parent_id = tm.id "
            + ") "
            + "SELECT "
            + "    id, "
            + "    parent_id, "
            + "    name, "
            + "    url, "
            + "    icon, "
            + "    ranking, "
            + "    ranking_path, "
            + "    depth "
            + "FROM "
            + "    tree_menu "
            + "ORDER BY "
            + "    ranking_path "
            + "</script>")
    public List<MenuDto> selectMenuList(
            @Param (value = "menuParamDto") MenuParamDto menuParamDto);

}