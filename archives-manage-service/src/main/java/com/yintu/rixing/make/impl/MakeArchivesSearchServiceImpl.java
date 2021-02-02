package com.yintu.rixing.make.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.make.IMakeArchivesSearchService;
import com.yintu.rixing.make.MakeArchivesSearchMapper;
import com.yintu.rixing.pojo.MakeArchivesSearchPojo;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.vo.make.MakeArchivesSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:59:59
 * @Version: 1.0
 */
@Service
public class MakeArchivesSearchServiceImpl implements IMakeArchivesSearchService {
    @Autowired
    private MakeArchivesSearchMapper makeArchivesSearchMapper;
    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private IDataFormalLibraryService iDataFormalLibraryService;

    @Override
    public IPage<MakeArchivesSearchVo> listByKeyWord(MakeArchivesSearchDto makeArchivesSearchDto) {
        Page<MakeArchivesSearchVo> page1 = new Page<>();
        Integer num = makeArchivesSearchDto.getNum();
        Integer size = makeArchivesSearchDto.getSize();
        String keyWord = makeArchivesSearchDto.getKeyWord();
        Page<MakeArchivesSearchPojo> page2 = makeArchivesSearchMapper.selectByKeyWord(new Page<>(num, size), keyWord);
        BeanUtil.copyProperties(page2, page1, "records");
        List<MakeArchivesSearchPojo> makeArchivesSearchPojos = page2.getRecords();
        List<MakeArchivesSearchVo> makeArchivesSearchVos = new ArrayList<>();
        for (MakeArchivesSearchPojo makeArchivesSearchPojo : makeArchivesSearchPojos) {
            Integer archivesLibId = makeArchivesSearchPojo.getArchivesLibId();
            Short archivesLibType = makeArchivesSearchPojo.getArchivesLibType();
            Integer archivesDirectoryId = makeArchivesSearchPojo.getArchivesDirectoryId();
            MakeArchivesSearchVo makeArchivesSearchVo = new MakeArchivesSearchVo();
            BeanUtil.copyProperties(makeArchivesSearchPojo, makeArchivesSearchVo);
            if (archivesLibType.equals((short) 1)) { //电子档案
                SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibId);
                if (sysArchivesLibrary != null) {
                    String name = sysArchivesLibrary.getName();
                    makeArchivesSearchVo.setArchivesLibName(name);
                    Map<String, Object> map = iDataFormalLibraryService.getById(archivesDirectoryId, archivesLibId);
                    makeArchivesSearchVo.setArchivesDirectoryNum((String) map.get(EnumArchivesLibraryDefaultField.ARCHIVES_NUM.getDataKey()));
                }
            } else {// 实体档案

            }
            makeArchivesSearchVos.add(makeArchivesSearchVo);
        }
        page1.setRecords(makeArchivesSearchVos);
        return page1;
    }
}
