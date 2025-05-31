-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation datetime: Sat May 24 12:27:59 2025 
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
     DataInizio DATE not null,
     DataFine DATE not null,
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
     DataSvolgimento date not null,
     SportPraticato enum ("Calcetto","Tennis","Padel") not null,
     CodiceCorso integer not null,
     constraint ID_LEZIONE_CORSO_ID primary key (NumeroCampo, Giorno, OrarioInizio, DataSvolgimento, SportPraticato));

create table LEZIONE_PRIVATA (
     NumeroCampo integer not null,
     Giorno enum ("Lunedi","Martedi","Mercoledi","Giovedi","Venerdi","Sabato","Domenica") not null,
     OrarioInizio time not null,
     DataSvolgimento date not null,
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
     Password varchar(30) not null,
     CF char(16) not null,
     Utente boolean not null,
     Allenatore boolean not null,
     Arbitro boolean not null,
     LezioniTenute integer not null,
     Admin boolean not null default false,
     constraint ID_PERSONA_ID primary key (CF));

create table PRENOTAZIONE (
     NumeroCampo integer not null,
     Giorno enum ("Lunedi","Martedi","Mercoledi","Giovedi","Venerdi","Sabato","Domenica") not null,
     OrarioInizio time not null,
     DataPrenotazioneEffettuata date not null,
     DataPartita date not null,
     Prenotante char(16) not null,
     constraint ID_PRENOTAZIONE_ID primary key (NumeroCampo, Giorno, OrarioInizio, DataPartita));

create table SQUADRA (
     Nome varchar(20) not null,
     CodiceSquadra integer not null auto_increment,
     Tipo enum ("Calcetto","Tennis_singolo","Tennis_doppio","Padel") not null,
     Componenti1 char(16) not null,
     Componenti2 char(16),
     Componenti3 char(16),
     Componenti4 char(16),
     Componenti5 char(16),
     constraint ID_SQUADRA_ID primary key (CodiceSquadra));

create table TORNEO (
     DataSvolgimento date not null,
     Nome char(50) not null,
     Premio varchar(50) not null,
     MassimoPartecipanti integer not null,
     QuotaIscrizione decimal(10,4) not null,
     CodiceTorneo integer not null auto_increment,
     Tipo enum ("Calcetto","Tennis_singolo","Tennis_doppio","Padel") not null,
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

alter table lezione_privata add constraint PERSONE_DIVERSE
	check (Partecipante1 <> Partecipante2 and Partecipante1 <> Partecipante3 and Partecipante2 <> Partecipante3);
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
     check(Utente is not null or Allenatore is not null or Arbitro is not null or Admin is not null); 

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
values ("Alessandro","Ravaioli","qualcosa@gmail.com","pipipupu","AAAAAAAAAAAAAAAA",TRUE,false,FALSE,0),
	   ("Daniele","Tramonti","qualcosa@gmail.com","pipipupu","DDDDDDDDDDDDDDDD",true,false,false,0),
       ("Edoardo","Frignoli","qualcosa@gmail.com","pipipupu","EEEEEEEEEEEEEEEE",true,false,false,0),
       ("Romeo","ofwianf","qualcosa@gmail.com","pipipupu","RRRRRRRRRRRRRRRR",false,true,true,1);
insert into persona(Nome,Cognome,E_mail,Password,CF,Utente,Allenatore,Arbitro,LezioniTenute,Admin)
values ("Gianfranco","Marchini","qualcosaltro@gmail.com","cambioPassword","GGGGGGGGGGGGGGGG",false,false,false,0,true);

-- Inserimento nella tabella fascia_oraria --

insert into fascia_oraria(NumeroCampo, Giorno, OrarioInizio, OrarioFine, Tipo, Prezzo)
values  (1,"Lunedi","09:00:00","10:30:00","Prenotabile",75.0),(1,"Lunedi","10:30:00","12:00:00","Prenotabile",75.0),(1,"Lunedi","12:00:00","13:30:00","Prenotabile",75.0),(1,"Lunedi","13:30:00","15:00:00","Prenotabile",75.0),(1,"Lunedi","15:00:00","16:30:00","Lezione",null),(1,"Lunedi","16:30:00","19 	:00:00","Lezione",null),(1,"Lunedi","19:00:00","20:30:00","Lezione",null),(1,"Lunedi","20:30:00","22:00:00","Prenotabile",75.0),(1,"Lunedi","22:00:00","23:30:00","Prenotabile",75.0),
		(1,"Martedi","09:00:00","10:30:00","Prenotabile",75.0),(1,"Martedi","10:30:00","12:00:00","Prenotabile",75.0),(1,"Martedi","12:00:00","13:30:00","Prenotabile",75.0),(1,"Martedi","13:30:00","15:00:00","Prenotabile",75.0),(1,"Martedi","15:00:00","16:30:00","Lezione",null),(1,"Martedi","16:30:00","19:00:00","Lezione",null),(1,"Martedi","19:00:00","20:30:00","Lezione",null),(1,"Martedi","20:30:00","22:00:00","Prenotabile",75.0),(1,"Martedi","22:00:00","23:30:00","Prenotabile",75.0),
		(1,"Mercoledi","09:00:00","10:30:00","Prenotabile",67.5),(1,"Mercoledi","10:30:00","12:00:00","Prenotabile",67.5),(1,"Mercoledi","12:00:00","13:30:00","Prenotabile",67.5),(1,"Mercoledi","13:30:00","15:00:00","Prenotabile",67.5),(1,"Mercoledi","15:00:00","16:30:00","Lezione",null),(1,"Mercoledi","16:30:00","19:00:00","Lezione",null),(1,"Mercoledi","19:00:00","20:30:00","Lezione",null),(1,"Mercoledi","20:30:00","22:00:00","Prenotabile",67.5),(1,"Mercoledi","22:00:00","23:30:00","Prenotabile",67.5),
		(1,"Giovedi","09:00:00","10:30:00","Prenotabile",75.0),(1,"Giovedi","10:30:00","12:00:00","Prenotabile",75.0),(1,"Giovedi","12:00:00","13:30:00","Prenotabile",75.0),(1,"Giovedi","13:30:00","15:00:00","Prenotabile",75.0),(1,"Giovedi","15:00:00","16:30:00","Lezione",null),(1,"Giovedi","16:30:00","19:00:00","Lezione",null),(1,"Giovedi","19:00:00","20:30:00","Lezione",null),(1,"Giovedi","20:30:00","22:00:00","Prenotabile",75.0),(1,"Giovedi","22:00:00","23:30:00","Prenotabile",75.0),
		(1,"Venerdi","09:00:00","10:30:00","Prenotabile",82.50000000000001),(1,"Venerdi","10:30:00","12:00:00","Prenotabile",82.50000000000001),(1,"Venerdi","12:00:00","13:30:00","Prenotabile",82.50000000000001),(1,"Venerdi","13:30:00","15:00:00","Prenotabile",82.50000000000001),(1,"Venerdi","15:00:00","16:30:00","Lezione",null),(1,"Venerdi","16:30:00","19:00:00","Lezione",null),(1,"Venerdi","19:00:00","20:30:00","Lezione",null),(1,"Venerdi","20:30:00","22:00:00","Prenotabile",82.50000000000001),(1,"Venerdi","22:00:00","23:30:00","Prenotabile",82.50000000000001),
		(1,"Sabato","09:00:00","10:30:00","Prenotabile",101.25),(1,"Sabato","10:30:00","12:00:00","Prenotabile",101.25),(1,"Sabato","12:00:00","13:30:00","Prenotabile",101.25),(1,"Sabato","13:30:00","15:00:00","Prenotabile",101.25),(1,"Sabato","15:00:00","16:30:00","Prenotabile",115.42500000000001),(1,"Sabato","16:30:00","19:00:00","Prenotabile",111.375),(1,"Sabato","19:00:00","20:30:00","Prenotabile",111.375),(1,"Sabato","20:30:00","22:00:00","Prenotabile",101.25),(1,"Sabato","22:00:00","23:30:00","Prenotabile",101.25),
		(1,"Domenica","09:00:00","10:30:00","Prenotabile",90.0),(1,"Domenica","10:30:00","12:00:00","Prenotabile",90.0),(1,"Domenica","12:00:00","13:30:00","Prenotabile",90.0),(1,"Domenica","13:30:00","15:00:00","Prenotabile",90.0),(1,"Domenica","15:00:00","16:30:00","Prenotabile",102.6),(1,"Domenica","16:30:00","19:00:00","Prenotabile",99.0),(1,"Domenica","19:00:00","20:30:00","Prenotabile",99.0),(1,"Domenica","20:30:00","22:00:00","Prenotabile",90.0),(1,"Domenica","22:00:00","23:30:00","Prenotabile",90.0),

		(2,"Lunedi","09:00:00","10:30:00","Prenotabile",50.0),(2,"Lunedi","10:30:00","12:00:00","Prenotabile",50.0),(2,"Lunedi","12:00:00","13:30:00","Prenotabile",50.0),(2,"Lunedi","13:30:00","15:00:00","Prenotabile",50.0),(2,"Lunedi","15:00:00","16:30:00","Lezione",null),(2,"Lunedi","16:30:00","19:00:00","Lezione",null),(2,"Lunedi","19:00:00","20:30:00","Lezione",null),(2,"Lunedi","20:30:00","22:00:00","Prenotabile",50.0),(2,"Lunedi","22:00:00","23:30:00","Prenotabile",50.0),
		(2,"Martedi","09:00:00","10:30:00","Prenotabile",50.0),(2,"Martedi","10:30:00","12:00:00","Prenotabile",50.0),(2,"Martedi","12:00:00","13:30:00","Prenotabile",50.0),(2,"Martedi","13:30:00","15:00:00","Prenotabile",50.0),(2,"Martedi","15:00:00","16:30:00","Lezione",null),(2,"Martedi","16:30:00","19:00:00","Lezione",null),(2,"Martedi","19:00:00","20:30:00","Lezione",null),(2,"Martedi","20:30:00","22:00:00","Prenotabile",50.0),(2,"Martedi","22:00:00","23:30:00","Prenotabile",50.0),
		(2,"Mercoledi","09:00:00","10:30:00","Prenotabile",45.0),(2,"Mercoledi","10:30:00","12:00:00","Prenotabile",45.0),(2,"Mercoledi","12:00:00","13:30:00","Prenotabile",45.0),(2,"Mercoledi","13:30:00","15:00:00","Prenotabile",45.0),(2,"Mercoledi","15:00:00","16:30:00","Lezione",null),(2,"Mercoledi","16:30:00","19:00:00","Lezione",null),(2,"Mercoledi","19:00:00","20:30:00","Lezione",null),(2,"Mercoledi","20:30:00","22:00:00","Prenotabile",45.0),(2,"Mercoledi","22:00:00","23:30:00","Prenotabile",45.0),
		(2,"Giovedi","09:00:00","10:30:00","Prenotabile",50.0),(2,"Giovedi","10:30:00","12:00:00","Prenotabile",50.0),(2,"Giovedi","12:00:00","13:30:00","Prenotabile",50.0),(2,"Giovedi","13:30:00","15:00:00","Prenotabile",50.0),(2,"Giovedi","15:00:00","16:30:00","Lezione",null),(2,"Giovedi","16:30:00","19:00:00","Lezione",null),(2,"Giovedi","19:00:00","20:30:00","Lezione",null),(2,"Giovedi","20:30:00","22:00:00","Prenotabile",50.0),(2,"Giovedi","22:00:00","23:30:00","Prenotabile",50.0),
		(2,"Venerdi","09:00:00","10:30:00","Prenotabile",55.00000000000001),(2,"Venerdi","10:30:00","12:00:00","Prenotabile",55.00000000000001),(2,"Venerdi","12:00:00","13:30:00","Prenotabile",55.00000000000001),(2,"Venerdi","13:30:00","15:00:00","Prenotabile",55.00000000000001),(2,"Venerdi","15:00:00","16:30:00","Lezione",null),(2,"Venerdi","16:30:00","19:00:00","Lezione",null),(2,"Venerdi","19:00:00","20:30:00","Lezione",null),(2,"Venerdi","20:30:00","22:00:00","Prenotabile",55.00000000000001),(2,"Venerdi","22:00:00","23:30:00","Prenotabile",55.00000000000001),
		(2,"Sabato","09:00:00","10:30:00","Prenotabile",67.5),(2,"Sabato","10:30:00","12:00:00","Prenotabile",67.5),(2,"Sabato","12:00:00","13:30:00","Prenotabile",67.5),(2,"Sabato","13:30:00","15:00:00","Prenotabile",67.5),(2,"Sabato","15:00:00","16:30:00","Prenotabile",76.95),(2,"Sabato","16:30:00","19:00:00","Prenotabile",74.25),(2,"Sabato","19:00:00","20:30:00","Prenotabile",74.25),(2,"Sabato","20:30:00","22:00:00","Prenotabile",67.5),(2,"Sabato","22:00:00","23:30:00","Prenotabile",67.5),
		(2,"Domenica","09:00:00","10:30:00","Prenotabile",60.0),(2,"Domenica","10:30:00","12:00:00","Prenotabile",60.0),(2,"Domenica","12:00:00","13:30:00","Prenotabile",60.0),(2,"Domenica","13:30:00","15:00:00","Prenotabile",60.0),(2,"Domenica","15:00:00","16:30:00","Prenotabile",68.39999999999999),(2,"Domenica","16:30:00","19:00:00","Prenotabile",66.0),(2,"Domenica","19:00:00","20:30:00","Prenotabile",66.0),(2,"Domenica","20:30:00","22:00:00","Prenotabile",60.0),(2,"Domenica","22:00:00","23:30:00","Prenotabile",60.0),

		(3,"Lunedi","09:00:00","10:30:00","Prenotabile",50.0),(3,"Lunedi","10:30:00","12:00:00","Prenotabile",50.0),(3,"Lunedi","12:00:00","13:30:00","Prenotabile",50.0),(3,"Lunedi","13:30:00","15:00:00","Prenotabile",50.0),(3,"Lunedi","15:00:00","16:30:00","Lezione",null),(3,"Lunedi","16:30:00","19:00:00","Lezione",null),(3,"Lunedi","19:00:00","20:30:00","Lezione",null),(3,"Lunedi","20:30:00","22:00:00","Prenotabile",50.0),(3,"Lunedi","22:00:00","23:30:00","Prenotabile",50.0),
		(3,"Martedi","09:00:00","10:30:00","Prenotabile",50.0),(3,"Martedi","10:30:00","12:00:00","Prenotabile",50.0),(3,"Martedi","12:00:00","13:30:00","Prenotabile",50.0),(3,"Martedi","13:30:00","15:00:00","Prenotabile",50.0),(3,"Martedi","15:00:00","16:30:00","Lezione",null),(3,"Martedi","16:30:00","19:00:00","Lezione",null),(3,"Martedi","19:00:00","20:30:00","Lezione",null),(3,"Martedi","20:30:00","22:00:00","Prenotabile",50.0),(3,"Martedi","22:00:00","23:30:00","Prenotabile",50.0),
		(3,"Mercoledi","09:00:00","10:30:00","Prenotabile",45.0),(3,"Mercoledi","10:30:00","12:00:00","Prenotabile",45.0),(3,"Mercoledi","12:00:00","13:30:00","Prenotabile",45.0),(3,"Mercoledi","13:30:00","15:00:00","Prenotabile",45.0),(3,"Mercoledi","15:00:00","16:30:00","Lezione",null),(3,"Mercoledi","16:30:00","19:00:00","Lezione",null),(3,"Mercoledi","19:00:00","20:30:00","Lezione",null),(3,"Mercoledi","20:30:00","22:00:00","Prenotabile",45.0),(3,"Mercoledi","22:00:00","23:30:00","Prenotabile",45.0),
		(3,"Giovedi","09:00:00","10:30:00","Prenotabile",50.0),(3,"Giovedi","10:30:00","12:00:00","Prenotabile",50.0),(3,"Giovedi","12:00:00","13:30:00","Prenotabile",50.0),(3,"Giovedi","13:30:00","15:00:00","Prenotabile",50.0),(3,"Giovedi","15:00:00","16:30:00","Lezione",null),(3,"Giovedi","16:30:00","19:00:00","Lezione",null),(3,"Giovedi","19:00:00","20:30:00","Lezione",null),(3,"Giovedi","20:30:00","22:00:00","Prenotabile",50.0),(3,"Giovedi","22:00:00","23:30:00","Prenotabile",50.0),
		(3,"Venerdi","09:00:00","10:30:00","Prenotabile",55.00000000000001),(3,"Venerdi","10:30:00","12:00:00","Prenotabile",55.00000000000001),(3,"Venerdi","12:00:00","13:30:00","Prenotabile",55.00000000000001),(3,"Venerdi","13:30:00","15:00:00","Prenotabile",55.00000000000001),(3,"Venerdi","15:00:00","16:30:00","Lezione",null),(3,"Venerdi","16:30:00","19:00:00","Lezione",null),(3,"Venerdi","19:00:00","20:30:00","Lezione",null),(3,"Venerdi","20:30:00","22:00:00","Prenotabile",55.00000000000001),(3,"Venerdi","22:00:00","23:30:00","Prenotabile",55.00000000000001),
		(3,"Sabato","09:00:00","10:30:00","Prenotabile",67.5),(3,"Sabato","10:30:00","12:00:00","Prenotabile",67.5),(3,"Sabato","12:00:00","13:30:00","Prenotabile",67.5),(3,"Sabato","13:30:00","15:00:00","Prenotabile",67.5),(3,"Sabato","15:00:00","16:30:00","Prenotabile",76.95),(3,"Sabato","16:30:00","19:00:00","Prenotabile",74.25),(3,"Sabato","19:00:00","20:30:00","Prenotabile",74.25),(3,"Sabato","20:30:00","22:00:00","Prenotabile",67.5),(3,"Sabato","22:00:00","23:30:00","Prenotabile",67.5),
		(3,"Domenica","09:00:00","10:30:00","Prenotabile",60.0),(3,"Domenica","10:30:00","12:00:00","Prenotabile",60.0),(3,"Domenica","12:00:00","13:30:00","Prenotabile",60.0),(3,"Domenica","13:30:00","15:00:00","Prenotabile",60.0),(3,"Domenica","15:00:00","16:30:00","Prenotabile",68.39999999999999),(3,"Domenica","16:30:00","19:00:00","Prenotabile",66.0),(3,"Domenica","19:00:00","20:30:00","Prenotabile",66.0),(3,"Domenica","20:30:00","22:00:00","Prenotabile",60.0),(3,"Domenica","22:00:00","23:30:00","Prenotabile",60.0),

		(4,"Lunedi","09:00:00","10:30:00","Prenotabile",50.0),(4,"Lunedi","10:30:00","12:00:00","Prenotabile",50.0),(4,"Lunedi","12:00:00","13:30:00","Prenotabile",50.0),(4,"Lunedi","13:30:00","15:00:00","Prenotabile",50.0),(4,"Lunedi","15:00:00","16:30:00","Lezione",null),(4,"Lunedi","16:30:00","19:00:00","Lezione",null),(4,"Lunedi","19:00:00","20:30:00","Lezione",null),(4,"Lunedi","20:30:00","22:00:00","Prenotabile",50.0),(4,"Lunedi","22:00:00","23:30:00","Prenotabile",50.0),
		(4,"Martedi","09:00:00","10:30:00","Prenotabile",50.0),(4,"Martedi","10:30:00","12:00:00","Prenotabile",50.0),(4,"Martedi","12:00:00","13:30:00","Prenotabile",50.0),(4,"Martedi","13:30:00","15:00:00","Prenotabile",50.0),(4,"Martedi","15:00:00","16:30:00","Lezione",null),(4,"Martedi","16:30:00","19:00:00","Lezione",null),(4,"Martedi","19:00:00","20:30:00","Lezione",null),(4,"Martedi","20:30:00","22:00:00","Prenotabile",50.0),(4,"Martedi","22:00:00","23:30:00","Prenotabile",50.0),
		(4,"Mercoledi","09:00:00","10:30:00","Prenotabile",45.0),(4,"Mercoledi","10:30:00","12:00:00","Prenotabile",45.0),(4,"Mercoledi","12:00:00","13:30:00","Prenotabile",45.0),(4,"Mercoledi","13:30:00","15:00:00","Prenotabile",45.0),(4,"Mercoledi","15:00:00","16:30:00","Lezione",null),(4,"Mercoledi","16:30:00","19:00:00","Lezione",null),(4,"Mercoledi","19:00:00","20:30:00","Lezione",null),(4,"Mercoledi","20:30:00","22:00:00","Prenotabile",45.0),(4,"Mercoledi","22:00:00","23:30:00","Prenotabile",45.0),
		(4,"Giovedi","09:00:00","10:30:00","Prenotabile",50.0),(4,"Giovedi","10:30:00","12:00:00","Prenotabile",50.0),(4,"Giovedi","12:00:00","13:30:00","Prenotabile",50.0),(4,"Giovedi","13:30:00","15:00:00","Prenotabile",50.0),(4,"Giovedi","15:00:00","16:30:00","Lezione",null),(4,"Giovedi","16:30:00","19:00:00","Lezione",null),(4,"Giovedi","19:00:00","20:30:00","Lezione",null),(4,"Giovedi","20:30:00","22:00:00","Prenotabile",50.0),(4,"Giovedi","22:00:00","23:30:00","Prenotabile",50.0),
		(4,"Venerdi","09:00:00","10:30:00","Prenotabile",55.00000000000001),(4,"Venerdi","10:30:00","12:00:00","Prenotabile",55.00000000000001),(4,"Venerdi","12:00:00","13:30:00","Prenotabile",55.00000000000001),(4,"Venerdi","13:30:00","15:00:00","Prenotabile",55.00000000000001),(4,"Venerdi","15:00:00","16:30:00","Lezione",null),(4,"Venerdi","16:30:00","19:00:00","Lezione",null),(4,"Venerdi","19:00:00","20:30:00","Lezione",null),(4,"Venerdi","20:30:00","22:00:00","Prenotabile",55.00000000000001),(4,"Venerdi","22:00:00","23:30:00","Prenotabile",55.00000000000001),
		(4,"Sabato","09:00:00","10:30:00","Prenotabile",67.5),(4,"Sabato","10:30:00","12:00:00","Prenotabile",67.5),(4,"Sabato","12:00:00","13:30:00","Prenotabile",67.5),(4,"Sabato","13:30:00","15:00:00","Prenotabile",67.5),(4,"Sabato","15:00:00","16:30:00","Prenotabile",76.95),(4,"Sabato","16:30:00","19:00:00","Prenotabile",74.25),(4,"Sabato","19:00:00","20:30:00","Prenotabile",74.25),(4,"Sabato","20:30:00","22:00:00","Prenotabile",67.5),(4,"Sabato","22:00:00","23:30:00","Prenotabile",67.5),
		(4,"Domenica","09:00:00","10:30:00","Prenotabile",60.0),(4,"Domenica","10:30:00","12:00:00","Prenotabile",60.0),(4,"Domenica","12:00:00","13:30:00","Prenotabile",60.0),(4,"Domenica","13:30:00","15:00:00","Prenotabile",60.0),(4,"Domenica","15:00:00","16:30:00","Prenotabile",68.39999999999999),(4,"Domenica","16:30:00","19:00:00","Prenotabile",66.0),(4,"Domenica","19:00:00","20:30:00","Prenotabile",66.0),(4,"Domenica","20:30:00","22:00:00","Prenotabile",60.0),(4,"Domenica","22:00:00","23:30:00","Prenotabile",60.0),

		(5,"Lunedi","09:00:00","10:30:00","Prenotabile",75.0),(5,"Lunedi","10:30:00","12:00:00","Prenotabile",75.0),(5,"Lunedi","12:00:00","13:30:00","Prenotabile",75.0),(5,"Lunedi","13:30:00","15:00:00","Prenotabile",75.0),(5,"Lunedi","15:00:00","16:30:00","Lezione",null),(5,"Lunedi","16:30:00","19:00:00","Lezione",null),(5,"Lunedi","19:00:00","20:30:00","Lezione",null),(5,"Lunedi","20:30:00","22:00:00","Prenotabile",75.0),(5,"Lunedi","22:00:00","23:30:00","Prenotabile",75.0),
		(5,"Martedi","09:00:00","10:30:00","Prenotabile",75.0),(5,"Martedi","10:30:00","12:00:00","Prenotabile",75.0),(5,"Martedi","12:00:00","13:30:00","Prenotabile",75.0),(5,"Martedi","13:30:00","15:00:00","Prenotabile",75.0),(5,"Martedi","15:00:00","16:30:00","Lezione",null),(5,"Martedi","16:30:00","19:00:00","Lezione",null),(5,"Martedi","19:00:00","20:30:00","Lezione",null),(5,"Martedi","20:30:00","22:00:00","Prenotabile",75.0),(5,"Martedi","22:00:00","23:30:00","Prenotabile",75.0),
		(5,"Mercoledi","09:00:00","10:30:00","Prenotabile",67.5),(5,"Mercoledi","10:30:00","12:00:00","Prenotabile",67.5),(5,"Mercoledi","12:00:00","13:30:00","Prenotabile",67.5),(5,"Mercoledi","13:30:00","15:00:00","Prenotabile",67.5),(5,"Mercoledi","15:00:00","16:30:00","Lezione",null),(5,"Mercoledi","16:30:00","19:00:00","Lezione",null),(5,"Mercoledi","19:00:00","20:30:00","Lezione",null),(5,"Mercoledi","20:30:00","22:00:00","Prenotabile",67.5),(5,"Mercoledi","22:00:00","23:30:00","Prenotabile",67.5),
		(5,"Giovedi","09:00:00","10:30:00","Prenotabile",75.0),(5,"Giovedi","10:30:00","12:00:00","Prenotabile",75.0),(5,"Giovedi","12:00:00","13:30:00","Prenotabile",75.0),(5,"Giovedi","13:30:00","15:00:00","Prenotabile",75.0),(5,"Giovedi","15:00:00","16:30:00","Lezione",null),(5,"Giovedi","16:30:00","19:00:00","Lezione",null),(5,"Giovedi","19:00:00","20:30:00","Lezione",null),(5,"Giovedi","20:30:00","22:00:00","Prenotabile",75.0),(5,"Giovedi","22:00:00","23:30:00","Prenotabile",75.0),
		(5,"Venerdi","09:00:00","10:30:00","Prenotabile",82.50000000000001),(5,"Venerdi","10:30:00","12:00:00","Prenotabile",82.50000000000001),(5,"Venerdi","12:00:00","13:30:00","Prenotabile",82.50000000000001),(5,"Venerdi","13:30:00","15:00:00","Prenotabile",82.50000000000001),(5,"Venerdi","15:00:00","16:30:00","Lezione",null),(5,"Venerdi","16:30:00","19:00:00","Lezione",null),(5,"Venerdi","19:00:00","20:30:00","Lezione",null),(5,"Venerdi","20:30:00","22:00:00","Prenotabile",82.50000000000001),(5,"Venerdi","22:00:00","23:30:00","Prenotabile",82.50000000000001),
		(5,"Sabato","09:00:00","10:30:00","Prenotabile",101.25),(5,"Sabato","10:30:00","12:00:00","Prenotabile",101.25),(5,"Sabato","12:00:00","13:30:00","Prenotabile",101.25),(5,"Sabato","13:30:00","15:00:00","Prenotabile",101.25),(5,"Sabato","15:00:00","16:30:00","Prenotabile",115.42500000000001),(5,"Sabato","16:30:00","19:00:00","Prenotabile",111.375),(5,"Sabato","19:00:00","20:30:00","Prenotabile",111.375),(5,"Sabato","20:30:00","22:00:00","Prenotabile",101.25),(5,"Sabato","22:00:00","23:30:00","Prenotabile",101.25),
		(5,"Domenica","09:00:00","10:30:00","Prenotabile",90.0),(5,"Domenica","10:30:00","12:00:00","Prenotabile",90.0),(5,"Domenica","12:00:00","13:30:00","Prenotabile",90.0),(5,"Domenica","13:30:00","15:00:00","Prenotabile",90.0),(5,"Domenica","15:00:00","16:30:00","Prenotabile",102.6),(5,"Domenica","16:30:00","19:00:00","Prenotabile",99.0),(5,"Domenica","19:00:00","20:30:00","Prenotabile",99.0),(5,"Domenica","20:30:00","22:00:00","Prenotabile",90.0),(5,"Domenica","22:00:00","23:30:00","Prenotabile",90.0),

		(6,"Lunedi","09:00:00","10:30:00","Prenotabile",75.0),(6,"Lunedi","10:30:00","12:00:00","Prenotabile",75.0),(6,"Lunedi","12:00:00","13:30:00","Prenotabile",75.0),(6,"Lunedi","13:30:00","15:00:00","Prenotabile",75.0),(6,"Lunedi","15:00:00","16:30:00","Lezione",null),(6,"Lunedi","16:30:00","19:00:00","Lezione",null),(6,"Lunedi","19:00:00","20:30:00","Lezione",null),(6,"Lunedi","20:30:00","22:00:00","Prenotabile",75.0),(6,"Lunedi","22:00:00","23:30:00","Prenotabile",75.0),
		(6,"Martedi","09:00:00","10:30:00","Prenotabile",75.0),(6,"Martedi","10:30:00","12:00:00","Prenotabile",75.0),(6,"Martedi","12:00:00","13:30:00","Prenotabile",75.0),(6,"Martedi","13:30:00","15:00:00","Prenotabile",75.0),(6,"Martedi","15:00:00","16:30:00","Lezione",null),(6,"Martedi","16:30:00","19:00:00","Lezione",null),(6,"Martedi","19:00:00","20:30:00","Lezione",null),(6,"Martedi","20:30:00","22:00:00","Prenotabile",75.0),(6,"Martedi","22:00:00","23:30:00","Prenotabile",75.0),
		(6,"Mercoledi","09:00:00","10:30:00","Prenotabile",67.5),(6,"Mercoledi","10:30:00","12:00:00","Prenotabile",67.5),(6,"Mercoledi","12:00:00","13:30:00","Prenotabile",67.5),(6,"Mercoledi","13:30:00","15:00:00","Prenotabile",67.5),(6,"Mercoledi","15:00:00","16:30:00","Lezione",null),(6,"Mercoledi","16:30:00","19:00:00","Lezione",null),(6,"Mercoledi","19:00:00","20:30:00","Lezione",null),(6,"Mercoledi","20:30:00","22:00:00","Prenotabile",67.5),(6,"Mercoledi","22:00:00","23:30:00","Prenotabile",67.5),
		(6,"Giovedi","09:00:00","10:30:00","Prenotabile",75.0),(6,"Giovedi","10:30:00","12:00:00","Prenotabile",75.0),(6,"Giovedi","12:00:00","13:30:00","Prenotabile",75.0),(6,"Giovedi","13:30:00","15:00:00","Prenotabile",75.0),(6,"Giovedi","15:00:00","16:30:00","Lezione",null),(6,"Giovedi","16:30:00","19:00:00","Lezione",null),(6,"Giovedi","19:00:00","20:30:00","Lezione",null),(6,"Giovedi","20:30:00","22:00:00","Prenotabile",75.0),(6,"Giovedi","22:00:00","23:30:00","Prenotabile",75.0),
		(6,"Venerdi","09:00:00","10:30:00","Prenotabile",82.50000000000001),(6,"Venerdi","10:30:00","12:00:00","Prenotabile",82.50000000000001),(6,"Venerdi","12:00:00","13:30:00","Prenotabile",82.50000000000001),(6,"Venerdi","13:30:00","15:00:00","Prenotabile",82.50000000000001),(6,"Venerdi","15:00:00","16:30:00","Lezione",null),(6,"Venerdi","16:30:00","19:00:00","Lezione",null),(6,"Venerdi","19:00:00","20:30:00","Lezione",null),(6,"Venerdi","20:30:00","22:00:00","Prenotabile",82.50000000000001),(6,"Venerdi","22:00:00","23:30:00","Prenotabile",82.50000000000001),
		(6,"Sabato","09:00:00","10:30:00","Prenotabile",101.25),(6,"Sabato","10:30:00","12:00:00","Prenotabile",101.25),(6,"Sabato","12:00:00","13:30:00","Prenotabile",101.25),(6,"Sabato","13:30:00","15:00:00","Prenotabile",101.25),(6,"Sabato","15:00:00","16:30:00","Prenotabile",115.42500000000001),(6,"Sabato","16:30:00","19:00:00","Prenotabile",111.375),(6,"Sabato","19:00:00","20:30:00","Prenotabile",111.375),(6,"Sabato","20:30:00","22:00:00","Prenotabile",101.25),(6,"Sabato","22:00:00","23:30:00","Prenotabile",101.25),
		(6,"Domenica","09:00:00","10:30:00","Prenotabile",90.0),(6,"Domenica","10:30:00","12:00:00","Prenotabile",90.0),(6,"Domenica","12:00:00","13:30:00","Prenotabile",90.0),(6,"Domenica","13:30:00","15:00:00","Prenotabile",90.0),(6,"Domenica","15:00:00","16:30:00","Prenotabile",102.6),(6,"Domenica","16:30:00","19:00:00","Prenotabile",99.0),(6,"Domenica","19:00:00","20:30:00","Prenotabile",99.0),(6,"Domenica","20:30:00","22:00:00","Prenotabile",90.0),(6,"Domenica","22:00:00","23:30:00","Prenotabile",90.0),

		(7,"Lunedi","09:00:00","10:30:00","Prenotabile",50.0),(7,"Lunedi","10:30:00","12:00:00","Prenotabile",50.0),(7,"Lunedi","12:00:00","13:30:00","Prenotabile",50.0),(7,"Lunedi","13:30:00","15:00:00","Prenotabile",50.0),(7,"Lunedi","15:00:00","16:30:00","Lezione",null),(7,"Lunedi","16:30:00","19:00:00","Lezione",null),(7,"Lunedi","19:00:00","20:30:00","Lezione",null),(7,"Lunedi","20:30:00","22:00:00","Prenotabile",50.0),(7,"Lunedi","22:00:00","23:30:00","Prenotabile",50.0),
		(7,"Martedi","09:00:00","10:30:00","Prenotabile",50.0),(7,"Martedi","10:30:00","12:00:00","Prenotabile",50.0),(7,"Martedi","12:00:00","13:30:00","Prenotabile",50.0),(7,"Martedi","13:30:00","15:00:00","Prenotabile",50.0),(7,"Martedi","15:00:00","16:30:00","Lezione",null),(7,"Martedi","16:30:00","19:00:00","Lezione",null),(7,"Martedi","19:00:00","20:30:00","Lezione",null),(7,"Martedi","20:30:00","22:00:00","Prenotabile",50.0),(7,"Martedi","22:00:00","23:30:00","Prenotabile",50.0),
		(7,"Mercoledi","09:00:00","10:30:00","Prenotabile",45.0),(7,"Mercoledi","10:30:00","12:00:00","Prenotabile",45.0),(7,"Mercoledi","12:00:00","13:30:00","Prenotabile",45.0),(7,"Mercoledi","13:30:00","15:00:00","Prenotabile",45.0),(7,"Mercoledi","15:00:00","16:30:00","Lezione",null),(7,"Mercoledi","16:30:00","19:00:00","Lezione",null),(7,"Mercoledi","19:00:00","20:30:00","Lezione",null),(7,"Mercoledi","20:30:00","22:00:00","Prenotabile",45.0),(7,"Mercoledi","22:00:00","23:30:00","Prenotabile",45.0),
		(7,"Giovedi","09:00:00","10:30:00","Prenotabile",50.0),(7,"Giovedi","10:30:00","12:00:00","Prenotabile",50.0),(7,"Giovedi","12:00:00","13:30:00","Prenotabile",50.0),(7,"Giovedi","13:30:00","15:00:00","Prenotabile",50.0),(7,"Giovedi","15:00:00","16:30:00","Lezione",null),(7,"Giovedi","16:30:00","19:00:00","Lezione",null),(7,"Giovedi","19:00:00","20:30:00","Lezione",null),(7,"Giovedi","20:30:00","22:00:00","Prenotabile",50.0),(7,"Giovedi","22:00:00","23:30:00","Prenotabile",50.0),
		(7,"Venerdi","09:00:00","10:30:00","Prenotabile",55.00000000000001),(7,"Venerdi","10:30:00","12:00:00","Prenotabile",55.00000000000001),(7,"Venerdi","12:00:00","13:30:00","Prenotabile",55.00000000000001),(7,"Venerdi","13:30:00","15:00:00","Prenotabile",55.00000000000001),(7,"Venerdi","15:00:00","16:30:00","Lezione",null),(7,"Venerdi","16:30:00","19:00:00","Lezione",null),(7,"Venerdi","19:00:00","20:30:00","Lezione",null),(7,"Venerdi","20:30:00","22:00:00","Prenotabile",55.00000000000001),(7,"Venerdi","22:00:00","23:30:00","Prenotabile",55.00000000000001),
		(7,"Sabato","09:00:00","10:30:00","Prenotabile",67.5),(7,"Sabato","10:30:00","12:00:00","Prenotabile",67.5),(7,"Sabato","12:00:00","13:30:00","Prenotabile",67.5),(7,"Sabato","13:30:00","15:00:00","Prenotabile",67.5),(7,"Sabato","15:00:00","16:30:00","Prenotabile",76.95),(7,"Sabato","16:30:00","19:00:00","Prenotabile",74.25),(7,"Sabato","19:00:00","20:30:00","Prenotabile",74.25),(7,"Sabato","20:30:00","22:00:00","Prenotabile",67.5),(7,"Sabato","22:00:00","23:30:00","Prenotabile",67.5),
		(7,"Domenica","09:00:00","10:30:00","Prenotabile",60.0),(7,"Domenica","10:30:00","12:00:00","Prenotabile",60.0),(7,"Domenica","12:00:00","13:30:00","Prenotabile",60.0),(7,"Domenica","13:30:00","15:00:00","Prenotabile",60.0),(7,"Domenica","15:00:00","16:30:00","Prenotabile",68.39999999999999),(7,"Domenica","16:30:00","19:00:00","Prenotabile",66.0),(7,"Domenica","19:00:00","20:30:00","Prenotabile",66.0),(7,"Domenica","20:30:00","22:00:00","Prenotabile",60.0),(7,"Domenica","22:00:00","23:30:00","Prenotabile",60.0),

		(8,"Lunedi","09:00:00","10:30:00","Prenotabile",50.0),(8,"Lunedi","10:30:00","12:00:00","Prenotabile",50.0),(8,"Lunedi","12:00:00","13:30:00","Prenotabile",50.0),(8,"Lunedi","13:30:00","15:00:00","Prenotabile",50.0),(8,"Lunedi","15:00:00","16:30:00","Lezione",null),(8,"Lunedi","16:30:00","19:00:00","Lezione",null),(8,"Lunedi","19:00:00","20:30:00","Lezione",null),(8,"Lunedi","20:30:00","22:00:00","Prenotabile",50.0),(8,"Lunedi","22:00:00","23:30:00","Prenotabile",50.0),
		(8,"Martedi","09:00:00","10:30:00","Prenotabile",50.0),(8,"Martedi","10:30:00","12:00:00","Prenotabile",50.0),(8,"Martedi","12:00:00","13:30:00","Prenotabile",50.0),(8,"Martedi","13:30:00","15:00:00","Prenotabile",50.0),(8,"Martedi","15:00:00","16:30:00","Lezione",null),(8,"Martedi","16:30:00","19:00:00","Lezione",null),(8,"Martedi","19:00:00","20:30:00","Lezione",null),(8,"Martedi","20:30:00","22:00:00","Prenotabile",50.0),(8,"Martedi","22:00:00","23:30:00","Prenotabile",50.0),
		(8,"Mercoledi","09:00:00","10:30:00","Prenotabile",45.0),(8,"Mercoledi","10:30:00","12:00:00","Prenotabile",45.0),(8,"Mercoledi","12:00:00","13:30:00","Prenotabile",45.0),(8,"Mercoledi","13:30:00","15:00:00","Prenotabile",45.0),(8,"Mercoledi","15:00:00","16:30:00","Lezione",null),(8,"Mercoledi","16:30:00","19:00:00","Lezione",null),(8,"Mercoledi","19:00:00","20:30:00","Lezione",null),(8,"Mercoledi","20:30:00","22:00:00","Prenotabile",45.0),(8,"Mercoledi","22:00:00","23:30:00","Prenotabile",45.0),
		(8,"Giovedi","09:00:00","10:30:00","Prenotabile",50.0),(8,"Giovedi","10:30:00","12:00:00","Prenotabile",50.0),(8,"Giovedi","12:00:00","13:30:00","Prenotabile",50.0),(8,"Giovedi","13:30:00","15:00:00","Prenotabile",50.0),(8,"Giovedi","15:00:00","16:30:00","Lezione",null),(8,"Giovedi","16:30:00","19:00:00","Lezione",null),(8,"Giovedi","19:00:00","20:30:00","Lezione",null),(8,"Giovedi","20:30:00","22:00:00","Prenotabile",50.0),(8,"Giovedi","22:00:00","23:30:00","Prenotabile",50.0),
		(8,"Venerdi","09:00:00","10:30:00","Prenotabile",55.00000000000001),(8,"Venerdi","10:30:00","12:00:00","Prenotabile",55.00000000000001),(8,"Venerdi","12:00:00","13:30:00","Prenotabile",55.00000000000001),(8,"Venerdi","13:30:00","15:00:00","Prenotabile",55.00000000000001),(8,"Venerdi","15:00:00","16:30:00","Lezione",null),(8,"Venerdi","16:30:00","19:00:00","Lezione",null),(8,"Venerdi","19:00:00","20:30:00","Lezione",null),(8,"Venerdi","20:30:00","22:00:00","Prenotabile",55.00000000000001),(8,"Venerdi","22:00:00","23:30:00","Prenotabile",55.00000000000001),
		(8,"Sabato","09:00:00","10:30:00","Prenotabile",67.5),(8,"Sabato","10:30:00","12:00:00","Prenotabile",67.5),(8,"Sabato","12:00:00","13:30:00","Prenotabile",67.5),(8,"Sabato","13:30:00","15:00:00","Prenotabile",67.5),(8,"Sabato","15:00:00","16:30:00","Prenotabile",76.95),(8,"Sabato","16:30:00","19:00:00","Prenotabile",74.25),(8,"Sabato","19:00:00","20:30:00","Prenotabile",74.25),(8,"Sabato","20:30:00","22:00:00","Prenotabile",67.5),(8,"Sabato","22:00:00","23:30:00","Prenotabile",67.5),
		(8,"Domenica","09:00:00","10:30:00","Prenotabile",60.0),(8,"Domenica","10:30:00","12:00:00","Prenotabile",60.0),(8,"Domenica","12:00:00","13:30:00","Prenotabile",60.0),(8,"Domenica","13:30:00","15:00:00","Prenotabile",60.0),(8,"Domenica","15:00:00","16:30:00","Prenotabile",68.39999999999999),(8,"Domenica","16:30:00","19:00:00","Prenotabile",66.0),(8,"Domenica","19:00:00","20:30:00","Prenotabile",66.0),(8,"Domenica","20:30:00","22:00:00","Prenotabile",60.0),(8,"Domenica","22:00:00","23:30:00","Prenotabile",60.0),

		(9,"Lunedi","09:00:00","10:30:00","Prenotabile",50.0),(9,"Lunedi","10:30:00","12:00:00","Prenotabile",50.0),(9,"Lunedi","12:00:00","13:30:00","Prenotabile",50.0),(9,"Lunedi","13:30:00","15:00:00","Prenotabile",50.0),(9,"Lunedi","15:00:00","16:30:00","Lezione",null),(9,"Lunedi","16:30:00","19:00:00","Lezione",null),(9,"Lunedi","19:00:00","20:30:00","Lezione",null),(9,"Lunedi","20:30:00","22:00:00","Prenotabile",50.0),(9,"Lunedi","22:00:00","23:30:00","Prenotabile",50.0),
		(9,"Martedi","09:00:00","10:30:00","Prenotabile",50.0),(9,"Martedi","10:30:00","12:00:00","Prenotabile",50.0),(9,"Martedi","12:00:00","13:30:00","Prenotabile",50.0),(9,"Martedi","13:30:00","15:00:00","Prenotabile",50.0),(9,"Martedi","15:00:00","16:30:00","Lezione",null),(9,"Martedi","16:30:00","19:00:00","Lezione",null),(9,"Martedi","19:00:00","20:30:00","Lezione",null),(9,"Martedi","20:30:00","22:00:00","Prenotabile",50.0),(9,"Martedi","22:00:00","23:30:00","Prenotabile",50.0),
		(9,"Mercoledi","09:00:00","10:30:00","Prenotabile",45.0),(9,"Mercoledi","10:30:00","12:00:00","Prenotabile",45.0),(9,"Mercoledi","12:00:00","13:30:00","Prenotabile",45.0),(9,"Mercoledi","13:30:00","15:00:00","Prenotabile",45.0),(9,"Mercoledi","15:00:00","16:30:00","Lezione",null),(9,"Mercoledi","16:30:00","19:00:00","Lezione",null),(9,"Mercoledi","19:00:00","20:30:00","Lezione",null),(9,"Mercoledi","20:30:00","22:00:00","Prenotabile",45.0),(9,"Mercoledi","22:00:00","23:30:00","Prenotabile",45.0),
		(9,"Giovedi","09:00:00","10:30:00","Prenotabile",50.0),(9,"Giovedi","10:30:00","12:00:00","Prenotabile",50.0),(9,"Giovedi","12:00:00","13:30:00","Prenotabile",50.0),(9,"Giovedi","13:30:00","15:00:00","Prenotabile",50.0),(9,"Giovedi","15:00:00","16:30:00","Lezione",null),(9,"Giovedi","16:30:00","19:00:00","Lezione",null),(9,"Giovedi","19:00:00","20:30:00","Lezione",null),(9,"Giovedi","20:30:00","22:00:00","Prenotabile",50.0),(9,"Giovedi","22:00:00","23:30:00","Prenotabile",50.0),
		(9,"Venerdi","09:00:00","10:30:00","Prenotabile",55.00000000000001),(9,"Venerdi","10:30:00","12:00:00","Prenotabile",55.00000000000001),(9,"Venerdi","12:00:00","13:30:00","Prenotabile",55.00000000000001),(9,"Venerdi","13:30:00","15:00:00","Prenotabile",55.00000000000001),(9,"Venerdi","15:00:00","16:30:00","Lezione",null),(9,"Venerdi","16:30:00","19:00:00","Lezione",null),(9,"Venerdi","19:00:00","20:30:00","Lezione",null),(9,"Venerdi","20:30:00","22:00:00","Prenotabile",55.00000000000001),(9,"Venerdi","22:00:00","23:30:00","Prenotabile",55.00000000000001),
		(9,"Sabato","09:00:00","10:30:00","Prenotabile",67.5),(9,"Sabato","10:30:00","12:00:00","Prenotabile",67.5),(9,"Sabato","12:00:00","13:30:00","Prenotabile",67.5),(9,"Sabato","13:30:00","15:00:00","Prenotabile",67.5),(9,"Sabato","15:00:00","16:30:00","Prenotabile",76.95),(9,"Sabato","16:30:00","19:00:00","Prenotabile",74.25),(9,"Sabato","19:00:00","20:30:00","Prenotabile",74.25),(9,"Sabato","20:30:00","22:00:00","Prenotabile",67.5),(9,"Sabato","22:00:00","23:30:00","Prenotabile",67.5),
		(9,"Domenica","09:00:00","10:30:00","Prenotabile",60.0),(9,"Domenica","10:30:00","12:00:00","Prenotabile",60.0),(9,"Domenica","12:00:00","13:30:00","Prenotabile",60.0),(9,"Domenica","13:30:00","15:00:00","Prenotabile",60.0),(9,"Domenica","15:00:00","16:30:00","Prenotabile",68.39999999999999),(9,"Domenica","16:30:00","19:00:00","Prenotabile",66.0),(9,"Domenica","19:00:00","20:30:00","Prenotabile",66.0),(9,"Domenica","20:30:00","22:00:00","Prenotabile",60.0),(9,"Domenica","22:00:00","23:30:00","Prenotabile",60.0),

		(10,"Lunedi","09:00:00","10:30:00","Prenotabile",50.0),(10,"Lunedi","10:30:00","12:00:00","Prenotabile",50.0),(10,"Lunedi","12:00:00","13:30:00","Prenotabile",50.0),(10,"Lunedi","13:30:00","15:00:00","Prenotabile",50.0),(10,"Lunedi","15:00:00","16:30:00","Lezione",null),(10,"Lunedi","16:30:00","19:00:00","Lezione",null),(10,"Lunedi","19:00:00","20:30:00","Lezione",null),(10,"Lunedi","20:30:00","22:00:00","Prenotabile",50.0),(10,"Lunedi","22:00:00","23:30:00","Prenotabile",50.0),
		(10,"Martedi","09:00:00","10:30:00","Prenotabile",50.0),(10,"Martedi","10:30:00","12:00:00","Prenotabile",50.0),(10,"Martedi","12:00:00","13:30:00","Prenotabile",50.0),(10,"Martedi","13:30:00","15:00:00","Prenotabile",50.0),(10,"Martedi","15:00:00","16:30:00","Lezione",null),(10,"Martedi","16:30:00","19:00:00","Lezione",null),(10,"Martedi","19:00:00","20:30:00","Lezione",null),(10,"Martedi","20:30:00","22:00:00","Prenotabile",50.0),(10,"Martedi","22:00:00","23:30:00","Prenotabile",50.0),
		(10,"Mercoledi","09:00:00","10:30:00","Prenotabile",45.0),(10,"Mercoledi","10:30:00","12:00:00","Prenotabile",45.0),(10,"Mercoledi","12:00:00","13:30:00","Prenotabile",45.0),(10,"Mercoledi","13:30:00","15:00:00","Prenotabile",45.0),(10,"Mercoledi","15:00:00","16:30:00","Lezione",null),(10,"Mercoledi","16:30:00","19:00:00","Lezione",null),(10,"Mercoledi","19:00:00","20:30:00","Lezione",null),(10,"Mercoledi","20:30:00","22:00:00","Prenotabile",45.0),(10,"Mercoledi","22:00:00","23:30:00","Prenotabile",45.0),
		(10,"Giovedi","09:00:00","10:30:00","Prenotabile",50.0),(10,"Giovedi","10:30:00","12:00:00","Prenotabile",50.0),(10,"Giovedi","12:00:00","13:30:00","Prenotabile",50.0),(10,"Giovedi","13:30:00","15:00:00","Prenotabile",50.0),(10,"Giovedi","15:00:00","16:30:00","Lezione",null),(10,"Giovedi","16:30:00","19:00:00","Lezione",null),(10,"Giovedi","19:00:00","20:30:00","Lezione",null),(10,"Giovedi","20:30:00","22:00:00","Prenotabile",50.0),(10,"Giovedi","22:00:00","23:30:00","Prenotabile",50.0),
		(10,"Venerdi","09:00:00","10:30:00","Prenotabile",55.00000000000001),(10,"Venerdi","10:30:00","12:00:00","Prenotabile",55.00000000000001),(10,"Venerdi","12:00:00","13:30:00","Prenotabile",55.00000000000001),(10,"Venerdi","13:30:00","15:00:00","Prenotabile",55.00000000000001),(10,"Venerdi","15:00:00","16:30:00","Lezione",null),(10,"Venerdi","16:30:00","19:00:00","Lezione",null),(10,"Venerdi","19:00:00","20:30:00","Lezione",null),(10,"Venerdi","20:30:00","22:00:00","Prenotabile",55.00000000000001),(10,"Venerdi","22:00:00","23:30:00","Prenotabile",55.00000000000001),
		(10,"Sabato","09:00:00","10:30:00","Prenotabile",67.5),(10,"Sabato","10:30:00","12:00:00","Prenotabile",67.5),(10,"Sabato","12:00:00","13:30:00","Prenotabile",67.5),(10,"Sabato","13:30:00","15:00:00","Prenotabile",67.5),(10,"Sabato","15:00:00","16:30:00","Prenotabile",76.95),(10,"Sabato","16:30:00","19:00:00","Prenotabile",74.25),(10,"Sabato","19:00:00","20:30:00","Prenotabile",74.25),(10,"Sabato","20:30:00","22:00:00","Prenotabile",67.5),(10,"Sabato","22:00:00","23:30:00","Prenotabile",67.5),
		(10,"Domenica","09:00:00","10:30:00","Prenotabile",60.0),(10,"Domenica","10:30:00","12:00:00","Prenotabile",60.0),(10,"Domenica","12:00:00","13:30:00","Prenotabile",60.0),(10,"Domenica","13:30:00","15:00:00","Prenotabile",60.0),(10,"Domenica","15:00:00","16:30:00","Prenotabile",68.39999999999999),(10,"Domenica","16:30:00","19:00:00","Prenotabile",66.0),(10,"Domenica","19:00:00","20:30:00","Prenotabile",66.0),(10,"Domenica","20:30:00","22:00:00","Prenotabile",60.0),(10,"Domenica","22:00:00","23:30:00","Prenotabile",60.0),

		(11,"Lunedi","09:00:00","10:30:00","Prenotabile",50.0),(11,"Lunedi","10:30:00","12:00:00","Prenotabile",50.0),(11,"Lunedi","12:00:00","13:30:00","Prenotabile",50.0),(11,"Lunedi","13:30:00","15:00:00","Prenotabile",50.0),(11,"Lunedi","15:00:00","16:30:00","Lezione",null),(11,"Lunedi","16:30:00","19:00:00","Lezione",null),(11,"Lunedi","19:00:00","20:30:00","Lezione",null),(11,"Lunedi","20:30:00","22:00:00","Prenotabile",50.0),(11,"Lunedi","22:00:00","23:30:00","Prenotabile",50.0),
		(11,"Martedi","09:00:00","10:30:00","Prenotabile",50.0),(11,"Martedi","10:30:00","12:00:00","Prenotabile",50.0),(11,"Martedi","12:00:00","13:30:00","Prenotabile",50.0),(11,"Martedi","13:30:00","15:00:00","Prenotabile",50.0),(11,"Martedi","15:00:00","16:30:00","Lezione",null),(11,"Martedi","16:30:00","19:00:00","Lezione",null),(11,"Martedi","19:00:00","20:30:00","Lezione",null),(11,"Martedi","20:30:00","22:00:00","Prenotabile",50.0),(11,"Martedi","22:00:00","23:30:00","Prenotabile",50.0),
		(11,"Mercoledi","09:00:00","10:30:00","Prenotabile",45.0),(11,"Mercoledi","10:30:00","12:00:00","Prenotabile",45.0),(11,"Mercoledi","12:00:00","13:30:00","Prenotabile",45.0),(11,"Mercoledi","13:30:00","15:00:00","Prenotabile",45.0),(11,"Mercoledi","15:00:00","16:30:00","Lezione",null),(11,"Mercoledi","16:30:00","19:00:00","Lezione",null),(11,"Mercoledi","19:00:00","20:30:00","Lezione",null),(11,"Mercoledi","20:30:00","22:00:00","Prenotabile",45.0),(11,"Mercoledi","22:00:00","23:30:00","Prenotabile",45.0),
		(11,"Giovedi","09:00:00","10:30:00","Prenotabile",50.0),(11,"Giovedi","10:30:00","12:00:00","Prenotabile",50.0),(11,"Giovedi","12:00:00","13:30:00","Prenotabile",50.0),(11,"Giovedi","13:30:00","15:00:00","Prenotabile",50.0),(11,"Giovedi","15:00:00","16:30:00","Lezione",null),(11,"Giovedi","16:30:00","19:00:00","Lezione",null),(11,"Giovedi","19:00:00","20:30:00","Lezione",null),(11,"Giovedi","20:30:00","22:00:00","Prenotabile",50.0),(11,"Giovedi","22:00:00","23:30:00","Prenotabile",50.0),
		(11,"Venerdi","09:00:00","10:30:00","Prenotabile",55.00000000000001),(11,"Venerdi","10:30:00","12:00:00","Prenotabile",55.00000000000001),(11,"Venerdi","12:00:00","13:30:00","Prenotabile",55.00000000000001),(11,"Venerdi","13:30:00","15:00:00","Prenotabile",55.00000000000001),(11,"Venerdi","15:00:00","16:30:00","Lezione",null),(11,"Venerdi","16:30:00","19:00:00","Lezione",null),(11,"Venerdi","19:00:00","20:30:00","Lezione",null),(11,"Venerdi","20:30:00","22:00:00","Prenotabile",55.00000000000001),(11,"Venerdi","22:00:00","23:30:00","Prenotabile",55.00000000000001),
		(11,"Sabato","09:00:00","10:30:00","Prenotabile",67.5),(11,"Sabato","10:30:00","12:00:00","Prenotabile",67.5),(11,"Sabato","12:00:00","13:30:00","Prenotabile",67.5),(11,"Sabato","13:30:00","15:00:00","Prenotabile",67.5),(11,"Sabato","15:00:00","16:30:00","Prenotabile",76.95),(11,"Sabato","16:30:00","19:00:00","Prenotabile",74.25),(11,"Sabato","19:00:00","20:30:00","Prenotabile",74.25),(11,"Sabato","20:30:00","22:00:00","Prenotabile",67.5),(11,"Sabato","22:00:00","23:30:00","Prenotabile",67.5),
		(11,"Domenica","09:00:00","10:30:00","Prenotabile",60.0),(11,"Domenica","10:30:00","12:00:00","Prenotabile",60.0),(11,"Domenica","12:00:00","13:30:00","Prenotabile",60.0),(11,"Domenica","13:30:00","15:00:00","Prenotabile",60.0),(11,"Domenica","15:00:00","16:30:00","Prenotabile",68.39999999999999),(11,"Domenica","16:30:00","19:00:00","Prenotabile",66.0),(11,"Domenica","19:00:00","20:30:00","Prenotabile",66.0),(11,"Domenica","20:30:00","22:00:00","Prenotabile",60.0),(11,"Domenica","22:00:00","23:30:00","Prenotabile",60.0);


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
values (1,"Lunedi","16:30:00","2025-02-03","Calcetto",30.95,"RRRRRRRRRRRRRRRR","AAAAAAAAAAAAAAAA",null,"EEEEEEEEEEEEEEEE");

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
values ("2025-10-05","Giganti","un elefante",2,1.0,1,"Tennis_singolo",null);

-- Inserimento nella tabella squadra --
insert into squadra(Nome,CodiceSquadra,Tipo,Componenti1,Componenti2,Componenti3,Componenti4,Componenti5) 
values ("I belli",1,"Tennis_singolo","AAAAAAAAAAAAAAAA",null,null,null,null),
	   ("I brutti",2,"Tennis_singolo", "DDDDDDDDDDDDDDDD",null,null,null,null),
       ("I mezzi",3,"Tennis_singolo", "EEEEEEEEEEEEEEEE",null,null,null,null);
       
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