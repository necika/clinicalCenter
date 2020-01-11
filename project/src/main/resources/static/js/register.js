Vue.component('registracija',{
    data: function() {
        return{
            ime : undefined,
            prezime : undefined,
            jedinstveniBrojOsiguranika: undefined,
            email : undefined,
            drzava : undefined,
            grad : undefined,
            adresa : undefined,
            telefon : undefined, 
            lozinka : undefined,
            retype_lozinka : undefined
        }
    },
    template : `
    <div class="container">
        <form action="#" class="form-inlin justify-content-center col-lg-6" method="post" v-on:submit.prevent="registrujSe" accept-charset="UTF-8">
            <h2>Registracija korisnika</h2>
            <div class="form-group">
                <label class="labels" for="inputIme">Ime</label>
                <input type="text" required class="form-control" id="inputIme" placeholder="Unesite svoje ime" v-model="ime" >
            </div>
            <div class="form-group">
                <label class="labels" for="inputPrezime">Prezime</label>
                <input type="text" required class="form-control" id="inputPrezime" placeholder="Unesite svoje Prezime" v-model="prezime">
            </div>
            <div class="form-group">
                <label class="labels" for="inputPrezime">Jedinstveni broj osiguranika</label>
                <input type="number" required class="form-control" id="inputJedinstveniBrojOsiguranika" placeholder="Unesite svoj jedinstveni broj osiguranika" v-model="jedinstveniBrojOsiguranika">
            </div>
            <div class="form-group">
                <label class="labels" for="inputEmail">Email</label>
                <input type="email" required class="form-control" id="inputEmail" placeholder="Unesite svoj email" v-model="email">
            </div>
            <div class="form-group">
                <label class="labels" for="inputDrzava">Država</label>
                <input type="text" required class="form-control" id="inputDrzava" placeholder="Unesite državu stanovanja" v-model="drzava">
            </div>
            <div class="form-group">
                <label class="labels" for="inputGrad">Grad</label>
                <input type="text" required class="form-control" id="inputGrad" placeholder="Unesite grad stanovanja" v-model="grad">
            </div>
            <div class="form-group">
                <label class="labels" for="inputAdresa">Adresa</label>
                <input type="text" required class="form-control" id="inputAdresa" placeholder="Unesite adresu stanovanja" v-model="adresa">
            </div>
            <div class="form-group">
                <label class="labels" for="inputTelefon">Telefon</label>
                <input type="tel" required class="form-control" id="inputTelefon" placeholder="Unesite svoj kontakt telefon" v-model="telefon">
            </div>
            <div class="form-group">
                <label class="labels" for="inputLozinkaReg">Lozinka</label>
                <input type="password" required class="form-control" id="inputLozinkaReg" placeholder="Unesite svoju lozinku" v-model="lozinka">
            </div>
            <div class="form-group">
                <label class="labels" for="inputLozinkaRetype">Lozinka</label>
                <input type="password" required class="form-control" id="inputLozinkaRetype" placeholder="Potvrdite svoju lozinku" v-model="retype_lozinka">
                <div> {{retypeLozinkaValidacija}} </div>
            </div>
            <button type="submit" class="btn btn-dark">Registruj se</button>
        </form>
    </div>
    `,
    computed: {
        retypeLozinkaValidacija : function() {
            if(this.retype_lozinka === this.lozinka) {
                return null;
            }  else {
                return 'Potvrda lozinke se ne poklapa';
            }
        }
    },
    methods: {
        registrujSe: function() {
            var pacijent = {
                "email" : this.email,
                "username": this.email,
                "password" : this.lozinka,
                "ime" : this.ime,
                "prezime" : this.prezime,
                "adresa" : this.adresa,
                "grad" : this.grad,
                "drzava" : this.drzava,
                "brTelefona" : this.telefon,
                "jedinstveniBrOsiguranika" : this.jedinstveniBrojOsiguranika
            }

            var ap = this;

            axios.post('/isa/registracija', pacijent)
                .then(function(response) {
                    window.location.href = '#/prijava';
                    alert("Uspesno ste se registrovali.");
                    ap.ime = undefined;
                    ap.prezime = undefined;
                    ap.jedinstveniBrojOsiguranika = undefined;
                    ap.email = undefined;
                    ap.drzava = undefined;
                    ap.grad = undefined;
                    ap.adresa = undefined;
                    ap.telefon = undefined;
                    ap.lozinka = undefined;
                    ap.retype_lozinka = undefined;
                })
                .catch(function (error) {
                    alert(error.response.data);
                });
        }
    }
});
