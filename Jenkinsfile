pipeline {
    agent any
    
	tools {
        maven 'Maven3'
    	}
    	
    stages {
		
		stage('Check Java') {
		    steps {
		        bat 'java -version'
		        bat 'javac -version'
		        bat 'mvn -version'
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