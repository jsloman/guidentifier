package com.guidentifier.util;

import java.util.List;

import com.guidentifier.dao.DAO;
import com.guidentifier.model.Group;
import com.guidentifier.model.Region;
import com.guidentifier.model.Category;

public class WebUtil {
	public static String encodeForWeb(String str) {
		if (str == null) {
			return "";
		}
		return str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
	}
	
	private static void showRegionChildren(StringBuffer sb, Region parent, List<Region>allRegions, Region current, String prefix, String postfix) {
		boolean first = true;
		for (Region r: allRegions) {
			if (r.getParent() != null &&  r.getParent().getName().equals(parent.getName())) {
				if (first) {
					sb.append("<ul>");
					first = false;
				}
				if (current != null && current.getName().equals(r.getName())) {
					sb.append("<li> <b>").append(r.getName()).append("</b>");	
				} else {
					sb.append("<li> <a href=\"/").append(prefix).
						append(r.getName()).append(postfix).append("\">").append(r.getName()).append("</a>");
				}
				showRegionChildren(sb, r, allRegions, current, prefix, postfix);
				sb.append("</li>");
			}
		}
		if (!first) {
			sb.append("</ul>");
		}
	}

	
	public static String showRegions(DAO dao, Region current, String prefix, String postfix) {
		List<Region> allRegions=  dao.getRegionsList();
		StringBuffer sb = new StringBuffer();
		sb.append("<ul>");
		if (current == null) {
			sb.append("<li> <b>All regions</b></li>");
		} else {
			sb.append("<li> <a href=\"/").append(prefix).append("0").append(postfix).append("\">All regions</a></li>");
		}
		for (Region r : allRegions) {
			if (r.getParent() == null) {
				if (current != null && current.getName().equals(r.getName())) {
					sb.append("<li> <b>").append(r.getName()).append("</b>");
				} else {
					sb.append("<li> <a href=\"/").append(prefix).
						append(r.getName()).append(postfix).append("\">").append(r.getName()).append("</a>");
				}
				showRegionChildren(sb, r, allRegions, current, prefix, postfix);
				sb.append("</li>");
			}
		}	
		sb.append("</ul>");
		return sb.toString();
	}
	
	static void showGroupChildren(StringBuffer sb, Group parent, List<Group>allGroups, Region r, String currentName) {
		boolean first = true;
		for (Group group: allGroups) {
			if (group.getParent() != null &&  group.getParent().getName().equals(parent.getName())) {
				if (first) {
					sb.append("<ul>");
					first = false;
				}
				if (group.getName().equals(currentName)) {
					sb.append("<li> <b>").append(group.getName()).append("</b>");		
				} else {
					sb.append("<li> <a href=\"/group/").append(r == null ? "-" : r.getName()).append("/").
						append(group.getName()).append("\">").append(group.getName()).append("</a>");
				}
				showGroupChildren(sb, group, allGroups, r, currentName);
				sb.append("</li>");
			}
		}
		if (!first) {
			sb.append("</ul>");
		}
	}
	
	public static String showGroups(DAO dao, Category category, Region region, String currentName) {
		List<Group> allGroups=  dao.getGroupList(category);
		StringBuffer sb = new StringBuffer();
		sb.append("<ul>");
		for (Group group: allGroups) {
			if (group.getParent() == null) {
				if (group.getName().equals(currentName)) {
					sb.append("<li> <b>").append(group.getName()).append("</b>");		
				} else {
					sb.append("<li> <a href=\"/group/").append(region == null ? "0" : region.getName()).append("/").
						append(group.getName()).append("\">").append(group.getName()).append("</a>");
				}
				showGroupChildren(sb, group, allGroups, region, currentName);
				sb.append("</li>");
			}
		}	
		sb.append("</ul>");
		return sb.toString();
	}
	
}
