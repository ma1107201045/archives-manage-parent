package com.yintu.rixing.make.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.IDataFormalLibraryService;
import com.yintu.rixing.dto.make.MakeArchivesSearchElectronicDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.make.IMakeArchivesSearchService;
import com.yintu.rixing.make.MakeArchivesSearchMapper;
import com.yintu.rixing.pojo.MakeArchivesSearchPojo;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;
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
    public Page<MakeArchivesSearchElectronicVo> listElectronicByKeyWord(MakeArchivesSearchElectronicDto makeArchivesSearchDto) {
        Page<MakeArchivesSearchElectronicVo> page1 = new Page<>();
        Integer num = makeArchivesSearchDto.getNum();
        Integer size = makeArchivesSearchDto.getSize();
        String keyWord = makeArchivesSearchDto.getKeyWord();
        Page<MakeArchivesSearchPojo> page2 = makeArchivesSearchMapper.selectByKeyWord(new Page<>(num, size), keyWord);
        BeanUtil.copyProperties(page2, page1, "records");
        List<MakeArchivesSearchPojo> makeArchivesSearchPojos = page2.getRecords();
        List<MakeArchivesSearchElectronicVo> makeArchivesSearchVos = new ArrayList<>();
        for (MakeArchivesSearchPojo makeArchivesSearchPojo : makeArchivesSearchPojos) {
            Integer archivesLibId = makeArchivesSearchPojo.getArchivesLibId();
            Integer archivesDirectoryId = makeArchivesSearchPojo.getArchivesDirectoryId();
            MakeArchivesSearchElectronicVo makeArchivesSearchVo = new MakeArchivesSearchElectronicVo();
            BeanUtil.copyProperties(makeArchivesSearchPojo, makeArchivesSearchVo);
            SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibId);
            makeArchivesSearchVo.setArchivesLibName(sysArchivesLibrary.getName());
            Map<String, Object> map = iDataFormalLibraryService.getById(archivesDirectoryId, archivesLibId);
            if (map != null) {
                makeArchivesSearchVo.setArchivesDirectoryNum((String) map.get(EnumArchivesLibraryDefaultField.ARCHIVES_NUM.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectoryTopicName((String) map.get(EnumArchivesLibraryDefaultField.TOPIC_NAME.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectoryRetentionPeriod(map.get(EnumArchivesLibraryDefaultField.RETENTION_PERIOD.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectoryValidPeriod(map.get(EnumArchivesLibraryDefaultField.VALID_PERIOD.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectorySecurityLevel(map.get(EnumArchivesLibraryDefaultField.SECURITY_LEVEL.getDataKey()));
                makeArchivesSearchVo.setArchivesDirectoryFilingAnnual((String) map.get(EnumArchivesLibraryDefaultField.FILING_ANNUAL.getDataKey()));
            }
            makeArchivesSearchVos.add(makeArchivesSearchVo);
        }
        page1.setRecords(makeArchivesSearchVos);
        return page1;
    }

}
