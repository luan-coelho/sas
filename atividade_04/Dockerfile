FROM php:8.2-fpm

# Instala as dependências
RUN apt-get update && apt-get install -y \
    nginx \
    libzip-dev \
    libpng-dev \
    libjpeg-dev \
    libxml2-dev \
    unzip \
    curl && \
    docker-php-ext-configure gd --with-jpeg && \
    docker-php-ext-install gd mysqli xml zip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* 

# Baixa Joomla 5.0.3 e extrai para uma pasta
RUN curl -o joomla.zip -SL https://downloads.joomla.org/cms/joomla5/5-0-3/Joomla_5-0-3-Stable-Full_Package.zip && \
    unzip joomla.zip -d /var/www/html && \
    rm joomla.zip && \
    chown -R www-data:www-data /var/www/html

# Configurar Nginx
RUN rm /etc/nginx/sites-enabled/default
COPY nginx.conf /etc/nginx/sites-available/default
RUN ln -s /etc/nginx/sites-available/default /etc/nginx/sites-enabled/default

# Gera um certificado SSL autoassinado
RUN openssl req -x509 -nodes -days 365 -newkey rsa:3096 -keyout /etc/ssl/private/nginx-selfsigned.key -out /etc/ssl/certs/nginx-selfsigned.crt -subj "/C=BR/ST=TO/L=Palmas/O=Unitins/CN=www.unitins.br"

EXPOSE 443

# Copia o script para o container e torna executável
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Executa script para iniciar PHP-FPM e Nginx
ENTRYPOINT ["/entrypoint.sh"]