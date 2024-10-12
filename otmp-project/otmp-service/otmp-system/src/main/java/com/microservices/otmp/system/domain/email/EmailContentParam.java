package com.microservices.otmp.system.domain.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailContentParam {

//    private String username;

    private String email;

    private String initUrl;

    private String role;
}
