package com.yintu.rixing.util;

import com.yintu.rixing.dto.data.DataCommonDto;

import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/18 15:23:54
 * @Version: 1.0
 */
public class ObjectConvertUtil {

    public static final String ID = "id";
    public static final String ARCHIVES_ID = "archivesId";

    public static DataCommonDto getAddDto(Map<String, String> params) {
        DataCommonDto dataCommonDto = new DataCommonDto();
        dataCommonDto.setArchivesId(Integer.valueOf(params.get(ARCHIVES_ID)));
        dataCommonDto.setParams(params);
        return dataCommonDto;
    }

    public static DataCommonDto getRemoveDto(Map<String, String> params) {
        DataCommonDto dataCommonDto = getAddDto(params);
        dataCommonDto.setId(Integer.valueOf(params.get(ID)));
        return dataCommonDto;
    }

    public static DataCommonDto getEditDto(Map<String, String> params) {
        return getRemoveDto(params);
    }


}
