INSERT INTO membership VALUES (1, 'X-Force', 'logoxforce.png');

INSERT INTO cerebook_user VALUES (1, 'bg_deadpool.jpg', 'Deadpools exact past remains unclear, with several versions of his past having come and gone. Known as Wade Wilson, he lost his father at the age of 5. Another version is that his father abandoned him young. Most versions tell of a difficult childhood. However, the most recent version is that his parents were still alive and he had a normal childhood.',
 '1991-02-01', 'M', 'deadpool.jpg', 'Regeneration', 1);
INSERT INTO cerebook_user VALUES (2, 'bg_domino.jpg', 'Domino''s real name is unknown, but she once used the identity of Neena Beatrice Thurman when she was married. Domino is Cable''s ally and helped him lead the New Mutants when they left the X-Men. It was actually an impostor at the time, a shapeshifting mutant named Copycat. Meanwhile, the real Domino was Tolliver''s prisoner. When she is freed, she takes over Copycat''s place on the team, now called X-Force.',
 '1992-05-01', 'F', 'domino.jpg', 'Luck', 1);

INSERT INTO app_user VALUES (1, 'deadpool@mail.com', 'f', 'Wade', 'Wilson', '$2a$10$dZIUEl0mLcczHA1YDkzqpOp8lbC6ZZRZiLcJUmygRdxwQLK/18ktC', 'ROLE_USER', 'deadpool', 1);
INSERT INTO app_user VALUES (2, 'domino@mail.com', 'f', 'Neena', 'Thurman', '$2a$10$dZIUEl0mLcczHA1YDkzqpOp8lbC6ZZRZiLcJUmygRdxwQLK/18ktC', 'ROLE_USER', 'domino', 2);

UPDATE cerebook_user SET user_id = 1 WHERE id = 1;
UPDATE cerebook_user SET user_id = 2 WHERE id = 2;

INSERT INTO event_category VALUES (1, 'Fight');

INSERT INTO event VALUES (1, 'xforce.jpg', '2022-07-11', '2022-07-14', 'We need to save Russell', 'Save Russell', '2022-07-12', 1, 1);

INSERT INTO cerebook_user_friends VALUES ('2000-05-01', 't', 1, 2);

INSERT INTO membership_event VALUES (1, 1);

INSERT INTO post VALUES (1, 'Maximum Effort', '2022-07-12', 'f', 'maximumeffort.jpg', 'https://youtube.com/embed/Sy8nPI85Ih4?rel=0', 1, 1);
INSERT INTO post VALUES (2, 'Powers', '2022-07-12', 'f', 'dominomar.jpg', 'https://youtube.com/embed/bM9BFyJko1I?rel=0', 2, 1);

INSERT INTO comment VALUES (1, 'I love you', 1, 1);
INSERT INTO comment VALUES (2, 'Seven', 2, 2);

INSERT INTO participation VALUES (1, 'Leader', 1, 1);
INSERT INTO participation VALUES (2, 'Member', 1, 2);
