Vue.component('tip',{
    data: function() {
        return{
            tipovi : null,
            naziv : "",
            opis : ""
        }
    },
    template : `
    <div class="container">
        <div v-if="tipovi != null" class="table-wrapper">
            <br>
            <form action="#" class="justify-content-center col-lg-5" method="post" v-on:submit.prevent="pretragaTipova" accept-charset="UTF-8">
                <h2 class="btn btn-success" data-toggle="collapse" data-target="#searchForm" aria-expanded="false" role="button">Pretraga Tipova Pregleda</h2>
                <div id="searchForm" class="collapse">
                    <div class="form-group">
                        <input type="text" class="form-control" id="inputNaziv" placeholder="Unesite naziv tipa" v-model="naziv">
                    </div>
                    <div class="form-group">
                        <input type="text"  class="form-control" id="inputOpis" placeholder="Unesite opis tipa" v-model="opis">  
                    </div>
                    <button v-on:click.prevent="pretrazi" class="btn btn-dark">Pretraga</button>
                </div>
            </form>
            <br>
            <table id="tipTable" class="fl-table">
                <thead>
                    <tr>
                        <th>Naziv</th>
                        <th>Opis</th>
                        <th colspan="2">Opcije</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="t in tipovi">
                        <td>{{t.naziv}}</td>
                        <td>{{t.opis}}</td>
                        <td><router-link :to="{path : '/tip/izmeni', query : {tip : t.id}}" class="btn btn-dark">Izmeni</router-link></td>
                        <td><button v-on:click.prevent="ukloni(t)" class="btn btn-dark">Ukloni</button></td>
                    </tr>
                </tbody>
            </table>
            <br>
            <router-link to="/tip/dodaj">
                <a href="#" class="btn btn-dark btn-sm text-center" role="button">
                    Dodavanje novog tipa pregleda
                </a>
            </router-link>
            <br>
        </div>
    </div>
    `,
    mounted: function(){
        var tipApp = this;
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/tipovi', {headers: {Authorization: jwt}})
        .then(function(response) {
            tipApp.tipovi = response.data;
        })
        .catch(function(error) {
            alert(error.response.data);
        })
    },
    methods: {
        ukloni: function(t) {
            let jwt = localStorage.getItem('auth')
            axios.delete('/isa/tipovi/remove/' + t.id, {headers: {Authorization: jwt}})
            .then(
                document.location.reload(true)
            )
            .catch(error => {alert(error.response.data);})
        },
        pretrazi: function() {
            var tip = {
                "naziv" : this.naziv,
                "opis" : this.opis
            };
            var ap = this;
            let jwt = localStorage.getItem('auth');
            axios.get('/isa/tipovi/pretraga/' + tip.naziv + "," + tip.opis + "," + ",ss", {headers: {Authorization: jwt}})
            .then(function(response) {
                ap.tipovi = response.data;
            })
            .catch(function (error) {
                alert("Greška.");
            }) 
        }
    }
});

Vue.component('noviTip',{
    data: function() {
        return{
            naziv : undefined,
            opis : undefined,
            cena : undefined
        }
    },
    template : 
        `
        <div class="container">
            <section id="cover" class="table-wrapper">
                <br>
                <div id="cover-caption">
                    <div id="container" class="container text-center">
                        <h2>Dodavanje Novog Tipa Pregleda</h2>
                        <div class="row text-white">
                            <div class="col-sm-6 offset-sm-3 text-center">
                                <div class="info-form">
                                    <form action="#" class="form-inlin justify-content-center" method="post" v-on:submit.prevent="kreirajTip" accept-charset="UTF-8">
                                        <div class="form-group">
                                            <label class="sr-only" for="inputNaziv">Naziv</label>
                                            <input type="text" required class="form-control text-center" id="inputNaziv" placeholder="Unesite naziv tipa" v-model="naziv">
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="inputOpis">Opis</label>
                                            <input type="text" required class="form-control text-center" id="inputOpis" placeholder="Unesite opis tipa" v-model="opis">
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
        kreirajTip : function() {
            var tip = {
                "naziv" : this.naziv,
                "opis" : this.opis,
            };

            var ok = true;

            if(this.naziv != undefined) {
                this.naziv.trim();
            } else {
                this.naziv = '';
            }

            if(this.opis != undefined) {
                this.opis.trim();
            } else {
                this.opis = '';
            }
            
            if(this.naziv === undefined || this.naziv === '' || this.opis === undefined || this.opis === '') {
                ok = false;
            } else {
                ok = true;
            }

            var app = this;

            if(ok) {
                let jwt = localStorage.getItem('auth');
                axios.post('/isa/tipovi/add', tip, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.hash = '#/tip/dodaj'
                        alert("Dodat je novi tip pregleda.");
                        app.naziv = undefined;
                        app.opis = undefined;
                    })
                    .catch(function(error) {
                        alert(error.response.data);
                    })
            }
        },
        odustani: function(){
            window.location.hash = '#/tip'
        }
    }
});

Vue.component('izmenaTip',{
    data: function() {
        return{
            id : undefined,
            naziv : undefined,
            opis : undefined
        }
    },
    template : 
        `
        <div class="container">
            <section id="cover" class="table-wrapper">
                <br>
                <div id="cover-caption">
                    <div id="container" class="container text-center">
                        <h2>Izmena Tipa Pregleda</h2>
                        <div class="row text-white">
                            <div class="col-sm-6 offset-sm-3 text-center">
                                <div class="info-form">
                                    <form action="#" class="form-inlin justify-content-center" method="put" v-on:submit.prevent="izmeniTip" accept-charset="UTF-8">
                                        <div class="form-group">
                                            <label class="sr-only" for="inputNaziv">Naziv</label>
                                            <input type="text" required class="form-control text-center" id="inputNaziv" placeholder="Unesite naziv tipa" v-model="naziv">
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="inputOpis">Opis</label>
                                            <input type="text" required class="form-control text-center" id="inputOpis" placeholder="Unesite opis tipa" v-model="opis">
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
    mounted : function() {
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/tipovi/' + this.$route.query.tip, {headers: {Authorization: jwt}})
            .then(response =>{
                this.id = response.data.id;
                this.naziv = response.data.naziv;
                this.opis = response.data.opis;               
            })
            .catch(error => {alert(error.response.data);})
    },
    methods: {
        izmeniTip : function() {
            var tip = {
                "id" : this.id,
                "naziv" : this.naziv,
                "opis" : this.opis
            };

            var ok = true;

            if(this.naziv != undefined) {
                this.naziv.trim();
            } else {
                this.naziv = '';
            }

            if(this.opis != undefined) {
                this.opis.trim();
            } else {
                this.opis = '';
            }
            
            if(this.naziv === undefined || this.naziv === '' || this.opis === undefined || this.opis === '') {
                ok = false;
            } else {
                ok = true;
            }

            if(ok) {
                let jwt = localStorage.getItem('auth');
                axios.put('/isa/tipovi/update/' + this.$route.query.tip, tip, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.hash = '#/tip'
                    })
                    .catch(function(error) {
                        alert(error.response.data);
                    })
            }
        },
        odustani: function(){
            window.location.hash = '#/tip'
        }
    }
});
