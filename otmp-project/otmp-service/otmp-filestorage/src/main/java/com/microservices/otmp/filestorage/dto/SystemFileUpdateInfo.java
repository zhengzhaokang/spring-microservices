package com.microservices.otmp.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemFileUpdateInfo implements Serializable {
    private List<SystemFileUpdateVO> systemFileUpdateVOList;
}
