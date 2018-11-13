# Bitbucket Webhook Listener for SonarQube

## Trigger Jenkins Job on PR Generation
In the webhook on bitbucket set the url to <your-server-url>/bitbucket/webhook/jenkins
Set values for the following placeholders if you want to tigger jenkins parameterized job.
- -Djenkins.server.url
- -Djenkins.server.crumb
- -Djenkins.server.auth.token
- -Djenkins.server.jenkins.username

Parameter name in jenkins should be `source_branch_name` of type string.
## Run SonarQube Analysis using this Application
If you are not using jenkins as your build cicd build system, no worries. You can still run sonarQube analysis with this application. But then you have to create build with different paramters. You 
- -Dskip.tests=<boolean>
- -Dbitbucket.repo.slug=
- -Dbitbucket.account.username=
- -Dsonarqube.server.url=
- -Dbitbucket.auth.key=
- -Dbitbucket.auth.secret=
- -Dsonarqube.project.token=
- -Dpath.to.project.root=

Run the following command to create build:
```
mvn clean verify sonar:sonar --batch-mode --errors 
-Dsonar.analysis.mode=issues
-Dsonar.bitbucket.repoSlug= 
-Dsonar.bitbucket.accountName= 
-Dsonar.host.url= -Dsonar.login= 
-Dsonar.bitbucket.oauthClientKey= 
-Dsonar.bitbucket.oauthClientSecret=
```
