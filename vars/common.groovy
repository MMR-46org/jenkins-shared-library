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
        sh 'trufflehog filesystem .'
    }
}

def artifactProduce() {
    stage ('Artifact Produce') {
        echo 'OK'
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
