# chmod +x
# 删除 sh 文件中的特殊符号（line 1: $'\r': command not found）：sed -i 's/\r$//' nacos.service
#
# mvn versions:set -DnewVersion=0.0.1-SNAPSHOT
#
mvn versions:set -DnewVersion=0.0.1-RELEASE
