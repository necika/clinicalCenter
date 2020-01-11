Vue.component('sala',{
    data: function() {
        return{
            sale : null
        }
    },
    template : `
    <div class="container">
        <div v-if="sale != null" class="table-wrapper">
            <br>
                <form action="#" class="justify-content-center col-lg-5" method="post" v-on:submit.prevent="searchSala" accept-charset="UTF-8">
                    <h2 class="btn btn-success" data-toggle="collapse" data-target="#searchForm" aria-expanded="false" role="button">Pretraga</h2>
                    <div id="searchForm" class="collapse">
                        <div class="form-group">
                            <input type="text" class="form-control" id="inputNazivBroj" placeholder="Unesite naziv/broj sale">
                        </div>
                        <button v-on:click.prevent="pretrazi" class="btn btn-dark">Pretraga</button>
                    </div>
                </form>
                <br>
                <form action="#" class="justify-content-center col-lg-5" method="post" v-on:submit.prevent="filterSala" accept-charset="UTF-8">
                    <h2 class="btn btn-success" data-toggle="collapse" data-target="#filterForm" aria-expanded="false" role="button">Filteri</h2>
                    <div id="filterForm" class="collapse">
                        <div class="form-group">
                            <input type="text" class="form-control" id="inputNaziv" v-on:keyup="filtriraj" placeholder="Unesite naziv sale">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" id="inputKlinikaNaziv" v-on:keyup="filtriraj" placeholder="Unesite naziv klinike">  
                        </div>
                    </div>
                </form>
            <br>
            <table id="ordinationTable" class="fl-table">
                <thead>
                    <tr>
                        <th>Naziv</th>
                        <th>Broj</th>
                        <th>Klinika</th>
                        <th colspan="2">Opcije</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <tr v-for="s in sale">
                        <td id="sf">{{s.naziv}}</td>
                        <td id="sf">{{s.broj}}</td>
                        <td>{{s.klinika.naziv}}</td>
                        <td><router-link :to="{path : '/sala/izmeni', query : {sala : s.id}}" class="btn btn-dark">Izmeni</router-link></td>
                        <td><button v-on:click.prevent="ukloni(s)" class="btn btn-dark">Ukloni</button></td>
                    </tr>
                </tbody>
            </table>
            <br>
            <router-link to="/sala/dodaj">
                <a href="#" class="btn btn-dark btn-sm text-center" role="button">
                    Dodavanje nove ordinacije
                </a>
            </router-link>
            <br>
        </div>
    </div>
    `,
    mounted: function(){
        var saleApp = this;
        let jwt = localStorage.getItem('auth');

        axios.get('/isa/sale', {headers: {Authorization: jwt}})
        .then(function(response) {
            saleApp.sale = response.data;
        })
        .catch(function(error) {
            alert(error.response.data);
        })
    },
    methods: {
        ukloni: function(s){
            let jwt = localStorage.getItem('auth');
            axios.delete('/isa/sale/remove/' + s.id, {headers: {Authorization: jwt}})
            .then(
                document.location.reload(true)
            )
            .catch(error => {alert(error.response.data);})
        },
        filtriraj: function() {
            var inputNaziv, inputKlinika, filterNaziv, table, tr, tdNaziv, i, txtValueNaziv;
            inputNaziv = document.getElementById("inputNaziv");
            inputKlinika = document.getElementById("inputKlinikaNaziv");
            filterNaziv = inputNaziv.value.toUpperCase();
            filterKlinika = inputKlinika.value.toUpperCase();
            table = document.getElementById("ordinationTable");
            tr = table.getElementsByTagName("tr");

            for (i = 0; i < tr.length; i++) {
                tdNaziv = tr[i].getElementsByTagName("td")[0];
                tdKlinika = tr[i].getElementsByTagName("td")[2];
                if (tdNaziv || tdKlinika) {
                  txtValueNaziv = tdNaziv.textContent || tdNaziv.innerText;
                  txtValueKlinika = tdKlinika.textContent || tdKlinika.innerText;
                  if (txtValueNaziv.toUpperCase().indexOf(filterNaziv) > -1 &&
                    txtValueKlinika.toUpperCase().indexOf(filterKlinika) > -1) {
                    tr[i].style.display = "";
                  } else {
                    tr[i].style.display = "none";
                  }
                }
            }
        },
        pretrazi: function() {
            /*Treba dodati unos datuma za rezervaciju sale i prikazati prvi slobodan datum za salu*/
            var filter = document.getElementById("inputNazivBroj").value.toUpperCase();
            var rows = document.querySelector("#ordinationTable tbody").rows;
            
            for (var i = 0; i < rows.length; i++) {
                var firstCol = rows[i].cells[0].textContent.toUpperCase();
                var secondCol = rows[i].cells[1].textContent.toUpperCase();
                if (firstCol.indexOf(filter) > -1 || secondCol.indexOf(filter) > -1) {
                    rows[i].style.display = "";
                } else {
                    rows[i].style.display = "none";
                }      
            }
        }
    }
});

Vue.component('novaSala',{
    data: function() {
        return{
            naziv : undefined,
            broj : undefined,
            klinika : null,
            klinike : null
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
    template : 
        `
        <div class="container">
            <section id="cover" class="table-wrapper">
                <br>
                <div id="cover-caption">
                    <div id="container" class="container">
                        <div class="row text-white">
                            <div class="col-sm-6 offset-sm-3 text-center">
                                <div class="info-form">
                                    <form action="#" class="form-inlin justify-content-center" method="post" v-on:submit.prevent="kreirajSalu" accept-charset="UTF-8">
                                        <div class="form-group">
                                            <label class="labels" for="inputKlinika"><strong>Klinika</strong>
                                                <select v-model="klinika" id="inputKlinika" required>
                                                    <option v-for="k in klinike" v-bind:value="k.id">
                                                        {{k.naziv}}
                                                    </option>
                                                </select>
                                            </label>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="inputNaziv">Naziv</label>
                                            <input type="text" required class="form-control text-center" id="inputNaziv" placeholder="Unesite naziv ordinacije" v-model="naziv">
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="inputBroj">Broj</label>
                                            <input type="text" pattern="[0-9]+" required class="form-control text-center" id="inputBroj" placeholder="Unesite broj ordinacije" v-model="broj">
                                        </div>
                                        <button type="submit" class="btn btn-dark">Dodaj</button>
                                        <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
            </section>
        </div>
        `,
    methods: {
        kreirajSalu : function() {
            var kl_id = this.klinika;
            let jwt = localStorage.getItem('auth');

            var uneseniBroj = parseInt(this.broj);
            var ok = true;

            if(this.naziv != undefined) {
                this.naziv.trim();
            } else {
                this.naziv = '';
            }
            
            if(this.naziv === undefined || this.naziv === '' || uneseniBroj < 0) {
                ok = false;
            } else {
                ok = true;
            }

            if(ok) {
                var sala = {
                    "naziv" : this.naziv,
                    "broj" : uneseniBroj
                };
                
                axios.post('/isa/sale/add/' + kl_id, sala, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.hash = "#/sala";
                    })
                    .catch(function(error) {
                        alert(error.response.data);
                    })
            }
        },
        odustani: function(){
            window.location.hash = "#/sala";
        }
    }
});

Vue.component('izmenaSala',{
    data: function() {
        return{
            id : undefined,
            naziv : undefined,
            broj : undefined
        }
    },
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/sale/' + this.$route.query.sala, {headers: {Authorization: jwt}})
            .then(response =>{
                this.id = response.data.id;
                this.naziv = response.data.naziv;
                this.broj = response.data.broj;
            })
            .catch(error => {alert(error.response.data);})
    },
    template : 
        `
        <div class="container">
            <section id="cover" class="table-wrapper">
                <br>
                <div id="cover-caption">
                    <div id="container" class="container">
                        <div class="row text-white">
                            <div class="col-sm-6 offset-sm-3 text-center">
                                <div class="info-form">
                                    <form action="#" class="form-inlin justify-content-center" method="post" v-on:submit.prevent="izmeniSalu" accept-charset="UTF-8">
                                        <div class="form-group">
                                            <label class="sr-only" for="inputNaziv">Naziv</label>
                                            <input type="text" required class="form-control text-center" id="inputNaziv" placeholder="Unesite naziv ordinacije" v-model="naziv">
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="inputBroj">Broj</label>
                                            <input type="text" pattern="[0-9]+" required class="form-control text-center" id="inputBroj" placeholder="Unesite broj ordinacije" v-model="broj">
                                        </div>
                                        <button type="submit" class="btn btn-dark">Dodaj</button>
                                        <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
            </section>
        </div>
        `,
    methods: {
        izmeniSalu : function() {
            var ok = true;
            let jwt = localStorage.getItem('auth');
            let uneseniBroj = parseInt(this.broj);

            if(this.naziv != undefined) {
                this.naziv.trim();
            } else {
                this.naziv = '';
            }
            
            if(this.naziv === undefined || this.naziv === '' || uneseniBroj < 0) {
                ok = false;
            } else {
                ok = true;
            }

            if(ok) {
                var sala = {
                    "id" : this.id,
                    "broj" : uneseniBroj,
                    "naziv" : this.naziv
                };

                axios.put('/isa/sale/update/' + this.$route.query.sala, sala, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.hash = "#/sala";
                    })
                    .catch(function(error) {
                        alert(error.response.data);
                    })
            }
        },
        odustani: function(){
            window.location.hash = "#/sala";
        }
    }
});
