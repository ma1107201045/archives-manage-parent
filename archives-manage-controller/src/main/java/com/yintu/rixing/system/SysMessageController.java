package com.yintu.rixing.system;


import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

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
public class SysMessageController implements BaseController<SysMessage, Integer> {

    @Autowired
    ISysMessageService sysMessageService;

    @PostMapping("/add")
    public Map<String, Object> add(@Validated SysMessage entity) {
        boolean b = sysMessageService.save(entity);
        if (b) {
            return ResponseDataUtil.ok("添加成功");
        }
        return ResponseDataUtil.ok("添加失败");
    }

    @DeleteMapping("/del")
    public Map<String, Object> remove(@RequestParam Integer id) {
        boolean b = sysMessageService.removeById(id);
        if (b) {
            return ResponseDataUtil.ok("删除成功");
        }
        return ResponseDataUtil.ok("删除失败");
    }

    @PutMapping("/edit")
    public Map<String, Object> edit(@RequestParam Integer id, @Validated SysMessage entity) {
        boolean b = sysMessageService.updateById(entity);
        if (b) {
            return ResponseDataUtil.ok("编辑成功");
        }

        return ResponseDataUtil.ok("编辑失败");
    }

    @GetMapping("/{id}")
    public Map<String, Object> findById(@Param("id") Integer id) {
        SysMessage byId = sysMessageService.getById(id);
        return ResponseDataUtil.ok("查询成功", byId);
    }


}
