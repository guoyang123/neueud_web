
# 用户表
create table neuedu_user(
 `id`       int(11)      not null  auto_increment comment '用户id',
 `username`       varchar(50)      not null   comment '用户名',
 `password`       varchar(50)      not null   comment '用户密码,MD5加密',
 `email`       varchar(50)      not null   comment '用户email',
 `phone`       varchar(20)    not null   comment '用户phone',
 `question`      varchar(100)      not null   comment '找回密码问题',
 `answer`      varchar(100)      not null   comment '找回密码答案',
 `role`       int(4)      not null   comment '角色0-管理员,1-普通用户',
 `create_time`       datetime      not null   comment '创建时间',
`update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`),
 UNIQUE KEY `user_name_unique` (`username`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

 alter table  neuedu_user add column token varchar(50) ;

insert into  neuedu_user (username,password,email,phone,question,answer,role,create_time,update_time) values
('admin','admin','xxx@126.com','135654554343','你的大学班主任？','张三',0,now(),now());

# 类别表
create table neuedu_category(
 `id`       int(11)      not null  auto_increment comment '类别id',
 `parent_id`       int(11)      default null   comment '父类Id,当pareng_id=0,说明是根节点，一级类别',
 `name`       varchar(50)      DEFAULT null   comment '类别名称',
 `status`       tinyint(1)      DEFAULT '1'  comment '类别状态1-正常，2-已废弃',
 `sort_order`       int(4)    DEFAULT null   comment '排序编号，同类展示顺序，数值相等则自然排序',
 `create_time`       datetime      not null   comment '创建时间',
`update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=100032 DEFAULT CHARSET=utf8;

 INSERT INTO neuedu_category(parent_id,name,status,sort_order,create_time,update_time) values(0,"phone",1,1,now(),now());



# 产品表
create table neuedu_product(
 `id`       int(11)      not null  auto_increment comment '商品id',
 `category_id`       int(11)      not  null   comment '类别id，对应neuedu_category表的主键',
 `name`       varchar(100)      not null   comment '商品名称',
 `subtitle`       varchar(200)      DEFAULT null   comment '商品副标题',
 `main_image`       varchar(500)      DEFAULT null   comment '产品主图，url相对地址',
 `sub_images`       text       comment '图片地址，json格式',
 `detail`       text        comment '商品详情',
 `price`       DECIMAL (20,2)      not NULL   comment '价格，单位-元保留两位小数',
 `stock`       int(11)   not NULL   comment '库存数量',
 `status`       int(6)    DEFAULT '1'   comment '商品状态，1-在售 2-下架 3-删除',
 `create_time`       datetime      not null   comment '创建时间',
`update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

INSERT INTO neuedu_product(category_id,name,price,stock,status,create_time,update_time) values(100032,"mi6",2000,1000,1,now(),now());
INSERT INTO neuedu_product(category_id,name,price,stock,status,create_time,update_time) values(100032,"mi2",1000,1000,1,now(),now());

 id查询商品信息
 select  p.id productid,p.category_id,p.name,p.subtitle,p.main_image,p.sub_images,p.price,p.stock,p.status,p.create_time,p.update_time,
 c.id categoryid,c.parent_id,c.name,c.status,c.sort_order,c.create_time,c.update_time
 from  neuedu_product p
 LEFT JOIN  neuedu_category c
 ON   p.category_id=c.id
 where  p.id=10000;

  查询类别下的所有商品
   Category
        List<Product> productList;

  select  p.id productid,p.category_id,p.name,p.subtitle,p.main_image,p.sub_images,p.price,p.stock,p.status,p.create_time,p.update_time,
 c.id categoryid,c.parent_id,c.name,c.status,c.sort_order,c.create_time,c.update_time
 from  neuedu_category c
 LEFT JOIN   neuedu_product p
 ON   p.category_id=c.id
 where c.id=100032;

   id   parent_id      catename
    1       0              电子产品
    2       1              手机
    3       1             电脑
    4       2               华为手机


INSERT INTO neuedu_category(parent_id,name,status,sort_order,create_time,update_time) values(100032,"huawei",1,1,now(),now());
INSERT INTO neuedu_category(parent_id,name,status,sort_order,create_time,update_time) values(100032,"xiaomi",1,1,now(),now());



# 购物车表
drop table  neuedu_cart;
create table neuedu_cart(
 `id`       int(11)      not null  auto_increment,
 `user_id`       int(11)      not  null  ,
 `product_id`       int(11)      not  null  ,
 `quantity`       int(11)      not null   comment '商品数量',
 `checked`       int(11)      DEFAULT null   comment '是否选择，1=已勾选，0=未勾选',
 `create_time`       datetime      not null   comment '创建时间',
`update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`),
 key `user_id_index`(`user_id`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;




























