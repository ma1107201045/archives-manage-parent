package com.yintu.rixing.make.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.make.IMakeArchivesCollectionService;
import com.yintu.rixing.make.MakeArchivesCollection;
import com.yintu.rixing.make.MakeArchivesCollectionMapper;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.system.SysArchivesLibraryMapper;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.make.MakeArchivesSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 档案收藏表 服务实现类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-03-16
 */
@Service
public class MakeArchivesCollectionServiceImpl extends ServiceImpl<MakeArchivesCollectionMapper, MakeArchivesCollection> implements IMakeArchivesCollectionService {
    @Autowired
    private MakeArchivesCollectionMapper makeArchivesCollectionMapper;
    @Autowired
    private IMakeArchivesCollectionService makeArchivesCollectionService;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private SysArchivesLibraryMapper sysArchivesLibraryMapper;

    @Override
    public Page<MakeArchivesSearchVo> findMyCollection(Integer userId, Integer num, Integer size) {
        List<MakeArchivesSearchVo> list=new ArrayList<>();
        Page<MakeArchivesSearchVo> page1 = new Page<>();
        QueryWrapper<MakeArchivesCollection> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userid",userId);
        Page<MakeArchivesCollection> archivesCollectionPage = makeArchivesCollectionService.page(new Page<>(num, size), queryWrapper);
        BeanUtil.copyProperties(archivesCollectionPage, page1, "records");
        for (MakeArchivesCollection makeArchivesCollection : archivesCollectionPage.getRecords()) {
            Integer archivesid = makeArchivesCollection.getArchivesid();
            Integer archiveslibid = makeArchivesCollection.getArchiveslibid();
            Integer id = makeArchivesCollection.getId();
            SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archiveslibid);
            String dataKey = sysArchivesLibrary.getDataKey();
            String fullTableName = TableNameUtil.getFullTableName(dataKey);
            Map<Object,String> map=sysArchivesLibraryMapper.findHourseData(fullTableName,archivesid);
            System.out.println("mapppppp="+map);
            String archives_code = map.get("archives_code");//档号
            String folder_code = map.get("folder_code");//案卷号
            String file_code = map.get("file_code");//件号
            String title = map.get("title");//提名
            String year =String.valueOf(map.get("year"));//年度
            String responsible_person = map.get("responsible_person");//责任者

            MakeArchivesSearchVo makeArchivesSearchVo=new MakeArchivesSearchVo();
            makeArchivesSearchVo.setArchivesDirectoryId(archivesid);
            makeArchivesSearchVo.setArchivesLibId(archiveslibid);
            makeArchivesSearchVo.setArchivesCode(archives_code);
            makeArchivesSearchVo.setFolderCode(folder_code);
            makeArchivesSearchVo.setFileCode(file_code);
            makeArchivesSearchVo.setTitle(title);
            makeArchivesSearchVo.setYear(year);
            makeArchivesSearchVo.setResponsiblePerson(responsible_person);
            makeArchivesSearchVo.setArchivesFile(sysArchivesLibrary.getName());
            makeArchivesSearchVo.setId(id);
            list.add(makeArchivesSearchVo);
        }
        page1.setRecords(list);
        return page1;
    }
}
