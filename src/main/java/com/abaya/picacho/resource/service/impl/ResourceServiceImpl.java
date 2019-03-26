package com.abaya.picacho.resource.service.impl;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.model.CommonState;
import com.abaya.picacho.common.util.EntityUtils;
import com.abaya.picacho.resource.entity.Resource;
import com.abaya.picacho.resource.repository.ResourceRepository;
import com.abaya.picacho.resource.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceRepository repository = null;

    @Override
    public List<Resource> queryAll() {
        return repository.findAll();
    }

    @Override
    public Resource addResource(Resource resource) throws ServiceException {
        Assert.notNull(resource, "传入的带增加资源对象不能为空");
        return repository.save(resource);
    }

    @Override
    public void deleteResource(String code, String operator) throws ServiceException {
        Assert.notNull(code, "传入资源编码不能为空");
        Assert.notNull(operator, "传入操作人员不能为空");

        Resource resource = repository.findByCodeIgnoreCase(code);
        if (resource == null) throw new ServiceException("资源(%s)不存在", code);

        repository.delete(resource);
    }

    @Override
    public Resource updateResource(Resource resource) throws ServiceException {
        Assert.notNull(resource, "传入的资源对象不能为空");

        String code = resource.getCode();
        Assert.notNull(code, "传入的资源编码不能为空");

        Resource origin = repository.findByCodeIgnoreCase(code);
        if (origin == null) throw new ServiceException("组织机构（%s）不存在，请确认请求参数是否正确", code);

        return repository.save(EntityUtils.entityUpdateMerge(origin, resource));
    }

    @Override
    public Resource activateResource(String code, String operator) throws ServiceException {
        Assert.notNull(code, "资源编码不能为空");
        Assert.notNull(operator, "操作人员不能为空");

        Resource resource = repository.findByCodeIgnoreCase(code);
        if (resource == null) throw new ServiceException("不存在该资源(%s)", code);

        if (resource.getState() == CommonState.valid) throw new ServiceException("资源(%s)当前状态有效，无需激活", code);

        resource.setState(CommonState.valid);
        resource.setModifier(operator);

        return repository.save(resource);
    }

    @Override
    public Resource deactivateResource(String code, String operator) throws ServiceException {
        Assert.notNull(code, "资源编码不能为空");
        Assert.notNull(operator, "操作人员不可为空");

        Resource resource = repository.findByCodeIgnoreCase(code);
        if (resource == null) throw new ServiceException("不存在该资源(%s)", code);

        if (resource.getState() != CommonState.valid) throw new ServiceException("资源(%s)当前状态异常，无法注销", code);

        resource.setState(CommonState.invalid);
        resource.setModifier(operator);

        return repository.save(resource);
    }
}
