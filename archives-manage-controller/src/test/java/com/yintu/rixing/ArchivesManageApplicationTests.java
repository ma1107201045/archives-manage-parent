package com.yintu.rixing;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.demo.DemoTest;
import com.yintu.rixing.demo.DemoTestMapper;
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
    private DemoTestMapper demoTestMapper;

    @Test
    void contextLoads() {
    }


    @Test
    void insert() {
        DemoTest SysTest = new DemoTest();
        // SysTest.setName("zs");
        // SysTest.setAge(10);
        SysTest.setEmail("zs@qq.com");
        demoTestMapper.insert(SysTest);
        System.out.println(SysTest.getId());

    }

    @Test
    void delete() {
        System.out.println(demoTestMapper.deleteById(100L));
        QueryWrapper<DemoTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DemoTest::getName, "z111s")
                .eq(DemoTest::getAge, 100);
        demoTestMapper.delete(queryWrapper);

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
        UpdateWrapper<DemoTest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(DemoTest::getName, "zssss").set(DemoTest::getAge, 20)
                .eq(DemoTest::getId, 10);
        demoTestMapper.update(new DemoTest(), updateWrapper);


    }

    @Test
    void select() {
//        System.out.println(SysTestMapper.selectById(1L));
        QueryWrapper<DemoTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(DemoTest.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("name") || tableFieldInfo.getColumn().equals("age")).eq(DemoTest::getName, "zs")
                .eq(DemoTest::getAge, 10)
                .last("order by id desc limit 1");
        demoTestMapper.selectOne(queryWrapper);
    }

    @Test
    void select1() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(demoTestMapper.selectBatchIds(list));
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "Zs");
        columnMap.put("age", "19");
        System.out.println(demoTestMapper.selectByMap(columnMap));


    }

    @Test
    void select2() {
        QueryWrapper<DemoTest> queryWrapper = new QueryWrapper<>();
        Page<DemoTest> page = demoTestMapper.selectPage(new Page<>(2L, 2L), queryWrapper);
        System.out.println(page);
    }
}
