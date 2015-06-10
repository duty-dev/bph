package com.tianyi.bph.domain.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrganGbOrgan {

	private Integer organId;

	private Integer parentId;

	private Set<Integer> gbOrganIds;

	private List<OrganGbOrgan> items;

	public Integer getOrganId() {
		return organId;
	}

	public void setOrganId(Integer organId) {
		this.organId = organId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Set<Integer> getGbOrganIds() {
		return gbOrganIds;
	}

	public void setGbOrganIds(Set<Integer> gbOrganIds) {
		this.gbOrganIds = gbOrganIds;
	}

	public List<OrganGbOrgan> getItems() {
		return items;
	}

	public void setItems(List<OrganGbOrgan> items) {
		this.items = items;
	}

	public void addChild(OrganGbOrgan node) {
		if (this.items == null) {
			this.items = new ArrayList<OrganGbOrgan>();
		}
		this.items.add(node);
	}

}
