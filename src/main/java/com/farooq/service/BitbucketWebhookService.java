package com.farooq.service;

import com.farooq.gateway.JenkinsGateway;
import com.farooq.model.BitbucketModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by farooq khan on 11/12/2018.
 */
@Slf4j
@Service
public class BitbucketWebhookService {

    @Autowired
    private JenkinsGateway jenkinsGateway;

    @Value("${path.to.project.root}")
    private String pathToProjectRoot;
    private String skipTests;

    @Value("${sonarqube.server.url}")
    private String sonarQubeServerUrl;

    @Value("${sonarqube.project.token}")
    private String sonarQubeProjectToken;

    @Value("${bitbucket.repo.slug}")
    private String repoSlug;

    @Value("${bitbucket.account.username}")
    private String bitbucketAccountUsername;

    @Value("${bitbucket.auth.key}")
    private String bitbucketAuthKey;

    @Value("${bitbucket.auth.secret}")
    private String bitbucketAuthSecret;

    @Value("${skip.tests}")
    public void setSkipTests(boolean shouldSkipTests) {
        if (shouldSkipTests) {
            this.skipTests = " -DskipTests";
        } else {
            this.skipTests = "";
        }

    }

    public void runSonarAnalysisCommand(String bitBucketModelString) {
        log.debug("Bitbucket webhook request: {}", bitBucketModelString);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            BitbucketModel bitbucketModel = objectMapper.readValue(bitBucketModelString, BitbucketModel.class);
            String command = String.format("mvn clean verify %s sonar:sonar --batch-mode --errors " +
                            "-Dsonar.bitbucket.repoSlug=%s -Dsonar.bitbucket.accountName=%s " +
                            "-Dsonar.bitbucket.branchName=%s -Dsonar.host.url=%s -Dsonar.login=%s " +
                            "-Dsonar.analysis.mode=issues -Dsonar.bitbucket.oauthClientKey=%s " +
                            "-Dsonar.bitbucket.oauthClientSecret=%s",
                    skipTests, repoSlug, bitbucketAccountUsername,
                    bitbucketModel.getPullrequest().getSource().getBranch().getName(),
                    sonarQubeServerUrl, sonarQubeProjectToken, bitbucketAuthKey, bitbucketAuthSecret);


            log.info("Running command {}", command);

            Process process = Runtime.getRuntime().exec("cmd.exe /c mvn clean", null, new File(pathToProjectRoot));

        } catch (Exception ex) {
            log.error("Error while running command.");
            ex.printStackTrace();

        }

    }

    public void triggerJenkinsJobWithParameters(String bitBucketModelString){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            BitbucketModel bitbucketModel = objectMapper.readValue(bitBucketModelString, BitbucketModel.class);

            jenkinsGateway.triggerParameterizedJenkinsJob(bitbucketModel.getPullrequest().getSource().getBranch().getName());
        } catch (Exception ex) {
            log.error("Error while triggering jenkins call.");
            ex.printStackTrace();
        }
    }
}
