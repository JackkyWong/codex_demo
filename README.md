# Spring Boot 服务器监控示例

该项目提供一个基础的服务器资源监控能力，支持：

- CPU 利用率监测
- 内存利用率监测
- 硬盘利用率监测
- 超阈值告警（日志告警 + 告警记录接口）

## 配置项

在 `src/main/resources/application.properties` 中可配置：

- `monitor.threshold.cpu`：CPU 告警阈值（默认 80）
- `monitor.threshold.memory`：内存告警阈值（默认 85）
- `monitor.threshold.disk`：硬盘告警阈值（默认 90）
- `monitor.poll-interval-ms`：监测轮询间隔，毫秒（默认 10000）

## 接口

- `GET /api/monitor/metrics`
  - 返回当前 CPU、内存、硬盘利用率
- `GET /api/monitor/alerts`
  - 返回最近的告警记录（最多保留 100 条）

## 运行

```bash
mvn spring-boot:run
```

## 测试

```bash
mvn test
```
