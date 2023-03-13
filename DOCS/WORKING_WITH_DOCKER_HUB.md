# Working with docker

## Build your Docker Image

### Set your environment file
```bash
# create a .env file
touch .env

#edit the file 
vim .env
```

### set the content
```bash
docker_registry_name="<PUT_YOUR_DOCKER_REGISTRY_NAME>"
image_version="<PUT_THE_VERSION_OF_YOUR_IMAGE>"

# This is the name for the producion image
image_name_prod="springboot-task-tracker-h2-api-docker"

image_name_base="springboot-task-tracker-h2-api-docker-base"
image_name_test="springboot-task-tracker-h2-api-docker-test"
image_name_dev="springboot-task-tracker-h2-api-docker-dev"
image_name_build="springboot-task-tracker-h2-api-docker-build"

```

### create your production image
```bash
# set environment variables
source .env

# verify the value are show in console
echo $image_name
echo $image_version
echo $docker_registry_name

docker build --target production  -t $image_name:$image_version .
```


### Test your containers
```bash
# Create the container with your production image
docker run -d -p 8080:8080 --name api01 $image_name:$image_version

# go inside the container
docker exec -it api01 sh
```

### Test with docker-compose
```bash

#
docker-compose up -d

#
docker-compose logs

#
docker-compose ps

```



### if you want to build different images try with
### This step is optional
```bash

# the image with the source code
docker build --target base  -t $image_name_base:$image_version .

# the image with the testing results 
docker build --target test  -t $image_name_test:$image_version .

# the image to develop with docker compose
docker build --target development  -t $image_name_dev:$image_version .

# the image with the jar
docker build --target build  -t $image_name_build:$image_version .

```

### Tag your image
```bash
docker tag $image_name:$image_version $docker_registry_name/$image_name:$image_version
```

### Login to docker hub
```bash
#
docker logout

#
docker login --username $docker_registry_name
```


### Upload to docker registry
```bash
docker push $docker_registry_name/$image_name:$image_version
```