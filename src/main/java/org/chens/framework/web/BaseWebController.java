package org.chens.framework.web;

import org.chens.core.base.BaseBackgroundFacade;
import org.chens.core.exception.BaseExceptionEnum;
import org.chens.core.util.StringUtils;
import org.chens.core.vo.PageResult;
import org.chens.core.vo.QueryPageEntity;
import org.chens.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 通用增删改查-抽象方法
 *
 * @author songchunlei@qq.com
 * @since 2018/3/12
 */
public abstract class BaseWebController<S extends BaseBackgroundFacade<T>, T> extends BaseController {
    @Autowired
    protected S service;

    /**
     * 根据分页对象查询
     * 
     * @param spage
     * @return
     */
    @PostMapping("/pagelist")
    @ResponseBody
    public Result<PageResult<T>> pagelist(@RequestBody QueryPageEntity<T> spage) {
        return service.selectPage(spage);
    }

    /**
     * 新增
     * 
     * @param t
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Result<Boolean> create(@RequestBody T t) {
        if (t != null) {
            return service.insert(t);
        } else {
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 更新
     * 
     * @param t
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public Result<Boolean> update(@RequestBody T t) {
        if (t != null) {
            return service.updateById(t);
        } else {
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 保存
     *
     * @param t
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<Boolean> save(@RequestBody T t) {
        if (t != null) {
            return service.insertOrUpdate(t);
        } else {
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 根据id获取实体对象
     * 
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @ResponseBody
    public Result<T> getInfo(@PathVariable String id) {
        if (id != null) {
            return service.selectById(id);
        } else {
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 列表查询
     * 
     * @param t
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Result<List<T>> list(T t) {
        if (t != null) {
            return service.selectList(t);
        } else {
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Result<Boolean> delete(@PathVariable String id) {
        if (StringUtils.isNotEmpty(id)) {
            return service.deleteById(id);
        } else {
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 批量删除
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/batchDelete/{id}")
    @ResponseBody
    public Result<Boolean> batchDelete(@PathVariable String id) {
        if (StringUtils.isNotEmpty(id)) {
            String[] idArray = id.split(",");
            List<String> idList = Arrays.asList(idArray);
            return service.deleteBatchIds(idList);
        } else {
            return Result.getError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

}
