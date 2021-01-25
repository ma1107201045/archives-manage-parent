package com.yintu.rixing.warehouse;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 库房管理实体库字段表 服务类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-15
 */
public interface IWareTemplateLibraryFieldService extends IService<WareTemplateLibraryField> {

    void updateOrderByIds(Integer id1, Integer id2);

    void creatTable(JSONObject jsonObject);

    Integer findTable(String tableName);

    Integer findTurnLeftState();

    void addWarehouse(JSONObject jsonObject);
}
