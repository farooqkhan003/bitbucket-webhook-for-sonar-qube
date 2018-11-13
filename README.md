# Bitbucket webhook listener for sonar qube
The values you need to pass while creating build are as follows
- -Dskip.tests=\<boolean>
- -Dbitbucket.repo.slug
- -Dbitbucket.account.username
- -Dsonarqube.server.url
- -Dbitbucket.auth.key
- -Dbitbucket.auth.secret
- -Dsonarqube.project.token
- -Dpath.to.project.root
- -Djenkins.server.url
- -Djenkins.server.crumb
- -Djenkins.server.auth.token
- -Djenkins.server.jenkins.username

Parameter name in jenkins should be `source_branch_name`

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
