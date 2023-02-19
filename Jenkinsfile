pipeline {
  agent any
 /* tools {
    maven "maven"
  } */
  environment {
    NEXUS_VERSION = "nexus3"
    NEXUS_PROTOCOL = "http"
    NEXUS_URL = "localhost:8081"
    NEXUS_REPOSITORY = "maven-releases"
   	NEXUS_GROUP_ID    = "com.example"
    NEXUS_CREDENTIAL_ID = "admin"
    ARTVERSION = '${env.BUILD_ID}'
  }
  stages {
    stage('Mvn and Java version') {
      steps {
        sh 'mvn --version;java -version'
      }
    }

    stage('Unit tests') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Create jar') {
      steps {
        sh 'mvn clean;mvn install ;mvn compile assembly:single;'
      }
    }
    stage('Build') {
        steps {
            sh 'mvn clean package'
        }
    }
    stage("Publish to Nexus Repository Manager") {
               steps {
                   script {
                       pom = readMavenPom file: "pom.xml";
                       filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                       echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                       artifactPath = filesByGlob[0].path;
                       artifactExists = fileExists artifactPath;
                       if(artifactExists) {
                           echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version} ARTVERSION";
                               nexusArtifactUploader(
                                   nexusVersion: NEXUS_VERSION,
                                   protocol: NEXUS_PROTOCOL,
                                   nexusUrl: NEXUS_URL,
                                   groupId: NEXUS_GROUP_ID,
                                   version: version,
                                   repository: NEXUS_REPOSITORY,
                                   credentialsId: NEXUS_CREDENTIAL_ID,
                                   artifacts: [
                                       [artifactId: projectName,
                                        classifier: '',
                                        file: 'go-securi-mspr-' + version + '.jar',
                                        type: 'jar']
                                   ]
                                );
                       }
   		    else {
                           error "*** File: ${artifactPath}, could not be found";
                       }
                   }
               }
           }

}
}