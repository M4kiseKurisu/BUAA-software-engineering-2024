## 需求分析

### 项目概述

#### 项目背景

本项目的项目背景有二：

一是大学学风建设的需要。大学的学风建设一直以来是教育界乃至社会关注的重点之一，大学的学风建设关系到校园生态，良好的学风能打造积极向上的面貌，对学生成长、人才培养等具有明显的正向作用。北京航空航天大学明确2024年为“办学质量提升年”，学风建设自然也是其重要的组成部分。学风建设需要各界的共同努力，学生自身也不应缺位。本小组期望通过学业互助平台的搭建与推广，带动学生积极参与课业、学业讨论，调动学生学业积极性，为北航学风建设贡献自己的一份力量。

二是大学生课业学业的显著需求。在大学三年的学习生活中，本小组同学发现：课业与学业上存在困难时，相较于师长、其他网络信息源等解决方式，学生互助这一模式相对处于空白状态。面对课业学业问题，学生常常处于“单打独斗”或“小群体抱团”（例如，同宿舍舍友）境地，较难更广泛地从同学处吸取有益经验。为学生提供一个互助空间，既可以增添一份信息来源，解决同学在选课、课业、学业等方面的疑惑，也有利于促进以获取知识、提升能力为目的的良性竞争，促进大学生共同进步、共同成长。

### 功能性需求描述

#### 用户端

##### 基本功能

- 用户注册：用户通过输入自己设定的账号、密码来完成网站的注册。
- 用户登录：用户采用输入账号密码、手机号密码、邮箱密码（后两者是在完成信息完善的前提之下）登录进入网站。
- 用户信息完善：可将个人手机号、邮箱、昵称、专业、学届、头像、个人签名等信息与个人账号绑定。
- 用户信息展示：展示当前用户的收藏帖子、关注用户、收获的点赞数和评论、私信等信息。
- 收藏帖子：对任意模块内的帖子，例如课业讨论模块中的讨论贴或升学模块的分享贴进行收藏，之后可以在收藏夹中找到对应的帖子，点击可以直接跳转。
- 关注用户：可以对网站中的其他用户进行关注，在这些被关注的用户发布信息时用户会收到提醒。
- 举报内容：针对网站内的所有信息，包括帖子、评论、资源，用户都可以进行举报，举报由管理员和拥有权限的用户进行审核，审核通过被举报内容被删除、发表内容的用户被警告/惩罚。

##### 课业讨论模块

- 创建课程分区：（*普通学生是否可以直接创建课程需要后续讨论*）用户通过上传对应创建课程的基本信息内容（包括课程基本信息，教师基本信息，课程属性等）申请创建课程分区，需要管理员审核通过才能完成创建。
- 浏览板块：在本版块广场上会展示所有的已有课程板块，用户可以点击进入，用户也可以搜索特定板块进入。
- 完善课程信息：用户可以对该课程补充其他有效客观信息，并将信息以规定格式上传，经过管理员、课程教师或者在本模块内具有管理员权限的同学的同意可以完成信息的更改和完善。
- 学生发帖：学生可以针对此课程内容发布帖子，内容可以包括求助、问答、经验分享等，编辑后给出帖名并选择（自定义或选择已有）帖子标签，完成后上传即可完成发帖。
- 浏览帖子：帖子默认以时间顺序排列，用户可以自行查阅，用户也可以给定关键词搜索或使用标签搜索对应的帖子。
- 点赞帖子：用户可以为欣赏的帖子点赞，每个帖子只能点赞一次，点赞可以取消。
- 收藏帖子：用户可以将有用的帖子进行收藏，就可以在信息展示的收藏夹中找到已收藏帖子并点击跳转。
- 评论帖子：可以针对帖子发布评论，评论会在帖子下方展示。
- 评论评论：对帖子的评论也可以进行评论，这种评论在其评论的评论下展示（没有嵌套关系，但可以以@他人的形式回复评论的评论）
- 评论点赞：针对以上两种评论都可以进行点赞，点赞功能同帖子点赞。
- 资源上传：每个课程分区下都有专门的资源板块，在资源板块中用户可以创建资源帖，用户可以将资源附加到帖子上，选定标题和标签并进行发布，这种资源贴只会出现在资源板块。
- 资源板块其他操作：同普通帖子板块。
- 申请权限：若用户现实是如课程助教等在课程中具有一定权力、或者取得老师同意的同学，可以向网站申请拥有对应课程模块的权限。申请权限需要上传对应的证明材料，经管理员审核可以取得权限。此权限可被管理员收回。

##### 升学信息模块
- 帖子相关：本模块内的讨论贴、资源贴的相关需求和课业讨论模块的帖子相同。分区布置也相同：分为讨论区和资源区。差别是帖子的预设标签，发布帖子时需要确定帖子的升学方向标签（考研、保研或出国等）。
- 升学学校信息：本模块拥有专门的不同学校信息的收集子模块，用户可以搜索或浏览学校对应界面，获取该学校的考研保研客观信息，包括考研分数线，保研人数，学校网站超链接等信息（类比研招网的学校信息界面）。
- 对应学校经验分享：（*此需求还未经过讨论*）将讨论区中针对某一学校（需要发帖者在标签中选择对应学校）的，获得点赞回复较多的帖子在对应学校的界面进行展示，可以点击跳转。

##### 圈子社交板块
（私聊、圈子、打卡等需求）
- 私信功能：用户可以向其他用户发送私信，在私信发送之后另一方会受到通知，并可以做出相应的回复。同时用户也可以将其他用户进行拉黑，被拉黑的用户无法对自己发送私信。
- 学习团体功能：在学习团体广场可以看到所有等待组建的学习团体，点击之后可以看到该学习团体的基本信息的介绍，点击申请加入即可发送给团体发起人申请加入团体的信息，发起人同意之后即可加入学习团体，在团体中可以实现团体内部成员的多人聊天。用户也都有权力成为团体的发起人，点击组建团体，并完善好团体的各种信息（团体介绍，限制人数，标签等）即可将团体发布出去在广场中展示。团体的发起人可以最自己团体进行管理，包括删除自己创建的团体，以及踢除团体中的其他成员，团体中的成员也有权退出或举报相应团体。
- 打卡功能：类似于小红书的形式，可以做图文分享并在打卡广场中向其他用户展示，同时可以获得其他用户的点赞和评论。
#### 特定课业板块权限用户
（板块管理员的特别需求）
- 助教：所有用户可以申请成为特定课程的助教，发送申请时需要附加能够证明自己助教身份的信息，由后台的管理员处理申请，申请通过之后即可获得该课程的助教身份。获得助教身份后可以在该课程的所有发帖或评论会被打上助教标签，同时有权对帖子进行加精。助教的各种举报将优先处理。
#### 认证教师用户
（教师用户的特别需求）
- 教师：教师可以在注册账号是选择身份为教师，并留下联系方式（北航邮箱或是电话）和认证信息申请教师账号，再由管理员处理申请，申请通过后管理员将创建相应的教师权限账号，并将该账号发送给教师。教师账号可以进行相应课程板块的创建以及管理，可以删除帖子或评论，发出的帖子和评论也将打上教师标签。教师的各种举报信息将优先处理。
#### 管理员
（管理员特别需求）
- 数据更新：管理员可以添加或删除网站中的公共数据，包括但不限于创建、删除课程模块，以及升学模块中更新院校信息。
- 处理申请：管理员可以在后台处理助教以及教师的权限申请。审核申请信息，如果信息足以表明助教身份则对相应用户发放助教权限。同理，如果教师信息足以表明教师身份，则会创建相应的教师权限账号，并发放到教师的邮箱。如果审核不通过也会回复不通过的理由，要求补充更多有用的信息。
- 处理举报：管理员可以从后台看到各类举报信息，如果举报信息详实可信，则会对被举报的帖子、评论、资源、用户、团体进行删除或账号封禁处理。
- 处理反馈：管理员将在后台看到用户的各种反馈信息，对于有价值的反馈将会被采用，并对项目进行一定程度的优化。
#### 用例图
![image](https://github.com/M4kiseKurisu/BUAA-software-engineering-2024/blob/xqx-branch/%E7%94%A8%E4%BE%8B%E5%9B%BE.jpg)

### 非功能性需求描述 *（待讨论）*

#### 软件非功能性需求

##### 界面需求

##### 访问性能
- 基本要求：学业支持平台的所有页面在运行的全过程中，页面所需的所有信息均能通过请求响应正常读取，各个元素能正常加载、渲染，不出现内容错误或样式异常。所有前后端性能优化，首先均需满足正确性这一基本要求。
- 请求响应性能：后端需及时响应前端发来的请求。从程序视角，后端事务处理不出现过长时间忙等、死锁等异常状况，同时通过算法优化等尽可能压缩后端响应时间；从用户视角，自请求发出到前端呈现反馈，不应发生明显影响用户体验的卡顿。
- 前端渲染性能：前端资源渲染不占用过多用户CPU算力；除加载样式、下载资源的过程外，不因前端渲染导致页面卡顿。
- 并发性能 *（详细指标待讨论）*：在服务器允许的算力消耗范围内，具备500人级别（据北航官网，北航现有本科生16341人，则此处约合北航在校本科生人数的3%）的并发访问而不出现明显卡顿的能力。
- AI性能：*（待补充）*

##### 数据正确性与安全
- 请求、数据正确性：保证前端请求能按功能需求正确发送、后端响应按前端请求正确生成并返回（即用户请求、后端接收请求、前端接收响应保持一致），保证用户信息、帖子等各项查询正确完成（即前端显示与后端数据库信息保持一致）。
- 数据完整性：具备一定的完整性验证、数据保护等能力，使正常存取过程中软件不因数据库中异常数据而崩溃，接受异常输入时软件能自动拒绝异常请求，在极端情况下尽可能保证数据库不损坏。
- 数据库安全：具备一定程度防范SQL注入等常见攻击的能力。
- 用户隐私安全：用户密码等隐私信息不明文保存，在进行加密处理后将密文保存于数据库中。

#### 其他非功能性需求
- 完善的用户协议、社区公约：论坛作为网络公共空间，必须有其基本的运行规则，以降低运行过程中的法律等非技术性风险，防止不法分子利用平台进行违法活动、侵害管理者或使用者的合法权益，防止垃圾信息（无关信息、网络谣言等）占据版面、影响用户正常使用。此外，以目前市面上常见的网络讨论平台为参照，使用这些平台的服务（如注册账户）时，必须首先同意其用户协议，同时论坛也设计了社区公约供用户了解及遵守。因此，在搭建学业互助平台时，必须配备一套完善的用户协议和社区公约。
- 内容维护：开发小组作为初始、最高权限管理员，应与其他管理员一同积极参与平台管理，整理帖子信息，维护讨论环境，营造良好社区空间。
- 法律保护：开发小组保证在软件开发、管理的全过程中，遵守有关计算机安全、计算机软件著作权及用户合法权益的法律法规，积极完成必要备案（若有需要），积极接受有关部门及社会各界监督。