package com.yintu.rixing;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.demo.SysTest;
import com.yintu.rixing.demo.SysTestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ArchivesManageApplicationTests {
    @Autowired
    private SysTestMapper sysTestMapper;

    @Test
    void contextLoads() {
    }


    @Test
    void insert() {
        SysTest SysTest = new SysTest();
        // SysTest.setName("zs");
        // SysTest.setAge(10);
        SysTest.setEmail("zs@qq.com");
        sysTestMapper.insert(SysTest);
        System.out.println(SysTest.getId());

    }

    @Test
    void delete() {
        System.out.println(sysTestMapper.deleteById(100L));
        QueryWrapper<SysTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysTest::getName, "z111s")
                .eq(SysTest::getAge, 100);
        sysTestMapper.delete(queryWrapper);

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
        UpdateWrapper<SysTest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SysTest::getName, "zssss").set(SysTest::getAge, 20)
                .eq(SysTest::getId, 10);
        sysTestMapper.update(new SysTest(), updateWrapper);


    }

    @Test
    void select() {
//        System.out.println(SysTestMapper.selectById(1L));
        QueryWrapper<SysTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysTest.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("name") || tableFieldInfo.getColumn().equals("age")).eq(SysTest::getName, "zs")
                .eq(SysTest::getAge, 10)
                .last("order by id desc limit 1");
        sysTestMapper.selectOne(queryWrapper);
    }

    @Test
    void select1() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(sysTestMapper.selectBatchIds(list));
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "Zs");
        columnMap.put("age", "19");
        System.out.println(sysTestMapper.selectByMap(columnMap));


    }

    @Test
    void select2() {
        QueryWrapper<SysTest> queryWrapper = new QueryWrapper<>();
        Page<SysTest> page = sysTestMapper.selectPage(new Page<>(2L, 2L), queryWrapper);
        System.out.println(page);
    }
}
