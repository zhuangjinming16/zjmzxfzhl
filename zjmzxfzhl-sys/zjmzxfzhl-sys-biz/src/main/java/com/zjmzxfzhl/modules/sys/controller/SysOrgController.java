package com.zjmzxfzhl.modules.sys.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.BaseController;
import com.zjmzxfzhl.common.log.annotation.Log;
import com.zjmzxfzhl.modules.sys.entity.SysOrg;
import com.zjmzxfzhl.modules.sys.entity.vo.ElTree;
import com.zjmzxfzhl.modules.sys.service.SysOrgService;

/**
 * 机构Controller
 *
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/org")
public class SysOrgController extends BaseController {
    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 自定义查询列表
     *
     * @param sysOrg
     * @param current
     * @param size
     * @return
     */
    @PreAuthorize("@elp.single('sys:org:list')")
    @GetMapping(value = "/list")
    public Result list(SysOrg sysOrg, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<SysOrg> pageList = sysOrgService.list(new Page<SysOrg>(current, size), sysOrg);
        return Result.ok(pageList);
    }

    @PreAuthorize("@elp.single('sys:org:list')")
    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) {
        SysOrg sysOrg = sysOrgService.getById(id);
        return Result.ok(sysOrg);
    }

    /**
     * @param sysOrg
     * @return
     * @功能：新增
     */
    @Log(value = "新增机构")
    @PreAuthorize("@elp.single('sys:org:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysOrg sysOrg) {
        sysOrgService.saveSysOrg(sysOrg);
        return Result.ok(sysOrg);
    }

    /**
     * @param sysOrg
     * @return
     * @功能：修改
     */
    @Log(value = "修改机构")
    @PreAuthorize("@elp.single('sys:org:update')")
    @PutMapping(value = "/update")
    public Result update(@Valid @RequestBody SysOrg sysOrg) {
        sysOrgService.updateSysOrg(sysOrg);
        return Result.ok(sysOrg);
    }

    /**
     * @param ids
     * @return
     * @功能：批量删除
     */
    @Log(value = "删除机构")
    @PreAuthorize("@elp.single('sys:org:delete')")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String ids) {
        if (ids == null || ids.trim().length() == 0) {
            return Result.error("ids can't be empty");
        }
        String[] idsArr = ids.split(",");
        if (idsArr.length > 1) {
            sysOrgService.removeByIds(Arrays.asList(idsArr));
        } else {
            sysOrgService.removeById(idsArr[0]);
        }
        return Result.ok();
    }

    /**
     * 机构管理，机构树数据
     *
     * @return
     */
    @PreAuthorize("@elp.single('sys:org:getTreeData')")
    @GetMapping(value = "/getTreeData")
    public Result getTreeData() {
        List<ElTree> treeData = sysOrgService.getTreeData();
        return Result.ok(treeData);
    }

    /**
     * 公共机构选择树
     *
     * @param type 不同机构选择树策略(预留字段，可根据不同应用场景新增type，由前端输入)
     * @return
     */
    @GetMapping(value = "/getSelectTreeData")
    public Result getSelectTreeData(String type) {
        // 默认返回当前用户数据权限范围的机构树
        List<ElTree> treeData = sysOrgService.getTreeData();
        return Result.ok(treeData);
    }
}
