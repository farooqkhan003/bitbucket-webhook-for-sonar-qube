package com.farooq.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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

    public void triggerParameterizedJenkinsJob(String branch) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Jenkins-Crumb", jenkinsCrumb);
        headers.setBasicAuth(jenkinsUsername, jenkinsAuthToken);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Jenkins-Crumb", jenkinsCrumb);


        String url = String.format("http://%s/%s", jenkinsServerUrl, "buildWithParameters");

        log.info("Sending post request to {}", jenkinsServerUrl);

        Map<String, String> params = new HashMap<>();
        params.put("source_branch_name", branch);

        HttpEntity request = new HttpEntity<>(params, headers);

        log.info("{}:{}", url, request.toString());

        restTemplate.postForObject(url, request, String.class);

    }
}
