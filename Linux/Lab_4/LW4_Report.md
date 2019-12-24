# Отчет по лабораторной работе 4

## Создать базу для wordpress

```bash
[root@centos7-vm ~]\# systemctl start mysqld


[root@centos7-vm ~]\# mysql -u root
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 2
Server version: 5.7.28 MySQL Community Server (GPL)
mysql> CREATE DATABASE wordpress DEFAULT CHARACTER SET utf8mb4;
Query OK, 1 row affected (0.00 sec)
mysql> GRANT ALL ON wordpress.* TO 'wordpressuser'@'localhost' IDENTIFIED BY 'password';
Query OK, 0 rows affected, 1 warning (0.00 sec)

mysql>  FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.00 sec)
```

## Установить профиль wordpress

```bash
[root@centos7-vm ~]\# cd /tmp
[root@centos7-vm tmp]\# curl -O https://wordpress.org/latest.tar.gz
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100 11.8M  100 11.8M    0     0   363k      0  0:00:33  0:00:33 --:--:--  291k
[root@centos7-vm tmp]\# cd /var/www/wordpress/
[root@centos7-vm wordpress]\# cp wp-config-sample.php wp-config.php
[root@centos7-vm wordpress]\# useradd www-data
[root@centos7-vm wordpress]\# id www-data
uid=1004(www-data) gid=1005(www-data) groups=1005(www-data)
[root@centos7-vm wordpress]\# chown -R www-data:www-data /var/www/wordpress/
[root@centos7-vm wordpress]\# find /var/www/wordpress/ -type d -exec chmod 750 {} \;
[root@centos7-vm wordpress]\# find /var/www/wordpress/ -type f -exec chmod 640 {} \;
```

## Редактирование конфигурационного файла wp-config.php

```bash
[root@centos7-vm wordpress]\# curl -s https://api.wordpress.org/secret-key/1.1/salt/
define('AUTH_KEY',         '(!u@bM+@c-~}YX->->Hw++i;R&(yV2s@vbs!?wO|ca}gw|J:*/F_84XYaf[+]E=9');
define('SECURE_AUTH_KEY',  '#cGX+mz9,p3R|VJ%-n){$2CJ!Avq:)Zn6gvS+WNf;.|7t:@(T!Og4-{(pf([I4uh');
define('LOGGED_IN_KEY',    '[B@o,?:WP1 mcqZ<T$L~MZz`pd@_g4j~R6PJlB>uc^32+:]!qUYZfi3T)&L/*wm<');
define('NONCE_KEY',        'GquL n0-2*v&L-*BJLj}.mHmU|.g;[k2~otE_+f-X=uqTZE:K`Dy+3ZbOU+1U|z.');
define('AUTH_SALT',        '?}O jXX;Q>Xv`zLFxWXc.FzZ::j:s9W|+as2I36YZMW!]T4!RcY#Yw+P^r(,X|/}');
define('SECURE_AUTH_SALT', 'Xk@^V1XQ?cj3fMBA@0}uk-yi5iAfbM;M51x3V=r%Yp]T~ `]^-ud{tA3Z|Y0YJZY');
define('LOGGED_IN_SALT',   '<&W<0Wg`mDM,APGR$d1A#@Xp5L56&O(r6{}LevKo,221Uf4@k|>|72jJ|VG~_cGX');
define('NONCE_SALT',       ',pGaQDt)({ywq;C[++N^3b;iUW_@;A?=zMc$l21qrd.|wO=820?Md2[voVU~)/*j');
```

