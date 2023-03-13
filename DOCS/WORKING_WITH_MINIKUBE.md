#  Deploy on Minikube

### Basics operations with the pod
```bash
# Create the minikube cluster
minikube start  --vm-driver=docker --memory=3g  --nodes 4


# Create the pod call task-api
kubectl run task-api --image=rmontesleo/springboot-todo-h2-api-k8s:v1

# get the pod in yaml format
kubectl run task-api --image=rmontesleo/springboot-todo-h2-api-k8s:v1 --dry-run=client -o yaml

# create a service to expose the pod (By the fault the service is ClusterIP)
kubectl expose pod task-api --port=8080 --name=task-api-service

# get the service in yaml format
kubectl expose pod task-api --port=8080 --name=task-api-service  --dry-run=client -o yaml

# create an ubuntu pod to invoke the ClusterIP service
kubectl run ubuntu --image=ubuntu -it -- bash

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

### Edit the sevice
```bash
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
service_port="TYPE_THE_PORT_EXPOSE"

curl http://$external_ip_node1:$service_port
curl http://$external_ip_node2:$service_port
curl http://$external_ip_node3:$service_port
curl http://$external_ip_node4:$service_port

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

# 
minikube tunel

# in other terminal and check the external ip for the load balancer
kubectl get svc -o wide


#
curl http://$exterlan_load_balancer_ip:8080

# Delete service
kubectl delete service task-api-service

# Delete pod
kubectl delete pod task-api

```

### Create a new deployment
```bash

kubectl create deployment task-api-deployment --image=rmontesleo/springboot-todo-h2-api-k8s:v1 --port=8080  --replicas=1

kubectl create deployment task-api-deployment --image=rmontesleo/springboot-todo-h2-api-k8s:v1 --port=8080  --replicas=1 --dry-run=client -o yaml


kubectl get pods
kubectl get pods --selector app=task-api-deployment

kubectl get deployments -o wide
kubectl get deployments -o wide --selector app=task-api-deployment

kubectl get replicaset -o wide
kubectl get replicaset -o wide --selector app=task-api-deployment


kubectl expose deployment task-api-deployment

kubectl expose deployment task-api-deployment --dry-run=client -o yaml
kubectl expose deployment task-api-deployment  type=NodePort  --dry-run=client -o yaml
kubectl expose deployment task-api-deployment  type=LoadBalancer  --dry-run=client -o yaml


# The default type is ClusterIP
kubectl get svc -o wide


kubectl exec -it ubuntu -- bash
```

### in the ubuntu pod
```bash
ping task-api-deployment	

curl task-api-deployment:8080

exit
```

### Scale the deployment
```bash
kubectl describe svc task-api-deployment

kubectl scale deployment  task-api-deployment --replicas=4

kubectl get pods -o wide --selctor app=task-api-deployment

kubectl describe svc task-api-deployment

```