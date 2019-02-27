package com.abaya.picacho.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Node implements Comparable<Node> {
  private Long id;
  private int level;
  private String type;
  private String primaryText;
  private String secondaryText;

  @JsonIgnore
  private Node parent;

  private List<Node> children;


  public Node() {
    children = new ArrayList<>();
  }

  public void addChild(Node child) {
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
  public int compareTo(Node another) {
    if (this.type.equals(another.type)) {
      return (int)(this.id - another.id);
    }

    if ("department".equals(this.type)) {
      return -1;
    }

    return 1;
  }
}
