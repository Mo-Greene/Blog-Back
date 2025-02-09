pipeline {
    agent any

    tools {
        gradle 'gradle'
    }

    environment {
        REGISTRY_URL = 'http://43.201.250.140'
        REGISTRY_CREDENTIALS = 'docker-registry'
        IMAGE_NAME = 'mo-greene-blog'
        IMAGE_TAG = 'latest'
    }

    stages {

        stage('copy yml') {
            steps {
                withCredentials([
                    file(credentialsId: 'application-aws', variable: 'AWS_YML'),
                    file(credentialsId: 'application-prod', variable: 'PROD_YML'),
                    file(credentialsId: 'application-secret', variable: 'SECRET_YML')
                ]) {
                    script {
                        sh "chmod -R 755 /var/lib/jenkins/workspace/mo-greene-blog_main/src/main/resources"

                        sh "cp $AWS_YML /var/lib/jenkins/workspace/mo-greene-blog_main/src/main/resources/application-aws.yml"
                        sh "cp $PROD_YML /var/lib/jenkins/workspace/mo-greene-blog_main/src/main/resources/application-prod.yml"
                        sh "cp $SECRET_YML /var/lib/jenkins/workspace/mo-greene-blog_main/src/main/resources/application-secret.yml"

                        sh "chmod 755 /var/lib/jenkins/workspace/mo-greene-blog_main/src/main/resources/application-aws.yml"
                        sh "chmod 755 /var/lib/jenkins/workspace/mo-greene-blog_main/src/main/resources/application-prod.yml"
                        sh "chmod 755 /var/lib/jenkins/workspace/mo-greene-blog_main/src/main/resources/application-secret.yml"
                    }
                }
                echo 'copy yml and change permissions'
            }
        }

        stage('Build Jar') {
            steps {
                script {
                    sh "chmod +x ./gradlew"
                    sh './gradlew clean build'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t ${REGISTRY_URL}/${IMAGE_NAME}:${IMAGE_TAG} ."
                }
            }
        }

        stage('Login to Docker Registry') {
            steps {
                withCredentials([usernamePassword(credentialsId: REGISTRY_CREDENTIALS, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    script {
                        sh "echo $DOCKER_PASS | docker login $REGISTRY_URL -u $DOCKER_USER --password-stdin"
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    sh "docker push ${REGISTRY_URL}/${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }

    }
}