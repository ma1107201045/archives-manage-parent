package com.yintu.rixing.warehouse.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.warehouse.IWareTemplateLibraryFieldService;
import com.yintu.rixing.warehouse.WareTemplateLibraryField;


import com.yintu.rixing.warehouse.WareTemplateLibraryFiledMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库房管理实体库字段表 服务实现类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-15
 */
@Service
public class WareTemplateLibraryFieldServiceImpl extends ServiceImpl<WareTemplateLibraryFiledMapper, WareTemplateLibraryField> implements IWareTemplateLibraryFieldService {

    @Override
    public void updateOrderByIds(Integer id1, Integer id2) {
        WareTemplateLibraryField libraryField1 = this.getById(id1);
        WareTemplateLibraryField libraryField2 = this.getById(id2);
        if (libraryField1!=null&&libraryField2!=null){
            Integer order1 = libraryField1.getOrder();
            Integer order2 = libraryField2.getOrder();
            libraryField1.setOrder(order2);
            libraryField2.setOrder(order1);
            this.updateById(libraryField1);
            this.updateById(libraryField2);
        }
    }
}
