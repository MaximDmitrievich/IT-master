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
mysql>  GRANT ALL ON wordpress.* TO 'wordpressuser'@'localhost' IDENTIFIED BY '1122qqww'
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

## Запуск php-fpm

```bash
[root@centos7-vm wordpress]\# systemctl start php-fpm.service
[root@centos7-vm wordpress]\# systemctl status php-fpm.service
● php-fpm.service - The PHP FastCGI Process Manager
   Loaded: loaded (/etc/systemd/system/php-fpm.service; bad; vendor preset: disabled)
   Active: failed (Result: exit-code) since Wed 2019-12-25 09:38:28 UTC; 7s ago
  Process: 5127 ExecStart=/etc/php-fpm --nodaemonize --fpm-config @EXPANDED_SYSCONFDIR@/php-fpm.conf (code=exited, status=203/EXEC)
 Main PID: 5127 (code=exited, status=203/EXEC)

Dec 25 09:38:28 centos7-vm systemd[1]: [/etc/systemd/system/php-fpm.service:10] Failed to parse service type, ignoring: @php_fpm_systemd@
Dec 25 09:38:28 centos7-vm systemd[1]: [/etc/systemd/system/php-fpm.service:35] Unknown lvalue 'ProtectKernelModules' in section 'Service'
Dec 25 09:38:28 centos7-vm systemd[1]: [/etc/systemd/system/php-fpm.service:42] Unknown lvalue 'ProtectKernelTunables' in section 'Service'
Dec 25 09:38:28 centos7-vm systemd[1]: [/etc/systemd/system/php-fpm.service:48] Unknown lvalue 'ProtectControlGroups' in section 'Service'
Dec 25 09:38:28 centos7-vm systemd[1]: [/etc/systemd/system/php-fpm.service:51] Unknown lvalue 'RestrictRealtime' in section 'Service'
Dec 25 09:38:28 centos7-vm systemd[1]: [/etc/systemd/system/php-fpm.service:58] Unknown lvalue 'RestrictNamespaces' in section 'Service'
Dec 25 09:38:28 centos7-vm systemd[1]: Started The PHP FastCGI Process Manager.
Dec 25 09:38:28 centos7-vm systemd[1]: php-fpm.service: main process exited, code=exited, status=203/EXEC
Dec 25 09:38:28 centos7-vm systemd[1]: Unit php-fpm.service entered failed state.
Dec 25 09:38:28 centos7-vm systemd[1]: php-fpm.service failed.
```

## Настройка nginx

```bash
server {
  listen 8080;

  access_log /var/log/nginx/wordpress_access.log;
  error_log /var/log/nginx/wordpress_error.log;
    root   /var/www/wordpress;
    index index.php index.html index.htm;

    location / {
        try_files $uri $uri/ =404;
    }
    error_page 404 /404.html;
    location = /50x.html {
        root /var/www/wordpress;
    }

    location ~ \.php$ {
        try_files $uri =404;
        fastcgi_pass unix:/run/php/php7.2-fpm.sock;
        fastcgi_index index.php;
        fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
        include fastcgi_params;
    }

}


[root@centos7-vm sites-enabled]# systemctl start nginx
[root@centos7-vm sites-enabled]# systemctl reload nginx
[root@centos7-vm sites-enabled]# systemctl start nginx
[root@centos7-vm sites-enabled]# systemctl status nginx
● nginx.service - The nginx HTTP and reverse proxy server
   Loaded: loaded (/usr/lib/systemd/system/nginx.service; disabled; vendor preset: disabled)
   Active: active (running) since Wed 2019-12-25 09:40:48 UTC; 10s ago
  Process: 5287 ExecReload=/bin/kill -s HUP $MAINPID (code=exited, status=0/SUCCESS)
  Process: 5275 ExecStart=/usr/sbin/nginx (code=exited, status=0/SUCCESS)
  Process: 5272 ExecStartPre=/usr/sbin/nginx -t (code=exited, status=0/SUCCESS)
  Process: 5271 ExecStartPre=/usr/bin/rm -f /run/nginx.pid (code=exited, status=0/SUCCESS)
 Main PID: 5277 (nginx)
   CGroup: /system.slice/nginx.service
           ├─5277 nginx: master process /usr/sbin/nginx
           └─5288 nginx: worker process

Dec 25 09:40:48 centos7-vm systemd[1]: Starting The nginx HTTP and reverse proxy server...
Dec 25 09:40:48 centos7-vm nginx[5272]: nginx: the configuration file /etc/nginx/nginx.conf syntax is ok
Dec 25 09:40:48 centos7-vm nginx[5272]: nginx: configuration file /etc/nginx/nginx.conf test is successful
Dec 25 09:40:48 centos7-vm systemd[1]: Failed to parse PID from file /run/nginx.pid: Invalid argument
Dec 25 09:40:48 centos7-vm systemd[1]: Started The nginx HTTP and reverse proxy server.
Dec 25 09:40:51 centos7-vm systemd[1]: Reloading The nginx HTTP and reverse proxy server.
Dec 25 09:40:51 centos7-vm systemd[1]: Reloaded The nginx HTTP and reverse proxy server.
```

### Результат

```bash
[root@centos7-vm sites-enabled]\# ls -l /etc/nginx/sites-enabled
total 4
-rw-r--r--. 1 root root 606 Dec 25 09:40 wordpress
```

## Включим расширение mysqli в /etc/php/7.2/fpm/php.ini

```bash
[PHP]
extension=mysqli
;;;;;;;;;;;;;;;;;;                                                                                                                                            ; About php.ini   ;
```