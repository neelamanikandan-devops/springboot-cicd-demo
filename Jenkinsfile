pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'Maven3'
    }

    environment {
        SONAR = 'SonarCloud'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/neelamanikandan-devops/springboot-cicd-demo.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Unit Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Sonar Analysis') {
            steps {
                withSonarQubeEnv("${SONAR}") {
                    bat 'mvn sonar:sonar'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
            }
        }

        stage('Deploy UAT') {
            steps {
                bat 'copy target\\*.jar C:\\deploy\\UAT\\CI-CD-DEVOPS-0.0.1-SNAPSHOT.jar'
            }
        }

        stage('Approval') {
            steps {
                input message: 'Deploy to Production?'
            }
        }

        stage('Deploy Production') {
            steps {
                bat 'copy target\\*.jar C:\\deploy\\PROD\\CI-CD-DEVOPS-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}