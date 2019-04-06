package com.abaya.picacho.biz.account.model;

import com.abaya.picacho.common.model.CommonState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUser {
    private Long id;
    private String name;
    private String username;
    private String department;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createDateTime;

    private CommonState state;
}
