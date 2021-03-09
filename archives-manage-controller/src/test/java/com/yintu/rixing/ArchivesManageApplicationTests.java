package com.yintu.rixing;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.data.impl.DataCommonService;
import com.yintu.rixing.demo.DeTest;
import com.yintu.rixing.demo.DeTestMapper;
import com.yintu.rixing.demo.IDeTestService;
import com.yintu.rixing.dto.person.PerBorrowManagementQueryDto;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.make.MakeBorrow;
import com.yintu.rixing.person.PerBorrowManagementMapper;
import com.yintu.rixing.pojo.SysPermissionPojo;
import com.yintu.rixing.security.ISecLogService;
import com.yintu.rixing.security.SecLog;
import com.yintu.rixing.system.ISysPermissionService;
import com.yintu.rixing.system.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class ArchivesManageApplicationTests {
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private DeTestMapper deTestMapper;
    @Autowired
    private IDeTestService iDeTestService;
    @Autowired
    private ISysPermissionService iSysPermissionService;
    @Autowired
    private ICommTableFieldService iCommTableFieldService;
    @Autowired
    private DataCommonService dataCommonService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PerBorrowManagementMapper perBorrowManagementMapper;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    @Autowired
    private ISecLogService iSecLogService;

    @Test
    void contextLoads() {
        DeTest deTest = new DeTest();
        deTest.setId(3);
        deTest.setEmail("111111");
        deTestMapper.updateById(deTest);
    }


    @Test
    void insert() {
        DeTest SysTest = new DeTest();
        // SysTest.setName("zs");
        // SysTest.setAge(10);
        SysTest.setEmail("zs@qq.com");
        deTestMapper.insert(SysTest);
        System.out.println(SysTest.getId());

    }

    @Test
    void delete() {
        System.out.println(deTestMapper.deleteById(100L));
        QueryWrapper<DeTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DeTest::getName, "z111s")
                .eq(DeTest::getAge, 100);
        deTestMapper.delete(queryWrapper);

    }

    @Test
    void update() {
//        SysTest SysTest = new SysTest();
//        SysTest.setId(9L);
//        SysTest.setName("111");
//        SysTest.setName("zs");
//        SysTest.setAge(10);
//        SysTest.setEmail("zs@qq.com");
//        SysTestMapper.updateById(SysTest);
        UpdateWrapper<DeTest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(DeTest::getName, "zssss").set(DeTest::getAge, 20)
                .eq(DeTest::getId, 10);
        deTestMapper.update(new DeTest(), updateWrapper);


    }

    @Test
    void select() {
//        System.out.println(SysTestMapper.selectById(1L));
        QueryWrapper<DeTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(DeTest.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("name") || tableFieldInfo.getColumn().equals("age")).eq(DeTest::getName, "zs")
                .eq(DeTest::getAge, 10)
                .last("order by id desc limit 1");
        deTestMapper.selectOne(queryWrapper);
    }

    @Test
    void select1() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(deTestMapper.selectBatchIds(list));
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "Zs");
        columnMap.put("age", "19");
        System.out.println(deTestMapper.selectByMap(columnMap));


    }

    @Test
    void select2() {
        QueryWrapper<DeTest> queryWrapper = new QueryWrapper<>();
        Page<DeTest> page = deTestMapper.selectPage(new Page<>(2L, 2L), queryWrapper);
        System.out.println(page);
    }

    @Test
    void list() {
        List<SysPermissionPojo> sysPermissionVos = iSysPermissionService.list(EnumFlag.False.getValue());
        System.out.println(JSONObject.toJSON(sysPermissionVos));
    }

    @Test
    void list1() {
        List<CommTableField> commTableFields = new ArrayList<>();
        CommTableField commTableField1 = new CommTableField();
        commTableField1.setFieldName("name");
        commTableField1.setDataType("varchar");
        commTableField1.setLength(200);
        commTableField1.setIsNull((short) 1);
        commTableField1.setComment("姓名");
        commTableFields.add(commTableField1);
//        iCommTableFieldService.addTable("lib_test", "", commTableFields);
    }

    @Test
    void list2() {
        CommTableField commTableField = new CommTableField();
        commTableField.setFieldName("name");
        commTableField.setDataType("varchar");
        commTableField.setLength(200);
        commTableField.setIsNull((short) 0);
        commTableField.setIsIndex((short) 1);
        commTableField.setComment("姓名");
//        iCommTableFieldService.add("dynamic_index", commTableField);
//        iCommTableFieldService.addIndex("dynamic_index", commTableField.getFieldName());
//        iCommTableFieldService.drop("dynamic_index", commTableField.getFieldName());
    }

    //    @Test
//    void list3() {
//        DataCommonFormDto dataCommonDto = new DataCommonFormDto();
//        dataCommonDto.setId(41);
//        dataCommonDto.setArchivesId(91);
//        Map<String, Object> d = iDataArchivesCollectionService.getById(dataCommonDto);
//        System.out.println(d);
//    }
    @Test
    void list4() {
        System.out.println("11");
    }

    @Test
    void list5() {
        QueryWrapper<DeTest> q = new QueryWrapper<>();
        q.select("email", "id");
        q.eq("email", 11);
        List<String> list = iDeTestService.listObjs(q, a -> {
            System.out.println(a.getClass());
            return (String) a;
        });
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    void list6() {
        System.out.println(JSONObject.toJSON(iCommTableFieldService.findByTableName("de_test")));
    }

    @Test
    void test7() {
        PerBorrowManagementQueryDto perBorrowManagementQueryDto = new PerBorrowManagementQueryDto();
        perBorrowManagementQueryDto.setType((short) 1);
        perBorrowManagementQueryDto.setUserId(3);
        perBorrowManagementQueryDto.setBorrowType(null);
        Page<MakeBorrow> makeBorrows = perBorrowManagementMapper.selectPage(new Page<>(1, 10), perBorrowManagementQueryDto);
        System.out.println(JSONObject.toJSON(makeBorrows));
    }

    @Test
    void test8() {
        executorService.submit(() -> {
            System.out.println("!111111111111111111111111");
            QueryWrapper<DeTest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", "zs");
            List<DeTest> deTests = deTestMapper.selectList(queryWrapper);
            System.out.println(deTests.size());
            if (deTests.size() == 1) {
                deTestMapper.insert(new DeTest());
            }
        });
        executorService.shutdown();

    }
}
