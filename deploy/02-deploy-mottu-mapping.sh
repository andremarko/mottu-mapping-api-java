#!/bin/bash

JDBC_CONNECTION_STRING=$(az sql db show-connection-string --client jdbc --name $DB_NAME --server $SERVER_NAME --output tsv)

az sql db show-connection-string --client jdbc --name $DB_NAME --server $SERVER_NAME --output tsv

az appservice plan create --name $APP_SERVICE_PLAN --resource-group $RESOURCE_GROUP --location $LOCATION --sku B1 --is-linux

az webapp create --name "$WEBAPP_NAME" --resource-group $RESOURCE_GROUP --plan $APP_SERVICE_PLAN --runtime $RUNTIME

az webapp config appsettings set --name $WEBAPP_NAME --resource-group $RESOURCE_GROUP --settings DB_ADMIN=$DB_ADMIN DB_PASSWORD=$DB_PASSWORD JDBC_CONNECTION_STRING="$JDBC_CONNECTION_STRING"

az webapp restart --name $WEBAPP_NAME --resource-group $RESOURCE_GROUP
