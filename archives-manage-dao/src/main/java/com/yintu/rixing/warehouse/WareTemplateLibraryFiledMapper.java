package com.yintu.rixing.warehouse;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WareTemplateLibraryFiledMapper extends BaseMapper<WareTemplateLibraryField> {
    int deleteByPrimaryKey(Integer id);

    int insert(WareTemplateLibraryField record);

    int insertSelective(WareTemplateLibraryField record);

    WareTemplateLibraryField selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WareTemplateLibraryField record);

    int updateByPrimaryKey(WareTemplateLibraryField record);
}