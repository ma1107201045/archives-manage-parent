package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.controller.AuthenticationController;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-25
 */
@RestController
@RequestMapping("/system/sysborrowaudit")
public class SysBorrowauditController extends AuthenticationController implements BaseController<SysBorrowaudit, Integer> {


    @Autowired
    ISysBorrowauditService iSysBorrowauditService;

    public Map<String, Object> add(SysBorrowaudit entity) {
        return null;
    }

    public Map<String, Object> remove(Integer id) {
        return null;
    }


    public Map<String, Object> edit(Integer id, SysBorrowaudit entity) {
        return null;
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        return null;
    }

    /**
     * 待审批
     *
     * @param id
     * @return
     */
    @GetMapping("/find")
    public Map<String, Object> findByIdDspAndYsp(@Param("id") Integer id, @Param("num") Integer num, @Param("size") Integer size) {
        QueryWrapper<SysBorrowaudit> qw = new QueryWrapper<>();
        if (id == 0) {
            qw.eq("status", "待审批");
        } else {
            qw.eq("status", "已审批");
        }
        qw.lambda()
                .select(SysBorrowaudit.class, tableFieldInfo -> !"".equals(tableFieldInfo.getColumn()));
        qw.orderByDesc("id");
        Page<SysBorrowaudit> page = iSysBorrowauditService.page(new Page<>(num, size), qw);
        return ResponseDataUtil.ok("成功", page);
    }

    /**
     * 查看
     *
     * @param id
     * @return
     */
    @GetMapping("/chakan")
    public Map<String, Object> chakan(@Param("borrower") String borrower) {

        Map<String, Object> map = iSysBorrowauditService.chakan(borrower);

        return ResponseDataUtil.ok("成功", map);
    }

    /**
     * 点击借阅人
     *
     * @param id
     * @return
     */
    @GetMapping("/djborrower")
    public Map<String, Object> djborrower(@Param("borrower") String borrower) {

        Map<String, Object> map = iSysBorrowauditService.djborrower(borrower);

        return ResponseDataUtil.ok("成功", map);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询全宗号信息列表")
    @GetMapping("/findpage")
    @ApiOperation(value = "查询借阅审批分页列表", notes = " 多条件查询借阅审批分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "borrower", value = "借阅人"),
            @ApiImplicitParam(name = "recordnumber", value = "档案号"),
            @ApiImplicitParam(name = "abstrac", value = "摘要"),
            @ApiImplicitParam(name = "borroweway", value = "借阅方式")})
    public Map<String, Object> findPage(@RequestParam Integer num,
                                        @RequestParam Integer size,
                                        @RequestParam("borrower") String borrower,
                                        @RequestParam("recordnumber") String recordnumber,
                                        @RequestParam("abstrac") String abstrac,
                                        @RequestParam("borroweway") String borroweway)
    {
        QueryWrapper<SysBorrowaudit> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda()
                .select(SysBorrowaudit.class, tableFieldInfo -> !"".equals(tableFieldInfo.getColumn()))
                .like(SysBorrowaudit::getBorrower, borrower == null ? "" : borrower)
                .like(SysBorrowaudit::getRecordnumber, recordnumber == null ? "" : recordnumber)
                .like(SysBorrowaudit::getAbstrac, abstrac == null ? "" : abstrac)
                .like(SysBorrowaudit::getBorroweway, borroweway == null ? "" : borroweway);
        queryWrapper.orderByDesc("id");
        Page<SysBorrowaudit> page = iSysBorrowauditService.page(new Page<>(num, size), queryWrapper);
        return ResponseDataUtil.ok("查询借阅审批列表成功", page);
    }


}
