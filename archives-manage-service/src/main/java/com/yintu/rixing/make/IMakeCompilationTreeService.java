package com.yintu.rixing.make;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.util.TreeUtil;

import java.util.List;

/**
 * <p>
 * 档案编研的树表 服务类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-20
 */
public interface IMakeCompilationTreeService extends IService<MakeCompilationTree> {

    List<TreeUtil> listTree(Integer parentId);
}
