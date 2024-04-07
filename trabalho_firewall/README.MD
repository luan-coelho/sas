### Comando para rodar o serviço Joomla

```bash
docker run -d -p 443:443 luancoelhobrjoomla-sas:1.0
```

### Rodar script no Iptables

Tornar o script executável

```bash
chmod +x /usr/local/bin/script.sh
```

### A hora do firewall deverá estar sincronizada via NTP com os servidores do NIC BR (a.ntp.br, b.ntp.br, c.ntp.br)

Instalar dependências (se ainda não estiver instalado):

```bash
apt install -y ntpdate
apt install -y ntp
```

No arquivo seguinte:

```bash
nano /etc/ntp.conf
```

Escreve isso:

```
server a.ntp.br
server b.ntp.br
server c.ntp.br
```

<!-- iptables -A INPUT -p udp --sport 123 -j ACCEPT
iptables -A OUTPUT -p udp --dport 123 -j ACCEPT
ntpq -p -->

#### Sincronizar com servidor NTP.br

```bash
ntpdate -q a.ntp.br
```

```bash
ntpdate -q b.ntp.br
```

```bash
ntpdate -q c.ntp.br
```
