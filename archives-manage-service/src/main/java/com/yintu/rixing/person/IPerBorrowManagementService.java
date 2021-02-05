package com.yintu.rixing.person;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.dto.person.PerBorrowManagementQueryDto;
import com.yintu.rixing.vo.make.MakeBorrowVo;
import com.yintu.rixing.vo.person.PerBorrowManagementVo;

/**
 * @Author: mlf
 * @Date: 2021/2/5 16:41:24
 * @Version: 1.0
 */
public interface IPerBorrowManagementService {


    Page<MakeBorrowVo> selectPage(PerBorrowManagementQueryDto perBorrowManagementQueryDto);


}
