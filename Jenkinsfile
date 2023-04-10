pipeline {
    agent any
    
    environment {
        DOCKER_HUB_USERNAME = credentials('DOCKER_HUB_USERNAME')
        DOCKER_HUB_PASSWORD = credentials('DOCKER_HUB_PASSWORD')
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build Docker images') {
            steps {
                script {
                    def services = ['api-gateway', 'app1', 'app2', 'eureka-server']
                    
                    services.each { service ->
                        sh "docker build -t ${DOCKER_HUB_USERNAME}/${service}:latest ${service}"
                    }
                }
            }
        }
        
        stage('Push Docker images') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    script {
                        sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                        
                        def services = ['api-gateway', 'app1', 'app2', 'eureka-server']
                        
                        services.each { service ->
                            sh "docker push ${DOCKER_HUB_USERNAME}/${service}:latest"
                        }
                    }
                }
            }
        }
        
        // Add a stage for running tests if you have any
    }
}