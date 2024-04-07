#!/bin/bash

RULES_IPV4="/etc/iptables/rules.v4"
FW_IP="192.168.15.170"

function check_rules_file() {
    if [ ! -f "$RULES_IPV4" ]; then
        echo "Arquivo de regras $RULES_IPV4 não encontrado. Criando um arquivo vazio."
        touch "$RULES_IPV4"
    elif [ ! -s "$RULES_IPV4" ]; then
        echo "Aviso: O arquivo de regras $RULES_IPV4 está vazio."
    fi
}

function save_rules() {
    echo "Salvando regras do iptables para IPv4..."
    iptables-save > "$RULES_IPV4"
    echo "Regras IPv4 salvas com sucesso."
}

function restore_rules() {
    echo "Restaurando regras do iptables para IPv4..."
    if iptables-restore < "$RULES_IPV4"; then
        echo "Regras IPv4 restauradas com sucesso."
    else
        echo "Falha ao restaurar as regras IPv4. Verifique se o arquivo $RULES_IPV4 contém regras válidas."
    fi
}

function configure() {
    echo "Configurando regras básicas do iptables para IPv4..."
    echo "1" > /proc/sys/net/ipv4/ip_forward
    iptables -t nat -A POSTROUTING -s 192.168.56.0/24 -j MASQUERADE
    iptables -t nat -A POSTROUTING -s 192.168.57.0/24 -j MASQUERADE
    dhclient enp0s8 && dhclient enp0s9
    iptables -t nat -I PREROUTING -p tcp --dport 9015 -d $FW_IP -i enp0s3 -j DNAT --to 192.168.56.100:443
    iptables -t nat -I PREROUTING -p tcp --dport 9016 -d $FW_IP -i enp0s3 -j DNAT --to 192.168.56.100:80
    
    echo "Regras básicas configuradas. Lembre-se de salvar as regras."
}

function show_usage() {
    echo "Uso: $0 {save|restore|configure}"
    echo "save       - Salva as regras atuais do iptables para IPv4."
    echo "restore    - Restaura as regras do iptables para IPv4 a partir do arquivo salvo."
    echo "configure  - Configura um conjunto básico de regras de segurança para o iptables."
}

#check_rules_file

case "$1" in
    save)
        save_rules
        ;;
    restore)
        restore_rules
        ;;
    configure)
        configure
        ;;
    *)
        show_usage
        ;;
esac
