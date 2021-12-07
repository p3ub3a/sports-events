insert into event (id,type,duration,location,maxPlayers,outdoors,scheduledDate,status,winner,players) 
	VALUES (12344, 'CHESS', 60, 'Bucuresti', 2, false, '2021-12-17T15:35:00','CLOSED','gica', decode('gica2, gica3', 'escape')), (12345, 'SWIMMING', 10, 'Iasi', 2, false, '2021-11-23T16:30:00','CLOSED','gica2', decode('gica2, gica', 'escape')),
    (12346, 'CHESS', 60, 'Bucuresti', 2, false, '2021-12-17T15:35:00','CLOSED','gica', decode('gica, gica3', 'escape')), (12347, 'SWIMMING', 10, 'Iasi', 2, false, '2021-11-23T16:30:00','CLOSED','gica2', decode('gica2, gica3', 'escape')),
    (12348, 'CHESS', 60, 'Bucuresti', 2, false, '2021-12-17T15:35:00','CLOSED','gica3', decode('gica2, gica3', 'escape')), (12349, 'SWIMMING', 10, 'Iasi', 2, false, '2021-11-23T16:30:00','CLOSED','gica2', decode('gica2, gica3', 'escape'));
    
alter sequence event_id_seq restart with 3;