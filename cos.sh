echo "cos:" > .cos.yaml
echo "  base:" >> .cos.yaml
echo "    secretid: $secretid" >> .cos.yaml
echo "    secretkey: $secretkey" >> .cos.yaml
echo "    sessiontoken: \"\"" >> .cos.yaml
echo "    protocol: https" >> .cos.yaml
echo "  buckets:" >> .cos.yaml
echo "    - name: $bucketname_appid" >> .cos.yaml
echo "      alias: $bucketname_appid" >> .cos.yaml
echo "      region: \"\"" >> .cos.yaml
echo "      endpoint: $cos_endpoint" >> .cos.yaml

echo "根据要储存文件名的特点查找文件（排除.m2文件夹）"
for file in $(find . -path ./.m2 -prune -o -name *-*-*.pom -o -name *-*-*.jar -o -name *-*-*.*.asc); do
  if [ './.m2' == $file ]; then
    continue
  fi
  echo $file
  ./coscli -c	./.cos.yaml cp $file cos://private-1255740549/$CI_SERVER_HOST/$CI_PROJECT_PATH/pipelines-$CI_PIPELINE_ID-jobs-$CI_JOB_ID-$CI_COMMIT_SHORT_SHA${file#*target}
done
