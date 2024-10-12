package com.microservices.otmp.analysis.remote.param;

import lombok.Data;

import java.util.List;

@Data
public class RemoteBatchSummaryParam {

    private List<String> batchNumberList;
}
