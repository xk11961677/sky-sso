package com.zyk.sso.auth.common.entity;

import java.io.Serializable;

/**
 * 分页查询 (此类中属性全部 transient , 防止响应json时序列化 , 如果是RPC服务直接调用需要将 transient 去掉 )
 */
@SuppressWarnings("serial")
public class BasePageDTO implements Serializable {

    private transient Integer begin;

    private transient Integer end;

    /**
     * 分页使用的参数，分页大小
     */
    private transient Integer pageSize;

    /**
     * 分页使用的参数，当前分页号
     */
    private transient Integer pageNum;

    /**
     * 是否分页
     */
    private transient boolean page = true;

    /**
     * 查询记录开始行号
     */
    private transient Integer startIdx;

    /**
     * 查询记录结束行号
     */
    private transient Integer endIdx;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null ? Pagination.DEFAULT_PAGE_SIZE : pageSize;
        if (this.pageSize != null) {
            if (this.pageNum == null) {
                this.pageNum = 1;
                this.startIdx = 0;
            } else {
                markPagination();
            }
        }
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum == null ? 1 : pageNum;
        if (this.pageNum != null && this.pageSize != null) {
            markPagination();
        }
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public Integer getStartIdx() {
        return startIdx;
    }

    public void setStartIdx(Integer startIdx) {
        this.startIdx = startIdx;
    }

    public Integer getEndIdx() {
        return endIdx;
    }

    public void setEndIdx(Integer endIdx) {
        this.endIdx = endIdx;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public void markPagination() {
        this.startIdx = (this.pageNum - 1) * this.pageSize;
    }
}
