Vue.component('administratorKlinickogCentra',{
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
            jedinstveniBrOsiguranika : undefined
        }
    },
    template : `
    <div class="container">
    <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="administratorKlinickogCentra" accept-charset="UTF-8">
        <h2>Dodavanje administratora kliničkog centra</h2>
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
            <label class="labels" for="inputDrzava"><strong>Drzava</strong></label>
            <input type="text" required class="form-control" id="inputDrzava" placeholder="Unesite drzavu" v-model="drzava">
        </div>
        <div class="form-group">
            <label class="labels" for="inputJedinstveniBroj"><strong>Jedinstveni broj osiguranika</strong></label>
            <input type="number" required class="form-control" id="inputJedinstveniBroj" placeholder="Unesite JBO" v-model="jedinstveniBrOsiguranika">
        </div>
        <button type="submit" class="btn btn-dark">Dodaj</button>
    </form>
    </div>
    `,
    computed: {
        

    },
    methods: {
        administratorKlinickogCentra: function() {

            var administratorKlinickogCentra = {
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
                var ap = this;
                let jwt = localStorage.getItem('auth');

                axios.post('/isa/adminKlinickogCentra/dodavanjeAdministratoraKlinickogCentra', administratorKlinickogCentra, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.href = "#/administratorKlinickogCentra/dodaj"
                        alert("Uspešno ste dodali administratora kliničkog centra.");
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
            
        }

    }
});
Vue.component('promenaLozinkeACC',{
    data: function() {
        return{
            lozinkaNova1 : null,
            lozinkaNova2 : null
        }
    },
    template : `
    <div class="container">
    <form action="#" class="form-inlin justify-content-center col-lg-6" method="put" v-on:submit.prevent="promenaPasswordaACC" accept-charset="UTF-8">
        <h2>Promena lozinke administratora kliničkog centra</h2>
        <div class="form-group">
            <label class="labels" for="inputLozinkaStara2"><strong>Nova lozinka</strong></label>
            <input type="password" required class="form-control" id="inputLozinkaStara2" placeholder="Unesite novu lozinku" v-model="lozinkaNova1">  
        </div>
        <div class="form-group">
            <label class="labels" for="inputLozinkaNova"><strong>Potvrdite novu lozinku</strong></label>
            <input type="password" required class="form-control" id="inputLozinkaNova" placeholder="Potvrdite novu lozinku" v-model="lozinkaNova2">  
        </div>
        <button type="submit" class="btn btn-dark">Promeni</button>
    </form>
    </div>
    `,
    computed: {
        

    },
    methods: {

        promenaPasswordaACC: function() {

            if(this.lozinkaNova1 !== this.lozinkaNova2) {
                alert("Stara lozinka se ne poklapa!");
            }else {
                var ap = this;
                let jwt = localStorage.getItem('auth');
                axios.put('/isa/adminKlinickogCentra/promenaLozinke/' +  ap.lozinkaNova2, {headers: {Authorization: jwt}})
                    .then(function(response) {
                         window.location.href = "#/"
                    })
                    .catch(function (error) {
                        alert("Neispravna lozinka.");
                    })


            }
               
        }

    }
});
Vue.component('zahteviZaRegistraciju', {
    data: function () {
        return {
            pacijenti: null,
            komentar : undefined,
            pacijentApp : undefined,
            pacijentId : undefined
        }
    },
    template:
        `
        <div class="container">
            <div class="table-wrapper">
                <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="odbijanjeZahteva" accept-charset="UTF-8">
                <div id="odbijanjeForm" class="collapse">
                    <h3>Dodaj komentar</h3>
                    <div class="form-group">
                       <textarea rows="4" cols="50" required v-model="komentar"></textarea>
                    </div>
                    <button type="submit" class="btn btn-dark">Dodaj</button>
                </div>
                </form>
                <br>
                <table id="doctorTable" class="fl-table">
                    <thead>
                        <tr>
                            <th>Ime</th>
                            <th>Prezime</th>
                            <th>Mail</th>
                            <th colspan="2">Opcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(pacijent, index) in pacijenti" v-bind:id="pacijent.id">
                            <td>{{pacijent.ime}}</td>
                            <td>{{pacijent.prezime}}</td>
                            <td>{{pacijent.email}}</td>
                            <td><button v-on:click.prevent="prihvati(pacijent, pacijent.id)" class="btn btn-success">Prihvati</button></td>
                            <td><h2 class="btn btn-danger" data-toggle="collapse" data-target="#odbijanjeForm" aria-expanded="false" role="button" v-on:click.prevent="odbij(pacijent, pacijent.id)">Odbij</h2></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        `,
    mounted: function () {
        varL = this;
        let jwt = localStorage.getItem('auth');

        axios.get('/isa/pacijent/allDisablePatients', {headers: {Authorization: jwt}}).then(function (response) {
            varL.pacijenti = response.data;
        }).catch(function (error) {
            alert(error.response.data);
        })
    },
    methods: {
        odbij: function(l, elem) {
            this.pacijentApp = l;
            this.pacijentId = elem;
        },
        odbijanjeZahteva: function() {         
            var ap = this;
            let jwt = localStorage.getItem('auth');
            axios.get('/isa/pacijent/odbijanje/' + ap.komentar + "," + ap.pacijentApp.id + "," + ap.pacijentApp.jedinstveniBrOsiguranika, {headers: {Authorization: jwt}})
                .then(function(response) {
                   ap.pacijenti = response.data;
                })
                .catch(function (error) {
                    alert("Mail ne postoji.");
                })
            
        },
        prihvati: function(l,elem){
            var ap = this;

            var patient = {
                "id" : l.id,
                "email" : l.email,
                "password" : l.password,
                "ime" : l.ime,
                "prezime" : l.prezime,
                "brTelefona" : l.brTelefona,
                "grad" : l.grad,
                "drzava" : l.drzava, 
                "adresa" : l.adresa,
                "jedinstveniBrOsiguranika" : l.jedinstveniBrOsiguranika,
                "odobrenaRegistracija" : l.odobrenaRegistracija,
                "username" : l.username
            }

            let jwt = localStorage.getItem('auth');

             axios.post('/isa/pacijent/prihvatanje', patient, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.href = "#/zahteviZaRegistraciju";
                        alert("Mail uspešno poslat.");
                    })
                    .catch(function (error) {
                        alert("Greška.");
                    })
        }
    }

});