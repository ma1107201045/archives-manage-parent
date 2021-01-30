package com.yintu.rixing.warehouse;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysTemplateLibraryFieldTypeService;
import com.yintu.rixing.system.SysTemplateLibraryFieldType;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.data.DataCommonVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 库房管理实体库字段表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/warehouse/ware-template-library-field")
public class WareTemplateLibraryFieldController {
    @Autowired
    private IWareTemplateLibraryFieldService iWareTemplateLibraryFieldService;
    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;

    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "查询模板库字段类型列表信息")
    @GetMapping("/findTemplateLibraryFieldType")
    @ApiOperation(value = "查询模板库字段类型列表信息", notes = "查询模板库字段类型列表信息")
    public ResultDataUtil<List<SysTemplateLibraryFieldType>> findSysTemplateLibraryFieldTypes() {
        List<SysTemplateLibraryFieldType> sysTemplateLibraryFieldTypes = iSysTemplateLibraryFieldTypeService.list(new QueryWrapper<SysTemplateLibraryFieldType>().orderByDesc("id"));
        return ResultDataUtil.ok("查询模板库字段类型列表信息成功", sysTemplateLibraryFieldTypes);
    }

    @PostMapping("/add")
    @Log(level = EnumLogLevel.DEBUG, module = "库房管理", context = "添加新的库房管理实体表字段信息")
    @ApiOperation(value = "添加新的库房管理实体表字段信息", notes = "添加新的库房管理实体表字段信息")
    public Map<String, Object> add(WareTemplateLibraryField wareTemplateLibraryField) {
        wareTemplateLibraryField.setTypeId(0);
        iWareTemplateLibraryFieldService.save(wareTemplateLibraryField);
        return ResponseDataUtil.ok("新增添加新的库房管理实体表字段信息成功");
    }

    @DeleteMapping("/{ids}")
    @Log(level = EnumLogLevel.WARN, module = "库房管理", context = "根据id删除库房管理实体表字段信息")
    @ApiOperation(value = "根据id删除库房管理实体表字段信息", notes = "根据id删除库房管理实体表字段信息")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        if (iWareTemplateLibraryFieldService.removeByIds(ids)) {
            return ResponseDataUtil.ok("删除库房管理实体表字段信息成功");
        }
        return ResponseDataUtil.error("删除库房管理实体表字段信息失败");
    }

    @PutMapping("/{id}")
    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "根据id编辑对应的库房管理实体表字段信息")
    @ApiOperation(value = "根据id编辑对应的库房管理实体表字段信息", notes = "根据id编辑对应的库房管理实体表字段信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> edit(@PathVariable Integer id, WareTemplateLibraryField wareTemplateLibraryField) {
        iWareTemplateLibraryFieldService.updateById(wareTemplateLibraryField);
        return ResponseDataUtil.ok("修改库房管理实体表字段信息成功");
    }

    @GetMapping
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "分页查询库房管理实体表字段列表信息")
    @ApiOperation(value = "分页库房管理实体表字段列表信息", notes = " 分页库房管理实体表字段列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "15")
    })
    public Map<String, Object> findWareTemplateLibraryFieldDatas(@RequestParam Integer num, @RequestParam Integer size) {
        QueryWrapper<WareTemplateLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("`order`");
        Page<WareTemplateLibraryField> wareTemplateLibraryFieldPage = iWareTemplateLibraryFieldService.page(new Page<>(num, size), queryWrapper);
        for (WareTemplateLibraryField record : wareTemplateLibraryFieldPage.getRecords()) {
            record.setSysTemplateLibraryFieldType(iSysTemplateLibraryFieldTypeService.getById(record.getTemplateLibraryFieldTypeId()));
        }
        return ResponseDataUtil.ok("分页查询库房管理实体表字段列表信息成功", wareTemplateLibraryFieldPage);
    }

    @GetMapping("/findFieldDatas")
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "查询库房管理实体表字段列表信息")
    @ApiOperation(value = "库房管理实体表字段列表信息", notes = " 库房管理实体表字段列表信息")
    public Map<String, Object> findFieldDatas() {
        QueryWrapper<WareTemplateLibraryField> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("`order`");
        List<WareTemplateLibraryField> wareTemplateLibraryFieldPage = iWareTemplateLibraryFieldService.list(queryWrapper);
        QueryWrapper<SysTemplateLibraryFieldType> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.like("data_key","datetime");
        SysTemplateLibraryFieldType sysTemplateLibraryFieldType=iSysTemplateLibraryFieldTypeService.getOne(queryWrapper1);
        WareTemplateLibraryField wareTemplateLibraryField1=new WareTemplateLibraryField();
        Long time1 = new Date().getTime();
        wareTemplateLibraryField1.setId( time1.intValue());
        wareTemplateLibraryField1.setName("入库时间");
        wareTemplateLibraryField1.setDataKey("inWarehouseTime");
        wareTemplateLibraryField1.setLength(0);
        wareTemplateLibraryField1.setIndex(0);
        wareTemplateLibraryField1.setRequired(0);
        wareTemplateLibraryField1.setTemplateLibraryFieldTypeId(sysTemplateLibraryFieldType.getId());
        wareTemplateLibraryField1.setQuery((short)0);
        wareTemplateLibraryField1.setTitle((short)1);
        wareTemplateLibraryField1.setForm((short)0);
        wareTemplateLibraryField1.setTypeId(3);
        wareTemplateLibraryFieldPage.add(wareTemplateLibraryField1);
        WareTemplateLibraryField wareTemplateLibraryField2=new WareTemplateLibraryField();
        wareTemplateLibraryField2.setId(time1.intValue()+1);
        wareTemplateLibraryField2.setName("出库时间");
        wareTemplateLibraryField2.setDataKey("outWarehouseTime");
        wareTemplateLibraryField2.setLength(0);
        wareTemplateLibraryField2.setIndex(0);
        wareTemplateLibraryField2.setRequired(0);
        wareTemplateLibraryField2.setTemplateLibraryFieldTypeId(sysTemplateLibraryFieldType.getId());
        wareTemplateLibraryField2.setQuery((short)0);
        wareTemplateLibraryField2.setTitle((short)1);
        wareTemplateLibraryField2.setForm((short)0);
        wareTemplateLibraryField2.setTypeId(3);
        wareTemplateLibraryFieldPage.add(wareTemplateLibraryField2);
        WareTemplateLibraryField wareTemplateLibraryField3=new WareTemplateLibraryField();
        QueryWrapper<SysTemplateLibraryFieldType> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.like("data_key","varchar");
        SysTemplateLibraryFieldType sysTemplateLibraryFieldType1=iSysTemplateLibraryFieldTypeService.getOne(queryWrapper2);
        wareTemplateLibraryField3.setId(time1.intValue()+2);
        wareTemplateLibraryField3.setName("档案号");
        wareTemplateLibraryField3.setDataKey("archivesNum");
        wareTemplateLibraryField3.setLength(255);
        wareTemplateLibraryField3.setIndex(1);
        wareTemplateLibraryField3.setRequired(1);
        wareTemplateLibraryField3.setTemplateLibraryFieldTypeId(sysTemplateLibraryFieldType1.getId());
        wareTemplateLibraryField3.setQuery((short)1);
        wareTemplateLibraryField3.setTitle((short)1);
        wareTemplateLibraryField3.setForm((short)0);
        wareTemplateLibraryField3.setTypeId(3);
        wareTemplateLibraryFieldPage.add(wareTemplateLibraryField3);
        return ResponseDataUtil.ok("查询库房管理实体表字段列表信息成功", wareTemplateLibraryFieldPage);
    }

    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "修改库房管理实体表字段顺序")
    @PatchMapping("/{id1}/{id2}")
    @ApiOperation(value = "修改库房管理实体表字段顺序", notes = "修改库房管理实体表字段顺序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id1", dataType = "int", value = "主键id1", required = true, paramType = "path"),
            @ApiImplicitParam(name = "id2", dataType = "int", value = "主键id2", required = true, paramType = "path")
    })
    public ResultDataUtil<Object> editOrder(@PathVariable Integer id1, @PathVariable Integer id2) {
        iWareTemplateLibraryFieldService.updateOrderByIds(id1, id2);
        return ResultDataUtil.ok("修改库房管理实体表字段顺序成功");
    }



    //动态创建数据表
    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "动态创建库房管理实体表")
    @PostMapping("/creatTable")
    @ApiOperation(value = "动态创建库房管理实体表", notes = "动态创建库房管理实体表")
    @ApiImplicitParam(name = "jsonObject", dataType = "JSONObject", value = "创建表的字段", required = true, paramType = "path")
    public ResultDataUtil<Object> creatTable(@RequestBody JSONObject jsonObject) {
        //JSONObject jsonObject=new JSONObject();
       // jsonObject.put("jsonObject","[{\"id\":3,\"createBy\":\"huxiaowen\",\"createTime\":\"2021-01-22 20:39:36\",\"modifiedBy\":\"huxiaowen\",\"modifiedTime\":\"2021-01-22 20:39:36\",\"name\":\"测试三\",\"dataKey\":\"test3\",\"length\":11,\"required\":1,\"index\":1,\"order\":11111,\"templateLibraryFieldTypeId\":2,\"typeId\":0},{\"id\":1,\"createBy\":\"huxiaowen\",\"createTime\":\"2021-01-22 20:16:58\",\"modifiedBy\":\"huxiaowen\",\"modifiedTime\":\"2021-01-22 20:16:58\",\"name\":\"测试一\",\"dataKey\":\"test1\",\"length\":111,\"required\":1,\"index\":1,\"order\":11,\"templateLibraryFieldTypeId\":2,\"typeId\":0},{\"id\":2,\"createBy\":\"huxiaowen\",\"createTime\":\"2021-01-22 20:17:15\",\"modifiedBy\":\"huxiaowen\",\"modifiedTime\":\"2021-01-22 20:17:15\",\"name\":\"测试二\",\"dataKey\":\"test2\",\"length\":11,\"required\":1,\"index\":1,\"order\":1,\"templateLibraryFieldTypeId\":2,\"typeId\":0}]");
        iWareTemplateLibraryFieldService.creatTable(jsonObject);
        return ResultDataUtil.ok("动态创建库房管理实体表成功");
    }

    //判断字段选项能否 从右边转到左边
    @GetMapping("/findTurnLeftState")
    @ApiOperation(value = "判断字段选项能否从右边转到左边", notes = "判断字段选项能否从右边转到左边")
    public ResultDataUtil<Object>findTurnLeftState(){
        Integer state=iWareTemplateLibraryFieldService.findTurnLeftState();
        return ResultDataUtil.ok("查询状态成功",state);
    }

    //新增入库
    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "新增入库")
    @PostMapping("/addWarehouse")
    @ApiOperation(value = "新增入库", notes = "新增入库")
    @ApiImplicitParam(name = "jsonObject", dataType = "JSONObject", value = "新增数据", required = true, paramType = "path")
    public ResultDataUtil<Object> addWarehouse(@RequestBody JSONObject jsonObject) {
        iWareTemplateLibraryFieldService.addWarehouse(jsonObject);
        return ResultDataUtil.ok("新增入库成功");
    }

    //根据id 出库
    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "更改出库")
    @PutMapping("/outWarehouse/{id}")
    @ApiOperation(value = "更改出库", notes = "更改出库")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> outWarehouse(@PathVariable Integer id) {
        iWareTemplateLibraryFieldService.outWarehouse(id);
        return ResultDataUtil.ok("更改出库成功");
    }

    //根据id 入库
    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "更改入库")
    @PutMapping("/inWarehouse/{id}")
    @ApiOperation(value = "更改入库", notes = "更改入库")
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> inWarehouse(@PathVariable Integer id) {
        iWareTemplateLibraryFieldService.inWarehouse(id);
        return ResultDataUtil.ok("更改入库成功");
    }

    //分页查询 实体档案管理
    @GetMapping("/findAllEntityArchives")
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "分页查询实体档案管理信息")
    @ApiOperation(value = "分页查询实体档案管理信息", notes = " 分页查询实体档案管理信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "15")
    })
    public ResultDataUtil<DataCommonVo>findAllEntityArchives(@RequestParam Integer num, @RequestParam Integer size){
        DataCommonVo dataCommonVo =iWareTemplateLibraryFieldService.findAllEntityArchives(num,size);
        return ResultDataUtil.ok("分页查询实体档案管理信息成功",dataCommonVo);
    }

    //多条件查询分页查询 实体档案管理
    @PostMapping("/findAllEntityArchivesBySomethings/{num}/{size}")
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "根据条件分页查询实体档案管理信息")
    @ApiOperation(value = "根据条件分页查询实体档案管理信息", notes = " 根据条件分页查询实体档案管理信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "jsonObject", dataType = "JSONObject", value = "条件数据", required = true, paramType = "path")
    })
    public ResultDataUtil<DataCommonVo>findAllEntityArchivesBySomethings(@RequestBody JSONObject jsonObject, @PathVariable Integer num, @PathVariable Integer size){
        DataCommonVo dataCommonVo =iWareTemplateLibraryFieldService.findAllEntityArchivesBySomethings(num,size,jsonObject);
        return ResultDataUtil.ok("根据条件分页查询实体档案管理信息成功",dataCommonVo);
    }

    //分页查询 实体档案入库管理
    @GetMapping("/findInWarehouse")
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "分页查询实体档案入库管理信息")
    @ApiOperation(value = "分页查询实体档案入库管理信息", notes = " 分页查询实体档案入库管理信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "15")
    })
    public ResultDataUtil<DataCommonVo>findInWarehouse(@RequestParam Integer num, @RequestParam Integer size){
        DataCommonVo dataCommonVo =iWareTemplateLibraryFieldService.findInWarehouse(num,size);
        return ResultDataUtil.ok("分页查询实体档案入库管理信息成功",dataCommonVo);
    }

    //多条件分页查询 实体档案入库管理
    @PostMapping("/findInWarehouseBySomethings/{num}/{size}")
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "根据条件分页查询实体档案入库管理信息")
    @ApiOperation(value = "根据条件分页查询实体档案入库管理信息", notes = " 根据条件分页查询实体档案入库管理信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "jsonObject", dataType = "JSONObject", value = "条件数据", required = true, paramType = "path")
    })
    public ResultDataUtil<DataCommonVo>findInWarehouseBySomethings(@RequestBody JSONObject jsonObject,@PathVariable Integer num, @PathVariable Integer size){
        DataCommonVo dataCommonVo =iWareTemplateLibraryFieldService.findInWarehouseBySomethings(num,size,jsonObject);
        return ResultDataUtil.ok("根据条件分页查询实体档案入库管理信息成功",dataCommonVo);
    }

    //分页查询 实体档案出库管理
    @GetMapping("/findOutWarehouse")
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "分页查询实体档案出库管理信息")
    @ApiOperation(value = "分页查询实体档案出库管理信息", notes = " 分页查询实体档案出库管理信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "15")
    })
    public ResultDataUtil<DataCommonVo>findOutWarehouse(@RequestParam Integer num, @RequestParam Integer size){
        DataCommonVo dataCommonVo =iWareTemplateLibraryFieldService.findOutWarehouse(num,size);
        return ResultDataUtil.ok("分页查询实体档案出库管理信息成功",dataCommonVo);
    }
    //多条件分页查询 实体档案出库管理
    @PostMapping("/findOutWarehouseBySomethings/{num}/{size}")
    @Log(level = EnumLogLevel.TRACE, module = "库房管理", context = "根据条件分页查询实体档案出库管理信息")
    @ApiOperation(value = "根据条件分页查询实体档案出库管理信息", notes = " 根据条件分页查询实体档案出库管理信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "jsonObject", dataType = "JSONObject", value = "条件数据", required = true, paramType = "path")
    })
    public ResultDataUtil<DataCommonVo>findOutWarehouseBySomethings(@RequestBody JSONObject jsonObject,@PathVariable Integer num, @PathVariable Integer size){
        DataCommonVo dataCommonVo =iWareTemplateLibraryFieldService.findOutWarehouseBySomethings(num,size,jsonObject);
        return ResultDataUtil.ok("根据条件分页查询实体档案出库管理信息成功",dataCommonVo);
    }

    //根据id 对数据进行编辑
    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "根据id编辑对应的实体库表数据")
    @PutMapping("/updateWarehouse/{id}")
    @ApiOperation(value = "根据id编辑对应的实体库表数据", notes = "根据id编辑对应的实体库表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jsonObject", dataType = "JSONObject", value = "需要编辑数据", required = true, paramType = "path"),
            @ApiImplicitParam(name = "id", dataType = "int", value = "需要编辑数据id", required = true, paramType = "path")
    })
    public ResultDataUtil<Object> updateWarehouse(@RequestBody JSONObject jsonObject,@PathVariable Integer id) {
        iWareTemplateLibraryFieldService.updateWarehouse(jsonObject,id);
        return ResultDataUtil.ok("编辑对应的实体库表数据成功");
    }

    //根据id 对数据进行删除
    @Log(level = EnumLogLevel.INFO, module = "库房管理", context = "根据id删除对应的实体库表数据")
    @DeleteMapping("/deleteWarehouse/{id}")
    @ApiOperation(value = "根据id删除对应的实体库表数据", notes = "根据id删除对应的实体库表数据")
    @ApiImplicitParam(name = "id", dataType = "int", value = "需要删除数据id", required = true, paramType = "path")
    public ResultDataUtil<Object> deleteWarehouse(@PathVariable Integer id) {
        iWareTemplateLibraryFieldService.deleteWarehouse(id);
        return ResultDataUtil.ok("删除对应的实体库表数据成功");
    }








    public static void main(String[] args) {//@RequestBody JSONObject jsonObject,
        Date date = new Date();
        String archivesNum="";
        int day = DateUtil.dayOfMonth(date);
        int month = DateUtil.month(date) + 1;
        String yearStr = String.valueOf(DateUtil.year(date));
        String monthStr = Integer.toString(month).length() == 1 ? "0" + month : Integer.toString(month);
        String dayStr = Integer.toString(day).length() == 1 ? "0" + day : Integer.toString(day);
        archivesNum="STDH-"+yearStr+monthStr+dayStr+"-0001";

        System.out.println(archivesNum);
        String[] split = archivesNum.split("-");
        for (String s : split) {
            System.out.println("sss1"+s);
        }

        System.out.println("ssss"+split[2]);







        /*JSONObject jsonObject=new JSONObject();
       // jsonObject.put("sss","[{\"id\":3,\"createBy\":\"huxiaowen\",\"createTime\":\"2021-01-22 20:39:36\",\"modifiedBy\":\"huxiaowen\",\"modifiedTime\":\"2021-01-22 20:39:36\",\"name\":\"测试三\",\"dataKey\":\"test3\",\"length\":1111,\"required\":1,\"index\":1,\"order\":11111,\"templateLibraryFieldTypeId\":2,\"typeId\":0},{\"id\":1,\"createBy\":\"huxiaowen\",\"createTime\":\"2021-01-22 20:16:58\",\"modifiedBy\":\"huxiaowen\",\"modifiedTime\":\"2021-01-22 20:16:58\",\"name\":\"测试一\",\"dataKey\":\"test1\",\"length\":111,\"required\":1,\"index\":1,\"order\":11,\"templateLibraryFieldTypeId\":2,\"typeId\":0},{\"id\":2,\"createBy\":\"huxiaowen\",\"createTime\":\"2021-01-22 20:17:15\",\"modifiedBy\":\"huxiaowen\",\"modifiedTime\":\"2021-01-22 20:17:15\",\"name\":\"测试二\",\"dataKey\":\"test2\",\"length\":11,\"required\":1,\"index\":1,\"order\":1,\"templateLibraryFieldTypeId\":2,\"typeId\":0}]");
        jsonObject.put("sss","{\"id\":3,\"createBy\":\" \",\"createTime\":\"2021-01-22 20:39:36\",\"modifiedBy\":\"huxiaowen\",\"modifiedTime\":\"2021-01-22 20:39:36\",\"name\":\"测试三\",\"dataKey\":\"test3\",\"length\":1111,\"required\":1,\"index\":1,\"order\":11111,\"templateLibraryFieldTypeId\":2,\"typeId\":0}");
        //JSONArray tableDates = jsonObject.getJSONArray("sss");
        JSONObject sss = (JSONObject)jsonObject.get("sss");
        Map<String, String> jsonMap = JSONObject.toJavaObject(sss, Map.class);
        System.out.println(jsonObject.get("sss"));
        System.out.println("sssss"+jsonMap);*/

       /* for (Object tableDate : tableDates) {
            System.out.println("6666"+tableDate);
           // Map map = JSONObject.parseObject(JSONObject.toJSONString(tableDate), Map.class);
            Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(tableDate));
            System.out.println("7777"+map);
            Set<String> strings = map.keySet();
            System.out.println("9999"+strings);
            Collection<Object> values = map.values();
            System.out.println("11111"+values);
            Object modifiedTime = map.get("modifiedTime");
            System.out.println("888"+modifiedTime);

        }*/

    }

}
