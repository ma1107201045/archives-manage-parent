package com.yintu.rixing.person;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.dto.person.PerBorrowManagementQueryDto;
import com.yintu.rixing.make.MakeBorrow;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: mlf
 * @Date: 2021/2/5 17:21:38
 * @Version: 1.0
 */
@Mapper
public interface PerBorrowManagementMapper {

    Page<MakeBorrow> selectPage(Page<MakeBorrow> page, PerBorrowManagementQueryDto perBorrowManagementQueryDto);
}
