insert into authority(uloga) values ('ROLE_USER');
insert into authority(uloga) values ('ROLE_ADMIN');
insert into authority(uloga) values ('ROLE_PATIENT');
insert into authority(uloga) values ('ROLE_DOCTOR');
insert into authority(uloga) values ('ROLE_NURSE');
insert into authority(uloga) values ('ROLE_CLINIC_ADMIN');

insert into zdravstveni_karton(osnovni_podaci) values ('Umires');
insert into zdravstveni_karton(osnovni_podaci) values ('Lose skroz');

--Klinike
insert into klinika(adresa, naziv, ocena, opis) values ('Svetogorska 13', 'Naša mala klinika', '1', 'blabla');
insert into klinika(adresa, naziv, ocena, opis) values ('Bulevar Oslobođenja 105', 'Klinika', '3', 'nestonesto');
insert into klinika(adresa, naziv, ocena, opis) values ('Novosadskog sajma 44', 'Bolnica', '2', 'asdasd');
insert into klinika(adresa, naziv, ocena, opis) values ('Dositeja Obradovića 3', 'Psihijatrija', '5', 'Dx');
insert into klinika(adresa, naziv, ocena, opis) values ('Janka Čmelika 76', 'Ambulanta', '4', 'qweqwe');

--Sale
insert into sala(naziv, broj, klinika_id) values ('A1', 100, 2);
insert into sala(naziv, broj, klinika_id) values ('A2', 112, 2);
insert into sala(naziv, broj, klinika_id) values ('B1', 334, 3);
insert into sala(naziv, broj, klinika_id) values ('B2', 567, 3);
insert into sala(naziv, broj, klinika_id) values ('C4', 1988, 1);

--Tipovi pregleda
insert into tip_pregleda(naziv, opis) values ('Ocni pregled', 'Pregled ociju');
insert into tip_pregleda(naziv, opis) values ('Pregled mozga', 'Lobotomija academia');


insert into izvestajopregledu(podaci,zdravstveni_karton_id) values ('sve je okej','1');

--Kombinacije u sifarniku
insert into sifarnik(lekovi, dijagnoze) values ('Ne postoji lek za ovo', 'Tumor mozga');
insert into sifarnik(lekovi, dijagnoze) values ('Brufen 500mg', 'Upala grla');
insert into sifarnik(lekovi, dijagnoze) values ('Samo caja', 'Prehlada');
insert into sifarnik(lekovi, dijagnoze) values ('Bensedin 300mg', 'Upala pluca');

--Pacijenti lozinka:patient
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Malbaška 1', '111-111', 'Bosna i Hercegovina', 'petko@gmail.com', false, 'Teslić', 'Petko', '100', '$2a$10$DTFkinaFRArwV2Ap449wE.MneiyaXQeuiLEZBjdcOflcfX8Uo7rzq', 'Petković', 'petko@gmail.com','prihvacen');
insert into pacijent(jedinstveni_br_osiguranika,zdravstveni_karton_id) values ('100','1');
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Svetosavska 34', '123-123', 'Srbija', 'mmilica@gmail.com', false, 'Subotica', 'Milica', '101', '$2a$10$DTFkinaFRArwV2Ap449wE.MneiyaXQeuiLEZBjdcOflcfX8Uo7rzq', 'Milićević', 'mmilica@gmail.com','obrada');
insert into pacijent(jedinstveni_br_osiguranika,zdravstveni_karton_id) values ('101','2');
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Vojvode Stepe 19', '321-321', 'Srbija', 'milanamilanovic@yahoo.com', false, 'Jagodina', 'Milana', '102', '$2a$10$DTFkinaFRArwV2Ap449wE.MneiyaXQeuiLEZBjdcOflcfX8Uo7rzq', 'Milanović', 'milanamilanovic@yahoo.com','obrada');
insert into pacijent(jedinstveni_br_osiguranika) values ('102');
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Arčibalda Rajsa 57', '587-988', 'Srbija', 'ognjeno@hotmail.com', false, 'Beograd', 'Ognjen', '103', '$2a$10$DTFkinaFRArwV2Ap449wE.MneiyaXQeuiLEZBjdcOflcfX8Uo7rzq', 'Ognjenović', 'ognjeno@hotmail.com','obrada');
insert into pacijent(jedinstveni_br_osiguranika) values ('103');

--Medicinske sestre lozinka:nurse
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Stražilovska 2', '222-222', 'Srbija', 'nurse@nurse', true, 'Kula', 'Dejan', '200', '$2a$10$5GpCWy48zU10LHQCGgmDfeh0GHl0pfehDwAZwCxhydtf0Dg0Hp6h6', 'Dejanović', 'nurse@nurse','prihvacen');
insert  into medicinska_sestra(datum_odlaska_na_godisnji, datum_povratka_sa_godisnjeg, jedinstveni_br_osiguranika, kraj_radnog_vremena, odobren_godisnji, pocetak_radnog_vremena, klinika_id) values (null, null, '200', '17:4', false, '10:0', 2);


--Doktori lozinka:doctor
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Jablanova 3', '333-444', 'Srbija', 'kcentar4@gmail.com', true, 'Sremska Kamenica', 'Borislav', '300', '$2a$10$NHtNfymO16b1ij8XSlRf7O/v7sQlmCVp..u4tavSNjlBx/gIEItB2', 'Borisavljević', 'kcentar4@gmail.com','prihvacen');
insert into lekar(jedinstveni_broj_osiguranika, kraj_radnog_vremena, ocena, pocetak_radnog_vremena, klinika_id) values ('300', '18:35', 5, '11:55', 3);
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Risitovska 6', '666-666', 'Srbija', 'mirkom@yahoo.com', true, 'Novi Sad', 'Mirko', '301', '$2a$10$NHtNfymO16b1ij8XSlRf7O/v7sQlmCVp..u4tavSNjlBx/gIEItB2', 'Mirkovic', 'mirkom@yahoo.com','prihvacen');
insert into lekar(jedinstveni_broj_osiguranika, kraj_radnog_vremena, ocena, pocetak_radnog_vremena, klinika_id) values ('301', '17:35', 3, '10:55', 1);
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Jurija Gagarina 13', '871-567', 'Srbija', 'milenamilovanovic@gmail.com', true, 'Beograd', 'Milena', '302', '$2a$10$NHtNfymO16b1ij8XSlRf7O/v7sQlmCVp..u4tavSNjlBx/gIEItB2', 'Milovanović', 'milenamilovanovic@gmail.com','prihvacen');
insert into lekar(jedinstveni_broj_osiguranika, kraj_radnog_vremena, ocena, pocetak_radnog_vremena, klinika_id) values ('302', '6:35', 5, '13:55', 1);
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Mihajla Pupina 41', '871-567', 'Srbija', 'zeljko@gmail.com', true, 'Nova Pazova', 'Željko', '303', '$2a$10$NHtNfymO16b1ij8XSlRf7O/v7sQlmCVp..u4tavSNjlBx/gIEItB2', 'Željković', 'zeljko@gmail.com','prihvacen');
insert into lekar(jedinstveni_broj_osiguranika, kraj_radnog_vremena, ocena, pocetak_radnog_vremena, klinika_id) values ('303', '8:35', 5, '15:55', 2);

--Admini klinike lozinka:admin
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Vojvođanskih Brigada 4', '444-444', 'Srbija', 'vukan@gmail.com', true, 'Kovilj', 'Vukan', '400', '$2a$10$q2P.TXahSg5HhCnrs57tQuZv7alxDK//a7qCjG1RA6Tvr419u9btC', 'Vukanović', 'vukan@gmail.com','prihvacen');
insert into administrator_klinike(jedinstveni_br_osiguranika, klinika_id) values ('400','2');
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Vihorska 15', '777-777', 'Srbija', 'janja@gmail.com', true, 'Kikinda', 'Janja', '401', '$2a$10$q2P.TXahSg5HhCnrs57tQuZv7alxDK//a7qCjG1RA6Tvr419u9btC', 'Janjatović', 'janja@gmail.com','prihvacen');
insert into administrator_klinike(jedinstveni_br_osiguranika, klinika_id) values ('401','1');

--Admini klinickog centra lozinka:aaa
insert into users(adresa, br_telefona, drzava, email, enabled, grad, ime, jedinstveni_br_osiguranika, password, prezime, username,odobrenaRegistracija) values ('Maksima Gorkog 23', '469-469', 'Srbija', 'adminjovan@gmail.com', true, 'Novi Sad', 'Jovan', '500', '$2y$10$4LXzEAvJhHh9GsvXrMQk0OH1S7U.Fn.TrkvdbgzfHNz62Y0xAXzqi', 'Jovanovic', 'adminjovan@gmail.com','prihvacen');
insert into administrator_klinickog_centra(promenjena_lozinka, jedinstveni_br_osiguranika) values (false, '500');

--Zahtevi za odmor
insert into zahtev_za_odmor (admin_id, pocetak, kraj, posiljalac, tip_odsustva, email) values (1, '2019-12-20 00:00:00', '2019-12-27 00:00:00', 'Borislav Borisavljevic', 'Odsustvo', 'kcentar4@gmail.com');
insert into zahtev_za_odmor (admin_id, pocetak, kraj, posiljalac, tip_odsustva, email) values (1, '2019-10-10 00:00:00', '2019-11-17 00:00:00', 'Milena Milovanović', 'Godišnji odmor', 'milenamilovanovic@gmail.com');

--Pregledi
insert into pregled(cena, datum, rezervisan, trajanje, pocetak, zavrsetak, lekar_id,medicinska_sestra_id,pacijent_id, sala_id, tip_pregleda_id) values ('100', '2019-12-19', true, '1h0min', '10:0', '11:0', '1','1','1', '1', '1');
insert into pregled(cena, datum, rezervisan, trajanje, pocetak, zavrsetak, lekar_id,medicinska_sestra_id,pacijent_id, sala_id, tip_pregleda_id) values ('322', '2019-12-26', false, '3h0min', '10:30', '13:30', '3','1',null, '2', '2');

insert into user_authority(user_id, authority_id) values ('1', '3');
insert into user_authority(user_id, authority_id) values ('2', '3');
insert into user_authority(user_id, authority_id) values ('3', '3');
insert into user_authority(user_id, authority_id) values ('4', '3');
insert into user_authority(user_id, authority_id) values ('5', '5');
insert into user_authority(user_id, authority_id) values ('6', '4');
insert into user_authority(user_id, authority_id) values ('7', '4');
insert into user_authority(user_id, authority_id) values ('8', '4');
insert into user_authority(user_id, authority_id) values ('9', '4');
insert into user_authority(user_id, authority_id) values ('10', '2');
insert into user_authority(user_id, authority_id) values ('11', '2');
insert into user_authority(user_id, authority_id) values ('12', '2');

insert into user_doctor(user_id, doctor_id) values ('300', '1');
insert into user_doctor(user_id, doctor_id) values ('301', '2');
insert into user_doctor(user_id, doctor_id) values ('302', '3');
insert into user_doctor(user_id, doctor_id) values ('303', '4');

insert into user_nurse(user_id, medicinska_sestra_id) values ('200', '1');


insert into user_patient(user_id, patient_id) values ('100', '1');
insert into user_patient(user_id, patient_id) values ('101', '2');
insert into user_patient(user_id, patient_id) values ('102', '3');
insert into user_patient(user_id, patient_id) values ('103', '4');

insert into recept(naziv_recepta, overen, izvestajopregledu_id,sestra_id,sifarnik_id) values ('Tumor mozga', 'false', '1','1','1');
insert into recept(naziv_recepta, overen, izvestajopregledu_id,sestra_id,sifarnik_id) values ('Upala grla', 'false', '1','1','2');
insert into recept(naziv_recepta, overen, izvestajopregledu_id,sestra_id,sifarnik_id) values ('Prehlada', 'false', '1','1','3');