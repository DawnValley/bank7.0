package com.cx.bank.util;

import java.util.List;

/**
 * 分页组件
 * @author RG
 * @version 1.0 2018/09/02
 */
public class PageModel {
	
	
	private int totalRecords;//总记录数
	

	private List list;	//结果集
	
	
	private int pageNo;//当前页
	
    
	private int pageSize; //每页显示多少条
	/**
	 * 取得总记录
	 * @return
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	
	/**
	 * 取得总页数
	 * @return
	 */
	public int getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}
	
	/**
	 * 设置总记录数
	 * @param totalRecords
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	/**
	 * 取得第一页
	 * @return
	 */
	public int getTopPageNo() {
		return 1;
	}
	
	/**
	 * 取得上一页
	 * @return
	 */
	public int getPreviousPageNo() {
		if (pageNo <= 1) {
			return 1;
		}
		return pageNo -1;
	}
	
	/**
	 * 取得下一页
	 * @return
	 */
	public int getNextPageNo() {
		//判断是否当前页数大于总页数
		if (pageNo >= getTotalPages()) {
			//当总页数为0时返回第一页，否则返回总页数为下一页
			return getTotalPages()==0?1:getTotalPages();
		}
		return pageNo + 1;
	}
	
	/**
	 * 取得最后一页
	 * @return
	 */
	public int getBottomPageNo() {
		//当总页数为0时返回第一页，否则返回总页数为最后一页
		return getTotalPages() == 0?1:getTotalPages();
	}
}
