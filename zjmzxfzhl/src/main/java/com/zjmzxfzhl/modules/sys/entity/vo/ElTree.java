package com.zjmzxfzhl.modules.sys.entity.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ElTree implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String label;
	private String parentId;
	private Boolean disabled;
	private Boolean isLeaf;
	private Object data;
	private List<ElTree> children;

	public void addChildren(ElTree elTree) {
		if (children == null) {
			children = new ArrayList<ElTree>();
		}
		children.add(elTree);
	}

	public void addChildrens(List<ElTree> elTreeList) {
		if (children == null) {
			children = new ArrayList<ElTree>();
		}
		children.addAll(elTreeList);
	}
}
