#!/bin/bash

export RESOURCE_GROUP="rg-mottu-mapping"
export SERVER_NAME="mottu-mapping-sqlserver"
export DB_ADMIN="mottu-mapping-admin"
export DB_NAME="mottu-mapping-backend-entities"
export DB_PASSWORD="Challenge@2025"


export WEBAPP_NAME="mottu-mapping-mvc"
export APP_SERVICE_PLAN="planMottuMapping"

export LOCATION="westus"
export RUNTIME="JAVA:21-java21"
export GITHUB_REPO="andremarko/mottu-mapping-api-java"
export BRANCH="main"

export MY_PUBLIC_IP=$(curl -s ifconfig.me)

export JDBC_CONNECTION_STRING=$(az sql db show-connection-string --client jdbc --name $DB_NAME --server $SERVER_NAME --output tsv)
