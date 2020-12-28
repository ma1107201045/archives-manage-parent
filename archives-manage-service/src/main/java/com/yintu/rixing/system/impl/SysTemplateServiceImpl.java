package com.yintu.rixing.system.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yintu.rixing.system.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-12-23
 */
@Service
public class SysTemplateServiceImpl extends ServiceImpl<SysTemplateMapper, SysTemplate> implements ISysTemplateService {

    @Autowired
    SysArchivesMapper sysArchivesMapper;

    @Autowired
    SysTemplateMapper sysTemplateMapper;
    /**
     * 模版1
     */
    @Autowired
    SysTableMessgeMapper messgeMapper;

    @Autowired
    SysLibraryMapper sysLibraryMapper;


    /**
     * 添加字段信息
     *  @param tablename
     * @param sysArchive * @returns
     * @return
     */
    @Override
    public Integer innertFleId(Integer id, SysTableMessge sysTableMessge) {
        Map<String, Object> map = new HashMap<>();

        String tableName = findByIdGetTableName(id);
        map.put("tablename", tableName);
        map.put("field", sysTableMessge.getField());
        map.put("fieldlength", sysTableMessge.getFieldlength());
        map.put("valuetype", sysTableMessge.getType());
        Integer b = sysTemplateMapper.innertFleId(map);

        return b;
    }

    /**
     * 根据删除字段信息
     *
     * @param id
     * @param fieldName
     * @return
     */
    @Override
    public boolean deleField(Integer id, String fieldName) {
        String tableName = findByIdGetTableName(id);
        boolean b = sysTemplateMapper.deleField(tableName, fieldName);
        return b;
    }

    /**
     * 修改表的字段信息
     *
     * @param tablename
     * @param sysArchives
     * @return
     */


    /**
     * 查询模板的结构信息
     *
     * @param id
     * @return
     */
    @Override
    public List<SysTableMessge> findById(Integer id) {
        String tableName = findByIdGetTableName(id);
        List<SysTableMessge> byTable = sysTemplateMapper.findByTable(tableName);
        return byTable;
    }

    @Override
    public Integer editfield(Integer id, String laofield, String newfield, String type, String fieldlength) {

        String tableName = findByIdGetTableName(id);
        Map<String, Object> map = new HashMap<>();
        map.put("tablename", tableName);
        map.put("laofield", laofield);
        map.put("newfield", newfield);
        map.put("type", type);
        map.put("fieldlength", fieldlength);
        Integer b = sysTemplateMapper.editfield(map);

        return b;
    }

    @Override
    public Map<String, Object> xsfield(Integer id, String sfieldName, String mfieldName, String valuetype, String fieldlength) {
        Map<String,Object> map=new HashMap<>();
        String tableName = findByIdGetTableName(id);
        map.put("tablename",tableName);
        map.put("sfieldName",sfieldName);
        map.put("mfieldName",mfieldName);
        map.put("valuetype",valuetype);
        map.put("fieldlength",fieldlength);

        Integer integer=   sysLibraryMapper.xsfield(map);
        if (integer==0){
            return ResponseDataUtil.error("失败");
        }
        return  ResponseDataUtil.ok("成功");
    }

    @Override
    public Map<String, Object> xxfield(Integer id, String xfieldName, String mfieldName, String valuetype, String fieldlength) {
        Map<String,Object> map=new HashMap<>();
        String tableName = findByIdGetTableName(id);
        map.put("tablename",tableName);
        map.put("xfieldName",xfieldName);
        map.put("mfieldName",mfieldName);
        map.put("valuetype",valuetype);
        map.put("fieldlength",fieldlength);

        Integer integer=   sysLibraryMapper.xxfield(map);
        if (integer==0){
            return ResponseDataUtil.error("失败");
        }
        return  ResponseDataUtil.ok("成功");
    }


    /**
     * 根据表名称映入
     *
     * @param id
     * @return
     */
    @Override
    public List<SysArchives> findByIdScrchives(Integer id) {
        QueryWrapper<SysArchives> qw = new QueryWrapper<>();
        qw.eq("tid", id);
        List<SysArchives> list = sysArchivesMapper.selectList(qw);
        return list;
    }

    /**
     * 根据模式名称查询
     *
     * @param mantname
     * @return
     */
    @Override
    public List<SysTemplate> findBymanageentname(String mantname) {
        QueryWrapper<SysTemplate> qw = new QueryWrapper<>();
        qw.eq("management", mantname);
        List<SysTemplate> sysTemplates = sysTemplateMapper.selectList(qw);
        return sysTemplates;

    }

    //根据id查询表名
    public String findByIdGetTableName(Integer id) {
        if (id != null) {
            //查询此id对应的模板查询名称
            SysTemplate sysTemplate = sysTemplateMapper.findById(id);
            if (!StringUtils.isEmpty(sysTemplate)) {
                String tablename = sysTemplate.getTablename();
                return tablename;
            }

        }
        return null;

    }


    //无限查询
    @Override
    public List<Map<String, Object>> findmulu(Integer id) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<SysTemplate> sysTemplates = sysTemplateMapper.findByPId(id);
        if (sysTemplates.size()>0){
            for (SysTemplate sysTemplate : sysTemplates) {
                Map<String, Object> map = new LinkedHashMap<>();
                List<Object> children = getChildren(sysTemplate.getId());
                if (children.size() == 0) {
                    map.put("id",sysTemplate.getId());
                    map.put("name",sysTemplate.getTepname());

                }else {
                    map.put("id",sysTemplate.getId());
                    map.put("name",sysTemplate.getTepname());
                    map.put("data",getChildren(sysTemplate.getId()));
                }
                list.add(map);
            }
        }
        return list;
    }


    /**
     * 递归
     *
     * @param id
     * @return
     */
    public List<Object> getChildren(Integer id) {
        List<Object> list = new ArrayList<>();
        List<SysTemplate> treeMenu = sysTemplateMapper.findByPId(id);
        for (SysTemplate menu : treeMenu) {
            Map<String, Object> map = new LinkedHashMap<>();
            List<Object> children = getChildren(menu.getId());
            if (children.size() == 0) {
                map.put("id",menu.getId());
                map.put("name",menu.getTepname());

            } else {
                map.put("id",menu.getId());
                map.put("name",menu.getTepname());
                map.put("data",getChildren(menu.getId()));

            }
            list.add(map);
        }
        return list;
    }

}
