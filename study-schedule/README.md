# Scheduled定时器的使用

### 【@scheduled定时器】技术点

> 1. @EnableScheduling 配置在启动类入口，用于开启定时功能
> 2. @Scheduled 用于配置具体某个任务的执行时间点，实现某个特定功能

### 方法上注解的一般用法

> - initialDelay表示一个初始延迟时间，第一次被调用前延迟的时间
>- fixedDelay表示一个固定延迟时间执行，上个任务完成后,延迟多久执行

````java
// 启动立即执行
// 每5秒执行一次
@Scheduled(initialDelay = 1000, fixedDelay = 5000)
````

> - Cron表达式用法：秒 分 时 日 月 周
>- 比如：
>-    	每5秒执行一次：*/5 * * * * *
>-    	每30分钟执行一次：* */30 * * * *
>-    	每1小时执行一次：* * */1 * * *
>-    	每天2点执行一次：* * 2 * * *

````java
// 固定时间才执行，即为10秒的整数倍执行，比如20秒，30秒，40秒时，会执行
// 每10秒执行一次
@Scheduled(cron = "*/10 * * * * *")
````

---
> 问题：这里你会发现，当有多个任务配置执行时，一直只有一个线程在跑（默认线程池是单个池）

> a. 方式一：通过实现SchedulingConfigurer接口，重写configureTasks方法，重新定义线程池

````java
public void configureTasks(ScheduledTaskRegistrar taskRegistrar){
        // 开启一个固定10个大小的线程池，也使用Executors下其他的线程池
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
        }
````

> b. 方式二：通过异步方式执行调度任务

````java
// 配置Application入口的@EnableAsync，在定时任务方法前面配置@Async，即配置了任务线程池 
// 启动立即执行 
// 每5秒执行一次 
@Async 
@Scheduled(initialDelay = 1000,fixedDelay = 5000)
public void testAsync(){
        logger.info("testAsync这里，每5秒执行一次");
        }
````

---

- 参考 [@Scheduled定时器用法和场景案例分析](https://blog.csdn.net/hemin1003/article/details/90454462)