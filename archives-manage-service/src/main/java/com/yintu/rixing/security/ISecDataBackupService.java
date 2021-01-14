package com.yintu.rixing.security;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.common.PageDto;
import com.yintu.rixing.vo.security.SecDataBackupVo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 安全数据备份表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-01-13
 */
public interface ISecDataBackupService extends IService<SecDataBackup> {

    @Transactional(rollbackFor = {Exception.class})
    void backup(HttpServletRequest request, Integer loginUserId, String loginUsername, String loginNickName);

    @Transactional(rollbackFor = {Exception.class})
    void remove(Collection<? extends Serializable> idList);

    @Transactional(rollbackFor = {Exception.class})
    void restore(Integer id);

    SecDataBackupVo findByIgnoreTableNames(String... ignoreTableNames);

    Page<SecDataBackup> page(PageDto pageDto);

}
