Vue.component('administratorKlinike',{
    data: function() {
        return{
            admini : null
        }
    },
    template : `
    <div class="container">
        <div v-if="admini != null" class="table-wrapper">
            <table id="akTable" class="fl-table">
                <thead>
                    <tr>
                        <th>Ime</th>
                        <th>Prezime</th>
                        <th>Email</th>
                        <th>Kontakt telefon</th>
                        <th colspan="3">Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="a in admini">
                        <td>{{a.ime}}</td>
                        <td>{{a.prezime}}</td>
                        <td>{{a.email}}</td>
                        <td>{{a.brTelefona}}</td>
                        <td><router-link :to="{path : '/administratorKlinike/profil', query : {admin : a.id}}" class="btn btn-dark">Profil</router-link></td>
                        <td><button v-on:click.prevent="ukloni(a)" class="btn btn-dark">Ukloni</button></td>
                    </tr>
                </tbody>
            </table>
            <br>
            <router-link to="/administratorKlinike/dodaj">
                <a href="#" class="btn btn-dark btn-sm text-center" role="button">
                    Dodavanje novog administratora klinike
                </a>
            </router-link>
            <br>
        </div>
    </div>
    `,
    mounted: function(){
        var adminApp = this;
        let jwt = localStorage.getItem('auth');

        axios.get('/isa/adminKlinike', {headers: {Authorization: jwt}})
        .then(function(response) {
            adminApp.admini = response.data;
        })
        .catch(function(error) {
            alert(error.response.data);
        })
    },
    methods: {
        ukloni: function(a){
            
        }
    }
});

Vue.component('profilAdministratorKlinike',{
    data: function() {
        return{
            id : undefined,
            email : undefined,
            lozinka : undefined,
            ime : undefined,
            prezime : undefined, 
            adresa : undefined,
            grad : undefined,
            drzava : undefined,
            brTelefona : undefined,
            jedinstveniBrOsiguranika : undefined,
            zahteviZaOdmor: null,
            obrazlozenje : undefined
        }
    },
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/adminKlinike/' + this.$route.query.admin, {headers: {Authorization: jwt}})
            .then(response =>{
                this.id = response.data.id;
                this.email = response.data.email;
                this.lozinka = response.data.lozinka;
                this.ime = response.data.ime;
                this.prezime = response.data.prezime;
                this.adresa = response.data.adresa;
                this.grad = response.data.grad;
                this.drzava = response.data.drzava;
                this.brTelefona = response.data.brTelefona;
                this.jedinstveniBrOsiguranika = response.data.jedinstveniBrOsiguranika;
            })
            .catch(error => {alert(error.response.data);})
            
        axios.get('/isa/adminKlinike/' + this.$route.query.admin + '/odmor', {headers: {Authorization: jwt}})
            .then(response =>{
                this.zahteviZaOdmor = response.data;
            })
            .catch(error => {alert(error.response.data);})
    },
    template : `
    <div class="container">
        <div class="table-wrapper">
            <table id="akTable" class="fl-table">
                <thead>
                    <tr>
                        <th colspan="2">Lični podaci</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td style="font-weight:bold">Ime</td>
                        <td>{{this.ime}}</td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold">Prezime</td>
                        <td>{{this.prezime}}</td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold">Email</td>
                        <td>{{this.email}}</td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold">Adresa</td>
                        <td>{{this.adresa}}, {{this.grad}}, {{this.drzava}}</td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold">Kontakt telefon</td>
                        <td>{{this.brTelefona}}</td>
                    </tr>
                </tbody>
            </table>
            <br>
            <h2 class="btn btn-success" data-toggle="collapse" data-target="#requestsForm" aria-expanded="false" role="button">Zahtevi za odmor</h2>
                <div id="requestsForm" class="collapse">
                    <table id="zahteviTable" class="fl-table">
                        <thead>
                            <tr>
                                <th>Pošiljalac</th>
                                <th>Adresa Pošiljaoca</th>
                                <th>Period</th>
                                <th>Tip</th>
                                <th colspan="2">Opcije</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="zom in this.zahteviZaOdmor">
                                <td>{{zom.posiljalac}}</td>
                                <td>{{zom.email}}</td>
                                <td>{{datePrint(zom.datumOdlaskaNaGodisnji)}} do {{datePrint(zom.datumPovratkaSaGodisnjeg)}}</td>
                                <td>{{zom.tip}}</td>
                                <td><button v-on:click.prevent="prihvati(zom)" class="btn btn-dark">Prihvati</button></td>
                                <td><button class="btn btn-dark" data-toggle="modal" data-target="#exampleModal">Odbij</button></td>
                                <!-- Modal -->
                                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Obrazloženje</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <input type="text" class="form-control" placeholder="Unesite obrazloženje za odbijanje zahteva" v-model="obrazlozenje">
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Zatvori</button>
                                            <button type="button" v-on:click.prevent="odbij(zom)" class="btn btn-primary" data-dismiss="modal">Potvrdi</button>
                                        </div>
                                        </div>
                                    </div>
                                </div>
                            </tr>
                        </tbody>
                    </table>
                </div>
            <br>
            <router-link :to="{path : '/administratorKlinike/izmenaLozinke', query : {admin : this.id}}" class="btn btn-dark">Izmeni lozinku</router-link>
            <br>
            <router-link :to="{path : '/administratorKlinike/izmeni', query : {admin : this.id}}" class="btn btn-dark">Izmeni podatke</router-link>
            <br>
        </div>
    </div>
    `,
    methods: {
        prihvati: function(zom) {
            let subject = "Zahtev za godišnji odmor-odsustvo";
            let txt = "Vaš zahtev je prihvaćen";
            let flag = "TRUE";

            let jwt = localStorage.getItem('auth');
            axios.put('/isa/adminKlinike/odmor/' + subject + '!' + txt + '!' + flag + '!zz', zom, {headers: {Authorization: jwt}})
                .then(function(response) {
                    //deleteTableRow(r);
                })
                .catch(function (error) {
                    alert("Greška.");
                })
        },
        odbij: function(zom) {
            let subject = "Zahtev za godišnji odmor-odsustvo";
            let txt = "Vaš zahtev je odbijen, obrazloženje: " + this.obrazlozenje;
            let flag = "FALSE";

            let jwt = localStorage.getItem('auth');
            axios.put('/isa/adminKlinike/odmor/' + subject + '!' + txt + '!' + flag + '!zz', zom, {headers: {Authorization: jwt}})
                .then(function(response) {
                    //deleteTableRow(r);
                })
                .catch(function (error) {
                    alert("Greška.");
                })
        },
        datePrint: function(someDate) {
            let varDate = new Date(someDate);
            return [varDate.getDate(), varDate.getMonth() + 1, varDate.getFullYear()].join("-");
        },
        deleteTableRow: function(r) {
            var i = r.parentNode.parentNode.rowIndex;
            document.getElementById("zahteviTable").deleteRow(i);
        }
    }
});

Vue.component('dodavanjeAdministratorKlinike',{
    data: function() {
        return{
            email : undefined,
            lozinka : undefined,
            ime : undefined,
            prezime : undefined, 
            adresa : undefined,
            grad : undefined,
            drzava : undefined,
            brTelefona : undefined,
            jedinstveniBrojOsiguranika : undefined,
            klinike : null,
            klinika: null
        }
    },
    mounted: function () {
        varL = this
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/klinika/all', {headers: {Authorization: jwt}}).then(function (response) {
            varL.klinike = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })
    },
    template : `
    <div class="container">
    <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="administratorKlinike" accept-charset="UTF-8">
        <h2>Dodavanje administratora klinike</h2>
        <div>
            <label class="labels" for="inputKlinika"><strong>Klinika</strong></label>
            <select v-model="klinika" id="inputKlinika" required>
                <option v-for="k in klinike" v-bind:value="k.id">
                    {{k.naziv}}
                </option>
            </select>
        </div>
        <div class="form-group">
            <label class="labels" for="inputEmail"><strong>Email</strong></label>
            <input type="email" required class="form-control" id="inputEmail" placeholder="Unesite email" v-model="email" >
            
        </div>
        <div class="form-group">
            <label class="labels" for="inputLozinka"><strong>Lozinka</strong></label>
            <input type="password" required class="form-control" id="inputLozinka" placeholder="Unesite lozinku" v-model="lozinka">
            
        </div>
        <div class="form-group">
            <label class="labels" for="inputIme"><strong>Ime</strong></label>
            <input type="text" required class="form-control" id="inputIme" placeholder="Unesite ime" v-model="ime">
            
        </div>
        <div class="form-group">
            <label class="labels" for="inputPrezime"><strong>Prezime</strong></label>
            <input type="text" required class="form-control" id="inputPrezime" placeholder="Unesite prezime" v-model="prezime">
            
        </div>
        <div class="form-group">
            <label class="labels" for="inputAdresa"><strong>Adresa</strong></label>
            <input type="text" required class="form-control" id="inputAdresa" placeholder="Unesite adresu" v-model="adresa">
           
        </div>
        <div class="form-group">
            <label class="labels" for="inputBrTelefona"><strong>Broj telefona</strong></label>
            <input type="tel" required class="form-control" id="inputBrTelefona" placeholder="Unesite broj telefona" v-model="brTelefona">
            
        </div>
        <div class="form-group">
            <label class="labels" for="inputGrad"><strong>Grad</strong></label>
            <input type="text" required class="form-control" id="inputGrad" placeholder="Unesite grad" v-model="grad">
        </div>
        <div class="form-group">
            <label class="labels" for="inputDrzava"><strong>Država</strong></label>
            <input type="text" required class="form-control" id="inputDrzava" placeholder="Unesite drzavu" v-model="drzava">
        </div>
        <div class="form-group">
            <label class="labels" for="inputJedinstveniBroj"><strong>Jedinstveni broj osiguranika</strong></label>
            <input type="number" required class="form-control" id="inputJedinstveniBroj" placeholder="Unesite JBO" v-model="jedinstveniBrojOsiguranika">
        </div>
        <button type="submit" class="btn btn-dark">Dodaj</button>
        <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
    </form>	
    </div>
    `,
    methods: {
        administratorKlinike: function() {

            var administratorKlinike = {
                "email" : this.email,
                "lozinka" : this.lozinka,
                "ime" : this.ime,
                "prezime" : this.prezime,
                "brTelefona" : this.brTelefona,
                "grad" : this.grad,
                "drzava" : this.drzava, 
                "adresa" : this.adresa,
                "jedinstveniBrOsiguranika" : this.jedinstveniBrojOsiguranika
            }
                var ap = this;
                let jwt = localStorage.getItem('auth');

                axios.post('/isa/adminKlinike/dodaj/' + this.klinika, administratorKlinike, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.href = "#/administratorKlinike/dodaj"
                        alert("Uspešno ste dodali administratora klinike.");
                        ap.email = undefined;
                        ap.lozinka = undefined;
                        ap.ime = undefined;
                        ap.prezime = undefined;
                        ap.brTelefona = undefined;
                        ap.grad = undefined;
                        ap.drzava = undefined;
                        ap.adresa = undefined;
                        ap.jedinstveniBrOsiguranika = undefined;
                    })
                    .catch(function (error) {
                        alert("Greška.");
                    })
            
        },
        odustani: function(){
            window.location.hash = "#/administratorKlinike";
        }
    }
});

Vue.component('izmenaAdministratorKlinike',{
    data: function() {
        return{
            id : undefined,
            email : undefined,
            lozinka : undefined,
            ime : undefined,
            prezime : undefined, 
            adresa : undefined,
            grad : undefined,
            drzava : undefined,
            brTelefona : undefined,
            jedinstveniBrOsiguranika : undefined,
        }
    },
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/adminKlinike/' + this.$route.query.admin, {headers: {Authorization: jwt}})
            .then(response =>{
                this.id = response.data.id;
                this.email = response.data.email;
                this.lozinka = response.data.lozinka;
                this.ime = response.data.ime;
                this.prezime = response.data.prezime;
                this.adresa = response.data.adresa;
                this.grad = response.data.grad;
                this.drzava = response.data.drzava;
                this.brTelefona = response.data.brTelefona;
                this.jedinstveniBrOsiguranika = response.data.jedinstveniBrOsiguranika;
            })
            .catch(error => {alert(error.response.data);})
    },
    template : 
        `
    <div class="container">
        <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="izmeni" accept-charset="UTF-8">
            <h2>Izmena administratora klinike</h2>
            <div class="form-group">
                <label class="labels" for="inputIme"><strong>Ime</strong></label>
                <input type="text" required class="form-control" id="inputIme" placeholder="Unesite ime" v-model="ime">
                
            </div>
            <div class="form-group">
                <label class="labels" for="inputPrezime"><strong>Prezime</strong></label>
                <input type="text" required class="form-control" id="inputPrezime" placeholder="Unesite prezime" v-model="prezime">
                
            </div>
            <div class="form-group">
                <label class="labels" for="inputAdresa"><strong>Adresa</strong></label>
                <input type="text" required class="form-control" id="inputAdresa" placeholder="Unesite adresu" v-model="adresa">
            
            </div>
            <div class="form-group">
                <label class="labels" for="inputBrTelefona"><strong>Broj telefona</strong></label>
                <input type="tel" required class="form-control" id="inputBrTelefona" placeholder="Unesite broj telefona" v-model="brTelefona">
                
            </div>
            <div class="form-group">
                <label class="labels" for="inputGrad"><strong>Grad</strong></label>
                <input type="text" required class="form-control" id="inputGrad" placeholder="Unesite grad" v-model="grad">
            </div>
            <div class="form-group">
                <label class="labels" for="inputDrzava"><strong>Država</strong></label>
                <input type="text" required class="form-control" id="inputDrzava" placeholder="Unesite drzavu" v-model="drzava">
            </div>
            <button type="submit" class="btn btn-dark">Dodaj</button>
            <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
        </form>	
    </div>
        `,
    methods: {
        izmeni : function() {
            var administratorKlinike = {
                "id" : this.id,
                "email" : this.email,
                "lozinka" : this.lozinka,
                "ime" : this.ime,
                "prezime" : this.prezime,
                "brTelefona" : this.brTelefona,
                "grad" : this.grad,
                "drzava" : this.drzava, 
                "adresa" : this.adresa,
                "jedinstveniBrOsiguranika" : this.jedinstveniBrOsiguranika
            }
            let jwt = localStorage.getItem('auth');
            var param = administratorKlinike.id;
          
            axios.put('/isa/adminKlinike/update/' + this.$route.query.admin, administratorKlinike, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.hash = "#/administratorKlinike/profil?admin=" + param;
                    })
                    .catch(function(error) {
                        alert(error.response.data);
                    })
        },
        odustani: function(){
            window.location.hash = "#/administratorKlinike/profil?admin=" + this.$route.query.admin;
        }
    }
});

Vue.component('lozinkaAdministratorKlinike',{
    data: function() {
        return{
            id : undefined,
            stvarnaLozinka : undefined,
            novaLozinka : undefined,
            retypeLozinka : undefined,
            staraLozinka : undefined
        }
    },
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/adminKlinike/' + this.$route.query.admin, {headers: {Authorization: jwt}})
            .then(response =>{
                this.id = response.data.id;
                this.stvarnaLozinka = response.data.lozinka;
            })
            .catch(error => {alert(error.response.data);})
    },
    template : 
        `
    <div class="container">
        <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="izmeni" accept-charset="UTF-8">
            <h2>Izmena lozinke</h2>
            <div class="form-group">
                <label class="labels" for="inputStaraLozinka"><strong>Stara lozinka</strong></label>
                <input type="password" required class="form-control" id="inputStaraLozinka" placeholder="Unesite staru lozinku" v-model="staraLozinka">
            </div>
            <div class="form-group">
                <label class="labels" for="inputNovaLozinka"><strong>Nova lozinka</strong></label>
                <input type="password" required class="form-control" id="inputNovaLozinka" placeholder="Unesite novu lozinku" v-model="novaLozinka">
            </div>
            <div class="form-group">
                <label class="labels" for="inputRetypeLozinka"><strong>Provera lozinke</strong></label>
                <input type="password" required class="form-control" id="inputRetypeLozinka" placeholder="Ponovite novu lozinku" v-model="retypeLozinka">
            </div>
            <button type="submit" class="btn btn-dark">Dodaj</button>
            <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
        </form>	
    </div>
        `,
    methods: {
        izmeni : function() {
            var ok = true;
            var param = this.id;
            
            if (this.novaLozinka != this.retypeLozinka) {
                alert("Ponovo uneta lozinka se ne poklapa!");
                ok = false;
            }
            
            if (ok) {
                var passwordChanger = {
                    "oldPassword" : this.staraLozinka,
                    "newPassword" : this.novaLozinka
                };
                let jwt = localStorage.getItem('auth');
                axios.post('/isa/passwordChange', passwordChanger, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.hash = "#/administratorKlinike/profil?admin=" + param;
                    })
                    .catch(function(error) {
                        alert(error.response.data);
                    })
            }
        },
        odustani: function(){
            window.location.hash = "#/administratorKlinike/profil?admin=" + this.$route.query.admin;
        }
    }
});

Vue.component('zahteviZaNovePreglede', {
    data: function () {
        return {
            pregledi : null,
            pregled : null,
            sale : null,
            sala : null,
            idZahteva : undefined,
            cena : undefined,
            datuM : undefined
        }
    },
    template:
        `
        <div class="container">
            <div class="table-wrapper">
                <br>
                <h3>Dodaj novi pregled</h3>
                <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="zahtevZaPregledOdobren" accept-charset="UTF-8">
                    <div>
                        <label class="labels" for="inputKlinika"><strong>Sala</strong></label>
                        <select v-model="sala" id="inputKlinika" required>
                            <option v-for="k in sale" v-bind:value="k.id">
                                {{k.naziv}}
                            </option>
                        </select>
                    </div>
                     <div class="form-group">
                        <label class="labels" for="inputJedinstveniBroj"><strong>Cena u $</strong></label>
                        <input type="number" required class="form-control" id="inputJedinstveniBroj" placeholder="Unesite JBO" v-model="cena">
                    </div>
                    <button type="submit" class="btn btn-dark">Posalji</button>
                </form>
                <br>
                <table id="doctorTable" class="fl-table">
                    <thead>
                        <tr>
                             <th>Datum</th>
                        <th>Vreme početka</th>
                        <th>Vreme završetka</th>
                        <th>Opcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="pr in this.pregledi">
                        <td>{{datePrint(pr.datum)}}</td>
                        <td>{{pr.pocetak}}</td>
                        <td>{{pr.kraj}}</td>
                        <td><h2 class="btn btn-success" data-toggle="collapse" data-target="#searchForm" aria-expanded="false"><button v-on:click.prevent="odaberiOsoblje(pr,pr.id)">Odaberi osoblje za pregled</button></h2></td>
                    </tr>
                    </tbody>
                </table>
                <br>
            </div>
        </div>
        `,
    mounted: function () {
        varL = this;
        let jwt = localStorage.getItem('auth');

        axios.get('/isa/adminKlinike/sviZahteviZaPreglede', {headers: {Authorization: jwt}}).then(function (response) {
            varL.pregledi = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })

        axios.get('/isa/sale', {headers: {Authorization: jwt}}).then(function (response) {
            varL.sale = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })
        

    },
    methods: {
        datePrint: function(someDate) {
            let varDate = new Date(someDate);
            return [varDate.getDate(), varDate.getMonth() + 1, varDate.getFullYear()].join("-");
        },
        odaberiOsoblje: function(pr,elem) {
           this.idZahteva = elem;
           this.pregled = pr;
           var datumcic = new Date(this.pregled.datum);
           console.log(datumcic);
           this.datuM = [datumcic.getMonth() + 1,datumcic.getDate(), datumcic.getFullYear()].join("-")
           console.log(this.pregled);
        },
        zahtevZaPregledOdobren: function(){
            if(this.pregled !== null){
            var pregled = {
                    "vremePocetka" : this.pregled.pocetak,
                    "vremeZavrsetka" : this.pregled.kraj,
                    "trajanje" : "1h",
                    "cena" : this.cena,
                    "medicinskaSestra" : this.pregled.sestra,
                    "pacijent" : this.pregled.pacijent
                };
            console.log(this.datuM);
            console.log(this.pregled.pocetak);
            console.log(this.pregled.kraj);
            console.log(this.cena);
            
            var ap = this;
            let jwt = localStorage.getItem('auth');
            axios.post('/isa/pregled/addDodatniPregled/'+this.datuM + "," + this.sala + "," + this.pregled.id + "," + this.pregled.tipPregleda + "," + this.pregled.lekar.id, pregled, {headers: {Authorization: jwt}})
                .then(function(response) {
                    window.location.hash = "#/pregled/dodaj"
                    alert("Uspesno ste dodali pregled.");
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            }else {
                alert("Izaberite zahtev.");
            }
        }
        
    }

});