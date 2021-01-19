package com.yintu.rixing.data;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.base.PageDto;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileFormDto;
import com.yintu.rixing.util.ResultDataUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * <p>
 * 数据档案库文件表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/data/data-archives-library-file")
public class DataArchivesLibraryFileController extends Authenticator implements BaseController<DataArchivesLibraryFileFormDto, PageDto, DataArchivesLibraryFile, Integer> {


    @Override
    public ResultDataUtil<Object> add(DataArchivesLibraryFileFormDto formDto) {
        return null;
    }

    @Override
    public ResultDataUtil<Object> remove(Set<Integer> ids) {
        return null;
    }

    @Override
    public ResultDataUtil<Object> edit(Integer id, DataArchivesLibraryFileFormDto formDto) {
        return null;
    }

    @Override
    public ResultDataUtil<DataArchivesLibraryFile> findById(Integer id) {
        return null;
    }

    @Override
    public ResultDataUtil<Page<DataArchivesLibraryFile>> findPage(PageDto queryDto) {
        return null;
    }
}
