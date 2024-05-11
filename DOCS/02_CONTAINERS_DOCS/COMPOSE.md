# Docker compose

### Create the .env.  This file set environment variables to your containers
```bash
touch .env
```

### Edit the .env file
```bash
TODO_API_VERSION=<SET_THE_VERSION_OF_THE_IMAGE_TO_TEST>
```

### Execute the compose
```bash
docker-compose up -d
```

### see the running containers
```bash
docker-compose ps
```

### see the logs containers
```bash
docker-compose logs
```

### stop the compose
```bash
docker-compose stop
```


### start the compose
```bash
docker-compose start
```

### delete the compose and delete the created volumenes ( -v )
```bash
docker-compose down -v
```


