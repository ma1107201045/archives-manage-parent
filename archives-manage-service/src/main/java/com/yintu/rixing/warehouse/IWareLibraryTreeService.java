package com.yintu.rixing.warehouse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.util.TreeUtil;

import java.util.List;

/**
 * <p>
 * 库房管理的树表 服务类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-28
 */
public interface IWareLibraryTreeService extends IService<WareLibraryTree> {

    List<TreeUtil> listTree(Integer parentId);
}
