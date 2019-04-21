package com.abaya.picacho.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TreeNode implements Comparable<TreeNode> {
    private Long id;
    private int level;
    private String type;
    private String primaryText;
    private String secondaryText;

    @JsonIgnore
    private TreeNode parent;

    private List<TreeNode> children;


    public TreeNode() {
        children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", level='" + level + '\'' +
                ", type='" + type + '\'' +
                ", primaryText='" + primaryText + '\'' +
                ", secondaryText='" + secondaryText + '\'' +
                '}';
    }

    @Override
    public int compareTo(TreeNode another) {
        if (this.type.equals(another.type)) {
            return this.id.compareTo(another.id);
        }

        if ("department".equals(this.type)) {
            return -1;
        }

        return 1;
    }
}
