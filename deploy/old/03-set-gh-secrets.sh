#!/bin/bash
set -e 

source ./env-config.sh

gh auth status || gh auth login

gh secret set JDBC_CONNECTION_STRING --repo $GITHUB_REPO --body "$JDBC_CONNECTION_STRING"

gh secret set DB_ADMIN --repo $GITHUB_REPO --body $DB_ADMIN

gh secret set DB_PASSWORD --repo $GITHUB_REPO --body $DB_PASSWORD
