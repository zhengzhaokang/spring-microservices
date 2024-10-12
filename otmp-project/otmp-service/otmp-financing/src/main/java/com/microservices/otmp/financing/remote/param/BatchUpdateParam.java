package com.microservices.otmp.financing.remote.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchUpdateParam implements Serializable {

    private List<FileUpdateParam> systemFileUpdateVOList;
}
