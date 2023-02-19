pipeline {
  agent any
  tools {
    maven "maven"
  }
  environment {
    NEXUS_USER = 'admin'
    NEXUS_PASSWORD = 'admin'
    SNAP_REPO = 'maven-snapshot'
    RELEASE_REPO = 'maven-release'
    CENTRAL_REPO = 'maven-central'
    NEXUS_IP = 'localhost'
    NEXUS_PORT = '8081'
    NEXUS_LOGIN = 'admin'
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
            sh 'mvn -s settings.xml install'
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
                               groupId: pom.groupId,
                               version: ARTVERSION,
                               repository: NEXUS_REPOSITORY,
                               credentialsId: NEXUS_CREDENTIAL_ID,
                               artifacts: [
                                   [artifactId: pom.artifactId,
                                   classifier: '',
                                   file: artifactPath,
                                   type: pom.packaging],
                                   [artifactId: pom.artifactId,
                                   classifier: '',
                                   file: "pom.xml",
                                   type: "pom"]
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