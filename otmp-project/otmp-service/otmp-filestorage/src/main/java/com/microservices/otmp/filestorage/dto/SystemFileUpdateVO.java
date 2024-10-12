package com.microservices.otmp.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemFileUpdateVO {
    private String dataId;
    private String attachments;
}
