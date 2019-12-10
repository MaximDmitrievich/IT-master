# Отчет по лабораторной работе 3

## Создаем пользователей

```bash
[root@centos7-vm vagrant]\# useradd -m -s /bin/bash user1
[root@centos7-vm vagrant]\# useradd -m -s /bin/bash user2
```

* Какие UID создались у пользователей?
  * 1001 для user1 и 1002 для user2

```bash
[root@centos7-vm vagrant]\# id user1
uid=1001(user1) gid=1001(user1) groups=1001(user1)
[root@centos7-vm vagrant]\# id user2
uid=1002(user2) gid=1002(user2) groups=1002(user2)
```

* Что означают опции -m и -s
  * -s - имя оболочки пользователя. В данном случае bash
  * -m - создать домашнюю директорию для пользователя по пути /bin/bash

## Создаем группу и добавляем туда пользователей

```bash
[root@centos7-vm vagrant]\# groupadd admins
[root@centos7-vm vagrant]\# gpasswd -a user1 admins
Adding user user1 to group admins
[root@centos7-vm vagrant]\# gpasswd -a user2 admins
Adding user user2 to group admins
[root@centos7-vm vagrant]\# id user1
uid=1001(user1) gid=1001(user1) groups=1001(user1),1003(admins)
[root@centos7-vm vagrant]\# id user2
uid=1002(user2) gid=1002(user2) groups=1002(user2),1003(admins)
```

### Через usermod сделайте группу admins основной для  user1

```bash
[root@centos7-vm vagrant]\# usermod -g admins user1
[root@centos7-vm vagrant]\# id user1
uid=1001(user1) gid=1003(admins) groups=1003(admins)
```

## Создать каталог от рута и дать права группе admins туда писать

```bash
[root@centos7-vm vagrant]\# mkdir /opt/upload
[root@centos7-vm vagrant]\# chmod 770 /opt/upload
[root@centos7-vm vagrant]\# chgrp admins /opt/upload
...
[root@centos7-vm opt]\# ls -l
total 0
drwxrwx---. 2 root admins 6 Nov 14 21:06 upload
```

* Что означают права 770?
  * Означают выдачу прав на запись, чтение и исполнение для владельцов файла и группы владельцов. Для остальных пользователей права не выданы
* Создать по файлу от пользователей user1 и user2 в каталоге /opt/uploads
  * Выкладка ниже

```bash
[root@centos7-vm upload]\# ls -l
total 0
-rw-r--r--. 1 user1 admins 0 Nov 14 21:14 file
-rw-rw-r--. 1 user2 user2  0 Nov 14 21:14 file1
```

* Проверить с какой группой создались файлы от каждого пользователя. Почему?
  * user1 с группой admins, user2 - user2. Потому что для user1 мы сделали основной группой admins, а для user2 основной группой admins мы не назначали.
* Сменить текущую группу пользователя newgrp admins у пользователя user2 и создать еще файл

```bash
[user2@centos7-vm upload]$ newgrp admins
[user2@centos7-vm upload]$ touch file2
...
[root@centos7-vm upload]\# ls -l
total 0
-rw-r--r--. 1 user1 admins 0 Nov 14 21:14 file
-rw-rw-r--. 1 user2 user2  0 Nov 14 21:14 file1
-rw-r--r--. 1 user2 admins 0 Nov 14 21:23 file2
```

## Создать пользователя user3 и дать ему права писать в /opt/uploads

```bash
[user3@centos7-vm vagrant]$ touch /opt/upload/file4
touch: cannot touch ‘/opt/upload/file4’: Permission denied
...
[root@centos7-vm upload]\# getfacl /opt/upload
getfacl: Removing leading '/' from absolute path names
\# file: opt/upload
\# owner: root
\# group: admins
user::rwx
group::rwx
other::---
[root@centos7-vm upload]\# setfacl -m u:user3:rwx /opt/upload
...
[user3@centos7-vm vagrant]$ touch /opt/upload/user3_file
...
[root@centos7-vm upload]\# ls -l
total 0
-rw-r--r--. 1 user1 admins 0 Nov 14 21:14 file
-rw-rw-r--. 1 user2 user2  0 Nov 14 21:14 file1
-rw-r--r--. 1 user2 admins 0 Nov 14 21:23 file2
-rw-rw-r--. 1 user3 user3  0 Nov 14 21:28 user3_file
[root@centos7-vm upload]\# getfacl /opt/upload
getfacl: Removing leading '/' from absolute path names
\# file: opt/upload
\# owner: root
\# group: admins
user::rwx
user:user3:rwx
group::rwx
mask::rwx
other::---
```

## Установить GUID флаг на директорию /opt/uploads

```bash
[root@centos7-vm upload]# chmod g+s /opt/upload
...
[user3@centos7-vm vagrant]$ touch /opt/upload/user3_file2
...
[root@centos7-vm upload]# ls -l
total 0
-rw-r--r--. 1 user1 admins 0 Nov 14 21:14 file
-rw-rw-r--. 1 user2 user2  0 Nov 14 21:14 file1
-rw-r--r--. 1 user2 admins 0 Nov 14 21:23 file2
-rw-rw-r--. 1 user3 user3  0 Nov 14 21:28 user3_file
-rw-rw-r--. 1 user3 admins 0 Nov 14 21:30 user3_file2
```

* Почему новый файл имеет группу admins?
  * Команда chmod g+s . устанавливает идентификатор группы (setgid) в текущем каталоге, написанный как '.'. Это означает, что все новые файлы и подкаталоги, созданные в текущем каталоге, наследуют идентификатор группы в каталоге, а не основной идентификатор группы пользователя, создавшего файл. Это также будет передано в новые подкаталоги, созданные в текущем каталоге

## Установить  SUID  флаг на выполняемый файл

```bash
[user3@centos7-vm vagrant]$ cat /etc/shadow
cat: /etc/shadow: Permission denied
...
[root@centos7-vm bin]# chmod u+s /bin/cat
...
[user3@centos7-vm vagrant]$ cat /etc/shadow
root:$1$.tZDnyHU$xLRdN8B4IhzQe3wm0DGfr/:18214:0:99999:7:::
bin:*:17834:0:99999:7:::
daemon:*:17834:0:99999:7:::
adm:*:17834:0:99999:7:::
lp:*:17834:0:99999:7:::
sync:*:17834:0:99999:7:::
shutdown:*:17834:0:99999:7:::
halt:*:17834:0:99999:7:::
mail:*:17834:0:99999:7:::
operator:*:17834:0:99999:7:::
games:*:17834:0:99999:7:::
ftp:*:17834:0:99999:7:::
nobody:*:17834:0:99999:7:::
systemd-network:!!:18048::::::
dbus:!!:18048::::::
polkitd:!!:18048::::::
rpc:!!:18048:0:99999:7:::
rpcuser:!!:18048::::::
nfsnobody:!!:18048::::::
sshd:!!:18048::::::
postfix:!!:18048::::::
chrony:!!:18048::::::
vagrant:$1$C93uBBDg$pqzqtS3a9llsERlv..YKs1::0:99999:7:::
dockerroot:!!:18214::::::
user1:$1$eFnzwAnQ$8qMUek8PdX5u.4XyrWFuN0:18214:0:99999:7:::
user2:$1$bWacL4I1$Pw.EUiBLbbsS5GcWPA28U.:18214:0:99999:7:::
user3:$1$sdgpQ1WT$fVXUMb8qciEG/gvXtiALR1:18214:0:99999:7:::
```

* Почему?
  * После присвоения suid-бита на cat, при выполнении этой утилиты пользователем user3 после он получает права такие же, какие и были повешены на cat пользователем root. Проще говоря, после выполнения этой команды пользователем user3 получил права root'а.

## Сменить владельца  /opt/uploads  на user3 и добавить sticky bit

```bash
[root@centos7-vm upload]\# chown user3 /opt/upload
[root@centos7-vm upload]\# chmod +t /opt/upload
...
[user3@centos7-vm vagrant]$ touch /opt/upload/user3_file2
...
[root@centos7-vm upload]\# ls -l
total 0
-rw-r--r--. 1 user1 admins 0 Nov 14 21:14 file
-rw-rw-r--. 1 user2 user2  0 Nov 14 21:14 file1
-rw-r--r--. 1 user2 admins 0 Nov 14 21:23 file2
-rw-r--r--. 1 user1 admins 0 Nov 14 21:34 user1_file_test
-rw-rw-r--. 1 user3 user3  0 Nov 14 21:28 user3_file
-rw-rw-r--. 1 user3 admins 0 Nov 14 21:30 user3_file2
...
[vagrant@centos7-vm ~]$ su user1
Password:
[user1@centos7-vm vagrant]$ touch /opt/upload/user1_file_test
[user1@centos7-vm vagrant]$ exit
exit
[vagrant@centos7-vm ~]$ su user3
Password:
[user3@centos7-vm vagrant]$ rm -f  /opt/upload/user1_file_test
...
[root@centos7-vm upload]\# ls -l
total 0
-rw-r--r--. 1 user1 admins 0 Nov 14 21:14 file
-rw-rw-r--. 1 user2 user2  0 Nov 14 21:14 file1
-rw-r--r--. 1 user2 admins 0 Nov 14 21:23 file2
-rw-rw-r--. 1 user3 user3  0 Nov 14 21:28 user3_file
-rw-rw-r--. 1 user3 admins 0 Nov 14 21:30 user3_file2
```

* Объясните почему user3 смог удалить файл, который ему не принадлежит
  * Командой chown мы назначили владельцем папки пользователя user3 и все файлы в ней может удалить только он. Никто больше не имеет права этого делать. (Sticky Bit)
* Создайте теперь файл от user1 и удалите его пользователем user1
  * Не получится. Объяснения выше.
* Объясните результат
  * ...

## Записи в sudoers

```bash
[user3@centos7-vm vagrant]$ sudo ls -l /root

We trust you have received the usual lecture from the local System
Administrator. It usually boils down to these three things:

    #1) Respect the privacy of others.
    #2) Think before you type.
    #3) With great power comes great responsibility.

[sudo] password for user3:
user3 is not in the sudoers file.  This incident will be reported.
```

### После прописывания user3 в sudoers

```bash
[user3@centos7-vm vagrant]$ sudo ls -l /root
total 16
-rw-------. 1 root root 5570 Jun  1 17:18 anaconda-ks.cfg
drwxr-xr-x. 2 root root   27 Nov 14 17:22 myfiles
-rw-------. 1 root root 5300 Jun  1 17:18 original-ks.cfg
```

* Почему у вас не получилось?
  * user3 не был прописан в группу суперпользователя и поэтому не мог просматривать домашний каталог пользователя root, который находится в группе sudoers

```bash
[user1@centos7-vm vagrant]$ sudo ls -l /root

We trust you have received the usual lecture from the local System
Administrator. It usually boils down to these three things:

    \#1) Respect the privacy of others.
    \#2) Think before you type.
    \#3) With great power comes great responsibility.

[sudo] password for user1:
```
