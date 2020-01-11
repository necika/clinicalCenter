const Registracija = { template : '<registracija></registracija>'}
const Prijava = { template : '<prijava></prijava>'}
const Sala = { template : '<sala></sala>'}
const DodavanjeSale = { template : '<novaSala></novaSala>'}
const IzmenaSale = { template : '<izmenaSala></izmenaSala>'}
const Klinika = { template : '<dodavanjeKlinike></dodavanjeKlinike>'}
const DijagnozeDodavanje = { template : '<dodavanjeSifarnika></dodavanjeSifarnika>'}
const Klinike = {template: '<klinike></klinike>'}
const Pocetna = {template: '<homepage></homepage>'}
const Lekari = {template: '<lekari></lekari>'}
const PromenaLozinkeLekar = {template: '<lekarLozinka></lekarLozinka>'}
const LekarProfil = {template: '<profilLekar></profilLekar>'}
const LekarZakazaniTermini = {template: '<zakazanoLekar></zakazanoLekar>'}
const LekarStranaZaPregled = {template: '<pregledLekar></pregledLekar>'}
const LekarOdmor = {template: '<odmorLekar></odmorLekar>'}
const DodavanjeLekar = {template: '<dodavanjeLekar></dodavanjeLekar>'}
const IzmenaLekar = {template: '<izmenaLekar></izmenaLekar>'}
const Profil = {template: '<profil></profil>'}
const AdministratorKlinike = {template: '<administratorKlinike></administratorKlinike>'}
const ProfilAdministratorKlinike = {template: '<profilAdministratorKlinike></profilAdministratorKlinike>'}
const DodavanjeAdministratorKlinike = {template : '<dodavanjeAdministratorKlinike></dodavanjeAdministratorKlinike>'}
const IzmenaAdministratoraKlinike = {template : '<izmenaAdministratorKlinike></izmenaAdministratorKlinike>'}
const LozinkaAdministratoraKlinike = {template : '<lozinkaAdministratorKlinike></lozinkaAdministratorKlinike>'}
const AdministratorKlinickogCentra = {template : '<administratorKlinickogCentra></administratorKlinickogCentra>'}
const TipoviPregleda = {template: '<tip></tip>'}
const DodavanjeTipaPregleda = {template: '<noviTip></noviTip>'}
const IzmenaTipaPregleda = {template: '<izmenaTip></izmenaTip>'}
const DodavanjePregled = {template: '<dodavanjePregled></dodavanjePregled>'}
const PregledPacijenata = {template: '<pregledPacijenata></pregledPacijenata>'}
const PretragaKlinika = {template: '<pretragaKlinika></pretragaKlinika>'}
const IzmenaPacijenta = {template: '<izmenaPacijenta></izmenaPacijenta>'}
const PacijentProfil = {template: '<profilPacijenta></profilPacijenta>'}
const MedicinskaSestra = {template: '<medicinskaSestra></medicinskaSestra>'}
const PromenaLozinkeMedicinskaSestra = {template: '<medicinskaSestraLozinka></medicinskaSestraLozinka>'}
const IzmenaMedicinskaSestra = {template: '<izmenaSestra></izmenaSestra>'}
const ProfilMedicinskeSestre = {template: '<profilMedicinskeSestre></profilMedicinskeSestre>'}
const PromenaLozinkeACC = {template: '<promenaLozinkeACC></promenaLozinkeACC>'}
const KalendarOdmora = {template: '<kalendarOdmora></kalendarOdmora>'}
const KalendarRadaMedicinskaSestra = {template: '<kalendarRadaMedicinskaSestra></kalendarRadaMedicinskaSestra>'}
const KalendarRadaLekar = {template: '<kalendarRadaLekar></kalendarRadaLekar>'}
const ProfilKlinike = {template: '<profilKlinike></profilKlinike>'}
const OceniKliniku = {template: '<oceniKliniku></oceniKliniku>'}
const IzmeniKliniku = {template: '<izmeniKliniku></izmeniKliniku>'}
const OceniLekara = {template: '<oceniLekara></oceniLekara>'}
const DodavanjeMedicinskeSestre = {template: '<dodavanjeMedicinskaSestra></dodavanjeMedicinskaSestra>'}
const Pregledi = {template: '<pregledi></pregledi>'}
const ZahteviZaRegistraciju = {template: '<zahteviZaRegistraciju></zahteviZaRegistraciju>'}
const PotvrdaPrekoEmaila = {template: '<potvrdaPrekoEmaila></potvrdaPrekoEmaila>'}
const UnapredDefinisaniPregledi = {template: '<unapredDefinisaniPregledi></unapredDefinisaniPregledi>'}
const ProfilSestreIliDoktora = {template: '<profilUlogovanogDoktoraIliSestre></profilUlogovanogDoktoraIliSestre>'}
const OveraRecepata = {template: '<overaRecepata></overaRecepata>'}
const ZahteviZaNovePreglede = {template: '<zahteviZaNovePreglede></zahteviZaNovePreglede>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        { path: '/', component: Pocetna},
        { path: '/registracija', component: Registracija},
        { path: '/prijava', component: Prijava},
        { path: '/sala', component: Sala},
        { path: '/sala/dodaj', component: DodavanjeSale},
        { path: '/sala/izmeni', component: IzmenaSale},
        { path: '/klinika/dodaj', component : Klinika },
        { path: '/sifarnik/dodaj', component : DijagnozeDodavanje },
        { path: '/klinika', component: Klinike},
        { path: '/lekar', component: Lekari},
        { path: '/lekar/lozinka', component: PromenaLozinkeLekar},
        { path: '/lekar/dodaj', component: DodavanjeLekar},
        { path: '/lekar/izmeni', component: IzmenaLekar},
        { path: '/lekar/profil', component: LekarProfil},
        { path: '/lekar/zakazano', component: LekarZakazaniTermini},
        { path: '/lekar/pregled', component: LekarStranaZaPregled},
        { path: '/lekar/odmor', component: LekarOdmor},
        { path: '/profil', component: Profil},
        { path: '/administratorKlinike', component : AdministratorKlinike},
        { path: '/administratorKlinike/profil', component : ProfilAdministratorKlinike},
        { path: '/administratorKlinike/izmenaLozinke', component: LozinkaAdministratoraKlinike},
        { path: '/administratorKlinike/dodaj', component : DodavanjeAdministratorKlinike},
        { path: '/administratorKlinike/izmeni', component : IzmenaAdministratoraKlinike},
        { path: '/administratorKlinickogCentra/dodaj', component : AdministratorKlinickogCentra},
        { path: '/administratorKlinickogCentra/izmenaLozinke', component : PromenaLozinkeACC},
        { path: '/tip', component: TipoviPregleda},
        { path: '/tip/dodaj', component: DodavanjeTipaPregleda},
        { path: '/tip/izmeni', component: IzmenaTipaPregleda},
        { path: '/pregled/dodaj', component: DodavanjePregled},
        { path: '/pacijent/pregled', component: PregledPacijenata},
        { path: '/klinika/pretraga', component: PretragaKlinika},
        { path: '/pacijent/izmeni', component: IzmenaPacijenta},
        { path: '/pacijent/profil', component: PacijentProfil},
        { path: '/medicinskaSestra', component: MedicinskaSestra},
        { path: '/medicinskaSestra/lozinka', component: PromenaLozinkeMedicinskaSestra},
        { path: '/medicinskaSestra/izmeni', component: IzmenaMedicinskaSestra},
        { path: '/medicinskaSestra/profil', component: ProfilMedicinskeSestre},
        { path: '/medicinskaSestra/kalendarOdmora', component: KalendarOdmora},
        { path: '/medicinskaSestra/kalendarRada', component: KalendarRadaMedicinskaSestra},
        { path: '/lekar/kalendarRada', component: KalendarRadaLekar},
        { path: '/klinika/profil', component: ProfilKlinike},
        { path: '/klinika/oceni', component: OceniKliniku},
        { path: '/lekar/oceni', component: OceniLekara},
        { path: '/medicinskaSestra/dodaj', component: DodavanjeMedicinskeSestre},
        { path: '/klinika/izmeni', component: IzmeniKliniku},
        { path: '/pregledi', component: Pregledi},
        { path: '/administratorKlinickogCentra/zahteviZaRegistraciju', component: ZahteviZaRegistraciju},
        { path: '/pacijent/potvrdaPrekoEmaila/:email', component: PotvrdaPrekoEmaila},
        { path: '/pacijent/unapredDefinisaniPregledi', component: UnapredDefinisaniPregledi},
        { path: '/profilSestreIliDoktora', component: ProfilSestreIliDoktora},
        { path: '/medicinskaSestra/overaRecepata', component: OveraRecepata},
        { path: '/adminKlinike/zahteviZaNovePreglede', component: ZahteviZaNovePreglede}     
           
    ]
});

var app = new Vue({
    router,
    el: '#app',
    data: {
        korisnik : null,
        ulogovan : false
    },
    mounted: function() {
        if (localStorage.getItem("auth") === null) {
            this.ulogovan = false;
        } else {
            this.ulogovan = true;
        }
    },
    methods: {
        odjaviSe: function () {
            localStorage.clear();
            router.push('/prijava');
            this.ulogovan = false;
        }
    }
});
