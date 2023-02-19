pipeline {
  agent any
 /* tools {
    maven "maven"
  } */
  environment {
    NEXUS_VERSION = "nexus3"
    NEXUS_PROTOCOL = "http"
    NEXUS_URL = "http://localhost:8081"
    NEXUS_REPOSITORY = "releases"
   	NEXUS_GROUP_ID    = "com.example"
    NEXUS_CREDENTIAL_ID = "admin"
    ARTVERSION = "${env.BUILD_ID}"
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
                       def groupId = pom.groupId
                       def artifactId = pom.artifactId
                       def version = pom.version
                       def packaging = pom.packaging
                       filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                       echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                       artifactPath = filesByGlob[0].path;
                       artifactExists = fileExists artifactPath;
                       if(artifactExists) {
                           echo '''*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version} ARTVERSION''';
                           def nexusPublisher = NexusPublisher.newPublisher(credentialsId: NEXUS_CREDENTIALS_ID, nexusUrl: NEXUS_URL)
                              nexusPublisher.upload(
                                              groupId: groupId,
                                              artifactId: artifactId,
                                              version: version,
                                              packaging: packaging,
                                              file: "target/${artifactId}-${version}.${packaging}"
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