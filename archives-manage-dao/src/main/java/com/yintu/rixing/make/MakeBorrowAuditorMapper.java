package com.yintu.rixing.make;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 借阅申请文件和审核人中间表 Mapper 接口
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
@Mapper
public interface MakeBorrowAuditorMapper extends BaseMapper<MakeBorrowAuditor> {

}
