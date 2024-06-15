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
    }
}