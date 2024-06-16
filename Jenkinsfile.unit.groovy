pipeline {
    agent {
        label 'laboratorio'
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building stage!'
                sh 'make build'
                
            }
        }
        stage('Unit tests') {
            steps {
                sh 'make test-unit'
                archiveArtifacts artifacts: 'unit_test/*.xml'
            }
        }
        stage('Pruebas API') {
            steps {
                sh 'make test-api'
                archiveArtifacts artifacts: 'api_test/*.xml'
            }
        }

        stage('Pruebas E2E') {
            steps {
                sh 'make server'
                sh 'make test-e2e'
                archiveArtifacts artifacts: 'results/e2e_test/*.xml'
            }
        }

    }
    post {
        always {
            junit 'results/unit_test/*_result.xml'
            junit 'results/api_test/*_result.xml'
            junit 'results/e2e_test/*_result.xml'
            cleanWs()
        }

         failure {
            mail to: 'rodrigoenrrique.ordonez189@comunidadunir.net',
                  subject: "Error en la Ejecucion de '${env.JOB_NAME}' #${env.BUILD_NUMBER}",
                  body: "El motivo de esta notificacion es porque su pipeline '${env.JOB_NAME}' #${env.BUILD_NUMBER} ha teminado en error"
        }

    }
}