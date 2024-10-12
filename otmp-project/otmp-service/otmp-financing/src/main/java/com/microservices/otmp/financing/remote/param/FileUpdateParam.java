package com.microservices.otmp.financing.remote.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUpdateParam {

    private String dataId;
    private String attachments;

}
