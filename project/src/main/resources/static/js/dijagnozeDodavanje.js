Vue.component('dodavanjeSifarnika',{
    data: function() {
        return{
            lekovi : undefined,
            dijagnoze : undefined
        }
    },
    template : `
    <div class="container">
        <form action="#" class="justify-content-center col-lg-6" method="post" v-on:submit.prevent="dodavanjeSifarnika" accept-charset="UTF-8">
            <h2>Dodavanje kombinacije lek/dijagnoza u šifarnik</h2>
            <div class="form-group">
                <label class="labels" for="inputLekovi">Lek</label>
                <input type="text" required class="form-control" id="inputLekovi" placeholder="Unesite naziv leka" v-model="lekovi">
            </div>
            <div class="form-group">
                <label class="labels" for="inputDijagnoze">Dijagnoza</label>
                <input type="text" required class="form-control" id="inputDijagnoze" placeholder="Unesite svoju dijagnozu" v-model="dijagnoze">
                <br>
                <button type="submit" class="btn btn-dark">Dodaj</button>
            <br>
            </div>
        </form>
    </div>	
    `,
    methods: {
        dodavanjeSifarnika: function() {
            var sifarnik = {
                "lekovi" : this.lekovi,
                "dijagnoze" : this.dijagnoze
            };
            
            var ap = this;
            let jwt = localStorage.getItem('auth');
            axios.post('/isa/sifarnici/dodavanje', sifarnik, {headers: {Authorization: jwt}})
                .then(function(response) {
                    window.location.href = '#/sifarnik/dodaj';
                    alert("Dodata je nova kombinacija u šifarnik.");
                    ap.lekovi = undefined;
                    ap.dijagnoze = undefined;
                })
                .catch(function (error) {
                    alert("Greska!");
                })
        }

    }
});