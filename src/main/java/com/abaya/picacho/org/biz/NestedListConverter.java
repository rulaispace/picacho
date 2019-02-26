package com.abaya.picacho.org.biz;

import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.matrix.model.NestedListNode;
import com.abaya.picacho.org.util.NestedListNodeHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NestedListConverter {
  private List<Organization> organizations = null;

  private Map<String, Integer> positions = null;
  private int[] chain = null;
  private NestedListNode[] nodes = null;
  private NestedListNode root = null;

  public NestedListConverter(List<Organization> organizations) {
    this.organizations = organizations;

    doWork();
  }

  private void doWork() {
    index();
    createChain();
    createNodes();
    findRoot();
    sort();
  }

  private void index() {
    if (this.positions != null) return ;

    this.positions = new HashMap<>();
    for(int i=0; i<organizations.size(); i++) {
      Organization organization = organizations.get(i);
      positions.put(organization.getOrgId(), i);
    }
  }

  private void createChain() {
    if (this.chain != null) return ;

    this.chain = new int[organizations.size()];
    for (int i=0; i<chain.length; i++) chain[i] = -1;

    for(int i=0; i<organizations.size(); i++) {
      Organization organization = organizations.get(i);
      String parentOrgId = organization.getParentOrgId();
      if (positions.get(parentOrgId) == null) continue;

      chain[i] = positions.get(parentOrgId);
    }
  }

  private void createNodes() {
    if (this.nodes != null) return ;

    initNodes();
    setProperties();
    setParentReference();
    setChildrenReference();
  }

  private void initNodes() {
    this.nodes = new NestedListNode[organizations.size()];
    for (int i=0; i<nodes.length; i++) {
      nodes[i] = new NestedListNode();
    }
  }

  private void setProperties() {
    for (int i=0; i<nodes.length; i++) {
      nodes[i] = NestedListNodeHelper.setNormalProperty(nodes[i], organizations.get(i));
    }
  }

  private void setParentReference() {
    for (int i=0; i<nodes.length; i++) {
      if (chain[i] == -1) continue;
      nodes[i].setParent(nodes[chain[i]]);
    }
  }

  private void setChildrenReference() {
    for (int i=0; i<nodes.length; i++) {
      NestedListNode parent = nodes[i].getParent();
      if (parent == null) continue;
      parent.addChild(nodes[i]);
    }
  }

  private void findRoot() {
    if (this.root != null) return ;

    for (NestedListNode nestedListNode : nodes) {
      if (nestedListNode.getParent() == null) {
        this.root = nestedListNode;
        return ;
      }
    }
  }

  private void sort() {
    sort(root.getChildren());
  }

  private void sort(List<NestedListNode> nodes) {
    if (nodes == null) return ;

    Collections.sort(nodes);
    for (NestedListNode node : nodes) {
      sort(node.getChildren());
    }
  }

  public NestedListNode get() {
    return this.root;
  }
}
