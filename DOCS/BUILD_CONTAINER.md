# Building Container

Instructions to build the image the first time


```bash
docker build -t  <IMAGE_NAME>:<VERSION>  .
```

### Create the image for the project
```bash
docker build -t  springboot-todo-h2-api-docker:0.1  .
```

### Create the image for production
```bash
docker build --target production -t springboot-todo-h2-api-docker:v1 .
```


### Check your images
```bash
docker images
```

### Run locally your image
```bash
docker run -d -p 8080:8080 --name app01 springboot-todo-h2-api-docker:0.1
```

### Go inside the container
```bash
docker exec -it app01 bash
```

### stop your container
docker stop app01

### delete your container
docker rm app01

### run with compose
```bash
docker-compose up -d
```

### check the application with compose
```bash
docker-compose ps 
```

### see the logs of the compose
```bash
docker-compose logs
```

```bash
docker-compose stop
```


```bash
docker-compose start
```


```bash
docker-compose down -v
```


```bash
export USER_REGISTRY_NAME=<SET_YOUR_DOCKER_USER_NAME>
```

### Tag your image
```bash
docker tag springboot-todo-h2-api-docker:0.1  $USER_REGISTRY_NAME/springboot-todo-h2-api-docker:0.1
```


```bash
docker logout
```


```bash
docker login
```

### upload the image

```bash
docker push  $USER_REGISTRY_NAME/springboot-todo-h2-api-docker:0.1
```