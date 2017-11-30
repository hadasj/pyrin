#!/bin/bash

#DB_NAME=${POSTGRES_DB:-}
#DB_USER=${POSTGRES_USER:-}
#DB_PASS=${POSTGRES_PASSWORD:-}

__run_supervisor() {
supervisord -n
}

# Call all functions
__run_supervisor

