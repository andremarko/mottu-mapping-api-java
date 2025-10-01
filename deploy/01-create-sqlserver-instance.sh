#!/bin/bash

source ./env-config.sh

echo "Vericando login no Azure..."
if ! az account show; then
    echo "Login não encontrado, executando az login..."
    az login
fi 

# === Criando Resource Group

az group create --name $RESOURCE_GROUP --location $LOCATION 
echo "Resource Group criado com sucesso"


# === Criando SQL Server
echo "CRIANDO SERVIDOR"
az sql server create -l $LOCATION -g $RESOURCE_GROUP -n $SERVER_NAME -u $DB_ADMIN -p $DB_PASSWORD --enable-public-network true 


# === Criando banco de dados
echo "CRIANDO BANCO DE DADOS"
az sql db create -g $RESOURCE_GROUP -s $SERVER_NAME -n $DB_NAME --service-objective Basic --backup-storage-redundancy Local --zone-redundant false 

# === Regras de firewall
echo "CRIANDO REGRAS DE FIREWALL"

# Web App / Aure Services
az sql server firewall-rule create -g $RESOURCE_GROUP -s $SERVER_NAME -n AllowAzureServices --start-ip-address 0.0.0.0 --end-ip-address 0.0.0.0 

# Acesso local
az sql server firewall-rule create -g $RESOURCE_GROUP -s $SERVER_NAME -n AllowLocally --start-ip-address $MY_PUBLIC_IP --end-ip-address $MY_PUBLIC_IP

echo "RECURSOS CRIADOS"
az resource list -g $RESOURCE_GROUP -o table







