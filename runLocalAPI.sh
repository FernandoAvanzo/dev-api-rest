#!/bin/bash

#!/bin/bash

docker build -t api_local_image .
docker run -d -p 80:9292 --name api_local_container api_local_image

delay=5
max_attempts=12
attempt=1

while ((attempt <= max_attempts)); do
  status_code=$(curl -o /dev/null -s -w "%{http_code}\n" http://localhost/health/check)
  echo "Attempt $attempt: Status code $status_code"

  if [ "$status_code" -eq 200 ]; then
    echo "API is up and running."
    exit 0
  fi

  echo "API is not yet running. Retrying in $delay seconds..."
  ((attempt++))
  sleep $delay
done

echo "API failed to start after $max_attempts attempts."
exit 1