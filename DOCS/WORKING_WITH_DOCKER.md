


## Docker Compose

### Create your development files
```bash
touch docker-compose.override.yaml  .env_variables
```

###
```bash
user_name="Chanchito Feliz"
user_data="12345"
```


###  Content of docker-compose.override.yaml
```yaml
version: '3.8'

services:
  api:
    build:
      context: .
      target: development
    environment:
      CHANCHITO: "Feliz"
    env_file:
      - .env_variables
    volumes:
      - .:/app
    command: sh mvnw spring-boot:run
```