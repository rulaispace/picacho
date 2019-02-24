package com.abaya.picacho.matrix.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class NestedListNode {
  private Long id;
  private int level;
  private String type;
  private String primaryText;
  private String secondaryText;

  @JsonIgnore
  private NestedListNode parent;

  private List<NestedListNode> children;


  public NestedListNode() {
    children = new ArrayList<>();
  }

  public void addChild(NestedListNode child) {
    children.add(child);
  }

  @Override
  public String toString() {
    return "NestedListNode{" +
      "id=" + id +
      ", level='" + level + '\'' +
      ", type='" + type + '\'' +
      ", primaryText='" + primaryText + '\'' +
      ", secondaryText='" + secondaryText + '\'' +
      '}';
  }
}
