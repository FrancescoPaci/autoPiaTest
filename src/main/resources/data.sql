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


-- 3. INSERIMENTO APPARECCHIATURE
INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('TAC Somatom 64', 'TAC', 'SN-TAC-2026-A', '2026-01-15', null, 1);

INSERT INTO apparecchiatura (nome, tipologia, numero_serie, data_installazione, id_organizzazione, id_contenitore)
VALUES ('Risonanza Magnetica 4G', 'Risonanza', 'SN-RM-3032-X', '2025-04-01', 3, null);
