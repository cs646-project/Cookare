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
update_user, INTEGER, FOREIGN KEY OF USER @rename to update_user
content, VARCHAR
tags, VARCHAR @changeToInt
create_time, TIMESTAMP
update_time, TIMESTAMP
delete_flg, INTEGER 
likes_num, INTEGER @drop
collect_num, INTEGER @drop
comment_num, INTEGER @drop
cover_url, VARCHAR
ingredients, VARCHAR @add
```

#### INGREDIENTS

```
id, INTEGER, KEY, AUTO
name, VARCHAR
recipe_id, INTEGER, FOREIGN KEY OF RECIPE
num, INTEGER
quantifier, VARCHAR
```

#### USER_RECIPE_REL @Dropped

```
id, INTEGER, KEY, AUTO
user_id, INTEGER, FOREIGN KEY OF USER
recipt_id, INTEGER
type, INTEGER # 0->collection, 1->like
create_time, TIMESTAMP
update_time, TIMESTAMP
delete_flg, INTEGER
```

#### COMMENT@Dropped

```
id, INTEGER, KEY, AUTO
update_user, INTEGER, FOREIGN KEY OF USER
recipe_id, INTEGER, FOREIGN KEY OF RECIPE
content, VARCHAR
create_time, TIMESTAMP
update_time, TIMESTAMP
delete_flg, INTEGER
```

