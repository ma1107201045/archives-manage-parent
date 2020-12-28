package com.yintu.rixing.system.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-12-18
 */
@Service
public class SysLibraryServiceImpl extends ServiceImpl<SysLibraryMapper, SysLibrary> implements ISysLibraryService {

    @Autowired
    SysLibraryMapper sysLibraryMapper;

    @Autowired
    SysArchivesMapper sysArchivesMapper;

    @Autowired
    SysTemplateMapper sysTemplateMapper;


    /**
     * 查询单条数据及下级数据
     *
     * @param id
     * @return
     */
    @Override
    public List<Map<String, Object>> findById(Integer id) {
        List<SysLibrary> sysLibraries = sysLibraryMapper.findById(id);
        List<Map<String, Object>> list = new ArrayList<>();

        if (sysLibraries.size() > 0) {
            for (SysLibrary menu : sysLibraries) {
                Map<String, Object> map = new LinkedHashMap<>();
                List<Object> children = getChildren(menu.getId());
                if (children.size() == 0) {
                    map.put("id", menu.getPid());
                    map.put("librarynumber", menu.getLibrarynumber());
                    map.put("libraryname", menu.getLibraryname());
                    map.put("tablename", menu.getTablename());
                    map.put("ordernumber", menu.getOrdernumber());
                    map.put("describee", menu.getDescribee());
                    map.put("libraryclasses", menu.getLibraryclasses());


                } else {
                    map.put("id", menu.getPid());
                    map.put("librarynumber", menu.getLibrarynumber());
                    map.put("libraryname", menu.getLibraryname());
                    map.put("tablename", menu.getTablename());
                    map.put("ordernumber", menu.getOrdernumber());
                    map.put("describee", menu.getDescribee());
                    map.put("libraryclasses", menu.getLibraryclasses());
                    map.put("data", getChildren(menu.getId()));
                }
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 引入模板
     *
     * @return
     */

    @Override
    public List<SysArchives> findByIdScrchives(Integer id) {
        QueryWrapper<SysArchives> qw = new QueryWrapper<>();
        qw.eq("lid", id);
        List<SysArchives> list = sysArchivesMapper.selectList(qw);

        return list;
    }

    /**
     * 根据库名称,库类别组合查询
     *
     * @param libraryname
     * @param libraryclasses
     * @return
     */


    @Override
    public List<SysLibrary> findBylnameAndlclasses(String libraryname, String libraryclasses) {
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("libraryname", libraryname);
        queryWrapper.eq("libraryclasses", libraryclasses);

        List list = sysLibraryMapper.selectList(queryWrapper);


        return list;
    }



    /**
     * 档案库引入模板
     *
     *
     * @param name
     * @param tepname
     * @return
     */
    @Override
    public Map<String, Object> findBytepname(String tepname, Integer id,String  librname) {
        //添加子节点信息
        SysLibrary sysLibrary1 = new SysLibrary();
        sysLibrary1.setPid(id);
        sysLibrary1.setLibraryname(librname);

        int insert = sysLibraryMapper.insert(sysLibrary1);



//        //判断有没有模板现在
//        SysLibrary sysLibrary = sysLibraryMapper.findByOne(id);
//        String tablename = sysLibrary.getTablename();
//        //查询表名在当前数据库中有没有存在,如果有就不创建直接查询当前的结构信息
//        Integer intege = sysLibraryMapper.seleBytablename(tablename);
//        if (intege != 0) {
//            //说明存在
//            List<SysTableMessge> byTable = sysTemplateMapper.findByTable(tablename);
//            return ResponseDataUtil.error("已有模板,请勿再次引入", byTable);
//        }


        SysTemplate sysTemplate = sysTemplateMapper.findByname(tepname);
        String laotablename = sysTemplate.getTablename();

        //动态创建表
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        String format1 = format.format(date);
        String newtableName = "sys_" + format1;//表名

        try {
            Integer newTable = sysLibraryMapper.createNewTable(newtableName, laotablename);

            //设置当前档案信息的表名
            Integer im = sysLibraryMapper.updateByIdTablename(id, newtableName);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //然后返回新表的结构数据
        List<SysTableMessge> sysTableMessges = sysTemplateMapper.findByTable(newtableName);

        return ResponseDataUtil.error("模板引入成功", sysTableMessges);

    }


    /**
     * 编辑档案库的模板字段信息
     *
     * @param id
     * @param laofield
     * @param newfield
     * @param type
     * @param fieldlength
     * @return
     */
    @Override
    public Integer editfield(Integer id, String laofield, String newfield, String type, String fieldlength) {
        Map<String, Object> map = new HashMap<>();
        //根据id查询
        SysLibrary sysLibrary = sysLibraryMapper.findByOne(id);
        String tablename = sysLibrary.getTablename();
        map.put("tablename", tablename);
        map.put("laofield", laofield);
        map.put("newfield", newfield);
        map.put("type", type);
        map.put("fieldlength", fieldlength);
        Integer editfield = sysTemplateMapper.editfield(map);
        return editfield;

    }

    /**
     * 添加模板字段信息
     *
     * @param id
     * @param sysTableMessge
     * @return
     */
    @Override
    public Map<String, Object> innertFleId(Integer id, SysTableMessge sysTableMessge) {
        Map<String, Object> map = new HashMap<>();
        String tablename = sysLibraryMapper.findByOne(id).getTablename();

        String field = sysTableMessge.getField();

        map.put("tablename", tablename);
        map.put("field", field);
        map.put("fieldlength", sysTableMessge.getFieldlength());
        map.put("valuetype", sysTableMessge.getType());
        //判断此字段在此表中存不存在，如果存在则停止添加 不存在则添加
        SysTableMessge messge = sysLibraryMapper.findByField(tablename, field);
        if (messge != null) {
            //说明存在return ResponseDataUtil.error("此字段已存在,请勿再次添加");
            return ResponseDataUtil.error("此字段已存在,请勿再次添加");
        }
        try {
            sysTemplateMapper.innertFleId(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseDataUtil.ok("添加字段成功");

    }

    /**
     * 删除字段
     *
     * @param id
     * @param field
     * @return
     */
    @Override
    public Map<String, Object> delfield(Integer id, String fieldName) {
        String tablename = sysLibraryMapper.findByOne(id).getTablename();
        try {
            sysTemplateMapper.deleField(tablename, fieldName);
            return ResponseDataUtil.ok("删除成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDataUtil.error("删除失败");
        }

    }

    /**
     * 向上调整位置
     * @param id
     * @param sfieldName
     * @param mfieldName
     * @param valuetype
     * @param fieldlength
     * @return
     */
    @Override
    public Map<String, Object> xsfield(Integer id, String sfieldName, String mfieldName, String valuetype, String fieldlength) {
        Map<String,Object> map=new HashMap<>();
        String tablename = sysLibraryMapper.findByOne(id).getTablename();
        map.put("tablename",tablename);
        map.put("sfieldName",sfieldName);
        map.put("mfieldName",mfieldName);
        map.put("valuetype",valuetype);
        map.put("fieldlength",fieldlength);

        Integer integer=   sysLibraryMapper.xsfield(map);
        if (integer==0){
            return ResponseDataUtil.ok("成功");
        }
        return  ResponseDataUtil.error("失败");
    }

    @Override
    public Map<String, Object> xxfield(Integer id, String xfieldName, String mfieldName, String valuetype, String fieldlength) {
        Map<String,Object> map=new HashMap<>();
        String tablename = sysLibraryMapper.findByOne(id).getTablename();
        map.put("tablename",tablename);
        map.put("xfieldName",xfieldName);
        map.put("mfieldName",mfieldName);
        map.put("valuetype",valuetype);
        map.put("fieldlength",fieldlength);

        Integer integer=   sysLibraryMapper.xxfield(map);
        if (integer==0){
            return ResponseDataUtil.ok("成功");
        }
        return  ResponseDataUtil.error("失败");

    }

    /**
     * 递归
     *
     * @param id
     * @return
     */
    public List<Object> getChildren(Integer id) {
        List<Object> list = new ArrayList<>();
        List<SysLibrary> treeMenu = sysLibraryMapper.findById(id);
        for (SysLibrary menu : treeMenu) {
            Map<String, Object> map = new LinkedHashMap<>();
            List<Object> children = getChildren(menu.getId());
            if (children.size() == 0) {
                map.put("id", menu.getPid());
                map.put("librarynumber", menu.getLibrarynumber());
                map.put("libraryname", menu.getLibraryname());
                map.put("tablename", menu.getTablename());
                map.put("ordernumber", menu.getOrdernumber());
                map.put("describee", menu.getDescribee());
                map.put("libraryclasses", menu.getLibraryclasses());

            } else {
                map.put("id", menu.getPid());
                map.put("libraryname", menu.getLibraryname());
                map.put("tablename", menu.getTablename());
                map.put("ordernumber", menu.getOrdernumber());
                map.put("describee", menu.getDescribee());
                map.put("libraryclasses", menu.getLibraryclasses());
                map.put("data", getChildren(menu.getId()));
            }
            list.add(map);
        }
        return list;
    }


}
