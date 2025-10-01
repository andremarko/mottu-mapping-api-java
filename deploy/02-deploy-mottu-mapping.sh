#!/bin/bash

source ./env-config.sh

az appservice plan create --name $APP_SERVICE_PLAN --resource-group $RESOURCE_GROUP --location $LOCATION --sku B1 --is-linux

az webapp create --name "$WEBAPP_NAME" --resource-group $RESOURCE_GROUP --plan $APP_SERVICE_PLAN --runtime $RUNTIME

az resource update --resource-group $RESOURCE_GROUP --namespace Microsoft.Web --resource-type basicPublishingCredentialsPolicies --name scm --parent sites/$WEBAPP_NAME --set properties.allow=true

az webapp config appsettings set --name $WEBAPP_NAME --resource-group $RESOURCE_GROUP --settings DB_ADMIN=$DB_ADMIN DB_PASSWORD=$DB_PASSWORD JDBC_CONNECTION_STRING="$JDBC_CONNECTION_STRING"

az webapp restart --name $WEBAPP_NAME --resource-group $RESOURCE_GROUP

az webapp deployment github-actions add --name $WEBAPP_NAME --resource-group $RESOURCE_GROUP --repo $GITHUB_REPO --branch $BRANCH --login-with-github
