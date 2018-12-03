package com.farooq.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by farooq khan on 11/13/2018.
 */
@Slf4j
@Component
public class JenkinsGateway {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${jenkins.server.url:}")
    private String jenkinsServerUrl;

    @Value(("${jenkins.server.jenkins.username:}"))
    private String jenkinsUsername;

    @Value("${jenkins.server.auth.token:}")
    private String jenkinsAuthToken;

    @Value("${jenkins.server.crumb:}")
    private String jenkinsCrumb;

    public void triggerParameterizedJenkinsJob(String branch, String jobName) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Jenkins-Crumb", jenkinsCrumb);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(jenkinsUsername, jenkinsAuthToken);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Jenkins-Crumb", jenkinsCrumb);


        String url = String.format("%s/job/%s/%s", jenkinsServerUrl, jobName,"buildWithParameters");

        log.info("Sending post request to {}", jenkinsServerUrl);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("source_branch_name", branch);

        HttpEntity request = new HttpEntity<>(params, headers);

        log.info("{}:{}", url, request.toString());

        restTemplate.postForObject(url, request, String.class);

    }
}
