<template>
  <v-content>
    <v-card>
      <!--<v-btn href="/#/welcome" class="ma-2" color="orange darken-2" dark>
          <v-icon dark left>mdi-arrow-left</v-icon>Back
      </v-btn>-->
    </v-card>
    <v-container fluid>
      <v-row align="center" justify="center">
        <v-col cols="6">
          <v-card color="blue" dark>
            <v-card-title color="white">User Profile</v-card-title>
          </v-card>
          <v-card>
            <div class="form-group">
              <v-text-field
                disabled
                label="Name"
                name="NameProfile"
                v-model="user.name"
                prepend-icon="mdi-account"
                id="NameProfile"
                type="text"
              />
            </div>
            <div class="form-group">

              <v-text-field
                label="Email"
                v-model="user.email"
                name="EmailProfile"
                id="EmailProfile"
                prepend-icon="mdi-at"
                disabled
                type="email"
              />
            </div>
            <div class="form-group">
              <v-text-field
                label="Username"
                v-model="user.username"
                disabled
                name="UsernameProfile"
                id="UsernameProfile"
                type="text"
              />
            </div>
            <div class="form-group">
              <v-text-field
                label="Tipo"
                v-model="user.tipo"
                name="TipoProfile"
                disabled
                id="TipoProfile"
                type="text"
              />
            </div>
            <div v-if="user.tipo=='ATLETA'">
              <v-card>
                <v-card
                  class="mx-auto"
                  max-width="300"
                  tile
                >
                  <v-list disabled>
                    <v-subheader>Modalidades</v-subheader>
                    <v-list-item-group v-model="item" color="primary">
                      <v-list-item
                        v-for="(item, i) in user.modalidades"
                        :key="i"
                      >
                        <v-list-item-icon>
                          <v-icon>mdi-basketball</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                          <v-list-item-title v-text="item"></v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>
                    </v-list-item-group>
                  </v-list>
                </v-card>

                <v-card
                  class="mx-auto"
                  max-width="300"
                  tile
                >
                  <v-list disabled>
                    <v-subheader>Escaloes</v-subheader>
                    <v-list-item-group v-model="item" color="primary">
                      <v-list-item
                        v-for="(item, i) in user.escaloes"
                        :key="i"
                      >
                        <v-list-item-icon>
                          <v-icon>mdi-arrow-up-bold</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                          <v-list-item-title v-text="item"></v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>
                    </v-list-item-group>
                  </v-list>
                </v-card>

                <v-card
                  class="mx-auto"
                  max-width="300"
                  tile
                >
                  <v-list disabled>
                    <v-subheader>Treinadores</v-subheader>
                    <v-list-item-group v-model="item" color="primary">
                      <v-list-item
                        v-for="(item, i) in user.treinadores"
                        :key="i"
                      >
                        <v-list-item-icon>
                          <v-icon>mdi-brain</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                          <v-list-item-title v-text="item"></v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>
                    </v-list-item-group>
                  </v-list>
                </v-card>
              </v-card>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-content>
</template>
<style scoped>
  img {
    max-height: 100px;
  }
</style>
<script type="text/javascript">
  export default {
    //props: ['user', 'wallets'],
    data() {
      return {
        user: {
          name: undefined,
          email: undefined,
          tipo: undefined,
          username: undefined,
          modalidades: [],
          escaloes: [],
          treinadores: [],
        },
        isUserAutenticado: false,
        item: 1,
        /* items: [
             { text: 'Real-Time', icon: 'mdi-clock' },
             { text: 'Audience', icon: 'mdi-account' },
             { text: 'Conversions', icon: 'mdi-flag' },
         ],*/

      };
    },
    /* beforeCreate: function () {
         if (!localStorage.getItem('username')) {
             this.$router.push('/')
         }
     },*/
    methods: {
      logOut() {
        localStorage.removeItem("username");
      }
    },
    mounted() {
      if (this.user.username != null) {
        this.isUserAutenticado = true;
      } else {
        let userUsername = localStorage.getItem("username");

        if (userUsername == null) {
          console.log("Não esta logado");
          this.$router.push('/login');
          return;
        }
        console.log(userUsername);
        this.$axios({
          method: 'get',
          url: 'api/users/' + userUsername,
          headers: {'Content-Type': 'application/json'}
        })
          .then(response => {
            //console.log(response)
            this.user.email = response.data.email;
            this.user.name = response.data.name;
            this.user.tipo = response.data.tipo;
            this.user.username = response.data.username;

            console.log(this.user.tipo);
            if (this.user.tipo != null && this.user.tipo == 'ATLETA') {
              console.log("OLIII");
              this.$axios({
                method: 'get',
                url: 'api/users/atletas/' + userUsername,
                headers: {'Content-Type': 'application/json'}
              })
                .then(response => {
                  console.log(response.data.modalidades)
                  this.user.modalidades = response.data.modalidades
                  console.log(this.user.modalidades)
                  this.user.escaloes = response.data.escaloes
                  console.log(this.user.escaloes)
                  this.user.treinadores = response.data.treinadores
                  console.log(this.user.treinadores)

                })
                .catch(error => {
                  console.log(error)
                  console.log(this.$axios.defaults.headers)
                })
            }
          })
          .catch(error => {
            console.log(error);
            console.log(this.$axios.defaults.headers);
          })

      }
    }
  };
</script>

<style scoped>
</style>
