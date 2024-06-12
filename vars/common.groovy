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
        echo 'OK'
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
        echo 'OK'
    }
}

def artifactProduce() {
    stage ('Artifact Produce') {
        echo 'OK'
    }
}
