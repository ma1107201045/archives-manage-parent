package com.yintu.rixing.util;

import java.util.List;
import java.util.Map;

/**
 * @author:mlf
 * @date:2020/5/22 16:37
 */
public class TreeNodeUtil {

    private Long id;
    private String label;
    private String value;
    private String icon;
    private Map<String, Boolean> state;
    private List<TreeNodeUtil> children;
    private Map<String, Object> li_attr;
    private Map<String, Object> a_attr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Map<String, Boolean> getState() {
        return state;
    }

    public void setState(Map<String, Boolean> state) {
        this.state = state;
    }

    public List<TreeNodeUtil> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeUtil> children) {
        this.children = children;
    }

    public Map<String, Object> getLi_attr() {
        return li_attr;
    }

    public void setLi_attr(Map<String, Object> li_attr) {
        this.li_attr = li_attr;
    }

    public Map<String, Object> getA_attr() {
        return a_attr;
    }

    public void setA_attr(Map<String, Object> a_attr) {
        this.a_attr = a_attr;
    }

    @Override
    public String toString() {
        return "TreeNodeUtil{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", icon='" + icon + '\'' +
                ", state=" + state +
                ", children=" + children +
                ", li_attr=" + li_attr +
                ", a_attr=" + a_attr +
                '}';
    }
}
