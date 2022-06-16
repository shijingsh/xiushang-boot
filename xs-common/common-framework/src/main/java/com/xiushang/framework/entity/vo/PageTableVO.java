package com.xiushang.framework.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单形式的分页组件
 */
public class PageTableVO<T> extends MgPageable {

	private static final long serialVersionUID = -2332973656118325329L;

	/**
	 * 数据集合
	 */
	@ApiModelProperty(notes = "数据集合")
	private List<T> rowData;
	/**
	 * 扩展信息
	 */
	@ApiModelProperty(notes = "扩展信息")
	private Object extendData;

	public PageTableVO() {
	}
	public PageTableVO(Page page,PageTableVO vo) {
		if(page!=null){
			setRowData(page.getContent());
			setTotalCount(page.getTotalElements());
		}
		if(vo!=null){
			setPageNo(vo.getPageNo());
			setPageSize(vo.getPageSize());
		}
	}
	public PageTableVO(Page page,MgPageable vo) {
		if(page!=null){
			setRowData(page.getContent());
			setTotalCount(page.getTotalElements());
		}
		if(vo!=null){
			setPageNo(vo.getPageNo());
			setPageSize(vo.getPageSize());
		}
	}

	public PageTableVO(Page page,BaseSearchPageVo vo) {
		if(page!=null){
			setRowData(page.getContent());
			setTotalCount(page.getTotalElements());
		}
		if(vo!=null){
			setPageNo(vo.getPageNo());
			setPageSize(vo.getPageSize());
		}
	}


	public List<T> getRowData() {
		if(rowData==null){
			rowData = new ArrayList<>();
		}
		return rowData;
	}

	public void setRowData(List<T> rowData) {
		this.rowData = rowData;
	}

	public Object getExtendData() {
		return extendData;
	}

	public void setExtendData(Object extendData) {
		this.extendData = extendData;
	}

}
