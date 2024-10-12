package com.microservices.otmp.erp.controller;

import com.microservices.otmp.erp.task.InvoiceDataTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;


@RestController
@RequestMapping("/api/job")
@Api(value = "manual", tags = { "手动JOB触发" })
public class TaskJobController {

	private final static Logger logger = LoggerFactory.getLogger(TaskJobController.class);


	@Autowired
	private InvoiceDataTask task;

	@GetMapping("/invoiceDataJob")
	public String update() {

		try {
			task.queryInvoiceDataByFeedJob();
			return "success";
		}catch (Exception e){
			logger.error(e.getMessage(),e);
			return "error";
		}
	}
}
