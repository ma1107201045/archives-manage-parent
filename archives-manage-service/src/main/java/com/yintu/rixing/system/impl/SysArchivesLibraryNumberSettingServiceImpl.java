package com.yintu.rixing.system.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.data.DataCommonKV;
import com.yintu.rixing.dto.system.SysArchivesLibraryNumberSettingDto;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统档案库字段表（档案设置表） 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-03-09
 */
@Service
public class SysArchivesLibraryNumberSettingServiceImpl extends ServiceImpl<SysArchivesLibraryNumberSettingMapper, SysArchivesLibraryNumberSetting> implements ISysArchivesLibraryNumberSettingService {

    @Autowired
    private ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;

    @Override
    public void archivesLibraryNumberSetting(List<SysArchivesLibraryNumberSettingDto> sysArchivesLibraryNumberSettingDtos) {
        AssertUtil.notLength(sysArchivesLibraryNumberSettingDtos, "不能为空");
        Integer archivesLibraryId = sysArchivesLibraryNumberSettingDtos.get(0).getArchivesLibraryId();
        List<SysArchivesLibraryNumberSetting> sysArchivesLibraryNumberSettings = new ArrayList<>();
        for (SysArchivesLibraryNumberSettingDto sysArchivesLibraryNumberSettingDto : sysArchivesLibraryNumberSettingDtos) {
            SysArchivesLibraryNumberSetting sysArchivesLibraryNumberSetting = new SysArchivesLibraryNumberSetting();
            sysArchivesLibraryNumberSetting.setArchivesLibraryId(archivesLibraryId);
            sysArchivesLibraryNumberSetting.setArchivesLibraryFieldId(sysArchivesLibraryNumberSettingDto.getArchivesLibraryFieldId());
            sysArchivesLibraryNumberSetting.setSeparator(sysArchivesLibraryNumberSettingDto.getSeparator());
            sysArchivesLibraryNumberSetting.setCustomCharacter(sysArchivesLibraryNumberSettingDto.getCustomCharacter());
            sysArchivesLibraryNumberSettings.add(sysArchivesLibraryNumberSetting);
        }
        QueryWrapper<SysArchivesLibraryNumberSetting> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(SysArchivesLibraryNumberSetting::getArchivesLibraryId, archivesLibraryId);
        this.remove(queryWrapper1);
        this.saveBatch(sysArchivesLibraryNumberSettings);
    }

    @Override
    public List<SysArchivesLibraryNumberSettingDto> findByArchivesLibraryId(Integer archivesLibraryId) {
        List<SysArchivesLibraryNumberSettingDto> sysArchivesLibraryNumberSettingDtos = new ArrayList<>();
        QueryWrapper<SysArchivesLibraryNumberSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibraryNumberSetting::getArchivesLibraryId, archivesLibraryId);
        List<SysArchivesLibraryNumberSetting> sysArchivesLibraryNumberSettings = this.list(queryWrapper);
        sysArchivesLibraryNumberSettings.forEach(sysArchivesLibraryNumberSetting -> {
            SysArchivesLibraryNumberSettingDto sysArchivesLibraryNumberSettingDto = new SysArchivesLibraryNumberSettingDto();
            sysArchivesLibraryNumberSettingDto.setArchivesLibraryId(archivesLibraryId);
            Integer archivesLibraryFieldId = sysArchivesLibraryNumberSetting.getArchivesLibraryFieldId();
            String separator = sysArchivesLibraryNumberSetting.getSeparator();
            String customCharacter = sysArchivesLibraryNumberSetting.getCustomCharacter();
            if (archivesLibraryFieldId != null) {
                SysArchivesLibraryField sysArchivesLibraryField = iSysArchivesLibraryFieldService.getById(archivesLibraryFieldId);
                sysArchivesLibraryNumberSettingDto.setArchivesLibraryFieldId(archivesLibraryFieldId);
                sysArchivesLibraryNumberSettingDto.setArchivesLibraryFieldName(sysArchivesLibraryField.getName());
            } else {
                sysArchivesLibraryNumberSettingDto.setSeparator(separator);
                sysArchivesLibraryNumberSettingDto.setCustomCharacter(customCharacter);
            }
            sysArchivesLibraryNumberSettingDtos.add(sysArchivesLibraryNumberSettingDto);
        });
        return sysArchivesLibraryNumberSettingDtos;
    }

    /**
     * 生成档号
     *
     * @param id 档案库id
     * @param dataCommonKVS 字段集合
     * @return 档号
     */
    @Override
    public String createArchivesCode(int id, List<DataCommonKV> dataCommonKVS) {
        StringBuilder code = new StringBuilder();
        if (id > 0 && dataCommonKVS != null && dataCommonKVS.size() > 0) {
            QueryWrapper<SysArchivesLibraryNumberSetting> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysArchivesLibraryNumberSetting::getArchivesLibraryId, id);
            List<SysArchivesLibraryNumberSetting> settingList = this.list(queryWrapper);
            if (settingList.size() > 0) {
                for (SysArchivesLibraryNumberSetting archivesLibraryNumberSetting : settingList) {
                    Integer fieldId = archivesLibraryNumberSetting.getArchivesLibraryFieldId();
                    String separator = archivesLibraryNumberSetting.getSeparator();
                    String customCharacter = archivesLibraryNumberSetting.getCustomCharacter();
                    if (fieldId != null && fieldId > 0) {
                        //字段信息
                        SysArchivesLibraryField libraryField = iSysArchivesLibraryFieldService.getById(fieldId);
                        if (libraryField != null && null != libraryField.getDataKey() && !"".equals(libraryField.getDataKey())) {
                            for (DataCommonKV dataCommonKV : dataCommonKVS) {
                                if(dataCommonKV.getFieldName().equalsIgnoreCase(libraryField.getDataKey())){
                                    Object fieldValue = dataCommonKV.getFieldValue();
                                    if(null != fieldValue){
                                        code.append(dataCommonKV.getFieldValue());
                                    }

                                }
                            }
                        }
                    } else if (null != separator && !"".equals(separator)) {
                        code.append(separator);
                    } else if (null != customCharacter && !"".equals(customCharacter)) {
                        code.append(customCharacter);
                    }
                }
            }
        } else {
            code.append("DH-" + UUID.randomUUID().toString(true).substring(0, 8));
        }

        return code.toString();
    }
}
