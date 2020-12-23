package com.yintu.rixing.system.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.system.ISysLibraryService;
import com.yintu.rixing.system.SysLibrary;
import com.yintu.rixing.system.SysLibraryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询单条数据
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
