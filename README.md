# Spring Cloud

## 初识Spring Cloud与微服务

* 在传统的软件架构中，我们通常采用的是单体应用来构建一个系统，一个单体应用糅合了各种业务模块。起初在业务规模不是很大的情况下，对于单体应用的开发维护也相对容易。但随着企业的发展，业务规模与日递增，单体应用变得愈发臃肿。由于单体应用将各种业务模块聚合在一起，并且部署在一个进程内，所以通常我们对其中一个业务模块的修改也必须将整个应用重新打包上线。为了解决单体应用变得庞大脯肿之后产生的难以维护的问题，微服务架构便出现在了大家的视线里

### 什么是微服务

* 微服务(`Microservices`)是一种软件架构风格，起源于Peter Rodgers博士于 2005 年度云端运算博览会提出的微 Web 服务 (Micro-Web-Service) 。微服务主旨是将一个原本独立的系统 拆分成多个小型服务，这些小型服务都在各自独立的进程中运行，服务之间通过基于HTTP的`RESTful API`进行通信协作。下图展示了单体应用和微服务之间的区别:

  ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125708123-547214004.png)

* 在微服务的架构下，单体应用的各个业务模块被拆分为一个单独的服务并部署在**单独的进程**里，每个服务都可以单独的部署和升级。这种去中心化的模式使得后期维护和开发变得更加灵活和方便，由于**各个服务单独部署**，所以可以使用不同的语句来开发各个业务服务模块

### 什么是Spring Cloud

* Spring Cloud是一种基于Spring Boot实现微服务架构的开发工具。它的微服务架构中涉及的配置管理、服务治理、断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理等操作提供了一种简单的开发方式。Spring Cloud的诞生并不是为了解决微服务中的某一个问题，而是提供了一套解决微服务架构实施的综合性解决方案
* Spring Cloud是一个由各个独立项目组成的综合项目，每个独立项目有着不同的发布节奏，为了管理每个版本的子项目清单，避免Spring Cloud的版本号与其子项目的版本号混淆，没有采用版本号的方式，而是通过命名的方式。这些版本的名字采用了伦敦地铁站的名字，根据字母表的顺序来对应版本时间顺序，比如“Angel”是Spring Cloud的第一个发行版名称，‘`Brixton`’是Spring Cloud的第二个发行版名称，当一个发行版本的Spring Cloud项目发布内容积累到临界点或者一个严重bug解决可用后，就会发布一个”service releases”版本，简称`SRX`版本，其中X是一个递增的数字，所以`Brixton.SR5`就是`Brixton`的第5个Release版本

### 上篇 Spring Boot 2.X版和Spring Cloud H版

![image-20200630171832774](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125708460-1064756082.png)

#### 版本选型

* spring boot 版本选型
  1. git源码地址
  2. spring boot 2.0 新特性（官网强烈建议升级到`2.x`版本）
  3. 官网看boot版本

* spring cloud版本选择
  1. git源码地址
  2. 官网
  3. 官网看Cloud版本 
     * 命名规则 采用了英国伦敦地铁站名称命名，并由地铁站字母A-Z依次类推的形式来发布迭代版本

* Spring cloud和Spring boot之间的依赖关系如何看

   1. ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125708966-1458271605.png)

   2. 更详细的版本查看信息

      * `https：//start.spring.io/actuator/info` 
      * 查看`json`串返回结果

      ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125709234-1627601794.png)

* 版本确定（截止2020.2.15）

  ````
  1.cloud :Hoxston.SR1
  2.boot :2.2.2.RELEASE
  3.cloud alibaba :2.1.0.RELEASE
  4.Java :Java8
  5.Maven : 3.5及以上
  6.MySql ：5.7及以上
  7.题外话：booot版本最新已经到2.2.4，为什么选择2.2.2？
  答：1. 只用boot 直接最新
     2. 同时使用boot和cloud 需照顾cloud 由Spring cloud来决定boot版本
  ````

####  关于Cloud 各种组件的停更/升级/替换

* 由停更引发的“升级惨案”

  1. 停更不停用
     * 被动修复bugs
     * 不再接受合并请求
     * 不再发布新版本

  2. 明细条目

     * 以前
     * now 2020

     ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125709541-870778646.png)

#### 搭建父工程

* Maven结构
  1. dependency Management :Maven 使用dependency Management 元素来提供了一种管理依赖版本号的方式。通常会在一个组织或者项目的最顶层的父`POM`中看到dependency Management元素。
  2. 使用`pom.xml`中的dependency Management元素能够让所有在子项目中引用的一个依赖而不用显式的列出版本号。Maven会沿着父子层次向上走，直到找到一个拥有dependency Management元素的项目，然后他就会使用这个dependency Management 元素中指定的版本号
  3. 这样做的好处就是：如果有对个子项目都引用同一个依赖，则可以避免在每个使用的子项目里都声明一个版本号，这样当想升级或者切换到另一个版本时，只需要在顶层父容器里更新，而不需要一个一个子项目的修改；另外如果某个子项目需要另外的版本，只需要声明version就可
  4. dependency Management 里只是声明依赖，并不实现引入，因此子项目需要显式的声明需要用的依赖。
  5. 如果不在子项目中声明依赖，是不会从父项目中继承下来的；只有在子项目中写了该依赖项，并且没有指定具体版本，才会从父项目中继承该项，并且version和scope都读取自父`pom`
  6. 如果子项目中指定了版本号，那么就会使用子项目指定的jar包

#### 微服务子模块构建

* 微服务模块
  1. 建module
  2. 改`POM`
  3. 写`YML`
  4. 主启动
  5. 业务类

* 微服务运行
  1. 通过修改idea的 `workspace.xml` 的方式来快速打开Run Dashboard窗口
  2. 开启Run Dashboard![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125709892-505210973.png)

* 热部署`Devtools`

  1. Adding `devtools` to your project  

     ````
     <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools -->
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
         <optional>true</optional>
     </dependency>
     ````

  2. Adding `plugin` to your `pom.xml`

     ````
     下一段配置黏贴到父工程当中的pom里
      
     <build>
       <plugins>
         <plugin>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-maven-plugin</artifactId>
           <configuration>
             <fork>true</fork>
             <addResources>true</addResources>
           </configuration>
         </plugin>
       </plugins>
     </build>
     ````

  3.  Enabling automatic build   ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125710162-1125739241.png)

  4. Update the value of    ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125710470-1920331324.png)

     

  5. 重启IDEA

* (**订单支付服务远程调用**)原始方法
  1. `RestTemplate`  提供了多种便捷访问远程`Http`服务的方法，是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集
  2. 使用`restTemplate`访问restful接口非常简单粗暴无脑，(`url、requestMap、ResponseBean.class`)这三个参数分别代表Rest请求地址、请求参数、HTTP响应转换被转换成的对象类型
  3. ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125710835-1574677263.png)

* 工程重构 
  1. 重复代码 冗余
  2. 建立公共模块

********

### 服务注册与发现

#### Eureka服务注册与发现

##### 是什么

* Eureka基础知识
  1. 什么是服务治理：Spring Cloud 封装了 `Netflix` 公司开发的Eureka模块来实现服务治理。
  2. 在传统的`rpc`远程调用框架中，管理每个服务与服务之间依赖关系比较复杂，管理比较复杂，所以需要使用服务治理，管理服务与服务之间依赖关系，可以实现服务调用、负载均衡、容错等，实现服务发现与注册。
* 什么是服务注册
  1. Eureka采用了CS的设计架构，Eureka Server作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，使用Eureka的客户端连接到Eureka server 并维持心跳连接，这样系统的维护人员就可以通过 Eureka Server 来监控系统中的各个微服务是否正常运行
  2. 在服务注册与发现中，有一个注册中心。当服务器启动的时候，会把当前自己的服务器的信息比如服务地址通讯等以别名的方式注册到注册中心上。另一方(消费者|服务提供者)，以该别名的方式去注册中心上获取到实际的服务通讯地址，然后在实现本地`RPC`调用。`RPC`远程调用框架**核心设计思想**：在于注册中心，因为使用注册中心管理每个服务与服务之间的一个依赖关系(服务治理概念)。在任何`rpc`远程框架中，都会有一个注册中心（存放服务地址相关信息（接口地址））
  3. ![image-20200611110621498](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125711106-207165697.png)

* Eureka的两个组件

  1. Eureka Server：提供服务注册服务

     各个微服务节点通过配置启动后，会在Eureka Server中进行注册，这样Eureka Server中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看到。

  2. Eureka Client

     是一个Java客户端，用于简化Eureka Server 的交互，客户端同时也具备一个内置的、使用轮询（round-robin）负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳（默认周期为30秒），如果Eureka server 在多个心跳周期内没有收到某个节点的心跳，Eureka Server 将会从服务注册表中把这个服务节点移除（默认90秒）

##### 怎么用

1. IDEA生成Eureka Server 端服务注册中心（类似物业公司）

   ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125711465-144424057.png)

   ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125711809-1710741902.png)

2. Eureka Client 端 `cloud-peovider-payment8001`将注册进Eureka Server成为服务提供者provider（类似学校提供授课服务）

   * `Eureka Client yml`配置 ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125712069-1682924649.png)

   * `Eureka Client`启动类 ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125712404-762012740.png)

     

3. Eureka Client 端 `cloud-consumer-order80`将注册进 Eureka Server 成为服务消费者consumer（类似上课消费的学生）

   * 如2的图

* 集群的Eureka原理说明
  1. 微服务`RPC`远程调用最核心是什么： **高可用**
  2. 解决办法：搭建Eureka注册中心集群，实现负载均衡＋故障容错
  3. 互相注册 相互守望

* 集群的Eureka的搭建步骤

  1. 修改映射配置 ：找到`C:\Windows\System32\drivers\etc`路径下的hosts文件

  2. 添加进hosts文件：

     1. `127.0.0.1     eureka7001`
     2. `127.0.0.1     eureka7002`

     

  3. 编写`yml`

     1. ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125712792-1691572812.png)
     2. ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125713192-1650375515.png)

  4. 启动类

* 将支付服务8001 发布到两台eureka集群配置中

  1. 其他配置不需要动

  2. 修改eureka配置的`defaultZone`

     ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125713475-1293129595.png)

* Eureka集群服务

  ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125713741-1449183109.png)

* 支付服务者提供者8001集群环境搭建

  1. ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125714067-1039890472.png)

  2. 复制8001服务为8002

  3. application name 一致![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125714397-1527124947.png)

  4. ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125714679-450037389.png)

  5. 80消费8001/8002  服务地址不能写死，在微服务中 统一调用application name

     ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125714923-1545025628.png)

  6. 使用@`LoadBalanced`
  7. 注解赋予`RestTemplate`**负载均衡**的能力 ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125715224-332124056.png)
  8. 测试结果
     1. 先要启动`EurekaServer`，7001/7002服务
     2. 再要启动服务提供者provider，8001/8002服务
     3. 负载均衡端口效果达到 8001/8002端口交替出现
     4. **Ribbon和Eureka整合后Consumer可以直接调用服务不用再关心地址和端口号，且该服务还有负载均衡功能**

* actuator微服务信息完善
  1. 主机名称：服务名称修改·
     1. `yml`   `eureka.instance.instance-id`
     2. ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125715523-1372487637.png)
  2. 访问信息有`IP`信息提示
     1. `yml`  `eureka,instance.prefer-ip-address:true`
     2. ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125715880-1520209721.png)

* Eureka服务发现Discovery
  1. 对于注册进eureka里面的微服务，可以通过服务发现来获得该服务的信息
  2. `controller    DiscoveryClient`
  3. Application启动类 添加 `@EnableDiscoveryClient`注解

* Eureka自我保护

  1. 概述：保护模式主要用于一组客户端和Eureka Server之间存在的网络分区场景下的保护，一旦进入保护模式，Eureka Server将会尝试保护去服务注册表中的信息，不再删除服务注册表中的数据，也就是**不会注销任何微服务**。
     1. 一句话：某时刻某一个微服务不可用了，Eureka不会立刻清理，依旧会对该微服务的信息进行保存。**属于CAP里面的AP分支**

  2. 为什么会产生Eureka自我保护机制
     1. 为了防止`EurekaClient`可以正常运行,但是与`EurekaServer`网络不通的情况下，`EurekaServer`不会立刻将`EurekaClient`服务剔除
     2. 什么是自我保护机制：当`EurekaServer`节点在短时间内丢失过多客户端时(可能发生了网络分区故障)，那么这个节点就会进入自我保护模式
     3. 在自我保护模式中，Eureka Server会保护服务注册表中的信息，不再注销任何服务实例。它的设计哲学就是宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例(好死不如赖活着)
     4. 综上，自我保护模式是一种应对网络异常的安全保护措施，它的架构哲学是宁可同时保留所有的微服务（健康的微服务和不健康的微服务都会保留）也不会盲目的注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮、稳定

  3. Eureka服务停更说明：停更不停用

#### Zookeeper服务注册与发现

##### 是什么

* `Zookeeper`概述
  * `Zookeeper`是一个开源的，分布式系统状态下的，多系统之间的协调调度、通知机制以达到一致性原则的一个服务功能架构
  * 管理者
* 设计模式来理解
  
  * 是一个基于**观察者模式**设计的分布式服务管理框架，它负责存储和管理大家都关心的数据，然后接受观察者的注册，一旦这些数据的状态发生变化，`Zookeeper`就将负责通知已经在`Zookeeper`上注册的那些观察者做出相应的反应，从而实现集群中类似的Master/Slave管理模式。
* 一句话理解
  
* **文件系统＋通知机制**
  
* `Zookeeper`安装

  1. 官网下载
  2. 解压 tar `-zxvf`
  3. `conf`文件夹中 `zoo_sample.cfg`参数解读
     * **`tickTime` :通信心跳数**，`Zookeeper`服务器心跳时间，单位毫秒。用于心跳机制，并且设置最小的session超时时间为两倍心跳时间（session的最小超时时间为2*`tickTime`）
     * **`initLimit`：LF初识通信时限（follower和leader）**：集群中follower跟随者服务器（F）与leader领导者服务器（L)之间初始连接时能容忍的最多心跳数(`tickTime`的数量)，用它来限定集群中的`Zookeeper`服务器连接到`Leader`的时限
     * **`syncLimit`：LF同步通信时限**：集群中Leader与Follower之间的最大响应时间单位，假如响应超过`syncLimit`*`tickTime`，Leader认为Follower死掉，从服务器列表中删除Follower
     * `dataDir`：实际存储位置
     * `clientPort`：默认端口号

  4. 开启服务＋客户端连接

     * 启动 `./zkServer.sh start`

     * 关闭  .`/zkServer.sh stop`

     * 客户端连接 `./zkCli.sh`

     * 退出 quit

     * 查看服务是否启动：  `ps -ef|grep zookeeper|grep -v grep`

     * 创建＋删除节点：`create  /testNode v1`   

       ​							 `delete /testNode`

       ​							 `set /testNode v2`

       ​							 `get /testnode`

  5. 查看＋获取`zookeeper`服务器上的数据存储信息

     * 文件系统：**`Zookeeper`维护一个类似文件系统的数据结构**，所使用的的数据模型风格很像文件系统的目录树结构，简单来说，有点类似windows中注册表的结构，**有名称，有树节点，有Key/Value 的关系**，可以看做一个树形结构的数据库，分布在不同的机器上做名称管理

     * 初识`znode`节点：`Zookeeper`数据模型的结构与Unix文件系统很类似，整体上可以看做是一棵树，每个节点称作一个`Znode`，很显然`zookeeper`集群自身维护了一套数据结构，这个存储结构是一个树形结构，其上每一个节点，我们称之为`“znode”`，每一个`znode`默认能够存储`1MB`的数据，每个`znode`都可以通过其路径唯一标识。

     * 数据模型/`znode`节点深入：

       1. `Znode`的数据模型

          * 是什么 **(/stat)**：`zookeeper`内部维护了一套类型UNIX的树形数据结构，由`znode`构成的集合，`znode`的集合又是一个树形结构，每一个`znode`又有很多属性进行描述。`Znode=path+nodeValue+Stat`
          * `zookeeper`的Stat结构体
            1. `czxid`:引起这个`znode`创建的`zxid`，创建节点的事务的`zxid`(`Zookeeper` Transaction Id)
            2. `ctime`:znode被创建的毫秒数(从1970年开始)
            3. `mzxid：znode`最后更新的`zxid`
            4. `mtime:znode`最后修改的毫秒数(从1970年开始)
            5. `pZxid：znode`最后更新的子节点`zxid`
            6. `cversion：znode`子节点变化号，`znode`子节点修改次数
            7. `dataversion：znode`数据变化号
            8. `aclVersion:znode`访问控制列表的变化号
            9. `ephemeralOwner`:如果是临时节点，这个是`znode`拥有者的session id，如果不是临时节点则是0
            10. `dataLength:znode`的数据长度
            11. `numChildren:znode`子节点数量

       2. `znode`的存在类型

           * 持久

             1. PERSISTENT：持久化目录节点   客户端与`zookeeper`断开连接后，该节点依旧存在
             2. PERSISTENT_SEQUENTIAL：持久化顺序编号目录节点   客户端与`zookeeper`断开连接后，该节点依旧存在，只是`Zookeeper`给该节点名称进行顺序编号

           * 临时

             3. EPHEMERAL：临时目录节点   客户端与`zookeeper`断开连接后，该节点被删除

             4. EPHEMERAL_SEQUENTIAL:临时顺序编号目录节点  客户端与`zookeeper`断开连接后，该节点被删除，只有`Zookeeper`给该节点名称进行顺序编号

          * create  /name        create -e   /name        create   -s   /name      create -e  -s   /name                          -s:代表序列号   -e:代表临时 什么都不加代表p:持久化

* 典型应用场景（能干嘛）
1. 命名服务
  2. 配置维护
3. 集群管理
  4. 分布式消息同步和协调机制
5. 负载均衡
  6. 对`Dubbo`的支持
7. 备注：**`ZooKeeper`提供了一套很好的分布式集群管理的机制，就是它这种基于层次型的目录树的数据结构，并对树中节点进行有效管理。从而可以设计出多种多样的分布式的数据管理模型，作为分布式系统的沟通调度桥梁**

* 实际运用（怎么玩）
  1. 统一命名服务（Name Service如`Dubbo`服务注册中心）
  2. 配置管理（Configuration Management如淘宝开源配置框架Diamond）
  3. 集群管理（Group Membership如`Hadoop`分布式集群管理）
  4. Java操作`API`

* 基础命令和Java客户端操作

  * `zkCli`的常用命令操作

    1. 和`redis`的`KV`键值对类似，只不过key变成了一个路径节点值，v就是data。 `Zookeeper`**表现为一个分层的文件系统目录树结构**，不同于文件系统之处在于：`zk`节点可以有自己的数据，而`unix`文件系统中的目录节点只有子节点。  **一个节点对应一个应用，节点存储的数据就是应用需要的配置信息**
    2. 常用命令：
       1. help
       2. ls
       3. `ls2`: 是否有结构体 stat的内容
       4. stat
       5. set
       6. get
       7. create
       8. delete
       9. `rmr`

  * 四字命令

    * 是什么
      1. `zookeeper`支持某些特定的四字命令，他们大多是用来查询`ZK`服务的当前状态及相关信息的，通过telnet或`nc`向`zookeeper`提交相应的命令，如`echo | ruok | nc 127.0.0.1 2181`    得到结果 `imok` 启动正常
      2. 运行公式：echo 四字命令 | `nc` 主机`IP` `zookeeper` 端口

    * 常用命令
      	1. `ruok`：测试服务是否处于正确状态，如果确实如此，返回`imok`，否则不做任何响应
       	2. stat：输出关于性能和连接的客户端列表
       	3. `conf`：输出相关服务配置的详细信息
       	4. cons：列出所有连接到服务器的客户端的完全的连接/会话的详细信息。包括接受/发送的包数量、
       	5. dump：列出未经处理的会话个临时节点
       	6. `envi`：输出关于服务环境的详细信息（区别于`conf`命令）
       	7. `reqs`：列出未经处理的请求
       	8. `wchs`：列出服务器watch的详细信息
       	9. `wchc`：通过session列出服务器watch的详细系信息，它的输出是一个与watch相关的会话的列表
       	10. `wchp`：通过路径列出服务器watch的详细信息，它输出一个与session相关的路径

  * `java`客户端操作

    * `zkclient zookeeper`jar包导入

* 通知机制

  * session是什么

  * watch

    1. 通知机制：客户端注册监听它关心的目录节点，当目录节点发生变化（数据改变、被删除、子目录节点增加删除）时，`zookeeper`会通知客户端
    2. 是什么
       * 观察者的功能：
         1. `ZooKeeper`支持watch（观察）的概念。客户端可以在每个`znode`节点上设置一个观察。如果被观察服务端的`znode`变更，那么watch就会被触发，这个watch所属的客户端将接收到一个通知包被告知节点已经发生变化，把相应的时间通知给设置watch的Client端
         2. `ZooKeeper`里的所有读取操作：`getData`（）、`getChildren`()和exists()都有设置watch的选项
       * 一句话：异步＋回调+触发机制
    3. watch事件理解：
       1. 一次触发(one-time trigger):
          * 当数据发生变化时，`zkserver`向客户端发送一个watch，它是一次性的动作，即触发一次就不再有效，类似一次性纸杯。
          * 只监控一次
          * 如果想继续watch的话，需要客户端重新设置watcher，因此如果你得到一个watch事件且想在将来的变化得到通知，必须重新设置另一个watch
       2. 发送客户端（send to client）：Watchers是异步发往客户端的，`ZooKeeper`提供一个顺序保证：在看到watch事件之前绝不会看到变化，这样不同客户端看待的是一致性的顺序。
       3. 为数据设置watch:节点有不同的改变方式，可以认为`ZooKeeper`维护两个观察列表：**数据观察和子节点观察**。`getData`、exists设置数据观察，`getChildren`设置子节点观察。**`setData`将为`znode`触发数据观察。成功的create将为创建节点触发数据观察，为其父节点触发子节点观察。delete触发节点的数据观察以及子节点观察，为其父节点触发子节点观察**
       4. 时序性和一致性
       5. 变化备注
          * data watches and child watches
          * watch之数据变化
          * watch之节点变化

    4. code
       * 一次性 one-time trigger -------`WatchOne`
       * 多次（命名服务------`WatchMore`
       * 子节点----`WatchChild`

* `ZooKeeper`集群

  * 是什么

    * `ZooKeeper`分为2部分：服务器端和客户端，客户端只连接到整个`ZooKeeper`服务的某个服务器上。客户端使用并维护一个TCP连接，通过这个连接发送请求、接受响应、获取观察的事件以及发送心跳。如果这个TCP连接中断，客户端将尝试连接到另外一个`ZooKeeper`服务器。客户端第一次连接到`ZooKeeper`服务时，接受这个连接的`ZooKeeper`服务器会为这个客户端建立一个会话。当这个客户端连接到另外的服务器时，这个会话会被新的服务器重新建立。

  * 伪分布式单机配置

    * 说明

      * 服务器名称与地址：集群信息（服务器编号，服务器地址，`LF`通信端口，选举端口）
      * 这个配置项的书写格式比较特殊，规则如下：`server.N=YYY:A:B` ,其中
      * N表示服务器编号，
      * `YYY`表示服务器的`IP`地址，
      * A为`LF`通信端口，表示该服务器与集群中的leader交换的信息端口(leader follower/master slave 主从通信端口)
      * B为选举端口，表示选举新Leader时服务器间相互通信的端口（当leader挂掉时，其余服务器会相互通信，选择出新的leader）
      * 一般来说，集群中每个服务器的A端口都是一样，每个服务器的B端口也是一样。下面是一个集群的例子：
        * `server.0=233.34.9.144:2008:6008`
        * `server.1=233.34.9.145:2008:6008`
        * `server.2=233.34.9.146:2008:6008`
        * `server.3=233.34.9.147:2008:6008`

      * 但是当所采用的的为集群时，`IP`地址都是一样的，只能是A端口和B端口不一样：
        * `server.0=127.0.0.1:2008:6008`
        * `server.1=127.0.0.1:2007:6007`
        * `server.2=127.0.0.1:2006:6006`
        * `server.3=127.0.0.1:2005:6005`

    * 配置步骤
      1. `zookeeper.tar.gz`解压后拷贝到`/zookeeper`目录下并重命名为`zk01`，在复制`zk01`形成`zk02`、`zk03`
      2. 进入`zk01/2/3` 分别新建文件夹 `mydata`、`mylog`
      3. 分别进入`zk01/2/3`各自的`conf` 新增`zoo.cfg`
      4. 编辑`zoo.cfg`
      5. 在各自`mydata`下面创建`myid`的文件，在里面写入server的数字
      6. 分别启动三个服务器
      7. `zkCli`连接server，带参数指定 -server：`./zkCli.sh  -server 127.0.0.1:2191`

##### SpringCloud整合ZooKeeper代替Eureka

* 注册中心`ZooKeeper`
  1. `ZooKeeper`是一个分布式协调工具，可以实现注册中心功能
  2. 关闭Linux服务器防火墙后启动`ZooKeeper`服务器
  3. `ZooKeeper`服务器取代Eureka服务器，`ZK`作为服务注册中心

* 服务提供者
  * `cloud-provider-payment8004`
  * 服务节点是临时性的  心跳检测机制：一定时间内为收到心跳包 就移除。
  * CAP:一致性（Consistency）、高可用（Availability）、分区容错性（Partition tolerance）。CAP 原则指的是：这三个要素最多只能同时实现两点，不可能三者兼顾
* 服务消费者
  * `cloud-consumerzk-order80`

#### Consul服务注册与发现

##### 是什么

* Consul是`HashiCorp`公司推出的开源工具，用于实现分布式系统的服务发现与配置。Consul是分布式的、高可用的、 可横向扩展的。它具备以下特性:
  - 服务发现: Consul提供了通过`DNS`或者HTTP接口的方式来注册服务和发现服务。一些外部的服务通过Consul很容易的找到它所依赖的服务。
  - 健康检测: Consul的Client提供了健康检查的机制，可以通过用来避免流量被转发到有故障的服务上。
  - Key/Value存储: 应用程序可以根据自己的需要使用Consul提供的Key/Value存储。 Consul提供了简单易用的HTTP接口，结合其他工具可以实现动态配置、功能标记、领袖选举等等功能。
  - 多数据中心: Consul支持开箱即用的多数据中心. 这意味着用户不需要担心需要建立额外的抽象层让业务扩展到多个区域。
* 下载安装并运行
  * 具体方法官网都有
  * win：进入`consul.exe`所在目录--->`cmd`--->consul --version (查看版本信息) --->`consul agent -dev` 启动
  * http://localhost:8500  访问页面

##### 怎么用

* 服务提供者
  * `cloud-providerconsul-payment8006`
* 服务消费者
  * `cloud-consumerconsul-order80`

##### 三个注册中心的异同点（Eureka、ZooKeeper、Consul）

* | 组件名    | 语言 | CAP  | 服务健康检测 | 对外暴露接口 | `SpringCloud`集成 |
  | --------- | ---- | ---- | ------------ | ------------ | ----------------- |
  | Eureka    | Java | AP   | 可配支持     | HTTP         | 已集成            |
  | ZooKeeper | Java | CP   | 支持         | 客户端       | 已集成            |
  | Consul    | Go   | CP   | 支持         | HTTP/DNS     | 已集成            |

* ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125716191-1806837684.png)
* CAP
  * `C:Consistency`(强一致性)
  * `A:Availability`(可用性)
  * `P:Partition tolerance`(分区容错性)
  * CAP理论关注粒度是数据，而不是整体系统设计的
* 经典CAP图
  * AP(Eureka)
  * CP(`Zookeeper`/Consul)

#### Ribbon负载均衡服务调用

##### 概述

* Spring Cloud Ribbon是基于`NetFlix` Ribbon实现的一套**客户端**负载均衡的工具。简单地说，Ribbon是`NetFlix`发布的开源项目，主要功能是提供**客户端的软件负载均衡算法和服务调用**。Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单的说，就是在配置文件中列出Load Balancer(简称LB)后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器。我们很容易使用Ribbon实现自定义的负责均衡算法。

##### 能干嘛

* LB(Load Balance)负责均衡:将用户的请求平摊的分配到多个服务器上，从而达到系统的HA(高可用)，常见的负载均衡有软件`Nginx`、`LVS`硬件`F5`等
  * 集中式LB
    * 即在服务的消费方和提供方之间是有独立的LB设施(可以是硬件，如`F5`,也可以是软件，如`nginx`)，由该设施负责把访问请求通过某种策略转发至服务的提供方
  * 进程式LB
    * 将LB逻辑集成到消费方，消费方从服务注册中心获知到有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。
    * Ribbon就属于进程内的LB，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址。

* Ribbon本地负责均衡客户端 vs `Nginx`服务端负载均衡区别
  * `Nginx`是服务器负载均衡，客户端所有请求都会交给`Nginx`，然后由`nginx`实现转发请求。即负载均衡是由服务端实现的
  * Ribbon本地负载均衡，在调用微服务接口时，会在注册中心上获取注册信息服务列表之后缓存到`JVM`本地，从而在本地实现`RPC`远程服务调用技术。
  * **一句话总结：负载均衡＋`RestTemplate`调用**

##### Ribbon负责均演示

* 架构说明
  * 总结：Ribbon其实就是一个软负载均衡的客户端组件，它可以和其他需要请求的客户端结合使用，和eureka结合只是其中的一个实例。
  * ![](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125716580-780527875.png)
  * Ribbon在工作时分成两步
    * 第一步先选择`EurekaServer`，它优先选择在同一个区域内负载较少的server
    * 第二步再根据用户指定的策略，再从server取到的服务注册列表中选择一个地址
    * 其中Ribbon提供了多种策略：比如轮询、随机和根据响应时间加权
  * Eureka中的 `spring-cloud-stater-netflix-eureka-client` 引入了Ribbon

* 二说`RestTemplate`的使用
  * `getForObject`方法/`getForEntity`方法
    * `getForObject`方法：返回对象为响应体中数据转化成的对象，基本可以理解为`Json`
    * `getForEntity`方法：返回对象为`ResponseEntity`对象，包含了响应中的一些重要信息，比如响应头、响应状态吗、响应体等。
  * `postForObject`/`postForEntity`
  * GET请求方法
  * POST请求方法

##### Ribbon核心组件IRule

* `IRule`：根据特定算法从服务列表中选取一个要访问的服务
  1. `com.netflix.loadbalancer.RoundRobinRule`  轮询
  2. `com.netflix.loadbalancer.RandomRule`     随机
  3. `com.netflix.loadbalancer.RetryRule`   先按照`RoundRobinRule`的策略获取服务，如果获取服务失败则在指定时间内会进行重试
  4. `WeightedResponseTimeRule` 对`RoundRobinRule`的扩展，响应速度越快的实例选择权重越大，越容易被选择
  5. `BestAvailableRule`  会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
  6. `AvailabilityFilteringRule` 先过滤掉故障实例，再选择并发较小的实例
  7. `ZoneAvoidanceRule`      默认规则，复合判断server所在区域的性能和server的可用性选择服务器
* 如何替换负载均衡策略
  * 修改`cloud-consumer-order80`
  * **配置细节**：官方文档明确给出了警告：这个自定义配置类不能放在@`ComponentScan`所扫描的**当前包下以及子包下**，否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享，达不到特殊定制化的目的了。
  * 新建package 不能跟主启动类所在包同包以及其子包
  * 新建自定义规则的配置类
  * 主启动类添加@`RibbonClient`注解，指定服务名称以及自定义规则类

##### Ribbon负责均衡算法剖析

* 原理
  * 负载均衡算法（轮询）：rest接口第几次请求数 % 服务器集群总数量 =实际调用服务器位置下标，每次服务重启后，rest接口计数从1开始
* 源码
  * 继承了`AbstractLoadBalancerRule`抽象类
* **手写**
  * 原理+`JUC`（`CAS`＋自旋锁）
    1. 7001,7002集群启动
    2. 8001/8002微服务改造
    3. 80订单微服务改造

#### OpenFeign服务

##### 概述

* `OpenFeign`是什么

  * `Fegin`是一个声明式Web Service客户端。使用Feign能让编写Web Service客户端更简单。它的使用方法是定义一个服务接口然后在上面添加注解。`Fegin`也支持可拔插式的编码器和解码器。Spring Cloud对`Fegin`进行了封装，使其支持了`Spring MVC`标准注解和`HttpMessageConverters`。`Fegin`可以与Eureka和Ribbon组合使用以支持负载均衡

* 能干嘛

  * `Fegin`旨在使编写Java `Http`客户端变得更容易
  * 前面在使用Ribbon+`RestTemplate`时，利用RestTemplate对http请求的封装处理，形成了一套模板化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，**往往接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用**。所以，Fegin在此基础上做了一进一步封装，由他来帮助我们定义和实现依赖服务接口的定义。在Fegin的实现下，**我们只需要创建一个接口并使用注解的方式来配置它(以前是Dao接口上面标注Mapper注解，现在是一个微服务接口上面标注一个Fegin注解即可)**，即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。
  * Fegin集成了Ribbon
    * 利用Ribbon维护了Payment的服务列表信息，并且通过轮询的方式实现了客户端的负载均衡。而与Ribbon不同的是，**通过Fegin只需要定义服务绑定接口且以声明式的方法**，优雅而简单的实现了服务调用

* Fegin与OpenFegin两者区别

  ![image-20200630155132616](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125716971-1182074175.png)

##### OpenFegin使用步骤

* 创建一个接口并使用注解的方式来配置它：微服务调用接口＋注解（@FeignClient）

* 代替原有的`Ribbon＋RestTemplate`的服务调用模式

* ![image-20200630155613865](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125717335-825224476.png)

* ![image-20200630161509554](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125717642-1851856779.png)

* ![image-20200630162339468](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125718011-1414187556.png)

  

##### OpenFegin超时控制

* 超时设置，故意设置超时演示出错情况
   	1. 服务提供方8001，故意写暂停程序
      	2. 服务消费方80添加超时方法`PaymentFeignService`
* OpenFeign客户端默认等待一秒，但是服务器端处理需要超过1秒钟，导致OpenFeign客户端不想等待了，直接返回报错。为了避免这样的情况，有时候我们需要设置feign客户端的超时控制。
* OpenFeign默认支持Ribbon
  * ![image-20200630165431528](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125718429-731486502.png)
  * yml文件中开启配置![image-20200630165454207](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125718849-788661848.png)

##### OpenFegin日志打印功能

* 日志级别
  1. NONE：磨人的，不显示任何日志
  2. BASIC：仅记录请求方法、URL、响应状态码及执行时间
  3. HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息
  4. FULL:除了HEADERS中定义的信息之外，还有请求和相应的正文及元数据

* 配置日志Bean

  ![image-20200630171635924](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125719150-31450947.png)

* yml文件开启日志

  ![image-20200630171725365](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125719429-1506481603.png)

  ****

### 服务降级

#### Hystrix断路器

##### 概述

* **分布式系统面临的问题**
  * 复杂分布式体系结构中的应用程序有数十个依赖关系，每个依赖关系在某些时候将不可避免的失败
  * **服务雪崩**：多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C,微服务B和微服务C又调用其他的微服务，这就是所谓的“**扇出**”。如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，所谓的“雪崩效应”。
  * 对于高流量的应用来说，单一的后端依赖可能会**导致所有服务器上的所有资源都在几秒钟内饱和**。比失败更糟糕的是，这些应用程序还可能导致服务之间的延迟增加，备份队列，线程和其它系统资源紧张，导致整个系统发生更多的级联故障。这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系的失败，不能取消整个应用程序或系统。
  * 所以，通常当你发现一个模块下的某个实例失败后，这时候这个模块依然还会接收流量，然后这个有问题的模块还需要调用其他模块，这样就会发生**级联故障**，或者叫做**雪崩**。
* 是什么
  * Hystrix是一个用于处理分布式系统的**延迟**和**容错**的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，**不会导致整个服务失败，避免级联故障，以提高分布式系统的弹性**
  * “断路器”本身是一种开关装置，当某个服务单元发生故障后，通过断路器的故障监控（类似熔断保险丝），向调用方**返回一个符合预期的、可处理的备选响应（FallBack）**，而不是长时间的等待或者抛出调用方无法处理的异常，这样就**保证了服务调用方的线程不会被长时间、不必要的占用**，从而避免了故障在分布式系统中的蔓延，乃至雪崩。
* 能干嘛
  * 服务降级
  * 服务熔断
  * 接近实时的监控
  * 。。。。。。（限流、隔离）
* Hystrix官宣，停更进维

##### Hystrix重要概念

* **服务降级**--fallback(备选响应)
  * 服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback
  * 哪些情况会触发降级：
    1. 程序运行异常
    2. 超时
    3. 服务熔断触发降级
    4. 线程池/信号量打满也会导致服务降级
* **服务熔断**--break
  * 类比保险丝达到最大服务访问后，**直接拒绝访问**，拉闸限电，然后**调用服务降级的方法并返回友好提示**
  * 就是保险丝：服务的降级---进而熔断---恢复调用链路
* **服务限流**--flowlimit
  * 秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行

##### Hystrix案例

* 新建`cloud-provider-hystrix-payment8001`
* 高并发测试 jemeter
  * 开启Jmeter，来20000个并发压死8001，20000个请求都去访问paymentInfo_timeout服务
  * 演示结果：出现卡顿，tomcat的默认工作线程数被打满了，没有多余的线程来分解压力和处理
* 压测结论
  
* 上面还是服务提供者8001自己测试，假如此时外部的消费者80也来访问，那消费者只能干等，最终导致消费端80不满意，服务端8001直接被拖死
  
* 看热闹不嫌事大，80新建加入
  * `cloud-consumer-feign-hystrix-order80`
  * 继续压测 8001自测以及80调用服务

* 故障现象和导致原因
  * 8001同一层次的其他接口服务被困死，因为tomcat线程里面的工作线程已经被挤占完毕
  * 80此时调用8001，客户端访问响应缓慢，转圈圈

* 结论：正因为有上述故障或不佳表现，才有我们的降级/容错/限流等技术诞生
* **如何解决？解决的要求**
  * 超时导致服务器变慢(转圈)----**超时不再等待**
  * 出错（宕机或程序运行出错）----**出错要有兜底**
  * 解决
    * 对方服务（8001）超时了，调用者（80）不能一直卡死等待，必须有服务降级
    * 对方服务（8001）宕机了，调用者（80）不能一直卡死等待，必须有服务降级
    * 对方服务（8001）OK,调用者（80）自己出故障或有自我要求（自己的等待时间小于服务提供者）自己处理降级

* 服务降级

  * 降级的配置 `@HystrixCommand`注解
  * 8001先从自身找问题：设置自身调用的超时时间峰值，峰值内可以正常运行，超过l需要有兜底的方法处理，作服务降级`fallback`
  * 8001 `fallback`
    * 业务类启用 
    * ![image-20200701151704152](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125719779-656861506.png)
    * 主启动类激活 添加新注解 `@EnableCircuitBreaker`
  * 80`fallback`
    * 80订单微服务，也可以更好的保护自己，自己也依样画葫芦进行客户端降级保护

  * 目前问题：
    * 每个业务方法对应一个兜底的方法，代码膨胀、冗余
    * 统一和自定义的分开

  * 待解决问题
    * **每个方法配置一个？？？代码冗余**
      	1. 注解`@DefaultProperties(defaultFallback = "通用处理方法名")`
       	2. 除了特别重要的核心业务有专属，其他普通的可以通过`@DefaultProperties（defaultFallback =""）`统一跳转到统一处理结果页面
       	3. 通用的和独享的各自分开，避免了代码膨胀，合理减少了代码量
    * **和业务逻辑混合在一起？？？乱！**
      * 服务降级，客户端去调用服务端，碰上服务端宕机或关闭
      * 本次案例服务降级处理是在客户端80实现的，与服务端8001没有关系，只需要为Feign客户端定义的接口添加一个服务降级处理的实现类即可实现解耦
      * 未来我们要面对的异常：运行超时、宕机
      * 根据`cloud-consumer-feign-hystrix-order80`已经有的`PaymentHystrixService`接口，重新新建一个类（`PaymentFallbackServiceImpl`）实现该接口，统一为接口里面的方法进行异常处理
        1. ![image-20200702103754640](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125720230-1182800040.png)`yml`文件
        2. `PaymentHystrixService`接口，指定降级处理类。![image-20200702104015075](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125720544-2064363763.png)
        3. ![image-20200702104041008](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125720887-1216175600.png)
        4. 客户端自己的提升：此时服务端provider已经down了，但是我们做了服务降级处理，让客户端在服务端不可用时也会获得提示信息而不会挂起耗死服务器

* 服务熔断

  * 断路器

  * 熔断是什么

    * 熔断机制概述：熔断机制是**应对雪崩效应的一种微服务链路保护机制**，当扇出链路的某个微服务出错不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，快速返回错误的响应信息。**当检测到该节点微服务调用响应正常后，恢复调用链路**
    * 在Spring Cloud框架里，熔断机制通过`Hystrix`实现，`Hystrix`会监控微服务间的调用状况，当失败的调用到一定阈值，缺省是**5秒内20次调用失败，就会启动熔断机制**，熔断机制的注解是`@HysrixCommand`

  * 实操

    * 修改`cloud-provider-hystrix-payment8001`
    * service接口![image-20200702134348785](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125721174-327304109.png)
    * 重点测试：**多次错误,然后慢慢正确，发现刚开始不满足条件，就算是正确的访问地址也不能进行访问，需要慢慢的恢复链路**

  * 原理（小总结）

    * 大神理论：![image-20200702135519532](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125721684-835636334.png)
    * 熔断类型：
      * 熔断打开：请求不再进行调用当前服务，内部设置时钟一般为`MTTR`（平均故障处理时间），当打开时长达到所设时钟则进入半熔断状态
      * 熔断关闭：熔断关闭不会对服务进行熔断
      * 熔断半开：部分请求根据规则调用当前服务，如果请求成功并且符合规则则认为当前服务恢复正常，关闭熔断

    * 官网断路器流程

      * 流程图![image-20200702141156481](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125721987-1610345951.png)

      * 官网步骤

        ![image-20200702142710746](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125722224-1175436502.png)

        ![image-20200702142615834](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125722452-234878684.png)

      * 断路器在什么情况下开始起作用![image-20200702142541769](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125722794-1851976966.png)

      * **断路器开启或者关闭的条件**

        1. 当请求满足一定阈值的时候（默认10秒内超过20个请求次数）
        2. 并且失败率达到一定值的时候（默认10秒内超过50%的请求失败）
        3. 到达以上阈值，断路器将会开启
        4. 当开启的时候，所有请求都不会进行转发
        5. 一段时间之后（默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行转发。如果成功，断路器会关闭，若失败，继续开启。重复4和5

      * 断路器打开之后

        * 打开之后，再有请求调用的时候，将不会调用主逻辑，而是直接调用降级fallback，通过断路器，实现了自动地发现错误并将降级逻辑切换为主逻辑，减少响应延迟的效果。
        * 原来的主逻辑要如何恢复呢？ -----对于这一问题，hystrix也为我们实现了**自动恢复功能**。当断路器打开，对主逻辑进行熔断之后，hystrix会启动一个休眠的时间窗，在这个时间窗内，降级逻辑是临时的成为主逻辑，当休眠时间窗到期，断路器将进入半开状态，释放一次请求到原来的主逻辑上，如果此次请求正常返回，那么断路器将继续闭合，主逻辑恢复，如果这次请求依然有问题，断路器继续进入打开状态，休眠时间窗重新计时。

      * ALL配置![image-20200702144350153](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125723266-2005831307.png)

        ![image-20200702144409967](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125723626-758574030.png)

        ![image-20200702144422464](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125724060-1575216507.png)

        ![image-20200702144430468](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125724590-602939391.png)

* 服务限流

  * 后面讲解alibaba的Sentinel说明

##### Hystrix工作流程

* https://github.com/Netflix/Hystrix/wiki/How-it-Works
* hystrix工作流程![image-20200702144856852](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125725017-1771504818.png)

##### 服务监控HystrixDashboard

* 概述
  * ![image-20200702145948989](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125725330-584045483.png)
* 仪表盘9001
  * 新建`cloud-consumer-hystrix-dashboard9001`
  * POM
  * YML
  * `HystrixDashboardMain9001`+新注解`@EnableHystrixDashboard`
  * 所有Provider微服务提供类（8001/8002/8003）都需要监控依赖配置
  * 启动`cloud-consumer-hystrix-dashboard9001`该微服务后续将监控微服务8001
* 断路器演示
  * 修改`cloud-provider-hystrix-payment8001`
    * 注意图形化展示：spring-boot-starter-actuator（必须有）
    * 注意：新版本`Hystrix`需要在主启动类`MainAppHystrix8001`中指定监控路径![image-20200702161223291](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125725648-1613466333.png)
    * 填写监控地址：http://localhost:8001/hystrix.stream
    * ![image-20200702161352327](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125725948-588748120.png)
    * 先访问正确地址，再访问错误地址，再正确地址，会发现图示断路器都是慢慢放开的（closed---->open 断路器打开     open--->close 断路器关闭）
* 如何看监控界面：
  * 7色：每种颜色对应不同的故障
  * 1圈:实心圈。共有两种含义，它通过颜色的变化代表了实例的健康程度，它的健康度从 绿色<黄色<橙色<红色递减。该实心圈除了颜色的变化之外，它的大小也会根据实例的请求流量发生变化，流量越大该实心圆就越大。所以通过该实心圆的展示，就可以在大量的实例中快速的发现**故障实例和高压力实例**
  * 1线：曲线。用来记录2分钟内流量的相对变化，才可以通过它来观察到流量的上升和下降的趋势。
  * 整个图的说明：
    * ![image-20200702162628363](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125726229-1704687012.png)
    * ![image-20200702162653484](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125726572-928478143.png)
    * ![image-20200702162909066](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125726897-1739072367.png)
  * 复杂的![image-20200702163105317](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125727236-1612136095.png)

****

### 服务网关

#### Gateway新一代网关

##### 概述简介

* 官网：https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.1.RELEASE/reference/html/
* 是什么
  * 概述
    * `SpringCloud Gateway`是Spring Cloud的一个全新项目，它旨在为微服务架构提供一种简单有效的统一的`API`路由管理方式
    * 为了提高网关的性能，`SpringCloud Gateway`是基于`WebFlux`框架实现的，而`WebFlux`框架底层则使用了高性能的Reactor模式通信框架Netty
    * `SpringCloud Gateway`的目标提供统一的路由方式且基于Filter链的方式提供了网关基本的功能，例如：安全，监控/指标和限流
  * 一句话：Spring Cloud Gateway 使用的`Webflux`中的`reactor-netty`响应式编程组件，底层使用了Netty通讯
* 能干嘛
  * 反向代理
  * 鉴权
  * 流量控制
  * 熔断
  * 日志监控
  * ......
* 微服务架构中网关在哪里
  
  * ![image-20200702173947561](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125727594-368566095.png)
* 为什么选择使用Gateway
  * 1.neflix不太靠谱，zuul2.0一直跳票,迟迟不发布
  * Spring Cloud Gateway 具有如下特性：
    * 基于Spring Framework 5，Project Reactor和Spring Boot2.0进行构建
    * 动态路由：能够匹配任何请求属性
    * 可以对路由指定Predicate（断言）和Filter（过滤器）
    * 集成Hystrix的断路器功能
    * 集成Spring Cloud 服务发现功能
    * 易于编写的Predicate（断言）和Filter（过滤器）
    * 请求限流功能
    * 支持路径重写
  * Spring Cloud Gateway和Zuul的区别
    * Zuul 1.x,，是一个基于阻塞I/O的API网关
    * Zuul 2.x 理念更先进，想基于Netty非阻塞和支持长连接，但SpringCloud目前还没有整合。Zuul 2.x的性能比Zuul 1.x有较大的提升。在性能方面，根据官方提供的测试基准，Spring Cloud Gateway的RPS（每秒请求数）是Zuul的1.6倍
    * Spring Cloud Gateway建立在Spring Framework5、Project Reator和Spring Boot 2之上，使用非阻塞API
    * Spring Cloud Gateway还支持WebSocket，并且与Spring紧密集成拥有更好的开发体验

* Zuul 1.x模型
  * ![image-20200710140944110](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125727846-460911999.png)
  * 上述模型的缺点：servlet是一个简单的网络IO模型，当请求进入servlet container时，servlet container就会为其绑定一个线程，在并发不高的场景下，这种模型是适用的，但是一旦高并发（比如抽风用jemeter压测），线程数量就会上涨，而线程资源是昂贵的（上下文切换，内存消耗大）严重影响请求的处理时间。在一些简单业务场景下，不希望为每个request分配一个线程，只需要1个或几个线程就能应对极大并发的请求，这种业务场景下servlet模型没有优势，所以**Zuul 1.x是基于servlet之上的一个阻塞式处理模型，即spring实现了处理所有请求的一个servlet（DispatcherServlet）并由该servlet阻塞式处理模型**。

* Gateway模型

  * webFlux：传统的Web框架，比如说：struts2、springmvc等都是基于Servlet API与Servlet容器基础之上运行的

    但是在Servlet3.1之后有了异步非阻塞的支持。而**WebFlux是一个典型非阻塞异步的框架**，它的核心是基于Reactor的相关API实现的。相对于传统的微博框架来说，它可以运行在诸如Netty，Undertow以及支持Servlet3.1的容器上。非阻塞式＋函数式编程（Spring5必须让你使用Java8）

    Spring WebFlux是spring5.0引入的新的响应式框架，区别于Spring MVC，它不需要依赖Servlet API，它是完全异步非阻塞的且基于Reactor来实现响应式流规范

##### 三大核心概念

* Route（路由）：路由是构建网关的基本模块，它由ID，目标URI,一系列的断言和过滤器组成，如果断言为true则匹配该路由

* Predicate（断言）：参考的是`Java8`的`java.util.function.Predicate`.。开发人员可以匹配HTTP请求中的所有内容（例如请求头或请求参数）如果请求与断言相匹配则进行路由

* Filter（过滤器）：指的是Spring框架中GatewayFilter的实例，使用过滤器，可以在请求被路由之前或者之后对请求镜像修改

* 总体

  ![image-20200710143620011](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125728118-1901673771.png)

  web请求，通过一些匹配条件，定位到真正的服务节点。并在这个转发过程的前后，进行一些精细化控制。perdicate就是我们的匹配条件。而Filter就可以理解为一个无所不能的拦截器，有了这两个元素，再加上目标uri，就可以实现一个具体的路由了。

##### Gateway工作流程

* ![image-20200710144527839](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125728538-834034922.png)

* Gateway流程：客户端向Spring Cloud Gateway发出请求。然后在Gateway Handler Mapping 中找到与请求相匹配的路由，将其发送到Gateway Web Handler

   Handler再通过指定的过滤器链来将请求发送到我们实际的服务执行业务逻辑，然后返回

   过滤器之间用虚线分开是因为过滤器可能会在发送代理请求之前（“pre”）或之后（“post”）执行业务逻辑

  Filter在“pre”类型的过滤器可以做参数校验、权限校验、流量监控、日志输出、协议转换等，在“post”类型的过滤器中可以做响应内容、响应头的修改，日志的输出，流量监控等有非常重要的作用

##### 入门配置

* cloud-gateway-gateway9527

* 配置

  ![image-20200713134803637](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125728841-977241554.png)

* 我们目前不想暴露8001端口，希望在8001外面套一层9527
* 测试
  * 添加网关前：http://localhost:8001/payment/get/31
  * 添加网关后：http://localhost:9527/payment/get/31

* Gateway网关路由有两种配置方式

  1. 在配置文件yml中配置

  ![image-20200713135107089](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125729179-2130747504.png)

  2. 代码中注入RouteLocator的Bean

     * 官网案例

       ![image-20200713135351313](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125729494-1165268519.png)

     * 例子

       ![image-20200713154320707](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125729809-167335144.png)

##### 通过微服务名实现动态路由

* 对比未使用Gateway，提供了统一的对外端口9527

  ![image-20200713165137090](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125730093-107753634.png)

* 动态路由
  * 默认情况下Gateway会根据注册中心注册的服务列表，**以注册中心上微服务名为路径创建动态路由进行转发，从而实现动态路由**
  * ![image-20200713170138263](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125730389-1126198726.png)
  * **需要注意的是uri的协议为lb，表示启用Gateway的负载均衡功能**。lb://serviceName是spring cloud gateway在微服务中自动为我们创建的负载均衡uri
  * 测试：http://localhost:9527/payment/lb   看到8001/8002两个端口切换

##### Predicate的使用

+ 是什么

  + 启动9527项目 查看控制台

    ![image-20200713170459784](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125730723-259583961.png)

  * Route Predicate Factories这个是什么？

    * ![image-20200713170857854](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125731063-409930196.png)

  * 常用的Route Predicate

    1. After

       ![image-20200713171925715](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125731386-1232832823.png)

    2. Before :同理After

    3. Between：同路After

    4. Cookie

       ![image-20200713172011974](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125731685-1618711796.png)

    5. Header

       ![image-20200713172512319](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125732035-351318416.png)

    6. Host

       ![image-20200713172543209](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125732322-1027774143.png)

    7. Method

       ​	yml: -Method=GET

    8. Path

    9. Query

       ![image-20200713172625034](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125732498-791498511.png)

    10. 说白了，Predicate就是为了实现一组匹配规则，让请求过来找到对应的Route进行处理

##### Filter的使用

* 是什么

  ![image-20200713173050059](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125732774-1967249637.png)

* Spring Cloud Gateway 的 Filter
  * 生命周期 两种
    1. pre
    2. post
  * 种类 两种
    1. Gateway Filter
    2. Global Filter
* 常用的Gateway Filter
  
  * ![image-20200714084020619](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125733098-1580210788.png)
* 自定义过滤器
  * 自定义全局Global Filter
    * 两个主要接口实现 `implements GlobalFilter Ordered`
    * 能干嘛
      * 全局日志记录
      * 统一网关鉴权
      * 。。。

++++

### 服务配置

#### Spring Cloud config分布式配置中心

##### 概述

* 分布式系统面临问题---配置问题

  * 微服务意味着要将单体应用中的业务拆分成一个个子服务，每个服务的粒度相对较小，因此系统中会出现大量的服务。由于每个服务都需要必要的配置信息才能运行，所以一套**集中式的、动态的配置管理**设施是必不可少的

    Spring Cloud提供了ConfigServer来解决这个问题，我们每一个微服务自己带着一个application.yml,上百个配置文件的管理

* 是什么

  * SpringCloud Config为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为**各个不同微服务应用的**所有环境提供了一个**中心化的外部配置**
  * ![image-20200714105650834](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125733397-961700295.png)

* 怎么玩

  * SpingCloud Config分为服务端和客户端两部分
  * 服务端也称为**分布式配置中心**，**它是一个独立的微服务应用**，用来连接配置服务器并为客户端提供获取配置信息，加密/解密信息等访问接口
  * 客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取加载配置信息，配置服务器默认采用git来存储配置信息，这样就有助于对环境配置进行版本管理，并且可以通过git客户端工具来方便的管理和访问配置内容

* 能干嘛

  * 集中管理配置文件
  * 不同环境不同配置，动态化的配置更新，分环境部署比如dev/test/prod/beta/release
  * 运行期间动态调整配置，不再需要在每个服务不是的机器上编写配置文件，服务回向配置中心统一拉取配置自己的信息
  * 当配置发生变动时，服务不需要重启即可感知到配置的变化并应用新的配置
  * 将配置信息以REST接口的形式暴露

* 与GitHub整合配置

  * 由于Spring Cloud Config默认使用Git来存储配置文件（也有其他方式，比如支持SVN和本地文件），但最推荐的还是Git，而且使用的是http/https访问的形式

* 官网

  * https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.2.1.RELEASE/reference/html/

##### Config服务端配置与测试

* 新建Module模块`cloud-config-center-3344`它既为Cloud的配置中心模块cloudConfig Center

* ![image-20200714131727782](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125733717-407257322.png)

  常用的配置读取规则

  * /{label}/{application}-{profile}.yml ----->http://config-3344:3344/master/config-dev.yml
  * /{application}-{profile}.yml ------>http://config-3344:3344/config-dev.yml
  * /{application}/{profile}[/{label}]------>http://config-3344:3344/config/dev/master

##### Config客户端配置与测试

* 新建`cloud-config-client-3355`

* **bootstrap.yml**

  * 是什么：![image-20200714144029646](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125734039-2063654032.png)

  * ![image-20200714154746872](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125734521-1197922533.png)

  * 成功实现了客户端3355访问SpringCloud Config3344通过GitHub获取配置信息
  * 问题随时而来，分布式配置的动态刷新
    * Linux运维修改GitHub上的配置文件内容做调整
    * 刷新3344，发现ConfigServer配置中心立刻响应
    * 刷新3355，发现ConfigServer客户端没有任何响应
    * 3355没有变化除非自己重启或者重新加载
    * 难道每次运维修改配置文件，客户端都需要重启？？噩梦

##### Config客户端之动态刷新（**）

* 避免每次更新配置都要重启客户端微服务3355

* 动态刷新

  * yml 暴露监控端口号

    ![image-20200714160316180](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125734904-179193937.png)

  * 添加@RefreshScope 注解

    ![image-20200714160243391](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125735316-827343314.png)

  * 需要运维人员发送**Post**请求刷新3355--->curl -X POST "http://localhost:3355/actuator/refresh"
  * 成功实现了客户端3355刷新到最新配置内容，**避免了服务的重启**

* 想想还有什么问题？---->**引出了spring cloud bus消息总线**

  * 假如有多个微服务客户端3355/3366/3377。。。。
  * 每个微服务都要执行一次post请求，手动刷新？
  * **可否广播，一次通知，处处生效？**
  * 我们想大范围的自动刷新

++++



### 服务总线

#### Spring Cloud Bus消息总线

##### 概述

* 上一讲的加深和扩充，分布式自动刷新配置功能，SpringCloud Bus和SpringCloud Config使用可以实现配置的动态刷新
* 是什么
  * Bus支持两种消息代理：RabbitMQ和Kafka
  * ![image-20200715083411600](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125735615-274231507.png)
* 能干嘛
  * ![image-20200715084155049](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125735932-1146264575.png)
* 为何被称为总线
  * **什么是总线**：在微服务架构的系统中，通常会使用**轻量级的消息代理**来构建一个**共用的消息主题**，并让系统中的所有微服务实例都连接上来。由于**该主题中产生的消息会被所有实例监听和消费，所以称它为消息总线**。在总线上的各个实例，都可以方便的广播一些需要让其他连接在该主题上的实例都知道的消息。
  * 基本原理：Config Client实例都监听MQ中同一个topic(默认是SpringCloud Bus).当一个服务刷新数据的时候，它会把这个信息放入到Topic中，这样其他监听同一Topic的服务就能得到通知，然后去更新自身的配置。

##### RabbitMQ环境配置(docker 启动)

##### SpringCloud Bus动态刷新全局广播

* cloud-config-client-3366
* 设计思想：
  1. 利用消息总线触发一个客户端/bus/refresh,而刷新所有客户端的配置
     1. ![image-20200715094709293](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125736286-22068370.png)
  2. 利用消息总线触发一个服务端ConfigServer的/bus/refresh端点,而刷新所有客户端的配置（更加推荐）
     1. ![image-20200715094814089](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125736592-568174918.png)

* 第二种设计显然更加合适，第一种不适合的原因如下：
  * 打破了微服务的职责单一性，因为微服务本身是业务模块，它本不应该承担配置刷新的职责
  * 破坏了微服务各个节点的对等性
  * 有一定的局限性，例如，微服务在迁移时，它的网络地址会常常发生变化，此时如果想要做到自动刷新，那就回增加更多的修改

* 示例
  1. 给cloud-config-center-3344配置中心服务端添加消息总线支持
  2. 给cloud-config-client-3355客户端添加消息总线支持
  3. 给cloud-config-client-3366客户端添加消息总线支持
  4. 测试   curl -X POST "http://localhost:3344/actuator/bus-refresh"
  5. 一次发送，处处生效

* **一次修改，广播通知，处处生效**

##### SpringCloud Bus动态刷新定点通知

* 不想全部通知，只想定点通知
* 一句话：指定具体某一个实例生效而不是全部
  * 公式：http://localhost:配置中心的端口号/actuator/bus-refresh/{destination}
  * /bus/refresh请求不再发送到具体的服务实例上，而是发给config server并通过destination参数类指定需要更新配置的服务或实例

* 案例：我们这里以刷新运行在3355端口上的config-client为例，只通知3355，不通知3366
  
* curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355"
  
* 案例总结

  ![image-20200715104408792](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125736894-858291065.png)

#### SpringCloud Stream消息驱动

##### 为什么要引入SpringCloud Stream

* 解决什么痛点
* ![image-20200715113700443](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125737226-461200423.png)

##### 消息驱动概述

* 是什么

  * **屏蔽底层消息中间件的差异，降低切换成本，统一消息的编程模型**

  * 官网：https://spring.io/projects/spring-cloud-stream#overview

    Spring Cloud Stream中文指导手册：https://m.wang1314.com/doc/webapp/topic/20971999.html

  * ![image-20200715114227878](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125737641-437474742.png)

* 设计思想

  * 标准MQ
    * ![image-20200715115144555](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125737951-288880205.png)
    * Message：生产者/消费者之间靠**消息**媒介传递信息内容
    * 消息通道MessageChannel：消息必须走特定的**通道**
    * 消息通道MessageChannel的子接口SubscribableChannel,由MessageHandler消息处理器订阅：消息通道里的消息如何被消费呢，谁负责收发**处理**
  * 为什么用Cloud Stream
    * ![image-20200715131922176](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125738189-4276350.png)
    * ![image-20200715131929992](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125738482-1826571743.png)
    * Stream凭什么可以统一底层差异
      * ![image-20200715132037488](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125738810-1524673806.png)
      * 通过**定义绑定器Binder作为中间层**，实现了应用程序与消息中间件细节之间的隔离
    * **Binder**
      * INPUT对应于消费者
      * OUTPUT对应于生产者
      * ![image-20200715133522169](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125739175-1870760849.png)
  * **Stream中的消息通信方式遵循了发布-订阅模式**
    * Topic主题进行广播
      * 在RabbitMQ中就是Exchange
      * 在Kakfa中就是Topic

* Spring Cloud Stream标准流程套路

  * ![image-20200715134147057](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125739468-326760283.png)
  * Binder：很方便的连接中间件，屏蔽差异
  * Channel：通道，是队列Queue的一种抽象，在消息通讯系统中就是实现存储和转发的媒介，通过Channel对队列进行配置
  * Source和Sink：简单的可理解为参照对象是Spring Cloud Stream自身，从Stream发布消息就是输出，接受消息就是输入

* 编码API和常用注解

  * ![image-20200715134337882](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125739798-810521566.png)

##### 案例说明

+ RabbitMQ环境已经OK
+ 三个子模块
  + cloud-stream-rabbitmq-provider8801,作为生产者发送消息模块
  + cloud-stream-rabbitmq-consumer8802,作为消息接收模块
  + cloud-stream-rabbitmq-consumer8803 作为消息接收模块

##### 消息驱动之生产者

* cloud-stream-rabbitmq-provider8801

* ![image-20200715142207106](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125740216-742213406.png)

  

* ![image-20200715142236665](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125740558-1651714501.png)

##### 消息驱动之消费者

* cloud-stream-rabbitmq-consumer8802
* ![image-20200715152923078](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125740990-1939052371.png)

##### 分组消费（避免重复消费）与持久化

* 依照8802，clone一份8803，cloud-stream-rabbitmq-consumer8803
* 8001生产消息，8002和8003消费
* 存在两个问题：
  * 重复消费
    * ![image-20200715155942073](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125741375-1460613487.png)
    * 解决方案：分组和持久化属性group（**）
    * 生产实际案例：![image-20200715155304870](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125741698-2121187764.png)
    * 不同的组是可以全面消费的（重复消费）；同一组内会发生竞争关系，只有其中一个可以消费
  * 分组
    * ![image-20200715160511090](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125742028-50728459.png)
    * 原理：微服务应用放置于同一个group中，就能保证消息只会被其中一个应用消费一次。不同的组是可以完全消费的，**同一个组内会发生竞争关系，只有其中一个可以消费**
    * 默认生成流水号，处于不同组，导致重复消费
    * 8802/8803实现了轮询分组，每次只有一个消费者 8801模块的发的消息只能被8802或8803其中一个接收到，这样避免了重复消费-
    * 8802/8803都变成相同组，两个group相同，**同一个组的多个微服务实例，每次只会被一个实例消费**
  * 消息持久化
    * 加上group属性 默认持久化
    * 去掉group属性，取消了持久化

#### SpringCloud Sleuth 分布式请求链路跟踪

##### 概述

* 为什么会出现这个技术？需要解决哪些问题？
  * ![image-20200716101038594](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125742401-1754525943.png)
* 是什么
  * 官网：https://github.com/spring-cloud/spring-cloud-sleuth
  * Spring Cloud Sleuth提供了一套完整的服务跟踪的解决方案，在分布式系统中提供追踪解决方案并且兼容支持了zipkin

##### 链路监控（略）

### 下篇 SpringCloud Alibaba

#### SpringCloud Alibaba 入门简介

##### 为什么会出现SpringCloud Alibaba

* **Spring Cloud Netflix项目进入维护模式**
* SpringCloud NetFlix Projects Entering Maintenance Mode
  * 什么是维护模式
  * ![image-20200716152442644](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125742791-347049969.png)
  * ![image-20200716152503697](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125743095-508464505.png)

##### SpringCloud Alibaba带来了什么

* 是什么
  * ![image-20200716154312102](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125743394-1840612749.png)
* 能干嘛
  * **服务限流降级**：默认支持 WebServlet、WebFlux, OpenFeign、RestTemplate、Spring Cloud Gateway, Zuul, Dubbo 和 RocketMQ 限流降级功能的接入，可以在运行时通过控制台实时修改限流降级规则，还支持查看限流降级 Metrics 监控
  * **服务注册与发现**：适配Spring Cloud服务注册与发现标准，默认集成了Ribbon的支持
  * **分布式配置管理**：支持分布式系统中的外部化配置，配置更改时自动刷新
  * **消息驱动能力**：基于Spring Cloud Stream为微服务应用构建消息驱动能力
  * **分布式事务**：使用 @GlobalTransactional 注解， 高效并且对业务零侵入地解决分布式事务问题
  * **阿里云对象存**储：阿里云提供的海量、安全、低成本、高可靠的云存储对象。支持在任何应用、任何时间、任何地点存储和访问任意类型的数据
  * **分布式任务调度**：提供秒级、精准、高可靠、高可用的定时（基于Cron表达式）任务调度服务。同时提供分布式的任务执行模型，如网格任务。网格任务支持海量子任务均匀分配到所有Worker（schedulerx-client）上执行
  * **阿里云短信服务**：覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道
* 组件
  * **Sentinel**:把流量作为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性
  * **Nacos**:一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台
  * **RocketMQ**：一款开源的分布式消息系统，基于高可用分布式集群技术、提供低延时的、高可靠的消息发布与订阅服务
  * **Dubbo**:Apache Dubbo是一款高性能的Java RPC框架
  * **Seata**:阿里巴巴开源产品，一个易于使用的高性能微服务分布式事务解决方案
  * **Alibaba Cloud ACM**:一款在分布式架构环境中对应用配置进行集中管理和推送的应用配置中心产品
  * **Alibaba Cloud OSS**:阿里云对象存储服务（Object Storage Service,简称OSS）,是阿里云提供的海量、安全、低成本、高可靠的云存储服务。您可以在任何应用、任何时间、任何地点存储和访问任意类型的数据
  * **Alibaba Cloud ScheduleX**:阿里中间件团队开发的一款分布式任务调度产品，提供秒级、精准、高可靠、高可用的定时（基于Cron表达式）任务调度服务
  * **Alibaba Cloud SMS**:覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道

##### SpringCloud alibaba学习资料获取

* 官网：https://spring.io/projects/spring-cloud-alibaba#overview
* 英文;https://github.com/alibaba/spring-cloud-alibaba和https://spring-cloud-alibaba-group.github.io/github-pages/greenwich/spring-cloud-alibaba.html
* 中文:https://github.com/alibaba/spring-cloud-alibaba/blob/master/README-zh.md

#### Spring Cloud Alibaba Nacos服务注册和配置中心

##### Nacos简介

* 为什么叫Nacos

  * 前四个字母分别是Naming和Configuration的前两个字母，最后的s为Service

* 是什么

  * 一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台

  * Nacos：Dynamic Naming and Configuration Service

  * Nacos就是注册中心＋配置中心的组合--->Nacos=Eureka + Config + Bus

  * Nacos 致力于帮助您发现、配置和管理微服务。Nacos 提供了一组简单易用的特性集，帮助您快速实现动态服务发现、服务配置、服务元数据及流量管理。

    Nacos 帮助您更敏捷和容易地构建、交付和管理微服务平台。 Nacos 是构建以“服务”为中心的现代应用架构 (例如微服务范式、云原生范式) 的服务基础设施。

* 能干嘛

  * **替代Eureka做服务注册中心**
  * **替代Config做服务配置中心**

* 去哪下

  * 官网：https://github.com/alibaba/Nacos
  * 官方文档：https://spring-cloud-alibaba-group.github.io/github-pages/greenwich/spring-cloud-alibaba.html#_spring_cloud_alibaba_nacos_discovery

* 各种注册中心比较

  * ![image-20200717103511552](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125743717-347594806.png)

##### 安装并运行Nacos（官网下载）

##### Nacos作为服务注册中心演示

* cloudalibaba-provider-payment9001，cloudalibaba-provider-payment9002
* ![image-20200717133439155](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125744147-1341103895.png)

* cloudalibaba-consumer-nacos-order83

* ![image-20200717135052796](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125744464-1249859834.png)

* 注册进了Nacos

  ![image-20200717135645959](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125744717-1205844744.png)

* 为什么nacos支持负载均衡：依赖中整合了ribbon

  ![image-20200717135456347](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125744991-1178997121.png)

  * 83访问9001/9002，轮询负载OK

##### 各种注册中心比较

* Nacos支持CAP中 AP和CP模式的切换

* Nacos全景图所示

  ![image-20200717140000191](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125745396-225144756.png)

* Nacos和CAP

  ![image-20200717140033992](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125745811-645240313.png)

  ![image-20200717140101991](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125746226-570479717.png)

* **Nacos支持AP和CP模式的切换**

  ![image-20200717140308679](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125746631-2046551828.png)

##### Nacos作为服务配置中心演示

* Nacos作为配置中心-基础配置

  * cloudalibaba-config-nacos-client3377
  * why配置两个
    * application.yml
    * bootstrap
    * ![image-20200717141646237](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125746991-1686965255.png)
    * ![image-20200717143028585](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125747225-1053315500.png)
    * 在Nacos中添加配置信息
      * 理论：Nacos中的dataid的组成格式与SpringBoot配置文件中的匹配规则
        * ![image-20200717143743754](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125747515-2124420182.png)
        * ![image-20200717143759240](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125747938-119569382.png)
      * 实操
        * 配置新增
        * Nacos界面配置对应
        * ![image-20200717144448041](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125748166-758445724.png)
        * ![image-20200717144430780](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125748436-900092028.png)
    * ![image-20200717145346614](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125748756-1743808551.png)
    * 自带动态刷新：修改下Nacos中的yaml配置文件，再次调用查看配置的接口，就会发现配置已经刷新

* Nacos作为配置中心-分类配置

  * 问题：多环境多项目管理

  * Nacos的图形化管理界面

    * 配置管理

      ![image-20200717150438906](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125748984-1202090464.png)

    * 命名空间

      ![image-20200717150452778](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125749290-78552299.png)

  * Namespace+Group+Data ID三者关系？为什么这么设计

    * ![image-20200717150838760](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125749562-1173353712.png)![image-20200717150850254](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125749875-708797363.png)

  * Case

    * DataID方案

      * 指定spring.profile.active和配置文件的DataID来使不同环境下读取不同的配置
      * 默认空间＋默认分组+新建dev和test两个DataID
        * 新建dev配置DataID
        * 新建test配置DataID
        * ![image-20200717162401101](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125750205-75658930.png)
      * 通过spring.profile.active属性就能进行多环境下配置文件的读取

    * Group方案

      * ![image-20200717164822996](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125750642-626206204.png)

      * 在config下增加一条group的配置即可。可配置为DEV_GROUP或TEST_GROUP

        ![image-20200717164849763](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125750971-665996753.png)

    * Namespace方案

      * 新建dev/test的Namespace
      * 回到服务管理-服务列表查看
        * ![image-20200717165754503](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125751287-74973137.png)
      * 按照域名配置填写

##### Nacos集群和持久化配置（**）

###### 官方说明

* 官网：https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html

* 官网架构图

  ![image-20200720110320898](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125751591-911890917.png)

* 官网架构翻译，真是情况

  ![image-20200720110409504](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125751793-1851558197.png)

  ![image-20200720110417977](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125752027-1036880640.png)

  * 说明
    * 需配置mysql数据，https://nacos.io/zh-cn/docs/deployment.html
    * ![image-20200720112239812](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125752313-280877127.png)
    * ![image-20200720112249945](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125752639-84671834.png)

###### Nacos持久化配置解释

* Nacos默认自带的是嵌入式数据库derby，https://github.com/alibaba/nacos/blob/develop/config/pom.xml

* derby到MySQL切换配置步骤

  * nacos-server-1.1.4\nacos\conf目录下找到sql脚本，执行脚本nacos-mysql.sql

  * nacos-server-1.1.4\nacos\conf目录下找到application.properties

    ```
    # db mysql
    spring.datasource.platform=mysql
    db.num=1
    db.url.0=jdbc:mysql://127.0.0.1:3306/nacosconfig?serverTimezone=UTC&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
    db.user=root
    db.password=123456
    ```

  * 启动nacos，可以看到是个全新的空记录界面，以前是记录进derby

###### Linux版Nacos+MySQL生产环境配置

* 预计1个nginx+3个nacos注册中心+1个mysql

* **搭建步骤参考**：https://www.jianshu.com/p/ad12f28c4e67

* 集群配置步骤(**)

* 测试

  * 微服务cloudalibaba-provider-payment9002启动注册进nacos集群
  * ![image-20200722114314868](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125752912-38176159.png)

* 高可用小总结

  ![image-20200722114330980](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125753168-147663502.png)



#### SpringCloud Alibaba Sentinel实现熔断与限流

##### Sentinel简介

* 官网：https://github.com/alibaba/Sentinel/wiki

* 是什么

  * Sentinel 以流量为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性
    * **丰富的应用场景**：Sentinel 承接了阿里巴巴近 10 年的双十一大促流量的核心场景，例如秒杀（即突发流量控制在系统容量可以承受的范围）、消息削峰填谷、集群流量控制、实时熔断下游不可用应用等
    * **完备的实时监控**：Sentinel 同时提供实时的监控功能。您可以在控制台中看到接入应用的单台机器秒级数据，甚至 500 台以下规模的集群的汇总运行情况
    * **广泛的开源生态**：Sentinel 提供开箱即用的与其它开源框架/库的整合模块，例如与 Spring Cloud、Dubbo、gRPC 的整合。您只需要引入相应的依赖并进行简单的配置即可快速地接入 Sentinel
    * **完善的 SPI 扩展点**：Sentinel 提供简单易用、完善的 SPI 扩展接口。您可以通过实现扩展接口来快速地定制逻辑。例如定制规则管理、适配动态数据源等

  ![image-20200722134337407](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125753468-883745658.png)

* 能干嘛

  * ![image-20200722134601188](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125753813-770913803.png)

* 怎么玩
  * 服务使用中的各种问题
    * 服务雪崩
    * 服务降级
    * 服务熔断
    * 服务限流

##### 安装Sentinel控制台

* Sentinel组件由两部分构成
  * ![image-20200722140330425](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125754081-1081540023.png)
* 安装步骤
  * 官网下载，sentinel-dashboard-x.x.jar
  * 运行命令： java -jar sentinel-dashboard-x.x.jar
  * 访问sentinel管理界面
    * http://localhost:8080
    * 登录账号密码均为sentinel

##### 初始化演示工程

* cloudalibaba-sentinel-service8401
* 启动Nacos8848成功
* 启动Sentinel8080
* 启动8401微服务后查看sentienl控制台
  * 第一次访问，空白页
  * Sentinel采用的懒加载说明，执行一次访问即可
  * 效果图：![image-20200722142522934](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125754331-1480691105.png)

##### 流控规则

* 基本介绍

  * ![image-20200722143539227](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125754676-1901315947.png)

  * 进一步介绍

    * ![image-20200722143602598](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125755072-819051743.png)

      ![image-20200722143624005](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125755439-1505856174.png)

* 流控模式

  * 直接（默认）

    * 系统默认，直接->快速失败

    * 配置及说明

      ![image-20200722144612549](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125755727-802497245.png)

    * 测试

      * 快速点击访问http://localhost:8401/testA
      * 结果；Blocked by Sentinel (flow limiting)
      * 思考：是否应该加一个类似fallback的兜底方法？

  * 关联

    * 是什么

      * 当关联的资源达到阈值时，就限流自己
      * 当与A关联的资源B达到阈值后，就限流自己
      * B惹事，A挂了
      * **应用场景: 比如支付接口达到阈值,就要限流下订单的接口,防止一直有订单**

    * postman模拟并发密集访问testB

      * 配置规则

        ![image-20200722150944857](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125756062-1656517525.png)

      * postman里新建多线程集合组

      * 将访问地址添加进新线程组 -- save as  选择对应的collection

      * ![image-20200722150903111](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125756453-670179577.png)

      * 大批量线程高并发访问B，导致A失效了

  * 链路

    * 链路流控模式指的是，当从某个接口过来的资源达到限流条件时，开启限流；它的功能有点类似于针对 来源配置项，区别在于：针对来源是针对上级微服务，而链路流控是针对上级接口，也就是说它的粒度 更细
    * ![image-20200722152647066](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125756737-1560004072.png)

* 流控效果

  * 直接--->快速失败（默认的流控处理），直接失败，抛出异常

  * 预热

    * 说明：公式：阈值除以coldFactor（默认值为3），经过预热时长后才会达到阈值

    * 官网：

      ![image-20200722154816697](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125757066-3539311.png)

    * 默认coldFactor为3，即请求QPS从threshold(阈值)/3开始，经预热时长逐渐升至设定的QPS阈值。

    * 限流冷启动：https://github.com/alibaba/Sentinel/wiki/%E9%99%90%E6%B5%81---%E5%86%B7%E5%90%AF%E5%8A%A8

    * 源码

      ![image-20200722155130831](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125757380-826998524.png)

    * 配置![image-20200722155200246](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125757717-1932885036.png)
    * 测试：多次点击http://localhost:8401/testB  结果：刚开始不行，后续慢慢OK
    * 应用场景![image-20200722155312491](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125758008-133293243.png)

  * 排队等待

    * 匀速排队，阈值必须设置为QPS![image-20200722155723153](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125758296-1693540114.png)

    * 官网![image-20200722155759410](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125758677-285944479.png)

      ![image-20200722155822024](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125758982-1254916413.png)

##### 降级规则

* 官网([https://github.com/alibaba/Sentinel/wiki/%E7%86%94%E6%96%AD%E9%99%8D%E7%BA%A7](https://github.com/alibaba/Sentinel/wiki/熔断降级))![image-20200722161624308](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125759325-1593778507.png)

* 基本介绍

  * ![image-20200722162402228](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125759582-1109937244.png)

    ![image-20200722162415155](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125759884-1579970879.png)

  * 进一步说明![image-20200722162552053](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125800138-1895141361.png)

  * **Sentinel的断路器是没有半开状态的**

    * 半开的状态系统自动去检测是否请求有异常，没有异常就关闭断路器恢复使用，有异常则继续打开断路器不可用。具体可以参考Hystrix
    * hystrix![image-20200722162812407](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125800402-1492402512.png)

* 降级策略实战

  * RT:平均响应时间

    * 是什么![image-20200722165347888](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125800675-1082134518.png)![image-20200722163349983](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125801015-703582709.png)
    * jmeter压测--->1秒钟打入10个请求
    * 结论![image-20200722165016586](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125801367-1684684599.png)

  * 异常比例

    * 是什么![image-20200722165448137](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125801698-1048403876.png)

      ![image-20200722165458269](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125802027-175607454.png)

    * 配置![image-20200722171134770](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125802341-1351964771.png)
    * jmeter压测，结论![image-20200722170503144](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125802647-618247660.png)

  * 异常数

    * 是什么![image-20200722170529795](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125803016-476977121.png)

      ![image-20200722170539812](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125803306-1468868765.png)

    * 异常数是按照分钟统计的

    * 配置＋测试![image-20200722171109104](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125803621-1263305589.png)

      

##### 热点key限流(**)

* 基本介绍

* 官网：https://github.com/alibaba/Sentinel/wiki/热点参数限流

  ![image-20200722171828117](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125803911-1103625346.png)

* 

* 承上启下复习start![image-20200722171805282](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125804202-1907759858.png)

* **@SentinelResource等同于@HystrixCommand**

* 源码：`com.alibaba.csp.sentinel.slots.block.BlockException`

* 配置

  * @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
  * ![image-20200723101145187](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125804557-2019834447.png)
  * 不添加blockHandler方法，会把异常打到前台页面，提示不友好
  * ![image-20200723100818472](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125804853-1686191966.png)
  * **方法testHostKey里面第一个参数只要QPS超过每秒1次，马上降级处理，并且用了我们自己定义的降级提示**

* 参数例外项

  * 上述案例演示了第一个参数p1,当QPS超过1秒1次点击后马上被限流

  * 特殊情况：

    * 普通：超过1秒钟一个后，达到阈值1后马上被限流

    * 特例：我们期望p1参数当它是某个特殊值时，它的限流值和平时不一样.

      例如当p1的值等于5时，它的阈值可以达到200

  * 配置![image-20200723103124593](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125805184-1825342308.png)

* 测试结果：

  * 当p1等于5的时候，阈值变为200
  * 当p1不等于5的时候，阈值就是平常的1

* 前提：热点参数的注意点，参数必须是基本类型或者String

* 其他：存在异常![image-20200723103433488](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125805438-1623385520.png)

##### 系统规则

* 是什么：https://github.com/alibaba/Sentinel/wiki/%E7%B3%BB%E7%BB%9F%E8%87%AA%E9%80%82%E5%BA%94%E9%99%90%E6%B5%81
* 各项配置参数说明![image-20200723104240978](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125805747-237786872.png)
* 配置**全局**QPS![image-20200723104649991](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125806065-347777991.png)

##### @SentinelResource

* 按资源名称限流+后续处理

  * 启动Nacos成功

  * 启动Sentinel成功

  * 修改cloudalibaba-sentinel-service8401

  * 业务类RateLimitController![image-20200723110231337](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125806325-1806555114.png)

  * 配置![image-20200723110206297](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125806577-751217066.png)

    表示1秒钟内查询次数大于1，就跑到我们自定义的处流，限流

  * 测试结果：1秒钟点击1下，OK

    超过上述问题，疯狂点击，返回了自己定义的限流处理信息，限流发送--->进入blockHandler配置的方法

  * 额外问题：此时关闭微服务8401看看，Sentinel控制台，流控规则消失了，**临时/持久**？

* 按照URL地址限流＋后续处理

  * 通过访问的URL来限流，会返回Sentinel自带默认的限流处理信息
  * 业务类RateLimitController![image-20200723111207133](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125806987-1289357260.png)
  * 配置![image-20200723111129955](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125807299-593176470.png)
  * 测试结果![image-20200723111246160](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125807559-280291053.png)

* **上面兜底方案面临的问题**![image-20200723111319008](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125807792-1938366094.png)

* 客户自定义限流处理逻辑

  * 创建customerBlockHandler类用于自定义限流处理逻辑
  * 自定义限流处理类![image-20200723112744568](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125808119-2142909212.png)
  * 业务类指定处理方法![image-20200723112845320](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125808433-529500045.png)
  * 测试结果：业务与异常处理结构，`blockHandlerClass`指定异常处理类和`blockHandler`指定该类中具体处理方法

* 更多注解属性说明

  * ![image-20200723113759329](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125808801-1194540610.png)

    ![image-20200723113812449](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125809302-953534791.png)

  * 注意![image-20200723113846466](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125809617-1521297476.png)
  * Sentinel主要有三个核心API
    1. SphU定义资源
    2. Tracer定义统计
    3. ContextUtil定义了上下文

##### 服务熔断功能

###### sentinel整合ribbon+openFeign+fallback(**)

###### Ribbon系列

* 启动nacos和sentinel

* 提供者9003/9004

* 消费者84

* ![image-20200723140959092](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125809940-1930443535.png)

* 只配置fallback，处理运行时异常![image-20200723145321074](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125810317-915839597.png)

* 只配置blockHandler，blockHandler只负责sentinel控制台配置违规![image-20200723145436836](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125810516-1339924817.png)

* fallback和blockHandler都配置![image-20200723150739554](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125810747-23581287.png)

  ![image-20200723150229273](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125810984-537463950.png)

* 忽略属性 exceptionToIgnore![image-20200723150709324](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125811293-1199899412.png)

###### Feign系列

	* 修改84模块，Fegin组件一般是在消费侧
	* yml 添加 feign配置
	* 主启动添加@EnableFeignClients启动Feign的功能
	* 业务类![image-20200723155057366](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125811650-2022437251.png)
	* 测试84调用9003/9004，此时故意关闭9003和9004微服务提供者，**84消费自动降级**，不会被耗死

###### 熔断框架比较

* ![image-20200723155433044](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125811949-976450227.png)
* ![image-20200723155441388](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125812230-43092137.png)

##### 规则持久化

* 是什么：一旦我们重启应用，Sentinel规则将消失，生产环境需要将配置规则进行持久化

* 怎么玩：将限流配置规则持久化进Nacos保存，只要刷新8401某个rest地址，sentinel控制台的流控规则就能看到，只要Nacos里面的配置不删除，针对8401上Sentinel上的流控规则持续有效

* 步骤

  * 修改cloudalibaba-sentinel-service8401

  * yml ：添加数据源配置

    ````
      cloud:
        nacos:
          discovery:
            #Nacos服务注册中心地址
            server-addr: localhost:8848
        sentinel:
          transport:
            #配置Sentinel dashboard地址
            dashboard: localhost:8080
            port: 8719  #默认8719，假如被占用了会自动从8719开始依次+1扫描。直至找到未被占用的端口
          datasource:
            ds1:
              nacos:
                server-addr: localhost:8848
                dataId: cloudalibaba-sentinel-service
                groupId: DEFAULT_GROUP
                data-type: json
                rule-type: flow
    
    ````

  * 添加Nacos业务规则配置![image-20200723160530371](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125812531-234448295.png)

    ![image-20200723160538552](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125812790-970918919.png)

    ````
    [
        {
             "resource": "/rateLimit/byUrl",
             "limitApp": "default",
             "grade":   1,
             "count":   1,
             "strategy": 0,
             "controlBehavior": 0,
             "clusterMode": false    
        }
    ]
    ````

    ![image-20200723163439855](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125813101-1451472229.png)

  * 启动8401后刷新sentinel发现业务规则有了![image-20200723161022652](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125813399-201484149.png)
  * 重新启动8401再看sentinel,多次调用,流控配置重新出现了，持久化验证通过



#### SpringCloud Alibaba Seata处理分布式事务 

##### 分布式事务问题

* 分布式前

  * 单机单库没这个问题
  * 从`1：1 -> 1:N -> N: N`

* 分布式之后![image-20200723165629433](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125813729-468643906.png)

  ![image-20200723165645880](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125813993-404664660.png)

* 一句话:一次业务操作需要跨多个数据源或需要跨多个系统进行远程调用，就会产生分布式事务问题

##### Seata简介

* 是什么

  * Seata是一款开源的分布式事务解决方案，致力于在微服务架构下提供高性能和简单易用的分布式事务服务
  * 官网：http://seata.io/zh-cn/

* 能干嘛

  * 一个典型的分布式事务过程：

    * 分布式事务处理过程一个ID＋三组件模型
      * Transaction ID XID 全局唯一的事务ID
      * 三组件：
        1. Transaction Coordinator(TC):事务协调器，维护全局事务的运行状态，负责协调并驱动全局事务的提交或回滚;
        2. Transaction  Manager(TM) :控制全局事务的边界，负责开启一个全局事务，并最终发起全局提交或全局回滚的决议;
        3. Resource Manager(RM) :控制分支事务，负责分支注册，状态汇报，并接收事务协调器的指令，驱动分支（本地）事务的提交和回滚；

    * 处理过程![image-20200724094547910](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125814255-1694321013.png)

      ![image-20200724094555292](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125814561-869960755.png)

      

* 去哪下：发布说明:https://github.com/seata/seata/releases

* 怎么玩

  * Spring 本地@Transactional
  * 全局@GlobalTransactional![image-20200724095411034](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125814831-936884259.png)

##### Seata-Server安装

* 官网下载稳定版本：https://github.com/seata/seata/releases
* 解压到指定目录并修改conf目录下的file.conf配置文件
  * 先备份原始file.conf文件
  * 主要修改：自定义事务组名称+事务日志存储模式为db+数据库连接信息
  * file.conf，主要修改service模块和store模块两个模块
  * 在seata库里建表
  * 修改\conf目录下的registry.conf配置文件，将seata注册进nacos
  * 先启动Nacos端口号8848
  * 再启动seata-server

##### 订单/库存/账户业务数据库准备

* 以下演示都需要先启动Nacos后启动Seata，保证两个都OK

* 分布式事务业务说明：三个服务 订单服务、库存服务、账户服务。当用户下单时，会在订单服务中创建一个订单，然后通过远程调用库存服务来扣减下单商品的库存，再通过远程调用账户服务来扣减用户账户里面的余额，最后在订单服务中修改订单状态为已完成

  该操作跨越三个数据库，有两次远程调用，很明显会有分布式事务问题

  下订单-->扣库存-->减账户（余额）

* 创建业务数据库

  ````
  -- seata_order: 存储订单的数据库
  CREATE DATABASE seata_order；
  
   -- seata_storage:存储库存的数据库
  CREATE DATABASE seata_storage；
   
   -- sseata_account: 存储账户信息的数据库
  CREATE DATABASE seata_account；
  ````

* 按照上述3库分别建对应业务表

  * seata_order库下建t_order表

    ````
    CREATE TABLE t_order(
        `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `user_id` BIGINT(11) DEFAULT NULL COMMENT '用户id',
        `product_id` BIGINT(11) DEFAULT NULL COMMENT '产品id',
        `count` INT(11) DEFAULT NULL COMMENT '数量',
        `money` DECIMAL(11,0) DEFAULT NULL COMMENT '金额',
        `status` INT(1) DEFAULT NULL COMMENT '订单状态：0：创建中; 1：已完结'
    ) ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
     
    SELECT * FROM t_order;
    ````

  * seata_storage库下建t_storage表

    ````
    CREATE TABLE t_storage(
        `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
        `product_id` BIGINT(11) DEFAULT NULL COMMENT '产品id',
       `'total` INT(11) DEFAULT NULL COMMENT '总库存',
        `used` INT(11) DEFAULT NULL COMMENT '已用库存',
        `residue` INT(11) DEFAULT NULL COMMENT '剩余库存'
    ) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
     
    INSERT INTO seata_storage.t_storage(`id`,`product_id`,`total`,`used`,`residue`)
    VALUES('1','1','100','0','100');
     
     
    SELECT * FROM t_storage;
    ````

  * seata_account库下建t_account表

    ````
    CREATE TABLE t_account(
        `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
        `user_id` BIGINT(11) DEFAULT NULL COMMENT '用户id',
        `total` DECIMAL(10,0) DEFAULT NULL COMMENT '总额度',
        `used` DECIMAL(10,0) DEFAULT NULL COMMENT '已用余额',
        `residue` DECIMAL(10,0) DEFAULT '0' COMMENT '剩余可用额度'
    ) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
     
    INSERT INTO seata_account.t_account(`id`,`user_id`,`total`,`used`,`residue`) VALUES('1','1','1000','0','1000')
     
    SELECT * FROM t_account;
    ````

  * 按照上述3库分别建对应的回滚日志表

    * 订单-库存-账户3个库下都需要建各自的回滚日志表

    * ````
      drop table `undo_log`;
      CREATE TABLE `undo_log` (
        `id` bigint(20) NOT NULL AUTO_INCREMENT,
        `branch_id` bigint(20) NOT NULL,
        `xid` varchar(100) NOT NULL,
        `context` varchar(128) NOT NULL,
        `rollback_info` longblob NOT NULL,
        `log_status` int(11) NOT NULL,
        `log_created` datetime NOT NULL,
        `log_modified` datetime NOT NULL,
        `ext` varchar(100) DEFAULT NULL,
        PRIMARY KEY (`id`),
        UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
      ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
      ````

    * ![image-20200724110646618](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125815124-385214810.png)

##### 订单/库存/账户业务微服务准备

* 业务需求：下订单->减库存->扣余额->改（订单）状态
* 新建订单Order-Module：`cloudalibaba-seata-order-service2001`
* 新建库存Storage-Module：`cloudalibaba-seata-storage-service2002`
* 新建账户Account-Module：`cloudalibaba-seata-account-service2003`
* 本地用的Seata1.2与视频中0.9配置差异较大。建议参考：https://blog.csdn.net/sinat_38670641/article/details/105857484
* 数据库文件及配置文件资源地址：https://github.com/seata/seata/tree/1.2.0/script/config-center

##### Test

* 下订单->减库存->扣余额->改（订单）状态
* 数据库初始情况![image-20200724153156684](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125815346-2004730325.png)
* 正常下单
  * http://localhost:2001/order/create?userid=1&producrid=1&counr=10&money=100
  * 数据库情况![image-20200724153241001](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125815659-1169613017.png)
* 超时异常，没加@GlobalTransactional
  * AccountServiceImpl添加超时
  * 故障情况：当库存和账户余额扣减后，订单状态并没有设置为已经完成，没有从零改为1。而且由于feign的重试机制，账户余额还有可能被多次扣减
* 超时异常，添加@GlobalTransactional
  * AccountServiceImpl添加超时
  * OrderServiceImpl@GlobalTransactional
  * 下单后数据库数据并没有任何改变，记录都添加不进来

##### Seata原理

* 再看TC/TM/RM三大组件![image-20200724160404487](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125815957-314825391.png)

  * **分布式事务执行流程**（**）
    * TM开启分布式事务(TM向TC注册全局事务记录)
    * 换业务场景，编排数据库，服务等事务内资源（RM向TC汇报资源准备状态）
    * TM结束分布式事务，事务一阶段结束（TM通知TC提交/回滚分布式事务）
    * TC汇总事务信息，决定分布式事务是提交还是回滚
    * TC通知所有RM提交/回滚资源，事务二阶段结束。

* AT模式如何做到对业务的无侵入

  * 是什么![image-20200724161249112](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125816255-417757200.png)

    ![image-20200724161257353](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125816545-1148509436.png)

  * 一阶段加载![image-20200724161323804](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125816860-1657773173.png)

    ![image-20200724161343485](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125817190-432789091.png)

  * 二阶段提交![image-20200724161443928](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125817476-643966507.png)

  * 二阶段回滚![image-20200724161453992](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125817771-1551399728.png)

    ![image-20200724161502244](https://img2020.cnblogs.com/blog/1875400/202008/1875400-20200819125818121-692967710.png)

* debug:观察数据库回滚细节

* 总结![img](https://ww1.sinaimg.cn/large/c3beb895ly1g4lpz3xzgzj20hg0830t2.jpg)
