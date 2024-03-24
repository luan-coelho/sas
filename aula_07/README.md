## VM fw_iptables_Unitins

### Instalar MariaDB

[Passo a passo](https://www.digitalocean.com/community/tutorials/how-to-install-mariadb-on-debian-11)

### Configuração do MariaDB no Debian com Acesso Externo

Este guia mostra como instalar e configurar o MariaDB em um servidor Debian, incluindo a criação de um banco de dados e configuração para acesso externo.

#### Instalar o MariaDB

Atualize os pacotes e instale o MariaDB:

```bash
apt update
apt install mariadb-server
mysql_secure_installation
```
#### Permitir Acesso Remoto

```bash
nano /etc/mysql/mariadb.conf.d/50-server.cnf
```

Modifique a linha com bind-address para:
`bind-address = 0.0.0.0`

Salve e saia do arquivo. Reinicie o MariaDB:
```bash
systemctl restart mariadb
```

#### Criar Banco de Dados e Usuário
Acesse o MariaDB e crie um banco de dados e um usuário:
```bash
mysql -u root -p
```

Dentro do MariaDB, execute:
```mariadb
CREATE DATABASE nome_do_banco;
CREATE USER 'nome_do_usuario'@'%' IDENTIFIED BY 'senha_forte';
GRANT ALL PRIVILEGES ON nome_do_banco.* TO 'nome_do_usuario'@'%';
FLUSH PRIVILEGES;
EXIT;
```