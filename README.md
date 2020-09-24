# TaskSelfDefined
自定义任务
生产者消费者
mpp-hdfs

mpp给id加上自增操作步骤：
su scadmin
cd /usr/local/tbds/tbdsmpp-db
cd bin

ssql -U dwt -d  dwtmppdb -h 172.19.1.49  -p 7300
\d dwttab;


                 Table "public.dwttab"
       Column       |         Type          | Modifiers
--------------------+-----------------------+-----------
 id                 | integer               | not null
 dept               | character(50)         | not null
 emp_id             | integer               | not null
 filter_repeat_data | character varying(33) |
Indexes:
    "dwttab_pkey" PRIMARY KEY, btree (id)
Distributed by: (id)

create sequence sequence_name start with 3;
alter table dwttab alter column id set default nextval('sequence_name'::regclass);

insert into dwttab (dept,emp_id) values('222',333);
insert into dwttab (dept,emp_id) values('222',333);

\q
退出




