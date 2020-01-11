Vue.component('profil', {
    data : function () {
        return {
            ime: undefined,
            prezime: undefined,
            email: undefined,
            brTelefona: undefined,
            drzava: undefined,
            grad: undefined,
            adresa: undefined
        }
    },
    template:
        `
        <div class="profile-card">
            <div class="content">
                <p><strong>{{this.ime}} {{this.prezime}}</strong></p>
                <p><strong>Pacijent</strong></p>
                <p>{{this.email}}</p>
                <p>{{this.brTelefona}}</p>
                <p>{{this.drzava}}</p>
                <p>{{this.grad}}</p>
                <p>{{this.adresa}}</p>
            </div>
        </div>
        `,
    mounted : function () {
        varP = this;
        let jwt = localStorage.getItem('auth');

        let payload = jwt.split(".")[1];
        payload = window.atob(payload);
        payload = JSON.parse(payload);
        let val = Object.values(payload)[1];
        val = val.toString();
        axios.get('/isa/korisnik/' + val, {headers: {Authorization: jwt}})
            .then(function (response) {
               this.ime = response.data.ime;
               this.prezime = response.data.prezime;
               this.email = response.data.email;
               this.grad = response.data.grad;
               this.drzava = response.data.drzava;
               this.adresa = response.data.adresa;
               this.brTelefona = response.data.brTelefona;
        }).catch(function (error) {
            alert(error.response.data);
        });
    }
});