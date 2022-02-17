package com.govi.batch.springbatchlearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.govi.batch.springbatchlearning.runner.JobRunner;

@RestController
@RequestMapping("/run")
public class JobController {

	@Autowired
	private JobRunner jobRunner;

	@RequestMapping(value = "/job")
	public String runJob() {
		jobRunner.runBatchJob();
		return String.format("Job Demo1 submitted successfully.");
	}
}
