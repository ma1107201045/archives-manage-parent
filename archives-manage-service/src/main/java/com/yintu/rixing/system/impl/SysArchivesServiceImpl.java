package com.yintu.rixing.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (SysArchives)表服务实现类
 *
 * @author makejava
 * @since 2020-12-17 18:48:38
 */
@Service
public class SysArchivesServiceImpl extends ServiceImpl<SysArchivesMapper,SysArchives> implements ISysArchivesService {

    /**
     * 模版1
     */
    @Autowired
    SysTableMessgeMapper messgeMapper;


    @Override
    public List<SysTableMessge> findByTableName(String tablename) {


        return messgeMapper.findByTable(tablename);
    }


    /**
     * 添加字段信息
     * @param tablename
     * @param sysArchives
     * @return
     */
    @Override
    public boolean innertFleId(String tablename, SysArchives sysArchives) {

        String chinesename = sysArchives.getChinesename();//中文名 备注
        String field = sysArchives.getStandardfields();//标准字段
        Integer fieldlength = sysArchives.getFieldlength();//字段长度
        String valuetype = sysArchives.getValuetype();

        Map<String,Object> map=new HashMap();
        map.put("field",field);
        map.put("chinesename",chinesename);
        map.put("fieldlength",fieldlength);
        map.put("valuetype",valuetype);
        map.put("tablename",tablename);

        boolean b = messgeMapper.innertFleId(map);
        return b;
    }

    /**
     * 根据表名删除字段信息
     * @param tablename
     * @param fieldName
     * @return
     */
    @Override
    public boolean deleField(String tablename, String standardfields) {

      boolean b = messgeMapper.deleField(tablename,standardfields);


        return b;
    }
}