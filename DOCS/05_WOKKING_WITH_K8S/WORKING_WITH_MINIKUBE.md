#  Deploy the application on Minikube


## Working with a single pod

### Basics operations with the pod
```bash
# Create the minikube cluster, depends on your resources could change the values
minikube start  --vm-driver=docker --memory=3g  --nodes 2


# Create the pod call task-api
kubectl run task-api-pod --image=rmontesleo/springboot-todo-h2-api-k8s:v1

# get the pod in yaml format a
kubectl run task-api-pod --image=rmontesleo/springboot-todo-h2-api-k8s:v1 --dry-run=client -o yaml

# go inside the pod
kubectl exec -it task-api-pod -- sh
```

### inside the pod
```bash
cat /etc/os-release

# list the files inside the home folder
ls $HOME

# check the current environment variables
env

exit
```

#### Create a new pod with environment variables
```bash
kubectl run task-api-env-pod --image=rmontesleo/springboot-todo-h2-api-k8s:v1 --env="user_data=987"  --env="user_name=Leo" 

## get teh yaml definion of the second pod
kubectl run task-api-env-pod --image=rmontesleo/springboot-todo-h2-api-k8s:v1 --env="user_data=987"  --env="user_name=Leo"  --dry-run=client -o yaml

kubectl get pods
kubectl get pod task-api-env-pod -o yaml
kubectl describe pod task-api-env-pod

# go inside the pod
kubectl exec -it task-api-env-pod -- sh
```

### in the new pod
```bash
# find the variables user_data and user_name
env
exit
```

### Create a config map 
```bash
# Create a config map
kubectl create configmap task-api-cm --from-literal=user_name="Chanchito Feliz" --from-literal=user_data=12345

# get details of the config map and create a manifest file of this resource
kubectl get cm task-api-cm
kubectl get cm task-api-cm -o yaml
kubectl get cm task-api-cm -o yaml > 01-01-configmap.yaml


## get teh yaml definion of the 3th pod
kubectl run task-api-cm-pod --image=rmontesleo/springboot-todo-h2-api-k8s:v1  --env="user_data=CHANGE_ME"  --env="user_name=CHANGE_ME" --dry-run=client -o yaml

# edit the task-api-cm-pod manifest
 
```

#### original
```yaml
- env:
    - name: user_data
      value: CHANGE_ME
    - name: user_name
      value: CHANGE_ME
```

#### modified
```yaml
- env:
    - name: user_data
      valueFrom:
        configMapKeyRef:
          name: task-api-cm
          key: user_data
    - name: user_name
      valueFrom:
        configMapKeyRef:
          name: task-api-cm
          key: user_name
```

### create the pod with the manifest
```bash
kubectl apply -f task-api-cm-pod.yaml

kubectl get  pods

# enter in the pod and check env variables
kubectl exec -it  task-api-cm-pod -- sh

```

### Create a service to expose the pod
```bash
# create a service to expose the pod (By the fault the service is ClusterIP)
kubectl expose pod task-api-pod --port=8080 --name=task-api-service

kubectl get service -o wide

# get the service in yaml format for cluster ip (Default)
kubectl expose pod task-api-pod --port=8080 --name=task-api-service  --dry-run=client -o yaml

# get the service in yaml format for NodePort 
kubectl expose pod task-api-pod --port=8080 --name=task-api-service --type=NodePort   --dry-run=client -o yaml

# get the service in yaml format for LoadBalancer
kubectl expose pod task-api-pod --port=8080 --name=task-api-service --type=LoadBalancer  --dry-run=client -o yaml

```


### Create a pod to test the services
```bash
# create an ubuntu pod to invoke the ClusterIP service
kubectl run ubuntu --image=ubuntu -it -- bash

#
kubectl get pods

# Go inside the pod to invoke the service
kubectl exec -it ubuntu -- bash

```


### inside the ubuntu pod
```bash
apt-get -y update && apt install -y iputils-ping curl

ping task-api-service

curl http://task-api-service:8080

exit
```

### edit the yaml of ubuntu pod
```bash
kubectl run ubuntu --image=ubuntu --dry-run=client -o yaml

# edit the pod definition
```

### configure the pod to avoid crashes when its created
```yaml
apiVersion: v1
kind: Pod
metadata:
  labels:
    run: ubuntu
  name: ubuntu
spec:
  containers:
  - name: ubuntu
    image: ubuntu
    args:
    - sleep
    - infinity

```

### Edit the sevice
```bash
#
kubectl get svc

#
kubectl edit svc task-api-service
```

### Original Service Type
```yaml
type: ClusterIP
```

### Change to Node Port
```yaml
type: NodePort
```

### Test the servie, pointing to each node
```bash
kubectl get svc -o wide


service_port="TYPE_THE_PORT_EXPOSE"

curl http://$external_ip_node1:$service_port
curl http://$external_ip_node2:$service_port

```

### Edit the sevice
```bash
#
kubectl edit svc task-api-service
```

### Change to Node Port
```yaml
type: LoadBalancer
```

###  Testing the load balancer
```bash
# the Type load balancer is pending in Minikube
kubectl get svc -o wide

# Minikube will open a tunnel to expose the service. Do not close the terminal
minikube tunnel

# in other terminal and check the external ip for the load balancer
kubectl get svc -o wide

#
curl http://$exterlan_load_balancer_ip:8080

# Delete service
kubectl delete service task-api-service

# Delete pod
kubectl delete pod task-api task-api-cm-pod task-api-env-pod ubuntu

# Delete the config map
kubectl delete configmap task-api-cm

```

---

## Working the application with a deployment

### Create a new deployment
```bash

# Create the deployment defining more parameters
kubectl create deployment task-api --image=rmontesleo/springboot-todo-h2-api-k8s:v1 --port=8080  --replicas=1

# verify what is created
kubectl get all -o wide

# get the yaml definition and create the yaml file
kubectl create deployment task-api --image=rmontesleo/springboot-todo-h2-api-k8s:v1 --port=8080  --replicas=1 --dry-run=client -o yaml


kubectl get pods
kubectl get pods --selector app=task-api

kubectl get deployments -o wide
kubectl get deployments -o wide --selector app=task-api

kubectl get replicaset -o wide
kubectl get replicaset -o wide --selector app=task-api


# create a service to expose the deployment (ClusterIP by default)
kubectl expose deployment task-api

# create the yaml file to get the definition of this service
kubectl expose deployment task-api --dry-run=client -o yaml

# The default type is ClusterIP, also verify the ip of the deployment pod
kubectl get svc -o wide

# go inside the ubuntu pod to test the clusterip service
kubectl exec -it ubuntu -- bash
```

### in the ubuntu pod
```bash
ping task-api	

curl http://task-api:8080

exit
```

### Add env variables from config map
```bash
kubectl create configmap task-api-cm --from-file=.env_variables
kubectl set env --from=configmap/task-api-cm deploy/task-api
```


### Scale the deployment
```bash
kubectl describe svc task-api

# Scale the deployment
kubectl scale deployment  task-api --replicas=2

kubectl describe svc task-api

kubectl get pods -o wide --selector app=task-api

kubectl describe svc task-api
```

###  delete the previous service and create a new one like NodePort
```bash
kubectl delete service task-api

# Create the new service like node port
kubectl expose deployment task-api  --type=NodePort

# Get the yaml definition of this service
kubectl expose deployment task-api  --type=NodePort  --dry-run=client -o yaml

# see the new service and see the open port in each node
kubectl get svc -o wide

# see the ip of your nodes and test by each external ip by the open port
curl http://$external_ip_node1:$service_port
curl http://$external_ip_node2:$service_port

```

### Delete the previous service and create a new one like load balancer
```bash
kubectl delete service task-api

# Create the new service like load balancer
kubectl expose deployment task-api --type=LoadBalancer

# Get the yaml definition of this service
kubectl expose deployment task-api --type=LoadBalancer  --dry-run=client -o yaml

kubectl get svc -o wide

# this is only for minikube, open a tunnel to use the Load Balancer, do not close the terminal and opend a new one
minikube tunnel

# check again your service
kubectl get svc -o wide

# test your service usign the public load balancer ip and the port of the deployment (8080)
curl http://$exterlan_load_balancer_ip:8080

```

### Clean up the deployment
```bash
kubectl delete deployment task-api
kubectl delete service task-api
kubectl delete configmap task-api-cm
```

---

## Create the deployment in a namespace and applying manifest

###
```bash
# create a pod
kubectl run task-api-pod --image=rmontesleo/springboot-todo-h2-api-k8s:v1

# this will send an error (AlreadyExists)
kubectl run task-api-pod --image=rmontesleo/springboot-todo-h2-api-k8s:v1

kubectl delete pod task-api-pod

kubectl get pods

# create a pod
kubectl apply -f 01-00-pod.yaml

# know the context.. nothing change
kubectl apply -f 01-00-pod.yaml
```

###
```bash
kubectl get ns

# create a basic namespace
kubectl create namespace demo01

kubectl create namespace demo02

# get the yaml definition
kubectl create namespace demo03 --dry-run=client -o yaml

kubectl get namespaces

# apply a manifest  and add the flag to some specific namespace
kubectl apply -f 01-00-pod.yaml -n demo01

kubectl run task-api-pod --image=rmontesleo/springboot-todo-h2-api-k8s:v1 -n demo02

```




---

## References

- [Namespaces Walkthrough](https://kubernetes.io/docs/tasks/administer-cluster/namespaces-walkthrough/)
- [How to create K8S deployment in specific namespace?](https://stackoverflow.com/questions/59261894/how-to-create-k8s-deployment-in-specific-namespace)
