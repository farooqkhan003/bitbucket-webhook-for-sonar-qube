package com.farooq.controller;

import com.farooq.service.BitbucketWebhookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "webhook/jenkins")
    public void runSonarAnalysisViaJenkins(@RequestBody String bitBucketModel){
        bitbucketWebhookService.triggerJenkinsJobWithParameters(bitBucketModel);
    }
}
