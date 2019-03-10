package com.abaya.picacho.common.convert;


import com.abaya.picacho.common.util.RandomUtils;
import lombok.Data;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class StringUUIDUtilsTest {
    @Test
    public void testCheckFile() {
        System.out.println(RandomUtils.uuid());
    }

    @Test
    public void testModelMapper() {
        SourceParent parent = new SourceParent();
        parent.name = "123";

        SourceChild child = new SourceChild();
        child.name = "456";

        List<SourceChild> children = new ArrayList<>();
        children.add(child);

        parent.children = children;

        ModelMapper modelMapper = new ModelMapper();

        DestParent result = modelMapper.map(parent, DestParent.class);
        System.out.println(result);
    }

    @Data
    static class SourceParent {
        private String name;
        private List<SourceChild> children;
    }

    @Data
    static class SourceChild {
        private String name;
    }

    @Data
    static class DestParent {
        private String name;
        private List<DestChild> children;
    }

    @Data
    static class DestChild {
        private String name;
    }
}
