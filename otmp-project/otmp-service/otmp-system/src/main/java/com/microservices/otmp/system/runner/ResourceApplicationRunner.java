package com.microservices.otmp.system.runner;

import com.microservices.otmp.system.service.ISysDictDataService;
import com.microservices.otmp.system.service.ISysUserDataScopeService;
import com.microservices.otmp.system.service.ISysDictDataService;
import com.microservices.otmp.system.service.ISysUserDataScopeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化 system 模块对应业务数据
 *
 * @author lovefamily
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ResourceApplicationRunner implements ApplicationRunner {


    private final ISysDictDataService sysDictDataService;

    private final ISysUserDataScopeService sysUserDataScopeService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("初始化字典数据开始");
        sysDictDataService.init();
        log.info("字典数据初始化完成==============>>>>>>>>");

        log.info("初始数据库表结构数据开始");
        sysUserDataScopeService.initDbTableColumns();
        log.info("数据库表结构数据初始化完成==============>>>>>>>>");

    }

}
