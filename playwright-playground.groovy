pipeline {
   agent { 
     docker {
        label 'agent-docker'
        image 'mcr.microsoft.com/playwright:latest'
     } 
    }
   stages {
      stage('Clone Repository') {
         steps {
            git branch: 'master',
            credentialsId: 'Tomasz64Github',
            url: 'git@github.com:Tomasz64/playwright-playground.git'
            
         }
      }
      stage('Run Tests') {
        steps {
            sh 'npm install'
            sh 'npx playwright test'
        }
      }
   }
   post {
        always {
            publishHTML (target : [allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'playwright-report',
            reportFiles: 'index.html',
            reportName: 'My Reports',
            reportTitles: 'Playwright Report'])
        }
        success {
            echo 'I succeeded!'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
    }
}