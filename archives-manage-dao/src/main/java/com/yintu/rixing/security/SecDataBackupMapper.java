package com.yintu.rixing.security;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yintu.rixing.vo.security.SecDataBackupVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 安全数据备份表 Mapper 接口
 * </p>
 *
 * @author mlf
 * @since 2021-01-13
 */
@Mapper
public interface SecDataBackupMapper extends BaseMapper<SecDataBackup> {


    SecDataBackupVo selectByIgnoreTableNames(String... ignoreTableNames);


}
