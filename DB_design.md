### TABLE

#### USER

```
id, INTEGER, KEY, AUTO
username, VARCHAR
password, VARCHAR
email,VARCHAR
phone,VARCHAR
avatar_url,VARCHAR
tags,VARCHAR
avatar_url, VARCHAR
create_time, TIMESTAMP
update_time, TIMESTAMP
delete_flg, INTEGER
```

#### RECIPE

```
id, INTEGER, KEY, AUTO
title, VARCHAR
author, INTEGER, FOREIGN KEY OF USER
content, VARCHAR
tags, VARCHAR
create_time, TIMESTAMP
update_time, TIMESTAMP
delete_flg, INTEGER
likes_num, INTEGER
collect_num, INTEGER
comment_num, INTEGER
```

#### USER_RECIPE_REL

```
id, INTEGER, KEY, AUTO
user_id, INTEGER, FOREIGN KEY OF USER
recipt_id, INTEGER
type, INTEGER # 0->collection, 1->like
create_time, TIMESTAMP
update_time, TIMESTAMP
delete_flg, INTEGER
```

#### COMMENT

```
id, INTEGER, KEY, AUTO
user_id, INTEGER, FOREIGN KEY OF USER
content, VARCHAR
create_time, TIMESTAMP
update_time, TIMESTAMP
delete_flg, INTEGER
```

