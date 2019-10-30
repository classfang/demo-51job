package org.java1928.entity;

import java.util.List;

import lombok.Data;

/**
 * 分页实体类
 * @author junki
 * @date 2019年10月18日
 */
@Data
public class PageBean<E> {
	
	// 当前页码
	private Long currentPage;
	// 每页显示数据量
	private Long pageSize;
	// 数据库中总数据量
	private Long totalRows;
	
	// 总页数
	private Long totalPages;
	// 查询数据库开始的索引
	private Long startIndex;
	// 页面显示开始页码
	private Long startPage;
	// 页面显示结束页面
	private Long endPage;
	
	// 数据封装集合
	private List<E> dataList;

	public PageBean(Long currentPage, Long pageSize, Long totalRows) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalRows = totalRows;
		
		if (totalRows % pageSize == 0) {
			this.totalPages = totalRows / pageSize;
		} else {
			this.totalPages = totalRows / pageSize + 1;
		}
		this.startIndex = (currentPage - 1) * pageSize;
		
		if (currentPage <= 3) {
			this.startPage = 1L;
			if (totalPages <= 7) {
				this.endPage = totalPages;
			} else {
				this.endPage = 7L;
			}
		} else {
			this.startPage = currentPage - 3;
			if (totalPages <= currentPage + 3) {
				this.endPage = totalPages;
			} else {
				this.endPage = currentPage + 3;
			}
		}

	}
	
}
