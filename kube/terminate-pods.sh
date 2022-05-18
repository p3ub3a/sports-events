

echo "Starting $1 busybox ... "

kubectl apply -f ping/quarkus-jvm-ping.yml

echo "Waiting for $1 busybox to start..."

sleep 12

echo "Terminating $1 pods ..."

for i in {1..15}
do
  POD_TO_KILL=$(kubectl get po --selector "app=$1" --output=name | head -n1)
  
  echo "will kill $POD_TO_KILL"
    kubectl delete --force $POD_TO_KILL
  sleep 10
done

kubectl logs ping-quarkus-jvm > ping-quarkus-jvm.log

TOTAL_REQUESTS=$(expr $(cat ping-quarkus-jvm.log | grep "Trying to connect to" | wc -l))
ERRORS=$(expr $(cat ping-quarkus-jvm.log | grep "Connection refused" | wc -l) + $(cat ping-quarkus-jvm.log | grep -i 'timed out' | wc -l) +  $(cat ping-quarkus-jvm.log | grep -i 'peer' | wc -l))

echo "Total requests: $TOTAL_REQUESTS"
echo "Number of $1 faults: $ERRORS"

kubectl delete -f ping/quarkus-jvm-ping.yml