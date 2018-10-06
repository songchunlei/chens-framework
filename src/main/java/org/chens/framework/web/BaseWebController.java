package org.chens.framework.web;

import org.chens.core.base.BaseBackgroundFacade;
import org.chens.core.exception.BaseExceptionEnum;
import org.chens.core.util.StringUtils;
import org.chens.core.vo.QueryPageEntity;
import org.chens.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Result> pagelist(@RequestBody QueryPageEntity<T> spage) {
        return doResponse(service.selectPage(spage));
    }

    /**
     * 新增
     * 
     * @param t
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Result> create(@RequestBody T t) {
        if (t != null) {
            return doResponse(service.insert(t));
        } else {
           return doError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 更新
     * 
     * @param t
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Result> update(@RequestBody T t) {
        if (t != null) {
            return doResponse(service.updateById(t));
        } else {
            return doError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 保存
     *
     * @param t
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Result> save(@RequestBody T t) {
        if (t != null) {
            return doResponse(service.insertOrUpdate(t));
        } else {
            return doError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 根据id获取实体对象
     * 
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<Result> getInfo(@PathVariable String id) {
        if (id != null) {
            return doResponse(service.selectById(id));
        } else {
            return doError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 列表查询
     * 
     * @param t
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Result> list(T t) {
        if (t != null) {
            return doResponse(service.selectList(t));
        } else {
            return doError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Result> delete(@PathVariable String id) {
        if (StringUtils.isNotEmpty(id)) {
            return doResponse(service.deleteById(id));
        } else {
            return doError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

    /**
     * 批量删除
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/batchDelete/{id}")
    public ResponseEntity<Result> batchDelete(@PathVariable String id) {
        if (StringUtils.isNotEmpty(id)) {
            String[] idArray = id.split(",");
            List<String> idList = Arrays.asList(idArray);
            return doResponse(service.deleteBatchIds(idList));
        } else {
            return doError(BaseExceptionEnum.REQUEST_NULL);
        }
    }

}
