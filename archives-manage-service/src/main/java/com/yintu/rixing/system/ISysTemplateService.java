package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlf
 * @since 2020-12-23
 */
public interface ISysTemplateService extends IService<SysTemplate> {

    List<SysArchives> findByIdScrchives(Integer id);

    List<SysTemplate> findBymanageentname(String mantname);

    Integer innertFleId(Integer id, SysTableMessge sysTableMessge);//模板添加字段信息

    boolean deleField(Integer id, String fieldName);

    List<SysTableMessge> findById(Integer id);


    Integer editfield(Integer id, String laofield, String newfield, String type, String fieldlength);

    Map<String, Object> xsfield(Integer id, String sfieldName, String mfieldName, String valuetype, String fieldlength);

    Map<String, Object> xxfield(Integer id, String xfieldName, String mfieldName, String valuetype, String fieldlength);

    List<Map<String, Object>> findmulu(Integer id);

}
