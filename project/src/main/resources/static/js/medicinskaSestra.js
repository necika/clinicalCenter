Vue.component('medicinskaSestra', {
    data: function () {
        return {
            medicinskaSestre: null
        }
    },
    template:
        `
        <div class="container">
            <div class="table-wrapper">
                <h1>Pregled Medicinskih Sestara</h1>       
                <table id="doctorTable" class="fl-table">
                    <thead>
                        <tr>
                            <th>Ime</th>
                            <th>Prezime</th>
                            <th>Email</th>
                            <th colspan="2">Opcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(s,index) in medicinskaSestre" v-bind:id="s.id">
                            <td>{{s.ime}}</td>
                            <td>{{s.prezime}}</td>
                            <td>{{s.email}}</td>
                            <td><router-link :to="{path : '/medicinskaSestra/profil', query : {sestra : s.id}}" class="btn btn-dark">Profil</router-link></td>
                            <td><button v-on:click.prevent="ukloni(s, s.id)" class="btn btn-dark">Ukloni</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        `,
    mounted: function () {
        varL = this
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/medicinskaSestra/all', {headers: {Authorization: jwt}}).then(function (response) {
            varL.medicinskaSestre = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })
    },
    methods: {
        ukloni: function(l, elem) {
            console.log(elem);
            console.log(l);
            let row = document.getElementById(elem);

            let jwt = localStorage.getItem('auth');
            axios.delete('/isa/medicinskaSestra/remove/' + l.id, {headers: {Authorization: jwt}})
            .then(
                row.innerHTML = ''
            )
            .catch(error => {alert(error.response.data);})
        }
    }
});

Vue.component('dodavanjeMedicinskaSestra',{
    data: function() {
        return{
            email : undefined,
            lozinka : undefined,
            ime : undefined,
            prezime : undefined, 
            adresa : undefined,
            grad : undefined,
            drzava : undefined,
            odobrenGodisnji : undefined,
            brTelefona : undefined,
            datumPovratkaSaGodisnjeg : undefined,
            datumOdlaskaNaGodisnjeg : undefined,
            jedinstveniBrojOsiguranika : undefined,
            pocetakRadnogVremena : undefined,
            krajRadnogVremena : undefined,
            pocetakSat : undefined,
            pocetakMinut : undefined,
            krajSat : undefined,
            krajMinut : undefined,
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
    <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="dodaj" accept-charset="UTF-8">
        <h2>Dodavanje medicinske sestre</h2>
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
            <input type="tel" pattern="[0-9]+" required class="form-control" id="inputBrTelefona" placeholder="Unesite broj telefona" v-model="brTelefona">
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
        <div class="form-group">
            <label class="labels"><strong>Početak radnog vremena</strong></label>
            <div class="time-container">
                <input type="number" onkeypress="return false;" required class="time-div" id="inputPocetakSat" min="0" max="23" placeholder="Sat" v-model="pocetakSat">
                <input type="number" onkeypress="return false;" required class="time-div" id="inputPocetakMinut" min="0" max="59" placeholder="Minut" v-model="pocetakMinut">
            </div>
        </div>
        <div class="form-group">
            <label class="labels"><strong>Kraj radnog vremena</strong></label>
            <div class="time-container">
                <input type="number" onkeypress="return false;" required class="time-div" id="inputKrajSat" min="0" max="23" placeholder="Sat" v-model="krajSat">
                <input type="number" onkeypress="return false;" required class="time-div" id="inputKrajMinut" min="0" max="59" placeholder="Minut" v-model="krajMinut">
            </div>
        </div>
        <button type="submit" class="btn btn-dark">Dodaj</button>
        <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
    </form> 
    </div>
    `,
    methods: {
        dodaj: function() {
            
            this.pocetakRadnogVremena = this.pocetakSat + ":" + this.pocetakMinut;
            this.krajRadnogVremena = this.krajSat + ":" + this.krajMinut;

            var sestra = {
                "email" : this.email,
                "lozinka" : this.lozinka,
                "ime" : this.ime,
                "prezime" : this.prezime,
                "brTelefona" : this.brTelefona,
                "grad" : this.grad,
                "drzava" : this.drzava, 
                "adresa" : this.adresa,
                "jedinstveniBrOsiguranika" : this.jedinstveniBrojOsiguranika,
                "pocetakRadnogVremena" : this.pocetakRadnogVremena,
                "krajRadnogVremena" : this.krajRadnogVremena,
                "datumPovratkaSaGodisnjeg" : null,
                "datumOdlaskaNaGodisnjeg" : null,
                "odobrenGodisnji" : false
                   
            }
            var ap = this;
            let jwt = localStorage.getItem('auth');

            axios.post('/isa/medicinskaSestra/add/' + this.klinika, sestra, {headers: {Authorization: jwt}})
                .then(function(response) {
                    window.location.hash = "#/lekar/dodaj"
                    alert("Uspešno ste dodali medicinskuSestru.");
                    ap.email = undefined;
                    ap.lozinka = undefined;
                    ap.ime = undefined;
                    ap.prezime = undefined;
                    ap.brTelefona = undefined;
                    ap.grad = undefined;
                    ap.drzava = undefined;
                    ap.adresa = undefined;
                    ap.jedinstveniBrojOsiguranika = undefined;
                    ap.pocetakRadnogVremena = undefined;
                    ap.krajRadnogVremena = undefined;
                    ap.pocetakSat = undefined;
                    ap.pocetakMinut = undefined;
                    ap.krajSat = undefined;
                    ap.krajMinut = undefined;
                    ap.odobrenGodisnji = undefined;
                    ap.datumPovratkaSaGodisnjeg = undefined;
                    ap.datumOdlaskaNaGodisnji = undefined;
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            
        },
        odustani: function(){
            window.location.hash = "#/profilMedicinskeSestre";
        }
    }
});

Vue.component('profilMedicinskeSestre',{
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
            jedinstveniBrOsiguranika : undefined
        }
    },
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/medicinskaSestra/' + this.$route.query.sestra, {headers: {Authorization: jwt}})
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
    template : `
    <div class="container">
        <div class="table-wrapper">
            <table id="lkTable" class="fl-table">
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
                    <tr>
                        <td style="font-weight:bold">JBO</td>
                        <td>{{this.jedinstveniBrOsiguranika}}</td>
                    </tr>
                </tbody>
            </table>
            <p></p>
            <router-link :to="{path : '/medicinskaSestra/lozinka', query : {sestra : this.id}}" class="btn btn-dark">Izmeni lozinku</router-link>
            <p></p>
            <router-link :to="{path : '/medicinskaSestra/izmeni', query : {sestra : this.id}}" class="btn btn-dark">Izmeni podatke</router-link>
            <p></p>
        </div>
    </div>
    `
});

Vue.component('izmenaSestra',{
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
            odobrenGodisnji : undefined,
            datumOdlaskaNaGodisnji : undefined,
            datumPovratkaSaGodisnjeg : undefined,
            pocetakRadnogVremena : undefined,
            krajRadnogVremena : undefined,
            //klinika : undefined
        }
    },
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/medicinskaSestra/' + this.$route.query.sestra, {headers: {Authorization: jwt}})
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
                this.odobrenGodisnji = response.data.odobrenGodisnji;
                this.datumOdlaskaNaGodisnji = response.data.datumOdlaskaNaGodisnji;
                this.datumPovratkaSaGodisnjeg = response.data.datumPovratkaSaGodisnjeg;
                this.pocetakRadnogVremena = response.data.pocetakRadnogVremena;
                this.krajRadnogVremena = response.data.krajRadnogVremena;
                //this.klinika = response.data.klinika;
            })
            .catch(error => {alert(error.response.data);})
    },
    template : `
    <div class="container">
    <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="dodaj" accept-charset="UTF-8">
        <h2>Izmena medicinske sestre</h2>
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
            <input type="tel" pattern="[0-9]+" required class="form-control" id="inputBrTelefona" placeholder="Unesite broj telefona" v-model="brTelefona">
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
        dodaj: function() {
            
            var sestra = {
                "id" : this.id,
                "email" : this.email,
                "lozinka" : this.lozinka,
                "ime" : this.ime,
                "prezime" : this.prezime,
                "brTelefona" : this.brTelefona,
                "grad" : this.grad,
                "drzava" : this.drzava, 
                "adresa" : this.adresa,
                "jedinstveniBrOsiguranika" : this.jedinstveniBrOsiguranika,
                "odobrenGodisnji" : this.odobrenGodisnji,
                "datumOdlaskaNaGodisnji" : this.datumOdlaskaNaGodisnji,
                "datumPovratkaSaGodisnjeg" : this.datumPovratkaSaGodisnjeg,
                "pocetakRadnogVremena" : this.pocetakRadnogVremena,
                "krajRadnogVremena" : this.krajRadnogVremena,
                //"klinika" : this.klinika
            }
            var param = sestra.id;
            let jwt = localStorage.getItem('auth');
            axios.put('/isa/medicinskaSestra/update/' + param, sestra, {headers: {Authorization: jwt}})
                .then(function(response) {
                    window.location.hash = "#/medicinskaSestra/profil?sestra=" + param;
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            
        },
        odustani: function(){
            window.location.hash = "#/medicinskaSestra/profil?sestra=" + this.$route.query.sestra;
        }
    }
});

Vue.component('kalendarOdmora', {
    data: function () {
        return {
           pocetakOdmora : null,
           krajOdmora : null,
           tipTermina : null
        }
    },
    template:
        `
        <div class="container">
            <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="posaljiZahtev" accept-charset="UTF-8">
                <h2>Biranje datuma za godišnji odmor ili odsustvo</h2>
                <div class="form-group">
                    <label class="labels" for="inputTip"><strong>Tip termina (godišnji odmor, odsustvo)</strong></label>
                    <select v-model="tipTermina" required>
                        <option>Godišnji odmor</option>
                        <option>Odsustvo</option>
                    </select>
                </div>
                <div class="form-group">
                    <label class="labels" for="inputPocetak"><strong>Datum početka</strong></label>
                    <input type="date" required class="form-control" id="inputPocetak" placeholder="Početni datum" v-model="pocetakOdmora">
                </div>
                <div class="form-group">
                    <label class="labels" for="inputKraj"><strong>Datum završetka</strong></label>
                    <input type="date" v-bind:min="this.pocetakOdmora" required class="form-control" id="inputKraj" placeholder="Završni datum" v-model="krajOdmora">
                </div>
                <button type="submit" class="btn btn-dark">Dodaj</button>
                <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
            </form> 
        </div > 
        `,
    methods: {
        posaljiZahtev: function() {
            var ok = true;
            if (this.tipTermina === null) {
                ok = false;
                alert("Niste odabrali tip termina.");
            }

            if (this.pocetakOdmora === null || this.krajOdmora === null) {
                ok = false;
                alert("Niste izabrali datum");
            }

            if (ok) {
                ap = this
                let jwt = localStorage.getItem('auth');
                axios.put('/isa/medicinskaSestra/kalendar/' + this.pocetakOdmora + ',' + this.krajOdmora + ',' + this.tipTermina + ',kk', null, {headers: {Authorization: jwt}})
                    .then(function(response) {
                    window.location.href = "#/"
                    alert("Poslat zahtev za zakazivanje termina.");
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            }
        },
        odustani: function() {
            window.location.hash = "#/"
        }
    }

});

Vue.component('kalendarRadaMedicinskaSestra', {
    data: function () {
        return {
          radnoVreme : ""
        }
    },
    template:
        `
        <div class="container">
            <div id="myCalendar" class="vanilla-calendar"></div>
        </div>
        `,
     mounted: function () {
        varL = this;
        let jwt = localStorage.getItem('auth');

        axios.get('/isa/medicinskaSestra/radnoVreme', {headers: {Authorization: jwt}}).then(function (response) {
            varL.radnoVreme = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })

        let myCalendar = new VanillaCalendar({
            selector: "#myCalendar",
            pastDates: false,
            months: ['Januar', 'Februar', 'Mart', 'April', 'Maj', 'Jun', 'Jul', 'Avgust', 'Septembar', 'Oktobar', 'Novembar', 'Decembar'],
            shortWeekday: ['Ned', 'Pon', 'Uto', 'Sre', 'Čet', 'Pet', 'Sub']
        });
    },
    methods: {
        
    }

});

Vue.component('medicinskaSestraLozinka',{
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
        axios.get('/isa/medicinskaSestra/' + this.$route.query.sestra, {headers: {Authorization: jwt}})
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
                        window.location.hash = "#/medicinskaSestra/profil?sestra=" + param;
                    })
                    .catch(function(error) {
                        alert(error.response.data);
                    })
            }
        },
        odustani: function(){
            window.location.hash = "#/medicinskaSestra/profil?sestra=" + this.$route.query.admin;
        }
    }
});

Vue.component('overaRecepata', {
    data: function () {
        return {
            recepti : null
        }
    },
    template:
        `
        <div class="container">
            <div class="table-wrapper">
                <h1>Pregled recepata medicinske sestre</h1>       
                <table id="doctorTable" class="fl-table">
                    <thead>
                        <tr>
                            <th>Naziv recepta</th>
                            <th colspan="1">Opcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(s,index) in recepti" v-bind:id="s.id">
                            <td>{{s.nazivRecepta}}</td>
                            <td><button v-on:click.prevent="overi(s.id)" class="btn btn-dark">Overi</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        `,
    mounted: function () {
        varL = this
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/medicinskaSestra/receptiZaOveru', {headers: {Authorization: jwt}}).then(function (response) {
            varL.recepti = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })
    },
    methods: {
        overi: function(elem) {
            console.log(elem);
            let row = document.getElementById(elem);

            let jwt = localStorage.getItem('auth');
            axios.delete('/isa/medicinskaSestra/overaRecepta/' + elem, {headers: {Authorization: jwt}})
            .then(
                row.innerHTML = ''
            )
            .catch(error => {alert(error.response.data);})
        }
    }
});
