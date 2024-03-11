# Use the official PHP image with FPM
FROM php:8.2-fpm

# Install dependencies for Joomla, Nginx, and SSL
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

# Download Joomla 5.0.3 and extract it
RUN curl -o joomla.zip -SL https://downloads.joomla.org/cms/joomla5/5-0-3/Joomla_5-0-3-Stable-Full_Package.zip && \
    unzip joomla.zip -d /var/www/html && \
    rm joomla.zip && \
    chown -R www-data:www-data /var/www/html

# Configure Nginx
RUN rm /etc/nginx/sites-enabled/default
COPY nginx.conf /etc/nginx/sites-available/default
RUN ln -s /etc/nginx/sites-available/default /etc/nginx/sites-enabled/default

# Generate a self-signed SSL certificate
RUN openssl req -x509 -nodes -days 365 -newkey rsa:3096 -keyout /etc/ssl/private/nginx-selfsigned.key -out /etc/ssl/certs/nginx-selfsigned.crt -subj "/C=US/ST=Denial/L=Springfield/O=Dis/CN=www.unitins.br"

# Expose port 443 for SSL
EXPOSE 443

# Copy entrypoint script and make it executable
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Use the entrypoint script to start PHP-FPM and Nginx
ENTRYPOINT ["/entrypoint.sh"]