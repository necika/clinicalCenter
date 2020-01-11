Vue.component('dodavanjePregled',{
    data: function() {
        return{
            datum : undefined,
            vremePocetka : undefined,
            vremeZavrsetka : undefined,
            trajanje : undefined, 
            cena : undefined,
            sala : null,
            lekar : null,
            sestra : null,
            tipPregleda : null,
            sale : null,
            lekari : null,
            tipovi : null,
            pocetakSat : undefined,
            pocetakMinut : undefined,
            krajSat : undefined,
            krajMinut : undefined,
            sestre : null
        }
    },
    mounted: function () {
        varL = this
        let jwt = localStorage.getItem('auth');
        axios.get('/isa/sale', {headers: {Authorization: jwt}}).then(function (response) {
            varL.sale = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })

        axios.get('/isa/lekar/all', {headers: {Authorization: jwt}}).then(function (response) {
            varL.lekari = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })

        axios.get('/isa/medicinskaSestra/all', {headers: {Authorization: jwt}}).then(function (response) {
            varL.sestre = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })

        axios.get('/isa/tipovi', {headers: {Authorization: jwt}}).then(function (response) {
            varL.tipovi = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })
    },
    template : `
    <div class="container">
    <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="dodaj" accept-charset="UTF-8">
        <h2>Dodavanje single-click pregleda</h2>
        <div class="form-group">
            <label class="labels" for="inputSala"><strong>Sala</strong>
                <select v-model="sala" id="inputSala" required>
                    <option v-for="s in sale" v-bind:value="s.id">
                        {{s.naziv}}
                    </option>
                </select>
            </label>
        </div>
        <div class="form-group">
            <label class="labels" for="inputLekar"><strong>Lekar</strong>
                <select v-model="lekar" id="inputLekar" required>
                    <option v-for="l in lekari" v-bind:value="l.id">
                        {{l.ime}} {{l.prezime}}
                    </option>
                </select>
            </label>
        </div>
        <div class="form-group">
            <label class="labels" for="inputLekar"><strong>Sestra</strong>
                <select v-model="sestra" id="inputSestra" required>
                    <option v-for="s in sestre" v-bind:value="s.id">
                        {{s.ime}} {{s.prezime}}
                    </option>
                </select>
            </label>
        </div>
        <div class="form-group">
            <label class="labels" for="inputTip"><strong>Tip Pregleda</strong>
                <select v-model="tipPregleda" id="inputTip" required>
                    <option v-for="t in tipovi" v-bind:value="t.id">
                        {{t.naziv}}
                    </option>
                </select>
            </label>
        </div>
        <div class="form-group">
            <label class="labels" for="inputDatum"><strong>Datum</strong></label>
            <input type="date" required class="form-control" id="inputDatum" placeholder="Odaberite datum" v-model="datum" >
        </div>
        <div class="form-group">
            <label class="labels" for="inputCena"><strong>Cena</strong></label>
            <input type="number" step="0.01" required class="form-control" id="inputCena" placeholder="Unesite cenu" v-model="cena" >
        </div>
        <div class="form-group">
            <label class="labels"><strong>Početak pregleda</strong></label>
            <div class="time-container">
                <input type="number" onkeypress="return false;" required class="time-div" id="inputPocetakSat" min="0" max="23" placeholder="Sat" v-model="pocetakSat">
                <input type="number" onkeypress="return false;" required class="time-div" id="inputPocetakMinut" min="0" max="59" placeholder="Minut" v-model="pocetakMinut">
            </div>
        </div>
        <div class="form-group">
            <label class="labels"><strong>Završetak pregleda</strong></label>
            <div class="time-container">
                <input type="number" onkeypress="return false;" required class="time-div" id="inputKrajSat" v-bind:min="this.pocetakSat" max="23" placeholder="Sat" v-model="krajSat">
                <input type="number" onkeypress="return false;" required class="time-div" id="inputKrajMinut" v-bind:min="this.pocetakMinut" max="59" placeholder="Minut" v-model="krajMinut">
            </div>
        </div>
        <button type="submit" class="btn btn-dark">Dodaj</button>
        <button v-on:click.prevent="odustani" class="btn btn-dark">Odustani</button>
    </form>	
    </div>
    `,
    methods: {
        dodaj: function() {
            
            this.vremePocetka = this.pocetakSat + ":" + this.pocetakMinut;
            this.vremeZavrsetka = this.krajSat + ":" + this.krajMinut;

            this.trajanje = (this.krajSat - this.pocetakSat) + "h" + (this.krajMinut - this.pocetakMinut) + "min";

            var pregled = {
                "datum" : this.datum,
                "vremePocetka" : this.vremePocetka,
                "vremeZavrsetka" : this.vremeZavrsetka,
                "trajanje" : this.trajanje,
                "cena" : this.cena
            }
            console.log(this.datum);
            var ap = this;
            let jwt = localStorage.getItem('auth');
            axios.post('/isa/pregled/add/' + this.sala + ',' + this.lekar + ',' + this.tipPregleda + ',' + this.sestra + ',dd', pregled, {headers: {Authorization: jwt}})
                .then(function(response) {
                    window.location.hash = "#/pregled/dodaj"
                    alert("Uspešno ste dodali single-click pregled.");
                    ap.datum = undefined;
                    ap.pocetakMinut = undefined;
                    ap.pocetakSat = undefined;
                    ap.krajMinut = undefined;
                    ap.krajSat = undefined;
                    ap.cena = undefined;
                    ap.sala = undefined;
                    ap.lekar = undefined;
                    ap.tipPregleda = undefined;
                })
                .catch(function (error) {
                    alert("Greška.");
                })
            
        },
        odustani: function(){
            window.location.hash = "#/";
        }
    }
});

Vue.component('pregledi', {
    data: function () {
        return {
            pregledi: null
        }
    },
    template:
        `
        <div>
            <br>
                <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="pretragaKlinika" accept-charset="UTF-8">
                   <h2 class="btn btn-success" data-toggle="collapse" data-target="#searchForm" aria-expanded="false" role="button">Pretraga</h2>
                    <div id="searchForm" class="collapse">
                        <div class="form-group">
                            <input type="text"  class="form-control" id="inputNaziv" placeholder="Unesite naziv klinike" v-model="naziv">
                        </div>
                        <div class="form-group">
                            <input type="number" min="1" max="5" class="form-control" id="inputOcena1" placeholder="Unesite min cenu" v-model="ocena1">
                            <input type="number" min="1" max="5" class="form-control" id="inputOcena2" placeholder="Unesite max cenu" v-model="ocena2">  
                        </div>
                        <button type="submit" class="btn btn-dark">Pretraga</button>
                    </div>
                </form>
            <br>
            <div class="content-wrap">
                <div class="row">
                    <div class="col" v-for="pregled in pregledi" v-if="!pregled.rezervisan">
                        <div class="element-wrap">
                            <router-link :to="{path : '/pregled', query : {pregled : pregled.id}}">
                                <a class="tabic">
                                    <p><h5 class="text-uppercase">{{pregled.tipPregleda.naziv}}</h5></p>                               
                                    <p><span class="cena">{{pregled.lekar.klinika.naziv}}</span></p>
<!--                                    <p><span class="cena">{{pregled.datum}}</span></p>-->
                                    <p><span class="cena">Ordinacija {{pregled.sala.naziv}}</span></p>
                                    <p><span class="cena">{{pregled.lekar.user.ime}} {{pregled.lekar.user.prezime}}</span></p>
                                    <p><span class="cena">{{pregled.cena}}€</span></p>
                                </a>
                            </router-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `,
    mounted: function () {
        varP = this;
        let jwt = localStorage.getItem('auth');

        axios.get('/isa/pregled/all', {headers: {Authorization: jwt}})
            .then(function (response) {
                varP.pregledi = response.data;
            })
            .catch(function (error) {
                alert(error.response.data);
            });
    }, methods: {

    }
});

Vue.component('unapredDefinisaniPregledi', {
    data: function () {
        return {
            pregledi: null

        }
    },
    template:
        `
        <div>
            <h2>Brzo do pregleda !</h2>   
            <br>
            <div class="content-wrap">
                    <table id="doctorTable" class="fl-table">
                    <thead>
                        <tr>
                            <th>Datum</th>
                            <th>Pocetak</th>
                            <th>Kraj</th>
                            <th>Cena</th>
                            <th colspan="2">Lekar</th>
                            <th colspan="2">Med. sestra</th>
                            <th>Tip pregleda</th>
                            <th>Opcija</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="pregled in this.pregledi" v-bind:id="pregled.id">
                            <td>{{datePrint(pregled.datum)}}</td>
                            <td>{{pregled.vremePocetka}}</td>
                            <td>{{pregled.vremeZavrsetka}}</td>
                            <td>{{pregled.cena}}</td>
                            <td>{{pregled.lekar.user.ime}}</td>
                            <td>{{pregled.lekar.user.prezime}}</td>
                            <td>{{pregled.medicinskaSestra.user.ime}}</td>
                            <td>{{pregled.medicinskaSestra.user.prezime}}</td>
                            <td>{{pregled.tipPregleda.naziv}}</td>
                            <td><button v-on:click.prevent="zakaziPregled(pregled.id)" class="btn btn-dark">Zakazi pregled</button></td>
                        </tr>
                    </tbody>
                </table>
                
            </div>
        </div>
        `,
    mounted: function () {
        varP = this;
        let jwt = localStorage.getItem('auth');

        axios.get('/isa/pregled/all', {headers: {Authorization: jwt}})
            .then(function (response) {
                varP.pregledi = response.data;
                console.log(varP.pregledi);
            })
            .catch(function (error) {
                alert(error.response.data);
            });
    }, methods: {
       zakaziPregled: function(ajdi) {
            varP = this;
            let jwt = localStorage.getItem('auth');
            axios.get('/isa/pregled/zakaziPredefinisaniPregled/'+ajdi, {headers: {Authorization: jwt}})
                .then(function (response) {
                    alert("Uspesno ste zakazali pregled..");
                    window.location.hash = "#/"
                })
                .catch(function (error) {
                    alert(error.response.data);
                });
        },
        datePrint: function(someDate) {
            let varDate = new Date(someDate);
            return [varDate.getDate(), varDate.getMonth() + 1, varDate.getFullYear()].join("-");
        }
    }
});