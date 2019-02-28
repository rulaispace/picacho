package com.abaya.picacho.org.model;

import com.abaya.picacho.common.model.TreeNode;
import com.abaya.picacho.user.model.AccountState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrgNode extends TreeNode {
    private AccountState state;
}
