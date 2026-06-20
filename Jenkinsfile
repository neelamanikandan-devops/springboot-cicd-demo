pipeline {

    agent any


    tools {

        jdk 'jdk21'

        maven 'Maven3'
    }


    environment {

        SONAR_PROJECT_KEY = 'springboot-cicd-demo'

        SONAR_HOST_URL = 'http://localhost:9000'

        SONAR_TOKEN = credentials('sonar-token')

        APP_NAME = 'CI-CD-DEVOPS-0.0.1-SNAPSHOT.jar'

        UAT_PATH = 'C:\\uat'

        PROD_PATH = 'C:\\deploy\\PROD'

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



        stage('SonarQube Analysis') {

            steps {

                bat """

                mvn clean verify sonar:sonar ^
                -Dsonar.projectKey=%SONAR_PROJECT_KEY% ^
                -Dsonar.host.url=%SONAR_HOST_URL% ^
                -Dsonar.token=%SONAR_TOKEN%

                """

            }

        }



        stage('Package') {

            steps {

                bat 'mvn clean package -DskipTests'

            }

        }



        stage('Deploy UAT') {

            steps {

                bat """

                echo Stopping existing UAT application


                for /f "tokens=2" %%i in ('wmic process where "CommandLine like '%%CI-CD-DEVOPS%%'" get ProcessId ^| findstr [0-9]') do (

                    taskkill /F /PID %%i

                )


                echo Starting UAT application


                cmd /c start "" /B java -jar "%WORKSPACE%\\target\\%APP_NAME%" > C:\\uat.log 2>&1


                echo UAT Deployment Completed

                """

            }

        }




        stage('Approval') {

            steps {

                script {


                    def approval = input(

                        message: 'Deploy Application to Production?',

                        ok: 'Proceed',

                        parameters: [

                            choice(

                                name: 'ACTION',

                                choices: ['DEPLOY','REJECT'],

                                description: 'Choose deployment action'

                            )

                        ]

                    )


                    if(approval == 'REJECT') {

                        error('Production deployment rejected')

                    }


                }

            }

        }




        stage('Deploy Production') {

            steps {


                bat """

                echo Deploying to Production


                copy target\\*.jar %PROD_PATH%\\%APP_NAME%


                echo Production Deployment Completed

                """

            }

        }


    }



    post {


        success {

            echo 'CI/CD Pipeline Completed Successfully'

        }


        failure {

            echo 'CI/CD Pipeline Failed'

        }


        always {

            echo 'Pipeline Execution Finished'

        }

    }

}