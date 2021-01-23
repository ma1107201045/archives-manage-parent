package com.yintu.rixing.warehouse;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WareTemplateLibraryFiledMapper extends BaseMapper<WareTemplateLibraryField> {

    Integer findTable(String tableName);
}