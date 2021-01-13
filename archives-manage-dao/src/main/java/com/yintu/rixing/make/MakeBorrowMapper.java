package com.yintu.rixing.make;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 利用中心的借阅申请表 Mapper 接口
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-11
 */
@Mapper
public interface MakeBorrowMapper extends BaseMapper<MakeBorrow> {

    int deleteByPrimaryKey(Integer id);

    int insert(MakeBorrow record);

    int insertSelective(MakeBorrow record);

    MakeBorrow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MakeBorrow record);

    int updateByPrimaryKey(MakeBorrow record);
///////////////////////////////////////////////////////////////////////////////
    List<MakeBorrow> findElectronicBorrowDatas(String name, String certificateNumber);

    List<MakeBorrow> findEntityBorrowDatas(String name, String certificateNumber);
}
