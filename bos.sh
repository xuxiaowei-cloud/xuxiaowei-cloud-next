mkdir .go-bcecli -p

echo "[Defaults]" > .go-bcecli/config
echo "Domain = $b_domain" >> .go-bcecli/config
echo "Region = " >> .go-bcecli/config
echo "AutoSwitchDomain =" >> .go-bcecli/config
echo "BreakpointFileExpiration = " >> .go-bcecli/config
echo "Https = yes" >> .go-bcecli/config
echo "MultiUploadThreadNum = " >> .go-bcecli/config
echo "SyncProcessingNum = " >> .go-bcecli/config
echo "MultiUploadPartSize = " >> .go-bcecli/config

echo "[Defaults]" > .go-bcecli/credentials
echo "Ak = $b_ak" >> .go-bcecli/credentials
echo "Sk = $b_sk" >> .go-bcecli/credentials
echo "Sts = " >> .go-bcecli/credentials

echo "根据要储存文件名的特点查找文件（排除.m2文件夹）"
for file in $(find . -path ./.m2 -prune -o -name *-*-*.pom -o -name *-*-*.jar -o -name *-*-*.*.asc); do
  if [ './.m2' == $file ]; then
    continue
  fi
  echo $file
  ./bcecmd --conf-path=./.go-bcecli bos cp $file bos://private-xuxiaowei/$CI_SERVER_HOST/$CI_PROJECT_PATH/pipelines-$CI_PIPELINE_ID-jobs-$CI_JOB_ID-$CI_COMMIT_SHORT_SHA${file#*target}
done
