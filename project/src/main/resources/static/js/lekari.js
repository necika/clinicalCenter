Vue.component('lekari', {
    data: function () {
        return {
            lekari: null,
            ime : "",
            prezime : "",
            minOcena : null,
            maxOcena : null
        }
    },
    template:
        `
        <div class="container">
            <div class="table-wrapper">
                <br>
                <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="pretragaTipova" accept-charset="UTF-8">
                    <h2 class="btn btn-success" data-toggle="collapse" data-target="#searchForm" aria-expanded="false" role="button">Pretraga Lekara</h2>
                    <div id="searchForm" class="collapse">
                        <div class="form-group">
                            <input type="text" class="form-control" id="inputIme" placeholder="Unesite ime lekara" v-model="ime">
                        </div>
                        <div class="form-group">
                            <input type="text"  class="form-control" id="inputPrezime" placeholder="Unesite prezime lekara" v-model="prezime">  
                        </div>
                        <div class="form-group">
                            <input type="number" step=".01" min="0" max="5" class="form-control" id="inputMinOcena" placeholder="Unesite minimalnu ocenu (0-5)" v-model="minOcena">
                            <input type="number" step=".01" min="0" max="5" class="form-control" id="inputMaxOcena" placeholder="Unesite maksimalnu ocenu (0-5)" v-model="maxOcena">
                        </div>
                        <button v-on:click.prevent="pretrazi" class="btn btn-dark">Pretraga</button>
                    </div>
                </form>
                <br>
                <table id="doctorTable" class="fl-table">
                    <thead>
                        <tr>
                            <th v-on:click.prevent="sortTable(0)">Ime</th>
                            <th v-on:click.prevent="sortTable(1)">Prezime</th>
                            <th v-on:click.prevent="sortTable(4)">Ocena</th>
                            <th colspan="2">Opcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(lekar, index) in lekari" v-bind:id="lekar.id">
                            <td>{{lekar.ime}}</td>
                            <td>{{lekar.prezime}}</td>
                            <td>{{lekar.ocena}}</td>
                            <td><router-link :to="{path : '/lekar/profil', query : {lekar : lekar.id}}" class="btn btn-dark">Profil</router-link></td>
                            <td><button v-on:click.prevent="ukloni(lekar, lekar.id)" class="btn btn-dark">Ukloni</button></td>
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

        axios.get('/isa/lekar/all', {headers: {Authorization: jwt}}).then(function (response) {
            varL.lekari = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })
    },
    methods: {
        sortTable: function (n) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("doctorTable");
            switching = true;
            dir = "asc";

            while (switching) {
                switching = false;
                rows = table.rows;

                for (i = 1; i < (rows.length - 1); i++) {
                    shouldSwitch = false;
                    x = rows[i].getElementsByTagName("TD")[n];
                    y = rows[i + 1].getElementsByTagName("TD")[n];

                    if (dir == "asc") {
                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                            shouldSwitch = true;
                            break;
                        }
                    } else if (dir == "desc") {
                        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                            shouldSwitch = true;
                            break;
                        }
                    }
                }

                if (shouldSwitch) {
                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                    switching = true;
                    switchcount ++;
                } else {
                    if (switchcount == 0 && dir == "asc") {
                        dir = "desc";
                        switching = true;
                    }
                }
            }
        },
        ukloni: function(l, elem) {
            let row = document.getElementById(elem);

            let jwt = localStorage.getItem('auth');
            axios.delete('/isa/lekar/remove/' + l.id, {headers: {Authorization: jwt}})
            .then(
                row.innerHTML = ''
            )
            .catch(error => {alert(error.response.data);})
        },
        pretrazi: function() {
            var lekar = {
                "ime" : this.ime,
                "prezime" : this.prezime,
                "minOcena" : this.minOcena,
                "maxOcena" : this.maxOcena
            };
            let jwt = localStorage.getItem('auth');
            var ok = true;

            if (this.minOcena < 0 || this.minOcena > 5 || this.maxOcena < 0 || this.maxOcena > 5) {
                ok = false;
            }

            var ap = this;
            if (ok) {
                axios.get('/isa/lekar/pretraga/' + lekar.ime + "," + lekar.prezime + "," + lekar.minOcena + "," + lekar.maxOcena + ",ss", {headers: {Authorization: jwt}})
                .then(function(response) {
                    ap.lekari = response.data;
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            } 
        }
    }

});

Vue.component('profilLekar',{
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
            ocena : undefined
        }
    },
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/lekar/' + this.$route.query.lekar, {headers: {Authorization: jwt}})
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
                this.ocena = response.data.ocena;
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
                    <tr>
                        <td style="font-weight:bold">Ocena</td>
                        <td>{{this.ocena}}</td>
                    </tr>
                </tbody>
            </table>
            <p></p>
            <router-link :to="{path : '/lekar/lozinka', query : {lekar : this.id}}" class="btn btn-dark">Izmeni lozinku</router-link>
            <p></p>
            <router-link :to="{path : '/lekar/izmeni', query : {lekar : this.id}}" class="btn btn-dark">Izmeni podatke</router-link>
            <router-link :to="{path : '/lekar/oceni', query : {lekar : this.id}}" class="btn btn-dark">Oceni lekara</router-link>
            <router-link :to="{path : '/lekar/zakazano', query : {lekar : this.id}}" class="btn btn-dark">Zakazani pregledi i operacije</router-link>
            <p></p>
        </div>
    </div>
    `
});

Vue.component('dodavanjeLekar',{
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
            pocetakRadnogVremena : undefined,
            krajRadnogVremena : undefined,
            ocena : undefined,
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
        <h2>Dodavanje lekara</h2>
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

            var lekar = {
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
                "ocena" : 0
            }
            var ap = this;
            let jwt = localStorage.getItem('auth');

            axios.post('/isa/lekar/add/' + this.klinika, lekar, {headers: {Authorization: jwt}})
                .then(function(response) {
                    window.location.hash = "#/lekar/dodaj"
                    alert("Uspešno ste dodali lekara.");
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
                    ap.ocena = undefined;
                    ap.pocetakSat = undefined;
                    ap.pocetakMinut = undefined;
                    ap.krajSat = undefined;
                    ap.krajMinut = undefined;
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            
        },
        odustani: function(){
            window.location.hash = "#/lekar";
        }
    }
});

Vue.component('izmenaLekar',{
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
            jedinstveniBrOsiguranika : undefined,
            pocetakRadnogVremena : undefined,
            krajRadnogVremena : undefined,
            ocena : undefined
        }
    },
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/lekar/' + this.$route.query.lekar, {headers: {Authorization: jwt}})
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
                this.ocena = response.data.ocena;
                this.jedinstveniBrOsiguranika = response.data.jedinstveniBrOsiguranika;
                this.pocetakRadnogVremena = response.data.pocetakRadnogVremena;
                this.krajRadnogVremena = response.data.krajRadnogVremena;
            })
            .catch(error => {alert(error.response.data);})
    },
    template : `
    <div class="container">
    <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="dodaj" accept-charset="UTF-8">
        <h2>Izmena lekara</h2>
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
        dodaj: function() {
            var lekar = {
                "id" : this.id,
                "email" : this.email,
                "lozinka" : this.lozinka,
                "ime" : this.ime,
                "prezime" : this.prezime,
                "adresa" : this.adresa,
                "grad" : this.grad,
                "drzava" : this.drzava,
                "brTelefona" : this.brTelefona,
                "jedinstveniBrOsiguranika" : this.jedinstveniBrOsiguranika,
                "ocena" : this.ocena,
                "pocetakRadnogVremena" : this.pocetakRadnogVremena,
                "krajRadnogVremena" : this.krajRadnogVremena
            }
            var ap = this;
            var param = lekar.id;

            let jwt = localStorage.getItem('auth');
            axios.put('/isa/lekar/update/' + param, lekar, {headers: {Authorization: jwt}})
                .then(function(response) {
                    window.location.hash = "#/lekar/profil?lekar=" + param;
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            
        },
        odustani: function(){
            window.location.hash = "#/lekar/profil?lekar=" + this.$route.query.lekar;
        }
    }
});

Vue.component('odmorLekar', {
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
        </div>
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
                axios.put('/isa/lekar/odmor/' + this.pocetakOdmora + ',' + this.krajOdmora + ',' + this.tipTermina + ',kk', null, {headers: {Authorization: jwt}})
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

Vue.component('oceniLekara', {
    data: function () {
        return {
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
            pocetakRadnogVremena : undefined,
            krajRadnogVremena : undefined,
            ocena : undefined
        }
    },
    template:
        `
        <div class="container">
    <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="oceni" accept-charset="UTF-8">
        <h2>Izmena lekara</h2>
        <div class="form-group">
            <label class="labels"><strong>Ocena</strong></label>
            <input type="number" onkeypress="return false;" required class="form-control" min="1" max="5" placeholder="Unesite ocenu" v-model="ocena">
        </div>
        <button type="submit" class="btn btn-dark">Oceni</button>
        <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
    </form>	
    </div>
        `,
    mounted: function () {
        let jwt = localStorage.getItem('auth');

        axios.get('/isa/lekar/' + this.$route.query.lekar, {headers: {Authorization: jwt}})
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
                this.ocena = response.data.ocena;
                this.jedinstveniBrOsiguranika = response.data.jedinstveniBrOsiguranika;
                this.pocetakRadnogVremena = response.data.pocetakRadnogVremena;
                this.krajRadnogVremena = response.data.krajRadnogVremena;
            })
            .catch(error => {alert(error.response.data);})
    },
    methods: {
        oceni: function() {

            var lekar = {
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
                "pocetakRadnogVremena" : this.pocetakRadnogVremena,
                "krajRadnogVremena" : this.krajRadnogVremena,
                "ocena" : this.ocena
            }

            var ap = this;
            var param = lekar.id;
            let jwt = localStorage.getItem('auth');

            axios.put('/isa/lekar/oceni/' + param, lekar, {headers: {Authorization: jwt}})
                .then(function(response) {
                    window.location.hash = "#/lekar/profil?lekar=" + param;
                })
                .catch(function (error) {
                    alert("Greška.");
                })

        },
        odustani: function(){
            window.location.hash = "#/lekar/profil?lekar=" + this.$route.query.lekar;
        }
    }
});
Vue.component('kalendarRadaLekar', {
    data: function () {
        return {
          radnoVreme : ""
        }
    },
    template:
        `
        <div class="container">
            <div id="calendar"></div>
        </div>
        `,
    mounted: function () {
        moment.locale('sr');
        var now = moment();

        var events = [{
            start: now.startOf('week').add(9, 'h').format('X'),
            end: now.startOf('week').add(10, 'h').format('X'),
            title: '1',
            content: 'Hello World! <br> <p>Foo Bar</p>',
            category:'Professionnal'
        },{
            start: now.startOf('week').add(10, 'h').format('X'),
            end: now.startOf('week').add(11, 'h').format('X'),
            title: '2',
            content: 'Hello World! <br> <p>Foo Bar</p>',
            category:'Professionnal'
        }];

        var daynotes = [{
            time: now.startOf('week').add(15, 'h').add(30, 'm').format('X'),
            title: 'Leo\'s holiday',
            content: 'yo',
            category: 'holiday'
        }];

        var myCalendar = $('#calendar').Calendar({
            events: events,
            daynotes: daynotes
        }).init();

        $('#calendar').Calendar({

            // language
            locale: 'sr',
          
            // 'day', 'week', 'month'
            view: 'month',
          
            // enable keyboard navigation
            enableKeyboard: true,
          
            // default view
            defaultView: {
              largeScreen: 'week',
              smallScreen: 'day',
              smallScreenThreshold: 1000
            },
          
          
            weekday: {
              timeline: {
                fromHour: 0, // start hour
                toHour: 23, // end hour
                intervalMinutes: 60,
                format: 'HH:mm',
                heightPx: 50,
                autoResize: true
              },
              dayline: {
                weekdays: [0, 1, 2, 3, 4, 5, 6],
                format: 'dddd DD/MM',
                heightPx: 31,
                month: {
                  format: 'MMMM YYYY',
                  heightPx: 30,
                  weekFormat: 'w'
                }
              }
            },
            month: {
              format: 'MMMM YYYY',
              heightPx: 31,
              weekline: {
                format: 'w',
                heightPx: 80
              },
              dayheader: {
                weekdays: [0, 1, 2, 3, 4, 5, 6],
                format: 'dddd',
                heightPx: 30
              },
              day: {
                format: 'DD/MM'
              }
            },
          
            // timestamp in the week to display
            unixTimestamp: moment().format('X'),
          
            // event options
            event: {
              hover: {
                delay: 500
              }
            },
          
            // category options
            categories: {
              enable: true,
              hover: {
                delay: 500
              }
            },
          
            // display the current time
            now: {
              enable: false,
              refresh: false,
              heightPx: 1,
              style: 'solid',
              color: '#03A9F4'
            }
            
          })
    },
    methods: {
        
    }

});

Vue.component('lekarLozinka',{
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
        axios.get('/isa/lekar/' + this.$route.query.lekar, {headers: {Authorization: jwt}})
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
                        window.location.hash = "#/lekar/profil?lekar=" + param;
                    })
                    .catch(function(error) {
                        alert(error.response.data);
                    })
            }
        },
        odustani: function(){
            window.location.hash = "#/lekar/profil?lekar=" + this.$route.query.lekar;
        }
    }
});

Vue.component('zakazanoLekar', {
    data: function () {
        return {
            pregledi: null,
            operacije : null
        }
    },
    mounted: function () {
        let jwt = localStorage.getItem('auth');
        
        axios.get('/isa/lekar/' + this.$route.query.lekar + '/pregledi', {headers: {Authorization: jwt}})
        .then(response =>{
            this.pregledi = response.data;
        }).catch(function (error) {
            alert("Trenutno nije moguće pristupiti listi pregleda.");
        })
    },
    template:
    `
        <div class="table-wrapper">
            <table id="preglediTable" class="fl-table">
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
                        <td>{{pr.vremePocetka}}</td>
                        <td>{{pr.vremeZavrsetka}}</td>
                        <td><router-link :to="{path : '/lekar/pregled', query : {pregled : pr.id}}" class="btn btn-dark">Započni</router-link></td>
                    </tr>
                </tbody>
            </table>
        </div>
    `,
    methods: {
        datePrint: function(someDate) {
            let varDate = new Date(someDate);
            return [varDate.getDate(), varDate.getMonth() + 1, varDate.getFullYear()].join("-");
        }
    }

});

Vue.component('pregledLekar', {
    data: function () {
        return {
            pregled : null,
            sifarnici : null,
            /* Atributi za slanje zahteva za novi pregled/operaciju */ 
            noviDatum : undefined,
            noviPocetak : undefined,
            noviKraj : undefined,
            noviTipPregleda : undefined, // bice prosledjen ID tipa pregleda trenutnog Pregleda
            noviTipTermina : undefined,  // operacija ili pregled
            zdravstveniKartonPacijenta : null,
            osnovniPodaci : undefined,
            osnovniPodaciZaSlanje : undefined,
            idZdravstvenogKartona : undefined,
            podaci : undefined
        }
    },
    mounted: function () {
        let jwt = localStorage.getItem('auth');
        
        axios.get('/isa/pregled/' + this.$route.query.pregled, {headers: {Authorization: jwt}})
        .then(response =>{
            this.pregled = response.data;
            this.noviTipPregleda = response.data.tipPregleda.id;

            axios.get('/isa/zdravstveniKarton/zdravstveniKartonPacijenta/' + this.pregled.pacijent.zdravstveniKarton.id, {headers: {Authorization: jwt}})
                .then(response =>{
                    this.zdravstveniKartonPacijenta = response.data;
                    this.osnovniPodaci = this.zdravstveniKartonPacijenta.osnovniPodaci;
                    this.idZdravstvenogKartona = this.zdravstveniKartonPacijenta.id;
                }).catch(function (error) {
                    alert("Trenutno nije moguće pristupiti zdravstvenog kartonu pacijenta.");
                })

        }).catch(function (error) {
            alert("Trenutno nije moguće pristupiti traženom pregledu.");
        })

        axios.get('/isa/sifarnici/all', {headers: {Authorization: jwt}})
        .then(response =>{
            this.sifarnici = response.data;
        }).catch(function (error) {
            alert("Trenutno nije moguće pristupiti listi kombinacija iz šifarnika.");
        })

    },
    template:
    `
    <div class="container">
        <div class="card text-center">
            <div class="card-header">
                <ul id="pregledInfo" class="nav nav-tabs card-header-tabs" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link active" data-toggle="tab" href="#pregledDetalji" role="tab" aria-controls="pregledDetalji" aria-selected="true">Informacije o pregledu</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#lekarDetalji" role="tab" aria-controls="lekarDetalji" aria-selected="false">Informacije o lekaru</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#pacijentDetalji" role="tab" aria-controls="pacijentDetalji" aria-selected="false">Informacije o pacijentu</a>
                    </li>
                     <li class="nav-item">
                        <a class="nav-link" data-toggle="tab" href="#sestraDetalji" role="tab" aria-controls="sestraDetalji" aria-selected="false">Informacije o med. sestri</a>
                    </li>
                </ul>
            </div>
            <div class="card-body">
                <h5 class="card-title">Osnovne informacije</h5>
                <div class="tab-content mt-3">
                    <div class="tab-pane fade show active" id="pregledDetalji" role="tabpanel">
                        <p class="card-text">Zakazani datum: {{datePrint(this.pregled.datum)}}</p>
                        <p class="card-text">Vreme početka: {{this.pregled.vremePocetka}}</p>
                        <p class="card-text">Vreme završetka: {{this.pregled.vremeZavrsetka}}</p>
                        <p class="card-text">Trajanje: {{this.pregled.trajanje}}</p>
                        <p class="card-text">Cena: {{this.pregled.cena}}</p>
                    </div>
                    <div class="tab-pane fade" id="lekarDetalji" role="tabpanel" aria-labelledby="lekarDetalji-tab">  
                        <p class="card-text">Odabrani lekar: {{this.pregled.lekar.ime}} {{this.pregled.lekar.prezime}}</p>
                        <p class="card-text">Email lekara: {{this.pregled.lekar.email}}</p>
                        <p class="card-text">Kontakt telefon lekara: {{this.pregled.lekar.brTelefona}}</p>
                    </div>
                    <div class="tab-pane fade" id="pacijentDetalji" role="tabpanel" aria-labelledby="pacijentDetalji-tab">
                        <p class="card-text">Ime i prezime pacijenta: {{this.pregled.pacijent.ime}} {{this.pregled.pacijent.prezime}}</p>
                        <p class="card-text">JBO pacijenta: {{this.pregled.pacijent.jedinstveniBrOsiguranika}}</p>
                        <p class="card-text">Email pacijenta: {{this.pregled.pacijent.email}}</p>
                        <p class="card-text">Kontakt telefon pacijenta: {{this.pregled.pacijent.brTelefona}}</p>
                    </div>
                    <div class="tab-pane fade" id="sestraDetalji" role="tabpanel" aria-labelledby="sestraDetalji-tab">
                        <p class="card-text">Ime i prezime med. sestre: {{this.pregled.medicinskaSestra.ime}} {{this.pregled.medicinskaSestra.prezime}}</p>
                        <p class="card-text">JBO med. sestre: {{this.pregled.medicinskaSestra.jedinstveniBrOsiguranika}}</p>
                        <p class="card-text">Email med. sestre: {{this.pregled.medicinskaSestra.email}}</p>
                        <p class="card-text">Kontakt telefon med. sestre: {{this.pregled.medicinskaSestra.brTelefona}}</p>
                    </div>
                </div>
            </div>
        </div>
        <div id="accordion">
            <div class="card">
                <div class="card-header" id="headingOne">
                <h5 class="mb-0">
                    <button class="btn btn-outline-primary collapsed" data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                    Odaberi recepte
                    </button>
                    <button class="btn btn-outline-success collapsed" data-toggle="collapse" data-target="#collapseZdravstevniKarton" aria-expanded="false" aria-controls="collapseOne">
                        Zdravstevni karton
                    </button>
                </h5>
                </div>

                <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                    <div class="card-body">
                        <form>
                            <select required id = "sifarniciSelect" multiple>
                                <option v-for="sifra in this.sifarnici" v-bind:value="sifra.id">
                                    {{sifra.dijagnoze}}-{{sifra.lekovi}}
                                </option>
                            </select>
                            <div class="form-group">
                                <label class="labels" for="inputDrzava"><strong>Podaci o pregledu</strong></label>
                                <input type="text" required class="form-control" id="inputPodaci" placeholder="Unesite podatke o pregledu" v-model="podaci">
                            </div>
                            <button class="btn-save btn btn-primary btn-sm" v-on:click.prevent="odaberiRecepte">Sačuvaj</button>
                        </form>
                    </div>
                </div>
                <div id="collapseZdravstevniKarton" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                    <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="izmeniZdravstvenuKarton" accept-charset="UTF-8">
                        <h2>Zdravstveni karton</h2>
                    <!--    <div>
                            <label class="labels" for="inputKlinika"><strong>Klinika</strong></label>
                            <select v-model="klinika" id="inputKlinika" required>
                                <option v-for="k in this.sifarnici" v-bind:value="k.id">
                                    {{k.dijagnoze}}
                                </option>
                            </select>
                        </div>      -->
                        <div class="form-group">
                            <label class="labels" for="inputEmail"><strong>Osnovni podaci</strong></label>
                            <textarea v-model="osnovniPodaciZaSlanje">{{this.osnovniPodaci}}</textarea>
                        </div>
                        <button type="submit" class="btn btn-dark">Izmeni</button>
                    </form> 
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="headingTwo">
                <h5 class="mb-0">
                    <button class="btn btn-outline-secondary collapsed" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                    Zakaži naredni termin
                    </button>
                </h5>
                </div>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
                <div class="card-body">
                    <form>
                        <div class="form-group row">
                            <label for="inputDatum3" class="col-sm-2 col-form-label">Datum</label>
                            <div class="col-sm-10">
                                <input type="date" v-bind:min="this.pregled.datum" class="form-control" id="inputDatum3" placeholder="Odaberite datum" v-model="noviDatum">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputPocetak3" class="col-sm-2 col-form-label">Vreme početka</label>
                            <div class="col-sm-10">
                                <input type="time" class="form-control" id="inputPocetak3" placeholder="Odaberite vreme početka" v-model="noviPocetak">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputKraj3" class="col-sm-2 col-form-label">Vreme završetka</label>
                            <div class="col-sm-10">
                                <input type="time" class="form-control" id="inputKraj3" placeholder="Odaberite vreme završetka" v-model="noviKraj">
                            </div>
                        </div>
                        <fieldset class="form-group">
                        <div class="row">
                            <legend class="col-form-label col-sm-2 pt-0">Termin za: </legend>
                            <div class="col-sm-10">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios1" value="Pregled" checked v-model="noviTipTermina">
                                    <label class="form-check-label" for="gridRadios1">
                                    Pregled
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios2" value="Operacija" v-model="noviTipTermina">
                                    <label class="form-check-label" for="gridRadios2">
                                    Operaciju
                                    </label>
                                </div>
                            </div>
                        </div>
                        </fieldset>
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <button v-on:click.prevent="posaljiZahtev" class="btn btn-primary">Pošalji zahtev</button>
                            </div>
                        </div>
                    </form>
                </div>
                </div>
            </div>
        </div>
        <button type="button" class="btn btn-outline-success float-right">Završetak</button>
    </div>
    `,
    methods: {
        datePrint: function(someDate) {
            let varDate = new Date(someDate);
            return [varDate.getDate(), varDate.getMonth() + 1, varDate.getFullYear()].join("-");
        },
        posaljiZahtev: function() {
            if(this.noviTipTermina == "Pregled"){
                var zahtevZaPregled= {
                    "datum" : this.noviDatum,
                    "pocetak" : this.noviPocetak.toString(),
                    "kraj" : this.noviKraj.toString(),
                    "tipPregleda" : this.noviTipPregleda,
                    "tipTermina" : this.noviTipTermina,
                    "lekar" : this.pregled.lekar,
                    "sestra" : this.pregled.medicinskaSestra,
                    "pacijent" : this.pregled.pacijent
                };

                let jwt = localStorage.getItem('auth');
                 axios.post('/isa/lekar/naredniTermin', zahtevZaPregled, {headers: {Authorization: jwt}})
                .then(response =>{
                    alert("Zahtev je poslat.");
                }).catch(function (error) {
                    alert("Trenutno nije moguće poslati zahtev.");
                })
            }
        },
        izmeniZdravstvenuKarton: function() {
            let jwt = localStorage.getItem('auth');

            var zdravstveniKartonSlanje = {
                "osnovniPodaci" : this.osnovniPodaci
            };

            var ap = this
            axios.put('/isa/zdravstveniKarton/izmeni/'+this.osnovniPodaciZaSlanje + "," + this.pregled.pacijent.zdravstveniKarton.id,null, {headers: {Authorization: jwt}})
            .then(response =>{
                alert("Izmenjen je zdravstveni karton");
                ap.osnovniPodaci = undefined;
            }).catch(function (error) {
                alert("Trenutno nije moguće poslati zahtev.");
            })
        },
        odaberiRecepte : function(){
            var itemList = document.getElementById('sifarniciSelect');
            var collection = itemList.selectedOptions;
            var output = "";

            for (let i=0; i<collection.length; i++) {
                output += collection[i].label;
                output += "_";
                
            }
            console.log(output);
            console.log(this.podaci);
            console.log(this.pregled.pacijent.zdravstveniKarton.id);
            let jwt = localStorage.getItem('auth');
            axios.get('/isa/zdravstveniKarton/addIzvestajOPregledu/'+this.pregled.pacijent.zdravstveniKarton.id + "," + this.podaci + "," + output + "," + this.pregled.medicinskaSestra.id, {headers: {Authorization: jwt}})
            .then(response =>{
                alert("Dodali ste izvestaj o pregledu.");
            }).catch(function (error) {
                alert("Trenutno ne moze da se upise recept..");
            })


        }
    }

});
Vue.component('profilUlogovanogDoktoraIliSestre', {
    data: function () {
        return {
            pacijenti : null,
            osnovniPodaciZaSlanje : undefined,
            osnovniPodaci : undefined,
            pacijent : null,
            promena : false
        }
    },
    mounted: function () {
        let jwt = localStorage.getItem('auth');
        
        axios.get('/isa/pacijentiUlogovanogKorisnika', {headers: {Authorization: jwt}})
        .then(response =>{
            this.pacijenti = response.data;
        }).catch(function (error) {
            alert("Trenutno nije moguće pristupiti listi pacijenata.");
        })
    },
    template:
    `
        <div class="table-wrapper">
             <form action="#" method="post" v-on:submit.prevent="izmenaKartona" accept-charset="UTF-8">
                <div>
                    <h3>Izmena zdravstvenog kartona</h3>
                    <div class="form-group">
                            <label class="labels" for="inputEmail"><strong>Osnovni podaci</strong></label>
                            <textarea v-model="osnovniPodaciZaSlanje">{{this.osnovniPodaci}}</textarea>
                        </div>
                    <button type="submit" class="btn btn-dark">Izmena</button>
                </div>
            </form>
            <br>
            <table id="preglediTable" class="fl-table">
                <thead>
                    <tr>
                        <th>Ime</th>
                        <th>Prezime</th>
                        <th>Email</th>
                        <th>Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="p in this.pacijenti">
                        <td>{{p.ime}}</td>
                        <td>{{p.prezime}}</td>
                        <td>{{p.email}}</td>
                        <td><button v-on:click.prevent="uzmiIdZaIzmenu(p.id)">Izmeni karton</button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    `,
    methods: {
        uzmiIdZaIzmenu: function(id) {
           
                let jwt = localStorage.getItem('auth');
                axios.get('/isa/pacijent/'+id, {headers: {Authorization: jwt}})
                 .then(response =>{
                    this.pacijent = response.data;
                    var s = this;
                    s.osnovniPodaci = s.pacijent.zdravstveniKarton.osnovniPodaci;
                }).catch(function (error) {
                    alert("Trenutno nije moguće pristupiti listi pacijenata.");
                })
        },
        izmenaKartona:function() {
            let jwt = localStorage.getItem('auth');

            var ap = this
            axios.put('/isa/zdravstveniKarton/izmeni/'+this.osnovniPodaciZaSlanje + "," + this.pacijent.zdravstveniKarton.id,null, {headers: {Authorization: jwt}})
            .then(response =>{
                alert("Izmenjen je zdravstveni karton");
                ap.osnovniPodaci = undefined;
            }).catch(function (error) {
                alert("Trenutno nije moguće poslati zahtev.");
            })
        }
    }

});