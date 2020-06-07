package com.zjmzxfzhl.modules.demo.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.base.BaseController;
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;
import com.zjmzxfzhl.modules.demo.service.DemoZjmzxfzhlService;

/**
 * 开发示例Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/demo/zjmzxfzhl")
public class DemoZjmzxfzhlController extends BaseController {
    @Autowired
    private DemoZjmzxfzhlService demoZjmzxfzhlService;

    /**
     * 自定义查询列表
     * 
     * @param demoZjmzxfzhl
     * @param current
     * @param size
     * @return
     */
    // // @RequiresPermissions("demo:zjmzxfzhl:list")
    @GetMapping(value = "/list")
    public Result list(DemoZjmzxfzhl demoZjmzxfzhl, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.list(new Page<DemoZjmzxfzhl>(current, size),
                demoZjmzxfzhl);
        // IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission1(new Page<DemoZjmzxfzhl>(current,
        // size), demoZjmzxfzhl);
        // IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission2(new Page<DemoZjmzxfzhl>(current,
        // size), demoZjmzxfzhl);
        // IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission3(new Page<DemoZjmzxfzhl>(current,
        // size), demoZjmzxfzhl);

        // DemoZjmzxfzhl demoZjmzxfzhl2 = new DemoZjmzxfzhl();
        // BeanUtils.copyProperties(demoZjmzxfzhl, demoZjmzxfzhl2);
        // IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission4(new Page<DemoZjmzxfzhl>(current,
        // size), demoZjmzxfzhl,
        // demoZjmzxfzhl2);

        // IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission5(new Page<DemoZjmzxfzhl>(current,
        // size), demoZjmzxfzhl);

        return Result.ok(pageList);
    }

    // // @RequiresPermissions("demo:zjmzxfzhl:list")
    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) {
        DemoZjmzxfzhl demoZjmzxfzhl = demoZjmzxfzhlService.getById(id);
        return Result.ok(demoZjmzxfzhl);
    }

    /**
     * @功能：新增
     * @param demoZjmzxfzhl
     * @return
     */
    // // @RequiresPermissions("demo:zjmzxfzhl:save")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody DemoZjmzxfzhl demoZjmzxfzhl) {
        demoZjmzxfzhlService.save(demoZjmzxfzhl);
        return Result.ok();
    }

    /**
     * @功能：修改
     * @param demoZjmzxfzhl
     * @return
     */
    // // @RequiresPermissions("demo:zjmzxfzhl:update")
    @PutMapping(value = "/update")
    public Result update(@Valid @RequestBody DemoZjmzxfzhl demoZjmzxfzhl) {
        demoZjmzxfzhlService.updateById(demoZjmzxfzhl);
        return Result.ok();
    }

    /**
     * @功能：批量删除
     * @param ids
     * @return
     */
    // // @RequiresPermissions("demo:zjmzxfzhl:delete")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String ids) {
        if (ids == null || ids.trim().length() == 0) {
            return Result.error("ids can't be empty");
        }
        String[] idsArr = ids.split(",");
        if (idsArr.length > 1) {
            demoZjmzxfzhlService.removeByIds(Arrays.asList(idsArr));
        } else {
            demoZjmzxfzhlService.removeById(idsArr[0]);
        }
        return Result.ok();
    }
}
