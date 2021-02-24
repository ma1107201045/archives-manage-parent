package com.yintu.rixing.make;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.make.MakeBorrowApproveDto;
import com.yintu.rixing.pojo.MakeBorrowAuditorPojo;
import com.yintu.rixing.vo.make.MakeBorrowTransferVo;

import java.util.List;

/**
 * <p>
 * 编研和审核人中间表 服务类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-22
 */
public interface IMakeCompilationAuditorService extends IService<MakeCompilationAuditor> {

    void addAuditors(Integer compilationId, Integer auditorId);

    void approve(MakeBorrowApproveDto makeBorrowApproveDto);

    List<MakeCompilationAuditor> listByMakeCompilationAuditor(MakeBorrowAuditorPojo makeBorrowAuditorPojo);

}
