pipeline {
    agent {label 'DPTUSIGPRODIL01' }
    
    tools {
        jdk 'Java JDK'
    }
	
	environment{
	    MAVEN_PATH = "C:/openstack/jenkins_node/apache-maven-3.8.1/bin:$MAVEN_PATH"
	    CRED = credentials('secret_text_cred')
	}

    stages {
        stage('build-clean') {
            steps {
                echo 'This is the build stage'
                 cleanWs()
            }
        }
    
    
        stage('build-clone') {
            steps {
                echo 'This is the clone stage'
				git branch: 'master',
   			    credentialsId: CRED,
    			url: 'https://github.com/massmutual/auto_java_smart_test_automation.git'
    		}
        }
		
		stage('build-test') {
            steps {
                echo 'This is the maven test stage'
                script {
                                          
				        bat 'mvn test -Dcucumber.options=" --tags @TestAutomationScenario1"'
				        echo "Test completed build-test"
				    
                }
            }
        }
    }  

		 post ("Post-Build Actions"){
		 
		 always{
            publishHTML(target: [allowMissing: true, alwaysLinkToLastBuild: false, escapeUnderscores: false, keepAll: true, reportDir: 'TestResults/', reportFiles: '**/TestReport.html', reportName: 'HTML_Report_1', reportTitles: ''])
        }
        
        success ("JOB SUCCESS"){
            echo "Success Job"
			emailext attachmentsPattern: '**TestResults/**/*.html', body: 'Hi Team,\n\n Please find the attached test summary results.\n\n http://vrflx12031:8080/job/${JOB_NAME}/${BUILD_NUMBER}/HTML_Report_1/ \n\n Thanks, \n Automation Team.\n\n This is auto generated email', from: 'nmadshetty40@massmutual.com', subject: 'Test results Success - Smart Test Automation Pipeline', to: 'nmadshetty40@massmutual.com'
        }

        failure ("JOB FAILURE"){
            echo "Failure Job"
            emailext attachmentsPattern: '**TestResults/**/*.html', body: 'Hi Team,\n\n Please find the attached test summary results.\n\n http://vrflx12031:8080/job/${JOB_NAME}/${BUILD_NUMBER}/HTML_Report_1/ \n\n Thanks, \n Automation Team.\n\n This is auto generated email', from: 'nmadshetty40@massmutual.com', subject: 'Test results Failed - Smart Test Automation Pipeline', to: 'nmadshetty40@massmutual.com'
        }
   
        unstable ("JOB UNSTABLE") {
            echo "Unstable Job"
        }

        aborted ("JOB ABORTED") {
            echo "Aborted Job"
        }
    }
}