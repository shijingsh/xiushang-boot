package com.mg.common.shiro.util;

import org.apache.shiro.authz.Permission;

public class AdminPermission implements Permission{

	@Override
	public boolean implies(Permission p) {
		return true;
	}
	

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if(obj instanceof AdminPermission){
			return true;
		}
		return false;
	}
	
	
}
