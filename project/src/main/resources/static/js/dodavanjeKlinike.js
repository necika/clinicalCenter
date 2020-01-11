Vue.component('dodavanjeKlinike',{
    data: function() {
        return{
            naziv : undefined,
            adresa : undefined,
            opis : undefined,
            ocena : undefined
        }
    },
    template : `
    <div class="container">
        <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="dodavanjeKlinike" accept-charset="UTF-8">
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
            <button type="submit" class="btn btn-dark">Dodaj</button>
        </form>
    </div>
    `,
    computed: {
        

    },
    methods: {
        dodavanjeKlinike: function() {

            var klinika = {
                "naziv" : this.naziv,
                "adresa" : this.adresa,
                "opis" : this.opis,
                "ocena": 0  
            }
                var ap = this;
                let jwt = localStorage.getItem('auth');
                console.log(ap.naziv)
                axios.post('/isa/klinika/dodavanje', klinika, {headers: {Authorization: jwt}})
                    .then(function(response) {
                        window.location.href = "#/klinika/dodaj"
                        alert("Napravljena je klinika.");
                        ap.naziv = undefined;
                        ap.adresa = undefined;
                        ap.opis = undefined;
                        ap.ocena = undefined
                    })
                    .catch(function (error) {
                        alert("Greška.");
                    })
            
        }

    }
});
