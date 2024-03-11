# Serviço Nginx com TLS e Aplicação Joomla

Este passo a passo fornece instruções sobre como configurar um servidor Nginx com suporte a TLS e hospedar uma aplicação Joomla, utilizando Docker.

### Pré-requisitos

- Docker instalado e funcionando em seu sistema.
- Acesso ao terminal ou linha de comando.

## Dockerfile

### Imagem container
Define a imagem base como PHP 8.2 FPM.

### Instalação de Dependências
- `RUN apt-get update && apt-get install -y`: Atualiza a lista de pacotes e instala dependências necessárias, incluindo o servidor web Nginx e bibliotecas para processamento de imagens, manipulação de arquivo zip, e transferência de dados via linha de comando.

### Configuração do PHP
- `docker-php-ext-configure` e `docker-php-ext-install`: Configura e instala extensões PHP necessárias para a aplicação Joomla.

### Download e Instalação do Joomla
- Utiliza `curl` para baixar o Joomla e `unzip` para extrair seu conteúdo para `/var/www/html`, preparando o ambiente para a aplicação.

### Configuração do Nginx
- Remove configuração padrão e copia a personalizada para os diretórios de sites disponíveis e habilitados.

### Geração de Certificado SSL Autoassinado
O comando a seguir gera um certificado SSL autoassinado para uso com o servidor Nginx. O certificado é válido por 365 dias e usa uma chave RSA de 3096 bits.
```shell
RUN openssl req -x509 -nodes -days 365 -newkey rsa:3096 -keyout /etc/ssl/private/nginx-selfsigned.key -out /etc/ssl/certs/nginx-selfsigned.crt -subj "/C=BR/ST=TO/L=Palmas/O=Unitins/CN=www.unitins.br"
```

* `openssl req -x509` indica a geração de um certificado X.509 usando OpenSSL.
* `-nodes` especifica que a chave privada não deve ser criptografada.
* `-days 365` define a validade do certificado para 365 dias.
* `-newkey rsa:3096` cria uma nova chave RSA de 3096 bits.
* `-keyout /etc/ssl/private/nginx-selfsigned.key` salva a chave privada no caminho especificado.
* `-out /etc/ssl/certs/nginx-selfsigned.crt` salva o certificado no caminho especificado.
* `-subj "/C=BR/ST=TO/L=Palmas/O=Unitins/CN=www.unitins.br"` especifica os detalhes do sujeito do certificado:

#### -subj

* C=BR indica o país (Brasil).
* ST=TO representa o estado (Tocantins).
* L=Palmas é a localidade/cidade (Palmas).
* O=Unitins é a organização (Universidade do Tocantins).
* CN=www.unitins.br domínio.

## Configuração Nginx (nginx.conf)

Configura o servidor Nginx para:
- Escutar na porta 443 com SSL/TLS.
- Usar o certificado SSL autoassinado.
- Servir arquivos da aplicação Joomla do diretório `/var/www/html`.
- Processar arquivos PHP via FastCGI.

#### Configurações arquivo
* `listen 443 ssl;` Instrui o Nginx a escutar na porta 443 e habilita o SSL.
* `ssl_certificate` /etc/ssl/certs/nginx-selfsigned.crt;  Local do arquivo de certificado SSL.
* `ssl_certificate_key` /etc/ssl/private/nginx-selfsigned.key; Local do arquivo de chave privada SSL.
* `ssl_protocols TLSv1.3;` Especifica o protocolo de segurança. TLSv1.3 é a versão mais recente e segura do TLS.
* `location / ` Define regras de tratamento de requisições para qualquer URL que comece com "/".
* `location ~ \.php$ ` Regras para arquivos com extensão ".php":

## Script de Entrada (entrypoint.sh)
- Copia e torna executável o script que inicia os serviços PHP-FPM e Nginx.
- Define `entrypoint.sh` como o comando inicial do container.

Onde:
- `php-fpm -D`: Inicia o PHP-FPM em modo daemon.
- `nginx -g 'daemon off;'`: Inicia o Nginx em primeiro plano para manter o container ativo.
