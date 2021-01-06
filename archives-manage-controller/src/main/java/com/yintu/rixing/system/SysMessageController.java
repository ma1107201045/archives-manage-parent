package com.yintu.rixing.system;


import com.yintu.rixing.config.controller.Authenticator;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-24
 */
@RestController
@RequestMapping("/system/sysmessage")
@Api(tags = "引入模板后的信息接口")
@ApiIgnore
public class SysMessageController extends Authenticator {

    @Autowired
    ISysMessageService sysMessageService;

    @PostMapping("/add")
    public ResultDataUtil<Object> add(@Validated SysMessage entity) {
        boolean b = sysMessageService.save(entity);
        if (b) {
            return ResultDataUtil.ok("添加成功");
        }
        return ResultDataUtil.ok("添加失败");
    }

    @DeleteMapping("/del")
    public ResultDataUtil<Object> remove(@RequestParam Set<Integer> ids) {
        boolean b = sysMessageService.removeByIds(ids);
        if (b) {
            return ResultDataUtil.ok("删除成功");
        }
        return ResultDataUtil.ok("删除失败");
    }

    @PutMapping("/edit")
    public ResultDataUtil<Object> edit(@RequestParam Integer id, @Validated SysMessage entity) {
        boolean b = sysMessageService.updateById(entity);
        if (b) {
            return ResultDataUtil.ok("编辑成功");
        }

        return ResultDataUtil.ok("编辑失败");
    }

    @GetMapping("/{id}")
    public ResultDataUtil<SysMessage> findById(@Param("id") Integer id) {
        SysMessage byId = sysMessageService.getById(id);
        return ResultDataUtil.ok("查询成功", byId);
    }


}
