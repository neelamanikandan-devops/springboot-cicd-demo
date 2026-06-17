pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'Maven3'
    }

    environment {
        SonarQube = 'SonarQube'
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

        // stage('SonarQube Analysis') {
        //     steps {
        //         withSonarQubeEnv('SonarQube') {
        //             bat 'mvn sonar:sonar'
        //         }
        //     }
        // }

        stage('SonarQube Analysis') {
            steps {
                bat '''
                    mvn clean verify sonar:sonar ^
                    -Dsonar.projectKey=springboot-cicd-demo ^
                    -Dsonar.host.url=http://localhost:9000 ^
                    -Dsonar.token=sqa_5ea45c2276d7a49ddd7f07eb862fe36feeb7a4af
                '''
            }
        }
        stage('Package') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }        

        stage('Deploy UAT') {
            steps {
                bat '''
                taskkill /F /IM java.exe || exit 0
                copy target\\*.jar C:\\deploy\\UAT\\CI-CD-DEVOPS.jar
                start javaw -jar C:\\deploy\\UAT\\CI-CD-DEVOPS.jar
                '''
            }
        }

        // stage('Approval') {
        //     steps {
        //         input message: 'Deploy to Production?'
        //     }
        // }
        stage('Approval') {
            steps {
                script {
                    def approval = input(
                        message: 'Deploy to Production?',
                        ok: 'Proceed',
                        parameters: [
                            choice(
                                name: 'ACTION',
                                choices: ['DEPLOY', 'REJECT'],
                                description: 'Select deployment action'
                            )
                        ]
                    )
                    if (approval == 'REJECT') {
                        error('Production deployment rejected')
                    }
                }
            }
        }

        stage('Deploy Production') {
            steps {
                bat 'copy target\\*.jar C:\\deploy\\PROD\\CI-CD-DEVOPS-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}