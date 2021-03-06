package com.abaya.picacho.biz.resource.service;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.biz.resource.entity.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> queryAll();

    Resource addResource(Resource resource) throws ServiceException;

    void deleteResource(String code, String operator) throws ServiceException;

    Resource updateResource(Resource resource) throws ServiceException;

    Resource activateResource(String code, String operator) throws ServiceException;

    Resource deactivateResource(String code, String operator) throws ServiceException;

    boolean resourceIsValid(Long resourceId) throws ServiceException;

    List<Resource> queryAllValidResource();
}
