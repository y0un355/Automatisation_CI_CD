pipeline {
  agent any
  stages {
    stage('Mvn and Java version') {
      steps {
        sh 'mvn --version; java -version'
      }
    }

    stage('Unit tests') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Create jar') {
      steps {
        sh 'mvn clean; mvn install; mvn compile assembly:single;'
        script {
          def artifactPath = sh(returnStdout: true, script: 'ls -t target/*.jar | head -1').trim()
          nexusArtifactUploader nexusParams: [
            nexusUrl: 'https://localhost:8081',
            nexusUsername: 'admin',
            nexusPassword: credentials('admin'),
            repository: 'maven-releases',
            groupId: 'com.example',
            artifactId: 'gosecuri',
            version: '1.0',
            packaging: 'jar',
            artifact: artifactPath
          ]
        }
      }
    }

    stage('Build jar') {
      steps {
        sh 'mvn clean package'
      }
    }
  }
}

def nexusArtifactUploader(def nexusParams) {
  def artifactPath = nexusParams.artifact
  def nexusUrl = nexusParams.nexusUrl
  def nexusUsername = nexusParams.nexusUsername
  def nexusPassword = nexusParams.nexusPassword
  def repository = nexusParams.repository
  def groupId = nexusParams.groupId
  def artifactId = nexusParams.artifactId
  def version = nexusParams.version
  def packaging = nexusParams.packaging
  def mavenCoords = "${groupId}:${artifactId}:${version}"
  def nexusUrlPath = "${nexusUrl}/repository/${repository}"
  def nexusArtifactPath = "${nexusUrlPath}/${groupId.replace('.','/')}/${artifactId}/${version}/${artifactId}-${version}.${packaging}"
  sh "curl -v -u ${nexusUsername}:${nexusPassword} --upload-file ${artifactPath} ${nexusArtifactPath}"
  echo "Artifact ${mavenCoords} uploaded to Nexus repository ${repository} at ${nexusUrlPath}"
}
