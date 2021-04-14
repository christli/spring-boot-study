# 整合Quartz实现动态定时任务

> Quartz是一个定时任务的调度框架，涉及到的主要概念有以下几个：
>- `Scheduler`：调度器，所有的调度都由它控制，所有的任务都由它管理。
>- `Job`：任务，定义业务逻辑。
>- `JobDetail`：基于Job，进一步封装。其中关联一个Job，并为Job指定更详细的信息。
>- `Trigger`：触发器，可以指定给某个任务，指定任务的触发机制。
---

- 参考 [整合Quartz实现动态定时任务](https://javatip.cn/archives/123)

---

- cron参数报错
- Support for specifying both a day-of-week AND a day-of-month parameter is not implemented.
- 原因是星期和月份两个参数必须有一个是问号，如下即可
- `*/2 * * * * ?`