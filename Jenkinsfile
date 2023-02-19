pipeline {
  agent any
 /* tools {
    maven "maven"
  } */
  environment {
    MAVEN_HOME = tool 'MAVEN'
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

                        sh "mvn clean package "
                }

    }
 }
     post {
            success {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    def groupId = pom.groupId
                    def artifactId = pom.artifactId
                    def version = pom.version
                    def packaging = pom.packaging

                    sh "mvn org.sonatype.plugins:nexus-staging-maven-plugin:1.6.7:deploy-staged-repository \
                      -DnexusUrl=${NEXUS_URL} \
                      -DserverId=nexus \
                      -DrepositoryDirectory=target \
                      -Dartifact=${WORKSPACE}/target/${artifactId}-${version}.${packaging} \
                      -DautoReleaseAfterClose=true \
                      -DskipStagingRepositoryClose=false \
                      -DskipTests"
                }
            }
    }
}