Vue.component('klinike', {
   data: function () {
       return {
           klinike: null,
           ocena1: null,
           ocena2: null,
           naziv: "",
           adresa: ""
       }
   },
    template:
        `
        <div class="table-wrapper">
            <br>
            <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="pretragaKlinika" accept-charset="UTF-8">
                <h2 class="btn btn-success" data-toggle="collapse" data-target="#searchForm" aria-expanded="false" role="button">Pretraga Klinika</h2>
                <div id="searchForm" class="collapse">
                    <div class="form-group">
                        <input type="text"  class="form-control" id="inputNaziv" placeholder="Unesite naziv klinike" v-model="naziv">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="inputAdresa" placeholder="Unesite svoju Adresu" v-model="adresa">  
                    </div>
                    <div class="form-group">
                        <input type="number" min="1" max="5" class="form-control" id="inputOcena1" placeholder="Unesite min ocenu" v-model="ocena1">
                        <input type="number" min="1" max="5" class="form-control" id="inputOcena2" placeholder="Unesite max ocenu" v-model="ocena2">  
                    </div>
                    <button type="submit" class="btn btn-dark">Pretraga</button>
                </div>
            </form>
            <br>
            <table id="clinicTable" class="fl-table">
                <thead>
                    <tr>
                        <th v-on:click.prevent="sortTable(0)">Naziv</th>
                        <th v-on:click.prevent="sortTable(1)">Adresa</th>
                        <th v-on:click.prevent="sortTable(2)">Ocena</th>
                        <th colspan="1">Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="klinika in klinike">
                        <td>{{klinika.naziv}}</td>
                        <td>{{klinika.adresa}}</td>
                        <td>{{klinika.ocena}}</td>
                        <td><router-link :to="{path : '/klinika/profil', query : {klinika : klinika.id}}" class="btn btn-dark">Profil</router-link></td>
                    </tr>
                </tbody>
            </table>
        </div>
        `,
    mounted: function () {
        varK = this;
        let jwt = localStorage.getItem('auth');

        axios.get('/isa/klinika/all', {headers: {Authorization: jwt}}).then(function (response) {
            varK.klinike = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })
    },
    methods: {
        sortTable: function (n) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("clinicTable");
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
        pretragaKlinika: function() {

            var klinika = {
                "naziv" : this.naziv,
                "adresa" : this.adresa,
                "ocena1": this.ocena1,
                "ocena2" : this.ocena2   
            }           
            
            var ap = this;
            let jwt = localStorage.getItem('auth');
            axios.get('/isa/klinika/pretraga/' + klinika.naziv + "," + klinika.adresa + "," + klinika.ocena1 + "," + klinika.ocena2 + ",ss", {headers: {Authorization: jwt}})
                .then(function(response) {
                    ap.klinike = response.data;
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            
        }
    }

});

Vue.component('pretragaKlinika', {
   data: function () {
       return {
           klinike: null,
           ocena1: null,
           ocena2: null,
           naziv: "",
           adresa: ""
       }
   },
    template:
        `
        <div>
        <div class="container">
            <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="pretragaKlinika" accept-charset="UTF-8">
               <h2 class="btn btn-success" data-toggle="collapse" data-target="#searchForm" aria-expanded="false" role="button">Pretraga Klinika</h2>
                <div id="searchForm" class="collapse">
                    <div class="form-group">
                        <input type="text"  class="form-control" id="inputNaziv" placeholder="Unesite naziv klinike" v-model="naziv">
                    </div>
                    <div class="form-group">
                        <input type="text"  class="form-control" id="inputAdresa" placeholder="Unesite svoju Adresu" v-model="adresa">  
                    </div>
                    <div class="form-group">
                        <input type="number"  class="form-control" id="inputOcena1" placeholder="Unesite min ocenu" v-model="ocena1">
                        <input type="number"  class="form-control" id="inputOcena2" placeholder="Unesite max ocenu" v-model="ocena2">  
                    </div>
                    <button type="submit" class="btn btn-dark">Pretraga</button>
                </div>
            </form>
        </div>

        <div class="table-wrapper">
            <table id="clinicTable" class="fl-table">
                <thead>
                    <tr>
                        <th>Naziv</th>
                        <th>Adresa</th>
                        <th>Ocena</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="klinika in klinike">
                        <td>{{klinika.naziv}}</td>
                        <td>{{klinika.adresa}}</td>
                        <td>{{klinika.ocena}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        </div>
        `,
    mounted: function () {
        varK = this
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/klinika/all', {headers: {Authorization: jwt}}).then(function (response) {
            varK.klinike = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })
    },
    methods: {
        pretragaKlinika: function() {

            var klinika = {
                "naziv" : this.naziv,
                "adresa" : this.adresa,
                "ocena1": this.ocena1,
                "ocena2" : this.ocena2   
            }           
            
            var ap = this;
            let jwt = localStorage.getItem('auth');
            axios.get('/isa/klinika/pretraga/' + klinika.naziv + "," + klinika.adresa + "," + klinika.ocena1 + "," + klinika.ocena2 + ",ss", {headers: {Authorization: jwt}})
                .then(function(response) {
                    ap.klinike = response.data;
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            
        }
    }
});

Vue.component('profilKlinike', {
    data: function () {
        return {
            id: undefined,
            naziv: undefined,
            ocena: undefined,
            adresa: undefined,
            opis: undefined,
            lekari : null,
            sale : null,
            cenovnik : null
        }
    },
    template:
        `
        <div class="container">
        <div class="table-wrapper">
            <table id="lkTable" class="fl-table">
                <thead>
                    <tr>
                        <th colspan="2">Podaci klinike</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td style="font-weight:bold">Naziv</td>
                        <td>{{this.naziv}}</td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold">Adresa</td>
                        <td>{{this.adresa}}</td>
                    </tr>
                    <tr>
                        <td style="font-weight:bold">Ocena</td>
                        <td>{{this.ocena}}</td>
                    </tr>
                </tbody>
            </table>
            <p></p>
            <div class="container">
                <h4>Cenovnik klinike {{this.naziv}}</h4>
                <ul v-for="c in this.cenovnik">
                    <li>{{c}}</li>
                </ul>
            </div>
            <p></p>
            <div class="container">
                <h4>Dostupne sale</h4>
                <ul v-for="s in this.sale">
                    <li>{{s.naziv}} {{s.broj}}</li>
                </ul>
            </div>
            <p></p>
            <div class="container">
                <h4>Zaposleni lekari</h4>
                <ul v-for="l in this.lekari">
                    <li>{{l.ime}} {{l.prezime}}</li>
                </ul>
            </div>
            <p></p>
            <router-link :to="{path : '/klinika/oceni', query : {klinika : this.id}}" class="btn btn-dark">Oceni kliniku</router-link>
            <p></p>
            <router-link :to="{path : '/klinika/izmeni', query : {klinika : this.id}}" class="btn btn-dark">Izmeni podatke</router-link>
            <p></p>
        </div>
    </div>
        `,
    mounted: function () {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/klinika/' + this.$route.query.klinika, {headers: {Authorization: jwt}})
            .then(response =>{
                this.id = response.data.id;
                this.naziv = response.data.naziv;
                this.ocena = response.data.ocena;
                this.adresa = response.data.adresa;
                this.opis = response.data.opis;
                this.cenovnik = response.data.cenovnik;
            })
            .catch(error => {alert(error.response.data);})

        axios.get('/isa/klinika/' + this.$route.query.klinika + '/lekari', {headers: {Authorization: jwt}})
            .then(response =>{
                this.lekari = response.data;
            })
            .catch(error => {alert(error.response.data);})

        axios.get('/isa/klinika/' + this.$route.query.klinika + '/sale', {headers: {Authorization: jwt}})
            .then(response =>{
                this.sale = response.data;
            })
            .catch(error => {alert(error.response.data);})
    }
});

Vue.component('oceniKliniku', {
    data: function () {
        return {
            id: undefined,
            naziv: undefined,
            ocena: undefined,
            adresa: undefined,
            opis: undefined
        }
    },
    template:
        `
        <div class="container">
    <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="oceni" accept-charset="UTF-8">
        <h2>Ocenjivanje klinike</h2>
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

        axios.get('/isa/klinika/' + this.$route.query.klinika, {headers: {Authorization: jwt}})
            .then(response =>{
                this.id = response.data.id;
                this.naziv = response.data.naziv;
                this.ocena = response.data.ocena;
                this.adresa = response.data.adresa;
                this.opis = response.data.opis;
            })
            .catch(error => {alert(error.response.data);})
    },
    methods: {
        oceni: function() {

            var klinika = {
                "id" : this.id,
                "naziv": this.naziv,
                "adresa" : this.adresa,
                "opis": this.opis,
                "ocena": this.ocena
            }
            var ap = this;
            var param = klinika.id;
            let jwt = localStorage.getItem('auth');

            axios.put('/isa/klinika/oceni/' + param, klinika, {headers: {Authorization: jwt}})
                .then(function(response) {
                    window.location.hash = "#/klinika/profil?klinika=" + param;
                })
                .catch(function (error) {
                    alert("Greška.");
                })

        },
        odustani: function(){
            window.location.hash = "#/klinika/profil?klinika=" + this.$route.query.klinika;
        }
    }
});

Vue.component('izmeniKliniku',{
    data: function() {
        return{
            id : undefined,
            naziv : undefined,
            adresa : undefined,
            opis : undefined,
            ocena : undefined
        }
    },
    template : `
    <div class="container">
        <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="izmenaKlinike" accept-charset="UTF-8">
            <h2>Dodavanje klinike</h2>
            <div class="form-group">
                <label class="labels" for="inputNaziv"><strong>Naziv</strong></label>
                <input type="text" required class="form-control" id="inputNaziv" placeholder="Unesite naziv klinike" v-model="naziv">
            </div>
            <div class="form-group">
                <label class="labels" for="inputAdresa"><strong>Adresa</strong></label>
                <input type="text" required class="form-control" id="inputAdresa" placeholder="Unesite svoju Adresu" v-model="adresa">
            </div>
            <div class="form-group">
                <label class="labels" for="inputOpis"><strong>Opis</strong></label>
                <input type="text" required class="form-control" id="inputOpis" placeholder="Unesite opis klinike" v-model="opis">
                
            </div>
            <button type="submit" class="btn btn-dark">Izmeni</button>
            <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
        </form>
    </div>
    `,
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/klinika/' + this.$route.query.klinika, {headers: {Authorization: jwt}})
            .then(response =>{
                this.id = response.data.id;
                this.naziv = response.data.naziv;
                this.adresa = response.data.adresa;
                this.opis = response.data.opis;
                this.ocena = response.data.ocena;
            })
            .catch(error => {alert(error.response.data);})
    },
    methods: {
        izmenaKlinike: function() {
            var ok = true;

            if(this.naziv != undefined) {
                this.naziv.trim();
            } else {
                this.naziv = '';
            }

            if(this.adresa != undefined) {
                this.adresa.trim();
            } else {
                this.adresa = '';
            }

            if(this.opis != undefined) {
                this.opis.trim();
            } else {
                this.opis = '';
            }
            
            if(this.naziv === undefined || this.naziv === '' || this.adresa === undefined ||
                this.adresa === '' || this.opis === undefined || this.opis === '') {
                ok = false;
            } else {
                ok = true;
            }

            if (ok) {
                var klinika = {
                    "id" : this.id,
                    "naziv" : this.naziv,
                    "adresa" : this.adresa,
                    "opis" : this.opis,
                    "ocena" : this.ocena
                }
                let jwt = localStorage.getItem('auth');
                var param = klinika.id;
                axios.put('/isa/klinika/update/' + param, klinika, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.href = "#/klinika/profil?klinika=" + param;
                    })
                    .catch(function (error) {
                        alert("Greška.");
                    })
            }
            
        },
        odustani: function(){
            window.location.hash = "#/klinika/profil?klinika=" + this.$route.query.klinika;
        }
    }
});
