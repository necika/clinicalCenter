Vue.component('prijava', {
    data: function() {
        return{
            email : undefined,
            lozinka : undefined,
            userDetails : null,
            jwt : null
        }
    },
    template : 
    `
    <div class="container">
        <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="prijaviSe" accept-charset="UTF-8">
            <h2>Prijava korisnika</h2>
            <div class="form-group">
                <label class="labels" for="inputEmail">Email</label>
                <input type="text" required class="form-control" id="inputEmail" placeholder="Unesite svoj email" v-model="email">
            </div>
            <div class="form-group">
                <label class="labels" for="inputLozinka">Lozinka</label>
                <input type="password" id="inputLozinka" required class="form-control" placeholder="Unesite svoju lozinku" v-model="lozinka">
            </div>
            <button class="btn btn-dark" type="submit">Prijava</button>
            <div>
                <br>
                <router-link to="/registracija">
                    <a href="#" class="btn btn-outline-secondary btn-sm" role="button">
                        Ukoliko nemate nalog, ovde se možete registrovati
                    </a>
                </router-link>
                <br>
            </div>
        </form>
    </div>
    `,
    computed : {

    },
    methods : {
        prijaviSe : function () {
            var korisnik = {
                "username" : this.email,
                "password" : this.lozinka
            }

            axios.post('/isa/prijava', korisnik)
                .then(function (response) {
                    this.userDetails = response.data

                    if (this.userDetails) {
                        localStorage.setItem('auth', 'Bearer ' + this.userDetails.accessToken);
                        app.ulogovan = true;
                    }

                    window.location.href = "#/";
                })
                .catch(function (error) {
                    alert(error.response.data);
                });
        }
    }
});