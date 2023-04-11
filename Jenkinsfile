pipeline {
    agent any

    environment {
        DOCKER_CREDS = credentials('docker-hub-credentials')
    }


        
        stage('Checkout') {
    steps {
        checkout scm
        sh "pwd" // Display the current working directory
        sh "ls -la" // List the contents of the directory
        sh "git status" // Check the Git status
    }
}

        stage('Build Docker images') {
            steps {
                script {
                    DOCKER_CREDS_USR = sh(script: 'echo $DOCKER_CREDS_USR', returnStdout: true).trim()
                    DOCKER_CREDS_PSW = sh(script: 'echo $DOCKER_CREDS_PSW', returnStdout: true).trim()
                }
                sh "docker login -u ${DOCKER_CREDS_USR} -p ${DOCKER_CREDS_PSW}"
                sh "docker-compose build"
            }
        }

        stage('Push Docker images') {
            steps {
                sh "docker-compose push"
            }
        }
    }
}
