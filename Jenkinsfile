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
            for /f "tokens=2" %%i in ('wmic process where "CommandLine like '%%CI-CD-DEVOPS%%'" get ProcessId ^| findstr [0-9]') do (
                taskkill /F /PID %%i
            )

            cmd /c start "" /B java -jar "%WORKSPACE%\\target\\CI-CD-DEVOPS-0.0.1-SNAPSHOT.jar" > C:\\uat.log 2>&1

            echo UAT Deployment Completed
            '''
        }
    }
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