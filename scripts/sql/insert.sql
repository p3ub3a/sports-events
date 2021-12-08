insert into event (id,type,duration,location,max_players,outdoors,scheduled_date,status,winner) 
	VALUES (12344, 'CHESS', 60, 'Bucuresti', 2, false, '2021-12-17T15:35:00','CLOSED','gica'), (12345, 'SWIMMING', 10, 'Iasi', 2, false, '2021-11-23T16:30:00','CLOSED','gica2'),
    (12346, 'CHESS', 60, 'Bucuresti', 2, false, '2021-12-17T15:35:00','CLOSED','gica'), (12347, 'SWIMMING', 10, 'Iasi', 2, false, '2021-11-23T16:30:00','CLOSED','gica2'),
    (12348, 'CHESS', 60, 'Bucuresti', 2, false, '2021-12-17T15:35:00','CLOSED','gica3'), (12349, 'SWIMMING', 10, 'Iasi', 2, false, '2021-11-23T16:30:00','CLOSED','gica2');
    
alter sequence event_id_seq restart with 3;