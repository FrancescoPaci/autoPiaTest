INSERT INTO utenti (username, password, azienda, ruoli, attivo)
VALUES ('admin', '$2a$10$qpPWvjFZMBlUWUERj0imMue6bvhhPLC3n7dzceoSOrhEmpoKoDglW', 'Azienda Demo', 'ROLE_ADMIN,ROLE_USER', true);
INSERT INTO utenti (username, password, azienda, ruoli, attivo)
VALUES ('user', 'pippo', 'Azienda Demo', 'ROLE_USER', true);

-- 1. INSERIMENTO ORGANIZZAZIONI
INSERT INTO organizzazione (id, nome)
VALUES (1, 'Ospedale Centrale San Raffaele');

INSERT INTO organizzazione (id, nome)
VALUES (2, 'Clinica Diagnostica Avanzata');

INSERT INTO organizzazione (id, nome)
VALUES (3, 'Clinica 3');

-- 2. INSERIMENTO CONTENITORI
INSERT INTO contenitore (id, id_organizzazione, ordine, nome)
VALUES (1, 1, 1, 'Contenitore TAC Principale');

INSERT INTO contenitore (id, id_organizzazione, ordine, nome)
VALUES (2, 1, 2, 'magazzino 1');

INSERT INTO contenitore (id, id_organizzazione, ordine, nome)
VALUES (3, 1, 3, 'armadio 1');

INSERT INTO contenitore (id, id_organizzazione, ordine, nome)
VALUES (4, 2, 1, 'Contenitore RX Secondario');

INSERT INTO contenitore (id, id_organizzazione, ordine, nome)
VALUES (5, 2, 2, 'magazzino 2');

INSERT INTO contenitore (id, id_organizzazione, ordine, nome)
VALUES (6, 2, 3, 'magazzino 3');


-- 3. INSERIMENTO APPARECCHIATURE
INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('TAC Somatom 64', 'TAC', 'SN-TAC-2026-A', '2026-01-15', null, 1);

INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('Sorgente RX Digital', 'RX', 'SN-RX-9988-B', '2025-11-10', null, 3);

INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('Risanza Magnetica 3T', 'Risonanza', 'SN-RM-2000-X', '2026-03-01', null, 3);

INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('Risonanza Magnetica 32T', 'Risonanza', 'SN-RM-4000-X', '2026-03-01', null, 6);

INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('Rinanza Magnetica 33T', 'Risonanza', 'SN-RM-3100-X', '2026-03-01', null, 6);

INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('Risonza Magnetica 34T', 'Risonanza', 'SN-RM-32200-X', '2026-03-01', null, 6);

INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('Rsonaza Magnetica 4G', 'Risonanza', 'SN-RM-30325-X', '2025-04-01', 3, null);

INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('Tac 4G', 'Risonanza', 'SN-RM-3120325-X', '2025-04-01', 3, null);