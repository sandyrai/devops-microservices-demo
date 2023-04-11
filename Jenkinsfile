pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build Maven projects') {
            steps {
                dir('api-gateway') {
                    sh 'mvn clean install -DskipTests'
                }
                dir('app1') {
                    sh 'mvn clean install -DskipTests'
                }
                dir('app2') {
                    sh 'mvn clean install -DskipTests'
                }
                dir('eureka-server') {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        stage('Build Docker images') {
            steps {
                sh 'docker-compose build'
            }
        }
        stage('Push Docker images') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                    withEnv(["PATH+DOCKER=/usr/local/bin"]) {
                        sh 'docker login -u $USERNAME -p $PASSWORD'
                        sh 'docker-compose push --quiet'
                    }
                }
            }
        }
    }
}
