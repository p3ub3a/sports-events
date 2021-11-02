insert into event (id,type,duration,location,maxPlayers,outdoors,scheduledDate) 
	VALUES (12344, 'CHESS', 60, 'Bucuresti', 2, false, '2021-12-17T15:35:00'), (12345, 'SWIMMING', 10, 'Iasi', 2, false, '2021-11-23T16:30:00');

alter sequence event_id_seq restart with 3;