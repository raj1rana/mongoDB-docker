node{
    
    
    currentBuild.result = "SUCCESS"
  
      try {
            stage('Pull-msater') {
                 // pulling master from the repo
                   git 'https://github.com/raj1rana/mongoDB-docker.git'

                   }
               stage('pull-staging'){
                       //pulling staging from the repo
                    git branch: 'staging', url: 'https://github.com/raj1rana/mongoDB-docker.git'
                   }
               stage('deploy-staging') {
                     //deploy to staging server
                   sh 'rsync -avz  -e ssh --exclude .git /var/lib/jenkins/workspace/pipeline-test/  ubuntu@13.232.107.33:/home/ubuntu/Docker-files/'
                 }
               stage('deploy-production'){
                    //deploy to production server
                   sh 'rsync -avz -e ssh  --exclude .git  /var/lib/jenkins/workspace/pipeline-test/  ubuntu@13.232.107.33:/home/ubuntu/master'
                 }
               stage('mail fail/sucess'){
                    mail body: 'project build successful',
                     from: 'xxxx@yyyyy.com',
                     replyTo: 'xxxx@yyyy.com',
                     subject: 'project build successful',
                     to: 'yyyyy@yyyy.com'
                 }
  
       }
       
       catch (err) {

        currentBuild.result = "FAILURE"

            mail body: "project build error is here: ${env.BUILD_URL}" ,
            from: 'xxxx@yyyy.com',
            replyTo: 'yyyy@yyyy.com',
            subject: 'project build failed',
            to: 'zzzz@yyyyy.com'

        throw err
    }

}
    
