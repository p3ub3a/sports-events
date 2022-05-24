if [ "$1" != "quarkus-jvm" ] && [ "$1" != "quarkus-native" ] && [ "$1" != "springboot" ]
  then
    echo "Wrong pod argument, possible values: quarkus-jvm, quarkus-native, springboot"
    exit 1
fi

if [ $2 != 5 ] && [ $2 != 10 ] && [ $2 != 15 ] && [ $2 != 20 ]
  then
    echo "No sleep argument passed, possible values: 5, 10, 15, 20"
    exit 1
fi

echo "Starting $1 busybox ... "

kubectl apply -f ping/$1-ping.yml

echo "Waiting for $1 busybox to start..."

sleep 12

echo "Terminating $1 pods ..."

for i in {1..10}
do
  POD_TO_KILL=$(kubectl get po --selector "app=$1" --output=name | head -n1)
  
  echo "will kill $POD_TO_KILL"
  kubectl delete --force $POD_TO_KILL
  # sleep time between restarts
  sleep $2
done

if [ -f "ping-$1.log" ] ; then
    rm "ping-$1.log"
fi

kubectl logs ping-$1 > ping-$1.log

TOTAL_REQUESTS=$(expr $(cat ping-$1.log | grep "Trying to connect to" | wc -l))
ERRORS=$(expr $(cat ping-$1.log | grep "Connection refused" | wc -l) + $(cat ping-$1.log | grep -i 'timed out' | wc -l) +  $(cat ping-$1.log | grep -i 'peer' | wc -l))

printf "\n\n\n==========================> $1 run; `date`\n" >> ping-result.log
printf "==========================> sleep time between pod restarts: $2\n" >> ping-result.log
echo "Total requests: $TOTAL_REQUESTS" >> ping-result.log
echo "Number of $1 faults: $ERRORS" >> ping-result.log

kubectl delete -f ping/$1-ping.yml

exit 0