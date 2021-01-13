package com.yintu.rixing.notification;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 通知公告表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2020-12-30
 */
@RestController
@RequestMapping("/notification/notificationAll")
@Api("通知公告")
public class NotNoticemessageController  {
    @Autowired
    private INotNoticemessageService iNotNoticemessageService;

    @PostMapping("/add")
    @Log(level = EnumLogLevel.INFO, module = "通知公告", context = "添加新的通知公告信息")
    @ApiOperation(value = "添加新的通知公告信息", notes = "添加新的通知公告信息")
    public Map<String, Object> add(NotNoticemessage entity) {
       if (iNotNoticemessageService.save(entity)){
           return ResponseDataUtil.ok("添加通知公告信息成功");
       }else {
           return ResponseDataUtil.error("添加通知公告信息失败");
       }
    }

    @Log(level = EnumLogLevel.INFO, module = "通知公告", context = "根据id删除通知公告信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除通知公告",  notes = "删除通知公告")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true ,paramType="path")
    public Map<String, Object> remove(@PathVariable  Set<Integer> ids) {
       if (iNotNoticemessageService.removeByIds(ids)){
           return ResponseDataUtil.ok("删除通知公告信息成功");
       }
        return ResponseDataUtil.error("删除通知公告信息失败");
    }

    @Log(level = EnumLogLevel.INFO, module = "通知公告", context = "根据id修改通知公告信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改通知公告信息", notes = "修改通知公告信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> edit(Integer id, NotNoticemessage entity) {
        iNotNoticemessageService.updateById(entity);
        return ResponseDataUtil.ok("修改通知公告信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "通知公告", context = "根据id查询单条通知公告信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询通知公告信息",notes = "查询通知公告信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> findById(@PathVariable Integer id) {
        NotNoticemessage noticemessage = iNotNoticemessageService.getById(id);
        return ResponseDataUtil.ok("查询单条通知公告信息成功",noticemessage);
    }


    @Log(level = EnumLogLevel.DEBUG, module = "通知公告", context = "查询通知公告信息列表")
    @GetMapping
    @ApiOperation(value = "查询通知公告信息列表", notes = " 多条件查询通知公告信息分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "title", value = "标题"),
            @ApiImplicitParam(name = "userName", value = "发布人")
    })
    public Map<String, Object> findPage(@RequestParam Integer num, @RequestParam Integer size, String title,String userName) {
        QueryWrapper<NotNoticemessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(NotNoticemessage::getTitle,title==null? "":title);
        queryWrapper.lambda().like(NotNoticemessage::getCreateBy,userName==null? "":userName);
        queryWrapper.orderByDesc("id");
        Page<NotNoticemessage> page = iNotNoticemessageService.page(new Page<>(num, size), queryWrapper);
        return ResponseDataUtil.ok("查询通知公告信息列表", page);
    }


}
