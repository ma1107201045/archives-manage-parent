package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * (SysArchives)表服务接口
 *
 * @author makejava
 * @since 2020-12-17 18:48:29
 */
public interface ISysArchivesService extends IService<SysArchives> {

    List<SysTableMessge> findByTableName(String tablename);//模板1查询
    boolean innertFleId(String tablename,SysArchives sysArchives);//模板添加字段信息

    boolean deleField(String tablename, String fieldName);

}