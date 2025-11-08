package com.maternal.health.result;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author MaLiang
 * @version 1.0
 * @date 2025-06-05
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 列表数据
     */
    private List<T> list;

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public PageResult(List<T> list, long total) {
        if (ObjectUtil.isNull(list)) {
            this.list = Lists.newArrayList();
        } else {
            this.list = list;
        }
        this.total = total;
    }

    public static <T> PageResult<T> getPageData(List<T> list, long total) {
        return new PageResult(list, total);
    }

    public static <T, R> PageResult<T> getPageData(Page<R> page, Class<T> cls) {
        List<T> ts = BeanUtil.copyToList(page.getRecords(), cls);
        return getPageData(ts, page.getTotal());
    }
}
