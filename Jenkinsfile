pipeline {
  agent any
  tools {
          maven "MAVEN"
  }
  environment {
          NEXUS_ID = "admin"
          NEXUS_URL = "http://localhost:8081"
          NEXUS_REPOSITORY = "maven-releases"
          GROUP_ID = ''
          ARTEFACT_ID = ''
          def FILE_PATH = ''
          def PACKAGGING = ''
          def VERSION = ''
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
                GROUP_ID = pom.groupId
                ARTEFACT_ID = pom.artifactId
                PACKAGING = pom.packaging
                VERSION = pom.version
                FILE_PATH = "target/${artifactId}-${version}.jar"
            }
            echo GROUP_ID
            echo artifactId
            echo PACKAGING
            echo VERSION
            echo FILE_PATH
          }
      }
    stage('Build package') {
        steps {
            sh 'mvn clean package'
        }
    }
    stage('Publish to Nexus Repository Manager') {
        steps {
              sh 'mvn deploy:deploy-file -e -Dinternal.repo.username=admin -Dinternal.repo.password=admin -DgroupId=${GROUP_ID} -Dversion=${VERSION} -Dpackaging=${PACKAGING} -Durl=${NEXUS_URL}/repository/ -Dfile=${FILE_PATH} -DartifactId=${artifactId} -DrepositoryId=${NEXUS_REPOSITORY}'
            }

      }
    }
}