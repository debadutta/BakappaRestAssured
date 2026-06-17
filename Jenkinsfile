pipeline {
    agent any
    
	tools {
        maven 'Maven3'
    	}
    	
    stages {
		
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Check Java') {
		    steps {
		        sh 'java -version'
		        sh 'javac -version'
		        sh 'mvn -version'
		    }
		}

        stage('Run Tests and generate report') {
            steps {
                sh 'mvn clean test site surefire-report:report'
            }
        }
    }

    post {
    	 always {
    	 	echo '***********************************'
            junit 'target/surefire-reports/*.xml'
            publishHTML([
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'target/site',
            reportFiles: 'surefire-report.html',
            reportName: 'Surefire Report'
        	])
            echo '***********************************'
            
        }
        
        success {
        	echo '***********************************'
            echo '*********Build Successful*********'
            echo '***********************************'
        }

        failure {
            echo '***********************************'
            echo '***********Build Failed***********'
            echo '***********************************'
        }
    }
}