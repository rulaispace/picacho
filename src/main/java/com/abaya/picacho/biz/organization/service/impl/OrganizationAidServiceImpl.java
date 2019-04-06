package com.abaya.picacho.biz.organization.service.impl;

import com.abaya.picacho.biz.organization.entity.Organization;
import com.abaya.picacho.biz.organization.model.OrgType;
import com.abaya.picacho.biz.organization.repository.OrganizationRepository;
import com.abaya.picacho.biz.organization.service.OrganizationAidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationAidServiceImpl implements OrganizationAidService  {
    @Autowired
    private OrganizationRepository repository = null;

    @Override
    public String generateAncestorFullName(String code) {
        return generateAncestorFullName(code, 1);
    }

    @Override
    public String generateOrganizationFullName(String code) {
        return generateAncestorFullName(code, 0);
    }

    private String generateAncestorFullName(String code, int level) {
        Assert.notNull(code, "组织机构编码不能为空");

        List<String> organizationNames = new ArrayList<String>();
        Organization org = repository.findByCodeIgnoreCase(code);
        while (org != null) {
            organizationNames.add(org.getName());
            org = repository.findByCodeIgnoreCase(org.getParentCode());
        }

        String fullName = "";
        for (int i=organizationNames.size()-1; i>=level; i--) {
            fullName += organizationNames.get(i) + "/";
        }

        return fullName.substring(0, fullName.length()-1);
    }

    @Override
    public boolean hasChildren(Organization organization) {
        if (organization == null) return false;
        List<Organization> children = repository.findByParentCode(organization.getCode());
        return children != null && children.size() != 0;
    }

    @Override
    public boolean isDepartmentCode(String code) {
        Assert.notNull(code, "组织机构编码不能为空");
        Organization organization = repository.findByCodeIgnoreCase(code);
        if (organization == null) return false;

        return (organization.getType() == OrgType.department);
    }

    @Override
    public Organization queryOrganizationByCode(String code) {
        Assert.notNull(code, "组织机构编码不能为空");
        return repository.findByCodeIgnoreCase(code);
    }
}
