package com.yintu.rixing.security.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.common.PageDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.security.ISecDataBackupService;
import com.yintu.rixing.security.SecDataBackup;
import com.yintu.rixing.security.SecDataBackupMapper;
import com.yintu.rixing.util.AddressUtil;
import com.yintu.rixing.util.JdbcInfoUtil;
import com.yintu.rixing.util.PathUtil;
import com.yintu.rixing.vo.security.SecDataBackupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 安全数据备份表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-13
 */
@Service
public class SecDataBackupServiceImpl extends ServiceImpl<SecDataBackupMapper, SecDataBackup> implements ISecDataBackupService {

    @Autowired
    private SecDataBackupMapper secDataBackupMapper;

    @Override
    public void backup(HttpServletRequest request, Integer loginUserId, String loginUsername, String loginNickName) {
        String host = JdbcInfoUtil.getHost();
        String databaseName = JdbcInfoUtil.getDatabase();
        String username = JdbcInfoUtil.getUsername();
        String password = JdbcInfoUtil.getPassword();
        String[] ignoreTableNames = {"de_test", "sec_data_backup"};
        StringBuilder sb = new StringBuilder();
        for (String ignoreTableName : ignoreTableNames) {
            sb.append("--ignore-table").append(" ").append(databaseName).append(".").append(ignoreTableName).append(" ");
        }
        String backupPath = PathUtil.getBackupPath();
        String UUIDStr = UUID.fastUUID().toString(true);
        String name = UUIDStr + ".sql";
        String backupFile = backupPath + name;
        String mysqlFormatCommand = "mysqldump --single-transaction -h %s -u %s -p%s --databases %s %s > %s";
        String mysqlCommand = String.format(mysqlFormatCommand, host, username, password, databaseName, sb.toString(), backupFile);
        String result = this.execCommand(mysqlCommand);
        if (StrUtil.containsIgnoreCase(result, "ERROR"))
            throw new BaseRuntimeException("备份失败:" + result);
        SecDataBackup secDataBackup = new SecDataBackup();
        secDataBackup.setCreateTime(DateUtil.date());
        secDataBackup.setUserId(loginUserId);
        secDataBackup.setUsername(loginUsername);
        secDataBackup.setOperator(loginNickName);
        secDataBackup.setName(name);
        secDataBackup.setBackupTime(DateUtil.date());
        secDataBackup.setBackupPath(backupPath.substring(0, backupPath.length() - 1));
        double backFileSize = FileUtil.size(FileUtil.file(backupFile)) / 1024.0 / 1024.0;
        secDataBackup.setBackupFileSize(new BigDecimal(backFileSize).setScale(2, RoundingMode.HALF_UP).doubleValue());
        secDataBackup.setRequestMapping(AddressUtil.getAddress(request) + PathUtil.getBackupMapping() + name);

        SecDataBackupVo secDataBackupVo = this.findByIgnoreTableNames(ignoreTableNames);
        secDataBackup.setTableCount(secDataBackupVo.getTableCount().intValue());
        secDataBackup.setRecordCount(secDataBackupVo.getRecordCount().intValue());
        secDataBackup.setRecordSize(secDataBackupVo.getRecordSize());
        this.save(secDataBackup);

    }

    public String execCommand(String cmd) {
        return RuntimeUtil.getResult(SystemUtil.getOsInfo().isWindows() ? RuntimeUtil.exec("cmd", "/c", cmd) : RuntimeUtil.exec("sh", "-c", cmd), Charset.defaultCharset());
    }

    @Override
    public void remove(Collection<? extends Serializable> idList) {
        List<SecDataBackup> secDataBackups = this.listByIds(idList);
        for (SecDataBackup secDataBackup : secDataBackups) {
            String backupFile = secDataBackup.getBackupPath() + File.separator + secDataBackup.getName();
            if (FileUtil.exist(backupFile)) {
                FileUtil.del(backupFile);
            }
        }
        this.removeByIds(idList);
    }

    @Override
    public void restore(Integer id) {
        SecDataBackup secDataBackup = this.getById(id);
        if (secDataBackup != null) {
            String host = JdbcInfoUtil.getHost();
            String databaseName = JdbcInfoUtil.getDatabase();
            String username = JdbcInfoUtil.getUsername();
            String password = JdbcInfoUtil.getPassword();
            String backupFile = secDataBackup.getBackupPath() + File.separator + secDataBackup.getName();
            if (FileUtil.exist(backupFile)) {
                String mysqlFormatCommand = "mysql -h %s -u %s -p%s %s < %s";
                String mysqlCommand = String.format(mysqlFormatCommand, host, username, password, databaseName, backupFile);
                String result = this.execCommand(mysqlCommand);
                if (StrUtil.containsIgnoreCase(result, "[ERROR]"))
                    throw new BaseRuntimeException("还原失败");
            }
            secDataBackup.setRecoveryTime(DateUtil.date());
            this.updateById(secDataBackup);
        }
    }

    @Override
    public SecDataBackupVo findByIgnoreTableNames(String... ignoreTableNames) {
        return secDataBackupMapper.selectByIgnoreTableNames(ignoreTableNames);
    }

    @Override
    public Page<SecDataBackup> page(PageDto pageDto) {
        Integer num = pageDto.getNum();
        Integer size = pageDto.getSize();
        return this.page(new Page<>(num, size));
    }
}
