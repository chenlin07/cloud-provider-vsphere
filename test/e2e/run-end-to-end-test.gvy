#!groovy

// Pipeline to run end-to-end tests to cloud-provider-vsphere repo
// See https://tkgservice.svc.eng.vmware.com/job/cloud-provider-vsphere-e2e-test/


pipeline {
    agent {
        docker { image 'golang:latest' }
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '30'))
    }

    parameters {
        string(name: 'BRANCH', description: '[Required] The cloud-provider-vsphere.git branch to run e2e test from', defaultValue: "master")
    }

    stages {
//         stage('install go 1.17') {
//             steps {
//                 sh 'wget https://go.dev/dl/go1.17.7.linux-amd64.tar.gz -q'
//                 sh 'sudo rm -rf /usr/local/go && sudo tar -C /usr/local -xzf go1.17.7.linux-amd64.tar.gz'
//                 sh 'export PATH=$PATH:/usr/local/go/bin'
//                 sh 'go version'
//             }
//         }

        stage('run e2e test') {
            steps {
                sh 'cd test/e2e'
                sh 'make'
            }
        }
    }
    post {
        success {
            archiveArtifacts "output/**"
        }
    }
}
