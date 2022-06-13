# 脚本

## Consul

- [下载](https://www.consul.io/downloads)

- 支持远程连接

```
-client 0.0.0.0
```

- 开发模式

```
-dev
```

- 服务

```
-server
```

- 启动UI

```
-ui
```

- 节点

```
-node=consul-1
```

- 绑定IP

```
-bind=192.168.5.4
```

- 开放端口
    - CentOS
        ```shell
        firewall-cmd --zone=public --add-port=8300/tcp --permanent
        firewall-cmd --zone=public --add-port=8500/tcp --permanent
        firewall-cmd --reload
        firewall-cmd --list-all
        ```
    - Ubuntu
        ```shell
        sudo ufw allow 8300
        sudo ufw allow 8500
        ```
