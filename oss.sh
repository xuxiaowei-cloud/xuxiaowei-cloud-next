echo "根据要储存文件名的特点查找文件（排除.m2文件夹）"
for file in $(find . -path ./.m2 -prune -o -name *-*-*.pom -o -name *-*-*.jar -o -name *-*-*.*.asc); do
  if [ './.m2' == $file ]; then
    continue
  fi
  echo $file
  ./ossutil64 -c .ossutilconfig -u cp $file oss://private-xuxiaowei/$CI_SERVER_HOST/$CI_PROJECT_PATH/pipelines-$CI_PIPELINE_ID-jobs-$CI_JOB_ID-$CI_COMMIT_SHORT_SHA${file#*target}
done
