package com.microservices.otmp.masterdata.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ToLpcApcDTO implements Serializable {
   private Integer  pageNumber;
    private Integer     totalPages;
    private Integer       pageSize;
    private Integer       totalElements;
    private List<ToLpcApcSubsetDTO> content;

    @Data
    public static class ToLpcApcSubsetDTO implements Serializable {
        private String partNumber;
        private String apc;
        private String country;
        private String internalName;
        private String externalName;
        private String status;

    }
}
