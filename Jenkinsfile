pipeline {
    agent any
    
	tools {
        maven 'Maven3'
    	}
    	
    stages {
		
		stage('Debug') {
		    steps {
		        sh 'cat suites/restassuredapitest.xml'
		    }
		}
		
		stage('Check Java') {
		    steps {
		        sh 'java -version'
		        sh 'javac -version'
		        sh 'mvn -version'
		    }
		}

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn clean test'
            }
        }
        
        stage('Generate Report') {
		    steps {
		        sh 'mvn surefire-report:report'
		    }
		}
    }

    post {
    	 always {
    	 	echo '***********************************'
            junit 'target/surefire-reports/*.xml'
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