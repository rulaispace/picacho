package com.abaya.picacho.biz.organization.service.impl;

import com.abaya.picacho.common.model.TreeNode;
import com.abaya.picacho.biz.organization.entity.Organization;
import com.abaya.picacho.biz.organization.model.OrgNode;
import com.abaya.picacho.biz.organization.model.OrgType;
import com.abaya.picacho.biz.organization.service.OrganizationConvertService;
import com.abaya.picacho.biz.organization.util.OrgNodeHelper;
import com.abaya.picacho.biz.account.entity.Account;
import com.abaya.picacho.biz.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrgNodeConvertServiceImpl implements OrganizationConvertService {
    @Autowired
    private AccountService accountService = null;

    public OrgNode convert(List<Organization> organizationList) {
        Map<String, Integer> positions = index(organizationList);
        List<OrgNode> nodeList = createOrgNodes(organizationList);

        int[] chain = createChain(organizationList, positions);

        setParentReference(nodeList, chain);
        setChildrenReference(nodeList);

        OrgNode root = findRoot(nodeList);
        sort(root);

        return root;
    }

    private Map<String, Integer> index(List<Organization> organizations) {
        Map<String, Integer> positions = new HashMap<>();
        for (int i = 0; i < organizations.size(); i++) {
            Organization organization = organizations.get(i);
            positions.put(organization.getCode(), i);
        }
        return positions;
    }

    private int[] createChain(List<Organization> organizations, Map<String, Integer> positions) {
        int[] chain = new int[organizations.size()];
        for (int i = 0; i < chain.length; i++) chain[i] = -1;

        for (int i = 0; i < organizations.size(); i++) {
            Organization organization = organizations.get(i);
            String parentCode = organization.getParentCode();
            if (positions.get(parentCode) == null) continue;

            chain[i] = positions.get(parentCode);
        }

        return chain;
    }

    private List<OrgNode> createOrgNodes(List<Organization> organizations) {
        List<OrgNode> result = new ArrayList<>();
        organizations.forEach(organization -> {
            OrgNode node = new OrgNode();
            OrgNodeHelper.setNormalProperty(node, organization);
            result.add(node);

            if (organization.getType() != OrgType.employee) return ;

            Account account = accountService.queryAccountByUsername(organization.getCode());
            if (account == null) return ;
            node.setState(account.getState());
        });


        return result;
    }

    private void setParentReference(List<OrgNode> nodeList, int[] chain) {
        for (int i = 0; i < nodeList.size(); i++) {
            if (chain[i] == -1) continue;
            nodeList.get(i).setParent(nodeList.get(chain[i]));
        }
    }

    private void setChildrenReference(List<OrgNode> nodeList) {
        for (int i = 0; i < nodeList.size(); i++) {
            TreeNode parent = nodeList.get(i).getParent();
            if (parent == null) continue;
            parent.addChild(nodeList.get(i));
        }
    }

    private OrgNode findRoot(List<OrgNode> nodeList) {
        for (OrgNode node : nodeList) {
            if (node.getParent() == null) {
                return node;
            }
        }
        return null;
    }

    private void sort(OrgNode root) {
        sort(root.getChildren());
    }

    private void sort(List<TreeNode> nodeList) {
        if (nodeList == null) return;

        Collections.sort(nodeList);
        for (TreeNode node : nodeList) {
            sort(node.getChildren());
        }
    }
}