pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/sandyrai/devops-microservices-demo'
            }
        }
        stage('Update Docker image tags') {
            steps {
                script {
                    def newTag = "v${env.BUILD_NUMBER}"
                    def composeFile = readFile('docker-compose.yml')
                    composeFile = composeFile.replaceAll(/(image: skumar97\/\w+:)v\d+/, "\$1${newTag}")
                    writeFile(file: 'docker-compose.yml', text: composeFile)
                }
            }
        }
        stage('Build Docker images') {
            steps {
                sh 'docker-compose build'
            }
        }
        stage('Login to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                }
            }
        }
        stage('Push Docker images') {
            steps {
                sh 'docker-compose push'
            }
        }
    }
}
