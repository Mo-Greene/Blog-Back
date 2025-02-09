pipeline {
    agent any

    tools {
        gradle 'gradle'
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
                        sh "cp $AWS_YML /var/jenkins_home/workspace/mo-greene-blog/src/main/resources/application-aws.yml"
                        sh "cp $PROD_YML /var/jenkins_home/workspace/mo-greene-blog/src/main/resources/application-prod.yml"
                        sh "cp $SECRET_YML /var/jenkins_home/workspace/mo-greene-blog/src/main/resources/application-secret.yml"
                    }
                }
                echo 'copy yml'
            }
        }

    }
}