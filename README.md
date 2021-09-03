# Music Linker Backend

音乐链接器后端项目，提供移动端、Web 端后台服务

## 项目结构

| 模块 | 描述 |
| ---- | ---- |
| mlb_api | 移动端与 Web 端通用 API 模块 |
| mlb_auth | 认证与授权模块 |
| mlb_biz | 业务处理模块 |
| mlb_common | 业务无关基础通用代码模块 |
| mlb_dao | 仓库模块 |
| mlb_fs | 文件系统模块 |
| mlb_mobile | 移动端 API 模块 |
| mlb_mq | AMQP 模块 |
| mlb_task | 定时任务模块 |
| mlb_web | Web 端 API 模块 |
| script | 项目脚本 |

## 环境及版本

- JDK 11.0
- MariaDB 10.5
- Redis 6.2
- Kafka 2.12
- Minio 2021.06.09

## API 约定

- 所有请求默认为 POST JSON Body 方式
- API 返回 HTTP 状态码只为 `200`, 基础响应由 `code` `msg` `data` 字段构成，响应 `code` 字段决定响应状态
    - 大于 `0`，业务状态，如验证码不正确
    - 等于 `0`，默认成功
    - `-10000` ~ `-19999`，系统异常，如 HTTP 请求方法不正确
    - `-20000` ~ `-29999`，认证与授权异常，如 Token 失效
    - `-30000` ~ `-39999`，客户端异常，如接口请求频率过快
    - 其他保留
