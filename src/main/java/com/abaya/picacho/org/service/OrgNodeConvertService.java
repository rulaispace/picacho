package com.abaya.picacho.org.service;

import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.model.OrgNode;

import java.util.List;

public interface OrgNodeConvertService {
    OrgNode convert(List<Organization> organizations);
}
