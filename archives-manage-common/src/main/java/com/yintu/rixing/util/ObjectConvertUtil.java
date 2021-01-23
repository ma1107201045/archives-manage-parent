package com.yintu.rixing.util;

import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.exception.BaseRuntimeException;

import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/18 15:23:54
 * @Version: 1.0
 */
public class ObjectConvertUtil {
    public static final String ARCHIVES_LIBRARY_ID = "archivesLibraryId";
    public static final String ID = "id";
    public static final String NUM = "num";
    public static final String SIZE = "size";


    public static DataCommonFormDto getAddFormDto(Map<String, String> params) {
        DataCommonFormDto dataCommonFormDto = new DataCommonFormDto();
        String archivesLibraryId = params.get(ARCHIVES_LIBRARY_ID);
        AssertUtil.notEmpty(archivesLibraryId, "档案库id不能为空");
        dataCommonFormDto.setArchivesLibraryId(Integer.valueOf(archivesLibraryId));
        dataCommonFormDto.setParams(params);
        return dataCommonFormDto;
    }

    public static DataCommonFormDto getNotAddFormDto(Map<String, String> params) {
        DataCommonFormDto dataCommonFormDto = getAddFormDto(params);
        dataCommonFormDto.setId(Integer.valueOf(params.get(ID)));
        return dataCommonFormDto;
    }

    public static DataCommonQueryDto getQueryDto(Map<String, String> params) {
        DataCommonQueryDto dataCommonQueryDto = new DataCommonQueryDto();
        String archivesLibraryId = params.get(ARCHIVES_LIBRARY_ID);
        String num = params.get(NUM);
        String size = params.get(SIZE);
        AssertUtil.notEmpty(archivesLibraryId, "档案库id不能为空");
        AssertUtil.notEmpty(num, "页码不能为空");
        AssertUtil.notEmpty(size, "页数不能为空");
        dataCommonQueryDto.setArchivesLibraryId(Integer.valueOf(archivesLibraryId));
        dataCommonQueryDto.setNum(Integer.valueOf(params.get(NUM)));
        dataCommonQueryDto.setSize(Integer.valueOf(params.get(SIZE)));
        dataCommonQueryDto.setParams(params);
        return dataCommonQueryDto;
    }

}
