package com.yintu.rixing.archives;

import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.vo.archives.ArchCommonQueryVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/19 11:12:11
 * @Version: 1.0
 */
public abstract class ArchAbstractService {

    @Autowired
    public ISysArchivesLibraryService iSysArchivesLibraryService;

    public List<ArchCommonQueryVo> findArchivesName() {
        List<SysArchivesLibrary> sysArchivesLibraries = this.iSysArchivesLibraryService.listByType((short) 2);
        List<ArchCommonQueryVo> archQuantityStatisticsQueryVos = new ArrayList<>();
        for (SysArchivesLibrary sysArchivesLibrary : sysArchivesLibraries) {
            ArchCommonQueryVo archQuantityStatisticsQueryVo = new ArchCommonQueryVo();
            archQuantityStatisticsQueryVo.setArchivesId(sysArchivesLibrary.getId());
            archQuantityStatisticsQueryVo.setArchivesName(sysArchivesLibrary.getName());
            archQuantityStatisticsQueryVos.add(archQuantityStatisticsQueryVo);
        }
        return archQuantityStatisticsQueryVos;
    }
}
