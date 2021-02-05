package com.yintu.rixing.make;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.pojo.MakeBorrowAuditorPojo;

import java.util.List;

/**
 * <p>
 * 借阅申请文件和审核人中间表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
public interface IMakeBorrowAuditorService extends IService<MakeBorrowAuditor> {


    List<MakeBorrowAuditor> listByMakeBorrowAuditorPojo(MakeBorrowAuditorPojo makeBorrowAuditorPojo);
}
