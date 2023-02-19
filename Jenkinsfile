pipeline {
  agent any
  tools {
          maven "MAVEN"
  }
  environment {
          NEXUS_ID = "admin"
          NEXUS_URL = "http://localhost:8081"
          NEXUS_REPOSITORY = "maven-releases"
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
    stage('Publish to Nexus Repository Manager') {
        steps {
              sh 'mvn deploy:deploy-file -e -Dinternal.repo.username=admin -Dinternal.repo.password=admin -DgroupId=${groupId} -Dversion=${version} -Dpackaging=${packaging} -Durl=${NEXUS_URL}/repository/ -Dfile=${filepath} -DartifactId=${artifactId} -DrepositoryId=${NEXUS_REPOSITORY}'
            }

    }