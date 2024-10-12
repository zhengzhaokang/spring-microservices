package com.microservices.otmp.finance;

import lombok.Data;

import java.util.List;

@Data
public class CheckResult<T> {
    private boolean pass;
    private String msg;
    private List<T> passLists;

}
