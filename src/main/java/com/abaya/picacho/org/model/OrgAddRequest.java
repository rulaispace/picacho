package com.abaya.picacho.org.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrgAddRequest {
    private long parentId;
    private String primaryText;
    private String secondaryText;
    private NodeType type;
}
