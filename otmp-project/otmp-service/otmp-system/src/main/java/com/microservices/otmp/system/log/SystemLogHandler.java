package com.microservices.otmp.system.log;

import com.microservices.otmp.system.domain.dto.SystemLogDTO;
import org.springframework.beans.factory.InitializingBean;


/*
* @Author: shirui3
*
* 之后如果 用不同的方法 比如有的void 有的有返回值 可用模板设计模式
* */
public interface SystemLogHandler extends InitializingBean {

    void handleData(SystemLogDTO systemLogDTO);
}
