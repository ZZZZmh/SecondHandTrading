# sql语句

```sql
CREATE TABLE account(
    account VARCHAR(32) NOT NULL   COMMENT '账号' ,
    password VARCHAR(32) NOT NULL   COMMENT '密码' ,
    user_id VARCHAR(32) NOT NULL   COMMENT '用户id' ,
    email VARCHAR(32) NOT NULL   COMMENT '邮箱' ,
    telephone VARCHAR(32)   COMMENT '联系电话' ,
    status TINYINT NOT NULL  DEFAULT 1 COMMENT '用户状态 1-正常，2-封禁，3-已注销' ,
    power tinyint NOT NULL  DEFAULT 1 COMMENT '用户权限 1-游客，2-用户，3-管理员',
    PRIMARY KEY (account)
) COMMENT = '账号密码表 ';
```

```sql
CREATE TABLE userinfo(
    user_id VARCHAR(32) NOT NULL   COMMENT '用户id' ,
    user_name VARCHAR(32) NOT NULL   COMMENT '虚拟名' ,
    signature VARCHAR(1024)   DEFAULT '这个人很懒，什么都没有留下' COMMENT '个性签名' ,
    real_name VARCHAR(32)   DEFAULT '未认证' COMMENT '真实姓名' ,
    head_portrait VARCHAR(32)   DEFAULT '默认头像' COMMENT '头像' ,
    id_card VARCHAR(32)   DEFAULT '未认证' COMMENT '身份证' ,
    student_id VARCHAR(32) NOT NULL  DEFAULT '未认证'  COMMENT '学号' ,
    credibility TINYINT NOT NULL  DEFAULT 100 COMMENT '信誉' ,
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    Logout_time DATETIME    COMMENT '注销时间' ,
    PRIMARY KEY (user_id)
) COMMENT = '用户信息表 ';

```

```sql
CREATE TABLE commodity(
    commodity_id VARCHAR(32) NOT NULL   COMMENT '商品id' ,
    user_id VARCHAR(32) NOT NULL   COMMENT '卖家id' ,
    name VARCHAR(32) NOT NULL   COMMENT '名称' ,
    price DECIMAL(32,8) NOT NULL   COMMENT '价格' ,
    image VARCHAR(32) NOT NULL   COMMENT '图片' ,
    number INT unsigned NOT NULL  COMMENT '数量 不能为负数' ,
    Introduction VARCHAR(32) NOT NULL   COMMENT '介绍' ,
    address VARCHAR(32)    COMMENT '发货地址 第四饭堂C15' ,
    Label VARCHAR(32) NOT NULL   COMMENT '标签分类' ,
    contact VARCHAR(32) NOT NULL   COMMENT '联系方式 微信or电话' ,
    status TINYINT unsigned NOT NULL   DEFAULT 1 COMMENT '状态 1-待审核，2-审核通过，3-审核不合格' ,
    version TINYINT NOT NULL DEFAULT 0 COMMENT '悲观锁' ,
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    PRIMARY KEY (commodity_id)
) COMMENT = '商品表 ';

```

```sql
CREATE TABLE order_form(
    order_id  VARCHAR(32) NOT NULL   COMMENT '订单id' ,
    commodity_id  VARCHAR(32) NOT NULL   COMMENT '商品' ,
    buyer VARCHAR(32) NOT NULL   COMMENT '买家' ,
    seller VARCHAR(32) NOT NULL   COMMENT '卖家' ,
    number INT unsigned NOT NULL  DEFAULT 1 COMMENT '商品数量 不能为负数' ,
    address VARCHAR(128) NOT NULL   COMMENT '收货地址' ,
    status TINYINT unsigned NOT NULL  DEFAULT 0 COMMENT '状态 0-待付款，1-未发货，2-已发货，3已签收，4-申请退款，5-退款成功，6-客服介入，7-订单关闭' ,
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    end_time DATETIME    COMMENT '结束时间' ,
    PRIMARY KEY (order_id)
) COMMENT = '订单表 ';

```

```sql
CREATE TABLE comment(
    comment_id VARCHAR(32) NOT NULL   COMMENT '评论id' ,
    order_id VARCHAR(32) NOT NULL   COMMENT '订单id' ,
    commodity_id VARCHAR(32) NOT NULL   COMMENT '商品id' ,
    comment VARCHAR(1024) NOT NULL   COMMENT '评价内容' ,
    imgae VARCHAR(32)    COMMENT '评价图片' ,
    ulike INT unsigned NOT NULL DEFAULT 0 COMMENT '喜欢人数' ,
    unlike INT unsigned NOT NULL DEFAULT 0 COMMENT '不喜欢人数' ,
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    PRIMARY KEY (comment_id,order_id)
) COMMENT = '评论表 ';

```

```sql
CREATE TABLE shopping_cart(
    user_id VARCHAR(32) NOT NULL   COMMENT '用户id' ,
    commodity_id VARCHAR(32) NOT NULL   COMMENT '商品id' ,
    number INT unsigned NOT NULL  DEFAULT 1 COMMENT '商品数量 不能是负数' ,
    isCheck TINYINT NOT NULL  DEFAULT 2 COMMENT '是否选中 1-选中，2-未选中' ,
    PRIMARY KEY (user_id,commodity_id)
) COMMENT = '购物车表 ';

```

```sql
CREATE TABLE address(
    user_id VARCHAR(32) NOT NULL   COMMENT '用户id' ,
    address VARCHAR(32) NOT NULL   COMMENT '常用地址' ,
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    PRIMARY KEY (user_id,address)
) COMMENT = '常用地址 ';

```

```sql
CREATE TABLE Banned_list(
    black_id VARCHAR(32)    COMMENT 'id' ,
    user_id VARCHAR(32)    COMMENT '用户id' ,
    id_card VARCHAR(32)    COMMENT '身份证' ,
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
) COMMENT = '系统封禁用户 ';

```

