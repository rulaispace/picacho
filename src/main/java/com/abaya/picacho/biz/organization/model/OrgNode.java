package com.abaya.picacho.biz.organization.model;

import com.abaya.picacho.common.model.TreeNode;
import com.abaya.picacho.common.model.CommonState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrgNode extends TreeNode {
    private CommonState state;
}
