Vue.component('pregledPacijenata', {
   data: function () {
       return {
           pacijenti: null
       }
   },
    template:
        `
        <div class="table-wrapper">
            <br>
            <form action="#" class="justify-content-center col-lg-5" method="post" accept-charset="UTF-8">
                <h2 class="btn btn-success" data-toggle="collapse" data-target="#searchForm" aria-expanded="false" role="button">Pretraga</h2>
                <div id="searchForm" class="collapse">
                    <div class="form-group">
                        <input type="text" class="form-control" id="inputParametar" placeholder="Unesite ime/prezime/JBO pacijenta">
                    </div>
                    <button v-on:click.prevent="pretrazi" class="btn btn-dark">Pretraga</button>
                </div>
            </form>
            <br>
            <table id="pacijentTable" class="fl-table">
                <thead>
                    <tr>
                        <th v-on:click.prevent="sortTable(0)">Ime</th>
                        <th v-on:click.prevent="sortTable(1)">Prezime</th>
                        <th v-on:click.prevent="sortTable(2)">JBO</th>
                        <th colspan="2">Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="p in pacijenti">
                        <td>{{p.ime}}</td>
                        <td>{{p.prezime}}</td>
                        <td>{{p.jedinstveniBrOsiguranika}}</td>
                        <td><router-link :to="{path : '/pacijent/profil', query : {pacijent : p.id}}" class="btn btn-dark">Profil</router-link></td>
                        <td><router-link :to="{path : '/pacijent/izmeni', query : {pacijent : p.id}}" class="btn btn-dark">Izmeni</router-link></td>                        
                    </tr>
                </tbody>
            </table>
        </div>
        `,
    mounted: function () {
        varK = this;
        let jwt = localStorage.getItem('auth');
        
        axios.get('/isa/pacijent/all', {headers: {Authorization: jwt}}).then(function (response) {
            varK.pacijenti = response.data;
            
        }).catch(function (error) {
            alert(error.response.data);
        })
    },
    methods: {
        sortTable: function (n) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("pacijentTable");
            switching = true;
            dir = "asc"

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
        pretrazi: function() {
            var filter = document.getElementById("inputParametar").value.toUpperCase();
            var rows = document.querySelector("#pacijentTable tbody").rows;
            
            for (var i = 0; i < rows.length; i++) {
                var firstCol = rows[i].cells[0].textContent.toUpperCase();
                var secondCol = rows[i].cells[1].textContent.toUpperCase();
                var thirdCol = rows[i].cells[2].textContent.toUpperCase();
                if (firstCol.indexOf(filter) > -1 || secondCol.indexOf(filter) > -1
                    || thirdCol.indexOf(filter) > -1) {
                    rows[i].style.display = "";
                } else {
                    rows[i].style.display = "none";
                }      
            }
        }
    }

});

Vue.component('profilPacijenta', {
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
            jedinstveniBrojOsiguranika : undefined
        }
    },
    mounted: function () {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/pacijent/' + this.$route.query.pacijent, {headers: {Authorization: jwt}})
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
                this.jedinstveniBrojOsiguranika = response.data.jedinstveniBrOsiguranika;
            })
            .catch(error => {alert(error.response.data);});
    },
    template:
        `
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
            <p></p>
            <router-link :to="{path : '/pacijent/izmeni', query : {pacijent : this.id}}" class="btn btn-dark">Izmeni podatke</router-link>
            <p></p>
        </div>
    </div>
        `
});

Vue.component('potvrdaPrekoEmaila', {
    data: function () {
        return {
            email : undefined
        }
    },
    methods: {
        potvrdi: function () {
            
            var korisnik = {
                "username" : this.$route.params.email
            }
             axios.post('/isa/pacijent/potvrdaPrekoEmailaNaBacku', korisnik)
                .then(function (response) {
                    alert("Uspesno ste se prijavili.")
                    window.location.href = "#/prijava";
                })
                .catch(function (error) {
                    alert(error.response.data);
                });

        }
           
    },
    template:
        `
        <div class="container">
            <h1>Kliknite za aktivaciju..</h1>
            <button v-on:click.prevent="potvrdi" class="btn btn-dark">Potvrdi</button>
        </div>
        `
});

Vue.component('izmenaPacijenta', {
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
            jedinstveniBrojOsiguranika : undefined
        }
    },
    template:
        `
         <div class="container">
        <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="izmeni" accept-charset="UTF-8">
            <h2>Izmena pacijenta</h2>
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
            <button type="submit" class="btn btn-dark">Izmeni</button>
            <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
        </form>	
    </div>
        `,
    mounted: function () {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/pacijent/' + this.$route.query.pacijent, {headers: {Authorization: jwt}})
            .then(response => {
                this.id = response.data.id;
                this.email = response.data.email;
                this.lozinka = response.data.lozinka;
                this.ime = response.data.ime;
                this.prezime = response.data.prezime;
                this.adresa = response.data.adresa;
                this.grad = response.data.grad;
                this.drzava = response.data.drzava;
                this.brTelefona = response.data.brTelefona;
                this.jedinstveniBrojOsiguranika = response.data.jedinstveniBrOsiguranika;
            })
            .catch(error => {alert(error.response.data);});
    },
    methods: {
        izmeni: function () {
            var pac = {
                "id" : this.id,
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
            let jwt = localStorage.getItem('auth');
            axios.put('/isa/pacijent/update/' + this.$route.query.pacijent, pac, {headers: {Authorization: jwt}})
                .then(function (response) {
                    window.location.hash = "#/pacijent/profil?pacijent=" + pac.id;
                })
                .catch(function (error) {
                    alert(error.response.data);
                });
        },
        odustani: function () {
            window.location.hash = "#/pacijent/profil?pacijent=" + this.$route.query.pacijent;
        }
    }
});