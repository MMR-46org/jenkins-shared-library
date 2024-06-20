def call () {
    node ('workstation') {

        common.codeCheckout()

        if (env.BRANCH_NAME == 'main') {
            echo "Nothing to do"
        }
        else if (env.BRANCH_NAME ==~ 'PR.*') {
            common.unitTests()
            common.integrationTests()
            common.codeQuality()
            common.sast()
            common.secretDetection()
        }
        else if(env.TAG_NAME ==~ '.*') {
            common.sast()
            common.sca()
            common.secretDetection()
            common.artifactProduce()

        }
        else if(env.BRANCH_NAME ==~ '.*') {
            common.unitTests()
            common.integrationTests()
            common.codeQuality()
        }
    }
}