package com.abaya.picacho.org.model;

import com.abaya.picacho.common.model.Node;
import com.abaya.picacho.user.model.AccountState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrgNode extends Node {
    private AccountState state;
}
