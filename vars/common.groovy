def unitTests() {
    stage('Unit Tests') {
        echo 'OK'
    }
}

def integrationTests() {
    stage('Integration Tests') {
        echo 'OK'
    }
}

def codeQuality() {
    stage('Code Quality') {

        withCredentials([usernamePassword(credentialsId:'SONARQUBE', passwordVariable: 'SONAR_PASS', usernameVariable: 'SONAR_USER')]){
            sh 'sonar-scanner -Dsonar.host.url=http://sonarqube-internal.madhanmohanreddy.tech:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${service_name}  -Dsonar.qualitygate.wait=true'
        }

    }
}

def sast() {
    stage ('SAST') {
        echo 'OK'
    }
}

def sca() {
    stage ('SCA') {
        echo 'OK'
    }
}

def secretDetection() {
    stage ('SECRET DETECTION') {
//        sh 'trufflehog filesystem .'
  }
}

def artifactProduce() {
    stage ('Artifact Produce') {
        sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 512646826903.dkr.ecr.us-east-1.amazonaws.com'
        sh 'docker build -t  512646826903.dkr.ecr.us-east-1.amazonaws.com/${service_name}:${TAG_NAME} .'
        sh 'docker push -t  512646826903.dkr.ecr.us-east-1.amazonaws.com/${service_name}:${TAG_NAME} .'
    }
}

def codeCheckout() {
    stage('CodeCheckout') {

        sh "find ."
        sh "find . | sed -e '1d' |xargs rm -rf"
        if(env.TAG_NAME ==~ ".*") {
            env.branch_name = "refs/tags/${env.TAG_NAME}"
        } else {
            env.branch_name = "${env.BRANCH_NAME}"
        }
        checkout scmGit(
                branches: [[name: "${branch_name}"]],
                userRemoteConfigs: [[url: "https://github.com/MMR-46org/${service_name}"]]
        )
    }
}
