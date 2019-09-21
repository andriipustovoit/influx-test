Start containers:

docker-compose up

After containers up and running connect to influxdb:

docker exec -it influxdbtest_influxdb_1 influx

End take a look at prepopulated data:

use wechat_local
select count(*) from items
select count(*) from items group by time(4w), *
