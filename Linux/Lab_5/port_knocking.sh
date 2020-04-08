port knocking - 6699 9966 22 - 60 sec
            # разрешим соединения со статусом est и rel в conntracker
            iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
            # разрешим icmp
            iptables -A INPUT -p icmp --icmp-type any -j ACCEPT
            # создадим цепочку для port knocking
            iptables -N SSH_KNOCK
            # сделаем переход из input в цепочку для port knocking
            iptables -A INPUT -j SSH_KNOCK
            # заранее создадим цепочку для SSH SET
            iptables -N SSH_SET
            # если наш хост есть в списке SSH_STEP2 не более, чем 60 секунд, то пускаем...
            iptables -A SSH_KNOCK -m state --state NEW -m tcp -p tcp -m recent --rcheck --seconds 60 --dport 22 --name SSH_STEP2 -j ACCEPT
            # ... иначе удаляем его из списка (> 60 секунд)
            iptables -A SSH_KNOCK -m state --state NEW -m tcp -p tcp -m recent --name SSH_STEP2 --remove -j DROP
            # если наш хост стучался по порту 9966 и был в списке SSH_STEP1 ...
            iptables -A SSH_KNOCK -m state --state NEW -m tcp -p tcp -m recent --rcheck --dport 9966 --name SSH_STEP1 -j SSH_SET
            # ... то включить его в список SSH_STEP2
            iptables -A SSH_SET -m recent --set --name SSH_STEP2 -j DROP
            # ... иначе - удалить из списка SSH_STEP1
            iptables -A SSH_KNOCK -m state --state NEW -m tcp -p tcp -m recent --name SSH_STEP1 --remove -j DROP
            # если хост стучится по порту 6699, то добавить его в список SSH_STEP1
            iptables -A SSH_KNOCK -m state --state NEW -m tcp -p tcp -m recent --set --dport 6699 --name SSH_STEP1 -j DROP
            # по-умолчанию DROP внутри port knocking
            iptables -A SSH_KNOCK -j DROP
            # по-умолчанию DROP со стороны, откуда будет идти проверка
            iptables -A INPUT -i eth1 -j DROP