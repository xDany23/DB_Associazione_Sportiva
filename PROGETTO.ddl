-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Sat May 24 12:27:59 2025 
-- * LUN file: C:\Users\user\OneDrive\Desktop\uni\basi di dati\PROGETTO.lun 
-- * Schema: Associazione Sportiva con analisi ridondanze eseguita versione logica/SQL 
-- ********************************************* 


-- Database Section
-- ________________ 

drop database if exists associazionesportiva;
create database if not exists associazioneSportiva;
use AssociazioneSportiva;

-- Tables Section
-- _____________ 

create table CAMPO (
     NumeroCampo integer not null auto_increment,
     Tipo enum ("Calcetto","Tennis","Padel") not null,
     constraint ID_CAMPO_ID primary key (NumeroCampo));

create table CORSO (
     DataInizio DATETIME not null,
     DataFine DATETIME not null,
     SportPraticato enum ("Calcetto","Tennis","Padel") not null,
     Prezzo decimal(10,4) not null,
     CodiceCorso integer not null auto_increment,
     Allenatore char(16) not null,
     constraint ID_CORSO_ID primary key (CodiceCorso));

create table FASCIA_ORARIA (
     NumeroCampo integer not null,
     Giorno enum ("Lunedi","Martedi","Mercoledi","Giovedi","Venerdi","Sabato","Domenica") not null,
     OrarioInizio time not null,
     OrarioFine time not null,
     Tipo enum ("Prenotabile","Lezione") not null,
     Prezzo decimal(10,4),
     constraint ID_FASCIA_ORARIA_ID primary key (NumeroCampo, Giorno, OrarioInizio));

create table GIOCA (
     CodiceSquadra integer not null,
     CodicePartita integer not null,
     punteggio integer not null,
     constraint ID_GIOCA_ID primary key (CodicePartita, CodiceSquadra));

create table ISCRIZIONE (
     CodiceTorneo integer not null,
     CodiceSquadra integer not null,
     constraint ID_ISCRIZIONE_ID primary key (CodiceTorneo, CodiceSquadra));

create table LEZIONE_CORSO (
     NumeroCampo integer not null,
     Giorno enum ("Lunedi","Martedi","Mercoledi","Giovedi","Venerdi","Sabato","Domenica") not null,
     OrarioInizio time not null,
     DataSvolgimento datetime not null,
     SportPraticato enum ("Calcetto","Tennis","Padel") not null,
     CodiceCorso integer not null,
     constraint ID_LEZIONE_CORSO_ID primary key (NumeroCampo, Giorno, OrarioInizio, DataSvolgimento, SportPraticato));

create table LEZIONE_PRIVATA (
     NumeroCampo integer not null,
     Giorno enum ("Lunedi","Martedi","Mercoledi","Giovedi","Venerdi","Sabato","Domenica") not null,
     OrarioInizio time not null,
     DataSvolgimento datetime not null,
     SportPraticato enum ("Calcetto","Tennis","Padel") not null,
     Prezzo decimal(10,4) not null,
     Allenatore char(16) not null,
     Partecipante1 char(16) not null,
     Partecipante2 char(16),
     Partecipante3 char(16),
     constraint ID_LEZIONE_PRIVATA_ID primary key (NumeroCampo, Giorno, OrarioInizio, DataSvolgimento, SportPraticato));

create table PARTECIPA (
     CF char(16) not null,
     CodiceCorso integer not null,
     constraint ID_PARTECIPA_ID primary key (CF, CodiceCorso));

create table PARTITA (
     CodicePartita integer not null,
     CodiceTorneo integer not null,
     Arbitro char(16) not null,
     SquadraVincitrice integer,
     constraint ID_PARTITA_ID primary key (CodicePartita));

create table PERSONA (
     Nome varchar(20) not null,
     Cognome varchar(20) not null,
     E_mail varchar(30) not null,
     Password varchar(16) not null,
     CF char(16) not null,
     Utente boolean,
     Allenatore boolean,
     Arbitro boolean,
     LezioniTenute integer,
     constraint ID_PERSONA_ID primary key (CF));

create table PRENOTAZIONE (
     NumeroCampo integer not null,
     Giorno enum ("Lunedi","Martedi","Mercoledi","Giovedi","Venerdi","Sabato","Domenica") not null,
     OrarioInizio time not null,
     DataPrenotazioneEffettuata datetime not null,
     DataPartita datetime not null,
     Prenotante char(16) not null,
     constraint ID_PRENOTAZIONE_ID primary key (NumeroCampo, Giorno, OrarioInizio, DataPartita));

create table SQUADRA (
     Nome varchar(20) not null,
     CodiceSquadra integer not null auto_increment,
     Tipo enum ("Calcetto","Tennis singolo","Tennis doppio","Padel") not null,
     Componenti1 char(16) not null,
     Componenti2 char(16),
     Componenti3 char(16),
     Componenti4 char(16),
     Componenti5 char(16),
     constraint ID_SQUADRA_ID primary key (CodiceSquadra));

create table TORNEO (
     DataSvolgimento datetime not null,
     Nome char(50) not null,
     Premio decimal(10,4) not null,
     MassimoPartecipanti integer not null,
     QuotaIscrizione decimal(10,4) not null,
     CodiceTorneo integer not null auto_increment,
     Tipo enum ("Calcetto","Tennis singolo","Tennis doppio","Padel") not null,
     SquadraVincitrice integer,
     constraint ID_TORNEO_ID primary key (CodiceTorneo));


-- Constraints Section
-- ___________________ 

-- Not implemented
-- alter table CAMPO add constraint ID_CAMPO_CHK
--     check(exists(select * from FASCIA_ORARIA
--                  where FASCIA_ORARIA.NumeroCampo = NumeroCampo)); 

-- Not implemented
-- alter table CORSO add constraint ID_CORSO_CHK
--     check(exists(select * from PARTECIPA
--                  where PARTECIPA.CodiceCorso = CodiceCorso)); 

-- Not implemented
-- alter table CORSO add constraint ID_CORSO_CHK
--     check(exists(select * from LEZIONE_CORSO
--                  where LEZIONE_CORSO.CodiceCorso = CodiceCorso)); 

alter table CORSO add constraint REF_CORSO_PERSO_FK
     foreign key (Allenatore)
     references PERSONA (CF);

alter table FASCIA_ORARIA add constraint EQU_FASCI_CAMPO
     foreign key (NumeroCampo)
     references CAMPO (NumeroCampo);

alter table GIOCA add constraint EQU_GIOCA_PARTI
     foreign key (CodicePartita)
     references PARTITA (CodicePartita);

alter table GIOCA add constraint REF_GIOCA_SQUAD_FK
     foreign key (CodiceSquadra)
     references SQUADRA (CodiceSquadra);

alter table ISCRIZIONE add constraint REF_ISCRI_SQUAD_FK
     foreign key (CodiceSquadra)
     references SQUADRA (CodiceSquadra);

alter table ISCRIZIONE add constraint REF_ISCRI_TORNE
     foreign key (CodiceTorneo)
     references TORNEO (CodiceTorneo);

alter table LEZIONE_CORSO add constraint REF_LEZIO_FASCI_1
     foreign key (NumeroCampo, Giorno, OrarioInizio)
     references FASCIA_ORARIA (NumeroCampo, Giorno, OrarioInizio);

alter table LEZIONE_CORSO add constraint EQU_LEZIO_CORSO_FK
     foreign key (CodiceCorso)
     references CORSO (CodiceCorso);

alter table LEZIONE_PRIVATA add constraint REF_LEZIO_FASCI
     foreign key (NumeroCampo, Giorno, OrarioInizio)
     references FASCIA_ORARIA (NumeroCampo, Giorno, OrarioInizio);

alter table LEZIONE_PRIVATA add constraint REF_LEZIO_PERSO_3_FK
     foreign key (Allenatore)
     references PERSONA (CF);

alter table LEZIONE_PRIVATA add constraint REF_LEZIO_PERSO_2_FK
     foreign key (Partecipante1)
     references PERSONA (CF);

alter table LEZIONE_PRIVATA add constraint REF_LEZIO_PERSO_1_FK
     foreign key (Partecipante2)
     references PERSONA (CF);

alter table LEZIONE_PRIVATA add constraint REF_LEZIO_PERSO_FK
     foreign key (Partecipante3)
     references PERSONA (CF);

alter table PARTECIPA add constraint EQU_PARTE_CORSO_FK
     foreign key (CodiceCorso)
     references CORSO (CodiceCorso);

alter table PARTECIPA add constraint REF_PARTE_PERSO
     foreign key (CF)
     references PERSONA (CF);

-- Not implemented
-- alter table PARTITA add constraint ID_PARTITA_CHK
--     check(exists(select * from GIOCA
--                  where GIOCA.CodicePartita = CodicePartita)); 

alter table PARTITA add constraint REF_PARTI_TORNE_FK
     foreign key (CodiceTorneo)
     references TORNEO (CodiceTorneo);

alter table PARTITA add constraint REF_PARTI_PERSO_FK
     foreign key (Arbitro)
     references PERSONA (CF);

alter table PARTITA add constraint REF_PARTI_SQUAD_FK
     foreign key (SquadraVincitrice)
     references SQUADRA (CodiceSquadra);

alter table PERSONA add constraint COEX_PERSONA
     check((Allenatore is not null and LezioniTenute is not null)
           or (Allenatore is null and LezioniTenute is null)); 

alter table PERSONA add constraint LSTONE_PERSONA
     check(Utente is not null or Allenatore is not null or Arbitro is not null); 

alter table PRENOTAZIONE add constraint REF_PRENO_FASCI
     foreign key (NumeroCampo, Giorno, OrarioInizio)
     references FASCIA_ORARIA (NumeroCampo, Giorno, OrarioInizio);

alter table PRENOTAZIONE add constraint REF_PRENO_PERSO_FK
     foreign key (Prenotante)
     references PERSONA (CF);

alter table SQUADRA add constraint REF_SQUAD_PERSO_4_FK
     foreign key (Componenti1)
     references PERSONA (CF);

alter table SQUADRA add constraint REF_SQUAD_PERSO_3_FK
     foreign key (Componenti2)
     references PERSONA (CF);

alter table SQUADRA add constraint REF_SQUAD_PERSO_2_FK
     foreign key (Componenti3)
     references PERSONA (CF);

alter table SQUADRA add constraint REF_SQUAD_PERSO_1_FK
     foreign key (Componenti4)
     references PERSONA (CF);

alter table SQUADRA add constraint REF_SQUAD_PERSO_FK
     foreign key (Componenti5)
     references PERSONA (CF);

alter table TORNEO add constraint REF_TORNE_SQUAD_FK
     foreign key (SquadraVincitrice)
     references SQUADRA (CodiceSquadra);


-- Index Section
-- _____________ 

create unique index ID_CAMPO_IND
     on CAMPO (NumeroCampo);

create unique index ID_CORSO_IND
     on CORSO (CodiceCorso);

create index REF_CORSO_PERSO_IND
     on CORSO (Allenatore);

create unique index ID_FASCIA_ORARIA_IND
     on FASCIA_ORARIA (NumeroCampo, Giorno, OrarioInizio);

create unique index ID_GIOCA_IND
     on GIOCA (CodicePartita, CodiceSquadra);

create index REF_GIOCA_SQUAD_IND
     on GIOCA (CodiceSquadra);

create unique index ID_ISCRIZIONE_IND
     on ISCRIZIONE (CodiceTorneo, CodiceSquadra);

create index REF_ISCRI_SQUAD_IND
     on ISCRIZIONE (CodiceSquadra);

create unique index ID_LEZIONE_CORSO_IND
     on LEZIONE_CORSO (NumeroCampo, Giorno, OrarioInizio, DataSvolgimento, SportPraticato);

create index EQU_LEZIO_CORSO_IND
     on LEZIONE_CORSO (CodiceCorso);

create unique index ID_LEZIONE_PRIVATA_IND
     on LEZIONE_PRIVATA (NumeroCampo, Giorno, OrarioInizio, DataSvolgimento, SportPraticato);

create index REF_LEZIO_PERSO_3_IND
     on LEZIONE_PRIVATA (Allenatore);

create index REF_LEZIO_PERSO_2_IND
     on LEZIONE_PRIVATA (Partecipante1);

create index REF_LEZIO_PERSO_1_IND
     on LEZIONE_PRIVATA (Partecipante2);

create index REF_LEZIO_PERSO_IND
     on LEZIONE_PRIVATA (Partecipante3);

create unique index ID_PARTECIPA_IND
     on PARTECIPA (CF, CodiceCorso);

create index EQU_PARTE_CORSO_IND
     on PARTECIPA (CodiceCorso);

create unique index ID_PARTITA_IND
     on PARTITA (CodicePartita);

create index REF_PARTI_TORNE_IND
     on PARTITA (CodiceTorneo);

create index REF_PARTI_PERSO_IND
     on PARTITA (Arbitro);

create index REF_PARTI_SQUAD_IND
     on PARTITA (SquadraVincitrice);

create unique index ID_PERSONA_IND
     on PERSONA (CF);

create unique index ID_PRENOTAZIONE_IND
     on PRENOTAZIONE (NumeroCampo, Giorno, OrarioInizio, DataPartita);

create index REF_PRENO_PERSO_IND
     on PRENOTAZIONE (Prenotante);

create unique index ID_SQUADRA_IND
     on SQUADRA (CodiceSquadra);

create index REF_SQUAD_PERSO_4_IND
     on SQUADRA (Componenti1);

create index REF_SQUAD_PERSO_3_IND
     on SQUADRA (Componenti2);

create index REF_SQUAD_PERSO_2_IND
     on SQUADRA (Componenti3);

create index REF_SQUAD_PERSO_1_IND
     on SQUADRA (Componenti4);

create index REF_SQUAD_PERSO_IND
     on SQUADRA (Componenti5);

create unique index ID_TORNEO_IND
     on TORNEO (CodiceTorneo);

create index REF_TORNE_SQUAD_IND
     on TORNEO (SquadraVincitrice);


-- Inserimento nella tabella campo --

insert into campo(NumeroCampo,Tipo)
values(1,"Calcetto"),(2,"Tennis"),(3,"Padel"),(4,"Padel"),(5,"Calcetto"),(6,"Calcetto"),(7,"Padel"),(8,"Tennis"),(9,"Padel"),(10,"Padel"),(11,"Tennis");

-- Inserimento nella tabella persona --

insert into persona(Nome,Cognome,E_mail,Password,CF,Utente,Allenatore,Arbitro,LezioniTenute)
values ("Alessandro","Ravaioli","qualcosa@gmail.com","pipipupu","AAAAAAAAAAAAAAAA",TRUE,NULL,FALSE,NULL),
	   ("Daniele","Tramonti","qualcosa@gmail.com","pipipupu","DDDDDDDDDDDDDDDD",true,null,false,null),
       ("Edoardo","Frignoli","qualcosa@gmail.com","pipipupu","EEEEEEEEEEEEEEEE",true,null,false,null),
       ("Romeo","ofwianf","qualcosa@gmail.com","pipipupu","RRRRRRRRRRRRRRRR",false,true,true,0);

-- Inserimento nella tabella fascia_oraria --

insert into fascia_oraria(NumeroCampo, Giorno, OrarioInizio, OrarioFine, Tipo, Prezzo)
values (1,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(1,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
	   (2,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(2,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (3,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(3,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (4,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(4,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (5,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(5,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (6,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(6,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (7,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(7,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (8,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(8,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (9,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(9,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (10,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(10,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (11,"Lunedi", "09:00:00","10:30:00","Prenotabile",10.35),(11,"Lunedi","10:30:00","12:00:00","Prenotabile",10.35),
       (1,"Lunedi", "12:00:00","13:30:00","Lezione",null),
       (5,"Lunedi", "12:00:00","13:30:00","Lezione",null);
       
-- Inserimento nella tabella prenotazione --

insert into prenotazione(NumeroCampo,Giorno,OrarioInizio,DataPrenotazioneEffettuata,DataPartita,Prenotante)
values (1,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
	   (2,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
       (3,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
       (4,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
       (5,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
       (6,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
       #(7,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
       (8,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
       (9,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
       (10,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA"),
       (11,"Lunedi","09:00:00","2025-01-25","2025-02-03","AAAAAAAAAAAAAAAA");
       
-- Inserimento nella tabella lezione_privata --
insert into lezione_privata(NumeroCampo,Giorno,OrarioInizio,DataSvolgimento,SportPraticato,Prezzo,Allenatore,Partecipante1,Partecipante2,Partecipante3)
values (1,"Lunedi","12:00:00","2025-02-03","Calcetto",30.95,"RRRRRRRRRRRRRRRR","AAAAAAAAAAAAAAAA",null,"EEEEEEEEEEEEEEEE");

-- Inserimento nella tabella corso --
insert	into corso(DataInizio,DataFine,SportPraticato,Prezzo,CodiceCorso,Allenatore)
values ("2024-05-06","2025-06-05","Calcetto",200.25,1,"RRRRRRRRRRRRRRRR"),
	   ("2024-05-06","2025-06-05","Calcetto",321.2,2,"RRRRRRRRRRRRRRRR"),
       ("2024-05-06","2024-06-05","Calcetto",321.2,3,"RRRRRRRRRRRRRRRR");

-- Inserimento nella tabella lezione_corso --
insert into lezione_corso(NumeroCampo,Giorno,OrarioInizio,DataSvolgimento,SportPraticato,CodiceCorso)
values (5,"Lunedi","12:00:00","2025-02-03","Calcetto",1);

-- Inserimento nella tabella torneo --
insert into torneo(DataSvolgimento,Nome,Premio,MassimoPartecipanti,QuotaIscrizione,CodiceTorneo,Tipo,SquadraVincitrice)
values ("2025-05-05","Giganti",5,2,1.0,1,"Tennis singolo",null);

-- Inserimento nella tabella squadra --
insert into squadra(Nome,CodiceSquadra,Tipo,Componenti1,Componenti2,Componenti3,Componenti4,Componenti5) 
values ("I belli",1,"Tennis singolo","AAAAAAAAAAAAAAAA",null,null,null,null),
	   ("I brutti",2,"Tennis singolo", "DDDDDDDDDDDDDDDD",null,null,null,null),
       ("I mezzi",3,"Tennis singolo", "EEEEEEEEEEEEEEEE",null,null,null,null);
       
-- Inserimento nella tabella iscrizione --
insert into iscrizione(CodiceTorneo,CodiceSquadra)
values (1,1);

-- Inserimento nella tabella partecipa --
insert into partecipa(CF,CodiceCorso)
values ("AAAAAAAAAAAAAAAA",1),("DDDDDDDDDDDDDDDD",1),
	   ("AAAAAAAAAAAAAAAA",2);
       
-- Inserimento nella tabella partita --
insert into partita(CodicePartita,CodiceTorneo,Arbitro,SquadraVincitrice)
values (1,1,"RRRRRRRRRRRRRRRR",2),(2,1,"RRRRRRRRRRRRRRRR",null);

-- Inserimento nella tabella gioca --
insert into gioca(CodiceSquadra,CodicePartita,punteggio)
values (1,1,2),(2,1,3),
	   (1,2,3),(2,2,3);