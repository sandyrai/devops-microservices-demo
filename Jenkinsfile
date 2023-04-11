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
        stage('Build and Push Docker images') {
            steps {
                script {
                    def imageNamePrefix = "skumar97"
                    def imageVersion = "v3"

                    withCredentials([usernamePassword(credentialsId: 'docker_hub_login', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        withEnv(["PATH+DOCKER=/usr/local/bin"]) {
                            sh 'docker login -u $USERNAME -p $PASSWORD'

                            def apigatewayImage = docker.build("${imageNamePrefix}/apigateway:${imageVersion}", "./api-gateway")
                            apigatewayImage.push()

                            def app1Image = docker.build("${imageNamePrefix}/app1:${imageVersion}", "./app1")
                            app1Image.push()

                            def app2Image = docker.build("${imageNamePrefix}/app2:${imageVersion}", "./app2")
                            app2Image.push()

                            def eurekaserverImage = docker.build("${imageNamePrefix}/eurekaserver:${imageVersion}", "./eureka-server")
                            eurekaserverImage.push()
                        }
                    }
                }
            }
        }
    }
}
