package com.mg.common.shiro.util;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.HashSet;
import java.util.Set;

public class StringPermission extends WildcardPermission {

	private int partSize;

	private String wildcardString;

	public StringPermission(String wildcardString) {
		super(wildcardString);
		this.wildcardString = wildcardString;
		if (this.getParts() == null) {
			this.partSize = 0;
		} else {
			this.partSize = this.getParts().size();
		}
	}

	public int getPartSize() {
		return partSize;
	}

	public Set<String> getPart(int index) {
		Set<String> result = null;
		if (getPartSize() >= index) {
			result = this.getParts().get(index - 1);
		}
		if (result == null) {
			result = new HashSet<>();
		}
		return result;
	}

	public String getWildcardString() {
		return wildcardString;
	}

	public void setWildcardString(String wildcardString) {
		this.wildcardString = wildcardString;
	}

	@Override
	public boolean implies(Permission p) {
		if (p instanceof WildcardPermission) {
			return super.implies(p);
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + partSize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringPermission other = (StringPermission) obj;
		if (partSize != other.partSize)
			return false;
		return true;
	}

}
