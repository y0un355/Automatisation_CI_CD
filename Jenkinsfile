pipeline {
  agent any
  tools {
          maven "MAVEN"
  }
  environment {
          NEXUS_VERSION = "nexus3"
          NEXUS_PROTOCOL = "https"
          NEXUS_URL = "localhost:8081"
          NEXUS_REPOSITORY = "maven-releases"
          NEXUS_CREDENTIAL_ID = "admin"
      }
  stages {
    stage('Mvn and Java version') {
      steps {
        sh 'mvn --version ;java -version'
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
    stage('Read POM file') {
          steps {
            script {
                pom = readMavenPom file: 'pom.xml'
                groupId = pom.groupId
                artifactId = pom.artifactId
                packaging = pom.packaging
                version = pom.version
                filepath = "target/${artifactId}-${version}.jar"
                isSnapshot = version.endsWith("-SNAPSHOT")
            }
            echo groupId
            echo artifactId
            echo packaging
            echo version
            echo filepath
          }
      }
    stage('Build package') {
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
                            echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                            nexusArtifactUploader(
                                nexusVersion: NEXUS_VERSION,
                                protocol: NEXUS_PROTOCOL,
                                nexusUrl: NEXUS_URL,
                                groupId: pom.groupId,
                                version: pom.version,
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
                        } else {
                            error "*** File: ${artifactPath}, could not be found";
                        }
                    }
                }
    }

  }
}