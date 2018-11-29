package com.farooq.controller;

import com.farooq.service.BitbucketWebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by farooq khan on 11/12/2018.
 */
@RestController
@RequestMapping(value = "bitbucket")
public class BitbucketWebhookController {

    @Autowired
    private BitbucketWebhookService bitbucketWebhookService;

    @PostMapping(value = "webhook")
    public void runSonarAnalysis(@RequestBody String bitBucketModel){
        bitbucketWebhookService.runSonarAnalysisCommand(bitBucketModel);
    }

    @PostMapping(value = "webhook/jenkins/{jobName}")
    public void runSonarAnalysisViaJenkins(@RequestBody String bitBucketModel, @PathVariable("jobName") String jobName){
        bitbucketWebhookService.triggerJenkinsJobWithParameters(bitBucketModel, jobName);
    }
}
